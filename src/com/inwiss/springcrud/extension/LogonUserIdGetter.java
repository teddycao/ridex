/*
 * Created on 2005-11-25
 */
package com.inwiss.springcrud.extension;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.support.*;
import com.inwiss.springcrud.support.ContextRelativeStringGenerator;

/**
 * 登录用户ID的获取器。
 * 
 * <p>注意：该类仅仅为了演示数据补录的功能，并不是OpenCrud的不可或缺的一部分。
 * 
 * @author  
 */
public class LogonUserIdGetter implements ContextRelativeStringGenerator
{
    /**
     * @see com.inwiss.springcrud.support.ContextRelativeStringGenerator#generateContextRelativeString(javax.servlet.http.HttpServletRequest, com.inwiss.springcrud.command.RecordCommand)
     */
    public String generateContextRelativeString(HttpServletRequest request, RecordCommand command)
    {
        return request.getParameter("emp_code");
    }
}
