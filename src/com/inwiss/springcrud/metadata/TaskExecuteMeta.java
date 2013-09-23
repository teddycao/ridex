package com.inwiss.springcrud.metadata;

import java.util.List;

public class TaskExecuteMeta {
	/**执行任务的URL http://localhost:8080/pdiserver/kettle/expert/*/
	protected String excuteURI = null;
	/**监控任务的URL*/
	protected String statusURI = null;
	protected String folderName = "/";
	/**需要的任务名称*/
	protected String taskName = null;
	protected String sysRunDate = null;
	/**任务的日志级别*/
	protected String logLevel = "Basic";
	protected boolean isJob = false;
	protected List<Object> params;
	

	/**
	 * @return the excuteURI
	 */
	public String getExcuteURI() {
		return excuteURI;
	}
	/**
	 * @param excuteURI the excuteURI to set
	 */
	public void setExcuteURI(String excuteURI) {
		this.excuteURI = excuteURI;
	}
	/**
	 * @return the statusURI
	 */
	public String getStatusURI() {
		return statusURI;
	}
	/**
	 * @param statusURI the statusURI to set
	 */
	public void setStatusURI(String statusURI) {
		this.statusURI = statusURI;
	}
	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}
	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return the sysRunDate
	 */
	public String getSysRunDate() {
		return sysRunDate;
	}
	/**
	 * @param sysRunDate the sysRunDate to set
	 */
	public void setSysRunDate(String sysRunDate) {
		this.sysRunDate = sysRunDate;
	}
	/**
	 * @return the logLevel
	 */
	public String getLogLevel() {
		return logLevel;
	}
	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	/**
	 * @return the isJob
	 */
	public boolean isJob() {
		return isJob;
	}
	/**
	 * @param isJob the isJob to set
	 */
	public void setJob(boolean isJob) {
		this.isJob = isJob;
	}
	
	/**
	 * @return the params
	 */
	public List<Object> getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(List<Object> params) {
		this.params = params;
	}
	
	
}
