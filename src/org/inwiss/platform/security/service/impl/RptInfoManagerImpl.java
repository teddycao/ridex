package org.inwiss.platform.security.service.impl;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptAndParam;
import org.inwiss.platform.model.core.RptInfo;
import org.inwiss.platform.persistence.core.RptAndParamDAO;
import org.inwiss.platform.persistence.core.RptInfoDAO;
import org.inwiss.platform.security.service.RptInfoManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public class RptInfoManagerImpl extends BaseManagerImpl implements RptInfoManager {
	
	protected RptInfoDAO rptInfoDAO;
	
	protected RptAndParamDAO rptAndParamDAO;

	public RptInfoDAO getRptInfoDAO() {
		return rptInfoDAO;
	}

	public void setRptInfoDAO(RptInfoDAO rptInfoDAO) {
		this.rptInfoDAO = rptInfoDAO;
	}

	public RptAndParamDAO getRptAndParamDAO() {
		return rptAndParamDAO;
	}

	public void setRptAndParamDAO(RptAndParamDAO rptAndParamDAO) {
		this.rptAndParamDAO = rptAndParamDAO;
	}

	@Override
	public PartialCollection listRptInfoItems(QueryInfo queryInfo) {
		return rptInfoDAO.listRptInfoItems(queryInfo);
	}
	
	@Override
	public PartialCollection listRptInfotrees(QueryInfo queryInfo) {
		return rptInfoDAO.listRptInfotrees(queryInfo);
	}

	@Override
	public void createRptInfo(RptInfo rptInfo)
			throws BeanAlreadyExistsException {
		if ( log.isDebugEnabled() ) {
			log.debug("Creating new Report Information (RptId=" + rptInfo.getRptId() + ")...");
		}
		
		String rptId = rptInfo.getRptId();

		rptInfoDAO.createRptInfo(rptInfo);
		
		long id = rptInfoDAO.getRptInterIdByRptId(rptId);
		RptAndParam rptAndParam = new RptAndParam();
		rptAndParam.setRptInfoInterId(id);
		rptAndParam.setRptId(rptInfo.getRptId());
		
		rptAndParamDAO.createRptAndParam(rptAndParam);
		
		if ( log.isDebugEnabled() ) {
			log.debug("New Report Information has been created succefully.");
		}
	}

	@Override
	public void deleteRptInfo(Long id) throws BeanNotFoundException {
		RptInfo rptInfo = rptInfoDAO.retrieveRptInfo(id);
		if ( rptInfo == null ) {
			String errorMessage = "No Report Information with interId=" + id + " could be found";
			throw new BeanNotFoundException(errorMessage);
		}
		
		RptAndParam rptAndParam = new RptAndParam();
		rptAndParam.setRptInfoInterId(id);
		rptAndParam.setRptId(rptInfo.getRptId());

		rptInfoDAO.deleteRptInfo(rptInfo);
		rptAndParamDAO.deleteRptAndParam(rptAndParam);
		
        if ( log.isDebugEnabled() ) {
            log.debug("Deleted Report Information with rptId=" + rptInfo.getRptId());
        }
	}

	@Override
	public RptInfo retrieveRptInfo(Long id) {
		RptInfo rptInfo = null;
		rptInfo = rptInfoDAO.retrieveRptInfo(id);
		return rptInfo;
	}

	@Override
	public void updateRptInfo(RptInfo rptInfo) {
		rptInfoDAO.removeFromCache(rptInfo);
		if ( log.isDebugEnabled() ) {
			log.debug("Updating Report Information (rptId=" + rptInfo.getRptId() + ")...");
		}

		long id = rptInfo.getId();
		RptAndParam rptAndParam = new RptAndParam();
		rptAndParam.setRptInfoInterId(id);
		rptAndParam.setRptId(rptInfo.getRptId());
		
		rptInfoDAO.updateRptInfo(rptInfo);
		rptAndParamDAO.updateRptAndParam(rptAndParam);

		if ( log.isDebugEnabled() ) {
			log.debug("Report Information was updated succefully.");
		}
	}
}
