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
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.inwiss.platform.model.BaseObject;
import org.inwiss.platform.model.core.Group;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.model.core.UserRoleAssociation;

/**
 * <p>Represents User. User is entity used to perform authentication. User
 * may have some roles, they define what pages resources and so on he can assess.
 * </p>
 * <p>
 * Also, user may be placed to groups to ease assigning roles.
 * </p>
 * <p><a href="User.java.html"><i>View Source</i></a></p>
 *
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @author Roman Puchkovskiy <a href="mailto:roman.puchkovskiy@blandware.com">
 *         &lt;roman.puchkovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.38 $ $Date: 2008/09/21 14:41:26 $
 * @see Role
 * @see Group
 * @struts.form include-all="false" extends="BaseForm"
 * @hibernate.class table="al_core_user`" lazy="false" dynamic-update="true" dynamic-insert="true"
 * @hibernate.cache usage="read-write"
 */
@JsonIgnoreProperties({ "roles", "rolesAssociations", "freeRoles","groups","password" })
public class User extends BaseObject {
	//~ Instance fields
	// ========================================================

	protected String name;
	protected String userId;


	protected String password;
	protected String updatePassword;
	protected String confirmPassword;
	protected String updateConfirmPassword;
	
	protected String fullName;
	protected String niceName;
	
	protected String dept;
	
	

	
	protected String firstName;
	protected String lastName;
	protected String address;
	protected String city;
	protected String province;
	protected String country;
	protected String postalCode;
	protected String phoneNumber;
	protected String email;
	protected String website;
	protected String jsonData;

   

	/**
     * Roles that were assigned to user
     */
  
    protected List<Role> roles = new ArrayList<Role>();
    /**
     * Associations with roles through groups (ternary association between
     * users, roles and groups): each role is assigned to user through some
     * group. Exceptions are 'free' roles which are assigned to user directly,
     * so association <code>group</code> component is <code>null</code>.
     */
	protected List<Role> rolesAssociations = new ArrayList<Role>();
    /**
     * Free roles: roles that were assigned to user directly (not through groups)
     */
    protected List<Role> freeRoles = new ArrayList<Role>();
    /**
     * Groups that contain this user
     */
    protected List<Group> groups = new ArrayList<Group>();
    /**
     * Version (used for optimistic-locking)
     */
	protected Long version = new Long(1);
    /**
     * Whether user may log in and operate
     */
	protected Boolean enabled = true;

	/**
	 * Returns name of this user (it's actually a system identifier that is
     * unique among users)
	 *
	 * @return user name
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @struts.validator type="mask" msgkey="core.cm.errors.identifier"
	 * @struts.validator-args arg0resource="core.user.form.name"
	 * @struts.validator-var name="mask" value="${identifier}"
	 * @hibernate.id column="username" length="20" generator-class="assigned"
	 * unsaved-value="null"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the password.
	 *
	 * @return password
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @struts.validator type="twofields" msgkey="core.cm.errors.twofields"
	 * @struts.validator type="minlength" msgkey="core.cm.errors.minlength" arg0resource="core.user.form.password" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" msgkey="core.cm.errors.maxlength" arg0resource="core.user.form.password" arg1value="${var:maxlength}"
	 * @struts.validator-args arg0resource="core.user.form.password"
	 * @struts.validator-args arg1resource="core.user.form.confirmPassword"
	 * @struts.validator-var name="secondProperty" value="confirmPassword"
	 * @struts.validator-var name="minlength" value="${passwordMinLength}"
	 * @struts.validator-var name="maxlength" value="${passwordMaxLength}"
	 * @hibernate.property column="password" not-null="true" length="252"
	 */
	
	public String getPassword() {
		return password;
	}

