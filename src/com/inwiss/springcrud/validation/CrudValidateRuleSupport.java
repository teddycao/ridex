/*
 * Created on 2005-8-8
 */
package com.inwiss.springcrud.validation;

import java.util.Map;

import org.springframework.validation.Errors;

import com.inwiss.springcrud.metadata.CrudMeta;


/**
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public abstract class CrudValidateRuleSupport implements CrudValidateRule
{
    private String errorCode;
    
    private String defaultMessage;
    
    /**规则是否可应用于新增记录时的校验*/
    private boolean applicableForAdd = true;
    
    /**规则是否可应用于编辑数据时的校验*/
    private boolean applicableForEdit = true;
    
    /**
     * @see com.inwiss.springcrud.validation.CrudValidateRule#validate(org.springframework.validation.Errors, java.lang.String, java.lang.String, java.util.Map, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public abstract void validate(Errors errors, String fieldName, String fieldValue, Map allFieldValueMap, CrudMeta crudMeta);
    
    /**
     * @return Returns the defaultMessage.
     */
    public String getDefaultMessage()
    {
        return defaultMessage;
    }
    
    /**
     * @param defaultMessage The defaultMessage to set.
     */
    public void setDefaultMessage(String defaultMessage)
    {
        this.defaultMessage = defaultMessage;
    }
    
    /**
     * @return Returns the errorCode.
     */
    public String getErrorCode()
    {
        return errorCode;
    }
    
    /**
     * @param errorCode The errorCode to set.
     */
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
    
    /**
     * @return Returns the applicableForAdd.
     */
    public boolean isApplicableForAdd()
    {
        return applicableForAdd;
    }
    /**
     * @param applicableForAdd The applicableForAdd to set.
     */
    public void setApplicableForAdd(boolean applicableForAdd)
    {
        this.applicableForAdd = applicableForAdd;
    }
    /**
     * @return Returns the applicableForEdit.
     */
    public boolean isApplicableForEdit()
    {
        return applicableForEdit;
    }
    /**
     * @param applicableForEdit The applicableForEdit to set.
     */
    public void setApplicableForEdit(boolean applicableForEdit)
    {
        this.applicableForEdit = applicableForEdit;
    }
}
