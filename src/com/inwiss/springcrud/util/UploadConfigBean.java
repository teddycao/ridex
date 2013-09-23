package com.inwiss.springcrud.util;

import java.util.Map;

/**
 * 
 * @author Raidery
 *
 */
public class UploadConfigBean {
	
	private Map<String, String> allowsMap = null;
	private String archivePath = null;

	private boolean isSaveDisk = true;

	public boolean isSaveDisk() {
		return isSaveDisk;
	}

	public void setSaveDisk(boolean isSaveDisk) {
		this.isSaveDisk = isSaveDisk;
	}

	public Map<String, String> getAllowsMap() {
		return allowsMap;
	}

	public void setAllowsMap(Map<String, String> allowsMap) {
		this.allowsMap = allowsMap;
	}

	public String getArchivePath() {
		return archivePath;
	}

	public void setArchivePath(String archivePath) {
		this.archivePath = archivePath;
	}


	
}
