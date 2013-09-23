/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.metadata;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.inwiss.springcrud.dataaccess.CrudPersistentStrategy;
import com.inwiss.springcrud.datafilter.DataFilter;
import com.inwiss.springcrud.support.dictionary.DictFactory;


/**
 * 支持CRUD操作的实体的定义元数据。
 * 
 * <p><code>CrudMeta</code>本质上是一个Value Object，它定义了一个实体需要进行CRUD操作时所需的所有元数据。
 * 
 * <P>一般，我们如果需要提供针对一个模型的CRUD操作，则需要定义一个<code>CrudMeta</code>对象。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class CrudMeta implements BeanNameAware
{
    /**标题*/
    private String title = null;
    
    /**模型的表名，可以是一个子查询语句，但是这样的话，某些操作会受到限制。*/
    private String tableName = null;
    
    /**取数据时OrderBy的字段名称*/
    private String orderByFieldName = null;
    
    /**是否按升序进行OrderBy*/
    private boolean orderByAsc = true;
    
    /**包括的列定义集合*/
    private ColumnMeta[] columnMetas = null;
    
    /**相应的展现元数据*/
    private CrudViewMeta crudViewMeta = null;
    
    /**嵌套后的缺省数据过滤器*/
    private DataFilter chainedDefaultFilter = null;
    
    /**缺省的数据过滤器*/
    private DataFilter[] defaultDataFilters = null;
    
    /**字典工厂*/
    private DictFactory dictFactory = null;
    
    /**crudMeta bean的名称*/
    private String beanName = null;
    
    /**实现CRUD持续化操作的策略接口，如果为空，则系统会使用默认的实现*/
    private CrudPersistentStrategy crudPersistentStrategy = null;
    
    /**列表页的view名称*/
	private String listView = null;
	
	/**新增页的view名称*/
	private String addView = null;
	
	/**批量编辑页的view名称*/
	private String editPageView = null;
	
	/**编辑单条记录页的view名称*/
	private String editSingleView = null;
	
	/**查看单条记录页的view名称*/
	private String viewSingleView = null;	
	
	/**删除数据的确认view名称*/
	private String deleteConfirmView = null;
	
	/**批量更新的确认view名称*/
	private String updateBatchConfirmView = null;
	
	/**文本导入的view名称*/
	private String importView = null;
	
	/**文本导入确认的view名称*/
	private String importConfirmView = null;
	
	/**成功上传文件的结果view名称*/
	private String uploadFileResultView = null;
	
	/**成功上传文件的view名称*/
	private String uploadFileView = null;
	
	/**定义是否允许文件上传功能*/
	private boolean allowUploadFile = false;


	/**导出为TXT的View*/
	private String exportAsTxtView = null;
	
	/**导出为CSV的View*/
	private String exportAsCsvView = null;
	
	/**导出为EXCEL的View*/
	private String exportAsExcelView = null;
	
	private ButtonForUpdateBatchMeta[] buttonForUpdateBatchs = null;
	
	/**其他需要的参数，这些参数需要在不同的View之间传递下去*/
	private String[] otherWantedParameters = null;
    
    /**初始化方法，完成缺省的数据过滤器的嵌套逻辑，以及字典工厂的设置逻辑*/
    public void init()
    {
        //如果用户没有配置CrudViewMeta，则应用缺省对象
        if ( this.getCrudViewMeta() == null )
            this.setCrudViewMeta(new CrudViewMeta());
        
        //应用缺省数据过滤器的逻辑
        if ( defaultDataFilters == null || defaultDataFilters.length == 0 )
            chainedDefaultFilter = null;
        else
        {
	        DataFilter dataFilter = null;
	        for ( int i = 0; i < defaultDataFilters.length; i++ )
	        {
	            if ( i == 0 )
	                dataFilter = defaultDataFilters[0];
	            else
	                dataFilter = dataFilter.setChainedFilter(defaultDataFilters[i]);
	        }
	        chainedDefaultFilter = defaultDataFilters[0];
        }
        
        
        //设置DictFactory
        for ( int i = 0; i < columnMetas.length; i++ )
        {
            ColumnMeta columnMeta = columnMetas[i];
            if ( StringUtils.hasText(columnMeta.getDictName()) )
            {
                columnMeta.setDictFactory(dictFactory);
            }
        }
    }
    
    /**
     * 获取和colName对应的<code>ColumnMeta</code>对象。
     * @param colName 字段名
     * @return 保存在集合中字段名为传入值的<code>ColumnMeta</code>对象。
     */
    public ColumnMeta getColumnMetaByColName(String colName)
    {
        Assert.hasText(colName);    
        for ( int i = 0; i < columnMetas.length; i++ )
        {
            if ( colName.equals(columnMetas[i].getColName()) )
                return columnMetas[i];
        }
        return null;
    }
    
    /**
     * @return Returns the columnMetas.
     */
    public ColumnMeta[] getColumnMetas()
    {
        return columnMetas;
    }
    /**
     * @param columnMetas The columnMetas to set.
     */
    public void setColumnMetas(ColumnMeta[] columnMetas)
    {
        this.columnMetas = columnMetas;
    }
    
    /**
     * @return Returns the orderByAsc.
     */
    public boolean isOrderByAsc()
    {
        return orderByAsc;
    }
    /**
     * @param orderByAsc The orderByAsc to set.
     */
    public void setOrderByAsc(boolean orderByAsc)
    {
        this.orderByAsc = orderByAsc;
    }
    /**
     * @return Returns the orderByFieldName.
     */
    public String getOrderByFieldName()
    {
        return orderByFieldName;
    }
    /**
     * @param orderByFieldName The orderByFieldName to set.
     */
    public void setOrderByFieldName(String orderByFieldName)
    {
        this.orderByFieldName = orderByFieldName;
    }
    /**
     * @return Returns the crudViewMeta.
     */
    public CrudViewMeta getCrudViewMeta()
    {
        return crudViewMeta;
    }
    /**
     * @param crudViewMeta The crudViewMeta to set.
     */
    public void setCrudViewMeta(CrudViewMeta crudViewMeta)
    {
        this.crudViewMeta = crudViewMeta;
    }
    /**
     * @return Returns the chainedDefaultFilter.
     */
    public DataFilter getChainedDefaultFilter()
    {
        return chainedDefaultFilter;
    }
    /**
     * @param defaultDataFilters The defaultDataFilters to set.
     */
    public void setDefaultDataFilters(DataFilter[] defaultDataFilters)
    {
        this.defaultDataFilters = defaultDataFilters;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * @return Returns the tableName.
     */
    public String getTableName()
    {
        return tableName;
    }
    /**
     * @param tableName The tableName to set.
     */
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    
    /**
     * @return Returns the crudPersistentStrategy.
     */
    public CrudPersistentStrategy getCrudPersistentStrategy()
    {
        return crudPersistentStrategy;
    }
    /**
     * @param crudPersistentStrategy The crudPersistentStrategy to set.
     */
    public void setCrudPersistentStrategy(
            CrudPersistentStrategy crudPersistentStrategy)
    {
        this.crudPersistentStrategy = crudPersistentStrategy;
    }
    /**
     * @param dictFactory The dictFactory to set.
     */
    public void setDictFactory(DictFactory dictFactory)
    {
        this.dictFactory = dictFactory;
    }

	/**
	 * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
	 */
	public void setBeanName(String name) 
	{
		this.beanName = name;
	}
	
	public String getBeanName()
	{
		return this.beanName;
	}
	
    /**
     * @return Returns the addView.
     */
    public String getAddView()
    {
        return addView;
    }
    /**
     * @param addView The addView to set.
     */
    public void setAddView(String addView)
    {
        this.addView = addView;
    }
    /**
     * @return Returns the deleteConfirmView.
     */
    public String getDeleteConfirmView()
    {
        return deleteConfirmView;
    }
    /**
     * @param deleteConfirmView The deleteConfirmView to set.
     */
    public void setDeleteConfirmView(String deleteConfirmView)
    {
        this.deleteConfirmView = deleteConfirmView;
    }
    
    /**
     * @return Returns the updateBatchConfirmView.
     */
    public String getUpdateBatchConfirmView()
    {
        return updateBatchConfirmView;
    }
    /**
     * @param updateBatchConfirmView The updateBatchConfirmView to set.
     */
    public void setUpdateBatchConfirmView(String updateBatchConfirmView)
    {
        this.updateBatchConfirmView = updateBatchConfirmView;
    }
    /**
     * @return Returns the importView.
     */
    public String getImportView()
    {
        return importView;
    }
    /**
     * @param importView The importView to set.
     */
    public void setImportView(String deleteSingleConfirmView)
    {
        this.importView = deleteSingleConfirmView;
    }
    /**
     * @return Returns the editPageView.
     */
    public String getEditPageView()
    {
        return editPageView;
    }
    /**
     * @param editPageView The editPageView to set.
     */
    public void setEditPageView(String editPageView)
    {
        this.editPageView = editPageView;
    }
    /**
     * @return Returns the editSingleView.
     */
    public String getEditSingleView()
    {
        return editSingleView;
    }
    /**
     * @param editSingleView The editSingleView to set.
     */
    public void setEditSingleView(String editSingleView)
    {
        this.editSingleView = editSingleView;
    }
    /**
     * @return Returns the listView.
     */
    public String getListView()
    {
        return listView;
    }
    /**
     * @param listView The listView to set.
     */
    public void setListView(String listView)
    {
        this.listView = listView;
    }
    /**
     * @return Returns the uploadFileResultView.
     */
    public String getUploadFileResultView()
    {
        return uploadFileResultView;
    }
    /**
     * @param uploadFileResultView The uploadFileResultView to set.
     */
    public void setUploadFileResultView(String uploadFileResultView)
    {
        this.uploadFileResultView = uploadFileResultView;
    }
    /**
     * @return Returns the importConfirmView.
     */
    public String getImportConfirmView()
    {
        return importConfirmView;
    }
    /**
     * @param importConfirmView The importConfirmView to set.
     */
    public void setImportConfirmView(String uploadFileView)
    {
        this.importConfirmView = uploadFileView;
    }
    /**
     * @return Returns the viewSingleView.
     */
    public String getViewSingleView()
    {
        return viewSingleView;
    }
    /**
     * @param viewSingleView The viewSingleView to set.
     */
    public void setViewSingleView(String viewSingleView)
    {
        this.viewSingleView = viewSingleView;
    }
    
    /**
     * @return Returns the exportAsCsvView.
     */
    public String getExportAsCsvView()
    {
        return exportAsCsvView;
    }
    /**
     * @param exportAsCsvView The exportAsCsvView to set.
     */
    public void setExportAsCsvView(String exportAsCsvView)
    {
        this.exportAsCsvView = exportAsCsvView;
    }
    /**
     * @return Returns the exportAsExcelView.
     */
    public String getExportAsExcelView()
    {
        return exportAsExcelView;
    }
    /**
     * @param exportAsExcelView The exportAsExcelView to set.
     */
    public void setExportAsExcelView(String exportAsExcelView)
    {
        this.exportAsExcelView = exportAsExcelView;
    }
    /**
     * @return Returns the exportAsTxtView.
     */
    public String getExportAsTxtView()
    {
        return exportAsTxtView;
    }
    /**
     * @param exportAsTxtView The exportAsTxtView to set.
     */
    public void setExportAsTxtView(String exportAsTxtView)
    {
        this.exportAsTxtView = exportAsTxtView;
    }
    
    /**
     * @return Returns the buttonForUpdateBatchs.
     */
    public ButtonForUpdateBatchMeta[] getButtonForUpdateBatchs()
    {
        return buttonForUpdateBatchs;
    }
    /**
     * @param buttonForUpdateBatchs The buttonForUpdateBatchs to set.
     */
    public void setButtonForUpdateBatchs(
            ButtonForUpdateBatchMeta[] buttonForUpdateBatchs)
    {
        this.buttonForUpdateBatchs = buttonForUpdateBatchs;
    }
    /**
     * @return Returns the otherWantedParameters.
     */
    public String[] getOtherWantedParameters()
    {
        return otherWantedParameters;
    }
    /**
     * @param otherWantedParameters The otherWantedParameters to set.
     */
    public void setOtherWantedParameters(String[] otherWantedParameters)
    {
        this.otherWantedParameters = otherWantedParameters;
    }
    
	/**
	 * @return the uploadFileView
	 */
	public String getUploadFileView() {
		return uploadFileView;
	}

	/**
	 * @param uploadFileView the uploadFileView to set
	 */
	public void setUploadFileView(String uploadFileView) {
		this.uploadFileView = uploadFileView;
	}
	
	
	/**
	 * @return the allowUploadFile
	 */
	public boolean isAllowUploadFile() {
		return allowUploadFile;
	}

	/**
	 * @param allowUploadFile the allowUploadFile to set
	 */
	public void setAllowUploadFile(boolean allowUploadFile) {
		this.allowUploadFile = allowUploadFile;
	}
}
