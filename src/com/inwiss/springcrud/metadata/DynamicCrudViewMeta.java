/*
 * Created on 2005-8-14
 */
package com.inwiss.springcrud.metadata;



/**
 * 
 * @author
 */
public class DynamicCrudViewMeta
{
   
    /**window dlg对应的宽度*/
    private String dlgWidth = "500";
    
    /**window dlg对应的高度*/
    private String dlgHeight = "400";

	/**展现使用的CSS文件名*/
    private String cssFileName;
    
    /**是否出现数据过滤窗口*/
    private boolean filterWindowEnabled = true;
    
    /**每行显示几个过滤条件*/
    private int filterControlsPerRow = 4;
    
    /**数据列表对应的Table的宽度设置*/
    private String listTableWidth = "100%";
    
    /**是否显示编辑整页按钮*/
    private boolean editPageEnabled = true;
    
    /**是否显示批量编辑按钮*/
    private boolean editBatchEnabled = true;
    
    /**是否显示编辑单条记录的按钮*/
    private boolean editSingleEnabled = true;
    
    /**是否显示查看单条记录的按钮*/
    private boolean viewSingleEnabled = true;    
    
    /**是否显示删除批量数据的按钮，同时包括每条记录之前的checkbox*/
    private boolean deleteBatchEnabled = true;
    
    /**是否显示删除单条记录按钮*/
    private boolean deleteSingleEnabled = true;
    
    /**是否显示新增按钮*/
    private boolean addEnabled = true;
    
    /**是否显示上传按钮*/
    private boolean dataImportEnabled = false;
    
    /**是否允许导出为TXT文件*/
    private boolean exportAsTxtEnabled = false;
    
    /**是否允许导出为Excel文件*/
    private boolean exportAsExcelEnabled = false;
    
    /**是否允许导出为CSV文件*/
    private boolean exportAsCsvEnabled = false;
    
