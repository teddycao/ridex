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

import java.util.*;

/**
 * <p>Base class for pages.</p>
 * <p>In Atleap, pages are {@link ContentPage}s, {@link ActionPage}s and other
 * objects which are called <b>linkable objects</b> (news items, for example).</p>
 * <p>Page has URI by which it is accessible. It also has a <b>usage counter</b>
 * attached, activity flag (inactive page, for example, may be hidden). Localized
 * title, description and keywords are defined for page.</p>
 * <p>Every Page has a list of {@link Role}s that are allowed to access it.
 * If that list is empty, everyone is allowed to access such Page, otherwise
 * only users that have at least one role from that list can access the Page.</p>
 * <p>
 * Every page is indexed for search and its content (actually content of fields
 * that belong to this page and its layout) becomes a subject for full-text
 * search.
 * </p>
 * <p>For each page, date of its (or its content) last modification is
 * remembered.</p>
 * <p><a href="Page.java.html"><i>View Source</i></a>
 * </p>
 * <p/>
 *
 * @author Andrey Grebnev <a href="mailto:andrey.grebnev@blandware.com">&lt;andrey.grebnev@blandware.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.51 $ $Date: 2008/09/21 14:41:27 $
 * @hibernate.joined-subclass table="`al_core_page`" lazy="false"
 * @hibernate.joined-subclass-key column="`localizable_id`"
 */
public abstract class Page extends Localizable {

	//~ Instance variables

    /**
     * The URI under which the page is accessible
     */
	protected String uri;
    /**
     * The number of links to this page
     */
	protected Integer usageCounter;
    /**
     * Is this page ready for publication (shown to users) or not
     */
	protected Boolean active;

    /**
     * Map of titles for different locales
     */
	protected Map title = new HashMap();
    /**
     * Map of keywords for different locales
     */
	protected Map keywords = new HashMap();
    /**
     * Map of descriptions for different locales
     */
	protected Map description = new HashMap();
    /**
     * Date and time of last update of this page
     */
	protected Date lastUpdatedDatetime = null;

    /**
     * List of CFVs which are linked to this page
     */
	protected List linkedContentFieldValues = new ArrayList();
    /**
     * List of menu items which are linked to this page
     */
	protected List linkedMenuItems = new ArrayList();

    /**
     * List of roles which are allowed to access this page
     */
	protected List roles = new ArrayList();

    /**
     * List of sequence items that point to this page
     */
	protected List sequenceItems = new ArrayList();

    //~ Methods

	/**
	 * Returns the URI under which this page is accessible.
     * Note that UNIQUE constraint is removed from this field as nullable field
     * cannot be UNIQUE in Interbase.
	 *
	 * @return URI
	 * @struts.form-field
	 * @struts.validator type="required"
	 * @struts.validator type="mask" msgkey="core.cm.errors.pageUri"
	 * @struts.validator-args arg0resource="core.page.form.uri"
	 * @struts.validator-var name="mask" value="^(\/[a-zA-Z0-9\-_\040]+)+$"
	 * @hibernate.property
	 * @hibernate.column name="`uri`" not-null="false" length="252"
	 */
	public String getUri() {
		return uri;
	}

    /**
     * Sets the URI under which this page will be accessible
     *
     * @param uri the URI to set
     */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
     * Returns the usage counter of the page
     *
	 * @return usage counter
	 * @hibernate.property column="`usage_counter`" not-null="false"
	 */
	public Integer getUsageCounter() {
		return usageCounter;
	}

    /**
     * Sets the usage counter of the page
     *
     * @param usageCounter usage counter
     */
	public void setUsageCounter(Integer usageCounter) {
		this.usageCounter = usageCounter;
	}

	/**
	 * Returns <code>Boolean.TRUE</code> if page is active (and visible for users)
	 *
	 * @return whether page is active
	 * @struts.form-field 
	 * @hibernate.property column="`active`" not-null="true" type="true_false"
	 */
	public Boolean getActive() {
		return active;
	}

    /**
     * Sets whether page is active (and visible for users)
     *
     * @param active whether page is active
     */
	public void setActive(Boolean active) {
		this.active = active;
	}

	// Internal field values

	/**
     * Returns map of titles for different locales
     *
	 * @return mapping from locale identifiers to titles
	 * @struts.form-field
     * @atleap.field-property type="line"
	 */
	public Map getTitle() {
		return title;
	}

    /**
     * Sets map of titles for different locales
     *
     * @param title mapping from locale identifiers to titles
     */
	public void setTitle(Map title) {
		this.title = title;
	}

	/**
     * Returns map of keywords for different locales
     *
	 * @return mapping from locale identifiers to keyword sets
	 * @struts.form-field
     * @atleap.field-property type="line"
	 */
	public Map getKeywords() {
		return keywords;
	}

    /**
     * Sets map of keywords for different locales
     *
     * @param keywords mapping from locale identifiers to keyword sets
     */
	public void setKeywords(Map keywords) {
		this.keywords = keywords;
	}

	/**
     * Returns map of decriptions for different locales
     *
	 * @return mapping from locale identifiers to descriptions
	 * @struts.form-field
     * @atleap.field-property type="line"
	 */
	public Map getDescription() {
		return description;
	}

