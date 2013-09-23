/**
 * 
 */
package com.inwiss.springcrud.web.controller;

import java.io.*;
import java.util.*;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.inwiss.platform.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.command.ImportDataRecordCommand;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.DynamicCrudViewMeta;

import com.inwiss.springcrud.metadata.ImportMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;

import com.inwiss.springcrud.support.excelinput.ExcelImportMeta;
import com.inwiss.springcrud.support.paging.OnDemandPagingSourceProvider;
import com.inwiss.springcrud.support.paging.PagedListOnDemandHolder;
import com.inwiss.springcrud.support.txtimport.UploadFileResolver;
import com.inwiss.springcrud.util.UploadConfigBean;
import com.inwiss.springcrud.web.depose.ListExposedModel;


/**
 * <p>
 *    <li>Common List Controller</li>
 * </p>
 */
@Controller
@RequestMapping(value = "/s/hut")
public class FileuploadController {

	Logger logger = LoggerFactory.getLogger(FileuploadController.class);

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
	
	@Autowired
	protected UploadConfigBean uploadConfigBean;

	
	
	/**
	 * If all uploads succeed: {"success":true}
	 * If an upload fails: {"success":false,"error":"Reason for error!"}
	 * @param request
	 * @param fileCommand
	 * @return
	 */
	@RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
	 public @ResponseBody String handleFormUpload(HttpServletRequest request,
        @RequestParam("Filedata") MultipartFile fileCommand) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		ObjectMapper mapper = new ObjectMapper();
		JsonGenerator jsonGenerator = null;
		
		String jsonStr = "{\"success\":true}";
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
	
		ImportMeta importMeta = controllerUtil.retrieveImportMetaByRequestParameter(request);
		
		try {
			String fileFullPath = WebUtils.getRealPath(request.getServletContext(), "/archives");
			UploadFileResolver fileResolver = importMeta.getFileResolver();
			Map  transRes = fileResolver.resolve(fileCommand, importMeta);
			logger.debug(("fileName:" + fileCommand.getOriginalFilename()));
			modelMap.put("success", true);
			
			if(transRes != null){
				modelMap.putAll(transRes);
			}
		} catch (Exception ex) {
				ex.printStackTrace();
				//jsonStr = "{success:false,error:\"" + ex.getMessage() + "\"}";
				modelMap.put("success", false);
				modelMap.put("error", ex.getMessage());
		}
		
		
		try {
			jsonStr = mapper.writeValueAsString(modelMap);
		} catch (Exception ex) {
			jsonStr = "{success:false,error:\"" + ex.getMessage() + "\"}";
			ex.printStackTrace();
		}

		return jsonStr;
		
	}
	
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/batchUploadView", method = RequestMethod.GET)
	public String list(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		 CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
	     //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
	     DynamicCrudViewMeta crudViewMeta = RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request);
	     model.addAttribute("crudViewMeta",crudViewMeta);
	     model.addAttribute("meta",crudMeta);
	     ImportMeta importMeta =  controllerUtil.retrieveImportMetaByRequestParameter(request);
	     model.addAttribute("importMeta",importMeta);
	     
	      String viewer = (crudMeta.getUploadFileView())==null?"batchUploadView":crudMeta.getUploadFileView();
		return "hut/" + viewer;
	}
	
	
	
	/**
	 * 
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/download")
	public String downDocFormDB(HttpServletResponse response,@RequestParam String id){
		
		
		try {
			File file = new File("c:\\temp\\test\\");
			String fileName = "";
			//InputStream in =  blob.getBinaryStream();
			InputStream in = null;
			OutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=\""+ fileName + "\"");
			//out = new FileOutputStream(file);
			FileCopyUtils.copy(in, out);
			out.flush();
			in.close();
	        out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
         
     		
		return null;
	}

}



