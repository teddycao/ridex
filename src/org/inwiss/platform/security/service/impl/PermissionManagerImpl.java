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
import org.inwiss.platform.model.core.Localizable;
import org.inwiss.platform.model.core.MenuItem;
import org.inwiss.platform.model.core.Permission;
import org.inwiss.platform.persistence.core.LocalizableDAO;
import org.inwiss.platform.persistence.core.MenuDAO;
import org.inwiss.platform.persistence.core.PermissionDAO;
import org.inwiss.platform.security.service.MenuManager;
import org.inwiss.platform.security.service.PermissionManager;
import org.inwiss.platform.service.exception.BeanNotFoundException;
import org.inwiss.platform.service.exception.ParentItemNotFoundException;

/**
 * <p>Implementation of MenuManager</p>
 * <p><a href="MenuManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.17 $ $Date: 2006/08/03 10:07:43 $
 */
public class PermissionManagerImpl extends BaseManagerImpl implements PermissionManager {

	/**
	 * Menu DAO
	 */
	protected PermissionDAO permDAO;

	/**
	 * Localizable DAO
	 */
	protected LocalizableDAO localizableDAO;

	/**
	 * Creates new instance of MenuManagerImpl
	 */
	public PermissionManagerImpl() {
	}

    /**
     * Sets DAO for operating with menu items
     *
     * @param menuDAO the DAO to set
     */
	public void setPermDAO(PermissionDAO permDAO) {
		this.permDAO = permDAO;
	}

    /**
     * Sets DAO for operating with menu items
     *
     * @param localizableDAO the DAO to set
    */
	public void setLocalizableDAO(LocalizableDAO localizableDAO) {
		this.localizableDAO = localizableDAO;
	}
 
	

	

	/**
	 * @see com.blandware.atleap.service.core.MenuManager#deleteMenuItem(Long)
	 */
	public void deletePermission(Long permId) throws BeanNotFoundException {
		Permission perm = permDAO.retrievePermission(permId);
		if ( perm == null ) {
			String errorMessage = "No content resource with ID=" + permId + "could be found";
			throw new BeanNotFoundException(errorMessage);
		}
		permDAO.deletePermission(perm);
	}


	public Long createPermission(Permission perm) throws BeanNotFoundException {
		if ( log.isDebugEnabled() ) {
			log.debug("Creating new menu Permission...");
		}

		permDAO.createPermission(perm);

		if ( log.isDebugEnabled() ) {
			log.debug("New menu item has been created succesfully. Its ID is " );
		}

		return null;
	}


	public Permission retrievePermission(Long permid) {
		return permDAO.retrievePermission(permid);
	}

	
	public void updatePermission(Permission perm) throws BeanNotFoundException {
	
		permDAO.updatePermission(perm);
		if ( log.isDebugEnabled() ) {
			log.debug("Menu item has been updated successfully.");
		}
		
	}

	

	
	public PartialCollection listPermissions(QueryInfo queryInfo) {
		
		return permDAO.listPermissions(queryInfo);
	}

	
}
