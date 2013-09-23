/*
 * Created on 2005-8-31
 */
package com.inwiss.springcrud.support.txtimport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.inwiss.springcrud.command.ImportDataRecordCommand;
import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.metadata.ImportMeta;

/**
 * FileResolver实现类，Text文件解析器
 * 
 * @author Raidery
 */
public class UploadFileResolverImpl implements UploadFileResolver 
{
    private static final Log logger = LogFactory.getLog(UploadFileResolverImpl.class);

    /**
     * @see com.datawise.opencrud.txtimport.UploadFileResolver#resolve(java.io.InputStream, com.datawise.opencrud.metadata.ImportMeta)
     */
    public ImportDataRecordCommand[] resolve(InputStream inputStream, ImportMeta importMeta)
    {
        List listLine = new ArrayList();
        
        int lineNumber = 0;
        BufferedReader reader = null;
        
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            
            while ( (line = reader.readLine()) != null )
            {
                listLine.add(line);
                lineNumber++;
            }
        } 
        catch (Exception e)
        {
            throw new UploadFileResolveException("解析上传文件时发生异常", e);
        } 
        finally
        {
            try { reader.close(); } catch (IOException e1) { }
        }
        
        ImportDataRecordCommand[] recordCommands = new ImportDataRecordCommand[listLine.size()];

        for (int i = 0; i < listLine.size(); i++)
        {
            ImportDataRecordCommand fileRecordCommand = this.composeCommand(importMeta, (String)listLine.get(i), i);
            recordCommands[i] = fileRecordCommand;
        }

        return recordCommands;
    }
    
	private ImportDataRecordCommand composeCommand(ImportMeta importMeta,String line,int lineNumber){

		List colList = importMeta.getColumnNames();
		
		String[] columnNames = (String[])colList.toArray(new String[colList.size()]);
		String delimiter = importMeta.getDelimiter();
		String name = importMeta.getCrudMeta().getBeanName();
		
		String[] columnsValue = this.fullSplit(line,delimiter);
		
		if(columnsValue.length!=columnNames.length)
		{
			return new ImportDataRecordCommand(lineNumber,line);
		}
		else
		{
			RecordCommand recordCommand = new RecordCommand();
			recordCommand.setName(name);
			
			for(int i=0;i<columnNames.length;i++)
			{
				recordCommand.getMapContent().put(columnNames[i],columnsValue[i]);
			}
			
			return new ImportDataRecordCommand(lineNumber, recordCommand);			
		}

	}
	
	/**
	 * 根据分割符将String分割成字符数组。
	 * @param originalStr 原始字符串
	 * @param delimiter 分割符字符串
	 * @return 分割后的字符串数组
	 * @see org.springframework.util.StringUtils#split(java.lang.String, java.lang.String)
	 */
	private String[] fullSplit( String originalStr, String delimiter )
	{
		List list = new ArrayList();
		
		while(originalStr.indexOf(delimiter)>-1)
		{
			String[] doubleStr = StringUtils.split(originalStr,delimiter);
			list.add(doubleStr[0]);
			originalStr = doubleStr[1];
		}
		
		if( StringUtils.hasLength(originalStr) )
		{
			list.add(originalStr);
		}
		
		String[] splited = new String[list.size()];
		
		for(int i=0;i<list.size();i++)
		{
			splited[i] = (String)list.get(i);
		}
		
		return splited;
	}

	
	
	@Override
	public Map resolve(MultipartFile fileCommand,
			ImportMeta importMeta) {
		// TODO Auto-generated method stub
		return null;
	}

}