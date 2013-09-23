/*
 * Created on 2005-8-7
 */
package com.inwiss.springcrud.validation;

import java.util.Map;

import org.springframework.validation.Errors;

import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * CRUD框架中单列信息的校验规则接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface CrudValidateRule
{

    /**
     * 校验传入数据。
     * @param errors Spring框架提供的校验对象
     * @param fieldName 要校验的参数名称
     * @param fieldValue 要校验的参数值
     * @param allFieldValueMap 所有参数值的集合，使用参数的名称作为key
     * @param crudMeta CRUD元数据
     * @return 校验结果
     */
    public void validate(Errors errors, String fieldName, String fieldValue, Map allFieldValueMap, CrudMeta crudMeta);
    
    /**
     * 是否可应用于新增记录时的校验
     * @return 相应的布尔值
     */
    public boolean isApplicableForAdd();
    
    /**
     * 是否可应用于修改记录时的校验
     * @return 相应的布尔值
     */
    public boolean isApplicableForEdit();
}
