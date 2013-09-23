/*
 * Created on 2005-11-25
 */
package com.inwiss.springcrud.extension;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.support.RequestContextStringRetriever;

/**
 * 从request请求中获取一个参数值的实现。
 * 
 * <p>注意：该类仅仅为了演示数据补录的功能，并不是OpenCrud的不可或缺的一部分。
 * 
 * @author  
 */
public class ParameterGetter implements RequestContextStringRetriever
{
    private String paramName;
    
    /**
     * @see com.inwiss.springcrud.support.RequestContextStringRetriever#getRequestContextString(javax.servlet.http.HttpServletRequest)
     */
    public String getRequestContextString(HttpServletRequest request)
    {
        return request.getParameter(paramName);
    }

    
    /**
     * @param paramName The paramName to set.
     */
    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }
}
