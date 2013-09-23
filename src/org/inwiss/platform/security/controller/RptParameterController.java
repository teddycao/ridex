/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptAndParam;
import org.inwiss.platform.model.core.RptInfo;
import org.inwiss.platform.model.core.RptParam;
import org.inwiss.platform.model.ext.TreeItemRptInfo;
import org.inwiss.platform.security.service.RptAndParamManager;
import org.inwiss.platform.security.service.RptInfoManager;
import org.inwiss.platform.security.service.RptParamManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class RptParameterController {

	Logger logger = LoggerFactory.getLogger(RptParameterController.class);

	@Autowired
	private RptInfoManager rptInfoManager;
	
	@Autowired
	private RptParamManager rptParamManager;
	
	@Autowired
	private RptAndParamManager rptAndParamManager;

	@Autowired
	public void setRptInfoManager(RptInfoManager rptInfoManager) {
		this.rptInfoManager = rptInfoManager;
	}

	@Autowired
	public void setRptParamManager(RptParamManager rptParamManager) {
		this.rptParamManager = rptParamManager;
	}

	@Autowired
	public void setRptAndParamManager(RptAndParamManager rptAndParamManager) {
		this.rptAndParamManager = rptAndParamManager;
	}

	@RequestMapping(value = "/listRptParamManagers", method = RequestMethod.GET)
	public String listRptParamManagers() {
		return "report/listRptParamManager";
	}

	@RequestMapping(value = "/showRptInfotrees")
	public @ResponseBody
	Map<String, ? extends Object> showRptInfotrees(String node) {
		QueryInfo queryInfo = new QueryInfo();
		PartialCollection partials = rptInfoManager.listRptInfoItems(queryInfo);

		Map<String, Object> child = new HashMap<String, Object>(3);
		List treeList = new ArrayList<Map<String, Object>>();

		for (Iterator iterator = partials.iterator(); iterator.hasNext();) {
			RptInfo rptInfo = (RptInfo) iterator.next();
			child.put("id", rptInfo.getId());
			child.put("text", rptInfo.getName());
			child.put("checked", Boolean.FALSE);
			child.put("leaf", Boolean.TRUE);
			treeList.add(child);
		}

		Map modelMap = new HashMap();
		modelMap.put("JSON_OBJECT", treeList);
		return modelMap;
	}

	@RequestMapping(value = "/loadRptParameterTrees")
	public @ResponseBody
	List<TreeItemRptInfo> loadRptParameterTrees(String node) {

		List<TreeItemRptInfo> nodeList = new ArrayList<TreeItemRptInfo>();
		Map<String, Object> paraMap = new HashMap<String, Object>();

		QueryInfo queryInfo = new QueryInfo();
		if (node != null && !node.equals("-1")) {
			paraMap.put("parentItemId", new Long(node));
		}

		queryInfo.setQueryParameters(paraMap);
		PartialCollection<RptInfo> partials = rptInfoManager
				.listRptInfotrees(queryInfo);

		for (Iterator iterator = partials.iterator(); iterator.hasNext();) {
			RptInfo rptInfo = (RptInfo) iterator.next();
			TreeItemRptInfo extNode = new TreeItemRptInfo();
			BeanUtils.copyProperties(rptInfo, extNode);
			boolean isleaf = rptInfo.getChildItems().size() == 0 ? true : false;
			extNode.setLeaf(isleaf);
			nodeList.add(extNode);
		}

		return nodeList;
	}

	@RequestMapping(value="/loadRptParamByRptId",method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object>
						loadRptParamByRptId(HttpServletRequest request){
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 30);
		QueryInfo queryInfo = new QueryInfo();
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {
			String id = ServletRequestUtils.getStringParameter(request, "id");
			RptAndParam rptAndParam = rptAndParamManager.retrieveRptAndParam(Long.parseLong(id));
			String rptId = rptAndParam.getRptId();
			//RptParam rptParam = rptParamManager.retrieveRptParam(Long.parseLong(id));
			//String rptId = rptParam.getRptId();
			Map<String,String> queryParameters = new HashMap<String,String>();
			queryParameters.put("rptId", rptId);
			queryInfo.setQueryParameters(queryParameters);
			
			queryInfo.setOffset(start);
			queryInfo.setLimit(limit);
			PartialCollection partialCollection = rptParamManager.loadRptParamByRptId(queryInfo);
			
			modelMap.put("result", partialCollection.asList());
			modelMap.put("totalCount", partialCollection.getTotal());
		} catch (Exception ex) {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/retrieveRptParam", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> retrieveRptParam(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		try {
			String id = ServletRequestUtils.getStringParameter(request, "id");			
			RptParam rptParam = rptParamManager.retrieveRptParam(Long.parseLong(id));
			
			//modelMap.put("data", rptInfo);
			modelMap.put("result", rptParam);
			modelMap.put("totalCount", 1);
			modelMap.put("success", true);
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		return modelMap;
	}

	@RequestMapping(value = "/updateRptParam", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> updateRptParam(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		String id = null;
		try {			
			Map params = WebUtils.getParametersStartingWith(request, "");
			id = ServletRequestUtils.getStringParameter(request, "id");
			RptParam rptParam = rptParamManager.retrieveRptParam(Long.parseLong(id));

			BeanUtils.copyProperties(rptParam,params);
			rptParamManager.updateRptParam(rptParam);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		modelMap.put("success", true);

		return modelMap;
	}

	@RequestMapping(value = "/saveRptParam", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> saveRptParam(HttpServletRequest request) {
		RptParam rptParam = new RptParam();
		BeanUtilsBean beanUtils = new BeanUtilsBean();
		Map params = WebUtils.getParametersStartingWith(request, "");

		try {
			beanUtils.copyProperties(rptParam, params);

			rptParamManager.createRptParam(rptParam);

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

	@RequestMapping(value = "/removeRptParam", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> removeRptParam(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);

		String ids = null;
		try {
			ids = ServletRequestUtils.getStringParameter(request, "ids");
			String[] interIds = ids.split(",");

			int length = interIds.length;
			for (int i = 0; i < length; i++) {
				String interId = interIds[i];
				rptParamManager.deleteRptParam(Long.parseLong(interId));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		modelMap.put("success", true);

		return modelMap;
	}
	
	@RequestMapping(value="/getRptParamCombo",method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object>
							getRptParamCombo(HttpServletRequest request){
		
		
		return null;
	}
	
}
