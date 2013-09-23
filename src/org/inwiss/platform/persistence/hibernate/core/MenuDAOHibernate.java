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
import org.inwiss.platform.model.core.MenuItem;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.persistence.core.MenuDAO;

/**
 * <p>Hibernate implementation of MenuDAO</p>
 * <p><a href="MenuDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author Andrey Grebnev <a href="mailto:andrey.grebnev@blandware.com">&lt;andrey.grebnev@blandware.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.39 $ $Date: 2006/08/03 10:07:39 $
 */
public class MenuDAOHibernate extends LocalizableDAOHibernate implements MenuDAO {

	/**
	 * Creates new instance of MenuDAOHibernate
	 */
	public MenuDAOHibernate() {
	}

	// ~ CRUD Methods ================================================================

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#createMenuItem(com.blandware.atleap.model.core.MenuItem, com.blandware.atleap.model.core.MenuItem, com.blandware.atleap.model.core.Localizable)
	 */
	public Long createMenuItem(MenuItem menuItem, MenuItem parent, Localizable owner) {
		menuItem.setParentItem(parent);
		menuItem.setOwner(owner);
		return (Long) getHibernateTemplate().save(menuItem);
	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#retrieveMenuItem(Long)
	 */
	public MenuItem retrieveMenuItem(Long menuItemId) {
		return (MenuItem) getHibernateTemplate().get(MenuItem.class, menuItemId);
	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#updateMenuItem(com.blandware.atleap.model.core.MenuItem, com.blandware.atleap.model.core.MenuItem, com.blandware.atleap.model.core.Localizable)
	 */
	public void updateMenuItem(MenuItem menuItem, MenuItem parent, Localizable owner) {
		menuItem.setParentItem(parent);
		menuItem.setOwner(owner);
		getHibernateTemplate().update(menuItem);
	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#deleteMenuItem(com.blandware.atleap.model.core.MenuItem)
	 */
	public void deleteMenuItem(MenuItem menuItem) {
		getHibernateTemplate().delete(menuItem);

		// break relations between roles and deleted menu item
		List roles = new ArrayList(menuItem.getRoles());
		for ( int i = 0; i < roles.size(); i++ ) {
			Role role = (Role) roles.get(i);
			menuItem.removeRole(role);
		}
	}

	// ~ Additional methods ================================================================

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#listMenuItems(com.blandware.atleap.common.util.QueryInfo)
	 */
	public PartialCollection listMenuItems(QueryInfo queryInfo) {
		String whereClause = new String();
		//String orderByClause = " order by item.position asc, item.identifier asc";
		String orderByClause = " order by item.position asc ";
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
		String hql = "select distinct item from MenuItem as item left outer join item.roles as role where";

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
	public MenuItem findMenuItemByIdentifierAndParentAndOwner(String identifier, Long parentItemId, Long ownerId) {

		String hql = "from MenuItem item where item.identifier = ?";

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
			return (MenuItem) menuItems.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#findMenuItemByPositionAndParentAndOwner(Integer, Long, Long)
	 */
	public MenuItem findMenuItemByPositionAndParentAndOwner(Integer position, Long parentItemId, Long ownerId) {

		String hql = "from MenuItem item where item.position = ?";
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

		return (MenuItem) findUniqueResult(hql, args.toArray());

	}

	/**
	 * @see com.blandware.atleap.persistence.core.MenuDAO#findMenuItemsByParentAndOwner(Long, Long)
	 */
	public List findMenuItemsByParentAndOwner(Long parentItemId, Long ownerId) {

		String hql = "from MenuItem item where ";
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
	public MenuItem findRedefinitionItem(Long origItemId, Long ownerId) {

		String hql = "from MenuItem item where ";
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

		return (MenuItem) findUniqueResult(hql, args.toArray());

	}

	// ~ Helper methods

}
