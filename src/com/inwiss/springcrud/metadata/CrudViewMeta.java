/*
 * Created on 2005-8-10
 */
package com.inwiss.springcrud.metadata;

import com.inwiss.springcrud.support.RequestContextBooleanRetriever;

/**
 * CRUD展现方面的元数据类。
 * 
 * @author 
 */
public class CrudViewMeta
{   
	
    /**window dlg对应的宽度*/
    private String dlgWidth = "500";
    
    /**window dlg对应的高度*/
    private String dlgHeight = "400";
    
    /**展现使用的CSS文件名*/
    private String cssFileName;
    
    /**是否出现数据过滤窗口*/
    private boolean filterWindowEnabled = true;
    
    /**是否出现数据过滤窗口的定义规则*/
    private RequestContextBooleanRetriever filterWindowEnabledRuler;
    
    /**每行显示几个过滤条件*/
    private int filterControlsPerRow = 4;
    
    /**是否显示编辑整页按钮*/
    private boolean editPageEnabled = true;
    
    /**是否显示编辑整页按钮的规则*/
    private RequestContextBooleanRetriever editPageEnabledRuler;
    
    /**是否显示编辑单条记录的按钮*/
    private boolean editSingleEnabled = true;
    
    /**是否显示编辑单条记录的按钮的规则*/
    private RequestContextBooleanRetriever editSingleEnabledRuler;
    
    /**是否显示查看单条记录的按钮*/
    private boolean viewSingleEnabled = true;
    
    /**是否显示查看单条记录的按钮的规则*/
    private RequestContextBooleanRetriever viewSingleEnabledRuler;    
    
    /**是否显示删除批量数据的按钮，同时包括每条记录之前的checkbox*/
    private boolean deleteBatchEnabled = true;
    
    /**是否显示删除批量数据的按钮的规则*/
    private RequestContextBooleanRetriever deleteEnabledRuler;

    /**是否显示删除单条记录按钮*/
    private boolean deleteSingleEnabled = true;    

    /**是否显示删除单条记录按钮的规则*/
    private RequestContextBooleanRetriever deleteSingleEnabledRuler;

    /**是否显示新增按钮*/
    private boolean addEnabled = true;
    
    /**是否显示新增按钮的规则*/
    private RequestContextBooleanRetriever addEnabledRuler;
    
    /**是否显示上传按钮*/
    private boolean dataImportEnabled = false;
    
    /**是否显示上传按钮的规则*/
    private RequestContextBooleanRetriever dataImportEnabledRuler;
    
    /**是否允许导出为TXT文件*/
    private boolean exportAsTxtEnabled = false;
    
    /**是否允许导出为TXT文件的规则*/
    private RequestContextBooleanRetriever exportAsTxtEnabledRuler;
    
    /**是否允许导出为Excel文件*/
    private boolean exportAsExcelEnabled = false;
    
    /**是否允许导出为Excel文件的规则*/
    private RequestContextBooleanRetriever exportAsExcelEnabledRuler;
    
    /**是否允许导出为CSV文件*/
    private boolean exportAsCsvEnabled = false;
    
    /**是否允许导出为CSV文件的规则*/
    private RequestContextBooleanRetriever exportAsCsvEnabledRuler;
    
    public CrudViewMeta()
    {
        
    }
    
