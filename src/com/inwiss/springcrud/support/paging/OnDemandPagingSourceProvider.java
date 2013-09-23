/*
 * Created on 2004-7-19
 */
package com.inwiss.springcrud.support.paging;

import java.util.List;
import java.util.Map;

/**
 * OnDemandPagingSourceProvider是一个数据提供者的抽象，提供按照分页方式获取数据元素的方法
 * 
 * <p>OnDemandPagingSourceProvider通常和PagedListOnDemandHolder一起，提供按需获取数据结果的分页支持
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 * 
 * @see com.inwiss.springcrud.support.paging.PagedListOnDemandHolder
 */
public interface OnDemandPagingSourceProvider
{
	/**
	 * 作为数据提供者可以提供的元素的总数
	 */
	public int getNrOfElements();
	
	/**
	 * @param nOffset 数据元素集合的偏移量
	 * @param nSize 需要获取的结果数量
	 * @return 根据偏移量和数量获取的List
	 */
	public List getItemListWithOffset(int nOffset, int nSize);
	
	public Map getOneRecord();

}
