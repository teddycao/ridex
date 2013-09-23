/*
 * Created on 2004-7-19
 */
package com.inwiss.springcrud.support.paging;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * PagedListOnDemandHolder是一个List的容器，提供针对List的分页服务。
 * 
 * <p>通常情况下的分页要求获取整个结果List，但在某些情况下是不合适的，特别是针对大数据量的查询结果。
 * PagedListOnDemandHolder可以根据相关参数，动态获取查询的一部分结果，从而达到On Demand Paging的效果。
 * 
 * <p>PagedListOnDemandHolder通过OnDemandPagingSourceProvider接口获取所需的部分结果。
 * 
 * <p>该类的设计参考了Spring Framework中的org.springframework.beans.support.PagedListHolder类
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 * 
 * @see com.inwiss.springcrud.support.paging.OnDemandPagingSourceProvider
 */
public class PagedListOnDemandHolder implements Serializable
{
	private static final Log log = LogFactory.getLog(PagedListOnDemandHolder.class);


	public static final int DEFAULT_PAGE_SIZE = 20;

	private OnDemandPagingSourceProvider sourceProvider;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private int page = 0;

	private int nrOfElements;
	
	private boolean pageSizeHasBeenSet = false;
	
	private int pageToBeSet;

	public PagedListOnDemandHolder()
	{

	}

	public PagedListOnDemandHolder(OnDemandPagingSourceProvider theSourceProvider)
	{
		this.sourceProvider = theSourceProvider;
		this.nrOfElements = sourceProvider.getNrOfElements();
	}

	/**
	 * 返回元素的总个数
	 */
	public int getNrOfElements()
	{
		return this.nrOfElements;
	}
	
	public int getCurrentPageNrOfElements()
	{
	    return this.getLastElementOnPage() - this.getFirstElementOnPage() + 1;
	}

	/**
	 * 返回按照所设置的页码大小(pageSize)所得的页面总数
	 */
	public int getNrOfPages()
	{
		if (this.nrOfElements <= this.pageSize)
		{
			return 1;
		}
		else if (this.nrOfElements % this.pageSize == 0)
		{
			return this.nrOfElements / this.pageSize;
		}
		else
		{
			return this.nrOfElements / this.pageSize + 1;
		}
	}

	/**
	 * 设置当前页的页码
	 * 页码从0开始计数
	 */
	public void setPage(int page)
	{
		//在一个PagedListOnDemandHolder实例内部，必须保证setPageSize-->setPage的调用顺序
		if ( !pageSizeHasBeenSet ){
			pageToBeSet = page;
			return;
		}
		
		if (page >= this.getNrOfPages()){
			this.page = this.getNrOfPages() - 1;
		}else if(page<0)
			page=0;
		else{
			this.page = page;
		}
		
	}

	/**
	 * 返回当前页的页码
	 * 页码从0开始计数
	 */
	public int getPage()
	{
		return page;
	}

	/**
	 * 返回当前页是否为首页的布尔值
	 */
	public boolean isFirstPage()
	{
		return this.page == 0;
	}

	/**
	 * 返回当前页是否为尾页的布尔值
	 */
	public boolean isLastPage()
	{
		return this.page == getNrOfPages() - 1;
	}

	/**
	 * 转至前一页
	 */
	public void previousPage()
	{
		if (!isFirstPage())
		{
			this.page--;
		}
	}

	/**
	 * 转至后一页
	 */
	public void nextPage()
	{
		if (!isLastPage())
		{
			this.page++;
		}
	}

	/**
	 * 返回当前页的第一个元素的位移值
	 */
	public int getFirstElementOnPage()
	{
		return (this.pageSize * this.page);
	}

	/**
	 * 返回当前页的最后一个元素的位移值
	 */
	public int getLastElementOnPage()
	{
		int endIndex = this.pageSize * (this.page + 1);
		return (endIndex > this.nrOfElements ? this.nrOfElements : endIndex) - 1;
	}
	
	/**
	 * 返回当前页包括的元素组成的列表
	 */
	public List getPageList()
	{
		int nCurrentPageSize = this.getLastElementOnPage() - this.getFirstElementOnPage() + 1;
		return this.sourceProvider
			.getItemListWithOffset(this.getFirstElementOnPage(),nCurrentPageSize);
	}

	
	public List getPageList(int nOffset, int nSize)
	{
		return this.sourceProvider.getItemListWithOffset(nOffset,nSize);
	}
	
	public Map getOneRecord(){
		return this.sourceProvider.getOneRecord();
	}
	
	/**
	 * 返回一个页面可以包含的最多元素个数
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 *	设置一个页面可以包含的最多元素个数
	 */
	public void setPageSize(int i)
	{
		pageSize = i;
		pageSizeHasBeenSet = true;
		this.setPage(pageToBeSet);
	}

	/**
	 * 设置数据提供者对象
	 */
	public void setSourceProvider(OnDemandPagingSourceProvider provider)
	{
		sourceProvider = provider;
		this.nrOfElements = sourceProvider.getNrOfElements();
		this.setPage(pageToBeSet);
	}

}
