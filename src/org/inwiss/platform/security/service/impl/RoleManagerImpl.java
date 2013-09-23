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
package org.inwiss.platform.security.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.inwiss.platform.common.Constants;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Group;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.persistence.core.RoleDAO;
import org.inwiss.platform.security.service.GroupManager;
import org.inwiss.platform.security.service.RoleManager;
import org.inwiss.platform.security.service.UserManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;

/**
 * <p>Implementation of RoleManager interface.
 * </p>
 * <p><a href="RoleManagerImpl.java.html"> <i>View Source </i> </a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.13 $ $Date: 2008/04/20 14:20:10 $
 */
public class RoleManagerImpl extends BaseManagerImpl implements RoleManager {

    /**
     * Role DAO
     */
	protected RoleDAO roleDAO;
    /**
     * Group manager
     */
	protected GroupManager groupManager;
    /**
     * User manager
     */
	protected UserManager userManager;

    /**
     * Sets DAO to work with roles.
     *
     * @param dao DAO to set
     */
	public void setRoleDAO(RoleDAO dao) {
		this.roleDAO = dao;
	}

    /**
     * Sets manager to work with groups.
     *
     * @param manager manager to set
     */
	public void setGroupManager(GroupManager manager) {
		this.groupManager = manager;
	}

    /**
     * Sets manager to work with users.
     *
     * @param manager manager to set
     */
	public void setUserManager(UserManager manager) {
		this.userManager = manager;
	}

    // ~ CRUD Methods ================================================================

	/**
	 * @see com.blandware.atleap.service.core.RoleManager#createRole(com.blandware.atleap.model.core.Role)
	 */
	public void createRole(Role role) throws BeanAlreadyExistsException {
		if ( log.isDebugEnabled() ) {
			log.debug("Creating new Role [roleName=" + role.getName() + "]...");
		}

		// check is done directly, because it is impossible to write one query for create and update methods
		Role tmp = roleDAO.retrieveRole(role.getName());
		if ( tmp != null ) {
			// role with the same name already exists
			String errorMessage = "Role with the name '" + role.getName() + "' already exists";
			if ( log.isErrorEnabled() ) {
				log.error(errorMessage);
			}
			throw new BeanAlreadyExistsException(errorMessage);
		}
		tmp = roleDAO.findRoleByTitle(role.getTitle());
		if ( tmp != null ) {
			// role with the same title already exists
			String errorMessage = "Role with the title '" + role.getTitle() + "' already exists";
			if ( log.isErrorEnabled() ) {
				log.error(errorMessage);
			}
			throw new BeanAlreadyExistsException(errorMessage);
		}
		roleDAO.createRole(role);

		if ( log.isDebugEnabled() ) {
			log.debug("New role has been created succesfully");
		}
	}

	/**
	 * @see com.blandware.atleap.service.core.RoleManager#retrieveRole(java.lang.String)
	 */
	public Role retrieveRole(String roleName) {
		Role role;
		role = roleDAO.retrieveRole(roleName);
		return role;
	}

	/**
	 * @see com.blandware.atleap.service.core.RoleManager#updateRole(com.blandware.atleap.model.core.Role)
	 */
	public void updateRole(Role role) throws BeanAlreadyExistsException {

        // remove role from cache in order to prevent Hibernate from assigning new version number
		roleDAO.removeFromCache(role);
		if ( log.isDebugEnabled() ) {
			log.debug("Updating Role [roleName=" + role.getName() + "]...");
		}

		if ( roleDAO.hasDuplicates(role) ) {
			throw new BeanAlreadyExistsException("Role with the same title already exists");
		}

		roleDAO.updateRole(role);

		if ( log.isDebugEnabled() ) {
			log.debug("Role has been updated succesfully");
		}
	}

	/**
	 * @see com.blandware.atleap.service.core.RoleManager#deleteRole(java.lang.String)
	 */
	public void deleteRole(String roleName) throws BeanNotFoundException {
		Role role = roleDAO.retrieveRole(roleName);
		if ( role == null ) {
			String errorMessage = "No role with ID=" + role + "could be found";
			throw new BeanNotFoundException(errorMessage);
		}
		roleDAO.deleteRole(role);
	}

	// ~ Additional methods ================================================================

	/**
	 * @see com.blandware.atleap.service.core.RoleManager#listRoles(com.blandware.atleap.common.util.QueryInfo)
	 */
	public PartialCollection listRoles(QueryInfo queryInfo) {
		return roleDAO.listRoles(queryInfo);
	}

    /**
     * @see com.blandware.atleap.service.core.RoleManager#addRolesAndAssignToAdmins(java.util.Set,boolean)
     */
    public void addRolesAndAssignToAdmins(Set roleNames, boolean fixed) throws BeanAlreadyExistsException {
        Iterator it = roleNames.iterator();
        Set createdRoles = new HashSet();
        while (it.hasNext()) {
            String roleName = (String) it.next();
            Role role = new Role();
            role.setName(roleName);
            role.setTitle(generateUnusedTitle(roleName));
            role.setFixed(fixed ? Boolean.TRUE : Boolean.FALSE);
            try {
                createRole(role);
            } catch (BeanAlreadyExistsException e) {
                // logging, but proceeding
                if (log.isErrorEnabled()) {
                    log.error("Cannot create role named " + roleName, e);
                }
            }
            createdRoles.add(role);
        }

        addRolesToGroup(createdRoles, Constants.ADMINS_GROUP);
    }

    /**
     * Generates title which is not used by any role.
     *
     * @param roleName  initial title which will be tried
     * @return unique title
     */
    protected String generateUnusedTitle(String roleName) {
        String title = roleName;
        Role existingRole;
        while (true) {
            existingRole = roleDAO.findRoleByTitle(title);
            if (existingRole == null) {
                // we found unused title
                break;
            }
            if (title.length() == 0) {
                title = "a";
            } else {
                char lastLetter = title.charAt(title.length() - 1);
                if (lastLetter >= 'a' && lastLetter < 'z'
                        || lastLetter >= 'A' && lastLetter < 'Z'
                        || lastLetter >= '0' && lastLetter < '9') {
                    lastLetter = (char) (lastLetter + 1);
                    title = title.substring(0, title.length() - 1) + lastLetter;
                } else {
                    title = title + "a";
                }
            }
        }
        return title;
    }

    /**
     * Adds given roles to group with given name if it exists.
     *
     * @param roles         set of roles to add
     * @param groupName     name of group
     * @throws com.blandware.atleap.service.exception.BeanAlreadyExistsException
     * thrown if cannot update group
     */
    protected void addRolesToGroup(Set roles, String groupName) throws BeanAlreadyExistsException {
        if (!roles.isEmpty()) {
            Group group = groupManager.retrieveGroup(groupName);
            if (group != null) {
                // list of users that were updated due to roles addition
                Set touchedUsers = new HashSet();

                // add new roles
                for ( Iterator i = roles.iterator(); i.hasNext(); ) {
                    Role role = (Role) i.next();
                    group.addRole(role);
                    List groupUsers = group.getUsers();
                    for (int j = 0; j < groupUsers.size(); j++) {
                        User user = (User) groupUsers.get(j);
                        user.addRole(role, group);
                        touchedUsers.add(user);
                    }
                }

                groupManager.updateGroup(group);
                for (Iterator i = touchedUsers.iterator(); i.hasNext();) {
                    User user = (User) i.next();
                    userManager.updateUser(user);
                }
            }
        }
    }
}