package org.inwiss.platform.security.service;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptInfo;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public interface RptInfoManager extends BaseManager {
	
	public PartialCollection listRptInfoItems(QueryInfo queryInfo);
	
	public PartialCollection listRptInfotrees(QueryInfo queryInfo);

	public void createRptInfo(RptInfo rptInfo) throws BeanAlreadyExistsException;

	public RptInfo retrieveRptInfo(Long id);

	public void updateRptInfo(RptInfo rptInfo);

	public void deleteRptInfo(Long id) throws BeanNotFoundException;

}
