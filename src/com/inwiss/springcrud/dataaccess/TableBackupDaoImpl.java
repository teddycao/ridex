/*
 * Author:wuzixiu
 * 
 * Created on 2005-9-13
 */
package com.inwiss.springcrud.dataaccess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * @author wuzixiu
 *
 * Created on:2005-9-13
 */
public class TableBackupDaoImpl extends SqlSessionDaoSupport implements TableBackupDao 
{

	private static final Log logger = LogFactory.getLog(TableBackupDaoImpl.class);

	/**
	 * @see com.inwiss.springcrud.dataaccess.TableBackupDao#backupTable(java.lang.String)
	 */
	public void backupTable(String sql) 
	{
        
	    if ( logger.isDebugEnabled() )
            logger.debug("trying to backup table using SQL["+sql+"]");
        
	    getSqlSession().update("uploadFile.backupTable", sql);
        
	}
}
