package org.inwiss.platform.persistence.support;

import org.inwiss.platform.persistence.pagination.PageRequest;

public class PageQuery extends PageRequest {

	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	
	protected static final String TIME_FORMAT = "HH:mm:ss";
	
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
		
	public static final int DEFAULT_PAGE_SIZE = 10;
	
    static {
        System.out.println("PageViewObject.DEFAULT_PAGE_SIZE="+DEFAULT_PAGE_SIZE);
    }
    
	public PageQuery() {
		setPageSize(DEFAULT_PAGE_SIZE);
	}
}
