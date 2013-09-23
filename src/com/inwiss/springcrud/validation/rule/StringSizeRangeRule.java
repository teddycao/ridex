/*
 * Created on 2005-10-21
 */
package com.inwiss.springcrud.validation.rule;

import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.validation.CrudValidateRuleSupport;

/**
 * 字符串长度范围校验。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class StringSizeRangeRule extends CrudValidateRuleSupport
{
    /**字符串允许的最小长度*/
    private int minSize = 0;
    
    /**字符串允许的最大长度*/
    private int maxSize = Integer.MAX_VALUE;
    
    /**
     * 构造方法，用于设置缺省值。
     */
    public StringSizeRangeRule()
    {
        this.setErrorCode("stringSizeRange");
        this.setDefaultMessage("输入的字符串长度必须大于{0}且小于{1}");
    }
    
    /**
     * @see com.inwiss.springcrud.validation.CrudValidateRule#validate(org.springframework.validation.Errors, java.lang.String, java.lang.String, java.util.Map, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public void validate(Errors errors, String fieldName, String fieldValue,
            Map allFieldValueMap, CrudMeta crudMeta)
    {
        if ( !StringUtils.hasLength(fieldValue) )
	        return;
	    
        String[] argsArr = new String[2];
		argsArr[0] = String.valueOf(minSize);
		argsArr[1] = String.valueOf(maxSize);
		
		if ( minSize > fieldValue.length() || maxSize < fieldValue.length() )
		{
		    errors.rejectValue(fieldName, this.getErrorCode(), argsArr, this.getDefaultMessage());
		}

    }
    
    /**
     * @param maxSize The maxSize to set.
     */
    public void setMaxSize(int maxSize)
    {
        this.maxSize = maxSize;
    }
    /**
     * @param minSize The minSize to set.
     */
    public void setMinSize(int minSize)
    {
        this.minSize = minSize;
    }
}
