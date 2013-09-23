/*
 * Created on 2005-8-30
 */
package com.inwiss.springcrud.support.txtimport;

import java.io.InputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.inwiss.springcrud.command.ImportDataRecordCommand;
import com.inwiss.springcrud.metadata.ImportMeta;




/**
 * 文件解析器接口，负责将上传的文件解析为行记录
 * 
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public abstract interface UploadFileResolver {

	
	/**
	 * 将上传的文件解析为FileRecordSetCommand对象，
	 * 并初步校验文件中记录的列数与ImportMeta中定义的列数是否相等，
	 * 不相等则将该记录置为Invalid状态
	 * @param inputStream 上传的文件对应的InputStream
	 * @param importMeta 元数据信息
	 * @return 导入数据记录集合
	 */
	public Map resolve(MultipartFile fileCommand,ImportMeta importMeta);
	
	/**
	 * 将上传的文件解析为FileRecordSetCommand对象，
	 * 并初步校验文件中记录的列数与ImportMeta中定义的列数是否相等，
	 * 不相等则将该记录置为Invalid状态
	 * @param inputStream 上传的文件对应的InputStream
	 * @param importMeta 元数据信息
	 * @return 导入数据记录集合
	 */
	public  ImportDataRecordCommand[] resolve(InputStream inputStream, ImportMeta importMeta);
	
	
	
}
