package com.inwiss.springcrud.support.excelinput;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.inwiss.springcrud.command.ImportDataRecordCommand;
import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.command.RecordSetCommand;
import com.inwiss.springcrud.dataaccess.CrudDao;
import com.inwiss.springcrud.dataaccess.CrudPersistentStrategy;
import com.inwiss.springcrud.dataaccess.CrudPersistentStrategyImpl;
import com.inwiss.springcrud.entity.spreadsheet.KCell;
import com.inwiss.springcrud.entity.spreadsheet.KSheet;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.ImportMeta;
import com.inwiss.springcrud.support.excelinput.jxl.XLSWorkbook;
import com.inwiss.springcrud.support.txtimport.UploadFileResolver;
import com.inwiss.springcrud.util.GeneralUtil;
import com.inwiss.springcrud.util.UploadConfigBean;

public class ExceDirectResolverImpl  implements UploadFileResolver  {

	Logger logger = LoggerFactory.getLogger(ExceDirectResolverImpl.class);
	@Autowired
	protected UploadConfigBean uploadConfigBean;
	
	/**CRUD的DAO对象*/
	@Autowired
	private CrudDao crudDao;
	
	@Override
	public synchronized Map resolve(MultipartFile fileCommand, ImportMeta importMeta) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		String fileName = fileCommand.getOriginalFilename();
		//文件名和账单日期
		Map<String,String> addtionMap = getPhoneBillDate(fileName);
		//data.workbook = WorkbookFactory.getWorkbook(meta.getSpreadSheetType(), data.filename, meta.getEncoding());
		ExcelImportMeta excelImportMeta = importMeta.getExcelImportMeta();
		CrudPersistentStrategy persistent = new CrudPersistentStrategyImpl();
		int startRow = excelImportMeta.getStartRow();
		String maskEndRow = excelImportMeta.getMaskEndRow();
		
