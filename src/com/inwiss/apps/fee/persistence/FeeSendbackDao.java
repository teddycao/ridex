/**
 * 2011-9-17
 */
package com.inwiss.apps.fee.persistence;

import java.util.Map;

import org.inwiss.platform.persistence.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.inwiss.apps.fee.model.FixupFee;



/**
 * 
 * @author Raidery
 *
 */
public interface FeeSendbackDao  {
	
	
	 
	 public Map callAllFeeSendback(final String msisdn,final String  billdate,Map<String,Object> params ) throws Exception;
	
	
}
