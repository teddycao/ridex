/*
 * Created on 2005-8-4
 */
package com.inwiss.springcrud.command;

/**
 * 标识CRUD操作中一个结果集的Command对象。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class RecordSetCommand
{
    /**标识每条记录的Command对象集合*/
    private RecordCommand[] records;
    
    /**CrudMeta名字，以便在某些场合可以根据这个名字获取CrudMeta的定义*/
	private String name;
	
    /**
     * @return Returns the records.
     */
    public RecordCommand[] getRecords()
    {
        return records;
    }
    
    /**
     * @param records The records to set.
     */
    public void setRecords(RecordCommand[] records)
    {
        this.records = records;
    }
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
