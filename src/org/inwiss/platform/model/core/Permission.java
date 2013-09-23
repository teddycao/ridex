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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.inwiss.platform.model.BaseObject;


/**
 * <p>Menu item -- an element of menu.</p>
 * @hibernate.class table="al_core_permission" lazy="false"
 */
//@JsonIgnoreProperties({ "parentItem", "childItems","Comparator" })
public class Permission extends BaseObject {

	/**
	 * Identifier of this menu item (corresponds to 'name' in MenuComponent)
	 * Must be <code>null</code> for redefinitions
	 */
	protected String identifier;

	protected String title;
	/**
	 * Holds value of property action, that is, Struts Logical Action Name.
	 */
	protected String action;

	
	protected String resource;
	
	protected String lacksResource;
	
	
	/**
	 * Holds value of property action, that is, Struts Global Action Forward Name.
	 */
	protected String mask;
	
	protected String type;

	/**
	 * Holds value of property location.
	 */
	protected String location;
	
	protected Integer position;
	
	protected String owner;
	


	/**
	 * List of allowed user roles
	 */
	protected List<Role> roles = new ArrayList<Role>();

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getLacksResource() {
		return lacksResource;
	}

	public void setLacksResource(String lacksResource) {
		this.lacksResource = lacksResource;
	}

	
    /**
	 * Returns roles. Those are the roles that are allowed to use this menu item.
	 *
	 * @return list of roles
     * @hibernate.bag table="al_core_menu_item_role" cascade="none" lazy="true" inverse="false" outer-join="false"
	 * @hibernate.collection-key column="menu_item_id"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.Role"
	 * column="rolename" outer-join="false"
	 * @hibernate.cache usage="read-write"
	 */
	public List getRoles() {
		return roles;
	}

	/**
	 * Gets all roles, represented as string (roles are separated with commas)
	 *
	 * @return roles, separated with commas
	 */
	public String getRolesAsString() {
		StringBuffer roles = new StringBuffer();
		if ( this.roles != null && this.roles.size() > 0 ) {
			for ( Iterator i = this.roles.iterator(); i.hasNext(); ) {
				Role role = (Role) i.next();
				roles.append(role.getTitle());
				if ( i.hasNext() ) {
					roles.append(", ");
				}
			}
		}
		return roles.toString();
	}

    /**
     * Sets roles. Those are the roles that are allowed to use this menu item.
     *
     * @param roles list of roles
     */
	public void setRoles(List roles) {
		this.roles = roles;
	}

    /**
     * Adds a connection between this menu item and a given role
     *
     * @param role the role to be connected with
     */
    public void addRole(Role role) {
        if ( !role.getMenuItems().contains(this) ) {
            role.getMenuItems().add(this);
        }
        if ( !getRoles().contains(role) ) {
            getRoles().add(role);
        }
    }

    /**
     * Removes a connection between this menu item and a given role
     *
     * @param role the role
     */
    public void removeRole(Role role) {
        role.getMenuItems().remove(this);
        getRoles().remove(role);
    }

	

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Returns position of menu item. It can be <code>null</code> -- this means,
     * that this element will be positioned last.
	 *
     * @return position
	 * @hibernate.property
	 * @hibernate.column name="pos" not-null="false" unique="false"
	 */
	public Integer getPosition() {
		return position;
	}

    /**
     * Sets position of menu item. It can be <code>null</code> -- this means,
     * that this element will be positioned last.
     *
     * @param position the position to set
     */
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	
    public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Permission) ) {
			return false;
		}

		final Permission menuItem = (Permission) o;

		if ( resource != null ? !resource.equals(menuItem.resource) : menuItem.resource != null ) {
			return false;
		}
				

		return true;
	}

	public int hashCode() {
		int result;
		result = (id != null ? id.hashCode() : 0);
		result = 29 * result + (resource != null ? resource.hashCode() : 0);
		result = 29 * result + (id != null ? id.hashCode() : 0);
		return result;
	}


}
