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
package org.inwiss.platform.persistence.core;


import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Group;

/**
 * <p>Group Data Access Object (DAO) interface.
 * </p>
 * <p><a href="GroupDAO.java.html"><i>View source</i></a></p>
 *
 * @author Roman Puchkovskiy <a href="mailto:roman.puchkovskiy@blandware.com">
 *         &lt;roman.puchkovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.1 $ $Date: 2006/03/06 18:12:28 $
 */
public interface GroupDAO extends DAO {
    // ~ CRUD Methods ================================================================

    /**
     * Creates new group
     *
     * @param group Value object that represents what group must be created
     */
    public void createGroup(Group group);

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
    public void updateGroup(Group group);

    /**
     * Deletes group
     *
     * @param group Group to delete
     */
    public void deleteGroup(Group group);

    // ~ Additional methods ================================================================

    /**
     * Retrieves filtered/sorted collection of groups.
     *
     * @param queryInfo Object that contains information about how to filter and sort data
     * @return Collection of groups
     */
    public PartialCollection listGroups(QueryInfo queryInfo);

    /**
     * Searches for duplicates. Returns <code>true</code> if there is one or more groups with another ID, but which
     * have same values from some set (e.g. title)
     *
     * @param group Group to find duplicates for
     * @return whether given group has duplicates
     */
    public boolean hasDuplicates(Group group);

    // ~ Finders ================================================================

    /**
     * Finds group by given title
     *
     * @param title to search group by
     * @return Group or null if nothing was found
     */
    public Group findGroupByTitle(String title);

}
