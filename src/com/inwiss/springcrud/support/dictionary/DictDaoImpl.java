/*
 * Created on 2004-8-15
 */
package com.inwiss.springcrud.support.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;


/**
 * 获取字典信息的Dao的缺省实现。
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 * @see com.inwiss.springcrud.support.dictionary.DictMetaData
 */
public class DictDaoImpl extends SqlSessionDaoSupport implements DictDao 
{
	
    /**
     * 获取所有的字典的元数据信息，即字典的定义信息
     * @return 所有字典的元数据信息集合,集合中的每个元素都是一个<code>com.inwiss.springcrud.support.dictionary.DictMetaData</code>对象
     */
    public List getDictMetaData() 
    {
    	return this.getSqlSession().selectList("getAllDictMetaData");
    }
    
    /**
     * @see com.inwiss.springcrud.support.dictionary.DictDao#getAllDictItemsWithSql(java.lang.String)
     */
    public Map getAllDictItemsWithSql(String sql)
    {
        List listResult = this.getSqlSession().selectList("getDictItemsWithSql",sql);
        
        return this.transferListIntoMap(listResult);
    }
    
    /**
	 * 获取某个字典的所有的字典项列表，这个方法适用于使用独立的表来存储一个字典的情况
	 * @param targetTableName 表名
	 * @param codeColName 表示code信息的字段名
	 * @param valueColName 表示value信息的字段名
	 * @return 某个字典的所有字段项的集合，集合中每个元素都是一个Map对象，包含code和value两组对偶
     */
    public Map getAllDictItems(String targetTableName, String codeColName, String valueColName) 
    {
    	Map<String,String> mapParam = new HashMap<String,String>();
    	mapParam.put("targetTableName",targetTableName);
		mapParam.put("codeColName",codeColName);
		mapParam.put("valueColName",valueColName);
		
		List listResult = this.getSqlSession().selectList("getDictItemsFromIndependentTable",mapParam);
		
		return this.transferListIntoMap(listResult);
    }

    /**
     * 获取某个字典的所有的字典项列表，这个方法适用于使用一个表来存储多个字典的情况
     * @param targetTableName 表名
     * @param identifyColName 用于标识是哪个字典的字段名
     * @param identifyColValue 指定的标识字段的值，使用这个值将其他字典的字典项过滤掉
     * @param codeColName 表示code信息的字段名
	 * @param valueColName 表示value信息的字段名
	 * @return 某个字典的所有字段项的集合，集合中每个元素都是一个Map对象，包含code和value两组对偶
     */
    public Map getAllDictItems(String targetTableName, String identifyColName, String identifyColValue,
        String codeColName, String valueColName) 
    {
		Map<String,String> mapParam = new HashMap<String,String>();
		mapParam.put("targetTableName",targetTableName);
		mapParam.put("identifyColName",identifyColName);
		mapParam.put("identifyColValue",identifyColValue);
		mapParam.put("codeColName",codeColName);
		mapParam.put("valueColName",valueColName);
		List listResult = this.getSqlSession().selectList("getDictItemsFromShareableTable",mapParam);
		
		return this.transferListIntoMap(listResult);
    }
    
    /**
     * 将List结构转换为Map结构。
     * @param list 源List对象
     * @return 相应的Map结构
     */
    private Map transferListIntoMap(List list)
    {
        Map mapResult = new TreeMap();
		for ( int i = 0, nSize = list.size(); i < nSize; i++ )
		{
			DictBean theDictItem = (DictBean)list.get(i);
			mapResult.put(theDictItem.getCode(), theDictItem.getValue());
	
		}
		list = null;
		return mapResult;
    }

}
