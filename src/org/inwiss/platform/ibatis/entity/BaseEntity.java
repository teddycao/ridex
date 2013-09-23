package org.inwiss.platform.ibatis.entity;

import org.inwiss.platform.persistence.pagination.PageRequest;


/**
 * @author Raidery
 */
public class BaseEntity  extends PageRequest implements java.io.Serializable {

	private static final long serialVersionUID = -7200095849148417467L;

	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	
	protected static final String TIME_FORMAT = "HH:mm:ss";
	
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	
	public BaseEntity(int pageNumber, int pageSize){
		//super(pageNumber,pageSize);
	}

	public BaseEntity(){
		
	}
}
