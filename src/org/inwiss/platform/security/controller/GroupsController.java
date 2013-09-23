/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.security.service.GroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lvhq
 *
 */
@Controller
//@RequestMapping(value="/s")
public class GroupsController {
	
	@Autowired
	private GroupManager groupManager;

	public GroupManager getGroupManager() {
		return groupManager;
	}

	@Autowired
	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}
	
	@RequestMapping(value="/listGroups",method = RequestMethod.GET)
	public String listGroups(){
		return "security/listGroups";
	}
	
	@RequestMapping(value="/showGroups")
	public @ResponseBody Map<String,? extends Object> showGroups(HttpServletRequest request){
		
		QueryInfo queryInfo = new QueryInfo();
		
		PartialCollection partialCollection = groupManager.listGroups(queryInfo);
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);		
		modelMap.put("data", partialCollection.asList());
		modelMap.put("total", partialCollection.getTotal());
		modelMap.put("success", true);
		
		return modelMap;
	}

}
