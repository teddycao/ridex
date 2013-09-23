/*
 * Created on 2005-8-30
 */
package com.inwiss.springcrud.web.depose;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.inwiss.springcrud.command.FileUploadCommand;
import com.inwiss.springcrud.command.ImportDataRecordCommand;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.ImportMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;
import com.inwiss.springcrud.support.txtimport.TableBackuper;
import com.inwiss.springcrud.support.txtimport.UploadFileResolver;

import com.inwiss.springcrud.web.controller.ControllerUtil;


/**
 * 处理文本导入表单的Controller类。
 * 
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DataImportFormController extends SimpleFormController 
{
	
	
	/**参数表数据备份处理类*/
	private TableBackuper tableBackuper;

	/**给Controller使用的工具对象*/
    private ControllerUtil controllerUtil;
    
    /**导入确认页面对应的View名称*/
    private String importConfirmViewName;
    
    public DataImportFormController()
    {
        setCommandClass(FileUploadCommand.class);
    }
    
    /**
     * 
     * @see org.springframework.web.servlet.mvc.BaseCommandController#onBind(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.BindException)
     */
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
    {
        FileUploadCommand fileCommand = (FileUploadCommand) command;

		ImportMeta importMeta = controllerUtil.retrieveImportMetaByRequestParameter(request);
		UploadFileResolver fileResolver = importMeta.getFileResolver();
		
		
		ImportDataRecordCommand[] fileRecordSetCommandArr = fileResolver.resolve(fileCommand.getFile().getInputStream(), importMeta);
		
		fileCommand.setRecords(fileRecordSetCommandArr);
    }
    
	protected Map referenceData(HttpServletRequest request) throws Exception 
	{
	    ImportMeta importMeta = controllerUtil.retrieveImportMetaByRequestParameter(request);
		Map mapContainer = new HashMap();
		mapContainer.put("importMeta",importMeta);
		mapContainer.put("crudMeta", importMeta.getCrudMeta());
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
        
	    String viewName = (crudMeta.getImportConfirmView()==null)?this.getFormView():crudMeta.getImportConfirmView();
		return this.showForm(request,errors,viewName,controlModel);
    }
    
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, 
	        Object command, BindException bindEx) throws Exception 
	{
	    ImportMeta importMeta = controllerUtil.retrieveImportMetaByRequestParameter(request);
		Map model = new HashMap();
		
		model.put("result", command);
		model.put("importMeta",importMeta);
		model.put("crudMeta",importMeta.getCrudMeta());
		model.put("crudViewMeta", RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(importMeta.getCrudMeta().getCrudViewMeta(), request));
		model.put("nrOfElements", new Integer(((FileUploadCommand)command).getRecords().length) );

		String viewName = (importMeta.getCrudMeta().getUploadFileResultView()==null)?importConfirmViewName:importMeta.getCrudMeta().getUploadFileResultView();
		return new ModelAndView(viewName, model);
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
	 * @param tableBackuper The tableBackuper to set.
	 */
	public void setTableBackuper(TableBackuper tableBackuper) 
	{
		this.tableBackuper = tableBackuper;
	}
	
    /**
     * @param controllerUtil The controllerUtil to set.
     */
    public void setControllerUtil(ControllerUtil controllerUtil)
    {
        this.controllerUtil = controllerUtil;
    }
    
    /**
     * @param importConfirmViewName The importConfirmViewName to set.
     */
    public void setImportConfirmViewName(String importConfirmViewName)
    {
        this.importConfirmViewName = importConfirmViewName;
    }
}
