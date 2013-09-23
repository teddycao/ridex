/*
 * Created on 2005-10-12
 */
package com.inwiss.springcrud.support.txtimport;

import org.springframework.core.NestedRuntimeException;

/**
 * 上传文件解析异常。
 * 
 * @author 
 */
public class UploadFileResolveException extends NestedRuntimeException
{

    /**
     * 构造仅包含详细信息的上传文件解析异常。
     * @param message 异常详细信息
     */
    public UploadFileResolveException(String message)
    {
        super(message);
    }

    /**
     * 构造包含详细信息和嵌套异常的上传文件解析异常。
     * @param message 异常详细信息
     * @param rootCause 嵌套异常
     */
    public UploadFileResolveException(String message, Throwable rootCause)
    {
        super(message, rootCause);
    }

}
