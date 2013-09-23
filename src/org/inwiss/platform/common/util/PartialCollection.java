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
package org.inwiss.platform.common.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <p>This collection contains part of some collection of entities and
 * total number of elements of initial collection
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.9 $ $Date: 2006/08/03 10:07:33 $
 */
public class PartialCollection<BaseObject> implements Collection, Serializable {

	/**
	 * Collection of entities (part of some another collection)
	 */
	private Collection collection;

	/**
	 * Total number of elements in collection this collection is part of
	 */
	private Integer total;

	/**
	 * Creates new instance of PartialCollection with specified collection and total
	 *
	 * @param collection Part of some collection of entities
	 * @param total      Total size of collection, which part is contained in this instance
	 */
	public PartialCollection(Collection collection, int total) {
		this(collection, new Integer(total));
	}

	/**
	 * Creates an empty instance of PartialCollection
	 */
	public PartialCollection() {
		this(new ArrayList(), 0);
	}

	/**
	 * Creates new instance of PartialCollection with specified collection and total
	 *
	 * @param collection Part of some collection of entities
	 * @param total      Total size of collection, which part is contained in this instance
	 */
	public PartialCollection(Collection collection, Integer total) {
		this.collection = collection;
		this.total = total;
	}

	/**
	 * Represents this collection as list
	 *
	 * @return list representing this collection
	 */
	public List asList() {
		List result = null;
		if ( collection != null ) {
			if ( !(collection instanceof List) ) {
				result = new ArrayList(collection);
			} else {
				result = (List) collection;
			}
		}
		return result;
	}

    /**
     * Gets the size of part of initial collection that is contained here
     *
     * @return number of elements in partial collection
     */
	public int size() {
		return collection.size();
	}

    /**
     * Clears the partial collection
     */
	public void clear() {
		collection.clear();
	}

    /**
     * Figures out is partial collection empty
     *
     * @return <code>true</code> if this collection is empty
     */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	public Object[] toArray() {
		return collection.toArray();
	}

	public boolean add(Object o) {
		return collection.add(o);
	}

	public boolean contains(Object o) {
		return collection.contains(o);
	}

	public boolean remove(Object o) {
		return collection.remove(o);
	}

	public boolean addAll(Collection c) {
		return collection.addAll(c);
	}

	public boolean containsAll(Collection c) {
		return collection.containsAll(c);
	}

	public boolean removeAll(Collection c) {
		return collection.removeAll(c);
	}

	public boolean retainAll(Collection c) {
		return collection.retainAll(c);
	}

	public Iterator iterator() {
		return collection.iterator();
	}

	public Object[] toArray(Object[] a) {
		return collection.toArray(a);
	}

    /**
     * Gets total number of elements in initial collection
     *
     * @return total number of elements
     */
	public Integer getTotal() {
		return total;
	}

//	public String toString() {
//		StringBuffer sb = new StringBuffer("[rows=[");
//		for ( Iterator i = collection.iterator(); i.hasNext(); ) {
//			sb.append(i.next());
//			if ( i.hasNext() ) {
//				sb.append(", ");
//			}
//		}
//		sb.append("];\n");
//		sb.append("]");
//		return sb.toString();
//	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
