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
import org.inwiss.platform.model.core.Org;
import org.inwiss.platform.persistence.core.LocalizableDAO;
import org.inwiss.platform.persistence.core.OrgDAO;
import org.inwiss.platform.security.service.OrgManager;
import org.inwiss.platform.service.exception.BeanNotFoundException;
import org.inwiss.platform.service.exception.ParentItemNotFoundException;

/**
 * <p>Implementation of OrgManager</p>
 * <p><a href="OrgManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.17 $ $Date: 2006/08/03 10:07:43 $
 */
public class OrgManagerImpl extends BaseManagerImpl implements OrgManager {

	/**
	 * Org DAO
	 */
	protected OrgDAO orgDAO;

	/**
	 * Localizable DAO
	 */
	protected LocalizableDAO localizableDAO;

	/**
	 * Creates new instance of OrgManagerImpl
	 */
	public OrgManagerImpl() {
	}

    /**
     * Sets DAO for operating with org items
     *
     * @param menuDAO the DAO to set
     */
	public void setOrgDAO(OrgDAO orgDAO) {
		this.orgDAO = orgDAO;
	}

    /**
     * Sets DAO for operating with org items
     *
     * @param localizableDAO the DAO to set
    */
	public void setLocalizableDAO(LocalizableDAO localizableDAO) {
		this.localizableDAO = localizableDAO;
	}
 
	/**
	 * @see com.blandware.atleap.service.core.OrgManager#createOrgItem(com.blandware.atleap.model.core.Org, Long, Long)
	 */
	public Long createOrgItem(Org org, Long parentItemId, Long ownerId) throws BeanNotFoundException {

		if ( log.isDebugEnabled() ) {
			log.debug("Creating new org item...");
		}

		// search for parent's and owner's existence
		Org parentItem = null;
		if ( parentItemId != null ) {
			parentItem = orgDAO.retrieveOrgItem(parentItemId);
			if ( parentItem == null ) {
				String errorMessage = "Org with ID=" + parentItemId + " does not exist";
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
		Long orgId = orgDAO.createOrgItem(org, parentItem, owner);

		// if item has no identifier and it is not the redifinition for another one, set identifier and update item
		if ( (org.getIdentifier() == null || org.getIdentifier().trim().length() == 0) && !org.isRedefinition() ) {
			org.setIdentifier("OrgItem-" + orgId);
		}

		if ( log.isDebugEnabled() ) {
			log.debug("New org item has been created succesfully. Its ID is " + orgId);
		}

		return orgId;
	}

	/**
	 * @see com.blandware.atleap.service.core.OrgManager#retrieveOrgItem(Long)
	 */
	public Org retrieveOrgItem(Long menuItemId) {
		return orgDAO.retrieveOrgItem(menuItemId);
	}

	/**
	 * @see com.blandware.atleap.service.core.OrgManager#updateOrgItem(com.blandware.atleap.model.core.Org, Long, Long)
	 */
	public void updateOrgItem(Org org, Long parentItemId, Long ownerId) throws BeanNotFoundException {

		// remove item from cache in order to prevent Hibernate from assigning new version number
		orgDAO.removeFromCache(org);

		if ( log.isDebugEnabled() ) {
			log.debug("Updating org item with ID=" + org.getId() + "...");
		}

		// search for parent's and owner's existence
		Org parentItem = null;
		if ( parentItemId != null ) {
			parentItem = orgDAO.retrieveOrgItem(parentItemId);
			if ( parentItem == null ) {
				String errorMessage = "Org with ID=" + parentItemId + " does not exist";
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

		// update org item
		orgDAO.updateOrgItem(org, parentItem, owner);

		if ( log.isDebugEnabled() ) {
			log.debug("Org item has been updated successfully.");
		}

	}

	/**
	 * @see com.blandware.atleap.service.core.OrgManager#deleteOrgItem(Long)
	 */
	public void deleteOrgItem(Long orgId) throws BeanNotFoundException {
		Org org = orgDAO.retrieveOrgItem(orgId);
		if ( org == null ) {
			String errorMessage = "No content resource with ID=" + org + "could be found";
			throw new BeanNotFoundException(errorMessage);
		}
		orgDAO.deleteOrgItem(org);
	}

	/**
	 * @see com.blandware.atleap.service.core.OrgManager#listOrgItems(com.blandware.atleap.common.util.QueryInfo)
	 */
	public PartialCollection listOrgItems(QueryInfo queryInfo) {
		return orgDAO.listOrgItems(queryInfo);
	}

	/**
	 * @see com.blandware.atleap.service.core.OrgManager#findOrgItemByIdentifierAndParentAndOwner(String, Long, Long)
	 */
	public Org findOrgItemByIdentifierAndParentAndOwner(String identifier, Long parentItemId, Long ownerId) {
		return orgDAO.findOrgItemByIdentifierAndParentAndOwner(identifier, parentItemId, ownerId);
	}


	/**
	 * @see com.blandware.atleap.service.core.OrgManager#findOrgItemByPositionAndParentAndOwner(Integer, Long, Long)
	 */
	public Org findOrgItemByPositionAndParentAndOwner(Integer position, Long parentItemId, Long ownerId) {
		return orgDAO.findOrgItemByPositionAndParentAndOwner(position, parentItemId, ownerId);
	}

	/**
	 * @see com.blandware.atleap.service.core.OrgManager#findOrgItemsByParentAndOwner(Long, Long)
	 */
	public List findOrgItemsByParentAndOwner(Long parentItemId, Long ownerId) {
		return orgDAO.findOrgItemsByParentAndOwner(parentItemId, ownerId);
	}

	/**
	 * @see com.blandware.atleap.service.core.OrgManager#findRedefinitionItem(Long, Long)
	 */
	public Org findRedefinitionItem(Long origItemId, Long ownerId) {
		return orgDAO.findRedefinitionItem(origItemId, ownerId);
	}

}
