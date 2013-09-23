/*
 * Created on 2005-8-14
 */
package com.inwiss.springcrud;

import java.util.List;
import java.util.Map;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.command.RecordSetCommand;
import com.inwiss.springcrud.dataaccess.CrudDao;
import com.inwiss.springcrud.dataaccess.CrudPersistentStrategy;
import com.inwiss.springcrud.dataaccess.CrudPersistentStrategyImpl;
import com.inwiss.springcrud.metadata.CrudMeta;

/**
 * CRUD业务逻辑接口的缺省实现。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class CrudServiceImpl implements CrudService
{
    /**CRUD的DAO对象*/
    private CrudDao crudDao;
    
    /**缺省的CRUD持续化策略接口实现*/
    private static CrudPersistentStrategy defaultPersistentStrategy = new CrudPersistentStrategyImpl();
    
    /**
     * 获取某个Crud定义在条件作用下的记录数量。
     * @see com.inwiss.springcrud.CrudService#getNrOfElements(com.inwiss.springcrud.metadata.CrudMeta, java.util.Map, java.lang.String)
     */
    public int getNrOfElements(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause)
    {
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
        String sql = strategy.buildGetDataCountSQLString(crudMeta, mapParam, defaultFilterWhereClause);
        return crudDao.getDataCount(sql);
    }
    
    
    /**
     * 获取某个Crud定义在条件作用下的记录列表。
     * @see com.inwiss.springcrud.CrudService#getItemList(com.inwiss.springcrud.metadata.CrudMeta, java.util.Map, java.lang.String)
     */
    public List getItemList(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause)
    {
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
        String sql= strategy.buildGetDataListSQLString(crudMeta, mapParam, defaultFilterWhereClause);
        return crudDao.getDataList(sql);
    }
    
    /**
     * 获取某个Crud定义在条件作用下的记录列表。
     * @see com.inwiss.springcrud.CrudService#getItemListWithOffset(com.inwiss.springcrud.metadata.CrudMeta, java.util.Map, int, int, java.lang.String)
     */
    public List getItemListWithOffset(CrudMeta crudMeta, Map mapParam,
            int nOffset, int nSize, String defaultFilterWhereClause)
    {
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
        String sql= strategy.buildGetDataListSQLString(crudMeta, mapParam, defaultFilterWhereClause);
        return crudDao.getDataList(sql, nOffset, nSize);
    }
    
    /**
     * 插入一条记录。
     * @see com.inwiss.springcrud.CrudService#insertRecord(com.inwiss.springcrud.command.RecordCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public void insertRecord(RecordCommand recordCommand, CrudMeta crudMeta)
    {
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
    	String[] sqlArr = strategy.buildInsertSQLString(recordCommand,crudMeta);
    	for ( int i = 0, nSize = sqlArr.length; i < nSize; i++ )
    	    crudDao.doUpdate(sqlArr[i]);
    }
    
    
    /**
     * 插入一批记录。
     * @see com.inwiss.springcrud.CrudService#insertRecordSet(com.inwiss.springcrud.command.RecordSetCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public void insertRecordSet(RecordSetCommand recordSet, CrudMeta crudMeta)
    {
        RecordCommand[] recordCmd = recordSet.getRecords();
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
    	for( int i=0; i<recordCmd.length; i++ )
    	{
    	    String[] sqlArr = strategy.buildInsertSQLString(recordCmd[i], crudMeta);
    	    for ( int j = 0, jSize = sqlArr.length; j < jSize; j++ )
    	    	crudDao.doUpdate(sqlArr[j]);
    	}
    }
    
    /**
     * 更新一条记录。
     * @see com.inwiss.springcrud.CrudService#updateRecord(com.inwiss.springcrud.command.RecordCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public void updateRecord(RecordCommand recordCommand, CrudMeta crudMeta)
    {
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
		String[] sqlArr = strategy.buildUpdateSQLString(recordCommand,crudMeta);
		for ( int i = 0, nSize = sqlArr.length; i < nSize; i++ )
			crudDao.doUpdate(sqlArr[i]);

    }
    
    /**
     * 更新一批记录。
     * @see com.inwiss.springcrud.CrudService#updateRecordSet(com.inwiss.springcrud.command.RecordSetCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public void updateRecordSet(RecordSetCommand recordSet, CrudMeta crudMeta)
    {
        RecordCommand[] recordCmd = recordSet.getRecords();
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
    	for( int i=0; i<recordCmd.length; i++ )
    	{
    	    String[] sqlArr = strategy.buildUpdateSQLString(recordCmd[i], crudMeta);
    	    for ( int j = 0, jSize = sqlArr.length; j < jSize; j++ )
    	    	crudDao.doUpdate(sqlArr[j]);
    	}
    }
    
    /**
     * 删除一条记录。
     * @see com.inwiss.springcrud.CrudService#deleteRecord(com.inwiss.springcrud.command.RecordCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public void deleteRecord(RecordCommand recordCommand, CrudMeta crudMeta)
    {
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
		String[] sqlArr = strategy.buildDeleteSQLString(recordCommand,crudMeta);
		for ( int i = 0, nSize = sqlArr.length; i < nSize; i++ )
		    crudDao.doUpdate(sqlArr[i]);		
    }
    
    /**
     * 删除一批记录。
     * @see com.inwiss.springcrud.CrudService#deleteRecordSet(com.inwiss.springcrud.command.RecordSetCommand, com.inwiss.springcrud.metadata.CrudMeta)
     */
    public void deleteRecordSet(RecordSetCommand recordSet, CrudMeta crudMeta)
    {
        RecordCommand[] recordCmd = recordSet.getRecords();
        CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(crudMeta);
    	for(int i = 0; i < recordCmd.length; i++ )
    	{
    		String[] sqlArr = strategy.buildDeleteSQLString(recordCmd[i], crudMeta);
    		for ( int j = 0, jSize = sqlArr.length; j < jSize; j++ )
    		    crudDao.doUpdate(sqlArr[j]);
    	}
    }

    /**
     * 获取当前应该使用的CrudPersistentStrategy对象
     * @param crudMeta CRUD元数据定义对象
     * @return 如果CrudMeta中配置有CrudPersistentStrategy，返回之，否则返回缺省的CrudPersistentStrategy
     */
    private CrudPersistentStrategy getAppliedPersistentStrategy(CrudMeta crudMeta)
    {
        return (crudMeta.getCrudPersistentStrategy()==null)?defaultPersistentStrategy:crudMeta.getCrudPersistentStrategy();
    }
    
    /**
     * @param crudDao The crudDao to set.
     */
    public void setCrudDao(CrudDao crudDao)
    {
        this.crudDao = crudDao;
    }
}
