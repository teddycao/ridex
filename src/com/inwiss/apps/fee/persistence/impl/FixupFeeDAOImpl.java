/**
 * 2011-9-17
 */
package com.inwiss.apps.fee.persistence.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.inwiss.platform.ibatis.dao.BaseIbatis3Dao;
import org.inwiss.platform.persistence.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inwiss.apps.fee.model.FixupFee;
import com.inwiss.apps.fee.persistence.FixupFeeDAO;

/**
 * @author lvhq
 *
 */

public class FixupFeeDAOImpl extends BaseIbatis3Dao<FixupFee,java.lang.String> implements FixupFeeDAO{
	
	
	private SqlSessionFactory sqlSessionFactory = null;
	
	
	public Page getFixupList(FixupFee fixupFee){
		return pageQuery("FixupFee.getFixupList",fixupFee);
	}
	
	
}
