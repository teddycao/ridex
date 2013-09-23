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
 * 整形数校验规则。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class IntegerRule extends CrudValidateRuleSupport
{
    /**
     * 构造方法，用于设置缺省值。
     */
    public IntegerRule()
    {
        this.setErrorCode("integer");
        this.setDefaultMessage("输入值必须是整形值");
    }
    
    /**
     * @see com.inwiss.springcrud.validation.CrudValidateRule#validate(org.springframework.validation.Errors, java.lang.String, java.lang.String, java.util.Map, com.inwiss.springcrud.metadata.CrudMeta)
     */
	public void validate(Errors errors, String fieldName, String fieldValue, Map allFieldValueMap, CrudMeta crudMeta)
    {
	    if ( !StringUtils.hasLength(fieldValue) )
	        return;
        
		try
        {
            Integer.parseInt(fieldValue);
        }
        catch (NumberFormatException e)
        {
            errors.rejectValue(fieldName, this.getErrorCode(), this.getDefaultMessage());
        }

    }

}
