/*
 * Created on 2005-9-29
 */
package com.inwiss.springcrud.dataaccess;

import java.util.Map;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * CrudPersistentStrategy的缺省适配器。
 * 
 * <p>在需要定制Crud的持续化逻辑的时候，我们直接继承这个类，并Override我们关心的方法就可以了。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class CrudPersistentStrategyAdaptor implements CrudPersistentStrategy
{
    
    /**缺省策略对象，请求都被转化为对这个对象的请求*/
    private static CrudPersistentStrategy defaultStrategy = new CrudPersistentStrategyImpl();
    
    /**
     * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildDeleteSQLString(com.inwiss.springcrud.command.RecordCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public String[] buildDeleteSQLString(RecordCommand recordCommand, CrudMeta crudMeta)
    {
        return defaultStrategy.buildDeleteSQLString(recordCommand, crudMeta);
    }
    /**
     * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildGetDataCountSQLString(com.inwiss.springcrud.metadata.CrudMeta, java.util.Map, java.lang.String)
     */
    public String buildGetDataCountSQLString(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause)
    {
        return defaultStrategy.buildGetDataCountSQLString(crudMeta, mapParam, defaultFilterWhereClause);
    }
    /**
     * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildGetDataListSQLString(com.inwiss.springcrud.metadata.CrudMeta, java.util.Map, java.lang.String)
     */
    public String buildGetDataListSQLString(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause)
    {
        return defaultStrategy.buildGetDataListSQLString(crudMeta, mapParam, defaultFilterWhereClause);
    }
    /**
     * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildInsertSQLString(com.inwiss.springcrud.command.RecordCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public String[] buildInsertSQLString(RecordCommand recordCommand, CrudMeta crudMeta)
    {
        return defaultStrategy.buildInsertSQLString(recordCommand, crudMeta);
    }
    /**
     * @see com.inwiss.springcrud.dataaccess.CrudPersistentStrategy#buildUpdateSQLString(com.inwiss.springcrud.command.RecordCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public String[] buildUpdateSQLString(RecordCommand recordCommand, CrudMeta crudMeta)
    {
        return defaultStrategy.buildUpdateSQLString(recordCommand, crudMeta);
    }
}
