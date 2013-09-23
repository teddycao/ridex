/*
 * Created on 2005-8-22
 */
package com.inwiss.springcrud.dataaccess;

import java.util.Map;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * CRUD持续化策略接口。
 * 
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */

public interface CrudPersistentStrategy
{	
	/**
	 * 构造执行插入操作的SQL语句。
	 * @param recordCommand 要插入数据的Command对象
	 * @param crudMeta CRDU元数据定义对象
	 * @return SQL语句
	 */
	public String[] buildInsertSQLString(RecordCommand recordCommand,CrudMeta crudMeta);
	
	/**
	 * 构造更新操作的SQL语句。
	 * @param recordCommand 要更新数据的Command对象
	 * @param crudMeta CRDU元数据定义对象
	 * @return SQL语句 
	 */	
	public String[] buildUpdateSQLString(RecordCommand recordCommand,CrudMeta crudMeta);
	
	/**
	 * 构造删除操作的SQL语句。
	 * @param recordCommand 要删除数据的Command对象
	 * @param crudMeta CRDU元数据定义对象
	 * @return SQL语句 
	 */	
	public String[] buildDeleteSQLString(RecordCommand recordCommand,CrudMeta crudMeta);
	
	/**
	 * 构造获取数据列表操作的SQL语句。
	 * @param crudMeta CRDU元数据定义对象
	 * @param mapParam 获取列表数据使用的参数集合
	 * @param defaultFilterWhereClause 缺省过滤器对应的Where子句
	 * @return SQL语句 
	 */	
	public String buildGetDataListSQLString(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause);
	
	/**
	 * 构造获取数据记录条数操作的SQL语句。
	 * @param crudMeta CRDU元数据定义对象
	 * @param mapParam 获取列表数据使用的参数集合
	 * @param defaultFilterWhereClause 缺省过滤器对应的Where子句
	 * @return SQL语句 
	 */	
	public String buildGetDataCountSQLString(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause);
}
