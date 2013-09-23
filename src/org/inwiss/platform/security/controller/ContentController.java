package org.inwiss.platform.security.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.org.rapid_framework.page.Page;




/**
 * Controller - Spring
 * 
 * @author Raidery
 */
@Controller
@RequestMapping(value="/cms")
public class ContentController  {

	private static Logger logger = LoggerFactory.getLogger(ContentController.class);
	
	
	
	
	@RequestMapping(value = "/viewList", method = RequestMethod.GET)
	public String cmsViewList(Model model) {
		
		
		return "cms/contentViewList";
	}
	/**
	 * 
	 * @param msg
	 * @return
	 */
	private Map<String,Object> getSuccessMap(String msg,boolean isSuccess){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(1);
		modelMap.put("message", msg);
		if(isSuccess){
			modelMap.put("success", true);
		}else{
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
	
	/**
	 * Generates modelMap to return in the modelAndView
	 * @param contacts
	 * @return
	 */
	private Map<String,Object> getMap(Page page){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", page.getTotalCount());
		modelMap.put("data", page.getResult());
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	/**
	 * Generates modelMap to return in the modelAndView in case
	 * of exception
	 * @param msg message
	 * @return
	 */
	private Map<String,Object> getModelMapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return modelMap;
	} 

}
