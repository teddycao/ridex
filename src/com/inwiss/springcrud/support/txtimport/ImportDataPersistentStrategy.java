/*
 * Created on 2005-9-2
 */
package com.inwiss.springcrud.support.txtimport;

import com.inwiss.springcrud.command.*;
import com.inwiss.springcrud.metadata.ImportMeta;

/**
 * 文件导入时应用的数据持续化接口。
 * 
 * @author 
 */
public interface ImportDataPersistentStrategy
{
    /**
     * 持续化导入数据。
     * @param recordSetCommand 导入数据值对象
     * @param importMeta 数据导入元数据定义对象
     */
	public void persistentImportData(RecordSetCommand recordSetCommand, ImportMeta importMeta);
}
