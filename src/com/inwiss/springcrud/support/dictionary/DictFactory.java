/*
 * Created on 2004-8-13
 */
package com.inwiss.springcrud.support.dictionary;

import java.util.Map;

/**
 * 字典工厂接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 * @see com.inwiss.springcrud.support.dictionary.DictDataRetriever
 */
public interface DictFactory 
{
    /**
     * 根据传入字典名称获取字典工厂是否包含该字典的信息
     * @param dictName 字典名称
     * @return 字典工厂是否包含某字典的布尔值
     */
    public boolean containsDict(String dictName);
    
    /**
     * 根据字典名称获取相应的字典Map
     * @param dictName 字典名称
     * @return 字典对应的Map
     */
	public Map getDictionaryByName(String dictName);

}
