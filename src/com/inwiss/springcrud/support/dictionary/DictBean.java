/*
 * Created on 2004-10-15
 */
package com.inwiss.springcrud.support.dictionary;

/**
 * 保存字典信息的Bean，由两个属性，分别是CODE和VALUE。<br><br>
 * 
 * 这个Bean仅在内部使用，外界接触的字典是一个Map结构的对象。
 * 因为Sqlmap不能为我们产生TreeMap结构的结果，所以在<code>com.datawise.dias.query.dataaccessDictDaoImpl</code>
 * 中我们必须先使用Sqlmap先生成DictBean的集合，在转换成使用TreeMap结构的字典<br><br>
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DictBean
{
	private String code;
	private String value;
	
	public String getCode()
	{
		return code;
	}

	public String getValue()
	{
		return value;
	}

	public void setCode(String string)
	{
		code = string;
	}

	public void setValue(String string)
	{
		value = string;
	}

}
