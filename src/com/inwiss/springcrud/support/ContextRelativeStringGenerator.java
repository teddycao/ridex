/*
 * Created on 2005-8-10
 */
package com.inwiss.springcrud.support;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.command.RecordCommand;

/**
 * 上下文相关值生成器。
 * 
 * <p>例子包括：
 * 
 * <li>根据用户使用的提交按钮、在线用户的角色和表单值进行提交值的改写，比如使用的是“保存”按钮，
 * 同时用户是“录入员”角色，而当前表单数据的状态是“初始态”，则将状态改为“保存态”，以便持续化。
 * 
 * <li>To be supplied
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface ContextRelativeStringGenerator
{
    /**
     * 生成上下文相关字符串。
     * @param request HTTP请求对象，从中可以获取上下文相关的信息
     * @param command 表示一条记录的Command对象
     * @return 生成的上下文相关字符串
     */
    public String generateContextRelativeString(HttpServletRequest request, RecordCommand command);
}