	/**
     * <p>
     * Returns the 'update password'.
     * </p>
     * <p>
	 * Password may be omitted while updating user
	 * It means that we don't want to change old password, so these two fields
     * (<code>updatePassword</code> and <code>updateConfirmPassword</code>) were
     * included to correctly handle this situation
     * </p>
	 *
	 * @return 'update password'
	 * @struts.form-field
	 * @struts.validator type="twofields" msgkey="core.cm.errors.twofields"
	 * @struts.validator type="minlength" msgkey="core.cm.errors.minlength" arg0resource="core.user.form.password" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" msgkey="core.cm.errors.maxlength" arg0resource="core.user.form.password" arg1value="${var:maxlength}"
	 * @struts.validator-args arg0resource="core.user.form.updatePassword"
	 * @struts.validator-args arg1resource="core.user.form.confirmPassword"
	 * @struts.validator-var name="secondProperty" value="updateConfirmPassword"
	 * @struts.validator-var name="minlength" value="${passwordMinLength}"
	 * @struts.validator-var name="maxlength" value="${passwordMaxLength}"
	 */
	public String getUpdatePassword() {
		return updatePassword;
	}

	/**
	 * Returns the confirmed password
	 *
	 * @return confirmed password
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @struts.validator-args arg0resource="core.user.form.confirmPassword"
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
     * <p>
	 * Returns the 'update confirmed password'
     * </p>
     * <p>
	 * Password may be omitted while updating user
	 * It means that we don't want to change old password, so these two fields
     * (<code>updatePassword</code> and <code>updateConfirmPassword</code>) were
     * included to correctly handle this situation
     * </p>
	 *
	 * @return 'update confirmed password'
	 * @struts.form-field
	 */
	public String getUpdateConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Returns the first name of this user
	 *
	 * @return first name
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @struts.validator-args arg0resource="core.user.form.firstName"
	 * @hibernate.property column="first_name" not-null="true" length="50"
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the last name of this user
	 *
	 * @return last name
	 * @struts.form-field
	 * @hibernate.property column="last_name" not-null="false" length="50"
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns full user name (constructed from first name and last name)
	 *
	 * @return full name
	 */
	public String getFullName() {
		return  fullName;
	}

	/**
	 * Returns the address of this user
	 *
	 * @return address
	 * @struts.form-field
	 * @hibernate.property column="address" not-null="false" length="150"
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns the city of this user
	 *
	 * @return city
	 * @struts.form-field
	 * @hibernate.property column="city" not-null="false" length="50"
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the province
	 *
	 * @return province
	 * @struts.form-field
	 * @hibernate.property column="province" not-null="false" length="100"
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Returns the country
	 *
	 * @return country
	 * @struts.form-field
	 * @hibernate.property column="country" not-null="false" length="100"
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Returns the postal code
	 *
	 * @return postal code
	 * @struts.form-field
	 * @struts.validator type="mask" msgkey="core.cm.errors.zip"
	 * @struts.validator-args arg0resource="core.user.form.postalCode"
	 * @struts.validator-var name="mask" value="${zip}"
	 * @hibernate.property column="postal_code" not-null="false" length="15"
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Returns the email.  This is an optional field for specifying a
	 * different e-mail than the username.
	 *
	 * @return e-mail address
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @struts.validator type="email"
	 * @struts.validator-args arg0resource="core.user.form.email"
	 * @hibernate.property
	 * @hibernate.column name="email" not-null="true" unique="false" length="252"
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the phone number
	 *
	 * @return phone number
	 * @struts.form-field
	 * @struts.validator type="mask" msgkey="core.cm.errors.phone"
	 * @struts.validator-args arg0resource="core.user.form.phoneNumber"
	 * @struts.validator-var name="mask" value="${phone}"
	 * @hibernate.property column="phone_number" not-null="false" length="252"
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Returns the website
	 *
	 * @return website
	 * @struts.form-field
	 * @hibernate.property column="website" not-null="false" length="252"
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Returns the user's roles associations
	 *
	 * @return list of roles associations
     * @see #rolesAssociations
     * @see UserRoleAssociation
     * @hibernate.bag table="al_core_user_role" lazy="true"
     * @hibernate.collection-key
     * @hibernate.collection-key-column name="username" length="20"
     * @hibernate.collection-composite-element class="com.blandware.atleap.model.core.UserRoleAssociation"
	 */
	public List getRolesAssociations() {
		return rolesAssociations;
	}

    /**
     * Returns user's roles
     *
     * @return list of roles
     */
    public List<Role> getRoles() {
		return roles;
	}

