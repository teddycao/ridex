package org.inwiss.platform.report.param;

import java.io.Serializable;

import org.apache.commons.collections.FastHashMap;

public class EntryHashMap /** extends FastHashMap*/ implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2686296220519907016L;
	protected String key = null;
	protected String value = null;
	
	
	public EntryHashMap(){
		
	}
	public EntryHashMap(String _key, String _value) {
	
		this.key = _key;
		this.value = _value;
		//this.setFast(true);
		//this.put(_key, _value);
	}
	
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/*
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EntryConfig [key=" + key + ", value=" + value + "]";
	}
	
	
}

