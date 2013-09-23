/**
 * 2011-9-17
 */
package com.inwiss.apps.fee.persistence.impl;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.inwiss.platform.ibatis.dao.BaseIbatis3Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inwiss.apps.fee.persistence.FeeSendbackDao;



/**
 * 
 * @author Raidery
 *
 */
@Service(value = "feeSendbackDao")
public class FeeSendbackDaoImpl extends BaseIbatis3Dao implements FeeSendbackDao {
	
	Logger logger = LoggerFactory.getLogger(FeeSendbackDaoImpl.class);
	 /**
	  * 调用存储过程
	  */
	 public Map callAllFeeSendback(final String msisdn,final String  billdate,Map<String,Object> params ) throws Exception{
		 Map<String, Object> callProcResultMap = new HashMap<String,Object>();
		 try {
		 Object result = getSqlSession().selectOne("com.inwiss.apps.fee.callAllFeeSendback",params);
	
		 ObjectMapper mapper = new ObjectMapper();
		 String resultStr = (String)params.get("p_o_result");
		 logger.debug("P_O_RESULT:"+resultStr);
		 
		  callProcResultMap = mapper.readValue(resultStr, Map.class);
		 } catch (Exception ex) {
				ex.printStackTrace();
				
			}
		 return callProcResultMap;
	 }
	 
	
	 public Map selectFeeSenbackHis(final String msisdn,final String  billdate,String status ) throws Exception{
			
		 Map<String, Object> param = new HashMap<String, Object>();
		// Object result = getSqlSession().selectMap(statement, mapKey)selectOne("com.inwiss.apps.fee.callAllFeeSendback",params);

		 return param;
	 }

	
}
