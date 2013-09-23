/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Permission;
import org.inwiss.platform.security.service.PermissionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Raidery
 *
 */
@Controller
@RequestMapping(value="/s")
public class PermissionsController {
	
	 Logger logger = LoggerFactory.getLogger(PermissionsController.class);
	 
	  
	protected BeanUtilsBean beanUtils = null;
	
	
	public PermissionsController() {
		 beanUtils = new BeanUtilsBean();
	}

	@Autowired
	private PermissionManager permManager;

	public PermissionManager getPermManager() {
		return permManager;
	}
	
	
	@Autowired
	public void setUserManager(PermissionManager permManager) {
		this.permManager = permManager;
	}

	@RequestMapping(value="/listPermissions",method = RequestMethod.GET)
	public String listUsers(){
		return "security/listPermissions";
	}
	
	@RequestMapping(value="/viewPermission",method = RequestMethod.GET)
	public String oneUserView(){
		return "security/viewPermission";
	}
	
	@RequestMapping(value="/showPermissions")
	public @ResponseBody Map<String, ? extends Object> 
						showPermissions(HttpServletRequest request){
		
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 30);
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		QueryInfo queryInfo = new QueryInfo();
		try {
		
		queryInfo.setOffset(start);
		 queryInfo.setLimit(limit);
		 PartialCollection partialCollection = permManager.listPermissions(queryInfo);
		
		
		modelMap.put("result", partialCollection.asList());
		modelMap.put("totalCount", partialCollection.getTotal());
		modelMap.put("success", true);
		} catch (Exception ex) {
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getPermissions",method = RequestMethod.GET)
	protected @ResponseBody Map<String, ? extends Object> 
			  retrievePermission(Model model,@RequestParam(value="pid",  required=false) Long pid){
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		
		Permission perm = permManager.retrievePermission(pid);

		modelMap.put("data", perm);
		
		modelMap.put("success", true);
		
		
		return modelMap;
	}
	
	
	
}
