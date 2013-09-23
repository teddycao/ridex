/*
 * Created on 2005-8-13
 */
package com.inwiss.springcrud.extension;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.support.RequestContextStringRetriever;

/**
 * 作为测试目的的产生八位系统日期的<code>RequestContextStringRetriever</code>。
 * 
 * <p>注意：该类仅仅为了演示数据补录的功能，并不是OpenCrud的不可或缺的一部分。
 * 
 * @author  
 */
public class SystemDateStringGenerator implements RequestContextStringRetriever
{

    /**
     * @see com.inwiss.springcrud.support.RequestContextStringRetriever#getRequestContextString(javax.servlet.http.HttpServletRequest)
     */
    public String getRequestContextString(HttpServletRequest request)
    {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

}
