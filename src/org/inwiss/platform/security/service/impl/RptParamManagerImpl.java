package org.inwiss.platform.security.service.impl;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptParam;
import org.inwiss.platform.persistence.core.RptParamDAO;
import org.inwiss.platform.security.service.RptParamManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public class RptParamManagerImpl extends BaseManagerImpl implements RptParamManager {
	
	protected RptParamDAO rptParamDAO;	

	public RptParamDAO getRptParamDAO() {
		return rptParamDAO;
	}

	public void setRptParamDAO(RptParamDAO rptParamDAO) {
		this.rptParamDAO = rptParamDAO;
	}

	@Override
	public void createRptParam(RptParam rptParam)
			throws BeanAlreadyExistsException {
		// TODO Auto-generated method stub
		rptParamDAO.createRptParam(rptParam);
	}

	@Override
	public void deleteRptParam(Long id) throws BeanNotFoundException {
		// TODO Auto-generated method stub
		RptParam rptParam = rptParamDAO.retrieveRptParam(id);
		if ( rptParam == null ) {
			String errorMessage = "No Report Param with RptId=" + rptParam.getRptId() + " could be found";
			throw new BeanNotFoundException(errorMessage);
		}
		rptParamDAO.deleteRptParam(rptParam);
	}

	@Override
	public PartialCollection loadRptParamByRptId(QueryInfo queryInfo) {
		// TODO Auto-generated method stub
		return rptParamDAO.loadRptParamByRptId(queryInfo);
	}

	@Override
	public RptParam retrieveRptParam(Long id) {
		// TODO Auto-generated method stub
		RptParam rptParam = null;
		rptParam = rptParamDAO.retrieveRptParam(id);
		return rptParam;
	}

	@Override
	public void updateRptParam(RptParam rptParam) {
		// TODO Auto-generated method stub
		rptParamDAO.removeFromCache(rptParam);
		if ( log.isDebugEnabled() ) {
			log.debug("Updating Report Param (rptId=" + rptParam.getRptId() + ")...");
		}
		rptParamDAO.updateRptParam(rptParam);
		if ( log.isDebugEnabled() ) {
			log.debug("Report Param was updated succefully.");
		}
	}

}
