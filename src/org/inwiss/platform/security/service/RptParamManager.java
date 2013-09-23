package org.inwiss.platform.security.service;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptParam;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public interface RptParamManager extends BaseManager {

	public void createRptParam(RptParam rptParam) throws BeanAlreadyExistsException;

	public RptParam retrieveRptParam(Long id);

	public void updateRptParam(RptParam rptParam);

	public void deleteRptParam(Long id) throws BeanNotFoundException;
	
	public PartialCollection loadRptParamByRptId(QueryInfo queryInfo);

}
