/*
 * Created on 2005-8-5
 */
package com.inwiss.springcrud.web.depose;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
//import org.springframework.web.bind.BindUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.command.RecordSetCommand;
import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;
import com.inwiss.springcrud.support.CrudLoggerAdaptor;
import com.inwiss.springcrud.web.controller.ControllerUtil;


/**
 * 用于处理编辑整页数据的Controller类。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class EditPageController extends SimpleFormController
{
    private static final Log log = LogFactory.getLog(EditPageController.class);

    /**日志记录适配器对象*/
    private CrudLoggerAdaptor crudLoggerAdaptor;
    
    /**CRUD业务逻辑接口对象*/
    private CrudService crudService;
    
    /**给Controller使用的工具对象*/
    private ControllerUtil controllerUtil;
    
    /**
     * 构造表单对象，接收从列表展现界面POST过来的数据，并填充到表单对象中。
     * 
     * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
     */
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);

        //获取列表界面传入的记录条数，进而进行RecordSetCommand的初始化工作。
        int nRecordCount = Integer.parseInt(request.getParameter("nrOfElements"));
        
        RecordSetCommand model = new RecordSetCommand();
        model.setRecords(new RecordCommand[nRecordCount]);
        for ( int i = 0; i < nRecordCount; i++ )
            model.getRecords()[i] = new RecordCommand();
        
        //绑定从列表界面传入的数据
        ServletRequestDataBinder binder = new ServletRequestDataBinder(model,"result");
        binder.bind(request);
        //BindUtils.bind(request, model, "result");
        
        //在请求表单的时候，将绑定后的数据（即修改前的数据）复制一份，放到每个RecordCommand的mapContentBefore中，
	    //以便为日志记录服务
        if(!this.isFormSubmission(request))
		{
	        for ( int i = 0, nSize = model.getRecords().length; i < nSize; i++ )
	        {	
	        	RecordCommand recCmd = model.getRecords()[i];
	            recCmd.setMapContentBefore(new HashMap(recCmd.getMapContent()));                
    	        //针对RecordSetCommand中的每行数据，应用ColumnMeta[]中定义的CandidateValueGenerator。
    	        for ( int j = 0, jSize = crudMeta.getColumnMetas().length; j < jSize; j++)
    	        {
    	            ColumnMeta columnMeta = crudMeta.getColumnMetas()[j];
    	            if ( columnMeta.getCandidateValueGenerator4Edit() != null )
    	            {    
    	            	recCmd.getMapContent().put(columnMeta.getColName(), 
    	                            columnMeta.getCandidateValueGenerator4Edit().getRequestContextString(request));
    	            }
    	        }        			
	        }
		}
        
        return model;
    }
    
	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#showForm(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.validation.BindException, java.util.Map)
	 */
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, Map controlModel)
			throws Exception 
	{
	    CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
	    String viewName = (crudMeta.getEditPageView()==null)?this.getFormView():crudMeta.getEditPageView();
		return this.showForm(request,errors,viewName,controlModel);
	}    
    
    /**
     * @see org.springframework.web.servlet.mvc.AbstractFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
     */
    protected Map referenceData(HttpServletRequest request, Object command,
            Errors errors) throws Exception
    {
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
        Map mapModel = new HashMap();
        mapModel.put("crudMeta", crudMeta);
        //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
        mapModel.put("crudViewMeta", RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request));
        return mapModel;
    }
    

    /**
     * 重载isFormSubmission方法，当有参数"requestForm"的时候，表示是从列表页面发出的表单请求，返回false。
     * 
     * @see org.springframework.web.servlet.mvc.AbstractFormController#isFormSubmission(javax.servlet.http.HttpServletRequest)
     */
    protected boolean isFormSubmission(HttpServletRequest request)
    {
        return controllerUtil.isFormSubmission(request);
    }
    
    
    
    /**
     * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
        RecordSetCommand recordSetCommand = (RecordSetCommand)command;
    	
        //使用ColumnMeta[]中定义的ContextRelativeStringGenerator对RecordSetCommand做最后的处理
        for ( int i = 0, nSize = recordSetCommand.getRecords().length; i < nSize; i++ )
        {
            for ( int j = 0, jSize = crudMeta.getColumnMetas().length; j < jSize; j++ )
            {
                ColumnMeta columnMeta = crudMeta.getColumnMetas()[j];
                if ( columnMeta.getUltimateValueGenerator4Edit() != null )
                {
                    recordSetCommand.getRecords()[i].getMapContent().put(columnMeta.getColName(),
                            columnMeta.getUltimateValueGenerator4Edit().generateContextRelativeString(request, recordSetCommand.getRecords()[i]) );
                }
            }
        }
        
        crudService.updateRecordSet(recordSetCommand,crudMeta);
        
        //使用LoggerAdaptor记录日志
        if ( crudLoggerAdaptor != null )
            crudLoggerAdaptor.log(request, EditPageController.class, crudMeta, recordSetCommand);

        return new ModelAndView("listRedirectView", "crudMeta", crudMeta);
    }
	
    /**
     * @param crudLoggerAdaptor The crudLoggerAdaptor to set.
     */
    public void setCrudLoggerAdaptor(CrudLoggerAdaptor crudLoggerAdaptor)
    {
        this.crudLoggerAdaptor = crudLoggerAdaptor;
    }
	/**
	 * @return Returns the crudService.
	 */
	public CrudService getCrudService() {
		return crudService;
	}
	/**
	 * @param crudService The crudService to set.
	 */
	public void setCrudService(CrudService crudService) {
		this.crudService = crudService;
	}

	
    /**
     * @param controllerUtil The controllerUtil to set.
     */
    public void setControllerUtil(ControllerUtil controllerUtil)
    {
        this.controllerUtil = controllerUtil;
    }
}
