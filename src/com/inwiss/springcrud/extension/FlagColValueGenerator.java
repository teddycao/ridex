/*
 * Created on 2005-11-30
 */
package com.inwiss.springcrud.extension;

import javax.servlet.http.HttpServletRequest;

import com.inwiss.springcrud.support.RequestContextStringRetriever;

/**
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class FlagColValueGenerator implements RequestContextStringRetriever
{

    /**
     * @see com.inwiss.springcrud.support.RequestContextStringRetriever#getRequestContextString(javax.servlet.http.HttpServletRequest)
     */
    public String getRequestContextString(HttpServletRequest request)
    {
        return request.getParameter("updateBatchToken");
    }

}
