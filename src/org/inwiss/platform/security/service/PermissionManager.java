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
package org.inwiss.platform.security.service;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Permission;
import org.inwiss.platform.service.exception.BeanNotFoundException;

/**
 * <p>Business Delegate (Proxy) Interface to handle communication between web and
 * persistence layer.
 * </p>
 * <p><a href="MenuManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.13 $ $Date: 2006/08/03 10:07:41 $
 */
public interface PermissionManager extends BaseManager {
	//~ CRUD Methods ================================================================

	/**
	 * Creates new menuItem
	 *
	 * @param menuItem     Menu item to create
	 * @param parentItemId ID of parent menu item
	 * @param ownerId      ID of owner of menu item
	 * @return ID of created menu item
	 * @throws BeanNotFoundException if parent item or owner with specified ID couldn't be found
	 */
	public Long createPermission(Permission perm) throws BeanNotFoundException;

	/**
	 * Retrieves menu item with specified ID
	 *
	 * @param menuItemId ID of menu item to retrieve
	 * @return MenuItem or null if no menu item with specified ID exists in database
	 */
	public Permission retrievePermission(Long perm);

	/**
	 * Updates menu item
	 *
	 * @param menuItem     Menu item to update
	 * @param parentItemId ID of parent menu item
	 * @param ownerId      ID of owner of menu item
	 * @throws com.blandware.atleap.service.exception.BeanNotFoundException
	 *          if menu item or parent item with specified ID couldn't be found
	 */
	public void updatePermission(Permission perm) throws BeanNotFoundException;


	/**
	 * Deletes menu item with specified ID
	 *
	 * @param menuItemId ID of menu item to delete
	 */
	public void deletePermission(Long permId) throws BeanNotFoundException;

	// ~ Additional methods ================================================================

	/**
	 * Retrieves filtered/sorted collection of menu items
	 *
	 * @param queryInfo Object that contains information about how to filter and sort data
	 * @return Collection of menuItems
	 */
	public PartialCollection listPermissions(QueryInfo queryInfo);

	// ~ Finders ================================================================

	

}
