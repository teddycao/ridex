/*
 *  Copyright 2004 Blandware (http://www.blandware.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inwiss.platform.persistence.hibernate.core;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.*;

import org.inwiss.platform.persistence.core.PermissionDAO;
import org.inwiss.platform.persistence.core.UserDAO;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 * </p>
 * <p><a href="UserDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.31 $ $Date: 2008/06/23 13:24:54 $
 */
public class PermissionDAOHibernate extends BaseDAOHibernate implements PermissionDAO {

 
  
	// ~ CRUD Methods ================================================================

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#createUser(org.inwiss.platform.model.vo.User)
	 */
	public void createPermission(Permission user) {
		getHibernateTemplate().save(user);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#retrieveUser(java.lang.String)
	 */
	public Permission retrievePermission(Long permid) {
		return (Permission) getHibernateTemplate().get(Permission.class, permid);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#updateUser(org.inwiss.platform.model.vo.User)
	 */
	public void updatePermission(Permission perm) {
		getHibernateTemplate().update(perm);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#deleteUser(org.inwiss.platform.model.vo.User)
	 */
	public void deletePermission(Permission perm) {
		getHibernateTemplate().delete(perm);

		// break relations between roles and deleted menu item
		List roles = new ArrayList(perm.getRoles());
		for ( int i = 0; i < roles.size(); i++ ) {
			Role role = (Role) roles.get(i);
			perm.removeRole(role);
		}
	}


	public PartialCollection listPermissions(QueryInfo queryInfo) {
		String whereClause = new String();
		String orderByClause = " order by item.position asc ";
		if ( queryInfo != null ) {
			whereClause = queryInfo.getWhereClause();
			if ( whereClause != null && whereClause.length() != 0 ) {
				whereClause = " and " + whereClause;
			}
		}

		List list = null;
		Integer total = null;
		Long ownerId = null;
		if ( queryInfo != null ) {
			ownerId = (Long) queryInfo.getQueryParameters().get("ownerId");
		}
		if ( log.isDebugEnabled() ) {
			log.debug("Owner ID: " + ownerId);
		}
		String hql = "select distinct item from Permission as item left outer join item.roles as role where";

		ArrayList args = new ArrayList();

	
		if ( ownerId != null ) {
			hql += " item.owner = ? ";
			args.add(ownerId);
		} else {
			hql += " item.owner is null";
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

	

	
}
