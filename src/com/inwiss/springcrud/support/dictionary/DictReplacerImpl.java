/*
 * Created on 2004-8-30
 */
package com.inwiss.springcrud.support.dictionary;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 字典替换缺省实现类
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DictReplacerImpl implements DictReplacer
{
	private static final Log log = LogFactory.getLog(DictReplacerImpl.class);

	private DictFactory dictFactory;
	
	/**
	 * 如果通过字典工厂获取的字典Map为空获取根据code获取不到相应的字典值，则把code作为字典值返回。
	 * 不过碰到这种情况，会记录一个WARN日志。
	 * @see com.inwiss.springcrud.support.dictionary.DictReplacer#dictReplace(java.lang.String, java.lang.String)
	 */
	public String dictReplace(String dictName, String code)
	{
		if ( dictName == null || "".equals(dictName))
		{
			log.warn("The dictName is null, return the code:["+code+"] as value");	
			return code; 
		} 
        if (code == null || "".equals(code))
        {
        	log.warn("Code is null, return the code:["+code+"] as value");
            return code;
        }
		Map mapDict = dictFactory.getDictionaryByName(dictName);
		if ( mapDict == null || !mapDict.containsKey(code) )
		{
			if ( mapDict == null )	
				log.warn("The map returned by DictFactory with name:["+dictName+"] is null, return the code as value");
			else
				log.warn("The dictMap with name:["+dictName+"] does not contain code:["+code+"] related dictionary item, return the code as value");
			return code;
		}
		else
			return (String)mapDict.get(code); 
	}

	/**
	 * 供Spring Framework设置Bean关联的回调方法，这里使用设定的DictFactory获取字典，进而进行字典替换
	 */	
	public void setDictFactory(DictFactory factory)
	{
		dictFactory = factory;
	}

}
