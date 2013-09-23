package org.inwiss.platform.security.service;

import org.inwiss.platform.model.core.RptAndParam;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public interface RptAndParamManager extends BaseManager {

	public void createRptAndParam(RptAndParam rptAndParam) throws BeanAlreadyExistsException;

	public RptAndParam retrieveRptAndParam(Long interId);

	public void updateRptAndParam(RptAndParam rptAndParam);

	public void deleteRptAndParam(Long interId) throws BeanNotFoundException;

}
