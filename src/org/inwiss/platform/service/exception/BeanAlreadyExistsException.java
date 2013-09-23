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

/**
 * <p>Thrown when trying to create bean that alredy exists</p>
 * <p><a href="BeanAlreadyExistsException.java.html"><i>View Source</i></a></p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.6 $ $Date: 2006/08/03 10:07:43 $
 */
public class BeanAlreadyExistsException extends Exception {

	/**
	 * Creates a new instance of BeanAlreadyExistsException without detail message.
	 */
	public BeanAlreadyExistsException() {
	}


	/**
	 * Constructs an instance of BeanAlreadyExistsException with the specified detail message.
	 *
	 * @param msg The detail message.
	 */
	public BeanAlreadyExistsException(String msg) {
		super(msg);
	}

	/**
	 * Creates an instance of BeanAlreadyExistsException with nested exception
	 *
	 * @param e Nested exception
	 */
	public BeanAlreadyExistsException(Exception e) {
		super(e);
	}

	/**
	 * Overrides toString() method in java.lang.Exception
	 */
	public String toString() {
		return getClass().getName() + (getMessage() != null ? ": " + getMessage() : "");
	}

}