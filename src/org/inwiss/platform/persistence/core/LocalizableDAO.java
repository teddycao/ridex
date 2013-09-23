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
package org.inwiss.platform.persistence.core;

import org.inwiss.platform.model.core.Localizable;


/**
 * <p>DAO for localizable</p>
 * <p><a href="LocalizableDAO.java.html"><i>View Source</i></a></p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.4 $ $Date: 2006/08/03 10:07:36 $
 */
public interface LocalizableDAO extends DAO {

	/**
	 * Retrieves localizable with specified ID
	 *
	 * @param localizableId ID to search by
	 * @return Localizable or null if no localizable with specified ID exists in database
	 */
	public Localizable retrieveLocalizable(Long localizableId);
}
