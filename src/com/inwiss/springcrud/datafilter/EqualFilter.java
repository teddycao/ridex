/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.datafilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理相等的DataFilter实现类。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class EqualFilter extends DataFilterSupport implements DataFilter
{
    /**是否为字符类型*/
    private boolean stringType;
    
    /**被嵌套的DataFilter*/
    private DataFilter chainedFilter;
    
    
    public EqualFilter(){}
    
    /**
     * 构造方法。
     * @param colName 字段名
     * @param colValue 字段值
     * @param stringType 是否字符类型
     */
    public EqualFilter(String colName, String colValue, boolean stringType)
    {
        this.setColName(colName);
        this.setColValue(colValue);
        this.stringType = stringType;
    }
    
    /**
     * 构造方法。
     * @param colName 字段名
     * @param colValue 字段值
     * @param stringType 是否字符类型
     * @param chainedFilter 嵌套的DataFilter
     */
    public EqualFilter(String colName, String colValue, boolean stringType, DataFilter chainedFilter)
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
                    this.getColName() + " = " + "'" + theColValue + "' and " + chainedFilter.getFitlerWhereClause(request)
                    :this.getColName() + " = " + "'" + theColValue + "'";
        else
            return ( chainedFilter != null )?
                    this.getColName() + " = " + theColValue + " and " + chainedFilter.getFitlerWhereClause(request)
                    :this.getColName() + " = " + theColValue;
        
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
    public void setStringType(boolean stringValue)
    {
        this.stringType = stringValue;
    }
}
