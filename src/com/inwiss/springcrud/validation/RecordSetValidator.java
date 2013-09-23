/*
 * Created on 2005-8-7
 */
package com.inwiss.springcrud.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.command.RecordSetCommand;
import com.inwiss.springcrud.context.MetaBeanRetriever;
import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * 用于对<code>RecordSetCommand</code>对象进行校验的Validator类。
 * 
 * <p>该类依赖支持CRUD操作的元数据定义类（<code>CrudMeta</code>）中的信息来实现具体的校验。
 * 
 * <p>该类实现的是Spring Framework提供的接口，可以访问<a herf="www.springframework.org">Spring Framework官方网站</a>获取更多信息。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class RecordSetValidator implements Validator
{

	private MetaBeanRetriever metaBeanRetriever;
    
    /**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz)
    {
        return RecordSetCommand.class.isAssignableFrom(clazz);
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object obj, Errors errors)
    {
    	RecordSetCommand rsCommand = (RecordSetCommand)obj;
    	
        /**CRUD元数据定义对象*/
        CrudMeta crudMeta = metaBeanRetriever.getCrudMetaBean(rsCommand.getName()); 
        
        RecordSetCommand recordSetCommand = (RecordSetCommand)obj;
        for ( int i = 0; i < recordSetCommand.getRecords().length; i++ )
        {
        	RecordCommand command = recordSetCommand.getRecords()[i];
        	
            for ( int j = 0, nSize = crudMeta.getColumnMetas().length; j < nSize; j++ )
            {
                CrudValidateRule[] rules = crudMeta.getColumnMetas()[j].getValidationRules();
                if ( rules != null )
                {
	                for ( int k = 0, ruleSize = rules.length; k < ruleSize; k++ )
	                {
	                    CrudValidateRule rule = rules[k];
	                    if ( rule.isApplicableForEdit() )
	                    {
	                        String fieldName = crudMeta.getColumnMetas()[j].getColName();
			                rule.validate(errors, "records["+i+"].mapContent["+fieldName+"]",
			                        (String)command.getMapContent().get(fieldName),
			                        command.getMapContent(),
			                        crudMeta);
	                    }
	                }
                }
            }
        }
    }

	public void setMetaBeanRetriever(MetaBeanRetriever metaBeansFactory) {
		this.metaBeanRetriever = metaBeansFactory;
	}
}
