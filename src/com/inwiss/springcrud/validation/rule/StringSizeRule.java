/*
 * Created on 2005-8-8
 */
package com.inwiss.springcrud.validation.rule;

import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.validation.CrudValidateRuleSupport;

/**
 * 字符串长度大小校验规则。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class StringSizeRule extends CrudValidateRuleSupport
{
    /**长度大小*/
	private int size;
	
    /**
     * 构造方法，用于设置缺省值。
     */
    public StringSizeRule()
    {
        this.setErrorCode("stringSize");
        this.setDefaultMessage("输入的字符串长度必须为{0}");
    }
	
	/**
	 * @see com.inwiss.springcrud.validation.CrudValidateRule#validate(org.springframework.validation.Errors, java.lang.String, java.lang.String, java.util.Map, com.inwiss.springcrud.metadata.CrudMeta)
	 */
	public void validate(Errors errors, String fieldName, String fieldValue, Map allFieldValueMap, CrudMeta crudMeta) 
	{
	    if ( !StringUtils.hasLength(fieldValue) )
	        return;
	    
	    String[] argsArr = new String[]{String.valueOf(size)};
	    
		if ( size != fieldValue.length() )
		{
		    errors.rejectValue(fieldName, this.getErrorCode(), argsArr, this.getDefaultMessage());
		}
	}
	
    /**
     * @param size The size to set.
     */
    public void setSize(int size)
    {
        this.size = size;
    }
}
