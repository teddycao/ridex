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
package org.inwiss.platform.security.service.impl;

import java.util.List;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.common.util.RandomGUID;
import org.inwiss.platform.common.util.StringUtil;
import org.inwiss.platform.model.core.MenuItem;
import org.inwiss.platform.model.core.UserCookie;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.persistence.core.UserDAO;

import org.inwiss.platform.security.service.UserManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;
import org.apache.commons.lang.StringUtils;


/**
 * <p>Implementation of UserManager interface.
 * </p>
 * <p><a href="UserManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.12 $ $Date: 2006/08/03 10:07:43 $
 */
public class UserManagerImpl extends BaseManagerImpl implements UserManager {

    /**
     * User DAO
     */
	protected UserDAO userDAO;

	/**
	 * @see com.blandware.atleap.service.core.UserManager#setUserDAO(com.blandware.atleap.persistence.core.UserDAO)
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	//~ CRUD Methods ================================================================

	/**
	 * @see com.blandware.atleap.service.core.UserManager#createUser(org.inwiss.platform.model.vo.blandware.atleap.model.core.User)
	 */
	public void createUser(User user) throws BeanAlreadyExistsException {

		if ( log.isDebugEnabled() ) {
			log.debug("Creating new user (name=" + user.getName() + ")...");
		}

		User tmp = userDAO.retrieveUser(user.getName());
		if ( tmp != null ) {
			// user already exists
			String errorMessage = "User with name '" + user.getName() + "' already exists";
			if ( log.isErrorEnabled() ) {
				log.error(errorMessage);
			}
			throw new BeanAlreadyExistsException(errorMessage);
		}

		// user does not exist, so create it
		userDAO.createUser(user);

		if ( log.isDebugEnabled() ) {
			log.debug("New user has been created succefully.");
		}
	}

	/**
	 * @see com.blandware.atleap.service.core.UserManager#retrieveUser(java.lang.String)
	 */
	public User retrieveUser(String userName) {
		User user = null;
		user = userDAO.retrieveUser(userName);
		return user;
	}

	/**
	 * @see com.blandware.atleap.service.core.UserManager#updateUser(org.inwiss.platform.model.vo.blandware.atleap.model.core.User)
	 */
	public void updateUser(User user) {

        // remove user from cache in order to prevent Hibernate from assigning new version number
		userDAO.removeFromCache(user);
		if ( log.isDebugEnabled() ) {
			log.debug("Updating user (name=" + user.getName() + ")...");
		}

		userDAO.updateUser(user);

		if ( log.isDebugEnabled() ) {
			log.debug("User was updated succefully.");
		}
	}

	/**
	 * @see com.blandware.atleap.service.core.UserManager#deleteUser(java.lang.String)
	 */
	public void deleteUser(String userName) throws BeanNotFoundException {
		User user = userDAO.retrieveUser(userName);
		if ( user == null ) {
			String errorMessage = "No user with name=" + userName + " could be found";
			throw new BeanNotFoundException(errorMessage);
		}

        userDAO.deleteUser(user);

        if ( log.isDebugEnabled() ) {
            log.debug("Deleted user with name=" + userName);
        }
    }

	// ~ Additional methods ================================================================

	/**
	 * @see com.blandware.atleap.service.core.UserManager#listUsers(com.blandware.atleap.common.util.QueryInfo)
	 */
	public PartialCollection listUsers(QueryInfo queryInfo) {
		return userDAO.listUsers(queryInfo);
	}

	/**
	 * @see com.blandware.atleap.service.core.UserManager#checkUserCookie(java.lang.String)
	 */
	public String checkUserCookie(String value) throws BeanNotFoundException {
		value = StringUtil.decodeString(value);

		String[] values = StringUtils.split(value, "|");
		String userName = values[0];
		String cookieId = values[1];


		if ( log.isDebugEnabled() ) {
			log.debug("looking up cookieId: " + cookieId);
		}

		UserCookie userCookie = null;
		userCookie = userDAO.retrieveUserCookie(cookieId);

		if ( userCookie != null ) {
			if ( log.isDebugEnabled() ) {
				log.debug("cookieId lookup succeeded, generating new cookieId");
			}
			return createUserCookie(userCookie, userName);
		} else {
			if ( log.isDebugEnabled() ) {
				log.debug("cookieId lookup failed, returning null");
			}

			return null;
		}
	}

	/**
	 * @see com.blandware.atleap.service.core.UserManager#createUserCookie(com.blandware.atleap.model.core.UserCookie, java.lang.String)
	 */
	public String createUserCookie(UserCookie userCookie, String userName) throws BeanNotFoundException {
		User user = null;
		user = userDAO.retrieveUser(userName);

		if ( user == null ) {
			String errorMessage = "User with name '" + userName + " could not be found";
			if ( log.isDebugEnabled() ) {
				log.debug(errorMessage);
			}
			throw new BeanNotFoundException(errorMessage);
		}

		userCookie.setCookieId(new RandomGUID().toString());
		userDAO.createUserCookie(userCookie, user);
		return StringUtil.encodeString(user.getName() + "|" +
		        userCookie.getCookieId());
	}

	/**
	 * @see com.blandware.atleap.service.core.UserManager#deleteUserCookies(java.lang.String)
	 */
	public void deleteUserCookies(String username) {
		userDAO.deleteUserCookies(username);
	}

	
	public List<Long> loadUserMenus(String userName) {
		return userDAO.loadUserMenus(userName);
	}

	
	public List<MenuItem> loadTopTree(String columnName, String sort) {
		return userDAO.loadTopTree(columnName, sort);
	}

	@Override
	public PartialCollection loadUsersByOrgId(QueryInfo queryInfo) {
		// TODO Auto-generated method stub
		return userDAO.loadUsersByOrgId(queryInfo);
	}

}
