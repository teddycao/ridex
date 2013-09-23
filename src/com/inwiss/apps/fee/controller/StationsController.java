/**
 * 
 */
package com.inwiss.apps.fee.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.inwiss.apps.fee.model.CellStation;
import com.inwiss.apps.fee.model.StationsReport;
import com.inwiss.apps.fee.persistence.StationsDAO;
import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.support.CrudLoggerAdaptor;
import com.inwiss.springcrud.web.controller.ControllerUtil;

/**
 * @author lvhq
 * 
 */
@Controller
@RequestMapping(value = "/s/fee")
public class StationsController {

	Logger logger = LoggerFactory.getLogger(StationsController.class);

	/** 日志记录适配器对象 */
	private CrudLoggerAdaptor crudLoggerAdaptor;

	@Autowired
	private CrudService crudService;

	@Autowired
	private StationsDAO stationsDAO;

	/** 给Controller使用的工具对象 */
	@Autowired
	private ControllerUtil controllerUtil;

	public CrudService getCrudService() {
		return crudService;
	}

	@Autowired
	public void setCrudService(CrudService crudService) {
		this.crudService = crudService;
	}

	public StationsDAO getStationsDAO() {
		return stationsDAO;
	}

	@Autowired
	public void setStationsDAO(StationsDAO stationsDAO) {
		this.stationsDAO = stationsDAO;
	}

	@RequestMapping(value = "/submitStations")
	public @ResponseBody
	Map<String, ? extends Object> submitStations(Model model, Object cmd,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);

		ObjectMapper mapper = new ObjectMapper();

		try {
			String jsonSetMap = ServletRequestUtils.getStringParameter(request,
					"keysMap");
			jsonSetMap = java.net.URLDecoder.decode(jsonSetMap, "UTF-8");

			List<HashMap<String, String>> list = mapper.readValue(jsonSetMap,List.class);

			//循环提交审批
			for (HashMap<String, String> hashMap : list) {
				StationsReport sr = new StationsReport();
				sr.setCidDec((String) hashMap.get("CID_DEC"));
				//更新数据，将边漫基站上报中的审批状态更新为2-待审批 
				sr.setStatus(Constants.WAIT_AUDIT);
				stationsDAO.updateStationsReport(sr);
			}

			modelMap.put("success", true);
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		return modelMap;
	}

	@RequestMapping(value = "/aduitPass", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> aduitPass(Model model, Object cmd,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);

		Map params = WebUtils.getParametersStartingWith(request, "");
		RecordCommand recordCommand = new RecordCommand();
		try {
			recordCommand.setMapContent(params);
			recordCommand.getMapContent().put("STATUS", Constants.AUDIT_PASS);
			for (int i = 0, nSize = crudMeta.getColumnMetas().length; i < nSize; i++) {
				ColumnMeta columnMeta = crudMeta.getColumnMetas()[i];
				if (columnMeta.getUltimateValueGenerator4Add() != null)
					recordCommand.getMapContent().put(
							columnMeta.getColName(),
							columnMeta.getUltimateValueGenerator4Add().generateContextRelativeString(request,recordCommand));
			}
			
			String ciDec = (String)recordCommand.getMapContent().get("CID_DEC");
			if(logger.isDebugEnabled()){
				logger.debug("Stations CID_DEC="+ciDec);
			}
			
			int count = stationsDAO.isExist(ciDec);
			if(count == 0){
				//更新数据，将边漫基站上报中的审批状态更新为3-审批通过 
				crudService.updateRecord(recordCommand, crudMeta);
				
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fdate = sdf.format(date);
				//将审核通过的边漫基站上报中的基站数据插入到边漫基站信息表中
				CellStation cs = new CellStation();
				cs.setCiDec(ciDec);
				cs.setFdate(fdate);
				stationsDAO.insertCellStation(cs);
				//设置成功标志
				modelMap.put("success", true);
			}else{
				modelMap.put("message", "基站号["+ciDec+"]在边漫基站信息表中已存在！");
				modelMap.put("success", false);
			}
			
			// 使用LoggerAdaptor记录日志
			if (crudLoggerAdaptor != null)
				crudLoggerAdaptor.log(request, StationsController.class,crudMeta, recordCommand);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/aduitNoPass", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> aduitNoPass(Model model, Object cmd,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		CrudMeta crudMeta = controllerUtil
				.retrieveCrudMetaByRequestParameter(request);

		Map params = WebUtils.getParametersStartingWith(request, "");
		RecordCommand recordCommand = new RecordCommand();
		try {
			recordCommand.setMapContent(params);
			recordCommand.getMapContent().put("STATUS", Constants.AUDIT_NO_PASS);
			for (int i = 0, nSize = crudMeta.getColumnMetas().length; i < nSize; i++) {
				ColumnMeta columnMeta = crudMeta.getColumnMetas()[i];
				if (columnMeta.getUltimateValueGenerator4Add() != null)
					recordCommand.getMapContent().put(
							columnMeta.getColName(),
							columnMeta.getUltimateValueGenerator4Add()
									.generateContextRelativeString(request,recordCommand));
			} 

			//更新数据，将边漫基站上报中的审批状态更新为4-审批不通过 
			crudService.updateRecord(recordCommand, crudMeta);
			// 使用LoggerAdaptor记录日志
			if (crudLoggerAdaptor != null)
				crudLoggerAdaptor.log(request, StationsController.class,
						crudMeta, recordCommand);
			modelMap.put("success", true);
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		return modelMap;
	}
}
