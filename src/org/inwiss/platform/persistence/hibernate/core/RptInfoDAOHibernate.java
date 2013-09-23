package org.inwiss.platform.persistence.hibernate.core;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.RptInfo;
import org.inwiss.platform.persistence.core.RptInfoDAO;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;

public class RptInfoDAOHibernate extends LocalizableDAOHibernate implements RptInfoDAO {

	/**
	 * Creates new instance of MenuDAOHibernate
	 */
	public RptInfoDAOHibernate() {
	}

	
	public PartialCollection listRptInfotrees(QueryInfo queryInfo) {
		String whereClause = new String();
		String orderByClause = " order by item.id asc, item.rptId asc";
		if ( queryInfo != null ) {
			whereClause = queryInfo.getWhereClause();
			if ( whereClause != null && whereClause.length() != 0 ) {
				whereClause = " and " + whereClause;
			}
		}

		List list = null;
		Integer total = null;
		Long parentItemId = null;
		//Long ownerId = null;
		if ( queryInfo != null ) {
			parentItemId = (Long) queryInfo.getQueryParameters().get("parentItemId");
			//ownerId = (Long) queryInfo.getQueryParameters().get("ownerId");
		}
		if ( log.isDebugEnabled() ) {
			log.debug("Parent ID: " + parentItemId);
			//log.debug("Owner ID: " + ownerId);
		}
		String hql = "select distinct item from RptInfo as item where";

		ArrayList args = new ArrayList();

		if ( parentItemId != null ) {
			hql += " item.parentItem.id = ?";
			args.add(parentItemId);
		} else {
			hql += " item.parentItem is null";
		}

		/*if ( ownerId != null ) {
			hql += " and item.owner.id = ?";
			args.add(ownerId);
		} else {
			hql += " and item.owner is null";
		}*/

		hql += whereClause + orderByClause;

		list = executeFind(hql, args.toArray());

		if ( list == null ) {
			list = new ArrayList();
		} else {
			total = new Integer(list.size());
		}

		return new PartialCollection(list, total);

	}
	
	public PartialCollection listRptInfoItems(QueryInfo queryInfo) {
		String whereClause = new String();
		String orderByClause = " order by item.id asc, item.rptId asc";
		if ( queryInfo != null ) {
			whereClause = queryInfo.getWhereClause();
			if ( whereClause != null && whereClause.length() != 0 ) {
				whereClause = " and " + whereClause;
			}
		}

		List list = null;
		Integer total = null;
		//Long parentItemId = null;
		//Long ownerId = null;
		/*if ( queryInfo != null ) {
			parentItemId = (Long) queryInfo.getQueryParameters().get("parentItemId");
		}
		if ( log.isDebugEnabled() ) {
			log.debug("Parent ID: " + parentItemId);
		}*/
		
		//String hql = "select distinct item from RptInfo as item where";		
		String hql = "select distinct item from RptInfo as item ";

		ArrayList args = new ArrayList();

		/*if ( parentItemId != null ) {
			hql += " item.parentItem.id = ?";
			args.add(parentItemId);
		} else {
			hql += " item.parentItem is null";
		}*/
		
		if(!("").equals(whereClause)){
			hql += " where ";
		}

		hql += whereClause + orderByClause;

		list = executeFind(hql, args.toArray());

		if ( list == null ) {
			list = new ArrayList();
		} else {
			total = new Integer(list.size());
		}

		return new PartialCollection(list, total);

	}


	@Override
	public void createRptInfo(RptInfo rptInfo)
			throws BeanAlreadyExistsException {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(rptInfo);
	}


	@Override
	public void deleteRptInfo(RptInfo rptInfo) throws BeanNotFoundException {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(rptInfo);
	}


	@Override
	public RptInfo retrieveRptInfo(Long id) {
		// TODO Auto-generated method stub
		return (RptInfo) getHibernateTemplate().get(RptInfo.class, id);
	}


	@Override
	public void updateRptInfo(RptInfo rptInfo) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(rptInfo);
	}
	
	public Long getRptInterIdByRptId(String rptId) {
		String hql = "from RptInfo r where r.rptId = ?";
		List list = executeFind(hql, new Object[]{rptId}, new Type[]{StandardBasicTypes.STRING});
		RptInfo rptInfo = (RptInfo)list.get(0);
		long id = rptInfo.getId();
		return id;
	}

}
