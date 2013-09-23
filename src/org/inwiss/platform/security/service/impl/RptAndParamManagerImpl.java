package org.inwiss.platform.security.service.impl;

import org.inwiss.platform.model.core.RptAndParam;
import org.inwiss.platform.persistence.core.RptAndParamDAO;
import org.inwiss.platform.security.service.RptAndParamManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public class RptAndParamManagerImpl extends BaseManagerImpl implements RptAndParamManager {
	
	protected RptAndParamDAO rptAndParamDAO;	

	public RptAndParamDAO getRptAndParamDAO() {
		return rptAndParamDAO;
	}

	public void setRptAndParamDAO(RptAndParamDAO rptAndParamDAO) {
		this.rptAndParamDAO = rptAndParamDAO;
	}

	@Override
	public void createRptAndParam(RptAndParam rptAndParam)
			throws BeanAlreadyExistsException {
		if ( log.isDebugEnabled() ) {
			log.debug("Creating new Report Info And Param (interId=" + rptAndParam.getRptInfoInterId() + ")...");
		}

		RptAndParam tmp = rptAndParamDAO.retrieveRptAndParam(rptAndParam.getRptInfoInterId());
		if ( tmp != null ) {
			String errorMessage = "Report Info And Param with RptInfoInterId '" + rptAndParam.getRptInfoInterId() + "' already exists";
			if ( log.isErrorEnabled() ) {
				log.error(errorMessage);
			}
			throw new BeanAlreadyExistsException(errorMessage);
		}

		rptAndParamDAO.createRptAndParam(rptAndParam);

		if ( log.isDebugEnabled() ) {
			log.debug("New Report Info And Param has been created succefully.");
		}
	}

	@Override
	public void deleteRptAndParam(Long interId) throws BeanNotFoundException {
		RptAndParam rptAndParam = rptAndParamDAO.retrieveRptAndParam(interId);
		if ( rptAndParam == null ) {
			String errorMessage = "No Report Info And Param with RptInfoInterId=" + interId + " could be found";
			throw new BeanNotFoundException(errorMessage);
		}

		rptAndParamDAO.deleteRptAndParam(rptAndParam);

        if ( log.isDebugEnabled() ) {
            log.debug("Deleted Report Info And Param with RptInfoInterId=" + rptAndParam.getRptInfoInterId());
        }
	}

	@Override
	public RptAndParam retrieveRptAndParam(Long interId) {
		// TODO Auto-generated method stub
		RptAndParam rptAndParam = null;
		rptAndParam = rptAndParamDAO.retrieveRptAndParam(interId);
		return rptAndParam;
	}

	@Override
	public void updateRptAndParam(RptAndParam rptAndParam) {
		rptAndParamDAO.removeFromCache(rptAndParam);
		if ( log.isDebugEnabled() ) {
			log.debug("Updating Report Info And Param (RptInfoInterId=" + rptAndParam.getRptInfoInterId() + ")...");
		}

		rptAndParamDAO.updateRptAndParam(rptAndParam);

		if ( log.isDebugEnabled() ) {
			log.debug("Report Info And Param was updated succefully.");
		}
	}

}
