/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.inwiss.platform.common.util.JsonUtils;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.MenuItem;
import org.inwiss.platform.model.ext.TreeItemMenu;
import org.inwiss.platform.security.service.MenuManager;
import org.inwiss.platform.service.exception.ParentItemNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Menu manager
 *@author Raidery
 */
@Controller
@RequestMapping(value="/s/rpt")
public class ReportMenuController {

	Logger logger = LoggerFactory.getLogger(ReportMenuController.class);
	@Autowired
	private MenuManager menuManager;

	public MenuManager getMenuManager() {
		return menuManager;
	}

	@Autowired
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
	
	@RequestMapping(value="/rptmagr",method = RequestMethod.GET)
	public String showRptManager(){
		return "security/rptMagr";
	}
	
	
	
	
	
   
	
	
	
	
}
