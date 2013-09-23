/*
 * Created on 2005-8-31
 */
package com.inwiss.springcrud.metadata;


import java.util.List;
import java.util.Map;


import com.inwiss.springcrud.support.excelinput.ExcelImportMeta;
import com.inwiss.springcrud.support.txtimport.ImportDataPersistentStrategy;
import com.inwiss.springcrud.support.txtimport.TableBackuper;
import com.inwiss.springcrud.support.txtimport.UploadFileResolver;


/**
 * 文本导入相关信息的元数据定义类。
 * 
 * @author

 */
public class ImportMeta 
{
	/**字段分隔符*/
	protected String delimiter;
	
	/**元数据信息*/
	protected CrudMeta crudMeta;
	
	/**导入文件中包含的列名集合*/
	protected List columnNames;
	
	/**导入文件中不包含的列名集合,与columnNames互斥*/
	protected Map<String,Integer> excludeColumnNames;
	
	protected Map<String,Object> params;

	/**使用的文件解析器*/
	protected UploadFileResolver fileResolver;
	
	protected TaskExecuteMeta teskExcuteMeta;

	/**导入数据持续化策略*/
	protected ImportDataPersistentStrategy importDataPersistentStrategy;
	
	protected ExcelImportMeta excelImportMeta;
	

	/**备份表名*/
	protected String backupTableName;
	
	/**表数据备份处理类*/
	protected TableBackuper tableBackuper;
	

	/**
	 * @return Returns the crudMeta.
	 */
	public CrudMeta getCrudMeta() {
		return crudMeta;
	}
	/**
	 * @param crudMeta The crudMeta to set.
	 */
	public void setCrudMeta(CrudMeta crudMeta) {
		this.crudMeta = crudMeta;
	}

	
	/**
	 * @return Returns the delimiter.
	 */
	public String getDelimiter() {
		return delimiter;
	}
	/**
	 * @param delimiter The delimiter to set.
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * @return Returns the backupTableName.
	 */
	public String getBackupTableName() {
		return backupTableName;
	}
	/**
	 * @param backupTableName The backupTableName to set.
	 */
	public void setBackupTableName(String backupTableName) {
		this.backupTableName = backupTableName;
	}

	/**
	 * @return Returns the tableBackuper.
	 */
	public TableBackuper getTableBackuper() {
		return tableBackuper;
	}
	/**
	 * @param tableBackuper The tableBackuper to set.
	 */
	public void setTableBackuper(TableBackuper tableBackuper) {
		this.tableBackuper = tableBackuper;
	}
	
	
 
    /**
     * @return Returns the importDataPersistentStrategy.
     */
    public ImportDataPersistentStrategy getImportDataPersistentStrategy()
    {
        return importDataPersistentStrategy;
    }
    /**
     * @param importDataPersistentStrategy The importDataPersistentStrategy to set.
     */
    public void setImportDataPersistentStrategy(
            ImportDataPersistentStrategy importDataPersistentStrategy)
    {
        this.importDataPersistentStrategy = importDataPersistentStrategy;
    }
    
	/**
	 * @return the fileResolver
	 */
	public UploadFileResolver getFileResolver() {
		return fileResolver;
	}
	/**
	 * @param fileResolver the fileResolver to set
	 */
	public void setFileResolver(UploadFileResolver fileResolver) {
		this.fileResolver = fileResolver;
	}

	/**
	 * @return the teskExcuteMeta
	 */
	public TaskExecuteMeta getTeskExcuteMeta() {
		return teskExcuteMeta;
	}
	/**
	 * @param teskExcuteMeta the teskExcuteMeta to set
	 */
	public void setTeskExcuteMeta(TaskExecuteMeta teskExcuteMeta) {
		this.teskExcuteMeta = teskExcuteMeta;
	}

	/**
	 * @return the excludeColumnNames
	 */
	public Map<String,Integer> getExcludeColumnNames() {
		return excludeColumnNames;
	}
	/**
	 * @param excludeColumnNames the excludeColumnNames to set
	 */
	public void setExcludeColumnNames(Map<String,Integer> excludeColumnNames) {
		this.excludeColumnNames = excludeColumnNames;
	}
	
	/**
	 * @return the excelImportMeta
	 */
	public ExcelImportMeta getExcelImportMeta() {
		return excelImportMeta;
	}
	/**
	 * @param excelImportMeta the excelImportMeta to set
	 */
	public void setExcelImportMeta(ExcelImportMeta excelImportMeta) {
		this.excelImportMeta = excelImportMeta;
	}
	
	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
