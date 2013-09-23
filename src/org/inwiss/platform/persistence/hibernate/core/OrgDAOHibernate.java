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

import java.util.ArrayList;
import java.util.List;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Localizable;
import org.inwiss.platform.model.core.Org;
import org.inwiss.platform.persistence.core.OrgDAO;

/**
 * <p>Hibernate implementation of MenuDAO</p>
 * <p><a href="MenuDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author Andrey Grebnev <a href="mailto:andrey.grebnev@blandware.com">&lt;andrey.grebnev@blandware.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.39 $ $Date: 2006/08/03 10:07:39 $
 */
public class OrgDAOHibernate extends LocalizableDAOHibernate implements OrgDAO {

	/**
	 * Creates new instance of MenuDAOHibernate
	 */
	public OrgDAOHibernate() {
	}

	// ~ CRUD Methods ================================================================

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#createMenuItem(com.blandware.atleap.model.core.MenuItem, com.blandware.atleap.model.core.MenuItem, com.blandware.atleap.model.core.Localizable)
	 */
	public Long createOrgItem(Org org, Org parent, Localizable owner) {
		org.setParentItem(parent);
		org.setOwner(owner);
		return (Long) getHibernateTemplate().save(org);
	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#retrieveMenuItem(Long)
	 */
	public Org retrieveOrgItem(Long menuItemId) {
		return (Org) getHibernateTemplate().get(Org.class, menuItemId);
	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#updateMenuItem(com.blandware.atleap.model.core.MenuItem, com.blandware.atleap.model.core.MenuItem, com.blandware.atleap.model.core.Localizable)
	 */
	public void updateOrgItem(Org menuItem, Org parent, Localizable owner) {
		menuItem.setParentItem(parent);
		menuItem.setOwner(owner);
		getHibernateTemplate().update(menuItem);
	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#deleteMenuItem(com.blandware.atleap.model.core.MenuItem)
	 */
	public void deleteOrgItem(Org menuItem) {
		getHibernateTemplate().delete(menuItem);
	}

	// ~ Additional methods ================================================================

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#listMenuItems(com.blandware.atleap.common.util.QueryInfo)
	 */
	public PartialCollection listOrgItems(QueryInfo queryInfo) {
		String whereClause = new String();
		String orderByClause = " order by item.position asc, item.identifier asc";
		if ( queryInfo != null ) {
			whereClause = queryInfo.getWhereClause();
			if ( whereClause != null && whereClause.length() != 0 ) {
				whereClause = " and " + whereClause;
			}
		}

		List list = null;
		Integer total = null;
		Long parentItemId = null;
		Long ownerId = null;
		if ( queryInfo != null ) {
			parentItemId = (Long) queryInfo.getQueryParameters().get("parentItemId");
			ownerId = (Long) queryInfo.getQueryParameters().get("ownerId");
		}
		if ( log.isDebugEnabled() ) {
			log.debug("Parent ID: " + parentItemId);
			log.debug("Owner ID: " + ownerId);
		}
		String hql = "select distinct item from Org as item where";

		ArrayList args = new ArrayList();

		if ( parentItemId != null ) {
			hql += " item.parentItem.id = ?";
			args.add(parentItemId);
		} else {
			hql += " item.parentItem is null";
		}

		if ( ownerId != null ) {
			hql += " and item.owner.id = ?";
			args.add(ownerId);
		} else {
			hql += " and item.owner is null";
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

	// ~ Finders ================================================================

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#findMenuItemByIdentifierAndParentAndOwner(String, Long, Long)
	 */
	public Org findOrgItemByIdentifierAndParentAndOwner(String identifier, Long parentItemId, Long ownerId) {

		String hql = "from Org item where item.identifier = ?";

		ArrayList args = new ArrayList();

		args.add(identifier);

		String parentHqlPart = " and item.parentItem.id = ?";
		if ( parentItemId == null ) {
			parentHqlPart = " and item.parentItem is null";
		} else {
			args.add(parentItemId);
		}

		String ownerHqlPart = " and item.owner.id = ?";
		if ( ownerId == null ) {
			ownerHqlPart = " and item.owner is null";
		} else {
			args.add(ownerId);
		}

		hql += parentHqlPart + ownerHqlPart;

		List menuItems = executeFind(hql, args.toArray());

		if ( menuItems != null && menuItems.size() > 0 ) {
			return (Org) menuItems.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#findMenuItemByPositionAndParentAndOwner(Integer, Long, Long)
	 */
	public Org findOrgItemByPositionAndParentAndOwner(Integer position, Long parentItemId, Long ownerId) {

		String hql = "from Org item where item.position = ?";
		ArrayList args = new ArrayList();

		args.add(position);

		String parentHqlPart = " and item.parentItem.id = ?";
		if ( parentItemId == null ) {
			parentHqlPart = " and item.parentItem is null";
		} else {
			args.add(parentItemId);
		}

		String ownerHqlPart = " and item.owner.id = ?";
		if ( ownerId == null ) {
			ownerHqlPart = " and item.owner is null";
		} else {
			args.add(ownerId);
		}

		hql += parentHqlPart + ownerHqlPart;

		return (Org) findUniqueResult(hql, args.toArray());

	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#findMenuItemsByParentAndOwner(Long, Long)
	 */
	public List findOrgItemsByParentAndOwner(Long parentItemId, Long ownerId) {

		String hql = "from Org item where ";
		ArrayList args = new ArrayList();


		String parentHqlPart = " item.parentItem.id = ?";
		if ( parentItemId == null ) {
			parentHqlPart = " item.parentItem is null";
		} else {
			args.add(parentItemId);
		}

		String ownerHqlPart = " and item.owner.id = ?";
		if ( ownerId == null ) {
			ownerHqlPart = " and item.owner is null";
		} else {
			args.add(ownerId);
		}

		hql += parentHqlPart + ownerHqlPart;

		return executeFind(hql, args.toArray());
	}


	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#findRedefinitionItem(Long, Long)
	 */
	public Org findRedefinitionItem(Long origItemId, Long ownerId) {

		String hql = "from Org item where ";
		ArrayList args = new ArrayList();

		String origItemHqlPart = "item.origItem.id = ?";
		if ( origItemId == null ) {
			origItemHqlPart = "item.origItem is null";
		} else {
			args.add(origItemId);
		}

		String ownerHqlPart = " and item.owner.id = ?";
		if ( ownerId == null ) {
			ownerHqlPart = " and item.owner is null";
		} else {
			args.add(ownerId);
		}

		hql += origItemHqlPart + ownerHqlPart;

		return (Org) findUniqueResult(hql, args.toArray());

	}

	// ~ Helper methods

}
