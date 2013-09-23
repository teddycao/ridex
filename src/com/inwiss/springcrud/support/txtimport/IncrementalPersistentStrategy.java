/*
 * Created on 2005-9-2
 */
package com.inwiss.springcrud.support.txtimport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.inwiss.springcrud.command.*;
import com.inwiss.springcrud.dataaccess.CrudDao;
import com.inwiss.springcrud.dataaccess.CrudPersistentStrategy;
import com.inwiss.springcrud.dataaccess.CrudPersistentStrategyImpl;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.ImportMeta;

/**
 * 数据增量导入持续化处理器。
 * 
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class IncrementalPersistentStrategy implements ImportDataPersistentStrategy
{
	private static final Log logger = LogFactory.getLog(IncrementalPersistentStrategy.class);

	/**CRUD的DAO对象*/
	private CrudDao crudDao;
	
	/**缺省的CRUD持续化策略接口实现*/
	private static CrudPersistentStrategy defaultPersistentStrategy = new CrudPersistentStrategyImpl();
	
	/**
	 * @see com.inwiss.springcrud.support.txtimport.ImportDataPersistentStrategy#persistentImportData(com.inwiss.springcrud.command.RecordSetCommand, com.inwiss.springcrud.metadata.ImportMeta)
	 */
	public void persistentImportData(RecordSetCommand recordSetCommand, ImportMeta importMeta) 
	{
		CrudPersistentStrategy strategy = this.getAppliedPersistentStrategy(importMeta.getCrudMeta());	
		
		
		for (int i = 0, nSize = recordSetCommand.getRecords().length; i < nSize; i++ )
		{
			String[] insertSql = strategy.buildInsertSQLString(recordSetCommand.getRecords()[i],importMeta.getCrudMeta());
		    for ( int j = 0, jSize = insertSql.length; j < jSize; j++ )
		    {
		        crudDao.doUpdate(insertSql[j]);
		        insertSql[j] = null;
		    }
		    
		    insertSql = null;
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
