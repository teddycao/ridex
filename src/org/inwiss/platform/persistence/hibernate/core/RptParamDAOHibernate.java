package org.inwiss.platform.persistence.hibernate.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptParam;
import org.inwiss.platform.persistence.core.RptParamDAO;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;

public class RptParamDAOHibernate extends LocalizableDAOHibernate implements RptParamDAO {

	public RptParamDAOHibernate() {
	}

	@Override
	public void createRptParam(RptParam rptParam)
			throws BeanAlreadyExistsException {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(rptParam);
	}


	@Override
	public void deleteRptParam(RptParam rptParam) throws BeanNotFoundException {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(rptParam);
	}


	@Override
	public RptParam retrieveRptParam(Long id) {
		// TODO Auto-generated method stub
		return (RptParam) getHibernateTemplate().get(RptParam.class, id);
	}

	@Override
	public void updateRptParam(RptParam rptParam) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(rptParam);
	}

	@Override
	public PartialCollection loadRptParamByRptId(QueryInfo queryInfo) {
		List list = null;
		Integer total = null;
		Map<String,String> queryParameters = queryInfo.getQueryParameters();
		String rptId = queryParameters.get("rptId");
		String hql = "from RptParam r where r.rptId = ?";
		list = executeFind(hql, new Object[]{rptId}, new Type[]{StandardBasicTypes.STRING});
		if(list == null){
			list = new ArrayList();
		}
		if ( total == null ) {
			total = new Integer(list.size());
		}

		return new PartialCollection(list, total);
	}
	
	public void removeFromCache(Object object) {
        if (object != null)
		    getHibernateTemplate().evict(object);
	}

}
