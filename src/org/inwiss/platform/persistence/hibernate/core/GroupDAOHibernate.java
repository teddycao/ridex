/*
 *  Copyright 2005 Blandware (http://www.blandware.com)
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

import org.inwiss.platform.persistence.core.GroupDAO;
import org.inwiss.platform.model.core.*;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Group objects.
 * </p>
 * <p><a href="GroupDAOHibernate.java.html"><i>View source</i></a></p>
 *
 * @author Roman Puchkovskiy <a href="mailto:roman.puchkovskiy@blandware.com">
 *         &lt;roman.puchkovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.4 $ $Date: 2008/06/23 13:24:54 $
 */
public class GroupDAOHibernate extends BaseDAOHibernate implements GroupDAO {

    /**
     * @see com.blandware.atleap.persistence.core.GroupDAO#createGroup(com.blandware.atleap.model.core.Group)
     */
    public void createGroup(Group group) {
        getHibernateTemplate().save(group);
    }

    /**
     * @see com.blandware.atleap.persistence.core.GroupDAO#retrieveGroup(String)
     */
    public Group retrieveGroup(String groupname) {
        return (Group) getHibernateTemplate().get(Group.class, groupname);
    }

    /**
     * @see com.blandware.atleap.persistence.core.GroupDAO#updateGroup(com.blandware.atleap.model.core.Group)
     */
    public void updateGroup(Group group) {
        getHibernateTemplate().update(group);
    }

    /**
     * @see com.blandware.atleap.persistence.core.GroupDAO#deleteGroup(com.blandware.atleap.model.core.Group)
     */
    public void deleteGroup(Group group) {
        List roles = new ArrayList(group.getRoles());
        List users = new ArrayList(group.getUsers());

        for ( int i = 0; i < users.size(); i++ ) {
            User user = (User) users.get(i);
            for ( int j = 0; j < roles.size(); j++ ) {
                Role role = (Role) roles.get(j);
                user.removeRole(role, group);
            }
        }

        getHibernateTemplate().delete(group);

		// break relations between roles and deleted group
		for ( int i = 0; i < roles.size(); i++ ) {
			Role role = (Role) roles.get(i);
			group.removeRole(role);
		}

		// break relations between users and deleted group
		for ( int i = 0; i < users.size(); i++ ) {
			User user = (User) users.get(i);
			group.removeUser(user);
		}

    }

    /**
     * @see com.blandware.atleap.persistence.core.GroupDAO#listGroups(com.blandware.atleap.common.util.QueryInfo)
     */
    public PartialCollection listGroups(QueryInfo queryInfo) {
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
        String hqlPart = "from Group g left outer join g.roles as r" + whereClause;
        if ( queryInfo != null && (queryInfo.getLimit() != null || queryInfo.getOffset() != null) ) {
            String hqlForTotal = "select count(distinct g.name) " + hqlPart;
            total = findUniqueIntegerResult(hqlForTotal);
            if ( total == null ) {
                total = new Integer(0);
            }
        }
        // If we don't have any info about the total number of results yet or
        // we know that there's something that will be found, then fetch data
        if ( total == null || total.intValue() > 0 ) {
            String hql = "select distinct g " + hqlPart + orderByClause;
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
     * @see com.blandware.atleap.persistence.core.GroupDAO#hasDuplicates(com.blandware.atleap.model.core.Group)
     */
    public boolean hasDuplicates(Group group) {

        ArrayList args = new ArrayList();
        args.add(group.getTitle());
        args.add(group.getName());

        String hql = "select count(g.name) from Group g where g.title = ? and g.name != ?";

        int count = (findUniqueIntegerResult(hql, args.toArray())).intValue();

        return count > 0;
    }

    // ~ Finders ================================================================

    /**
     * @see com.blandware.atleap.persistence.core.GroupDAO#findGroupByTitle(java.lang.String)
     */
    public Group findGroupByTitle(String title) {
        return (Group) findUniqueResult("from Group g where g.title = ?", new Object[]{title});
    }

}
