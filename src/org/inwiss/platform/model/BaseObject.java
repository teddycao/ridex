package org.inwiss.platform.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.inwiss.platform.persistence.pagination.PageRequest;

import java.io.Serializable;
import java.util.List;


/**
 * <p>Base class for Model objects.
 * </p>
 * <p><a href="BaseObject.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 * @author Andrey Grebnev <a href="mailto:andrey.grebnev@blandware.com">&lt;andrey.grebnev@blandware.com&gt;</a>
 * @version $Revision: 1.6 $ $Date: 2005/02/24 19:50:23 $
 */
public abstract class BaseObject implements Serializable {

    protected Long id;
    protected List properties;
    protected String name;

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
		        new NoArrayDetailToStringStyle());
	}

	public abstract boolean equals(Object o);

	public abstract int hashCode();

	/**
	 * <p><code>ToStringStyle</code> that does not print out
	 * the array details.</p>
	 */
	protected static class NoArrayDetailToStringStyle extends ToStringStyle {
		/**
		 * <p>Constructor.</p>
		 * <p>		 * <p>Use the static constant rather than instantiating.</p>
		 */
		private NoArrayDetailToStringStyle() {
			super();
			this.setArrayContentDetail(false);
			this.setDefaultFullDetail(false);
		}
	}
	
    public List getProperties() {
        return properties;
    }

    public void setProperties(List properties) {
        this.properties = properties;
    }

    public Class getPersistentClass() {
        return getClass();
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
