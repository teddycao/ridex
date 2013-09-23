package org.inwiss.platform.persistence.core;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptParam;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public interface RptParamDAO  extends DAO {

	public void createRptParam(RptParam rptParam) throws BeanAlreadyExistsException;

	public RptParam retrieveRptParam(Long id);

	public void updateRptParam(RptParam rptParam);

	public void deleteRptParam(RptParam rptParam) throws BeanNotFoundException;
	
	public PartialCollection loadRptParamByRptId(QueryInfo queryInfo);
	
	public void removeFromCache(Object object);
}
