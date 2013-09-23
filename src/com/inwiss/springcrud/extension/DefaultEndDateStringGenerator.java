/*
 * Created on 2005-9-28
 */
package com.inwiss.springcrud.extension;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.support.RequestContextStringRetriever;

/**
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DefaultEndDateStringGenerator implements RequestContextStringRetriever
{

    /**
     * @see com.inwiss.springcrud.support.RequestContextStringRetriever#getRequestContextString(javax.servlet.http.HttpServletRequest)
     */
    public String getRequestContextString(HttpServletRequest request)
    {
        return "99991231";
    }

}
