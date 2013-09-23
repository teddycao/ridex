/*
 * Created on 2005-8-8
 */
package com.inwiss.springcrud.validation.rule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.validation.Errors;
import org.springframework.util.*;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.validation.CrudValidateRuleSupport;


/**
 * 日期模式串校验规则。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DateRule extends CrudValidateRuleSupport 
{
    private static String DEFAULT_PATTERN = "yyyyMMdd";
    
    /**日期模式，如"yyyyMMdd"等*/
    private String pattern = DEFAULT_PATTERN;
    
    /**
     * 构造方法，用于设置缺省值。
     */
    public DateRule()
    {
        this.setErrorCode("datePattern");
        this.setDefaultMessage("输入的日期值不符合预定义的格式:{0}");
    }
    
	/**
	 * @see com.inwiss.springcrud.validation.CrudValidateRule#validate(org.springframework.validation.Errors, java.lang.String, java.lang.String, java.util.Map, com.inwiss.springcrud.metadata.CrudMeta)
	 */
	public void validate(Errors errors, String fieldName, String fieldValue, Map allFieldValueMap, CrudMeta crudMeta)
	{
	    if ( !StringUtils.hasLength(fieldValue) )
	        return;
	    
	    String[] argsArr = new String[]{pattern};

        try
        {
            Date theDate = new SimpleDateFormat(pattern).parse(fieldValue);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(theDate);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            calendar = null;
            
            int inputYear = Integer.parseInt(fieldValue.substring(0,4));
            int inputMonth = Integer.parseInt(fieldValue.substring(4,6));
            int inputDayOfMonth = Integer.parseInt(fieldValue.substring(6,8));
            
            if ( ( year != inputYear ) || ( month+1 != inputMonth ) || ( dayOfMonth != inputDayOfMonth ) )
            {
                errors.rejectValue(fieldName, this.getErrorCode(), argsArr, this.getDefaultMessage());
            }
            
        } 
        catch (ParseException e)
        {
            errors.rejectValue(fieldName, this.getErrorCode(), argsArr, this.getDefaultMessage());
        }
	    
	}
	
	
    /**
     * @param pattern The pattern to set.
     */
    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }
}
