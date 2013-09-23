package org.inwiss.platform.persistence.core;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptInfo;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


public interface RptInfoDAO  extends DAO {
	
	public PartialCollection listRptInfoItems(QueryInfo queryInfo);
	
	public PartialCollection listRptInfotrees(QueryInfo queryInfo);

	public void createRptInfo(RptInfo rptInfo) throws BeanAlreadyExistsException;

	public RptInfo retrieveRptInfo(Long id);

	public void updateRptInfo(RptInfo rptInfo);

	public void deleteRptInfo(RptInfo rptInfo) throws BeanNotFoundException;
	
	public Long getRptInterIdByRptId(String rptId);
}
