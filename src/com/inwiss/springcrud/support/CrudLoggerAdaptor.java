/*
 * Created on 2005-8-13
 */
package com.inwiss.springcrud.support;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.command.RecordSetCommand;
import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * 与应用特定的日志记录器相结合的适配器接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface CrudLoggerAdaptor
{
    /**
     * 记录日志。
     * @param request HttpServletRequest对象。
     * @param clazz 调用该方法的类，一般用于区分所做的动作。
     * @param crudMeta 元数据定义对象。
     * @param recordCommand 结果集Command对象。
     */
    public void log(HttpServletRequest request, Class clazz, CrudMeta crudMeta, RecordCommand recordCommand);
    
    /**
     * 记录日志
     * @param request HttpServletRequest对象。
     * @param clazz 调用该方法的类，一般用于区分所做的动作。
     * @param crudMeta 元数据定义对象。
     * @param recordSetCommand 结果集Command对象。
     */
    public void log(HttpServletRequest request, Class clazz, CrudMeta crudMeta, RecordSetCommand recordSetCommand);
}
