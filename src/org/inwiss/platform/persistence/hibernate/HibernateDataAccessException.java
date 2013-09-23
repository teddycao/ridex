/*****************************************************************************
 * Copyright (C) NCI Organization. All rights reserved.                      *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the NCI      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 *****************************************************************************/

package org.inwiss.platform.persistence.hibernate;
import org.apache.commons.lang.exception.NestableException;
/**
 *@author <a href="mailto:raidery@hotmail.com">Raider Yang</a>
 */
public class HibernateDataAccessException extends NestableException {

	public HibernateDataAccessException(String error) {
		super(error);
	}
	public HibernateDataAccessException(Throwable root) {
		super(root);
	}
	public HibernateDataAccessException(String error, Throwable t) {
		super(error, t);
	}
	
	
}
