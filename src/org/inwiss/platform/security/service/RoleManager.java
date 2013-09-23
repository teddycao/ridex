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

import java.util.Set;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.persistence.core.RoleDAO;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;

/**
 * <p>Business Delegate (Proxy) Interface to handle communication between web and
 * persistence layer.
 * </p>
 * <p><a href="RoleManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.11 $ $Date: 2008/04/20 14:20:10 $
 */
public interface RoleManager extends BaseManager {

	/**
	 * Sets DAO for operating with roles
	 *
	 * @param dao the DAO to set
	 */
	public void setRoleDAO(RoleDAO dao);	

	// ~ CRUD Methods ================================================================

	/**
	 * Creates new role
	 *
	 * @param role Value object that represents what role must be created
	 */
	public void createRole(Role role) throws BeanAlreadyExistsException;

	/**
	 * Retrieves role with specified name
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
	public void updateRole(Role role) throws BeanAlreadyExistsException;

	/**
	 * Deletes role with specified name
	 *
	 * @param roleName Name of role to delete
	 */
	public void deleteRole(String roleName) throws BeanNotFoundException;

	// ~ Additional methods ================================================================

	/**
	 * Retrieves filtered/sorted collection of roles.
	 *
	 * @param queryInfo Object that contains information about how to filter and sort data
	 * @return Collection of roles
	 */
	public PartialCollection listRoles(QueryInfo queryInfo);

    /**
     * Creates given roles and assignes them to admins group. Role titles are
     * set equal to role names.
     *
     * @param roleNames set containing names of roles to create
     * @param fixed whether created roles should be fixed
     * @throws com.blandware.atleap.service.exception.BeanAlreadyExistsException
     * thrown if cannot create/update
     */
    public void addRolesAndAssignToAdmins(Set roleNames, boolean fixed) throws BeanAlreadyExistsException;
}