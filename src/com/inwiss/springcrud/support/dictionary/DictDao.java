/*
 * Created on 2004-8-15
 */
package com.inwiss.springcrud.support.dictionary;

import java.util.List;
import java.util.Map;

/**
 * 存取字典信息的DAO
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 * @see com.inwiss.springcrud.support.dictionary.DictMetaData
 */
public interface DictDao 
{
	/**获取字典元数据信息，返回的结果是<code>DictMetaData</code>类对象的集合*/
	public List getDictMetaData();
	
	/**
	 * 直接使用sql语句获取字典数据。
	 * @param sql 指定的sql语句，结果即至少包括两个字段，第一个被认为是code，第二个被认为是value
	 * @return 字典值Map
	 */
	public Map getAllDictItemsWithSql(String sql);
	
	/**从独占的一张表中获取所有的字典项*/
	public Map getAllDictItems(String targetTableName, String codeColName, String valueColName);
	
	/**从和其他字典共享的一张表中获取所需字典的所有字典项*/
	public Map getAllDictItems(String targetTableName, String identifyColName, String identifyColValue, 
				String codeColName, String valueColName);
					
}
