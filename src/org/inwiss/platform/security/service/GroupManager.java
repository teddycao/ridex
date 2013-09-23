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
package org.inwiss.platform.security.service;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Group;
import org.inwiss.platform.persistence.core.GroupDAO;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;

;

/**
 * <p>Business Delegate (Proxy) Interface to handle communication between web and
 * persistence layer.
 * </p>
 * <p><a href="GroupManager.java.html"><i>View source</i></a></p>
 *
 * @author Roman Puchkovskiy <a href="mailto:roman.puchkovskiy@blandware.com">
 *         &lt;roman.puchkovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.1 $ $Date: 2006/03/06 18:12:30 $
 */
public interface GroupManager extends BaseManager {

	/**
	 * Sets DAO for operating with groups
	 *
	 * @param dao the DAO to set
	 */
	public void setGroupDAO(GroupDAO dao);

	// ~ CRUD Methods ================================================================

	/**
	 * Creates new group
	 *
	 * @param group Value object that represents what group must be created
	 */
	public void createGroup(Group group) throws BeanAlreadyExistsException;

	/**
	 * Retrieves group with specified name
	 *
	 * @param groupName Name to search by
	 * @return Group or null if no group with specified name exists in database
	 */
	public Group retrieveGroup(String groupName);

	/**
	 * Updates group
	 *
	 * @param group Group to update
	 */
	public void updateGroup(Group group) throws BeanAlreadyExistsException;

	/**
	 * Deletes group with specified name
	 *
	 * @param groupName Name of group to delete
	 */
	public void deleteGroup(String groupName) throws BeanNotFoundException;

	// ~ Additional methods ================================================================

	/**
	 * Retrieves filtered/sorted collection of groups.
	 *
	 * @param queryInfo Object that contains information about how to filter and sort data
	 * @return Collection of groups
	 */
	public PartialCollection listGroups(QueryInfo queryInfo);
}
