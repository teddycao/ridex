/*
 * Created on 2005-8-30
 */
package com.inwiss.springcrud.command;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 文件上传Command对象。
 * 
 * @author
 */
public class FileUploadCommand 
{
    /**CrudMeta名字，以便在某些场合可以根据这个名字获取CrudMeta的定义*/
	private String name;
	
    /**Spring提供的上传文件封装类的对象*/
	private CommonsMultipartFile file;
	
	/**解析后的Command对象*/
	private ImportDataRecordCommand[] records;
	
    /**
     * @return Returns the file.
     */
    public CommonsMultipartFile getFile()
    {
        return file;
    }
    /**
     * @param file The file to set.
     */
    public void setFile(CommonsMultipartFile file)
    {
        this.file = file;
    }
    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * @return Returns the records.
     */
    public ImportDataRecordCommand[] getRecords()
    {
        return records;
    }
    /**
     * @param records The records to set.
     */
    public void setRecords(ImportDataRecordCommand[] records)
    {
        this.records = records;
    }
}