    public DynamicCrudViewMeta(CrudViewMeta theMeta)
    {
        if ( theMeta != null )
        {
	        this.cssFileName = theMeta.getCssFileName();        
	        this.filterControlsPerRow = theMeta.getFilterControlsPerRow();
	        
	        this.addEnabled = theMeta.isAddEnabled();
	        this.viewSingleEnabled = theMeta.isViewSingleEnabled();
	        this.deleteBatchEnabled = theMeta.isDeleteBatchEnabled();
	        this.deleteSingleEnabled = theMeta.isDeleteSingleEnabled();
	        this.editPageEnabled = theMeta.isEditPageEnabled();
	        this.editSingleEnabled = theMeta.isEditSingleEnabled();
	        this.filterWindowEnabled = theMeta.isFilterWindowEnabled();
	        this.dataImportEnabled = theMeta.isDataImportEnabled();
	        
	        this.exportAsTxtEnabled = theMeta.isExportAsTxtEnabled();
	        this.exportAsExcelEnabled = theMeta.isExportAsExcelEnabled();
	        this.exportAsCsvEnabled = theMeta.isExportAsCsvEnabled();
	        this.dlgWidth = theMeta.getDlgWidth();
	        this.dlgHeight = theMeta.getDlgHeight();
        }
    }
    /**
     * @return Returns the addEnabled.
     */
    public boolean isAddEnabled()
    {
        return addEnabled;
    }
    /**
     * @param addEnabled The addEnabled to set.
     */
    public void setAddEnabled(boolean addEnabled)
    {
        this.addEnabled = addEnabled;
    }
    /**
     * @return Returns the cssFileName.
     */
    public String getCssFileName()
    {
        return cssFileName;
    }
    /**
     * @param cssFileName The cssFileName to set.
     */
    public void setCssFileName(String cssFileName)
    {
        this.cssFileName = cssFileName;
    }
    /**
     * @return Returns the deleteBatchEnabled.
     */
    public boolean isDeleteBatchEnabled()
    {
        return deleteBatchEnabled;
    }
    /**
     * @param deleteBatchEnabled The deleteBatchEnabled to set.
     */
    public void setDeleteBatchEnabled(boolean deleteEnabled)
    {
        this.deleteBatchEnabled = deleteEnabled;
    }
    /**
     * @return Returns the editBatchEnabled.
     */
    public boolean isEditBatchEnabled()
    {
        return editBatchEnabled;
    }
	/**
	 * @return Returns the viewSingleEnabled.
	 */
	public boolean isViewSingleEnabled() {
		return viewSingleEnabled;
	}    
    /**
     * @param editBatchEnabled The editBatchEnabled to set.
     */
    public void setEditBatchEnabled(boolean editBatchEnabled)
    {
        this.editBatchEnabled = editBatchEnabled;
    }
    /**
     * @return Returns the editSingleEnabled.
     */
    public boolean isEditSingleEnabled()
    {
        return editSingleEnabled;
    }
    /**
     * @param editSingleEnabled The editSingleEnabled to set.
     */
    public void setEditSingleEnabled(boolean editSingleEnabled)
    {
        this.editSingleEnabled = editSingleEnabled;
    }
    /**
     * @return Returns the filterControlsPerRow.
     */
    public int getFilterControlsPerRow()
    {
        return filterControlsPerRow;
    }
    /**
     * @param filterControlsPerRow The filterControlsPerRow to set.
     */
    public void setFilterControlsPerRow(int filterControlsPerRow)
    {
        this.filterControlsPerRow = filterControlsPerRow;
    }
    /**
     * @return Returns the filterWindowEnabled.
     */
    public boolean isFilterWindowEnabled()
    {
        return filterWindowEnabled;
    }
    /**
     * @param filterWindowEnabled The filterWindowEnabled to set.
     */
    public void setFilterWindowEnabled(boolean filterWindowEnabled)
    {
        this.filterWindowEnabled = filterWindowEnabled;
    }
    /**
     * @return Returns the listTableWidth.
     */
    public String getListTableWidth()
    {
        return listTableWidth;
    }
    /**
     * @param listTableWidth The listTableWidth to set.
     */
    public void setListTableWidth(String listTableWidth)
    {
        this.listTableWidth = listTableWidth;
    }
	/**
	 * @return Returns the deleteSingleEnabled.
	 */
	public boolean isDeleteSingleEnabled() {
		return deleteSingleEnabled;
	}
	/**
	 * @param deleteSingleEnabled The deleteSingleEnabled to set.
	 */
	public void setDeleteSingleEnabled(boolean deleteSingleEnabled) {
		this.deleteSingleEnabled = deleteSingleEnabled;
	}
	/**
	 * @return Returns the editPageEnabled.
	 */
	public boolean isEditPageEnabled() {
		return editPageEnabled;
	}
	/**
	 * @param editPageEnabled The editPageEnabled to set.
	 */
	public void setEditPageEnabled(boolean editPageEnabled) {
		this.editPageEnabled = editPageEnabled;
	}
	/**
	 * @param viewSingleEnabled The viewSingleEnabled to set.
	 */
	public void setViewSingleEnabled(boolean viewSingleEnabled) {
		this.viewSingleEnabled = viewSingleEnabled;
	}
	/**
	 * @return Returns the dataImportEnabled.
	 */
	public boolean isDataImportEnabled() {
		return dataImportEnabled;
	}
	/**
	 * @param dataImportEnabled The dataImportEnabled to set.
	 */
	public void setDataImportEnabled(boolean uploadEnabled) {
		this.dataImportEnabled = uploadEnabled;
	}
	
    /**
     * @return Returns the exportAsCsvEnabled.
     */
    public boolean isExportAsCsvEnabled()
    {
        return exportAsCsvEnabled;
    }
    /**
     * @param exportAsCsvEnabled The exportAsCsvEnabled to set.
     */
    public void setExportAsCsvEnabled(boolean exportAsCsvEnabled)
    {
        this.exportAsCsvEnabled = exportAsCsvEnabled;
    }
    /**
     * @return Returns the exportAsExcelEnabled.
     */
    public boolean isExportAsExcelEnabled()
    {
        return exportAsExcelEnabled;
    }
    /**
     * @param exportAsExcelEnabled The exportAsExcelEnabled to set.
     */
    public void setExportAsExcelEnabled(boolean exportAsExcelEnabled)
    {
        this.exportAsExcelEnabled = exportAsExcelEnabled;
    }
    /**
     * @return Returns the exportAsTxtEnabled.
     */
    public boolean isExportAsTxtEnabled()
    {
        return exportAsTxtEnabled;
    }
    /**
     * @param exportAsTxtEnabled The exportAsTxtEnabled to set.
     */
    public void setExportAsTxtEnabled(boolean exportAsTxtEnabled)
    {
        this.exportAsTxtEnabled = exportAsTxtEnabled;
    }
	public String getDlgWidth() {
		return dlgWidth;
	}
	public void setDlgWidth(String dlgWidth) {
		this.dlgWidth = dlgWidth;
	}
	
	public String getDlgHeight() {
		return dlgHeight;
	}
	
	public void setDlgHeight(String dlgHeight) {
		this.dlgHeight = dlgHeight;
	}
}
