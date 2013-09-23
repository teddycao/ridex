/*
 * Created on 2005-11-27
 */
package com.inwiss.springcrud.datafilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理In逻辑的DataFilter。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class InFilter extends DataFilterSupport implements DataFilter
{
    
    /**被嵌套的DataFilter*/
    private DataFilter chainedFilter;

    /**
     * @see com.inwiss.springcrud.datafilter.DataFilter#getFitlerWhereClause(javax.servlet.http.HttpServletRequest)
     */
    public String getFitlerWhereClause(HttpServletRequest request)
    {
        String theColValue = this.getColValue();
        
        if ( this.getRequestContextColValueRetriever() != null )
            theColValue = this.getRequestContextColValueRetriever().getRequestContextString(request);
        
        return ( chainedFilter != null )?
                    this.getColName() + " in (" + theColValue + ") and " + chainedFilter.getFitlerWhereClause(request)
                    :this.getColName() + " in (" + theColValue + ")";
    }

    /**
     * @see com.inwiss.springcrud.datafilter.DataFilter#setChainedFilter(com.inwiss.springcrud.datafilter.DataFilter)
     */
    public DataFilter setChainedFilter(DataFilter chainedFilter)
    {
        this.chainedFilter = chainedFilter;
        return chainedFilter;
    }

}
