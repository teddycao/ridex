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
package org.inwiss.platform.persistence.hibernate.core;



import java.util.List;

import org.inwiss.platform.model.core.Localizable;
import org.inwiss.platform.persistence.core.LocalizableDAO;

/**
 * <p>Hibernate implementation of LocalizableDAO
 * </p>
 * <p><a href="LocalizableDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.5 $ $Date: 2008/08/01 15:50:17 $
 */
public class LocalizableDAOHibernate extends BaseDAOHibernate implements LocalizableDAO {

	/**
	 * Creates new instance of LocalizableDAOHibernate
	 */
	public LocalizableDAOHibernate() {
	}

	/**
	 * @see com.blandware.atleap.persistence.core.LocalizableDAO#retrieveLocalizable(java.lang.Long)
	 */
	public Localizable retrieveLocalizable(Long localizableId) {
		return (Localizable) getHibernateTemplate().get(Localizable.class, localizableId);
	}

    /**
	 * Finds a localizable by id in list of localizables.
     *
     * @param localizables list to search
     * @param id           ID of localizable
     * @return localizable
     */
    protected Localizable findLocalizableById(List localizables, Long id) {
        for (int i = 0; i < localizables.size(); i++) {
            Localizable localizable = (Localizable) localizables.get(i);
            if (localizable.getId().equals(id)) {
                return localizable;
            }
        }
        return null;
    }
}
