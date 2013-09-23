/* @(#) NameEntity.java
 * @author: Leon
 *
 * $Revision: 1.1 $
 * $Date: 2005/06/29 06:57:01 $
 * 
 * Copyright 2005 SYSDONE INC, All Rights Reserved
 */

package org.inwiss.platform.persistence.hibernate;

public class NameEntity extends EntityObject {
    private String name;

    /**
     * Returns the name ���(Ψһ).
     *
     * @return String
     * 
     * @hibernate.property column="name" type="string" unique="true" length="30"
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name ���.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
