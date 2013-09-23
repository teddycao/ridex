/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.datafilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理小于的DataFilter实现类。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class LessThanFilter extends DataFilterSupport implements DataFilter
{
    private boolean stringType;
    
    private DataFilter chainedFilter;
    
    public LessThanFilter(){}
    
    public LessThanFilter(String colName, String colValue, boolean stringType)
    {
        this.setColName(colName);
        this.setColValue(colValue);
        this.stringType = stringType;
    }
    
    public LessThanFilter(String colName, String colValue, boolean stringType, DataFilter chainedFilter)
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
                    this.getColName() + " < " + "'%" + theColValue + "%' and " + chainedFilter.getFitlerWhereClause(request)
                    :this.getColName() + " < " + "'%" + theColValue + "%'";
        else
            return ( chainedFilter != null )?
                    this.getColName() + " < " + theColValue + " and " + chainedFilter.getFitlerWhereClause(request)
                    :this.getColName() + " < " + theColValue;
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
    protected void setStringType(boolean stringType)
    {
        this.stringType = stringType;
    }
}
