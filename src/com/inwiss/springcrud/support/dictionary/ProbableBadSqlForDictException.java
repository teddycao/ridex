/*
 * Created on 2005-11-30
 */
package com.inwiss.springcrud.support.dictionary;

import org.springframework.core.NestedRuntimeException;

/**
 * 在字段数据获取器(DictDataRetriever)中，需要直接使用Sql来获取字典数据值。
 * 当传入的sql执行出错时，抛出该异常。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class ProbableBadSqlForDictException extends NestedRuntimeException
{

    /**
     * 构造仅包含详细信息的上传文件解析异常。
     * @param message 异常详细信息
     */
    public ProbableBadSqlForDictException(String message)
    {
        super(message);
    }
    
    /**
     * 构造包含详细信息和嵌套异常的上传文件解析异常。
     * @param message 异常详细信息
     * @param rootCause 嵌套异常
     */
    public ProbableBadSqlForDictException(String message, Throwable rootCause)
    {
        super(message, rootCause);
    }
}
