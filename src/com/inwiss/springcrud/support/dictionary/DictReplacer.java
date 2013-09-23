/*
 * Created on 2004-8-30
 */
package com.inwiss.springcrud.support.dictionary;

/**
 * 字典替换接口
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface DictReplacer
{
	/**
	 * 根据字典名称和code获取替换后的字典值
	 */
	public String dictReplace(String dictName, String code);
	
}
