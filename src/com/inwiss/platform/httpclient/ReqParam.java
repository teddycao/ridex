package com.inwiss.platform.httpclient;

import java.util.List;
import java.util.Map;

public class ReqParam {
	private String transName = null;
	private String dirName = null;

	//Map<String,Object> params = new HashMap<String,Object>();
	private List<Map<String, Object>> params = null;
	private List<Map<String, Object>> variables = null;

	//public enum LogLevel { Nothing, Error,Minimal,Basic,Detailed,Debug,Rowlevel };

	private String logLevel = "Basic";

	
	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public List<Map<String, Object>> getParams() {
		return params;
	}

	public void setParams(List<Map<String, Object>> params) {
		this.params = params;
	}

	public List<Map<String, Object>> getVariables() {
		return variables;
	}

	public void setVariables(List<Map<String, Object>> variables) {
		this.variables = variables;
	}



	

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	

}


class Param {
	private String name =null;
	private String value =null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}

class Variable {
	private String name =null;
	private String value =null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

