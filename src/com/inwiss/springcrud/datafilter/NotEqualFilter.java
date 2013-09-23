/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.datafilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理不相等的DataFilter实现类。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class NotEqualFilter extends DataFilterSupport implements DataFilter
{
    private boolean stringType;
    
    private DataFilter chainedFilter;
    
    public NotEqualFilter(){}
    
    public NotEqualFilter(String colName, String colValue, boolean stringType)
    {
        this.setColName(colName);
        this.setColValue(colValue);
        this.stringType = stringType;
    }
    
    public NotEqualFilter(String colName, String colValue, boolean stringType, DataFilter chainedFilter)
    {
        this.setColName(colName);
        this.setColValue(colValue);
        this.stringType = stringType;
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
        
        if ( stringType )
            return ( chainedFilter != null )?
                    this.getColName() + " <> " + "'%" + theColValue + "%' and " + chainedFilter.getFitlerWhereClause(request)
                    :this.getColName() + " <> " + "'%" + theColValue + "%'";
        else
            return ( chainedFilter != null )?
                    this.getColName() + " <> " + theColValue + " and " + chainedFilter.getFitlerWhereClause(request)
                    :this.getColName() + " <> " + theColValue;
    }
    
    /**
     * @param chainedFilter The chainedFilter to set.
     */
    public DataFilter setChainedFilter(DataFilter chainedFilter)
    {
        this.chainedFilter = chainedFilter;
        return chainedFilter;
    }
    
    /**
     * @param stringType The stringType to set.
     */
    public void setStringType(boolean stringType)
    {
        this.stringType = stringType;
    }
}