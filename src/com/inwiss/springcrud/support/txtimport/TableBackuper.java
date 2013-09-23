/*
 * Created on 2005-9-13
 */
package com.inwiss.springcrud.support.txtimport;

import com.inwiss.springcrud.metadata.ImportMeta;

/**
 * 备份参数表数据到专用的备份表的接口。
 * 
 * @author wuzixiu
 */
public interface TableBackuper 
{
	/**
	 * 备份参数表数据到备份表
	 * @param importMeta 数据导入元数据定义对象
	 */
	public void backup(ImportMeta importMeta);
	
}
