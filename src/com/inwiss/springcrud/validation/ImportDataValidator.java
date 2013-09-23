/*
 * Created on 2005-10-12
 */
package com.inwiss.springcrud.validation;

import java.util.*;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.inwiss.springcrud.command.FileUploadCommand;
import com.inwiss.springcrud.command.ImportDataRecordCommand;
import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.metadata.*;
import com.inwiss.springcrud.context.MetaBeanRetriever;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.ImportMeta;
/**
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class ImportDataValidator implements Validator
{
    private MetaBeanRetriever metaBeanRetriever;
    
    /**一行数据被错误解析后的error code*/
    private String errorInParsingErrorCode = "errorInParsing";
    
    /**导入文本中有主键冲突错误对应的error code*/
    private String duplicateRecordErrorCode = "duplicateRecordAtImportFile";
    
    /**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz)
    {
        return FileUploadCommand.class.isAssignableFrom(clazz);
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object obj, Errors errors)
    {
        FileUploadCommand fileUploadCommand = (FileUploadCommand)obj;
        ImportMeta importMeta = metaBeanRetriever.getImportMetaBean(fileUploadCommand.getName());
        CrudMeta crudMeta = importMeta.getCrudMeta();
        
        Map mapContainerForPkStr = new HashMap();
        
        for ( int i = 0, nSize = fileUploadCommand.getRecords().length; i < nSize; i++ )
        {
            ImportDataRecordCommand importDataRecordCommand = fileUploadCommand.getRecords()[i];
            
            //如果整行数据没有被成功分解，提示之
            if ( importDataRecordCommand.isErrorInParsing() )
                errors.rejectValue("records["+i+"].originalStr", 
                        errorInParsingErrorCode, 
                        new String[]{String.valueOf(i+1), importDataRecordCommand.getOriginalStr()}, 
                        null);
            else
            {
                //针对解析好的Command对象进行列值的校验
                RecordCommand command = importDataRecordCommand.getRecordCommand();
                
                //使用ColumnMeta迭代command对象，将主键列的数据拼接起来，由此判断是否有主键冲突。
                String pkRresentativeStr = this.getPkRepresentativeString(command, crudMeta);
                if ( mapContainerForPkStr.containsKey(pkRresentativeStr) )
                {
                    Integer value = (Integer)mapContainerForPkStr.get(pkRresentativeStr);
                    errors.rejectValue("records["+i+"].originalStr", 
                            duplicateRecordErrorCode,
                            new String[]{String.valueOf(i+1), String.valueOf(value.intValue()+1) },
                            null);
                }
                else
                {
                    mapContainerForPkStr.put(pkRresentativeStr, new Integer(i));
                }
                
                for ( int j = 0, jSize = crudMeta.getColumnMetas().length; j < jSize; j++ )
                {
                    CrudValidateRule[] rules = crudMeta.getColumnMetas()[j].getValidationRules();
                    if ( rules != null )
                    {
    	                for ( int k = 0, ruleSize = rules.length; k < ruleSize; k++ )
    	                {
    	                    CrudValidateRule rule = rules[k];
    	                    String fieldName = crudMeta.getColumnMetas()[j].getColName();
    		                rule.validate(errors, "records["+i+"].recordCommand.mapContent["+fieldName+"]",
    		                        (String)command.getMapContent().get(fieldName),
    		                        command.getMapContent(),
    		                        crudMeta);
    	                }
                    }
                }
            }
        }
    }
    
    /**
     * 获取一行数据中可以和主键形成双射关系的串。
     * @param command 代表一行数据的RecordCommand对象
     * @param crudMeta CRUD元数据
     * @return 一行数据中可以和主键形成双射关系的串
     */
    private String getPkRepresentativeString(RecordCommand command, CrudMeta crudMeta)
    {
        StringBuffer buffer = new StringBuffer();
        
        ColumnMeta[] colMetas = crudMeta.getColumnMetas();
        for ( int i = 0, nSize = colMetas.length; i < nSize; i++ )
        {
            if ( colMetas[i].isPrimaryKey() )
                buffer.append(command.getMapContent().get(colMetas[i].getColName()));
        }        
        return buffer.toString();
    }
    
    /**
     * @param errorInParsingErrorCode The errorInParsingErrorCode to set.
     */
    public void setErrorInParsingErrorCode(String errorInParsingErrorCode)
    {
        this.errorInParsingErrorCode = errorInParsingErrorCode;
    }
    
    /**
     * @param duplicateRecordErrorCode The duplicateRecordErrorCode to set.
     */
    public void setDuplicateRecordErrorCode(String duplicateRecordErrorCode)
    {
        this.duplicateRecordErrorCode = duplicateRecordErrorCode;
    }
    /**
     * @param metaBeanRetriever The metaBeanRetriever to set.
     */
    public void setMetaBeanRetriever(MetaBeanRetriever metaBeanRetriever)
    {
        this.metaBeanRetriever = metaBeanRetriever;
    }
}
