/**
 * 
 */
package com.inwiss.apps.fee.controller;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.inwiss.apps.fee.persistence.FeeSendbackDao;
import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.entity.FileUploadBean;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.ImportMeta;
import com.inwiss.springcrud.support.txtimport.UploadFileResolver;
import com.inwiss.springcrud.util.UploadConfigBean;
import com.inwiss.springcrud.web.controller.ControllerUtil;


/**
 * <p>
 *    <li>Common List Controller</li>
 * </p>
 */
@Controller
@RequestMapping(value = "/s/fee")
public class FeeSendbackController  {

	Logger logger = LoggerFactory.getLogger(FeeSendbackController.class);

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

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
    protected  FeeSendbackDao feeSendbackDao;
	
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String showMainPanel(Model model,HttpServletRequest request) {
		
		model.addAttribute("vproducts", queryVProduct());
		return "fee/sendback_main" ;
	}
	
	
	/**
	 * 导入用户语音清单
	 * @param uploadItem
	 * @param result
	 * @param request
	 * @return
	 */
	 @RequestMapping(value = "/feeGsmImport", method = RequestMethod.POST)
	 public ResponseEntity<String> feeGsmImport(FileUploadBean uploadItem,BindingResult result, 
			 HttpServletRequest request){  
	   
	        JsonResult jsonResult = new JsonResult();
	        
	        //文件名称:1380917xxxx[201109]_语音_清单.xls
	        Map<String, Object> params = WebUtils.getParametersStartingWith(request, ""); 
	        String ishomeuser = (String) params.get("ishomeuser");
	        String isdouble = (String) params.get("isdouble");
	       
	      try{
	        	
	        if (result.hasErrors()){  
	            for(ObjectError error : result.getAllErrors()){  
	                System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());  
	            }  
	            //set <a target="_blank" title="extjs" href="http://sencha.com/">extjs</a> return - error  
	            jsonResult.setSuccess(false);  
	            return null;
	        }  
	        
	        //Map<String,String> addtionMap = getPhoneBillDate(uploadItem.getFile().getOriginalFilename());
	      //检查用户有没有退过费,不能重复退费
	        String msisdn = (String)params.get("msisdn");
	        String billdate = (String)params.get("billdate");
	        String vprice = (String)params.get("vprice");
	        String prd_price = (String)params.get("prd_price");
	        if(vprice ==null || vprice.equals("")){
	        	vprice = "0";
	        }
	        
			 if(hasBillSendback(msisdn,billdate)){
				 jsonResult.setSuccess(false);  
				 jsonResult.setMessage("该户有已经退过费,不能重复退费！");
				 return buildResponseEntity(jsonResult.toString());
			 }else{
				 deleteUploaedGsm(msisdn,billdate);
			 }
			 
    		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
    		ImportMeta importMeta = controllerUtil.retrieveImportMetaByRequestParameter(request);
	        String fileFullPath = WebUtils.getRealPath(request.getServletContext(), "/archives");
			UploadFileResolver fileResolver = importMeta.getFileResolver();
			Map<String,String>  transRes = fileResolver.resolve(uploadItem.getFile(), importMeta);			
			
			transRes.put("MSISDN", msisdn);
			transRes.put("BILL_DATE", billdate);
			
			if(transRes.get("MSISDN")!=null){
				jsonResult.setMsisdn(transRes.get("MSISDN"));		
			}
			
			if(transRes.get("BILL_DATE")!=null){
				jsonResult.setBilldate(transRes.get("BILL_DATE"));		
			}
			
			 jsonResult.setSuccess(true);
	   	 }catch (Exception ex) {	   		
	   		 logger.debug("Test upload: " + uploadItem.getFile().getOriginalFilename());  
	   		 jsonResult.setSuccess(false); 
	   	    jsonResult.setMessage("文件导入发生错误");
	   	    ex.printStackTrace();
	    }
	       
