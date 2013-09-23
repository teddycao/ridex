package com.inwiss.springcrud.support.excelinput.jxl;

import java.io.File;
import java.io.InputStream;

import com.inwiss.springcrud.entity.spreadsheet.KSheet;
import com.inwiss.springcrud.entity.spreadsheet.KWorkbook;
import com.inwiss.springcrud.util.StringUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;



public class XLSWorkbook implements KWorkbook {

  private Workbook workbook;
  private String filename;
  private String encoding;

  public XLSWorkbook(String filename, String encoding) throws Exception {
    this.filename = filename;
    this.encoding = encoding;
    
    WorkbookSettings ws = new WorkbookSettings();
    if (!StringUtils.isEmpty(encoding))
    {
        ws.setEncoding(encoding);
    }
    try {
      workbook = Workbook.getWorkbook(new File(filename), ws);
    } catch(Exception e) {
      throw new Exception(e);
    }
  }
  
  /**
   * 
   * @param inStream
   * @param encoding
   * @throws Exception
   */
  public XLSWorkbook(InputStream inStream, String encoding) throws Exception {
	    //this.filename = filename;
	    this.encoding = encoding;
	    
	    WorkbookSettings ws = new WorkbookSettings();
	    if (!StringUtils.isEmpty(encoding))
	    {
	        ws.setEncoding(encoding);
	    }
	    try {
	      workbook = Workbook.getWorkbook(inStream, ws);
	    } catch(Exception e) {
	      throw new Exception(e);
	    }
	  }
  
  
  
  public void close() {
    workbook.close();
  }
  
  @Override
  public KSheet getSheet(String sheetName) {
    Sheet sheet = workbook.getSheet(sheetName);
    if (sheet==null) return null;
    return new XLSSheet(sheet);
  }
  

  
  public String[] getSheetNames() {
    return workbook.getSheetNames();
  }
  
  public String getFilename() {
    return filename;
  }
  
  public String getEncoding() {
    return encoding;
  }
  
  public int getNumberOfSheets() {
    return workbook.getNumberOfSheets();
  }
  
  public KSheet getSheet(int sheetNr) {
    Sheet sheet = workbook.getSheet(sheetNr);
    if (sheet==null) return null;
    return new XLSSheet(sheet);
  }
}
