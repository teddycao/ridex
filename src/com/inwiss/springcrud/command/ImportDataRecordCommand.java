/*
 * Created on 2005-8-31
 */
package com.inwiss.springcrud.command;

/**
 * 上传文本解析后，用于保存其中一条记录信息的Command类。
 *
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class ImportDataRecordCommand
{
    /**对应在上传文本中的序号*/
    private int lineNum;
    
	/**是否通过解析，即是否可以正确的分解为多列的值*/
	private boolean errorInParsing = false;
	
	/**记录的具体内容*/
	private String originalStr;
	
	/**正确分解上传文本中的一行数据后，对应的RecordCommand对象，这个Command对象还需要进一步被校验*/
	private RecordCommand recordCommand;	
	
	public ImportDataRecordCommand(int theLineNum, String theOriginalStr)
	{
	    this.errorInParsing = true;
	    this.lineNum = theLineNum;
	    this.originalStr = theOriginalStr;
	}
	
	public ImportDataRecordCommand(int theLineNum, RecordCommand theCommand)
	{
	    this.lineNum = theLineNum;
	    this.recordCommand = theCommand;
	}
	
    /**
     * @return Returns the errorInParsing.
     */
    public boolean isErrorInParsing()
    {
        return errorInParsing;
    }
    /**
     * @return Returns the lineNum.
     */
    public int getLineNum()
    {
        return lineNum;
    }
    /**
     * @return Returns the originalStr.
     */
    public String getOriginalStr()
    {
        return originalStr;
    }
    /**
     * @return Returns the recordCommand.
     */
    public RecordCommand getRecordCommand()
    {
        return recordCommand;
    }
}
