/*
 * @(#) HibernateObjectDAO.java
 *
 * $Revision: 1.2 $
 * $Date: 2005/07/10 12:35:06 $
 *
 * Copyright 2004 NGSHOUSE, INC. All Rights Reserved.
 */
package org.inwiss.platform.persistence.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class EntityObject implements Serializable, Cloneable {

    protected Long id;
    protected List properties;
    protected String name;


    public List getProperties() {
        return properties;
    }

    public void setProperties(List properties) {
        this.properties = properties;
    }

    public Class getPersistentClass() {
        return getClass();
    }
    
    public PropertyEntity getProperty(String name) {
        if (name == null) {
            return null;
        }
        if (properties != null) {
            Iterator iterator = properties.iterator();
            PropertyEntity property = null;
            while (iterator.hasNext()) {
                property = (PropertyEntity)iterator.next();
                if (name.equals(property.getName())) {
                    return property;
                }
            }
        }
        return null;
    }

    public EntityObject() {
        id = null;
    }

    /**
     * Returns the id.
     * 
     * @return Long
     * 
     * @hibernate.id column="id" length="9" type="java.lang.Long" generator-class="native" unsaved-value="null"
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    public int hashCode() {
        if (id == null)
            return 0;
        int idLongValue = (int) id.longValue();
        return (int) (idLongValue ^ idLongValue >>> 32);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EntityObject))
            return false;
        EntityObject entityObject = (EntityObject) o;
        return id == null ? entityObject.getId() == null : id.equals(entityObject.getId());
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}