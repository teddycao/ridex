package com.inwiss.apps.fee.persistence;

import org.springframework.stereotype.Service;

import com.inwiss.apps.fee.model.CellStation;
import com.inwiss.apps.fee.model.StationsReport;


public interface StationsDAO {
	
	public void updateStationsReport(StationsReport sr);
	
	public void insertCellStation(CellStation cs);
	
	public int isExist(String ciDec);

}
