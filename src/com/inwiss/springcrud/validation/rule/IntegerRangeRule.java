/*
 * Created on 2005-8-8
 */
package com.inwiss.springcrud.validation.rule;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.validation.CrudValidateRuleSupport;

/**
 * 整形数范围校验规则。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class IntegerRangeRule extends CrudValidateRuleSupport
{
    private static final Log log = LogFactory.getLog(IntegerRangeRule.class);

    private int minValue;
    
    private int maxValue;
    
    /**
     * 构造方法，用于设置缺省值。
     */
    public IntegerRangeRule()
    {
        this.setErrorCode("intRange");
        this.setDefaultMessage("输入的整形值必须大于{0}且小于{1}");
    }
    
    /**
     * @see com.inwiss.springcrud.validation.CrudValidateRule#validate(org.springframework.validation.Errors, java.lang.String, java.lang.String, java.util.Map, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public void validate(Errors errors, String fieldName, String fieldValue, Map allFieldValueMap, CrudMeta crudMeta)
    {   
        if ( !StringUtils.hasLength(fieldValue) )
	        return;
        
		Integer[] argsArr = new Integer[2];
        argsArr[0] = new Integer(minValue);
        argsArr[1] = new Integer(maxValue);
        
		int nValue;
		try
        {
            nValue = Integer.parseInt(fieldValue);
            if ( !( nValue >= minValue && nValue <= maxValue ) )
                errors.rejectValue(fieldName, this.getErrorCode(), argsArr, this.getDefaultMessage());
        }
        catch (NumberFormatException e)
        {
            errors.rejectValue(fieldName, this.getErrorCode(), argsArr, this.getDefaultMessage());
        }
    }

    /**
     * @param maxValue The maxValue to set.
     */
    public void setMaxValue(int maxValue)
    {
        this.maxValue = maxValue;
    }
    
    /**
     * @param minValue The minValue to set.
     */
    public void setMinValue(int minValue)
    {
        this.minValue = minValue;
    }
}
