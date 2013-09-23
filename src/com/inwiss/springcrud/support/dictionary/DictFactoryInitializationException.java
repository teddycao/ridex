/*
 * Created on 2005-10-14
 */
package com.inwiss.springcrud.support.dictionary;

import org.springframework.core.NestedRuntimeException;

/**
 * 字典工厂初始化异常。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DictFactoryInitializationException extends NestedRuntimeException
{
    
    /**
     * 构造仅包含详细信息的上传文件解析异常。
     * @param message 异常详细信息
     */
    public DictFactoryInitializationException(String message)
    {
        super(message);
    }
    /**
     * 构造包含详细信息和嵌套异常的上传文件解析异常。
     * @param message 异常详细信息
     * @param rootCause 嵌套异常
     */
    public DictFactoryInitializationException(String message, Throwable rootCause)
    {
        super(message, rootCause);
    }
}
