/*
 * Created on 2005-11-30
 */
package com.inwiss.springcrud.dataaccess;

import java.util.List;

/**
 * 通用的用于直接接收Sql串的DAO接口。
 * 
 * <p>提供下列功能：
 * <li>获取一个结果集的大小；
 * <li>获取一个结果集的对象列表。
 * 
 * <p>该接口的好处是可以借助于SqlMap，实现从关系型数据到对象数据的直接映射。
 * 但其缺点也很明显，即数据库存取逻辑完全依赖客户端拼接的Sql，这种方式较容易出错。
 * 所以一般情况下，<b>建议编写针对特定存取逻辑的DAO</b>，而不是使用这个接口完成数据库存取逻辑。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface GeneralSqlDao
{
    /**
     * 获取数据列表。
     * @param sql 执行的sql串
     * @return 根据sql取出的数据列表，其中每个元素都是一个Map结构，Map中包含了N个对偶，表示N个列的数据。
     */
    public List getDataList(String sql);
    
    /**
     * 获取数据列表。
     * @param sql 执行的sql串
     * @param nOffset 从结果集中取数的偏移值
     * @param nSize 从结果集中要取出数据的数量
     * @return 根据sql、偏移量和数量取出的数据列表，其中每个元素都是一个Map结构，Map中包含了N个对偶，表示N个列的数据。
     */
    public List getDataList(String sql, int nOffset, int nSize);
    
    /**
     * 获取数据列表的大小。
     * @param sql 执行的sql串
     * @return 结果集的大小
     */
    public int getDataCount(String sql);
    
    /**
     * 执行一条完成更新操作的sql。
     * @param sql 执行的sql串
     * @return 更新操作影响的记录数
     */
    public int doUpdate(String sql);
}
