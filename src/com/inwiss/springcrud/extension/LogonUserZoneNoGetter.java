/*
 * Created on 2005-11-25
 */
package com.inwiss.springcrud.extension;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.support.ContextRelativeStringGenerator;

/**
 * 
 * @author 
 */
public class LogonUserZoneNoGetter implements ContextRelativeStringGenerator
{

    /**
     * @see com.inwiss.springcrud.support.ContextRelativeStringGenerator#generateContextRelativeString(javax.servlet.http.HttpServletRequest, com.inwiss.springcrud.command.RecordCommand)
     */
    public String generateContextRelativeString(HttpServletRequest request,
            RecordCommand command)
    {
        return "0100";
    }

}
