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
 * <p>Thrown when parent menu item with specified ID couldn't be found (e.g. it has already been deleted)</p>
 * <p><a href="ParentItemNotFoundException.java.html"><i>View Source</i></a></p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.4 $ $Date: 2006/08/03 10:07:44 $
 */
public class ParentItemNotFoundException extends BeanNotFoundException {

	/**
	 * Creates a new instance of ParentItemNotFoundException without detail message.
	 */
	public ParentItemNotFoundException() {
	}


	/**
	 * Constructs an instance of ParentItemNotFoundException with the specified detail message.
	 *
	 * @param msg The detail message.
	 */
	public ParentItemNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Creates an instance of ParentItemNotFoundException with nested exception
	 *
	 * @param e Nested exception
	 */
	public ParentItemNotFoundException(Exception e) {
		super(e);
	}

	/**
	 * Overrides toString() method in java.lang.Exception
	 */
	public String toString() {
		return getClass().getName() + (getMessage() != null ? ": " + getMessage() : "");
	}

}