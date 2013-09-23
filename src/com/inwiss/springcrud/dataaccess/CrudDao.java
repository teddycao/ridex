/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.dataaccess;

import java.util.List;

/**
 * CRUD框架的DAO接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface CrudDao
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
     */
    public void doUpdate(String sql);
    
    
}
