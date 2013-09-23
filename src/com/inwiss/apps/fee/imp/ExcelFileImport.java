/**
 * 
 */
package com.inwiss.apps.fee.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * @author lvhq
 *
 */
public class ExcelFileImport implements FileImport {

	/* (non-Javadoc)
	 * @see org.inwiss.fee.imp.FileImport#fileImport(java.lang.String, java.io.File)
	 */
	@Override
	public void fileImport(String type, File file) {
		try {
			InputStream is = new FileInputStream(file);
			Workbook wb = Workbook.getWorkbook(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
