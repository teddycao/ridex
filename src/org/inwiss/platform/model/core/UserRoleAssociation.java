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
package org.inwiss.platform.model.core;

import java.io.Serializable;

import org.inwiss.platform.model.BaseObject;
import org.inwiss.platform.model.core.User;

/**
 * <p>
 * Represents an association between user, role and group: when a user is placed
 * into a group, group roles are assigned to him; so, for each role in group
 * a triad is created: &lt;user, role, group&gt;, where <em>user</em> is a user
 * which is placed to a group, <em>role</em> is role which belongs to a group,
 * and <em>group</em> is a group to which user is placed. Such triad is
 * represented with this class.
 * </p>
 * <p>
 * When a role is assigned directly to user, not through group, third component
 * is <code>null</code>.
 * </p>
 * <p><a href="UserRoleAssociation.java.html"><i>View source</i></a></p>
 *
 * @author Roman Puchkovskiy <a href="mailto:roman.puchkovskiy@blandware.com">
 *         &lt;roman.puchkovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.1 $ $Date: 2006/03/06 18:12:28 $
 */
public class UserRoleAssociation extends BaseObject  {

    /**
     * User which is a member of association
     */
    private User user;
    /**
     * Role which is a member of association
     */
    private Role role;
    /**
     * Group which is a member of association
     */
    private Group group;

    /**
     * Creates a UserRoleAssociation instance
     */
    public UserRoleAssociation() {
    }

    /**
     * Creates a UserRoleAssociation instance initializing its fields
     *
     * @param user user which is a member of association
     * @param role role which is a member of association
     * @param group group which is a member of association
     */
    public UserRoleAssociation(User user, Role role, Group group) {
        this.user = user;
        this.role = role;
        this.group = group;
    }

    /**
     * Returns user
     *
     * @return user
     * @hibernate.parent name="user"
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user
     *
     * @param user user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns role
     *
     * @return role
     * @hibernate.many-to-one class="com.blandware.atleap.model.core.Role"
     * column="`rolename`" not-null="true"
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role
     *
     * @param role role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Returns group
     *
     * @return group
     * @hibernate.many-to-one class="com.blandware.atleap.model.core.Group"
     * column="`groupname`" not-null="false"
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets group
     *
     * @param group group to set
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleAssociation)) return false;

        final UserRoleAssociation userRoleAssociation = (UserRoleAssociation) o;

        if (group != null ? !group.equals(userRoleAssociation.group) : userRoleAssociation.group != null) return false;
        if (role != null ? !role.equals(userRoleAssociation.role) : userRoleAssociation.role != null) return false;
        if (user != null ? !user.equals(userRoleAssociation.user) : userRoleAssociation.user != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (role != null ? role.hashCode() : 0);
        result = 29 * result + (user != null ? user.hashCode() : 0);
        result = 29 * result + (group != null ? group.hashCode() : 0);
        return result;
    }
}
