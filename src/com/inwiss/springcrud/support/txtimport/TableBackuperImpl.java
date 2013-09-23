/*
 * Created on 2005-9-13
 */
package com.inwiss.springcrud.support.txtimport;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.inwiss.springcrud.dataaccess.TableBackupDao;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.ImportMeta;

/**
 * 备份表数据到备份表操作实现类。
 * 
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class TableBackuperImpl implements TableBackuper 
{
	private static final Log logger = LogFactory.getLog(TableBackuperImpl.class);

	private TableBackupDao tableBackupDao;
	
	/**备份表中用于保存备份日期的字段名*/
	private String backupDateColName = "BACKUPDATE";
	
	/**备份表中保存日期信息所使用的pattern*/
	private String datePattern = "yyyyMMdd";
	
	/**
	 * @see com.inwiss.springcrud.support.txtimport.TableBackuper#backup(com.inwiss.springcrud.metadata.ImportMeta)
	 */
	public void backup(ImportMeta importMeta) 
	{

		StringBuffer columnNameStrBuffer = new StringBuffer();
		ColumnMeta[] columnMetas = importMeta.getCrudMeta().getColumnMetas();
		
		for(int i=0;i<columnMetas.length;i++)
		{
			if(!columnMetas[i].isDerived())
			{
			    columnNameStrBuffer.append( "," + columnMetas[i].getColName() );
			}
		}
		
		//构造插入目标表的表名、字段名，如"insert into targetTable (BACKUPDATE, COL1, COL2)"
		StringBuffer sqlBuffer = new StringBuffer("insert into " + importMeta.getBackupTableName() + " (BACKUPDATE");
		sqlBuffer.append(columnNameStrBuffer.toString() + ")");
		
		//构造源表的字段名，如" (select '20051015', COL1, COL2"
		String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		sqlBuffer.append(" (select '" + currentDate + "'" + columnNameStrBuffer.toString() );
		currentDate = null;
		
		//构造源表表名，如" from sourceTable)"
		sqlBuffer.append(" from " + importMeta.getCrudMeta().getTableName() + ")");
		
		columnNameStrBuffer = null;
		
		tableBackupDao.backupTable(sqlBuffer.toString());
	}

	
    /**
     * @param tableBackupDao The tableBackupDao to set.
     */
    public void setTableBackupDao(TableBackupDao uploadFileDao)
    {
        this.tableBackupDao = uploadFileDao;
    }
    
    
    /**
     * @param backupDateColName The backupDateColName to set.
     */
    public void setBackupDateColName(String backupDateColName)
    {
        this.backupDateColName = backupDateColName;
    }
    /**
     * @param datePattern The datePattern to set.
     */
    public void setDatePattern(String datePattern)
    {
        this.datePattern = datePattern;
    }
}
