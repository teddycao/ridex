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
 * ˫������ΧУ�����
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DoubleRangeRule extends CrudValidateRuleSupport {

	private String minValue;

	private String maxValue;

	/**
	 * ���췽������������ȱʡֵ��
	 */
	public DoubleRangeRule()
	{
	    this.setErrorCode("doubleRange");
	    this.setDefaultMessage("����ֵ�������{0}��С��{1}");
	}
	
	/**
	 * @see com.inwiss.springcrud.validation.CrudValidateRule#validate(org.springframework.validation.Errors, java.lang.String, java.lang.String, java.util.Map, com.inwiss.springcrud.metadata.CrudMeta)
	 */
	public void validate(Errors errors, String fieldName, String fieldValue, Map allFieldValueMap, CrudMeta crudMeta)
	{
	    if ( !StringUtils.hasLength(fieldValue) )
	        return;
	    
		String[] argsArr = new String[2];
		argsArr[0] = minValue;
		argsArr[1] = maxValue;

		double nValue;

		try 
		{
			nValue = Double.parseDouble(fieldValue);
			if (!(new Double(nValue).compareTo(new Double(minValue)) >= 0 && new Double(nValue).compareTo(
					new Double(maxValue) ) <= 0))
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
    public void setMaxValue(String maxValue)
    {
        this.maxValue = maxValue;
    }

    /**
     * @param minValue The minValue to set.
     */
    public void setMinValue(String minValue)
    {
        this.minValue = minValue;
    }
}
