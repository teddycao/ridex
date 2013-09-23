/*
 * Created on 2005-8-5
 */
package com.inwiss.springcrud.web.depose;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;
import com.inwiss.springcrud.support.CrudLoggerAdaptor;
import com.inwiss.springcrud.web.controller.ControllerUtil;

/**
 * 新增记录的Controller类。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class AddController extends SimpleFormController
{  
    /**日志记录适配器对象*/
    private CrudLoggerAdaptor crudLoggerAdaptor;
    
    /**CRUD业务逻辑接口对象*/
    private CrudService crudService;
    
    /**给Controller使用的工具对象*/
    private ControllerUtil controllerUtil;
    
    /**
     * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
     */
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        RecordCommand command = new RecordCommand();
        
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
        //遍历ColumnMeta[]，应用其中的CandidateValueGenerator。
        for ( int i = 0, nSize = crudMeta.getColumnMetas().length; i < nSize; i++)
        {
            ColumnMeta columnMeta = crudMeta.getColumnMetas()[i];
            if ( columnMeta.getCandidateValueGenerator4Add() != null )
                command.getMapContent().put(columnMeta.getColName(), 
                        	columnMeta.getCandidateValueGenerator4Add().getRequestContextString(request) );
        }
        return command;
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
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, Map controlModel)
			throws Exception 
	{
	    CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
	    String viewName = (crudMeta.getAddView()==null)?this.getFormView():crudMeta.getAddView();
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
    
    /**
     * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
        RecordCommand recordCommand = (RecordCommand)command;
    	
        //使用ColumnMeta[]中定义的ContextRelativeStringGenerator对RecordCommand做最后的处理
        for ( int i = 0, nSize = crudMeta.getColumnMetas().length; i < nSize; i++)
        {
            ColumnMeta columnMeta = crudMeta.getColumnMetas()[i];
            if ( columnMeta.getUltimateValueGenerator4Add() != null )
                recordCommand.getMapContent().put(columnMeta.getColName(), 
                        	columnMeta.getUltimateValueGenerator4Add().generateContextRelativeString(request, recordCommand) );
        }
        
        crudService.insertRecord(recordCommand,crudMeta);
        
        //使用LoggerAdaptor记录日志
        if ( crudLoggerAdaptor != null )
            crudLoggerAdaptor.log(request, AddController.class, crudMeta, recordCommand);
        
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
