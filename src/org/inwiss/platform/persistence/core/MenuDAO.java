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
package org.inwiss.platform.persistence.core;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Group;
import org.inwiss.platform.model.core.Localizable;
import org.inwiss.platform.model.core.MenuItem;

import java.util.List;

/**
 * <p>DAO for menu item</p>
 * <p><a href="MenuDAO.java.html"><i>View Source</i></a></p>
 *
 * @author Andrey Grebnev <a href="mailto:andrey.grebnev@blandware.com">&lt;andrey.grebnev@blandware.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.12 $ $Date: 2006/08/03 10:07:37 $
 */
public interface MenuDAO  extends DAO {

	// ~ CRUD Methods ================================================================

	/**
	 * Creates new menu item
	 *
	 * @param menuItem Value object that represents what resource must be created
	 * @param parent   Value object that represents the parent menu item, maybe <code>null</code> if menu does not have parent
	 * @param owner    Owner of this menu item
	 * @return ID of created content resource
	 */
	public Long createMenuItem(MenuItem menuItem, MenuItem parent, Localizable owner);

	/**
	 * Retrieves menu item with specified ID
	 *
	 * @param menuItemId ID to search by
	 * @return Menu item or null if no menu item with specified ID exists in database
	 */
	public MenuItem retrieveMenuItem(Long menuItemId);

	/**
	 * Updates menu item
	 *
	 * @param menuItem Menu item to update
	 * @param parent   Value object that represents the parent menu item, maybe <code>null</code> if menu does not have parent
	 * @param owner    Owner of this menu item
	 */
	public void updateMenuItem(MenuItem menuItem, MenuItem parent, Localizable owner);

	/**
	 * Deletes menu item
	 *
	 * @param menuItem Menu item to delete
	 */
	public void deleteMenuItem(MenuItem menuItem);

	// ~ Additional methods ================================================================

	/**
	 * Retrieves filtered/sorted collection of menu items.
	 *
	 * @param queryInfo Object that contains information about how to filter and sort data
	 * @return Collection of resources
	 */
	public PartialCollection listMenuItems(QueryInfo queryInfo);

	// ~ Finders ================================================================

	/**
	 * Finds menu item by identifier, and parent and owner
	 *
	 * @param identifier   Identifier of menu item
	 * @param parentItemId ID of parent menu item
	 * @param ownerId      ID of owner of menu item
	 * @return Menu item or null if none exists
	 */
	public MenuItem findMenuItemByIdentifierAndParentAndOwner(String identifier, Long parentItemId, Long ownerId);

	/**
	 * Finds menu item by position, parent and owner
	 *
	 * @param position     Position of menu item
	 * @param parentItemId ID of parent menu item
	 * @param ownerId      ID of owner of menu item
	 * @return Menu item or null if none exists
	 */
	public MenuItem findMenuItemByPositionAndParentAndOwner(Integer position, Long parentItemId, Long ownerId);

	/**
	 * Finds all menu items with specified parent and owner
	 *
	 * @param parentItemId ID of parent menu item
	 * @param ownerId      ID of owner of menu item
	 * @return List of menu items or null if none exists
	 */
	public List findMenuItemsByParentAndOwner(Long parentItemId, Long ownerId);

	/**
	 * Looks up for redefinition of item with specified ID on specified layer
	 *
	 * @param origItemId ID of item to lookup redefinitions for
	 * @param ownerId    ID of layer to search on
	 * @return Menu item or null if none exists
	 */
	public MenuItem findRedefinitionItem(Long origItemId, Long ownerId);

}