    /**
     * Sets map of decriptions for different locales
     *
     * @param description mapping from locale identifiers to descriptions
     */
	public void setDescription(Map description) {
		this.description = description;
	}

	/**
	 * Gets date/time when this page was created/last updated
	 *
	 * @return last updated datetime
	 * @hibernate.property
	 * @hibernate.column name="`last_updated`" not-null="true" unique="false"
	 */
	public Date getLastUpdatedDatetime() {
		return lastUpdatedDatetime;
	}

    /**
     * Sets date/time when this page was created/last updated
     *
     * @param lastUpdatedDatetime the date/time of creation/last update
     */
	public void setLastUpdatedDatetime(Date lastUpdatedDatetime) {
		this.lastUpdatedDatetime = lastUpdatedDatetime;
	}

    /**
	 * Gets list of CFVs this page is linked with
	 *
     * @return list if linked CFVs
     * @hibernate.bag table="`al_core_field_value_page`" cascade="none" lazy="true" inverse="false" outer-join="false"
	 * @hibernate.collection-key column="`page_id`"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.ContentFieldValue" column="`field_value_id`" outer-join="false"
	 */
	public List getLinkedContentFieldValues() {
		return linkedContentFieldValues;
	}

    /**
     * Sets list of CFVs this page is linked with
     *
     * @param linkedContentFieldValues list if linked CFVs
     */
	public void setLinkedContentFieldValues(List linkedContentFieldValues) {
		this.linkedContentFieldValues = linkedContentFieldValues;
	}

	/**
	 * Gets list of menu items this page is linked with
	 *
     * @return list of linked menu items
     * @hibernate.bag table="`al_core_menu_item_page`" cascade="none" lazy="true" inverse="false" outer-join="false"
	 * @hibernate.collection-key column="`page_id`"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.MenuItem" column="`menu_item_id`" outer-join="false"
	 */
	public List getLinkedMenuItems() {
		return linkedMenuItems;
	}

    /**
     * Sets list of menu items this page is linked with
     *
     * @param linkedMenuItems list of linked menu items
     */
	public void setLinkedMenuItems(List linkedMenuItems) {
		this.linkedMenuItems = linkedMenuItems;
	}

	/**
	 * Returns <code>true</code> if this page is in use, i.e. there are links on it from content field values or menu items
	 * or its usage counter is greater than zero
	 * @return <code>true</code> if this page is in use
	 */
	public boolean isInUse() {
		return !linkedContentFieldValues.isEmpty() || !linkedMenuItems.isEmpty() || usageCounter.intValue() > 0;
	}

    /**
     * Returns whether there are some objects linked to this page by URI that
     * cannot be modified to have correct links when this page URI is changed
     *
     * @return whether there are unmodifiable linked objects
     */
    public boolean doUnmodifiableLinkedObjectsExist() {
        for (Iterator i = linkedMenuItems.iterator(); i.hasNext();) {
            MenuItem menuItem = (MenuItem) i.next();
            if (!menuItem.isDynamic()) {
                return true;
            }
        }
        return false;
    }

	/**
	 * Returns the page roles (the roles that are allowed to access this page)
	 *
	 * @return list of roles
     * @hibernate.bag table="`al_core_page_role`" cascade="none" lazy="true" inverse="false" outer-join="false"
	 * @hibernate.collection-key column="`page_id`"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.Role"
	 * column="`rolename`" outer-join="false"
	 * @hibernate.cache usage="read-write"
	 */
	public List getRoles() {
		return roles;
	}

    /**
     * Sets the page roles (the roles that are allowed to access this page)
     *
     * @param roles list of roles
     */
	public void setRoles(List roles) {
		this.roles = roles;
	}

	/**
	 * Gets all page roles, represented as string (roles are separated with commas)
	 *
	 * @return roles separated with commas
	 */
	public String getRolesAsString() {
		StringBuffer roles = new StringBuffer();
		if ( this.roles != null && !this.roles.isEmpty() ) {
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
     * Adds a connection between this page and a given role
     *
     * @param role the role to be connected with
     */
    public void addRole(Role role) {
        if ( !role.getPages().contains(this) ) {
            role.getPages().add(this);
        }
        if ( !getRoles().contains(role) ) {
            getRoles().add(role);
        }
    }

    /**
     * Removes a connection between this page and a given role
     *
     * @param role the role
     */
    public void removeRole(Role role) {
        role.getPages().remove(this);
        getRoles().remove(role);
    }

	/**
	 * Returns the list of sequence items that point to this page.
	 *
	 * @return the list of sequence items
	 * @hibernate.bag name="sequenceItems" inverse="true" lazy="true" cascade="delete" outer-join="false"
	 * @hibernate.collection-key column="`page_id`"
	 * @hibernate.collection-one-to-many class="com.blandware.atleap.model.core.SequenceItem"
	 */
	protected List getSequenceItems() {
		return sequenceItems;
	}

    /**
     * Sets list of sequence items that point to this page.
     *
     * @param sequenceItems the list of sequence items
     */
	protected void setSequenceItems(List sequenceItems) {
		this.sequenceItems = sequenceItems;
	}

    public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Page) ) {
			return false;
		}

		final Page page = (Page) o;

		if ( uri != null ? !uri.equals(page.uri) : page.uri != null ) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		return (uri != null ? uri.hashCode() : 0);
	}

}
