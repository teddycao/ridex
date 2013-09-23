/*
 * Created on 2005-8-9
 */
package com.inwiss.springcrud.support;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Web请求上下文相关的一个字符串的接口。
 * 
 * <p>比如，当前登录用户的ID、所属角色、系统日期等等。
 * 
 * <p>该接口和<code>RequestContextBooleanRetriever</code>一道，为CRUD提供灵活的插入机制。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface RequestContextStringRetriever
{
    /**
     * 根据<code>HttpServletRequest</code>获取Web请求上下文相关的字符串值。
     * @param request 请求对象
     * @return 所需的字符串
     */
    public String getRequestContextString(HttpServletRequest request);
    
}
