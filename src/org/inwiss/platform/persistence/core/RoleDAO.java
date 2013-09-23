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
import org.inwiss.platform.model.core.Role;

/**
 * <p>Role Data Access Object (DAO) interface.
 * </p>
 * <p><a href="RoleDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 */
public interface RoleDAO extends DAO {

    /**
     * Sets DAO to work with users
     *
     * @param userDAO user DAO to set
     */
    public void setUserDAO(UserDAO userDAO);

	// ~ CRUD Methods ================================================================

	/**
	 * Creates new role
	 *
	 * @param role Value object that represents what role must be created
	 */
	public void createRole(Role role);

	/**
	 * Retrieves role  with specified name
	 *
	 * @param roleName Name to search by
	 * @return Role or null if no role with specified name exists in database
	 */
	public Role retrieveRole(String roleName);

	/**
	 * Updates role
	 *
	 * @param role Role to update
	 */
	public void updateRole(Role role);

	/**
	 * Deletes role
	 *
	 * @param role Role to delete
	 */
	public void deleteRole(Role role);

	// ~ Additional methods ================================================================

	/**
	 * Retrieves filtered/sorted collection of roles.
	 *
	 * @param queryInfo Object that contains information about how to filter and sort data
	 * @return Collection of roles
	 */
	public PartialCollection listRoles(QueryInfo queryInfo);

	/**
	 * Searches for duplicates. Returns <code>true</code> if there is one or more roles with another ID, but which
	 * have same values from some set (e.g. title)
	 *
	 * @param role Role to find duplicates for
	 * @return whether given role has duplicates
	 */
	public boolean hasDuplicates(Role role);

	// ~ Finders ================================================================

    /**
     * Finds role by given title
     *
     * @param title to search role by
     * @return Role or null if nothing was found
     */
	public Role findRoleByTitle(String title);
}
