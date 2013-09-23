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
import org.inwiss.platform.common.util.StringUtil;
import org.inwiss.platform.model.core.*;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.persistence.core.RoleDAO;
import org.inwiss.platform.persistence.core.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 * </p>
 * <p><a href="RoleDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 */
public class RoleDAOHibernate extends BaseDAOHibernate implements RoleDAO {

	  private static Logger logger = LoggerFactory.getLogger(RoleDAOHibernate.class);
    /**
     * DAO to work with users
     */
    protected UserDAO userDAO;

	/**
	 * @see org.inwiss.platform.persistence.core.RoleDAO#setUserDAO(org.inwiss.platform.persistence.core.UserDAO)
	 */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

	/**
	 * @see org.inwiss.platform.persistence.core.RoleDAO#createRole(org.inwiss.platform.model.core.Role)
	 */
	public void createRole(Role role) {
		getHibernateTemplate().save(role);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.RoleDAO#retrieveRole(java.lang.String)
	 */
	public Role retrieveRole(String rolename) {
		return (Role) getHibernateTemplate().get(Role.class, rolename);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.RoleDAO#updateRole(org.inwiss.platform.model.core.Role)
	 */
	public void updateRole(Role role) {
		logger.debug("Update Role:" +role.getName());
		getHibernateTemplate().update(role);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.RoleDAO#deleteRole(org.inwiss.platform.model.core.Role)
	 */
	public void deleteRole(Role role) {
        // break all relations between users and deleted role
        // TODO: this seems to be a bad way, because we just walk through all users
        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setWhereClause("(fra.role.name = '" + StringUtil.sqlEscape(role.getName()) + "')");
        PartialCollection users = userDAO.listUsers(queryInfo);
        for (Iterator i = users.iterator(); i.hasNext();) {
            User user = (User) i.next();
            user.removeRole(role);
        }

		getHibernateTemplate().delete(role);

		// break all relations between pages and deleted role
		/**
		 *  Comment by Raidery 2011-03-22 
		
		List pages = new ArrayList(role.getPages());
		for ( int i = 0; i < pages.size(); i++ ) {
			Page page = (Page) pages.get(i);
			page.removeRole(role);
		}

		// break all relations between content resources and deleted role
		List resources = new ArrayList(role.getResources());
		for ( int i = 0; i < resources.size(); i++ ) {
			ContentResource resource = (ContentResource) resources.get(i);
			resource.removeRole(role);
		}
		 */

		// break all relations between menu items and deleted role
		List menuItems = new ArrayList(role.getMenuItems());
		for ( int i = 0; i < menuItems.size(); i++ ) {
			MenuItem item = (MenuItem) menuItems.get(i);
			item.removeRole(role);
		}

		// break all relations between groups and deleted role
		List groups = new ArrayList(role.getGroups());
		for ( int i = 0; i < groups.size(); i++ ) {
			Group group = (Group) groups.get(i);
			group.removeRole(role);
		}
	}

	/**
	 * @see org.inwiss.platform.persistence.core.RoleDAO#listRoles(org.inwiss.platform.common.util.QueryInfo)
	 */
	public PartialCollection listRoles(QueryInfo queryInfo) {
		String whereClause = new String();
		String orderByClause = new String();
		Boolean fixed = null;
		if ( queryInfo != null ) {
			if ( queryInfo.getQueryParameters() != null ) {
				fixed = (Boolean) queryInfo.getQueryParameters().get("fixed");
			}
			whereClause = queryInfo.getWhereClause();
			orderByClause = queryInfo.getOrderByClause();
			if ( whereClause != null && whereClause.length() != 0 ) {
				whereClause = " where " + whereClause;
			} else {
				whereClause = new String();
			}
			if ( orderByClause != null && orderByClause.length() != 0 ) {
				orderByClause = " order by " + orderByClause;
			} else {
				orderByClause = new String();
			}
		}

		List list = null;
		Integer total = null;

		if ( fixed != null ) {
			if ( whereClause.length() != 0 ) {
				whereClause += " and ";
			} else {
				whereClause += " where ";
			}
			whereClause += "fixed = " + (fixed.booleanValue() ? "'T'" : "'F'");
		}
		String hqlPart = "from Role r" + whereClause;
		if ( queryInfo != null && (queryInfo.getLimit() != null || queryInfo.getOffset() != null) ) {
			String hqlForTotal = "select count(r.name) " + hqlPart;
			total = findUniqueIntegerResult(hqlForTotal);
			if ( total == null ) {
				total = new Integer(0);
			}
		}
        // If we don't have any info about the total number of results yet or
        // we know that there's something that will be found, then fetch data
		if ( total == null || total.intValue() > 0 ) {
			String hql = "select r " + hqlPart + orderByClause;
			list = executeFind(hql, queryInfo);
			if ( total == null ) {
				total = new Integer(list.size());
			}
		} else {
			list = new ArrayList();
		}
		return new PartialCollection(list, total);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.RoleDAO#hasDuplicates(org.inwiss.platform.model.core.Role)
	 */
	public boolean hasDuplicates(Role role) {

		ArrayList args = new ArrayList();
		args.add(role.getTitle());
		args.add(role.getName());

		String hql = "select count(r.name) from Role r where r.title = ? and r.name != ?";

		int count = (findUniqueIntegerResult(hql, args.toArray())).intValue();

		return count > 0;
	}

	// ~ Finders ================================================================

	/**
	 * @see org.inwiss.platform.persistence.core.RoleDAO#findRoleByTitle(java.lang.String)
	 */
	public Role findRoleByTitle(String title) {
		return (Role) findUniqueResult("from Role r where r.title = ?", new Object[]{title});
	}

}

