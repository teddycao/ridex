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

import org.inwiss.platform.persistence.core.UserDAO;
import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
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
public class UserDAOHibernate extends BaseDAOHibernate implements UserDAO {

 
    /**
     * Sets DAO to work with statistics.
     *
     * @param statisticsDAO DAO to set
    
	 protected StatisticsDAO statisticsDAO = null;
    public void setStatisticsDAO(StatisticsDAO statisticsDAO) {
        this.statisticsDAO = statisticsDAO;
    }
     */
	
	
	// U S E R

	// ~ CRUD Methods ================================================================

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#createUser(org.inwiss.platform.model.vo.User)
	 */
	public void createUser(User user) {
		getHibernateTemplate().save(user);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#retrieveUser(java.lang.String)
	 */
	public User retrieveUser(String userName) {
		return (User) getHibernateTemplate().get(User.class, userName);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#updateUser(org.inwiss.platform.model.vo.User)
	 */
	public void updateUser(User user) {
		getHibernateTemplate().update(user);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#deleteUser(org.inwiss.platform.model.vo.User)
	 */
	public void deleteUser(User user) {
        // break relations between roles and deleted user
        List rolesAssociation = new ArrayList(user.getRolesAssociations());
        for ( int i = 0; i < rolesAssociation.size(); i++ ) {
            UserRoleAssociation userRoleAssociation = (UserRoleAssociation) rolesAssociation.get(i);
            Role role = userRoleAssociation.getRole();
            Group group = userRoleAssociation.getGroup();
            user.removeRole(role, group);
        }

        // break relations with visits
        /*
         * Raidery comment 2011-03-22
        List visits = statisticsDAO.listUserVisits(user.getName());
        for (int i = 0; i < visits.size(); i++) {
            Visit visit = (Visit) visits.get(i);
            visit.setUser(null);
            statisticsDAO.updateVisit(visit);
        }
           */

        // remove cookies
        deleteUserCookies(user.getName());

        getHibernateTemplate().delete(user);

		// break relations between groups and deleted user
		List groups = new ArrayList(user.getGroups());
		for ( int i = 0; i < groups.size(); i++ ) {
			Group group = (Group) groups.get(i);
			group.removeUser(user);
		}
	}

	// ~ Additional methods ================================================================

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#listUsers(org.inwiss.platform.common.util.QueryInfo)
	 */
	public PartialCollection listUsers(QueryInfo queryInfo) {

		String whereClause = new String();
		String orderByClause = new String();
        Map queryParameters = new HashMap();
        boolean freeRolesFilterEnabled = false;
		if ( queryInfo != null ) {
			whereClause = queryInfo.getWhereClause();
			orderByClause = queryInfo.getOrderByClause();
            queryParameters = queryInfo.getQueryParameters();
            if (queryParameters != null) {
                freeRolesFilterEnabled = "true".equalsIgnoreCase((String) queryParameters.get("freeRolesFilterEnabled"));
            }
			if ( whereClause != null && whereClause.length() != 0 ) {
                if (freeRolesFilterEnabled) {
				    whereClause = " where ((fra.group is null) and (" + whereClause + "))";
                } else {
				    whereClause = " where " + whereClause;
                }
			} else {
                if (freeRolesFilterEnabled) {
				    whereClause = " where fra.group is null ";
                } else {
                    whereClause = new String();
                }
			}
			if ( orderByClause != null && orderByClause.length() != 0 ) {
				orderByClause = " order by " + orderByClause;
			} else {
				orderByClause = new String();
			}
		}

		List list = null;
		Integer total = null;
		String hqlPart = "from User u left outer join u.rolesAssociations as fra left outer join u.groups as g" + whereClause;
		if ( queryInfo != null && (queryInfo.getLimit() != null || queryInfo.getOffset() != null) ) {
			String hqlForTotal = "select count(distinct u.name) " + hqlPart;
			total = findUniqueIntegerResult(hqlForTotal);
			if ( total == null ) {
				total = new Integer(0);
			}
		}
        // If we don't have any info about the total number of results yet or
        // we know that there's something that will be found, then fetch data
		if ( total == null || total.intValue() > 0 ) {
			String hql = "select distinct u " + hqlPart + orderByClause;
			list = executeFind(hql, queryInfo);
			if ( total == null ) {
				total = new Integer(list.size());
			}
		} else {
			list = new ArrayList();
		}

		return new PartialCollection(list, total);
	}

	// U S E R   C O O K I E

	// ~ CRUD Methods ================================================================

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#createUserCookie(org.inwiss.platform.model.core.UserCookie, org.inwiss.platform.model.vo.User)
	 */
	public Long createUserCookie(UserCookie userCookie, User user) {
		userCookie.setUser(user);
		return (Long) getHibernateTemplate().save(userCookie);
	}

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#retrieveUserCookie(java.lang.String)
	 */
	public UserCookie retrieveUserCookie(String cookieId) {
		return (UserCookie) findUniqueResult("from UserCookie c where c.cookieId = ?", new Object[]{cookieId});
	}

	/**
	 * @see org.inwiss.platform.persistence.core.UserDAO#deleteUserCookies(java.lang.String)
	 */
	public void deleteUserCookies(String userName) {
		String hql = "from UserCookie c where c.user.name = ?";
		List cookies = executeFind(hql, new Object[]{userName}, new Type[]{StandardBasicTypes.STRING});
        if (cookies != null && cookies.size() > 0) {
            for (int i = 0; i < cookies.size(); i++) {
                UserCookie userCookie = (UserCookie) cookies.get(i);
                getHibernateTemplate().delete(userCookie);
            }
        }
    }


	public List<Long> loadUserMenus(String userName) {
        String hql = " select distinct m.id from MenuItem m  "
                + " inner join m.roles as r " + " inner join r.users as u "
                + " where u.name = ?";
		List menuIds = executeFind(hql, new Object[]{userName}, new Type[]{StandardBasicTypes.STRING});
		return menuIds;
	}

	/**
	 * 
	 */
	public List<MenuItem> loadTopTree(String columnName, String sort) {
		String hql = "from MenuItem m where m.parentItem is null order by "
	            + columnName + " " + sort;
		return  executeFind(hql);
	}

	@Override
	public PartialCollection loadUsersByOrgId(QueryInfo queryInfo) {
		List list = null;
		Integer total = null;
		Map<String,String> queryParameters = queryInfo.getQueryParameters();
		String dept = queryParameters.get("dept");
		String hql = "from User u where u.dept = ?";
		list = executeFind(hql, new Object[]{dept}, new Type[]{StandardBasicTypes.STRING});
		if(list == null){
			list = new ArrayList();
		}
		if ( total == null ) {
			total = new Integer(list.size());
		}

		return new PartialCollection(list, total);
	}

}
