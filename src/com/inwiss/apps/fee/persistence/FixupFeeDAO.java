/**
 * 2011-9-17
 */
package com.inwiss.apps.fee.persistence;

import org.inwiss.platform.persistence.pagination.Page;

import com.inwiss.apps.fee.model.FixupFee;



/**
 * @author lvhq
 *
 */
//@Repository
//@Transactional
public interface FixupFeeDAO{
	
	/** 获取固定费用列表 */
	public Page getFixupList(FixupFee fixupFee);
}
