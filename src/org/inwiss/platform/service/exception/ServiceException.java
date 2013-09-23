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
package org.inwiss.platform.service.exception;

import org.apache.commons.lang.exception.NestableException;


/**
 * <p>A general ServiceException that can be thrown to indicate an error
 * in business logic.
 * </p>
 * <p><a href="ServiceException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 */
public class ServiceException extends NestableException {
	//~ Constructors ===========================================================

	/**
	 * Constructor for ServiceException.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * Constructor for ServiceException.
	 *
	 * @param message error message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor for ServiceException.
	 *
	 * @param message error message
	 * @param cause wrapped Throwable
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for ServiceException.
	 *
	 * @param cause wrapped Throwable
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
