/*
 * Created on 2005-8-10
 */
package com.inwiss.springcrud.support;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Web请求上下文相关的一个布尔值的接口。
 * 
 * <p>比如，当前用户是否已经经过登录认证、当前用户是否有编辑整页数据的权限等等。
 * 
 * <p>该接口和<code>RequestContextStringRetriever</code>一道，为CRUD提供灵活的插入机制。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface RequestContextBooleanRetriever
{
    /**
     * 根据<code>HttpServletRequest</code>获取Web请求上下文相关的布尔值。
     * @param request 请求对象
     * @return 所需的布尔值
     */
    public boolean getRequestContextBoolean(HttpServletRequest request);
}
