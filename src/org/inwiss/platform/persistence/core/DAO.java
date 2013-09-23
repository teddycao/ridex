package org.inwiss.platform.persistence.core;

import org.inwiss.platform.model.BaseObject;





/**
 * <p>Data Access Object (DAO) interface.   This is an interface
 * used to tag our DAO classes and to provide common methods to all DAOs.
 * </p>
 * <p><a href="DAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 * @version $Revision: 1.7 $ $Date: 2005/03/22 13:07:22 $
 */
public interface DAO {

	/**
	 * Removes object from cache
	 *
	 * @param object Object to be removed from cache
	 */
	public void removeFromCache(BaseObject object);

	/**
	 * Reloads object from database (synchronizes state)
	 * @param object Object to reload
	 */
	public void reload(Object object);
}