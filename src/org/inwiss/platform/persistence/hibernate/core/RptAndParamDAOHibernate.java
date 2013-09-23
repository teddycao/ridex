package org.inwiss.platform.persistence.hibernate.core;

import org.inwiss.platform.model.core.RptAndParam;
import org.inwiss.platform.persistence.core.RptAndParamDAO;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;

public class RptAndParamDAOHibernate extends LocalizableDAOHibernate implements RptAndParamDAO {

	public RptAndParamDAOHibernate() {
	}

	@Override
	public void createRptAndParam(RptAndParam rptAndParam)
			throws BeanAlreadyExistsException {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(rptAndParam);
	}


	@Override
	public void deleteRptAndParam(RptAndParam rptAndParam) throws BeanNotFoundException {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(rptAndParam);
	}


	@Override
	public RptAndParam retrieveRptAndParam(Long interId) {
		// TODO Auto-generated method stub
		return (RptAndParam) getHibernateTemplate().get(RptAndParam.class, interId);
	}


	@Override
	public void updateRptAndParam(RptAndParam rptAndParam) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(rptAndParam);
	}
	
	public void removeFromCache(Object object) {
        if (object != null)
		    getHibernateTemplate().evict(object);
	}
	
}
