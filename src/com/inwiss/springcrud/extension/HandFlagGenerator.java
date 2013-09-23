/*
 * Created on 2005-11-25
 */
package com.inwiss.springcrud.extension;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.support.handcraft.*;
import com.inwiss.springcrud.support.ContextRelativeStringGenerator;
import com.inwiss.springcrud.support.handcraft.HandCraftStatusEnum;

/**
 * 生成手工补录数据的HandFlag字典的实现。
 * 
 * <p>如果从前端传入的参数"submitType"值为"save"，则表示用户需要保存补录数据，返回保存态对应字符串；
 * 否则返回提交态对应字符串。
 * 
 * <p>注意：该类仅仅为了演示数据补录的功能，并不是OpenCrud的不可或缺的一部分。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class HandFlagGenerator implements ContextRelativeStringGenerator
{

    /**
     * @see com.inwiss.springcrud.support.ContextRelativeStringGenerator#generateContextRelativeString(javax.servlet.http.HttpServletRequest, com.inwiss.springcrud.command.RecordCommand)
     */
    public String generateContextRelativeString(HttpServletRequest request,
            RecordCommand command)
    {
        if ( "save".equals(request.getParameter("submitType")) )
            return HandCraftStatusEnum.SAVE_STATE;
        else
            return HandCraftStatusEnum.SUBMIT_STATE;
    }
}
