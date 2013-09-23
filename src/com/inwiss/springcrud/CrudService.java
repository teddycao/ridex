/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud;

import java.util.List;
import java.util.Map;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.command.RecordSetCommand;
import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * CRUD业务逻辑接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface CrudService
{   
	/**
	 * 获取某个Crud定义在条件作用下的记录数量。
	 * @param crudMeta CRUD元数据
	 * @param mapParam 用户提交参数
	 * @param defaultFilterWhereClause 作为缺省过滤条件的Where条件子句
	 * @return 记录数量
	 */
    public int getNrOfElements(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause);
    
    /**
     * 获取某个Crud定义在条件作用下的记录列表。
     * @param crudMeta CRUD元数据
     * @param mapParam 用户提交参数
     * @param defaultFilterWhereClause 作为缺省过滤条件的Where条件子句
     * @return 记录列表，其中的每一个元素都是一个Map结构
     */
    public List getItemList(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause);
    
    /**
     * 获取某个Crud定义在条件作用下的记录列表。
     * @param crudMeta CRUD元数据
     * @param mapParam 用户提交参数
     * @param nOffset 获取记录列表的偏移量
     * @param nSize 获取记录列表的长度
     * @param defaultFilterWhereClause 作为缺省过滤条件的Where条件子句
     * @return 记录列表，其中的每一个元素都是一个Map结构
     */
    public List getItemListWithOffset(CrudMeta crudMeta, Map mapParam, int nOffset, int nSize, String defaultFilterWhereClause);
    
    /**
     * 插入一条记录。
     * @param recordCommand 保存记录信息的Command对象
     * @param crudMeta CRUD元数据
     */
    public void insertRecord(RecordCommand recordCommand,CrudMeta crudMeta);
    
    /**
     * 插入一批记录。
     * @param recordSet 保存记录集合的Command对象
     * @param crudMeta CRUD元数据
     */
    public void insertRecordSet(RecordSetCommand recordSet,CrudMeta crudMeta);
    
    /**
     * 更新一条记录。
     * @param recordCommand 保存记录信息的Command对象
     * @param crudMeta CRUD元数据
     */
    public void updateRecord(RecordCommand recordCommand,CrudMeta crudMeta);
    
    /**
     * 更新一批记录。
     * @param recordSet 保存记录集合的Command对象
     * @param crudMeta CRUD元数据
     */
    public void updateRecordSet(RecordSetCommand recordSet,CrudMeta crudMeta);
    
    /**
     * 删除一条记录。
     * @param recordCommand 保存记录信息的Command对象
     * @param crudMeta CRUD元数据
     */
    public void deleteRecord(RecordCommand recordCommand,CrudMeta crudMeta);
    
    /**
     * 删除一批记录。
     * @param recordSet 保存记录集合的Command对象
     * @param crudMeta CRUD元数据
     */
    public void deleteRecordSet(RecordSetCommand recordSet,CrudMeta crudMeta);
}
