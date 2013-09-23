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
package org.inwiss.platform.model.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.inwiss.platform.model.BaseObject;
import org.inwiss.platform.model.core.User;

/**
 * <p>This class is used to represent available roles in the database. Such roles
 * are assigned to some entities to restrict access to them. Each user has a set
 * of roles. When a user tries to gain access to some entity with restricted
 * assess (content page, for instance), each his role is checked whether it's
 * allowed to access this entity. If at least one role is allowed, user is
 * permitted to access that entity. The algorithm to determine whether role
 * has access to entity is as follows: if none of the roles is assigned to the
 * entity, access is granted, otherwise access is granted when and only when
 * the checked role is assigned to the entity.
 * </p>
 * <p>
 * User may also be a member of some groups. Each group may have roles assigned
 * to it. When checking for user's roles, his groups' roles are considered too.
 * </p>
 * <p>
 * So, roles may be assigned to user in two ways: directly (such roles are
 * called 'free') and through group (placing a user into some group).

 * @see User
 * @see Group
 * @struts.form include-all="false" extends="BaseForm"
 * @hibernate.class table="al_core_role" lazy="false"
 * @hibernate.cache usage="read-write"
 */
@JsonIgnoreProperties({ "pages", "menuItems", "resources","groups" })
public class Role extends BaseObject implements Serializable {
	//~ Instance fields ========================================================

    /**
     * Name of the role (used as a system identifier)
     */
	private String name;
    /**
     * Human-readable name of the role
     */
	private String title;
    /**
     * Description of the role; this may be quite long
     */
	private String description;
    /**
     * Whether role is fixed. Fixed roles cannot be deleted
     */
	private Boolean fixed;
    /**
     * Version of this role (used in optimistic locking)
     */
	protected Long version;

	/**
	 * List of groups to which this role belongs
	 */
    private List<Group> groups = new ArrayList<Group>();

	/**
	 * List of groups to which this role belongs
	 */
    private List<User> users = new ArrayList<User>();
    


	/**
	 * List of pages for which this role has been assigned
	 */
	private List pages = new ArrayList();

	/**
	 * List if menu items for which this role has been assigned
	 */
	private List<MenuItem> menuItems = new ArrayList<MenuItem>();

	/**
	 * List of resources for which this role has been assigned
	 */
	private List<Permission> permissions = new ArrayList<Permission>();

	private boolean authorized = false;
	
    public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	/**
     * Creates a role
     */
	public Role() {
	}

    /**
     * Creates a role with a given name
     *
     * @param name role name
     * @param title role title
     */
	public Role(String name, String title) {
		this.name = name;
	    this.title = title;
	}

	//~ Methods ================================================================

	/**
	 * Returns the name of the role (it's the analog of system identifier)
	 *
	 * @return role name
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @struts.validator type="mask" msgkey="core.cm.errors.identifierAllowingCapitals"
	 * @struts.validator-args arg0resource="core.role.form.name"
	 * @struts.validator-var name="mask" value="${identifierAllowingCapitals}"
	 * @hibernate.id column="rolename" length="40"
	 * generator-class="assigned" unsaved-value="null"
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns human-readable title of the role.
	 *
	 * @return role title
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @struts.validator-args arg0resource="core.role.form.title"
	 * @hibernate.property
	 * @hibernate.column name="title" not-null="true" unique="true" length="63"
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the description of the role
	 *
	 * @return role description
	 * @struts.form-field
	 * @hibernate.property column="description" length="252"
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Returns <code>Boolean.TRUE</code>, if role is fixed, i.e. it cannot be deleted
	 *
	 * @return whether role is fixed
	 * @hibernate.property column="fixed" not-null="true" type="true_false"
	 */
	public Boolean getFixed() {
		return fixed;
	}

    /**
     * Sets name of the role
     *
     * @param name role name
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Sets human-readable title of the role
     *
     * @param title role title
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * Sets description of the role
     *
     * @param description role description
     */
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * Sets whether the role will be fixed (and no one will be able to delete it)
     *
     * @param fixed whether the role will be fixed
     */
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

	/**
	 * Returns version of the role
	 *
	 * @return version
	 * @struts.form-field
	 * @hibernate.version column="version" type="long" unsaved-value="null"
	 */
	public Long getVersion() {
		return version;
	}

    /**
     * Sets version of the role
     *
     * @param version the version to set
     */
	public void setVersion(Long version) {
		this.version = version;
	}

    /**
     * Returns list of groups to which this role belongs
     *
     * @return list of groups
     * @hibernate.bag table="al_core_group_role" cascade="none" lazy="true" inverse="true" outer-join="false"
     * @hibernate.collection-key
     * @hibernate.collection-key-column name="rolename" length="40"
     * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.Group"
     * column="groupname" outer-join="false"
     */
    public List getGroups() {
        return groups;
    }

    /**
     * Sets list of groups to which this role belongs
     *
     * @param groups list of groups
     */
    public void setGroups(List groups) {
        this.groups = groups;
    }

	/**
	 * Returns list of pages to which this role is assigned
	 *
     * @return list of pages
     * @hibernate.bag table="al_core_page_role" cascade="none" lazy="true" inverse="true" outer-join="false"
	 * @hibernate.collection-key column="rolename"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.Page"
	 * column="page_id" outer-join="false"
	 */
	public List getPages() {
		return pages;
	}

    /**
     * Sets list of pages to which this role is assigned
     *
     * @param pages list of pages
     */
	public void setPages(List pages) {
		this.pages = pages;
	}

	/**
	 * Returns list of menu items to which this role is assigned
	 *
     * @return list of menu items
     * @hibernate.bag table="al_core_menu_item_role" cascade="none" lazy="true" inverse="true" outer-join="false"
	 * @hibernate.collection-key column="rolename"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.MenuItem"
	 * column="menu_item_id" outer-join="false"
	 */
	public List getMenuItems() {
		return menuItems;
	}

    /**
     * Gets list of menu items to which this role is assigned
     *
     * @param menuItems list of menu items
     */
	public void setMenuItems(List menuItems) {
		this.menuItems = menuItems;
	}

	
	/**
	 * Returns list of content resources to which this role is assigned
	 *
     * @return list of content resources
     * @hibernate.bag table="al_core_resource_role" cascade="none" lazy="true" inverse="true" outer-join="false"
	 * @hibernate.collection-key column="rolename"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.ContentResource"
	 * column="resource_id" outer-join="false"
	 */
	public List<Permission>  getPermissions() {
		return permissions;
	}

    /**
     * Sets list of content resources to which this role is assigned
     *
     * @param resources list of content resources
     */
	public void setPermissions(List<Permission> permission) {
		this.permissions = permissions;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}

		if ( !(o instanceof Role) ) {
			return false;
		}
		final Role role = (Role) o;

		if ( name != null ? !name.equals(role.name) : role.name != null ) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		if ( name == null ) {
			return 0;
		} else {
			return name.hashCode();
		}
	}

}
