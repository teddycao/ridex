/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.codehaus.jackson.map.ObjectMapper;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.model.core.RptInfo;
import org.inwiss.platform.security.service.RptInfoManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

/**
 * @author lvhq
 *         <p>
 *         Users Report Parameter Manager
 *         </p>
 */
@Controller
@RequestMapping(value = "/s")
public class RptInfoController {

	Logger logger = LoggerFactory.getLogger(RptInfoController.class);

	@Autowired
	private RptInfoManager rptInfoManager;

	public RptInfoManager getRptInfoManager() {
		return rptInfoManager;
	}

	@Autowired
	public void setRptInfoManager(RptInfoManager rptInfoManager) {
		this.rptInfoManager = rptInfoManager;
	}

	@RequestMapping(value = "/listRptInfoManagers", method = RequestMethod.GET)
	public String listRptInfoManagers() {
		return "report/listRptInfoManager";
	}

	@RequestMapping(value = "/showRptInfos")
	protected @ResponseBody
	Map<String, ? extends Object> showRptInfos(HttpServletRequest request) {
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 30);
		QueryInfo queryInfo = new QueryInfo();

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		try {
			queryInfo.setOffset(start);
			queryInfo.setLimit(limit);
			PartialCollection partialCollection = rptInfoManager
					.listRptInfoItems(queryInfo);

			modelMap.put("result", partialCollection.asList());
			modelMap.put("totalCount", partialCollection.getTotal());
		} catch (Exception ex) {
			modelMap.put("success", false);
		}

		return modelMap;
	}

	@RequestMapping(value = "/retrieveRptInfo", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> retrieveRptInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		try {
			String id = ServletRequestUtils.getStringParameter(request, "id");			
			RptInfo rptInfo = rptInfoManager.retrieveRptInfo(Long.parseLong(id));
			
			//modelMap.put("result", rptInfo);
			//modelMap.put("totalCount", 1);
			//modelMap.put("success", true);
			
			modelMap.put("data", rptInfo);
			modelMap.put("success", true);
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		return modelMap;
	}

	@RequestMapping(value = "/updateRptInfo", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> updateRptInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		String id = null;
		try {			
			Map params = WebUtils.getParametersStartingWith(request, "");
			id = ServletRequestUtils.getStringParameter(request, "id");
			RptInfo rptInfo = rptInfoManager.retrieveRptInfo(Long.parseLong(id));

			BeanUtils.copyProperties(rptInfo,params);
			rptInfoManager.updateRptInfo(rptInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		modelMap.put("success", true);

		return modelMap;
	}

	@RequestMapping(value = "/saveRptInfo", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> saveRptInfo(HttpServletRequest request) {
		RptInfo rptInfo = new RptInfo();
		BeanUtilsBean beanUtils = new BeanUtilsBean();
		Map params = WebUtils.getParametersStartingWith(request, "");

		try {
			beanUtils.copyProperties(rptInfo, params);

			rptInfoManager.createRptInfo(rptInfo);

		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		} catch (BeanAlreadyExistsException e) {
			e.printStackTrace();
		}

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("success", true);

		return modelMap;
	}

	@RequestMapping(value = "/removeRptInfo", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> removeRptInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);

		String ids = null;
		try {
			ids = ServletRequestUtils.getStringParameter(request, "ids");
			String[] interIds = ids.split(",");

			int length = interIds.length;
			for (int i = 0; i < length; i++) {
				String interId = interIds[i];
				rptInfoManager.deleteRptInfo(Long.parseLong(interId));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		modelMap.put("success", true);

		return modelMap;
	}

}
