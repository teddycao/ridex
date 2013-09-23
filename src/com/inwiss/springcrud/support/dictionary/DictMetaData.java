/*
 * Created on 2004-8-13
 */
package com.inwiss.springcrud.support.dictionary;

/**
 * 描述字典的元数据信息
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DictMetaData 
{
	/**字段名称*/
	private String dictName;
	
	/**字典类型*/
	private String dictType;
	
	/**字典所在表的表名*/
	private String dictTableName;
	
	/**标识字典的字段名称。多个字典存于同一张表的时候,需要使用标识字典名称和相应值来做过滤*/
	private String identifyColName;
	
	/**标识字典的字段值。多个字典存于同一张表的时候,需要使用标识字典名称和相应值来做过滤*/
	private String identifyColValue;
	
	/**作为Code的字段名*/
	private String codeColName;
	
	/**作为value的字段名*/
	private String valueColName;
	
    /**是否需要'全部'选项*/
	private String needTotalFlag;
    
	public String getCodeColName()
	{
		return codeColName;
	}

	public String getDictName()
	{
		return dictName;
	}

	public String getDictTableName()
	{
		return dictTableName;
	}

	public String getDictType()
	{
		return dictType;
	}

	public String getIdentifyColName()
	{
		return identifyColName;
	}

	public String getIdentifyColValue()
	{
		return identifyColValue;
	}

	public String getValueColName()
	{
		return valueColName;
	}

	public void setCodeColName(String string)
	{
		codeColName = string;
	}

	public void setDictName(String string)
	{
		dictName = string;
	}

	public void setDictTableName(String string)
	{
		dictTableName = string;
	}

	public void setDictType(String string)
	{
		dictType = string;
	}

	public void setIdentifyColName(String string)
	{
		identifyColName = string;
	}

	public void setIdentifyColValue(String string)
	{
		identifyColValue = string;
	}

	public void setValueColName(String string)
	{
		valueColName = string;
	}


	/**
	 * @return Returns the needTotalFlag.
	 */
	public String getNeedTotalFlag() {
		return needTotalFlag;
	}
	/**
	 * @param needTotalFlag The needTotalFlag to set.
	 */
	public void setNeedTotalFlag(String needTotalFlag) {
		this.needTotalFlag = needTotalFlag;
	}
}
