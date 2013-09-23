/*
 * Created on 2005-10-16
 */
package com.inwiss.springcrud.web.depose;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.command.RecordSetCommand;
import com.inwiss.springcrud.metadata.ImportMeta;
import com.inwiss.springcrud.support.txtimport.ImportDataPersistentStrategy;
import com.inwiss.springcrud.web.controller.ControllerUtil;
/**
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DataImportController extends AbstractController
{
	/**给Controller使用的工具对象*/
    private ControllerUtil controllerUtil;
    
    /**
     * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ImportMeta importMeta = controllerUtil.retrieveImportMetaByRequestParameter(request);
        
        int nRecordCount = Integer.parseInt(request.getParameter("nrOfElements"));
        
        RecordSetCommand command = new RecordSetCommand();
        command.setRecords(new RecordCommand[nRecordCount]);
        for ( int i = 0; i < nRecordCount; i++ )
            command.getRecords()[i] = new RecordCommand();
        
        /**
         * Spring3.0.5
         */
        ServletRequestDataBinder binder = new ServletRequestDataBinder(command);
        binder.bind(request);
        /**
         * Spring1.2
         */
        //BindUtils.bind(request, command, "");
        
        ImportDataPersistentStrategy persistentStrategy = importMeta.getImportDataPersistentStrategy();
		persistentStrategy.persistentImportData(command, importMeta);
		
		System.out.println("I'm been called!");
		return new ModelAndView("listRedirectView","crudMeta",importMeta.getCrudMeta());
    }
    
    

    /**
     * @param controllerUtil The controllerUtil to set.
     */
    public void setControllerUtil(ControllerUtil controllerUtil)
    {
        this.controllerUtil = controllerUtil;
    }
}
