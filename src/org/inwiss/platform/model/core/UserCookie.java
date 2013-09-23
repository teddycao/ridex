package org.inwiss.platform.model.core;

import java.util.Date;

import org.inwiss.platform.model.BaseObject;
import org.inwiss.platform.model.core.User;


/**
 * <p>This class is used to manage cookie-based authentication.
 * </p>
 * <p><a href="UserCookie.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 * @version $Revision: 1.16 $ $Date: 2005/12/18 16:43:44 $
 * @hibernate.class table="`al_core_user_cookie`" lazy="false"
 */
public class UserCookie extends BaseObject {
	//~ Instance fields ========================================================

    /**
     * ID of this cookie in database (as stored object)
     */
	private Long id;
    /**
     * Owner of this cookie
     */
	private User user;
    /**
     * ID of the cookie (as cookie)
     */
	private String cookieId;
    /**
     * Creation date
     */
	private Date dateCreated;

	//~ Methods ================================================================

    /**
     * Create a cookie and set it's creation date to current one
     */
	public UserCookie() {
		this.dateCreated = new Date();
	}

	/**
	 * Returns the id.
	 *
	 * @return id
	 * @hibernate.id column="`id`"
	 * generator-class="increment" unsaved-value="null"
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id The id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the user this cookie associated with.
	 *
	 * @return user
	 * @hibernate.many-to-one column="`username`" not-null="false" lazy="false" outer-join="false"
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user The user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the cookie id (a GUID).
	 *
	 * @return cookie id
	 * @hibernate.property column="`cookie_id`" not-null="true" length="100"
	 */
	public String getCookieId() {
		return cookieId;
	}

	/**
	 * Sets the cookieId.
	 *
	 * @param cookieId The cookie id to set
	 */
	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}

	/**
     * Gets date of this cookie creation
     *
	 * @return Returns the date this cookie was created
	 * @hibernate.property column="`date_created`" not-null="true"
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
     * Sets date of this cookie creation
     *
	 * @param dateCreated The date this cookie was created
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof UserCookie) ) {
			return false;
		}

		final UserCookie userCookie = (UserCookie) o;

		if ( cookieId != null ? !cookieId.equals(userCookie.cookieId) : userCookie.cookieId != null ) {
			return false;
		}
		if ( dateCreated != null ? !dateCreated.equals(userCookie.dateCreated) : userCookie.dateCreated != null ) {
			return false;
		}
		if ( user != null ? !user.equals(userCookie.user) : userCookie.user != null ) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (user != null ? user.hashCode() : 0);
		result = 29 * result + (cookieId != null ? cookieId.hashCode() : 0);
		result = 29 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
		return result;
	}

}