		try {
			XLSWorkbook workbook = new XLSWorkbook(fileCommand.getInputStream(),"UTF-8");
			
			KSheet sheet = workbook.getSheet(0);
			//GeneralUtil.translateVariables(maskEndRow,"rownum",sheet.getRows());
			// 得到单元格
			for (int rownr = startRow; rownr < sheet.getRows()-1; rownr++) {
					KCell[] cell = sheet.getRow(rownr);
					RecordCommand  record = buildRecordCommand(cell,importMeta);
					record.getMapContent().putAll(addtionMap);
					persistentImportData(record,importMeta,persistent);
			}
			
			workbook.close();
			modelMap.put("message", "数据导入成功.");
			modelMap.put("success", true);
			modelMap.putAll(addtionMap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return modelMap;
	}
	
	
	/**
	 *   文件名称:1380917xxxx[201109]_语音_清单.xls
	 * @param fileName
	 * @return 1380917xxxx,201109
	 */
	private Map getPhoneBillDate(String fileName){
		Map<String,String> dataMap = new HashMap<String,String>(2);
		String phone = fileName.substring(0,11);
		String date =  fileName.substring(12, 18);
		dataMap.put("MSISDN", phone);
		dataMap.put("BILL_DATE", date);
		logger.debug(phone +"-->"+date);
		return dataMap;
	}
	/**
	 * 
	 * @param recordSetCommand
	 * @param importMeta
	 * @param strategy
	 */
	public void persistentImportData(RecordCommand  record , ImportMeta importMeta,CrudPersistentStrategy strategy ) 
	{
			String[] insertSql = strategy.buildInsertSQLString(record,importMeta.getCrudMeta());
		    for ( int j = 0, jSize = insertSql.length; j < jSize; j++ )
		    {
		        crudDao.doUpdate(insertSql[j]);
		        insertSql[j] = null;
		    }
		    
		    insertSql = null;
		
	}
	/**
	 * jxl读取最后一行报警告:并且最后一行数据读不到
	* Warning:  Cell A506 exceeds defined cell boundaries in Dimension record (15x505)
	*Warning:  Cell B506 exceeds defined cell boundaries in Dimension record (15x505)
	*Warning:  Cell C506 exceeds defined cell boundaries in Dimension record (15x505)
	*Warning:  Cell D506 exceeds defined cell boundaries in Dimension record (15x505)
	*导致，数据没法读入到数据库中，后来发现只要在最后一列的下一列或者下下列等等，只要是在最后一列的后面的任何一列写一个字就可以成功的把数据读入到数据库中，但是这里有几百了excel呢，总不能手动在每一个excel的最后一列的下一列加上一个字吧，崩溃了。
	*最后想出一个方法，就是用下面的方法在最后一列添加一个字，虽然在调用这个方法的时候，还是会上面的waring，但是在读取excel内容到数据库中的时候，倒是可以正确的实现，哎真是服了jxl啦
	* @param filePath
	 */
	 public synchronized static void addCell(String filePath) {
		  File file = new File(filePath);
		  FileInputStream fileInputStream = null;
		  Workbook workbook = null;
		  WritableWorkbook writableWorkbook = null;
		  try {
		   fileInputStream = new FileInputStream(file);
		   workbook = Workbook.getWorkbook(fileInputStream);
		   writableWorkbook = Workbook.createWorkbook(new File(filePath), workbook);
		   WritableSheet writableSheet = writableWorkbook.getSheet(0);
		   jxl.write.Number   label=new jxl.write.Number(15,2,0);
		   writableSheet.addCell(label);
		   
		   writableWorkbook.write();
		   writableWorkbook.close();
		   workbook.close();
		   fileInputStream.close();
		  } catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (BiffException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (WriteException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		 }
	/**
	 * 
	 * @param cell
	 * @param importMeta
	 * @return
	 */
	protected  RecordCommand buildRecordCommand(KCell[] cell,ImportMeta importMeta){
		ColumnMeta[] allColList = importMeta.getCrudMeta().getColumnMetas();
		//不包含要导入的列
		Map<String,Integer> exColMap = importMeta.getExcludeColumnNames();
		List<ColumnMeta> colList = removeExcludeCols(Arrays.asList(allColList),exColMap);
		
		String name = importMeta.getCrudMeta().getBeanName();
		RecordCommand recordCommand = new RecordCommand();
		recordCommand.setName(name);
		
		for (int i = 0; i < colList.size(); i++) {
			if(colList.get(i) != null){
			String colName = colList.get(i).getColName();
			String colVal = cell[i].getContents();
			//logger.debug(colName +"-->"+colVal);
			recordCommand.getMapContent().put(colName,colVal);
			}
		}
		//String msisdn = (String) importMeta.getParams().get("msisdn");
		//recordCommand.getMapContent().put("MSISDN",msisdn);
		return recordCommand;
	}
	
	/**
	 * 去除不包含的列名
	 * @param colList
	 * @param exColMap
	 * @return
	 */
    private List<ColumnMeta> removeExcludeCols(List<ColumnMeta> allCols,Map<String,Integer> exColMap){
    	
    	List<ColumnMeta> finalList = new NoNullsList<ColumnMeta>();
    	finalList.addAll(allCols);
    	List<ColumnMeta> tempList = new ArrayList<ColumnMeta>();
    	
    	for (int i = 0; i < allCols.size(); i++){
    		String colName = allCols.get(i).getColName();
    		if(exColMap.get(colName) != null && (exColMap.get(colName).intValue() != 0)){
    			int index = exColMap.get(colName).intValue();
    			if((index-1) == i){//index != 0 表示在被过滤的列中
    				tempList.add(allCols.get(i));
    			}
    			
    		}
    	}
    	 
    	finalList.removeAll(tempList);
    	return  finalList;
    }
    
    
    
    

	@Override
	public ImportDataRecordCommand[] resolve(InputStream inputStream,ImportMeta importMeta) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	protected class Test2 {

		public void testjxl() {
			try {
				Workbook book = Workbook.getWorkbook(new File("测试.xls"));
				// 获得第一个工作表对象
				Sheet sheet = book.getSheet(0);
				// 得到单元格
				for (int i = 0; i < sheet.getColumns(); i++) {
					for (int j = 0; j < sheet.getRows(); j++) {
						Cell cell = sheet.getCell(i, j);
						System.out.print(cell.getContents() + "  ");
					}
					System.out.println();
				}
				book.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public class NoNullsList<E> extends ArrayList<E> {

		  public void add(int index, E element) {
		    if (element != null) {
		      super.add(index, element);
		    }
		  }

		  public boolean add(E e) {
		    return e == null ? false : super.add(e);
		  }
		}
}
