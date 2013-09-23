package com.inwiss.apps.fee.persistence.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.inwiss.platform.ibatis.dao.BaseIbatis3Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.inwiss.apps.fee.model.CellStation;
import com.inwiss.apps.fee.model.StationsReport;
import com.inwiss.apps.fee.persistence.StationsDAO;

@Service(value = "stationsDAO")
public class StationsDAOImpl extends BaseIbatis3Dao<StationsReport,java.lang.String> implements StationsDAO{
	

	
	@Override
	public void updateStationsReport(StationsReport sr) {
		this.getSqlSession().update("StationsReport.updateStationsReport",sr);
	}

	@Override
	public void insertCellStation(CellStation cs) {
		this.getSqlSession().insert("CellStation.insertCellStationOfReport", cs);
	}

	@Override
	public int isExist(String ciDec) {
		int count = ((Integer)this.getSqlSession().selectOne("CellStation.checkCellStationByCID", ciDec)).intValue();
		return count;
	}

}
