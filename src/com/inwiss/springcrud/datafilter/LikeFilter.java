/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.datafilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理Like的DataFilter实现类
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class LikeFilter extends DataFilterSupport implements DataFilter
{
    private DataFilter chainedFilter;
    
    public LikeFilter(){}
    
    public LikeFilter(String colName, String colValue)
    {
        this.setColName(colName);
        this.setColValue(colValue);
    }
    
    public LikeFilter(String colName, String colValue, DataFilter chainedFilter)
    {
        this.setColName(colName);
        this.setColValue(colValue);
        this.chainedFilter = chainedFilter;
    }
    	
    /**
     * @see com.inwiss.springcrud.datafilter.DataFilter#getFitlerWhereClause(javax.servlet.http.HttpServletRequest)
     */
    public String getFitlerWhereClause(HttpServletRequest request)
    {
        String theColValue = this.getColValue();
        if ( this.getRequestContextColValueRetriever() != null )
            theColValue = this.getRequestContextColValueRetriever().getRequestContextString(request);
        
        return ( chainedFilter != null )?
                this.getColName() + " like " + "'%" + theColValue + "%' and " + chainedFilter.getFitlerWhereClause(request)
                :this.getColName() + " like " + "'%" + theColValue + "%'";
    }

    
    /**
     * @param chainedFilter The chainedFilter to set.
     */
    public DataFilter setChainedFilter(DataFilter chainedFilter)
    {
        this.chainedFilter = chainedFilter;
        return chainedFilter;
    }
    
}
