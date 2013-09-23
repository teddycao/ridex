/*
 * Created on 2004-8-15
 */
package com.inwiss.springcrud.support.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.InitBinder;
/**
 * 数据库字典工厂的实现。
 * 
 * @author 
 */
//@Configuration
public final class DbDictFactoryImpl implements DictFactory
{
	private static final Log log = LogFactory.getLog(DbDictFactoryImpl.class);
	
	private Map dictMetaDataHolder = new HashMap();
	
	private Map dictCache = new HashMap();
	
	private DictDao dictDao;
	
	/**
	 * 初始化方法,将字典的元数据信息取出,需要在spring配置文件中配置好[init-method="init"]
	 */
	//@Bean(initMethod = "init")
	public void init()
	{
		log.info("Begin to retrieve dicts' metaData from DB...");
		List listDictMetaData = null;
		boolean needRetry = false;
		int retryCount = 0;
		
		//下面的重试逻辑的作用在于确保整个应用不会因为这个初始化方法发生错误而不能正常启动。
		//因为在使用Websphere数据源连接数据库的时候，我们常常会碰到诸如StaleConnectionException的错误，但是这种错误往往是可以通过重试加以解决的。
		do
		{    
		    try
		    {
		        listDictMetaData = dictDao.getDictMetaData();
		        needRetry = false;
		    }
		    catch(DataAccessException accessException)
		    {
		        
		        if ( retryCount >= 1 )
		            needRetry = false;
		        else
		        {    
		            needRetry = true;
		            retryCount++;
		            log.warn("Error occured when try to load dict meta data, \n" +
		            		"the error message:["+accessException.getMessage()+"], \n" +
		            		"and the root cause:["+accessException.getCause()+"].\n" +
		            		"now retry it(retry count=["+retryCount+"])...");
		        }        
		    }
		}
		while(needRetry);
		
		if ( listDictMetaData == null )
		    throw new DictFactoryInitializationException("读取数据库中定义的字典元数据出错，无法初始化数据库字典工厂！");
		
		StringBuffer buffer = new StringBuffer();
		for ( int i = 0, nSize = listDictMetaData.size(); i < nSize; i++ )
		{
			DictMetaData metaData = (DictMetaData)listDictMetaData.get(i);
			dictMetaDataHolder.put(metaData.getDictName(),metaData);
			if ( i == 0 )
				buffer.append(metaData.getDictName());
			else
				buffer.append(";"+metaData.getDictName());
		}
        
        log.info("The following dicts' meta data have been loaded into memory:[" + buffer.toString()+"]");
        buffer = null;
	}
	
	
    /**
     * @see com.inwiss.springcrud.support.dictionary.DictFactory#containsDict(java.lang.String)
     */
    public boolean containsDict(String dictName)
    {
        return dictMetaDataHolder.containsKey(dictName);
    }
    
	//TODO 下面的这个方法需要考虑使用synchronized进行同步
    /**
     * 根据字典名称获取整个字典的对偶值
     * @return 整个字典对偶值的集合，集合中的每个元素都是一个Map，包含code和value两组对偶，分别表示字典项的代码和值
     * @see com.inwiss.springcrud.support.dictionary.DictFactory#getDictionaryByName(java.lang.String)
     */
    public Map getDictionaryByName(String dictName) 
    {
    	//如果cache中可以找到这个字典，则直接取
    	if ( dictCache.containsKey(dictName) )
    		return (Map)dictCache.get(dictName);
    	DictMetaData metaData = (DictMetaData)dictMetaDataHolder.get(dictName);
    	
    	if ( metaData == null )
    	{
    	    return null;
    	}
    	//一个字典表只包括一个字典内容的情况
    	if ( "0".equals(metaData.getDictType()) )
    	{	
    		if ( log.isDebugEnabled() )
    			log.debug("The Dict type is:["+metaData.getDictType()
    				+ "], so retrieve the dict items from one table:["+metaData.getDictTableName()+"]");
    		Map theDictMap = dictDao.getAllDictItems(metaData.getDictTableName(),metaData.getCodeColName(),metaData.getValueColName());
    		//取出后，放到cache容器中
    		dictCache.put(dictName,theDictMap);
    		return theDictMap; 
    	} 
    	else//一个字典表包含多个字典内容的情况
    	{	
    		if ( log.isDebugEnabled() )
    			log.debug("The Dict type is:["+metaData.getDictType()
    				+ "], so retrieve the dict items from table:["+metaData.getDictTableName()+"] with specified colName:["
    				+ metaData.getIdentifyColName()+"] and specified colValue:["+metaData.getIdentifyColValue()+"]");
    		Map theDictMap = dictDao.getAllDictItems(metaData.getDictTableName(),metaData.getIdentifyColName(),metaData.getIdentifyColValue(),   	
    									metaData.getCodeColName(),metaData.getValueColName());
    		//取出后，放到cache容器中
    		dictCache.put(dictName,theDictMap);
    		return theDictMap;
    	}
    }

    
	public void setDictDao(DictDao dao)
	{
		dictDao = dao;
	}

}
