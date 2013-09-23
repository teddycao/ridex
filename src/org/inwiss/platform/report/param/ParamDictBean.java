package org.inwiss.platform.report.param;

import java.io.Serializable;
import java.util.*;

public class ParamDictBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5709572089850942798L;
	
	protected String paramSql = null;
	protected String code = null;
	protected String type = null;
	protected String defaultVal = null;

	protected String datasource = null;

	protected Collection<EntryHashMap> entryList = null;

	public ParamDictBean(){
		super();
	}
	public ParamDictBean( String code, String type,
			String defaultVal, Collection<EntryHashMap> entrys) {
		super();
		this.code = code;
		this.type = type;
		this.defaultVal = defaultVal;
		this.entryList = entrys;
	}
	
	public ParamDictBean(String code, String type,
			String defaultVal,String datasource) {
		super();
		this.code = code;
		this.type = type;
		this.defaultVal = defaultVal;
		this.datasource = datasource;
		
	}
	
	public String getParamSql() {
		return paramSql;
	}
	public void setParamSql(String param) {
		this.paramSql = param;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	
	
	/**
	 * @return the entryList
	 */
	public Collection<EntryHashMap> getEntryList() {
		return entryList;
	}
	/**
	 * @param entryList the entryList to set
	 */
	public void setEntryList(Collection<EntryHashMap> entryList) {
		this.entryList = entryList;
	}
	
	/**
	 * @return the datasource
	 */
	public String getDatasource() {
		return datasource;
	}
	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParamDictBean [paramSql=" + paramSql + ", code=" + code
				+ ", type=" + type + ", defaultVal=" + defaultVal
				+ ", datasource=" + datasource + ", entryList=" + entryList
				+ "]";
	}
	
}
