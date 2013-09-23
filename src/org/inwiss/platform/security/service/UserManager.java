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
package org.inwiss.platform.security.service;

import java.util.List;

import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.MenuItem;
import org.inwiss.platform.model.core.UserCookie;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.persistence.core.UserDAO;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.inwiss.platform.service.exception.BeanNotFoundException;


/**
 * <p>Business Delegate (Proxy) Interface to handle communication between web and
 * persistence layer.
 * </p>
 * <p><a href="UserManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.8 $ $Date: 2006/08/03 10:07:41 $
 */
public interface UserManager extends BaseManager {

	// U S E R

	/**
	 * Sets DAO for operating with users
	 *
	 * @param userDAO the DAO to set
	 */
	public void setUserDAO(UserDAO userDAO);

	/**
	 * Creates new user
	 *
	 * @param user Value object that represents what user must be created
	 * @throws BeanAlreadyExistsException if user with the same name already exists
	 */
	public void createUser(User user) throws BeanAlreadyExistsException;

	/**
	 * Retrieves user with specified name
	 *
	 * @param userName Name to search by
	 * @return User or null if no user with specified ID exists in database
	 */
	public User retrieveUser(String userName);

	/**
	 * Updates user
	 *
	 * @param user User to update
	 */
	public void updateUser(User user);

	/**
	 * Deletes user with specified name
	 *
	 * @param userName Name of user to delete
	 */
	public void deleteUser(String userName) throws BeanNotFoundException;

	// ~ Additional methods ================================================================

	/**
	 * Retrieves filtered/sorted collection of users.
	 *
	 * @param queryInfo Object that contains information about how to filter and sort data
	 * @return Collection of users
	 */
	public PartialCollection listUsers(QueryInfo queryInfo);

	// U S E R   C O O K I E

	/**
	 * Validates a user based on a cookie value.  If successful, it returns
	 * a new cookie String. If not, then it returns null.
	 *
	 * @param value (in format username|guid)
	 * @return indicator that this is a valid login (null == invalid)
	 * @throws BeanNotFoundException if user with specified name couldn't be found
	 */
	public String checkUserCookie(String value) throws BeanNotFoundException;

	/**
	 * Creates a cookie string using a user cookie
	 *
	 * @param userCookie Cookie to be created
     * @param userName Name of the user for whom the cookie is created
	 * @return String to put in a cookie for remembering user
	 * @throws BeanNotFoundException if user with specified name couldn't be found
	 */
	public String createUserCookie(UserCookie userCookie, String userName) throws BeanNotFoundException;

	/**
	 * Deletes all cookies for user.
	 *
	 * @param userName Name of user to delete cookies for
	 */
	public void deleteUserCookies(String userName);
	
	public List<Long> loadUserMenus(String userName);
	
	public List<MenuItem> loadTopTree(String columnName, String sort) ;
	
	public PartialCollection loadUsersByOrgId(QueryInfo queryInfo);

}
