/*
 * Created on 2005-8-22
 */
package com.inwiss.springcrud.dataaccess;

import java.util.Iterator;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.ColumnTypeEnum;
import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * CRUD持续化策略接口的缺省实现。
 * 
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class CrudPersistentStrategyImpl implements CrudPersistentStrategy 
{

	/**
	 * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildInsertSQLString(com.inwiss.springcrud.command.RecordCommand,com.inwiss.springcrud.metadata.CrudMeta)
	 */
	public String[] buildInsertSQLString(RecordCommand recordCommand,CrudMeta crudMeta){
		StringBuffer buffer = new StringBuffer("insert into ");
		StringBuffer valueBuffer = new StringBuffer(" values (");
        boolean isFirst = true;
        ColumnMeta[] colMeta = crudMeta.getColumnMetas();
        Map mapContent = recordCommand.getMapContent();
        
        buffer.append(crudMeta.getTableName()+ " (");
        
        for(int i=0;i<colMeta.length;i++) {
            String name = colMeta[i].getColName();
            String value = (String)mapContent.get(name);
            
            if(ColumnTypeEnum.NUMBER_TYPE.equalsIgnoreCase(colMeta[i].getType())&&!StringUtils.hasText(value)){
            	value = "NULL";
            }
            
            if(!colMeta[i].isDerived()){
            	if(isFirst){
            		isFirst = false;
            	}else{
            		buffer.append(",");
            		valueBuffer.append(",");
            	}
            	
            	buffer.append(name);
            	valueBuffer.append(this.decorateColumnValue(value,colMeta[i].getType()));
            }
        }
        
        valueBuffer.append(")");
        buffer.append(")");
        buffer.append(valueBuffer.toString());
        
        return new String[]{buffer.toString()};		
	}
	
	/**
	 * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildUpdateSQLString(com.inwiss.springcrud.command.RecordCommand,com.inwiss.springcrud.metadata.CrudMeta)
	 */	
	public String[] buildUpdateSQLString(RecordCommand recordCommand,CrudMeta crudMeta) {
		StringBuffer buffer = new StringBuffer("update ");
        boolean isFirst = true;
        ColumnMeta[] colMeta = crudMeta.getColumnMetas();
        Map mapContent = recordCommand.getMapContent();
        
        buffer.append(crudMeta.getTableName());
        
        for(int i=0;i<colMeta.length;i++) {
            String name = colMeta[i].getColName();
            String value = (String)mapContent.get(name);
            
            if(ColumnTypeEnum.NUMBER_TYPE.equalsIgnoreCase(colMeta[i].getType())&&!StringUtils.hasText(value)){
            	value = "NULL";
            }
            
            if(!colMeta[i].isPrimaryKey()&&!colMeta[i].isDerived()){
            	if(isFirst){
            		buffer.append(" set ");
            		isFirst = false;
            	}else{
            		buffer.append(",");
            	}
            	
            	buffer.append(name);
            	buffer.append("=");
            	buffer.append(this.decorateColumnValue(value,colMeta[i].getType()));
            }
        }
        
        buffer.append(this.buildWhereClauseOfPrimaryKey(recordCommand,crudMeta));
        return new String[]{buffer.toString()};
	}
	
	/**
	 * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildDeleteSQLString(com.inwiss.springcrud.command.RecordCommand,com.inwiss.springcrud.metadata.CrudMeta)
	 */
	public String[] buildDeleteSQLString(RecordCommand recordCommand,CrudMeta crudMeta) {
		StringBuffer buffer = new StringBuffer("delete from ");
        
		buffer.append(crudMeta.getTableName());
        buffer.append(this.buildWhereClauseOfPrimaryKey(recordCommand,crudMeta));
        
        return new String[]{buffer.toString()};
	}	
	
	
    public String buildGetDataListSQLString(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause)
    {
        String tableName = crudMeta.getTableName();
        
        //拼接用于获取数据列表的sql
        StringBuffer buffer = new StringBuffer("select ");
        ColumnMeta[] columnMetaArr = crudMeta.getColumnMetas();
        for ( int i = 0; i < columnMetaArr.length; i++ )
        {
            if ( columnMetaArr[i].getColExpression() == null )
                buffer.append(columnMetaArr[i].getColName());
            else
                buffer.append(columnMetaArr[i].getColExpression()+" as "+columnMetaArr[i].getColName());
            if ( i != columnMetaArr.length - 1)
                buffer.append(", ");
        }
        buffer.append(" from "+tableName);
        if ( crudMeta.getChainedDefaultFilter() != null )
        {
            buffer.append(" where "+defaultFilterWhereClause);
            if (StringUtils.hasLength(getUserInputParamWhereClause(crudMeta, mapParam)) )
            {
                buffer.append(" and "+ getUserInputParamWhereClause(crudMeta, mapParam));
            }
        }
        else
        {
            if (StringUtils.hasLength(getUserInputParamWhereClause(crudMeta, mapParam)) )
            {
                buffer.append(" where "+getUserInputParamWhereClause(crudMeta, mapParam));
            }
        }
        
        buffer.append(this.getOrderByClause(crudMeta));
        
        String sqlGetDataList = buffer.toString();
        buffer = null;
        return sqlGetDataList;
    }

	/**
	 * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildGetDataCountSQLString(com.inwiss.springcrud.metadata.CrudMeta, java.util.Map, java.lang.String)
	 */		
    public String buildGetDataCountSQLString(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select count(*) as COUNT from "+crudMeta.getTableName());
        if ( crudMeta.getChainedDefaultFilter() != null )
        {
            buffer.append(" where "+defaultFilterWhereClause);
            if (StringUtils.hasLength(getUserInputParamWhereClause(crudMeta, mapParam)) )
            {
                buffer.append(" and "+ getUserInputParamWhereClause(crudMeta, mapParam));
            }
        }
        else
        {
            if (StringUtils.hasLength(getUserInputParamWhereClause(crudMeta, mapParam)) )
            {
                buffer.append(" where "+getUserInputParamWhereClause(crudMeta, mapParam));
            }
        }
        String sqlGetDataCount = buffer.toString();
        buffer = null;
        return sqlGetDataCount;
    }
    
    private String getUserInputParamWhereClause(CrudMeta crudMeta, Map mapParam)
    {
        if ( mapParam == null || mapParam.size() == 0 )
            return null;
        StringBuffer buffer = new StringBuffer();
        Iterator paramIterator = mapParam.keySet().iterator();
        boolean isFirst = true;
        
        while ( paramIterator.hasNext() )
        {
            String key = (String)paramIterator.next();
            String value = (String)mapParam.get(key);
            if ( StringUtils.hasLength(value) )
            {
                ColumnMeta columnMeta = crudMeta.getColumnMetaByColName(key);
                
                String colName = (columnMeta.getColExpression()!=null)?columnMeta.getColExpression():key;
                if ( isFirst )
                    isFirst = false;
                else
                    buffer.append("and ");
                
                if( ColumnTypeEnum.NUMBER_TYPE.equalsIgnoreCase(columnMeta.getType()))
                {
                	buffer.append(colName + " = " + value);
                }else{
	                if ( columnMeta.isApplyLike() )
	                    buffer.append(colName + " like '%" + value + "%' ");
	                else
	                    buffer.append(colName + " = '" + value + "'");
                }
            }
        }
        
        return buffer.toString();
    }
    
    private String getOrderByClause(CrudMeta crudMeta)
    {
        if ( !StringUtils.hasLength(crudMeta.getOrderByFieldName()) )
            return "";
        else
            return " order by " + crudMeta.getOrderByFieldName() + " " + (crudMeta.isOrderByAsc()?"asc":"desc");
        
    }
	
	/**
	 * 根据CrudMeta和RecordCommand构造更新或者删除一条记录的Where条件子串。
	 * @param recordCommand RecordCommand对象，是一条数据对应的Command Object
	 * @param crudMeta CRUD元数据
	 * @return 构造好的where子句
	 */
	private String buildWhereClauseOfPrimaryKey(RecordCommand recordCommand,CrudMeta crudMeta){
		StringBuffer buffer = new StringBuffer(" where ");
        boolean isFirst = true;
        ColumnMeta[] colMeta = crudMeta.getColumnMetas();
        Map mapContent = recordCommand.getMapContent();
        
        for(int i=0;i<colMeta.length;i++) {
            String name = colMeta[i].getColName();
            String value = (String)mapContent.get(name);
            boolean isPrimary = colMeta[i].isPrimaryKey();
            
            if(isPrimary){
            	if(isFirst){
            		isFirst = false;
            	}else{
            		buffer.append(" and ");
            	}
            	
            	buffer.append(name);
            	buffer.append("=");
            	buffer.append(this.decorateColumnValue(value,colMeta[i].getType()));
            }
        }
        
        return buffer.toString();		
	}

	/**
	 * 对字段值做处理，如果是字符串类型，则加上引号，否则直接返回。
	 * @param value 字段值
	 * @param columnType 字典类型
	 * @return 处理后的字段值
	 */
	private String decorateColumnValue(String value, String columnType){
		if(ColumnTypeEnum.NUMBER_TYPE.equalsIgnoreCase(columnType))
			return value;
		else
			return "'"+value+"'";
	}
}