    /**
     * Returns user's free roles (these are roles that were assigned to user
     * directly, not through some group)
     *
     * @return list of free roles
     */
    public List getFreeRoles() {
		return freeRoles;
	}

    /**
     * Returns list of groups to which this user belongs
     *
     * @return list of groups
     * @hibernate.bag table="al_core_group_user" cascade="none" lazy="true" inverse="true" outer-join="false"
     * @hibernate.collection-key
     * @hibernate.collection-key-column name="username" length="20"
     * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.Group"
     * column="groupname" outer-join="false"
     */
    public List getGroups() {
        return groups;
    }

    /**
     * Sets list of groups to which this user belongs
     *
     * @param groups list of groups
     */
    public void setGroups(List groups) {
        this.groups = groups;
    }

	/**
	 * Returns <code>Boolean.TRUE</code> if user is enabled (can login and operate) and
	 * <code>Boolean.FALSE</code> otherwise
	 *
	 * @return whether this user is active
	 * @hibernate.property column="enabled" not-null="true" type="true_false"
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Gets all user roles, represented as string (roles are separated with commas)
	 *
	 * @return roles separated with commas
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
	 * Gets free user roles, represented as string (roles are separated with commas)
	 *
	 * @return roles separated with commas
	 */
	public String getFreeRolesAsString() {
		StringBuffer roles = new StringBuffer();
		if ( this.freeRoles != null && this.freeRoles.size() > 0 ) {
			for ( Iterator i = this.freeRoles.iterator(); i.hasNext(); ) {
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
	 * Gets all user groups, represented as string (groups are separated with commas)
	 *
	 * @return roles separated with commas
	 */
	public String getGroupsAsString() {
		StringBuffer groups = new StringBuffer();
		if ( this.groups != null && this.groups.size() > 0 ) {
			for ( Iterator i = this.groups.iterator(); i.hasNext(); ) {
				Group group = (Group) i.next();
				groups.append(group.getTitle());
				if ( i.hasNext() ) {
					groups.append(", ");
				}
			}
		}
		return groups.toString();
	}

	/**
	 * Returns version
	 *
	 * @return version
	 * @struts.form-field
	 * @hibernate.version column="version" type="long" unsaved-value="null"
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * Sets the user name.
	 *
	 * @param name the user name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

    /**
     * Sets the 'update password'
     *
     * @param updatePassword the 'update password to set'
     * @see User#getUpdatePassword()
     */
	public void setUpdatePassword(String updatePassword) {
		this.updatePassword = updatePassword;
	}

	/**
	 * Sets the confirmed password.
	 *
	 * @param confirmPassword the confirmed password to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

    /**
     * Sets the 'update confirm password'
     *
     * @param updateConfirmPassword the 'update password' to set
     * @see User#getUpdateConfirmPassword()
     */
	public void setUpdateConfirmPassword(String updateConfirmPassword) {
		this.updateConfirmPassword = updateConfirmPassword;
	}

	/**
	 * Sets the first name of the user
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the last name of the user
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the address
	 *
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Sets the city
	 *
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Sets the country
	 *
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Sets the postal code
	 *
	 * @param postalCode the postal code to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Sets the province
	 *
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * Sets the email
	 *
	 * @param email the e-mail address to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the phone number
	 *
	 * @param phoneNumber the phone number to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Sets the website
	 *
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Sets the roles associations
	 *
	 * @param rolesAssociations list of roles associations
     * @see #rolesAssociations
	 */
	public void setRolesAssociations(List rolesAssociations) {
		this.rolesAssociations = rolesAssociations;

        roles = new ArrayList();
        freeRoles = new ArrayList();
        for (Iterator i = rolesAssociations.iterator(); i.hasNext();) {
            UserRoleAssociation association = (UserRoleAssociation) i.next();
            Role role = association.getRole();
/*            User user = association.getUser();
            role.getUsers().add(user);*/
            if (!roles.contains(role)) {
                roles.add(role);
            }
            if (association.getGroup() == null) {
                if (!freeRoles.contains(role)) {
                    freeRoles.add(role);
                }
            }
        }
	}

/*    public void setRoles(List roles) {
		this.roles = roles;
	}*/

	// Methods for operating with object model representing "user - roles" relationship

    /**
     * Adds a connection between this user and a given role (as free role)
     *
     * @param role role to connect with
     */
	public void addFreeRole(Role role) {
         addRole(role, null);
	}

    /**
     * Adds a connection between this user and a given role through a given
     * group (like a user is placed into <em>group</em> which has <em>role</em>
     * assigned to it)
     *
     * @param role role to connect with
     * @param group group through which to connect. If this is <code>null</code>,
     * added role will be considered as free.
     */
	public void addRole(Role role, Group group) {
        UserRoleAssociation association = new UserRoleAssociation(this, role, group);
		if ( !getRolesAssociations().contains(association) ) {
			getRolesAssociations().add(association);
		}
        // This is done to update roles and freeRoles lists too
        setRolesAssociations(getRolesAssociations());
	}

    /**
     * Removes a connection between this user and a given free role
     *
     * @param role the role
     */
	public void removeFreeRole(Role role) {
        removeRole(role, null);
	}

    /**
     * Removes a connection between this user and a given role through a given
     * group (like a user is removed from <em>group</em> which has <em>role</em>
     * assigned to it)
     *
     * @param role the role
     * @param group the group. If this is <code>null</code>, free role will
     * be removed.
     */
	public void removeRole(Role role, Group group) {
        UserRoleAssociation association = new UserRoleAssociation(this, role, group);
		getRolesAssociations().remove(association);
        // This is done to update roles and freeRoles lists too
        setRolesAssociations(getRolesAssociations());
	}

    /**
     * Removes all connections of this user to given role (free and assigned
     * through groups)
     *
     * @param role the role
     * 
     */
    public void removeRole(Role role) {
        List newAssociations = new ArrayList();
        List oldAssociations = getRolesAssociations();
        for (int i = 0; i < oldAssociations.size(); i++) {
            UserRoleAssociation association = (UserRoleAssociation) oldAssociations.get(i);
            if (!association.getRole().equals(role)) {
                newAssociations.add(association);
            }
        }
        setRolesAssociations(newAssociations);
    }

    /**
     * Sets version
     *
     * @param version the version to set
     */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * Sets user to be enabled or disabled
	 *
	 * @param enabled Boolean value to set user enabled or disabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof User) ) {
			return false;
		}

		final User user = (User) o;

		if ( address != null ? !address.equals(user.address) : user.address != null ) {
			return false;
		}
		if ( city != null ? !city.equals(user.city) : user.city != null ) {
			return false;
		}
		if ( country != null ? !country.equals(user.country) : user.country != null ) {
			return false;
		}
		if ( email != null ? !email.equals(user.email) : user.email != null ) {
			return false;
		}
		if ( firstName != null ? !firstName.equals(user.firstName) : user.firstName != null ) {
			return false;
		}
		if ( lastName != null ? !lastName.equals(user.lastName) : user.lastName != null ) {
			return false;
		}
		if ( password != null ? !password.equals(user.password) : user.password != null ) {
			return false;
		}
		if ( phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null ) {
			return false;
		}
		if ( postalCode != null ? !postalCode.equals(user.postalCode) : user.postalCode != null ) {
			return false;
		}
		if ( province != null ? !province.equals(user.province) : user.province != null ) {
			return false;
		}
		if ( website != null ? !website.equals(user.website) : user.website != null ) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (password != null ? password.hashCode() : 0);
		result = 29 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 29 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 29 * result + (address != null ? address.hashCode() : 0);
		result = 29 * result + (city != null ? city.hashCode() : 0);
		result = 29 * result + (province != null ? province.hashCode() : 0);
		result = 29 * result + (country != null ? country.hashCode() : 0);
		result = 29 * result + (postalCode != null ? postalCode.hashCode() : 0);
		result = 29 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
		result = 29 * result + (email != null ? email.hashCode() : 0);
		result = 29 * result + (website != null ? website.hashCode() : 0);
		return result;
	}
	

	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	
	public String getUserId() {
		
		return (this.userId == null)? name:userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	 public String getJsonData() {
			return jsonData;
	}

	public void setJsonData(String jsonData) {
			this.jsonData = jsonData;
	}
	
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}