/**
 * 
 */
package org.inwiss.platform.report.controller;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.inwiss.platform.cache.CacheWrapper;
import org.inwiss.platform.report.param.EntryHashMap;
import org.inwiss.platform.report.param.ParamDictBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author Raidery
 *
 */
@Controller
@RequestMapping(value="/s/rpt")
public class ReportParamController {
	
	 Logger logger = LoggerFactory.getLogger(ReportParamController.class);
	 
	
	@Autowired
	protected JdbcTemplate jdbcTemplate = null;
		
	@Autowired
	protected CacheWrapper<String, ParamDictBean> paramCache;
	 



	@RequestMapping(value="/rptParamView")
	public String rptParamView(Model model,@RequestParam(value="rptid",  required=true) String rptid){

		String sqlStr = "SELECT ROWNUM, RPT_ID,PARAM_NAME,PARAM_TITLE,PARAM_DESC,PARAM_TYPE, PARAM_REF,RPT_FREQ,PARAM_TIP FROM al_rpt_param_def WHERE RPT_ID = ? ORDER BY P_SORT ";
		List params = jdbcTemplate.queryForList(sqlStr, rptid);

		model.addAttribute("rptid", rptid);
		model.addAttribute("rptParams", params);
		
		return "report/rptParamView";
	}
	
	
	@RequestMapping(value="/reportviewer")
	public String reportviewer(Model model,HttpServletRequest request){
		
		/**报表ID*/
		String rptid = request.getParameter("rptid");
		/**应用根目标*/
		String tplRoot = request.getServletContext().getRealPath("/");
		String tplPath = null;
		//contextRoot
		if (rptid != null && rptid.startsWith("cr:")) {
		
			if (!tplRoot.endsWith(File.separator)) {
				tplRoot += File.separator;
			}
			tplRoot += rptid.substring(rptid.indexOf(":") + 1);
			rptid = tplRoot.replace('\\', '/');
		}
		
		if (!tplRoot.endsWith(File.separator)) {
			tplRoot += File.separator;
		}
		 
		tplPath = tplRoot + ("WEB-INF"+File.separator+"report"+File.separator+"templates");
		String rptFile = tplPath + File.separator + rptid+".xml";
		
		rptFile = rptFile.replace('\\','/');
		model.addAttribute("rptFile", rptFile);
		return "report/defaultviewer";
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/paramSelectVal")
	protected @ResponseBody Map<String, ? extends Object>
							getParamSelectValue(HttpServletRequest request,String code){
	
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {
			Object cacheCode = null;
			Collection<EntryHashMap> entrys  =null;
			
			
	        if ((cacheCode = this.paramCache.get(code)) != null) {
	        	entrys =	((ParamDictBean) cacheCode).getEntryList();
	        
	        }
			
			
			modelMap.put("result", entrys);
			modelMap.put("totalCount", entrys.size());
		} catch (Exception ex) {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
	
	
	/**
	 * @return the paramCache
	 */
	public CacheWrapper<String, ParamDictBean> getParamCache() {
		return paramCache;
	}



	/**
	 * @param paramCache the paramCache to set
	 */
	public void setParamCache(CacheWrapper<String, ParamDictBean> paramCache) {
		this.paramCache = paramCache;
	}
	
	
	
	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}



	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}
