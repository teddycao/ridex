/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.datafilter;

import com.inwiss.springcrud.support.RequestContextStringRetriever;

/**
 * 为实现<code>DataFilter</code>的类提供的便利基类。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public abstract class DataFilterSupport
{
    /**字段名*/
    private String colName;
    
    /**字段值*/
    private String colValue;
    
    /**与Request相关的字段值获取接口*/
    private RequestContextStringRetriever requestContextColValueRetriever;
    
    
    /**
     * @param colName The colName to set.
     */
    public void setColName(String colName)
    {
        this.colName = colName;
    }
    
    /**
     * @param colValue The colValue to set.
     */
    public void setColValue(String colValue)
    {
        this.colValue = colValue;
    }
    
    
    /**
     * @return Returns the colName.
     */
    protected String getColName()
    {
        return colName;
    }
    
    /**
     * @return Returns the colValue.
     */
    protected String getColValue()
    {
        return colValue;
    }
    
    /**
     * @return Returns the requestContextColValueRetriever.
     */
    public RequestContextStringRetriever getRequestContextColValueRetriever()
    {
        return requestContextColValueRetriever;
    }
    
    /**
     * @param requestContextColValueRetriever The requestContextColValueRetriever to set.
     */
    public void setRequestContextColValueRetriever(
            RequestContextStringRetriever requestInfoRetriever)
    {
        this.requestContextColValueRetriever = requestInfoRetriever;
    }
}
