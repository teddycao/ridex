/*
 * Created on 2005-11-30
 */
package com.inwiss.springcrud.web.depose;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
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
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class UpdateBatchController extends SimpleFormController
{
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
    protected Object formBackingObject(HttpServletRequest request) throws Exception
    {
        
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        int nRecordCount = Integer.parseInt(request.getParameter("nrOfElements"));
        
        RecordSetCommand model = new RecordSetCommand();
        model.setRecords(new RecordCommand[nRecordCount]);
        
        for ( int i = 0; i < nRecordCount; i++ )
            model.getRecords()[i] = new RecordCommand();
        
        ServletRequestDataBinder binder = new ServletRequestDataBinder(model,"result");
        binder.bind(request);
        //BindUtils.bind(request, model,"result");
        
        //TODO document this
        if(!this.isFormSubmission(request))
		{
	        for ( int i = 0, nSize = model.getRecords().length; i < nSize; i++ )
	        {	
	        	RecordCommand recCmd = model.getRecords()[i];
	            recCmd.setMapContentBefore(new HashMap(recCmd.getMapContent()));                
    	        //针对RecordSetCommand中的每行数据，应用ColumnMeta[]中定义的ValueGenerator4UpdateBatch。
    	        for ( int j = 0, jSize = crudMeta.getColumnMetas().length; j < jSize; j++)
    	        {
    	            ColumnMeta columnMeta = crudMeta.getColumnMetas()[j];
    	            if ( columnMeta.getValueGenerator4UpdateBatch() != null )
    	            {    
    	            	recCmd.getMapContent().put(columnMeta.getColName(), 
    	                            columnMeta.getValueGenerator4UpdateBatch().getRequestContextString(request));
    	            }
    	        }        			
	        }
		}
        return model;
    }
    
    /**
     * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
     */
    protected Map referenceData(HttpServletRequest request) throws Exception
    {
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
        Map mapContainer = new HashMap();
        mapContainer.put("crudMeta", crudMeta);
        
        //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
        mapContainer.put("crudViewMeta", RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request));
        return mapContainer;
    }
    
    
    /**
     * @see org.springframework.web.servlet.mvc.SimpleFormController#showForm(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.validation.BindException, java.util.Map)
     */
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel)
            throws Exception
    {
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
	    String viewName = (crudMeta.getUpdateBatchConfirmView()==null)?this.getFormView():crudMeta.getUpdateBatchConfirmView();
		return this.showForm(request,errors,viewName,controlModel);
    }
    
    /**
     * 重载isFormSubmission方法，当有参数"requestForm"的时候，表示是从列表页面发出的表单请求，返回false。
     * @see org.springframework.web.servlet.mvc.AbstractFormController#isFormSubmission(javax.servlet.http.HttpServletRequest)
     */
    protected boolean isFormSubmission(HttpServletRequest request)
    {
        return controllerUtil.isFormSubmission(request);
    }
    
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
        RecordSetCommand recordSetCommand = (RecordSetCommand)command;      
        crudService.updateRecordSet(recordSetCommand,crudMeta);
        
        //使用LoggerAdaptor记录日志
        if ( crudLoggerAdaptor != null )
            crudLoggerAdaptor.log(request, AddController.class, crudMeta, recordSetCommand);
        
        return new ModelAndView("listRedirectView","crudMeta",crudMeta);
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
	public CrudService getCrudService() 
	{
		return crudService;
	}
	/**
	 * @param crudService The crudService to set.
	 */
	public void setCrudService(CrudService crudService) 
	{
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
