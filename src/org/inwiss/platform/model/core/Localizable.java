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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.inwiss.platform.model.BaseObject;

/**
 * <p>Abstract class to make some virtual fields of child localizable.</p>
 * <p>It has {@link ContentField}s that contain localized content for different
 * languages, represented with {@link ContentLocale}s.</p> Localizable has a
 * menu attached to it. That menu is formed with {@link MenuItem}s.</p>
 * <p>Set of Content Fields and menu are inherited by {@link Layout}s, {@link Page}s
 * and so on.</p>
 * <p><a href="Localizable.java.html"><i>View Source</i></a></p>
 * <p/>
 *
 * @author Andrey Grebnev <a href="mailto:andrey.grebnev@blandware.com">&lt;andrey.grebnev@blandware.com&gt;</a>
 * @version $Revision: 1.34 $ $Date: 2007/12/13 13:52:58 $
 * @hibernate.class table="al_core_localizable" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public abstract class Localizable extends BaseObject {

	//~ Instance fields ========================================================

    /**
     * The ID of this localizable
     */
	protected Long id;
    /**
     * List of content fields that are owned by this localizable
     */
	protected List contentFields = new ArrayList();
    /**
     * List of sequences that are owned by this localizable
     */
    protected List sequences = new ArrayList();
    /**
     * List of menu items that are owned by this localizable
     */
	protected List menuItems = new ArrayList();
    /**
     * Version of this localizable (used in optimistic locking)
     */
	protected Long version;

    /**
     * Name of this class
     */
	protected String className = getClass().getName();

    /**
     * Simple name of this class (with no package). This property may be
     * inconsistent with className.
     */
    protected String simpleClassName = getClass().getName().substring(getClass().getPackage().getName().length() + 1);

	//~ Methods ================================================================

	/**
	 * Returns the id of this localizable
	 *
	 * @return id
	 * @struts.form-field
	 * @hibernate.id column="id"
	 * generator-class="increment" unsaved-value="null"
	 */
	public Long getId() {
		return id;
	}

    /**
     * Sets the id of this localizable
     *
     * @param id new id
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets list of content fields that belong to this layout
	 *
	 * @return list of content fields
	 * @hibernate.bag name="fields" inverse="true" lazy="true" cascade="delete" outer-join="false"
	 * @hibernate.collection-key column="localizable_id"
	 * @hibernate.collection-one-to-many class="com.blandware.atleap.model.core.ContentField"
	 */
	public List getContentFields() {
		return contentFields;
	}

    /**
     * Sets list of content fields that belong to this layout
     *
     * @param contentFields list of content fields
     */
	public void setContentFields(List contentFields) {
		this.contentFields = contentFields;
	}

    /**
     * Adds content field to this localizable
     *
     * @param contentField the field to be added
    
	public void addContentField(ContentField contentField) {
		contentField.setOwner(this);
		contentFields.add(contentField);
	}
 */
    /**
     * Updates changed content field
     *
     * @param contentField the field to be updated
     * @return content field that has been replaced
     
	public ContentField updateContentField(ContentField contentField) {
        ContentField oldContentField = null;
		if ( !contentField.getOwner().equals(this) ) {
            int index = contentField.getOwner().contentFields.indexOf(contentField);
            if (index != -1)
                oldContentField = (ContentField)contentField.getOwner().contentFields.remove(index);
			contentFields.add(contentField);
			contentField.setOwner(this);
		}
        return oldContentField;
	}
*/
	
	
    /**
     * Removes content field from this localizable
     *
     * @param contentField the field to be removed
    
	public void removeContentField(ContentField contentField) {
		contentFields.remove(contentField);
	}
 */
	
	
	/**
	 * Returns map with keys - contentField identifier and values - contentField
	 *
	 * @return mapping from CF identifiers to CFs
	
	public Map getContentFieldsMap() {
		Collection collection = getContentFields();
		Map map = new HashMap();
		for ( Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
			ContentField contentField = (ContentField) iterator.next();
			map.put(contentField.getIdentifier(), contentField);
		}
		return map;
	}
 */
	
	
    /**
     * Gets list of sequences that belong to this layout
     *
     * @return list of sequences
     * @hibernate.bag name="sequences" inverse="true" lazy="true" cascade="delete" outer-join="false"
     * @hibernate.collection-key column="localizable_id"
     * @hibernate.collection-one-to-many class="com.blandware.atleap.model.core.Sequence"
    
    public List getSequences() {
        return sequences;
    }
 */
    /**
     * Sets list of sequences that belong to this layout
     *
     * @param sequences list of sequences
   
    public void setSequences(List sequences) {
        this.sequences = sequences;
    }
  */
	
	
    /**
     * Adds sequence to this localizable
     *
     * @param sequence the sequence to be added
 
	public void addSequence(Sequence sequence) {
		sequence.setOwner(this);
		sequences.add(sequence);
	}
    */
	
	
	
	
	
    /**
     * Updates changed sequence
     *
     * @param sequence the sequence to be updated
     * @return sequence that has been replaced
     
	public Sequence updateSequence(Sequence sequence) {
        Sequence oldSequence = null;
		if ( !sequence.getOwner().equals(this) ) {
            int index = sequence.getOwner().sequences.indexOf(sequence);
            if (index != -1)
                oldSequence = (Sequence) sequence.getOwner().sequences.remove(index);
			sequences.add(sequence);
			sequence.setOwner(this);
		}
        return oldSequence;
	}
*/
	
	
    /**
     * Removes sequence from this localizable
     *
     * @param sequence the sequence to be removed
    
	public void removeSequence(Sequence sequence) {
		sequences.remove(sequence);
	}
 */
	
	
	
	/**
	 * Returns map with keys - sequence identifier and values - sequence
	 *
	 * @return mapping from sequence identifiers to sequences
	 
	public Map getSequencesMap() {
		Collection collection = getSequences();
		Map map = new HashMap();
		for ( Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
			Sequence sequence = (Sequence) iterator.next();
			map.put(sequence.getIdentifier(), sequence);
		}
		return map;
	}
*/
	
	
	
    /**
	 * Returns list of menu items which lie in the menu layer of this owner
	 *
	 * @return list of menu items
	 * @hibernate.bag name="menuItems" inverse="true" lazy="true" cascade="delete" outer-join="false"
	 * @hibernate.collection-key column="owner_id"
	 * @hibernate.collection-one-to-many class="com.blandware.atleap.model.core.MenuItem"
	 */
	protected List getMenuItems() {
		return menuItems;
	}

    /**
     * Sets list of menu items which lie in the menu layer of this owner
     *
     * @param menuItems list of menu items
     */
	protected void setMenuItems(List menuItems) {
		this.menuItems = menuItems;
	}

	/**
	 * Returns fully-qualified Java class name of this class
	 *
	 * @return fully-qualified Java class name
	 * @hibernate.property column="class_name" length="252"
	 */
	public String getClassName() {
		return className;
	}

    /**
     * Actually, does nothing (how can you change class of object at runtime?).
     * This is just a stub.
     *
     * @param className ignored
     */
	public void setClassName(String className) {
	}

    /**
     * Returns simple class name of this class (with no package).
     *
     * @return simple class name
     */
    public String getSimpleClassName() {
        return simpleClassName;
    }

    /**
     * Sets simple class name.
     *
     * @param simpleClassName name to set
     */
    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

	/**
	 * Returns version of this localizable
	 *
	 * @return version
	 * @struts.form-field
	 * @hibernate.version column="version" type="long" unsaved-value="null"
	 */
	public Long getVersion() {
		return version;
	}

    /**
     * Sets version of this localizable
     *
     * @param version new version
     */
	public void setVersion(Long version) {
		this.version = version;
	}


}
