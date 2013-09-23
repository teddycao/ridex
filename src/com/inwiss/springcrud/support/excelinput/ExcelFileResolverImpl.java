package com.inwiss.springcrud.support.excelinput;

import java.io.*;
import java.util.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


import org.apache.commons.fileupload.FileUploadException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.inwiss.platform.httpclient.KettleExcuteProxy;
import com.inwiss.platform.httpclient.ReqParam;
import com.inwiss.springcrud.command.ImportDataRecordCommand;
import com.inwiss.springcrud.metadata.ImportMeta;
import com.inwiss.springcrud.metadata.TaskExecuteMeta;
import com.inwiss.springcrud.support.txtimport.UploadFileResolver;
import com.inwiss.springcrud.util.UploadConfigBean;

public class ExcelFileResolverImpl  implements UploadFileResolver  {

	Logger logger = LoggerFactory.getLogger(ExcelFileResolverImpl.class);
	@Autowired
	protected UploadConfigBean uploadConfigBean;
	
	@Override
	public Map resolve(MultipartFile fileCommand, ImportMeta importMeta) {

		TaskExecuteMeta taskExcute = importMeta.getTeskExcuteMeta();
		Map statusMap = null;
		
		List<Map<String, Object>> params = new ArrayList();
		Map paraMap = new HashMap<String, Object>();
		try {
			ReqParam reqParam = new ReqParam();
			String taskExcuteURI = taskExcute.getExcuteURI();
			String folderName = taskExcute.getFolderName();
			String taskName = taskExcute.getTaskName();
			String sysRunDate = taskExcute.getSysRunDate();
			String logLevel = taskExcute.getLogLevel();
			//BeanUtils.copyProperties(taskExcute, reqParam);
			reqParam.setTransName(taskName);
			reqParam.setDirName(folderName);
			reqParam.setLogLevel(logLevel);
			//-----------------
			paraMap.put("transName", taskName);
			paraMap.put("dirName", folderName);
			paraMap.put("fileName", "linux.txt");
			
			params.add(paraMap);
			reqParam.setParams(params);
			this.uploadFileStoreDisk(fileCommand,importMeta);
			KettleExcuteProxy ketf = new KettleExcuteProxy();
			statusMap = ketf.fireExcuteTrans(reqParam);
			
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return statusMap;
	}
	
	
	
	
	/**
	 * 
	 * @param file
	 * @param doc
	 * @return
	 * @throws FileUploadException
	 * @throws IOException
	 */
	private String uploadFileStoreDisk(MultipartFile file,ImportMeta importMeta)
						throws FileUploadException, IOException {
		
		if(validateFile(file)){
			String filename = file.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")).toLowerCase();
			//String lastFileName = System.currentTimeMillis()+CalendarUtil.getRadomValue()+extName;
			
			String fileFullPath = "c:\\temp";
		
			FileCopyUtils.copy(file.getBytes(),new File(fileFullPath+File.separator+filename));
			
			return fileFullPath;
		}else{			
			return "{success:false,error:\"上传文件不符合规格\"}";
			//throw new FileUploadException("上传文件不符合规格");
		}
			
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	private boolean validateFile(MultipartFile file) {
		if(file.getSize() <= 0  || file.getSize() > 50000000) 
	               return false;
		String filename = file.getOriginalFilename();
		String extName = filename.substring(filename.lastIndexOf(".")).toLowerCase();
		Map<String,Boolean> allowMap = new HashMap<String,Boolean> ();
		allowMap.put("xls", true);
		if(allowMap.get(extName) != null && !allowMap.get(extName)){
			return false;
		}else{
			return true;
		}
		
	}





	@Override
	public ImportDataRecordCommand[] resolve(InputStream inputStream,ImportMeta importMeta) {
		// TODO Auto-generated method stub
		
		//data.workbook = WorkbookFactory.getWorkbook(meta.getSpreadSheetType(), data.filename, meta.getEncoding());
        
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
}
