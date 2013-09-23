/*
 * Created on 2005-8-6
 */
package com.inwiss.springcrud.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.context.MetaBeanRetriever;
import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * 在新增记录时，用于对<code>RecordCommand</code>对象进行校验的Validator类。
 * 
 * <p>该类依赖支持CRUD操作的元数据定义类（<code>CrudMeta</code>）中的信息来实现具体的校验。
 * 
 * <p>该类实现的是Spring Framework提供的接口，可以访问<a herf="www.springframework.org">Spring Framework官方网站</a>获取更多信息。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class RecordValidatorForAdd implements Validator
{
	private MetaBeanRetriever metaBeanRetriever;
    
    /**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz)
    {
        return RecordCommand.class.isAssignableFrom(clazz);
    }

    
    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object obj, Errors errors)
    {
    	RecordCommand command = (RecordCommand)obj;
    	
        /**CRUD元数据定义对象*/
        CrudMeta crudMeta = (CrudMeta)metaBeanRetriever.getCrudMetaBean(command.getName());
        
        for ( int i = 0, nSize = crudMeta.getColumnMetas().length; i < nSize; i++ )
        {
            CrudValidateRule[] rules = crudMeta.getColumnMetas()[i].getValidationRules();
            if ( rules != null )
            {
	            for ( int j = 0, ruleSize = rules.length; j < ruleSize; j++ )
	            {
	                CrudValidateRule rule = rules[j];
	                if ( rule.isApplicableForAdd() )
	                {
	                    String fieldName = crudMeta.getColumnMetas()[i].getColName();
	                    rule.validate(errors, "mapContent["+fieldName+"]",
	                            (String)command.getMapContent().get(fieldName),
	                            command.getMapContent(),
	                            crudMeta);
	                }
	            }
            }
        }

    }
    

	public void setMetaBeanRetriever(MetaBeanRetriever metaBeansFactory) {
		this.metaBeanRetriever = metaBeansFactory;
	}
}
