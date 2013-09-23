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
 * <p>
 * Menu item points to some page, linkable object, content resource or external
 * source. Resulting URL is computed from <b>action</b>, <b>forward</b> and
 * <b>location</b>.
 * </p>
 * <p>
 * There are several layers on which menu items may be defined. Base layer
 * consists of all items that are defined in configurational files. Such items
 * cannot be edited. Next layer if formed with menu items that belong to
 * {@link Layout}. Final layer exists only for {@link ContentPage}s and consists
 * of menu items that were defined for some content page.
 * </p>
 * <p>
 * Each menu item may have child menu items. For each menu item visibility is
 * defined. This can be visible, invisible, heritable. Heritable visibility
 * means that this menu item will have the same visibility as on previous layer.
 * </p>
 * <p>
 * Menu item may actually be a redefinition. Redefinition is created when
 * user 'edits' some menu item on layer different from that on which it was
 * defined. Redefinition, in turn, may be redefined. Redefinition just changes
 * some properties (currently visibility and position) of original menu item.
 * </p>
 * <p>
 * Position specifies place in which menu item will be displayed in menu. If
 * position is not specified (is <code>null</code>), than such menu item will
 * be placed in the end of menu.
 * </p>
 * <p><a href="MenuItem.java.html"><i>View Source</i></a>
 * </p>
 * <p/>
 *
 * @author Andrey Grebnev <a href="mailto:andrey.grebnev@blandware.com">&lt;andrey.grebnev@blandware.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.50 $ $Date: 2008/09/21 14:41:27 $
 * @struts.form include-all="false" extends="BaseForm"
 * @hibernate.class table="`al_core_menu_item`" lazy="false"
 */
@JsonIgnoreProperties({ "parentItem", "childItems","Comparator" })
public class MenuItem extends BaseObject {

    /**
     * The visibility of menu item is inherited from the parent
     */
	public static final byte VISIBILITY_HERITABLE = 0;
    /**
     * Menu item is visible
     */
	public static final byte VISIBILITY_VISIBLE = 1;
    /**
     * Menu item is onvisible
     */
	public static final byte VISIBILITY_INVISIBLE = 2;

    /**
     * Constants for alignment of menu item. These are the same as those that
     * defined for HTML 'align' attribute
     */
	public static final String ALIGN_LEFT = "left";
	public static final String ALIGN_RIGHT = "right";
	public static final String ALIGN_ABS_BOTTOM = "absBottom";
	public static final String ALIGN_ABS_MIDDLE = "absMiddle";
	public static final String ALIGN_BASELINE = "baseline";
	public static final String ALIGN_BOTTOM = "bottom";
	public static final String ALIGN_MIDDLE = "middle";
	public static final String ALIGN_TEXT_TOP = "textTop";
	public static final String ALIGN_TOP = "top";