    public CrudViewMeta(CrudViewMeta theMeta)
    {

        this.cssFileName = theMeta.getCssFileName();
        this.filterControlsPerRow = theMeta.getFilterControlsPerRow();
        
        this.addEnabled = theMeta.isAddEnabled();
        this.deleteBatchEnabled = theMeta.isDeleteBatchEnabled();
        this.deleteSingleEnabled = theMeta.isDeleteSingleEnabled();
        this.editPageEnabled = theMeta.isEditPageEnabled();
        this.editSingleEnabled = theMeta.isEditSingleEnabled();
        this.filterWindowEnabled = theMeta.isFilterWindowEnabled();
        this.dataImportEnabled = theMeta.isDataImportEnabled();
        
        this.addEnabledRuler = theMeta.getAddEnabledRuler();
        this.deleteEnabledRuler = theMeta.getDeleteEnabledRuler();
        this.deleteSingleEnabledRuler = theMeta.getDeleteSingleEnabledRuler();
        this.editPageEnabledRuler = theMeta.getEditPageEnabledRuler();
        this.editSingleEnabledRuler = theMeta.getEditSingleEnabledRuler();
        this.filterWindowEnabledRuler = theMeta.getFilterWindowEnabledRuler();
        this.dataImportEnabledRuler = theMeta.getDataImportEnabledRuler();
        
        this.exportAsTxtEnabledRuler = theMeta.getExportAsTxtEnabledRuler();
        this.exportAsExcelEnabledRuler = theMeta.getExportAsExcelEnabledRuler();
        this.exportAsCsvEnabledRuler = theMeta.getExportAsCsvEnabledRuler();
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
     * @return Returns the addEnabledRuler.
     */
    public RequestContextBooleanRetriever getAddEnabledRuler()
    {
        return addEnabledRuler;
    }
    /**
     * @param addEnabledRuler The addEnabledRuler to set.
     */
    public void setAddEnabledRuler(
            RequestContextBooleanRetriever addEnabledRuler)
    {
        this.addEnabledRuler = addEnabledRuler;
    }
    /**
     * @return Returns the deleteEnabledRuler.
     */
    public RequestContextBooleanRetriever getDeleteEnabledRuler()
    {
        return deleteEnabledRuler;
    }
    /**
     * @param deleteEnabledRuler The deleteEnabledRuler to set.
     */
    public void setDeleteEnabledRuler(
            RequestContextBooleanRetriever deleteEnabledRuler)
    {
        this.deleteEnabledRuler = deleteEnabledRuler;
    }
    /**
     * @return Returns the editSingleEnabledRuler.
     */
    public RequestContextBooleanRetriever getEditSingleEnabledRuler()
    {
        return editSingleEnabledRuler;
    }
    /**
     * @param editSingleEnabledRuler The editSingleEnabledRuler to set.
     */
    public void setEditSingleEnabledRuler(
            RequestContextBooleanRetriever editSingleEnabledRuler)
    {
        this.editSingleEnabledRuler = editSingleEnabledRuler;
    }
    /**
     * @return Returns the filterWindowEnabledRuler.
     */
    public RequestContextBooleanRetriever getFilterWindowEnabledRuler()
    {
        return filterWindowEnabledRuler;
    }
    /**
     * @param filterWindowEnabledRuler The filterWindowEnabledRuler to set.
     */
    public void setFilterWindowEnabledRuler(
            RequestContextBooleanRetriever filterWindowEnabledRuler)
    {
        this.filterWindowEnabledRuler = filterWindowEnabledRuler;
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
	 * @return Returns the editPageEnabledRuler.
	 */
	public RequestContextBooleanRetriever getEditPageEnabledRuler() {
		return editPageEnabledRuler;
	}
	/**
	 * @param editPageEnabledRuler The editPageEnabledRuler to set.
	 */
	public void setEditPageEnabledRuler(
			RequestContextBooleanRetriever editPageEnabledRuler) {
		this.editPageEnabledRuler = editPageEnabledRuler;
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
	 * @return Returns the deleteSingleEnabledRuler.
	 */
	public RequestContextBooleanRetriever getDeleteSingleEnabledRuler() {
		return deleteSingleEnabledRuler;
	}
	/**
	 * @param deleteSingleEnabledRuler The deleteSingleEnabledRuler to set.
	 */
	public void setDeleteSingleEnabledRuler(
			RequestContextBooleanRetriever deleteSingleEnabledRuler) {
		this.deleteSingleEnabledRuler = deleteSingleEnabledRuler;
	}
	/**
	 * @return Returns the viewSingleEnabled.
	 */
	public boolean isViewSingleEnabled() {
		return viewSingleEnabled;
	}
	/**
	 * @param viewSingleEnabled The viewSingleEnabled to set.
	 */
	public void setViewSingleEnabled(boolean viewSingleEnabled) {
		this.viewSingleEnabled = viewSingleEnabled;
	}
	/**
	 * @return Returns the viewSingleEnabledRuler.
	 */
	public RequestContextBooleanRetriever getViewSingleEnabledRuler() {
		return viewSingleEnabledRuler;
	}
	/**
	 * @param viewSingleEnabledRuler The viewSingleEnabledRuler to set.
	 */
	public void setViewSingleEnabledRuler(
			RequestContextBooleanRetriever viewSingleEnabledRuler) {
		this.viewSingleEnabledRuler = viewSingleEnabledRuler;
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
	 * @return Returns the dataImportEnabledRuler.
	 */
	public RequestContextBooleanRetriever getDataImportEnabledRuler() {
		return dataImportEnabledRuler;
	}
	/**
	 * @param dataImportEnabledRuler The dataImportEnabledRuler to set.
	 */
	public void setDataImportEnabledRuler(
			RequestContextBooleanRetriever uploadEnabledRuler) {
		this.dataImportEnabledRuler = uploadEnabledRuler;
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
     * @return Returns the exportAsCsvEnabledRuler.
     */
    public RequestContextBooleanRetriever getExportAsCsvEnabledRuler()
    {
        return exportAsCsvEnabledRuler;
    }
    /**
     * @param exportAsCsvEnabledRuler The exportAsCsvEnabledRuler to set.
     */
    public void setExportAsCsvEnabledRuler(
            RequestContextBooleanRetriever exportAsCsvEnabledRuler)
    {
        this.exportAsCsvEnabledRuler = exportAsCsvEnabledRuler;
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
     * @return Returns the exportAsExcelEnabledRuler.
     */
    public RequestContextBooleanRetriever getExportAsExcelEnabledRuler()
    {
        return exportAsExcelEnabledRuler;
    }
    /**
     * @param exportAsExcelEnabledRuler The exportAsExcelEnabledRuler to set.
     */
    public void setExportAsExcelEnabledRuler(
            RequestContextBooleanRetriever exportAsExcelEnabledRuler)
    {
        this.exportAsExcelEnabledRuler = exportAsExcelEnabledRuler;
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
    /**
     * @return Returns the exportAsTxtEnabledRuler.
     */
    public RequestContextBooleanRetriever getExportAsTxtEnabledRuler()
    {
        return exportAsTxtEnabledRuler;
    }
    /**
     * @param exportAsTxtEnabledRuler The exportAsTxtEnabledRuler to set.
     */
    public void setExportAsTxtEnabledRuler(
            RequestContextBooleanRetriever exportAsTxtEnabledRuler)
    {
        this.exportAsTxtEnabledRuler = exportAsTxtEnabledRuler;
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