	        return buildResponseEntity(jsonResult.toString()); 
	        //return jsonResult.toString();
	   
	} 
		
	 private ResponseEntity buildResponseEntity(String jsonResult){
		 HttpHeaders headers = new HttpHeaders();  
	        MediaType mt=new MediaType("text","html",Charset.forName("UTF-8"));  
	        headers.setContentType(mt);  
	        ResponseEntity<String> responseEntity =new ResponseEntity<String>(jsonResult, headers, HttpStatus.OK);    
	        return responseEntity; 
	 }
	 
		/**
		 * 是否已经上传过文件,删除已经上传并且未退费的记录
		 * @param msisdn
		 * @param billdate
		 * @return
		 */
		public int  deleteUploaedGsm(String msisdn,String billdate){
			
			//统计用户是否已经上传该次通话信息
			String isUploadGsm = "delete from FEE_GSMDETAIL_TP where MSISDN = ? and BILL_DATE = ?";
			
			//检查用户有没有退过费,不能重复退费
			int allowUploadGsm = jdbcTemplate.update(isUploadGsm,new String[] {msisdn,billdate});
			 
			String deletInvalidData = "delete from FEE_SENDBACK_HIS where MSISDN = ? and BILL_DATE = ? and status = '0' ";
			jdbcTemplate.update(deletInvalidData,new String[] {msisdn,billdate});
			
			return allowUploadGsm;
			 
		}
		
	   private Map<String,String> getPhoneBillDate(String fileName){
			Map<String,String> dataMap = new HashMap<String,String>(2);
			String phone = fileName.substring(0,11);
			String date =  fileName.substring(12, 18);
			dataMap.put("MSISDN", phone);
			dataMap.put("BILL_DATE", date);
			logger.debug(phone +"-->"+date);
			return dataMap;
		}
	 
	   /**
	    * 判断用户是否已经退费
	    *   //status 0: 代表初始状态     -1: 无记录不能退费        1: 已经成功退费  9999:出错
	    * @param msisdn
	    * @param billdate
	    * @return
	    */
	   
		public boolean  hasBillSendback(String msisdn,String billdate){
			/**统计用户是否已经退费*/
			 String isSendback = "select count(1) CNT from FEE_SENDBACK_HIS where MSISDN = ? and BILL_DATE = ? and status='1' ";
			 Integer allowSendbackFee = (Integer)jdbcTemplate.queryForInt(isSendback,new String[] {msisdn,billdate});
			 if(allowSendbackFee > 0 ){
				 return true;
			 }else{
				 return false;
			 }
		}
		
		
	
		/**
		 * 退费程序主题流程,省市边漫基站退费
		 * @param request
		 * @return
		 * @throws Exception
		 */
		
		@RequestMapping(value = "/cntSendbackFee", method = RequestMethod.POST)
		protected @ResponseBody Map<String, ? extends Object> cntSendbackFee(HttpServletRequest request) throws Exception {
		 Map<String, Object> modelMap = new HashMap<String, Object>(3);
		 
		 final String msisdn = ServletRequestUtils.getStringParameter(request, "msisdn");
		 final String  billdate = ServletRequestUtils.getStringParameter(request, "billdate");
			 
			 
		  /**省市级统计查询退费金额计算*/
		 String cuntSenbackFeeStr = " select sum(gsm.lfee)  AMT from FEE_GSMDETAIL_TP gsm, FEE_CELL_STATIONS stat where " +
		 		" stat.CI_DEC = gsm.CELL_ID and gsm.MSISDN = ? and gsm.BILL_DATE = ?";
		/**统计通话记录数*/
		 String cntCallsStr = "select count(1) CALLS_CNT  from FEE_GSMDETAIL_TP gsm  where " +
		 		" gsm.MSISDN = ? and gsm.BILL_DATE = ?";
		 /**统计边漫通话记录数*/
		 String cntLimitCallStr = "SELECT count(gsm.lfee) LIMIT_CNT FROM fee_gsmdetail_tp gsm, fee_cell_stations stat WHERE" +
		 		" stat.ci_dec = gsm.cell_id AND gsm.msisdn = ? AND gsm.bill_date = ? and gsm.lfee > 0";
		 
		 String insertFEE_SENDBACK_HIS = "INSERT INTO FEE_SENDBACK_HIS ( MSISDN, BILL_DATE, OPT_USER, AMT, CALLS_CNT, LIMIT_CNT, IS_DOUBLE, IS_TREBLE, OPER_DATE, OPER_TIME, CMT )" +
		 								" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 

		 try {
			 
			 if(hasBillSendback(msisdn,billdate)){
				 modelMap.put("success", true);
				 modelMap.put("allowSendbackFee", false);
				 return modelMap;
			 }
			 final Map amtMap = jdbcTemplate.queryForMap(cuntSenbackFeeStr,new String[] {msisdn,billdate});
			 
			 final Map cntCallsStrMap = jdbcTemplate.queryForMap(cntCallsStr,new String[] {msisdn,billdate});
			 
			 final Map cntLimitCallMap = jdbcTemplate.queryForMap(cntLimitCallStr,new String[] {msisdn,billdate});
			 
			 
			 
			 jdbcTemplate.update(insertFEE_SENDBACK_HIS,new Object[] {msisdn,billdate,"OPT_USER",amtMap.get("AMT"),cntCallsStrMap.get("CALLS_CNT"),cntLimitCallMap.get("LIMIT_CNT"),"0","0",billdate,billdate,""});
			 modelMap.put("success", true);
			 modelMap.put("MSISDN", msisdn);
			 modelMap.put("BILL_DATE", billdate);
			 modelMap.put("allowSendbackFee", true);
			 
			 modelMap.putAll(amtMap);
			 modelMap.putAll(cntCallsStrMap);
			 modelMap.putAll(cntLimitCallMap);
			 
		 } catch (Exception ex) {
				modelMap.put("success", false);
				ex.printStackTrace();
		 }
		 
		 return modelMap;
	 }
	
		
		/**
		 * 退费程序主题流程
		 * @param request
		 * @return
		 * @throws Exception
		 */
		
		@RequestMapping(value = "/feeSendbackMain", method = RequestMethod.POST)
		protected @ResponseBody Map<String, ? extends Object> feeSendbackMain(HttpServletRequest request) throws Exception {
		 Map<String, Object> modelMap = new HashMap<String, Object>(3);
		 
		 final String msisdn = ServletRequestUtils.getStringParameter(request, "msisdn");
		 final String  billdate = ServletRequestUtils.getStringParameter(request, "billdate");
		 Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
		
		 try {
			 Map<String, Object> inParam = new HashMap<String, Object>();
			 if(hasBillSendback(msisdn,billdate)){
				 modelMap.put("success", true);
				 modelMap.put("allowSendbackFee", false);
				 return modelMap;
			 }
			 
			 modelMap.put("success", true);
			 modelMap.put("MSISDN", msisdn);
			 modelMap.put("BILL_DATE", billdate);
			 modelMap.put("allowSendbackFee", true);
			 
			 //手机号码
			 inParam.put("p_i_msisdn", msisdn);
			 //账单日期
			 inParam.put("p_i_bill_date", billdate);
			 //是否双倍
			 inParam.put("p_i_isdouble", params.get("isdouble"));
			 //产品编码
			 inParam.put("p_i_product", params.get("product"));
			 //V网编码
			 inParam.put("p_i_product_v", params.get("product_vid"));
			 //执行结果
			 //inParam.put("p_o_result", 0);
	
			 Map callProcMap = this.feeSendbackDao.callAllFeeSendback(msisdn, billdate, inParam);
			 //如果存储过程执行成功且判断该用户需要退费
			  //status 0: 代表初始状态     -1: 无记录不能退费        1: 已经成功退费  9999:出错
			 int status = (Integer) callProcMap.get("status");
			 //double atm = (Double) callProcMap.get("atm");
			 if(status == 0){
				 Map retMap = selectFeeSenbackHis(msisdn,billdate,"0");
				 if(retMap == null || retMap.size() == 0){
					 modelMap.put("allowSendbackFee", false);
					 modelMap.put("status", -1);
					 return modelMap;
				 }
				
				 modelMap.putAll(retMap);
			 }else{
				 modelMap.put("allowSendbackFee", false);
			 }
			 modelMap.putAll(callProcMap);
			


		 } catch (Exception ex) {
				modelMap.put("success", false);
				ex.printStackTrace();
				return modelMap;
		 }
		 
		 
		 return modelMap;
		
	 }
		/**
		 * 插入退费信息表
		 * @param msisdn
		 * @param billdate
		 * @return
		 */
		@RequestMapping(value = "/updateFeeSendbackStatus", method = RequestMethod.POST)
		protected @ResponseBody Map<String, ? extends Object> 
								updateFeeSendbackStatus(HttpServletRequest request){
			String currentUser = (String)SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> modelMap = new HashMap<String, Object>(3);
			try {
				 
				final String msisdn = ServletRequestUtils.getStringParameter(request, "msisdn");
				final String  billdate = ServletRequestUtils.getStringParameter(request, "billdate");
				final String  status = ServletRequestUtils.getStringParameter(request, "status");
				final String  cmt = ServletRequestUtils.getStringParameter(request, "cmt");
				//Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
		
				String updateFEE_SENDBACK_HIS = " update FEE_SENDBACK_HIS set OPT_USER = ? ,status = ?,CMT = ? where MSISDN = ? and BILL_DATE = ?"; 
				int ret = jdbcTemplate.update(updateFEE_SENDBACK_HIS,new Object[] {currentUser,status,cmt,msisdn,billdate});
				
				if(ret > 0){
					//将退费成功的语音清单迁移到历史表
					insertFeeGsmdetailHis(msisdn,billdate);
					//删除当前语音表中退费成功的语音清单数据
					deleteFeeGsmdetail(msisdn,billdate);
				}
				
				modelMap.put("success", true);
				
			} catch (Exception ex) {
				ex.printStackTrace();
				modelMap.put("success", false);
			}
			 
			return modelMap;
		}
		
		/**
		 * 对于退费成功的语音清单数据移到历史表中
		 * @param msisdn
		 * 			手机号码
		 * @param billdate
		 * 			账单日期
		 * @return
		 */
		public int insertFeeGsmdetailHis(String msisdn,String billdate){
			int cnt = 0;
			try{
				String insertSql = "INSERT INTO fee_gsmdetail_his SELECT msisdn, bill_date, call_type, start_date, start_time, " +
					"call_duration,other_party, origin_no, office_code, biz_type, roam_type, cfee,lfee, total_fee,tui_fee, cell_id, " +
					"vpn_id, source_type, status, ? FROM fee_gsmdetail_tp WHERE msisdn = ? AND bill_date = ? ";
		
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String fdate = sdf.format(date);
		
				cnt = jdbcTemplate.update(insertSql, new Object[]{fdate,msisdn,billdate});
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return cnt;
		}
		
		/**
		 * 在退费成功的语音清单数据成功移到历史表后
		 * 将当前语音信息表中已经退费成功的数据进行删除
		 * @param msisdn
		 * 			手机号
		 * @param billdate
		 * 			账单日期
		 */
		public int deleteFeeGsmdetail(String msisdn,String billdate){
			int cnt = 0;
			try{
				String deleteSql = "DELETE FROM fee_gsmdetail_tp WHERE msisdn = ? AND bill_date = ? ";
				cnt = jdbcTemplate.update(deleteSql, new Object[]{msisdn,billdate});
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return cnt;
		}
		
		/**
		 * 插入把退费信息插入到退费表中
		 * @param msisdn
		 * @param billdate
		 * @return
		 */
		public Map cntCallsNumber(String msisdn,String billdate){
			Map<String, Object> cntMap = new HashMap<String, Object>(3);
			
			 try {
				 
				 /**统计通话记录数*/
				 String cntCallsStr = "select count(1) CALLS_CNT  from FEE_GSMDETAIL_TP gsm  where " +
				 		" gsm.MSISDN = ? and gsm.BILL_DATE = ?";
				 /**统计边漫通话记录数*/
				 String cntLimitCallStr = "SELECT count(gsm.lfee) LIMIT_CNT FROM fee_gsmdetail_tp gsm, fee_cell_stations stat WHERE" +
				 		" stat.ci_dec = gsm.cell_id AND gsm.msisdn = ? AND gsm.bill_date = ? and gsm.lfee > 0";
				 final Map cntCallsStrMap = jdbcTemplate.queryForMap(cntCallsStr,new String[] {msisdn,billdate});
				 final Map cntLimitCallMap = jdbcTemplate.queryForMap(cntLimitCallStr,new String[] {msisdn,billdate});
				 
				 cntMap.putAll(cntCallsStrMap);
				 cntMap.putAll(cntLimitCallMap);
				 
				
			 } catch (Exception ex) {
				ex.printStackTrace();
			 }
			 
			 
			 return cntMap;
		}

	
		
		
		/**
		 * 插入退费信息表
		 * @param msisdn
		 * @param billdate
		 * @return
		 */
		public Map selectFeeSenbackHis(String msisdn,String billdate,String status){
			String currentUser = (String)SecurityUtils.getSubject().getPrincipal();
				Map retMap = new HashMap();
		
			 try {
				 String queryStr = " select AMT,CALLS_CNT,LIMIT_CNT FROM FEE_SENDBACK_HIS  where MSISDN = ? and BILL_DATE = ? and status = ? "; 
				 List retList = jdbcTemplate.queryForList(queryStr,new Object[] {msisdn,billdate,status});
				
				 if(retList != null && retList.size()>0){
					 retMap = (Map)retList.get(0);
				 }
				return retMap;
			 } catch (Exception ex) {
					ex.printStackTrace();
					return null;
			 }
			
		}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showGsmDetailList", method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object> showGsmDetailList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		
		try {
			
		 final String msisdn = ServletRequestUtils.getStringParameter(request, "msisdn");
		 final String  billdate = ServletRequestUtils.getStringParameter(request, "billdate");
		 Map<String, Object> params = WebUtils.getParametersStartingWith(request, ""); 
		 if(msisdn == null || billdate == null){
			 modelMap.put("success", false);
			 modelMap.put("totalCount", 0);
			 return modelMap;
		 }
		String queryFeeLimitStr = " SELECT  GSM.MSISDN , GSM.BILL_DATE , GSM.TUI_FEE,GSM.CALL_TYPE,   " +
				"	GSM.START_DATE ,  	GSM.START_TIME,  GSM.CALL_DURATION,  GSM.OTHER_PARTY, " +
				" GSM.ORIGIN_NO,  GSM.OFFICE_CODE ,  GSM.BIZ_TYPE ,  GSM.ROAM_TYPE,  GSM.CFEE, " +
				" GSM.LFEE,  GSM.TOTAL_FEE,  GSM.CALL_TYPE,  GSM.CELL_ID,  GSM.VPN_ID   " +
				" FROM FEE_GSMDETAIL_TP GSM, FEE_CELL_STATIONS STAT               " +
				" WHERE STAT.CI_DEC = GSM.CELL_ID AND  GSM.TUI_FEE <> 0 AND" +
				" GSM.MSISDN = ? AND GSM.BILL_DATE = ?  order by GSM.START_DATE,GSM.START_TIME asc";
		
			List<Map<String,Object>> records = jdbcTemplate.queryForList(queryFeeLimitStr, new String[] {msisdn,billdate});
			
			modelMap.put("success", true);
			modelMap.put("result", records);
			modelMap.put("totalCount", records.size());
		} catch (Exception ex) {
			modelMap.put("success", false);
			ex.printStackTrace();
		}
		return modelMap;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadGridProduct", method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object> loadGridProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		
		try {
			
			String prd_name = ServletRequestUtils.getStringParameter(request, "query").trim();
		 	Map<String, Object> params = WebUtils.getParametersStartingWith(request, ""); 
		 	String queryPrdStr = " SELECT  PRODUCT_ID,PRODUCT_NAME,PRODUCT_STANDARD,PRODUCT_DESC  FROM FEE_PRODUCT WHERE PRODUCT_NAME LIKE '%"+prd_name+"%' ";
			List<Map<String, Object>> records = jdbcTemplate.queryForList(queryPrdStr);

			modelMap.put("success", true);
			modelMap.put("result", records);
			modelMap.put("totalCount", records.size());
		} catch (Exception ex) {
			modelMap.put("success", false);
			ex.printStackTrace();
		}
		return modelMap;
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadGridVProduct", method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object> loadGridVProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		
		try {
			
		 String queryPrdStr = " SELECT V_ID VID,V_NAME VNAME FROM  FEE_PRODUCT_V ORDER BY V_SORT ASC ";

			List<Map<String, Object>> records = jdbcTemplate.queryForList(queryPrdStr);

			modelMap.put("success", true);
			modelMap.put("result", records);
			modelMap.put("totalCount", records.size());
		} catch (Exception ex) {
			modelMap.put("success", false);
			ex.printStackTrace();
		}
		return modelMap;
	}
	
	protected   List<Map<String, Object>>   queryVProduct() {
		List<Map<String, Object>> records = null;
		
		try {
			
		 String queryPrdStr = " SELECT V_ID VID,V_NAME VNAME FROM  FEE_PRODUCT_V ORDER BY V_SORT ASC ";
		 records = jdbcTemplate.queryForList(queryPrdStr);
		 
		} catch (Exception ex) {
 			ex.printStackTrace();
		}
		return records;
	}
	
	
	
}



