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
import org.inwiss.platform.model.core.Permission;
/**
 * <p>User Data Access Object (DAO) interface.
 * </p>
 * <p><a href="UserDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.8 $ $Date: 2006/08/03 10:07:37 $
 */
public interface PermissionDAO extends DAO {

	// U S E R

	// ~ CRUD Methods ================================================================

	/**
	 * Creates new user
	 *
	 * @param user Value object that represents what user must be created
	 */
	public void createPermission(Permission perm);

	/**
	 * Retrieves user with specified name
	 *
	 * @param userName Name to search by
	 * @return User or null if no user with specified ID exists in database
	 */
	public Permission retrievePermission(Long id);

	/**
	 * Updates user
	 *
	 * @param user User to update
	 */
	public void updatePermission(Permission perm);

	/**
	 * Deletes user
	 *
	 * @param user User to delete
	 */
	public void deletePermission(Permission perm);

	// ~ Additional methods ================================================================

	/**
	 * Retrieves filtered/sorted collection of users.
	 *
	 * @param queryInfo Object that contains information about how to filter and sort data
	 * @return Collection of users
	 */
	public PartialCollection listPermissions(QueryInfo queryInfo);

	// U S E R   C O O K I E

	// ~ CRUD Methods ================================================================

	
}
