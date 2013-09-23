package org.inwiss.platform.persistence.core;

import org.inwiss.platform.model.core.RptAndParam;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public interface RptAndParamDAO  extends DAO {

	public void createRptAndParam(RptAndParam rptAndParam) throws BeanAlreadyExistsException;

	public RptAndParam retrieveRptAndParam(Long interId);

	public void updateRptAndParam(RptAndParam rptAndParam);

	public void deleteRptAndParam(RptAndParam rptAndParam) throws BeanNotFoundException;
	
	public void removeFromCache(Object object);
}
