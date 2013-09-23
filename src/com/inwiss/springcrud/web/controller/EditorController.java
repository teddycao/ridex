/**
 * 
 */
package com.inwiss.springcrud.web.controller;

import java.util.*;


import javax.servlet.http.HttpServletRequest;


import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.command.RecordSetCommand;

import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.support.CrudLoggerAdaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <p>
 *    <li>Common List Controller</li>
 * </p>
 */
@Controller
@RequestMapping(value = "/s/hut")
public class EditorController {

	Logger logger = LoggerFactory.getLogger(EditorController.class);

	/**日志记录适配器对象*/
    private CrudLoggerAdaptor crudLoggerAdaptor;
    
	@Autowired
	private CrudService crudService;

    /**给Controller使用的工具对象*/
	@Autowired
    private ControllerUtil controllerUtil;
    
	public CrudService getCrudService() {
		return crudService;
	}

	@Autowired
	public void setCrudService(CrudService crudService) {
		this.crudService = crudService;
	}
	/**
	 * 
	 * @param model
	 * @param cmd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody Map<String, ? extends Object> 
				addRecord(Model model,Object cmd,HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);

		Map params = WebUtils.getParametersStartingWith(request, "");
		RecordCommand recordCommand = new RecordCommand();
        try {
        	recordCommand.setMapContent(params);
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
            crudLoggerAdaptor.log(request, EditorController.class, crudMeta, recordCommand); 
        modelMap.put("success", true);
        } catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		return modelMap;
	}
	/**
	 * 
	 * @param model
	 * @param cmd
	 * @param request
	 * @return @ResponseBody Map json data
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> 
				updateRecord(Model model,Object cmd,HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);

		Map params = WebUtils.getParametersStartingWith(request, "");
		RecordCommand recordCommand = new RecordCommand();
        try {
        	recordCommand.setMapContent(params);
        //使用ColumnMeta[]中定义的ContextRelativeStringGenerator对RecordCommand做最后的处理
        for ( int i = 0, nSize = crudMeta.getColumnMetas().length; i < nSize; i++)
        {
            ColumnMeta columnMeta = crudMeta.getColumnMetas()[i];
            if ( columnMeta.getUltimateValueGenerator4Add() != null )
                recordCommand.getMapContent().put(columnMeta.getColName(), 
                        	columnMeta.getUltimateValueGenerator4Add().generateContextRelativeString(request, recordCommand) );
        }
        
        crudService.updateRecord(recordCommand,crudMeta);
        //使用LoggerAdaptor记录日志
        if ( crudLoggerAdaptor != null )
            crudLoggerAdaptor.log(request, EditorController.class, crudMeta, recordCommand); 
        modelMap.put("success", true);
        } catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		return modelMap;
	}
	/**
	 * 批量删除
	 * @param model
	 * @param cmd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/remove")
	public @ResponseBody Map<String, ? extends Object> 
		removeRecord(Model model,Object cmd,HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        //RecordCommand recordCommand = (RecordCommand)cmd;

		RecordCommand recordCommand = null;
		 ObjectMapper mapper = new ObjectMapper();
		 
        try {
        	String jsonSetMap = ServletRequestUtils.getStringParameter(request, "keysMap");
        	jsonSetMap = java.net.URLDecoder.decode(jsonSetMap, "UTF-8");
        	
        	RecordSetCommand recordSetCommand = new RecordSetCommand(); 
        	List<HashMap<String, String>> list = mapper.readValue(jsonSetMap, List.class);

        	List<RecordCommand> cmds = new ArrayList<RecordCommand>();
        	for (HashMap<String, String> hashMap : list) {
        		recordCommand = new RecordCommand();
        		recordCommand.setMapContent(hashMap);
        		cmds.add(recordCommand);
        		
			}
        	RecordCommand[] rcds = (RecordCommand[])cmds.toArray(new RecordCommand[cmds.size()]);
        	recordSetCommand.setRecords(rcds);
        	crudService.deleteRecordSet(recordSetCommand,crudMeta);
         //使用LoggerAdaptor记录日志
        if ( crudLoggerAdaptor != null )
            crudLoggerAdaptor.log(request, EditorController.class, crudMeta, recordSetCommand); 
        modelMap.put("success", true);
        } catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		return modelMap;
	}

}



