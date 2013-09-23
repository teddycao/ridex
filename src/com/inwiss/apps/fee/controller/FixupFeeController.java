/**
 * 2011-9-16
 */
package com.inwiss.apps.fee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.inwiss.platform.persistence.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.inwiss.apps.fee.model.FixupFee;
import com.inwiss.apps.fee.persistence.FixupFeeDAO;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.DynamicCrudViewMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;

/**
 * @author lvhq
 * 
 */
@Controller
@RequestMapping(value = "/s/fee")
public class FixupFeeController {

	Logger logger = LoggerFactory.getLogger(FixupFeeController.class);

	@Autowired
	private FixupFeeDAO fixupFeeDAO;

	@RequestMapping(value = "/listFixupFees")
	public String listFixupFees() {
		return "fee/listFixupFees";
	}

	@RequestMapping(value = "/showFixupFees", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> showFixupFees(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		try {
			int start = ServletRequestUtils.getIntParameter(request, "start");
			int limit = ServletRequestUtils.getIntParameter(request, "limit");
			start = start / limit;
			start++;

			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, "");
			if (logger.isDebugEnabled()) {
				logger.debug("FixupFeeController Map[msisdn,startDate]=" + map);
			}
			// 手机号码
			String msisdn = (String) map.get("msisdn");
			// 通话日期 Y-M
			String startDate = (String) map.get("startDate");
			if (startDate != null && !("").equals(startDate)) {
				// 2011-09-01T00:00:00
				startDate = startDate.substring(0, 7);
			}

			FixupFee fixupFee = new FixupFee(start, limit);
			fixupFee.setMsisdn(msisdn);
			fixupFee.setStartDate(startDate);

			Page page = fixupFeeDAO.getFixupList(fixupFee);

			modelMap.put("result", page.getResult());
			modelMap.put("totalCount", page.getTotalCount());
		} catch (ServletRequestBindingException ex) {
			modelMap.put("success", false);
			ex.printStackTrace();
		}
		return modelMap;
	}

	@RequestMapping(value = "/importFile", method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> importFile(HttpServletRequest request) {
		return null;
	}
	
	

	
	
}
