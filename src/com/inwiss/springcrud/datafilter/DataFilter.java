/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.datafilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 支持CRUD操作中可能涉及的数据缺省过滤规则的接口。
 * 
 * <p>在CRUD操作中，我们可能仅仅关注于某张表的部分记录。在这种情况下我们可以使用<code>DataFilter</code>来实现这一目标。
 * 
 * <p>实际的例子可能包括：
 * <li>仅仅需要关注表中“是否生效”字段为“T”的记录；
 * <li>仅仅需要关注当月的数据；
 * <li>当前登录用户仅仅关注其所属机构的数据；
 * <li>上述三个例子的任意并集。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface DataFilter
{
    /**
     * 返回作为DataFilter作用结果的Where条件子句
     * @return Where条件子句
     */
    public String getFitlerWhereClause(HttpServletRequest request);
    
    /**
     * 为Filter设置嵌套过滤器
     * @param chainedFilter 被嵌套的DataFilter
     * @return 嵌套作用后的DataFilter
     */
    public DataFilter setChainedFilter(DataFilter chainedFilter);
}
