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
import org.inwiss.platform.persistence.core.LocalizableDAO;
import org.inwiss.platform.persistence.core.MenuDAO;
import org.inwiss.platform.security.service.MenuManager;
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
public class MenuManagerImpl extends BaseManagerImpl implements MenuManager {

	/**
	 * Menu DAO
	 */
	protected MenuDAO menuDAO;

	/**
	 * Localizable DAO
	 */
	protected LocalizableDAO localizableDAO;

	/**
	 * Creates new instance of MenuManagerImpl
	 */
	public MenuManagerImpl() {
	}

    /**
     * Sets DAO for operating with menu items
     *
     * @param menuDAO the DAO to set
     */
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
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
	 * @see com.blandware.atleap.service.core.MenuManager#createMenuItem(com.blandware.atleap.model.core.MenuItem, Long, Long)
	 */
	public Long createMenuItem(MenuItem menuItem, Long parentItemId, Long ownerId) throws BeanNotFoundException {

		if ( log.isDebugEnabled() ) {
			log.debug("Creating new menu item...");
		}

		// search for parent's and owner's existence
		MenuItem parentItem = null;
		if ( parentItemId != null ) {
			parentItem = menuDAO.retrieveMenuItem(parentItemId);
			if ( parentItem == null ) {
				String errorMessage = "MenuItem with ID=" + parentItemId + " does not exist";
				if ( log.isErrorEnabled() ) {
					log.error(errorMessage);
				}
				throw new ParentItemNotFoundException(errorMessage);
			}
		}
		Localizable owner = null;
		/** TODO Raidery 2011-03-22
		
		if ( ownerId != null ) {
			owner = localizableDAO.retrieveLocalizable(ownerId);
			if ( owner == null ) {
				String errorMessage = "Localizable with ID=" + ownerId + " does not exist";
				if ( log.isErrorEnabled() ) {
					log.error(errorMessage);
				}
				throw new OwnerNotFoundException(errorMessage);
			}
		}*/

		// create item
		Long menuItemId = menuDAO.createMenuItem(menuItem, parentItem, owner);

		// if item has no identifier and it is not the redifinition for another one, set identifier and update item
		if ( (menuItem.getIdentifier() == null || menuItem.getIdentifier().trim().length() == 0) && !menuItem.isRedefinition() ) {
			menuItem.setIdentifier("MenuItem-" + menuItemId);
			//menuDAO.updateMenuItem(menuItem, parentItem, owner);
		}

		if ( log.isDebugEnabled() ) {
			log.debug("New menu item has been created succesfully. Its ID is " + menuItemId);
		}

		return menuItemId;
	}

	/**
	 * @see com.blandware.atleap.service.core.MenuManager#retrieveMenuItem(Long)
	 */
	public MenuItem retrieveMenuItem(Long menuItemId) {
		return menuDAO.retrieveMenuItem(menuItemId);
	}

	/**
	 * @see com.blandware.atleap.service.core.MenuManager#updateMenuItem(com.blandware.atleap.model.core.MenuItem, Long, Long)
	 */
	public void updateMenuItem(MenuItem menuItem, Long parentItemId, Long ownerId) throws BeanNotFoundException {

		// remove item from cache in order to prevent Hibernate from assigning new version number
		menuDAO.removeFromCache(menuItem);

		if ( log.isDebugEnabled() ) {
			log.debug("Updating menu item with ID=" + menuItem.getId() + "...");
		}

		// search for parent's and owner's existence
		MenuItem parentItem = null;
		if ( parentItemId != null ) {
			parentItem = menuDAO.retrieveMenuItem(parentItemId);
			if ( parentItem == null ) {
				String errorMessage = "MenuItem with ID=" + parentItemId + " does not exist";
				if ( log.isErrorEnabled() ) {
					log.error(errorMessage);
				}
				throw new ParentItemNotFoundException(errorMessage);
			}
		}

		Localizable owner = null;
		/** TODO Raidery 2011-03-22
		if ( ownerId != null ) {
			owner = localizableDAO.retrieveLocalizable(ownerId);
			if ( owner == null ) {
				String errorMessage = "Localizable with ID=" + ownerId + " does not exist";
				if ( log.isErrorEnabled() ) {
					log.error(errorMessage);
				}
				throw new OwnerNotFoundException(errorMessage);
			}
		}*/

		// update menu item
		menuDAO.updateMenuItem(menuItem, parentItem, owner);

		if ( log.isDebugEnabled() ) {
			log.debug("Menu item has been updated successfully.");
		}

	}

	/**
	 * @see com.blandware.atleap.service.core.MenuManager#deleteMenuItem(Long)
	 */
	public void deleteMenuItem(Long menuItemId) throws BeanNotFoundException {
		MenuItem menuItem = menuDAO.retrieveMenuItem(menuItemId);
		if ( menuItem == null ) {
			String errorMessage = "No content resource with ID=" + menuItem + "could be found";
			throw new BeanNotFoundException(errorMessage);
		}
		menuDAO.deleteMenuItem(menuItem);
	}

	/**
	 * @see com.blandware.atleap.service.core.MenuManager#listMenuItems(com.blandware.atleap.common.util.QueryInfo)
	 */
	public PartialCollection listMenuItems(QueryInfo queryInfo) {
		return menuDAO.listMenuItems(queryInfo);
	}

	/**
	 * @see com.blandware.atleap.service.core.MenuManager#findMenuItemByIdentifierAndParentAndOwner(String, Long, Long)
	 */
	public MenuItem findMenuItemByIdentifierAndParentAndOwner(String identifier, Long parentItemId, Long ownerId) {
		return menuDAO.findMenuItemByIdentifierAndParentAndOwner(identifier, parentItemId, ownerId);
	}


	/**
	 * @see com.blandware.atleap.service.core.MenuManager#findMenuItemByPositionAndParentAndOwner(Integer, Long, Long)
	 */
	public MenuItem findMenuItemByPositionAndParentAndOwner(Integer position, Long parentItemId, Long ownerId) {
		return menuDAO.findMenuItemByPositionAndParentAndOwner(position, parentItemId, ownerId);
	}

	/**
	 * @see com.blandware.atleap.service.core.MenuManager#findMenuItemsByParentAndOwner(Long, Long)
	 */
	public List findMenuItemsByParentAndOwner(Long parentItemId, Long ownerId) {
		return menuDAO.findMenuItemsByParentAndOwner(parentItemId, ownerId);
	}

	/**
	 * @see com.blandware.atleap.service.core.MenuManager#findRedefinitionItem(Long, Long)
	 */
	public MenuItem findRedefinitionItem(Long origItemId, Long ownerId) {
		return menuDAO.findRedefinitionItem(origItemId, ownerId);
	}

}
