/*
 * Created on 2005-11-25
 */
package com.inwiss.springcrud.extension;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.support.ContextRelativeStringGenerator;

/**
 * <p>注意：该类仅仅为了演示数据补录的功能，并不是OpenCrud的不可或缺的一部分。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class LogonUserBranchNoGetter implements ContextRelativeStringGenerator
{

    /**
     * @see com.inwiss.springcrud.support.ContextRelativeStringGenerator#generateContextRelativeString(javax.servlet.http.HttpServletRequest, com.inwiss.springcrud.command.RecordCommand)
     */
    public String generateContextRelativeString(HttpServletRequest request,
            RecordCommand command)
    {
        return "0000";
    }

}
