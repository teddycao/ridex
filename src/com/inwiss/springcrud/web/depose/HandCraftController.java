/*
 * Created on 2005-11-24
 */
package com.inwiss.springcrud.web.depose;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.util.*;

import com.inwiss.springcrud.command.*;
import com.inwiss.springcrud.metadata.*;
import com.inwiss.springcrud.support.handcraft.*;
import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.HandCraftMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;
import com.inwiss.springcrud.support.CrudLoggerAdaptor;
import com.inwiss.springcrud.web.controller.ControllerUtil;

/**
 * 处理手工补录的Controller。
 * 
 * <p>这是一个与CS2002业务紧密相关的Controller，专门用于处理手工补录的需求。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class HandCraftController extends SimpleFormController
{
    private static final Log logger = LogFactory
            .getLog(HandCraftController.class);

    /**日志记录适配器对象*/
    private CrudLoggerAdaptor crudLoggerAdaptor;
    
    /**CRUD业务逻辑接口对象*/
    private CrudService crudService;
    
    /**给Controller使用的工具对象*/
    private ControllerUtil controllerUtil;
    
    /**
     * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
     */
    protected Object formBackingObject(HttpServletRequest request) throws Exception
    {
        HandCraftMeta handCraftMeta = controllerUtil.retrieveHandCraftMetaByRequestParameter(request);
        CrudMeta crudMeta = handCraftMeta.getCrudMeta();
        
        String defaultFilterWhereClause = crudMeta.getChainedDefaultFilter().getFitlerWhereClause(request);
        String filterWhereClauseForCreateAs = handCraftMeta.getChainedFilterForCreateAs().getFitlerWhereClause(request);
        String filterWhereClauseForForm = handCraftMeta.getChainedFilterForForm().getFitlerWhereClause(request);
        
        List listData = crudService.getItemList(crudMeta, null, defaultFilterWhereClause);
        List listTemplateData = crudService.getItemList(crudMeta, null, filterWhereClauseForCreateAs);
        List listForForm = crudService.getItemList(crudMeta, null, filterWhereClauseForForm);
        
        boolean emptyListData = listData == null || listData.size() == 0;
        boolean emptyListFormData = listForForm == null || listForForm.size() == 0;
        
        if ( logger.isDebugEnabled() )
        {
            if ( !emptyListData && emptyListFormData )
                logger.debug("HandCraft data exists, but should not be edit right now.");
            else if ( emptyListFormData )
                logger.debug("HandCraft data does not exists, try to use create as strategy.");
            else
                logger.debug("HandCraft data exists, try to load from database.");
        }
        
        RecordSetCommand model = handCraftMeta.getFormObjectCreateStrategy().createFormObject(
                this.isFormSubmission(request),
                listData,
                listForForm, 
                listTemplateData);
        
        //应用CandidateValueGenerator4Add或者CandidateValueGenerator4Edit
        if(!this.isFormSubmission(request))
        {
            for ( int i = 0, nSize = model.getRecords().length; i < nSize; i++ )
	        {	
	        	RecordCommand recCmd = model.getRecords()[i];
    	        //针对RecordSetCommand中的每行数据，应用ColumnMeta[]中定义的CandidateValueGenerator。
    	        for ( int j = 0, jSize = crudMeta.getColumnMetas().length; j < jSize; j++)
    	        {
    	            ColumnMeta columnMeta = crudMeta.getColumnMetas()[j];
    	            if ( emptyListFormData && columnMeta.getCandidateValueGenerator4Add() != null )
    	            {    
    	            	recCmd.getMapContent().put(columnMeta.getColName(), 
    	                            columnMeta.getCandidateValueGenerator4Add().getRequestContextString(request));
    	            }
    	            if ( !emptyListFormData && columnMeta.getCandidateValueGenerator4Edit() != null )
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
     * @see org.springframework.web.servlet.mvc.AbstractFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
     */
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception
    {
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
        Map mapModel = new HashMap();
        mapModel.put("crudMeta", crudMeta);
        //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
        mapModel.put("crudViewMeta", RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request));
        
        String defaultFilterWhereClause = crudMeta.getChainedDefaultFilter().getFitlerWhereClause(request);
        List listData = crudService.getItemListWithOffset(crudMeta, null, 0, Integer.MAX_VALUE, defaultFilterWhereClause);
        mapModel.put("listData", listData);
        
        defaultFilterWhereClause = null;
        listData = null;
        
        return mapModel;
    }
    
    
    /**
     * @see org.springframework.web.servlet.mvc.SimpleFormController#showForm(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.validation.BindException, java.util.Map)
     */
    protected ModelAndView showForm(HttpServletRequest request,
            HttpServletResponse response, BindException errors, Map controlModel)
            throws Exception
    {
        HandCraftMeta crudMeta = controllerUtil.retrieveHandCraftMetaByRequestParameter(request);
        
	    String viewName = (crudMeta.getHandCraftView()==null)?this.getFormView():crudMeta.getHandCraftView();
		return this.showForm(request,errors,viewName,controlModel);
    }
    
    /**
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
        
        String defaultFilterWhereClause = crudMeta.getChainedDefaultFilter().getFitlerWhereClause(request);
        int nCount = crudService.getNrOfElements(crudMeta, null, defaultFilterWhereClause);
        
        
        String strHandCraftDataDoesNotExist = request.getParameter("handCraftDataDoesNotExist");
        
        boolean handCraftDataDoesNotExist = "true".equals(strHandCraftDataDoesNotExist);
        
        RecordSetCommand recordSetCommand = (RecordSetCommand)command;
    	
        //使用ColumnMeta[]中定义的ContextRelativeStringGenerator对RecordSetCommand做最后的处理
        for ( int i = 0, nSize = recordSetCommand.getRecords().length; i < nSize; i++ )
        {
            for ( int j = 0, jSize = crudMeta.getColumnMetas().length; j < jSize; j++ )
            {
                ColumnMeta columnMeta = crudMeta.getColumnMetas()[j];
                //如何之前没有补录过数据，则应用UltimateValueGenerator4Add
                if ( handCraftDataDoesNotExist && columnMeta.getUltimateValueGenerator4Add() != null )
                {
                    recordSetCommand.getRecords()[i].getMapContent().put(columnMeta.getColName(),
                            columnMeta.getUltimateValueGenerator4Add().generateContextRelativeString(request, recordSetCommand.getRecords()[i]) );
                }
                
                //如果是在之前补录数据的基础上进行修改，则应用UltimateValueGenerator4Edit
                if ( !handCraftDataDoesNotExist && columnMeta.getUltimateValueGenerator4Edit() != null )
                {
                    recordSetCommand.getRecords()[i].getMapContent().put(columnMeta.getColName(),
                            columnMeta.getUltimateValueGenerator4Edit().generateContextRelativeString(request, recordSetCommand.getRecords()[i]) );
                }
                
            }
        }
        
        if ( nCount == 0 )
            crudService.insertRecordSet(recordSetCommand,crudMeta);
        else
            crudService.updateRecordSet(recordSetCommand,crudMeta);
        
        //使用LoggerAdaptor记录日志
        if ( crudLoggerAdaptor != null )
            crudLoggerAdaptor.log(request, HandCraftController.class, crudMeta, recordSetCommand);

        return new ModelAndView("handCraftRedirectView", "crudMeta", crudMeta);
    }
    
    
    
    /**
     * @param controllerUtil The controllerUtil to set.
     */
    public void setControllerUtil(ControllerUtil controllerUtil)
    {
        this.controllerUtil = controllerUtil;
    }
    /**
     * @param crudLoggerAdaptor The crudLoggerAdaptor to set.
     */
    public void setCrudLoggerAdaptor(CrudLoggerAdaptor crudLoggerAdaptor)
    {
        this.crudLoggerAdaptor = crudLoggerAdaptor;
    }
    /**
     * @param crudService The crudService to set.
     */
    public void setCrudService(CrudService crudService)
    {
        this.crudService = crudService;
    }
}
