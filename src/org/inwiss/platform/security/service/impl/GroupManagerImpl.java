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
package org.inwiss.platform.security.service.impl;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.common.util.RandomGUID;
import org.inwiss.platform.common.util.StringUtil;
import org.inwiss.platform.model.core.Group;
import org.inwiss.platform.model.core.UserCookie;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.persistence.core.GroupDAO;
import org.inwiss.platform.persistence.core.UserDAO;

import org.inwiss.platform.security.service.GroupManager;
import org.inwiss.platform.security.service.UserManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;
import org.apache.commons.lang.StringUtils;

/**
 * <p>Implementation of GroupManager interface.
 * </p>
 * <p><a href="GroupManagerImpl.java.html"><i>View source</i></a></p>
 *
 * @author Roman Puchkovskiy <a href="mailto:roman.puchkovskiy@blandware.com">
 *         &lt;roman.puchkovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.1 $ $Date: 2006/03/06 18:12:30 $
 */
public class GroupManagerImpl extends BaseManagerImpl implements GroupManager {

    /**
     * Group DAO
     */
	protected GroupDAO groupDAO;

    /**
     * Sets DAO to work with groups
     *
     * @param dao DAO to set
     */
    public void setGroupDAO(GroupDAO dao) {
		this.groupDAO = dao;
	}

	// ~ CRUD Methods ================================================================

	/**
	 * @see com.blandware.atleap.service.core.GroupManager#createGroup(com.blandware.atleap.model.core.Group)
	 */
	public void createGroup(Group group) throws BeanAlreadyExistsException {
		if ( log.isDebugEnabled() ) {
			log.debug("Creating new Group [groupName=" + group.getName() + "]...");
		}

		// check is done directly, because it is impossible to write one query for create and update methods
		Group tmp = groupDAO.retrieveGroup(group.getName());
		if ( tmp != null ) {
			// group with the same name already exists
			String errorMessage = "Group with the name '" + group.getName() + "' already exists";
			if ( log.isErrorEnabled() ) {
				log.error(errorMessage);
			}
			throw new BeanAlreadyExistsException(errorMessage);
		}
		tmp = groupDAO.findGroupByTitle(group.getTitle());
		if ( tmp != null ) {
			// group with the same title already exists
			String errorMessage = "Group with the title '" + group.getTitle() + "' already exists";
			if ( log.isErrorEnabled() ) {
				log.error(errorMessage);
			}
			throw new BeanAlreadyExistsException(errorMessage);
		}
		groupDAO.createGroup(group);

		if ( log.isDebugEnabled() ) {
			log.debug("New group has been created succesfully");
		}
	}

	/**
	 * @see com.blandware.atleap.service.core.GroupManager#retrieveGroup(String)
	 */
	public Group retrieveGroup(String groupName) {
		Group group = null;
		group = groupDAO.retrieveGroup(groupName);
		return group;
	}

	/**
	 * @see com.blandware.atleap.service.core.GroupManager#updateGroup(com.blandware.atleap.model.core.Group)
	 */
	public void updateGroup(Group group) throws BeanAlreadyExistsException {

        // remove group from cache in order to prevent Hibernate from assigning new version number
		groupDAO.removeFromCache(group);
		if ( log.isDebugEnabled() ) {
			log.debug("Updating Group [groupName=" + group.getName() + "]...");
		}

		if ( groupDAO.hasDuplicates(group) ) {
			throw new BeanAlreadyExistsException("Group with the same title already exists");
		}

		groupDAO.updateGroup(group);

		if ( log.isDebugEnabled() ) {
			log.debug("Group has been updated succesfully");
		}
	}

	/**
	 * @see com.blandware.atleap.service.core.GroupManager#deleteGroup(String)
	 */
	public void deleteGroup(String groupName) throws BeanNotFoundException {
		Group group = groupDAO.retrieveGroup(groupName);
		if ( group == null ) {
			String errorMessage = "No group with ID=" + group + "could be found";
			throw new BeanNotFoundException(errorMessage);
		}
		groupDAO.deleteGroup(group);
	}

	// ~ Additional methods ================================================================

	/**
	 * @see com.blandware.atleap.service.core.GroupManager#listGroups(com.blandware.atleap.common.util.QueryInfo)
	 */
	public PartialCollection listGroups(QueryInfo queryInfo) {
		return groupDAO.listGroups(queryInfo);
	}

}