	/**
	 * Comparator that compares menu items by their positions
	 */
	public static final Comparator POSITION_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2) {
			if ( !(o1 instanceof MenuItem) || !(o2 instanceof MenuItem) ) {
				throw new ClassCastException("Cannot compare instance of " + o1.getClass().getName() + " to the instance of " + o2.getClass().getName());
			}
			MenuItem item1 = (MenuItem) o1;
			MenuItem item2 = (MenuItem) o2;
			return item1.getPosition().compareTo(item2.getPosition());
		}
	};

	//~ Instance variables

	/**
	 * ID of this menu item
	 */
	protected Long id;

	/**
	 * Parent item
	 */
	protected MenuItem parentItem;

	/**
	 * List of child items
	 */
	protected List childItems = new ArrayList();

	/**
	 * Identifier of this menu item (corresponds to 'name' in MenuComponent)
	 * Must be <code>null</code> for redefinitions
	 */
	protected String identifier;

	/**
	 * Align menu 'left','right','top','bottom' ...and other alignment of particular menu system
	 */
	protected String align;

	/**
	 * Style to apply to element that renders this item on the View layer (primarily, CSS-style, but there is no restriction)
	 */
	protected String style;

	/**
	 * Style class to apply to element that renders this item on the View layer (primarily, CSS-class, but there is no restriction)
	 */
	protected String styleClass;

	/**
	 * ID of element that renderes this item on the View layer (e.g. value of HTML "id" element)
	 */
	protected String styleId;

	/**
	 * Image for this menu item
	 */
	protected String image;

	/**
	 * Alternate image for this menu item
	 */
	protected String altImage;

	/**
	 * Holds value of property action, that is, Struts Logical Action Name.
	 */
	protected String action;

	/**
	 * Holds value of property action, that is, Struts Global Action Forward Name.
	 */
	protected String forward;

	/**
	 * Holds value of property location.
	 */
	protected String location;

	/**
	 * Whether or not location is external
	 */
	protected Boolean externalLocation = Boolean.FALSE;

	/**
	 * Holds value of property page.
	 */
	protected String anchor;

	/**
	 * Target window to open menu link in
	 */
	protected String target;

	/**
	 * Visibility on current layer. Menu item can be visible or invisible on curernt layer, or its behaviour
	 * can be the same as its equivalent on parent layer
	 */
	protected Byte visibility;

	/**
	 * Position of menu item in list. Can be null, it means that menu item will be at the end of the list
	 */
	protected Integer position;

	/**
	 * 'onlick' JavaScript event handler for this menu item
	 */
	protected String onclick;

	/**
	 * 'onmouseover' JavaScript event handler for this menu item
	 */
	protected String onmouseover;

	/**
	 * 'onmouseout' JavaScript event handler for this menu item
	 */
	protected String onmouseout;

    /**
     * 'hreflang' for this menu item - specifies language of resource to which
     * generated link points
     */
    protected String hreflang;

    /**
	 * List of allowed user roles
	 */
	protected List roles = new ArrayList();

	/**
	 * Link to menu item, for which this item is redefinition
	 */
	protected MenuItem origItem;

	/**
	 * List of redefinition items
	 */
	protected List redefinitionItems = new ArrayList();

	/**
	 * Owner of this menu item. Owner acts as a layer
	 */
	protected Localizable owner;

	// localizable fields

	/**
	 * Map of titles for different locales
	 */
	protected Map title = new HashMap();

	/**
	 * Map of tooltips for different locales
	 */
	protected Map toolTip = new HashMap();

	/**
	 * Version of this menu item
	 */
	protected Long version;

	/**
	 * List of pages linked with this item
	 */
	protected List linkedPages = new ArrayList();

	/**
	 * List of resources linked with this item
	 */
	protected List linkedResources = new ArrayList();


	//~ Methods

	/**
	 * Returns the id of the menu item
	 *
	 * @return id
	 * @struts.form-field
	 * @hibernate.id column="`id`"
	 * generator-class="increment" unsaved-value="null"
	 */
	public Long getId() {
		return id;
	}

    /**
     * Sets id of the menu item
     *
     * @param id the id to be set
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the parent menu item
	 *
	 * @return parent folder
	 * @hibernate.many-to-one column="`parent_id`" not-null="false" outer-join="false" lazy="false"
	 */
	public MenuItem getParentItem() {
		return parentItem;
	}

    /**
     * Sets the parent menu item
     *
     * @param parentItem parent menu item
     */
	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	/**
     * Returns list of child items of this menu item
     *
	 * @return list of child items
	 * @hibernate.bag name="childItems" inverse="true" lazy="true" cascade="delete" order-by="`pos`" outer-join="false"
	 * @hibernate.collection-key column="`parent_id`"
	 * @hibernate.collection-one-to-many class="com.blandware.atleap.model.core.MenuItem"
	 */
	public List getChildItems() {
		return childItems;
	}

    /**
     * Sets list of child items of this menu item
     *
     * @param childItems list of child items
     */
	public void setChildItems(List childItems) {
		this.childItems = childItems;
	}

	/**
	 * Returns the identifier of the menu item
	 *
	 * @return identifier
	 * @hibernate.property
	 * @hibernate.column name="`identifier`" not-null="false" unique="false" length="252"
	 */
	public String getIdentifier() {
		return identifier;
	}

    /**
     * Sets the identifier of the menu item
     *
     * @param identifier the identifier to set
     */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Returns the align 'left','right','top','bottom' ...and other alignment of particular menu system.
	 *
	 * @return align
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`align`" not-null="false" unique="false" length="252"
	 */
	public String getAlign() {
		return align;
	}

    /**
     * Sets the align of this menu item
     *
     * @param align the align to set
     */
	public void setAlign(String align) {
		this.align = align;
	}


	/**
	 * Returns the CSS-style of this menu item
	 *
	 * @return CSS-style
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`style`" not-null="false" unique="false" length="252"
	 */
	public String getStyle() {
		return style;
	}

    /**
     * Sets the CSS-style of this menu item
     *
     * @param style CSS-style
     */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Returns the style class of this menu item
	 *
	 * @return style class
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`style_class`" not-null="false" unique="false" length="252"
	 */
	public String getStyleClass() {
		return styleClass;
	}

    /**
     * Sets the style class of this menu item
     *
     * @param styleClass the style class to set
     */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * Returns the style ID of this menu item
	 *
	 * @return style ID
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`style_id`" not-null="false" unique="false" length="252"
	 */
	public String getStyleId() {
		return styleId;
	}

    /**
     * Sets the style ID of this menu item
     *
     * @param styleId the style ID to set
     */
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	/**
	 * Returns the URL of image that corresponds to this menu item
	 *
	 * @return URL of image
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`image`" not-null="false" unique="false" length="252"
	 */
	public String getImage() {
		return image;
	}

    /**
     * Sets the URL of image
     *
     * @param image URL of image
     */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Returns the URL of alternative image that corresponds to this menu item
	 *
	 * @return URL of alternative image
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`alt_image`" not-null="false" unique="false" length="252"
	 */
	public String getAltImage() {
		return altImage;
	}

    /**
     * Sets the URL of alternative image
     *
     * @param altImage URL of alternative image
     */
	public void setAltImage(String altImage) {
		this.altImage = altImage;
	}


	/**
	 * Gets name of Struts Action that is triggered when user activates this menu item
	 *
     * @return name of Struts Action
	 * @hibernate.property
	 * @hibernate.column name="`action`" not-null="false" unique="false" length="252"
	 */
	public String getAction() {
		return action;
	}

    /**
     * Sets name of Struts Action that is triggered when user activates this menu item
     *
     * @param action name of Struts Action
     */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets name of Struts Global Action Forward that is used when user activates this menu item
	 *
     * @return String name of Struts Global Action Forward
	 * @hibernate.property
	 * @hibernate.column name="`forward`" not-null="false" unique="false" length="252"
	 */
	public String getForward() {
		return forward;
	}

    /**
     * Sets name of Struts Global Action Forward that is used when user activates this menu item
     *
     * @param forward name of Struts Global Action Forward
     */
	public void setForward(String forward) {
		this.forward = forward;
	}

	/**
	 * Gets concrete location
	 *
     * @return location
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`location`" not-null="false" unique="false" length="252"
	 */
	public String getLocation() {
		return location;
	}

    /**
     * Sets concrete location
     *
     * @param location the location to set
     */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
     * Gets whether the location is external
     *
     * @return whether the location is external
	 * @hibernate.property column="`external_location`" not-null="true" type="true_false"
	 */
	public Boolean getExternalLocation() {
		return externalLocation;
	}

    /**
     * Sets whether the location is external
     *
     * @param externalLocation whether the location is external
     */
	public void setExternalLocation(Boolean externalLocation) {
		this.externalLocation = externalLocation;
	}

	/**
	 * Gets name of link on page where this item is displayed
	 *
     * @return name of anchor
	 * @hibernate.property
	 * @hibernate.column name="`anchor`" not-null="false" unique="false" length="252"
	 */
	public String getAnchor() {
		return anchor;
	}

    /**
     * Sets name of link on page where this item is displayed
     *
     * @param anchor the name of anchor
     */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}


	/**
	 * Returns the target of the link
	 *
	 * @return target of the link
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`target`" not-null="false" unique="false" length="252"
	 */
	public String getTarget() {
		return target;
	}

    /**
     * Sets target of the link
     *
     * @param target target of the link
     */
	public void setTarget(String target) {
		this.target = target;
	}


	/**
	 * Returns visibility of this menu item
	 *
	 * @return visibility
	 * @struts.form-field
	 * @hibernate.property
	 * @hibernate.column name="`visibility`" not-null="true" unique="false"
	 */
	public Byte getVisibility() {
		return visibility;
	}

    /**
     * Sets visibility of this menu item
     *
     * @param visibility the visibility to set
     */
	public void setVisibility(Byte visibility) {
		this.visibility = visibility;
	}

	/**
	 * Returns <code>true</code> if this menu item is visible on current layer
	 *
	 * @return boolean whether this menu item is visible on current layer
	 */
	public boolean isVisible() {
		return visibility.byteValue() == VISIBILITY_VISIBLE;
	}

	/**
	 * Returns <code>true</code>, if this menu item is inivisible on current layer
	 *
	 * @return boolean whether this menu item is invisible on current layer
	 */
	public boolean isInvisible() {
		return visibility.byteValue() == VISIBILITY_INVISIBLE;
	}

	/**
	 * Returns <code>true</code> if this menu item should behave like its equivavelent on parent layer
	 *
	 * @return boolean whether this menu item should behave like its equivavelent on parent layer
	 */
	public boolean isVisibilityHeritable() {
		return visibility.byteValue() == VISIBILITY_HERITABLE;
	}


	/**
	 * Returns position of menu item. It can be <code>null</code> -- this means,
     * that this element will be positioned last.
	 *
     * @return position
	 * @hibernate.property
	 * @hibernate.column name="`pos`" not-null="false" unique="false"
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

	/**
	 * Returns 'onclick' JavaScript event handler for this menu item
	 *
     * @return 'onclick' event handler
	 * @hibernate.property
	 * @hibernate.column name="`js_onclick`" not-null="false" unique="false" length="252"
	 */
	public String getOnclick() {
		return onclick;
	}

    /**
     * Sets 'onclick' JavaScript event handler for this menu item
     *
     * @param onclick 'onclick' event handler
     */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * Returns 'onmouseover' JavaScript event handler for this menu item
	 *
     * @return String 'onmouseover' event handler
	 * @hibernate.property
	 * @hibernate.column name="`js_onmouseover`" not-null="false" unique="false" length="252"
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

    /**
     * Sets 'onmouseover' JavaScript event handler for this menu item
     *
     * @param onmouseover 'onmouseover' event handler
     */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * Returns 'onmouseout' JavaScript event handler for this menu item
	 *
     * @return 'onmouseout' event handler
	 * @hibernate.property
	 * @hibernate.column name="`js_onmouseout`" not-null="false" unique="false" length="252"
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

    /**
     * Sets 'onmouseout' JavaScript event handler for this menu item
     *
     * @param onmouseout 'onmouseout' event handler
     */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

    /**
     * Returns 'hreflang' attribute (specifies language of resource to which
     * menu item points).
     *
     * @return hreflang value
     * @struts.form-field
     * @hibernate.property
	 * @hibernate.column name="`hreflang`" not-null="false" unique="false" length="252"
     */
    public String getHreflang() {
        return hreflang;
    }

    /**
     * Sets 'hreflang'.
     *
     * @param hreflang value to set
     */
    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }

    /**
	 * Returns roles. Those are the roles that are allowed to use this menu item.
	 *
	 * @return list of roles
     * @hibernate.bag table="`al_core_menu_item_role`" cascade="none" lazy="true" inverse="false" outer-join="false"
	 * @hibernate.collection-key column="`menu_item_id`"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.Role"
	 * column="`rolename`" outer-join="false"
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

	/**
	 * Returns menu item, for which this one is redefinition
	 *
     * @return original item
	 * @hibernate.many-to-one column="`orig_id`" not-null="false" outer-join="false" lazy="false"
	 */
	public MenuItem getOrigItem() {
		return origItem;
	}

    /**
     * Sets menu item, for which this one is redefinition
     *
     * @param origItem original item
     */
	public void setOrigItem(MenuItem origItem) {
		this.origItem = origItem;
	}

	/**
     * Returns a list of redefinitions of this menu item
     *
	 * @return list of redefinitions
	 * @hibernate.bag name="redefinitionItems" inverse="true" lazy="true" cascade="delete" outer-join="false"
	 * @hibernate.collection-key column="`orig_id`"
	 * @hibernate.collection-one-to-many class="com.blandware.atleap.model.core.MenuItem"
	 */
	protected List getRedefinitionItems() {
		return redefinitionItems;
	}

    /**
     * Sets list of redefinitions of this menu item
     *
     * @param redefinitionItems list of redefinitions
     */
	protected void setRedefinitionItems(List redefinitionItems) {
		this.redefinitionItems = redefinitionItems;
	}

	/**
	 * Returns <code>true</code> if this menu item is redefinition of another
     *
     * @return whether this is redefinition
	 */
	public boolean isRedefinition() {
		return this.origItem != null;
	}


	/**
	 * Returns the owner: Layout or Page
	 *
     * @return the owner
	 * @struts.form-field
	 * @hibernate.many-to-one column="`owner_id`" not-null="false" outer-join="false" lazy="false"
	 */
	public Localizable getOwner() {
		return owner;
	}

    /**
     * Sets the owner of this menu item
     *
     * @param owner new owner
     */
	public void setOwner(Localizable owner) {
		this.owner = owner;
	}

	/**
	 * Returns <code>true</code> if this menu item is dynamic. Dynamic menu item
     * is item that was created by user, not defined in xml-file.
     *
     * @return whether this menu item is dynamic
	 */
	public boolean isDynamic() {
		return this.owner != null;
	}


	/**
	 * Gets list of pages this menu item is linked with
	 *
     * @return list of linked pages
     * @hibernate.bag table="`al_core_menu_item_page`" cascade="none" lazy="true" inverse="false" outer-join="false"
	 * @hibernate.collection-key column="`menu_item_id`"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.Page" column="`page_id`" outer-join="false"
	 */
	public List getLinkedPages() {
		return linkedPages;
	}

    /**
     * Sets list of pages this menu item is linked with
     *
     * @param linkedPages list of linked pages
     */
	public void setLinkedPages(List linkedPages) {
		this.linkedPages = linkedPages;
	}

    /**
     * Adds a connection between this menu item and a given page
     *
     * @param page the page to be connected with
     */
    public void addLinkedPage(Page page) {
        if ( !page.getLinkedMenuItems().contains(this) ) {
            page.getLinkedMenuItems().add(this);
        }
        if ( !getLinkedPages().contains(page) ) {
            getLinkedPages().add(page);
        }
    }

    /**
     * Removes a connection between this menu item and a given page
     *
     * @param page the page
     */
    public void removeLinkedPage(Page page) {
        page.getLinkedMenuItems().remove(this);
        getLinkedPages().remove(page);
    }


	/**
	 * Gets list of content resources this menu item is linked with
	 *
     * @return list of content resources
     * @hibernate.bag table="`al_core_menu_item_resource`" cascade="none" lazy="true" inverse="false" outer-join="false"
	 * @hibernate.collection-key column="`menu_item_id`"
	 * @hibernate.collection-many-to-many class="com.blandware.atleap.model.core.ContentResource" column="`resource_id`" outer-join="false"
	 */
	public List getLinkedResources() {
		return linkedResources;
	}

    /**
     * Sets list of content resources this menu item is linked with
     *
     * @param linkedContentResources list of content resources
     */
	public void setLinkedResources(List linkedContentResources) {
		this.linkedResources = linkedContentResources;
	}

    /**
     * Adds a connection between this menu item and a given content resource
     *
     * @param resource the content resource to be connected with
    
    public void addLinkedResource(ContentResource resource) {
        if ( !resource.getLinkedMenuItems().contains(this) ) {
            resource.getLinkedMenuItems().add(this);
        }
        if ( !getLinkedPages().contains(resource) ) {
            getLinkedPages().add(resource);
        }
    }
 */
	
	
	
    /**
     * Removes a connection between this menu item and a given content resource
     *
     * @param resource the content resource
     
    public void removeLinkedResource(ContentResource resource) {
        resource.getLinkedMenuItems().remove(this);
        getLinkedResources().remove(resource);
    }
*/

	/**
	 * Gets mapping from locale identifiers to titles of this menu item
	 *
	 * @return mapping from locale identifiers to titles
	 * @struts.form-field
	 * @hibernate.map table="`al_core_menu_item_title`" lazy="true" cascade="all" outer-join="false"
	 * @hibernate.collection-key column="`id`"
	 * @hibernate.collection-index column="`title_identifier`" type="string" length="5"
	 * @hibernate.collection-element type="string" column="`title`" not-null="false" length="252"
	 */
	public Map getTitle() {
		return title;
	}

    /**
     * Sets mapping from locale identifiers to titles of this menu item
     *
     * @param title mapping from locale identifiers to titles
     */
	public void setTitle(Map title) {
		this.title = title;
	}

	/**
	 * Gets mapping from locale identifiers to tool tips of this menu item
	 *
	 * @return mapping from locale identifiers to tool tips
	 * @struts.form-field
	 * @hibernate.map table="`al_core_menu_item_tooltip`" lazy="true" cascade="all" outer-join="false"
	 * @hibernate.collection-key column="`id`"
	 * @hibernate.collection-index column="`tooltip_identifier`" type="string" length="5"
	 * @hibernate.collection-element type="string" column="`tooltip`" not-null="false" length="252"
	 */
	public Map getToolTip() {
		return toolTip;
	}

    /**
     * Sets mapping from locale identifiers to tool tips of this menu item
     *
     * @param toolTip mapping from locale identifiers to tool tips
     */
	public void setToolTip(Map toolTip) {
		this.toolTip = toolTip;
	}

	/**
	 * Returns version of this menu item
	 *
	 * @return version
	 * @struts.form-field
	 * @hibernate.version column="`version`" type="long" unsaved-value="null"
	 */
	public Long getVersion() {
		return version;
	}

    /**
     * Sets version of this menu item
     *
     * @param version new version
     */
	public void setVersion(Long version) {
		this.version = version;
	}

	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof MenuItem) ) {
			return false;
		}

		final MenuItem menuItem = (MenuItem) o;

		if ( identifier != null ? !identifier.equals(menuItem.identifier) : menuItem.identifier != null ) {
			return false;
		}
		if ( owner != null ? !owner.equals(menuItem.owner) : menuItem.owner != null ) {
			return false;
		}
		if ( parentItem != null ? !parentItem.equals(menuItem.parentItem) : menuItem.parentItem != null ) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (parentItem != null ? parentItem.hashCode() : 0);
		result = 29 * result + (identifier != null ? identifier.hashCode() : 0);
		result = 29 * result + (owner != null ? owner.hashCode() : 0);
		return result;
	}


}
