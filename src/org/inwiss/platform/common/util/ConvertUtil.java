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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inwiss.platform.common.Constants;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.*;


/**
 * <p>Utility class to perform conversions.</p>
 * <p><a href="ConvertUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.19 $ $Date: 2006/08/03 10:07:33 $
 */
public final class ConvertUtil {
	//~ Static fields/initializers =============================================

	//~ Methods ================================================================

	/**
	 * Converts a ResourceBundle to a Map object.
	 *
	 * @param rb a given resource bundle
	 * @return Map a populated map
	 */
	public static Map convertBundleToMap(ResourceBundle rb) {
		Map map = new HashMap();

		for ( Enumeration keys = rb.getKeys(); keys.hasMoreElements(); ) {
			String key = (String) keys.nextElement();
			map.put(key, rb.getString(key));
		}

		return map;
	}

	/**
	 * Converts a ResourceBundle to a Properties object.
	 *
	 * @param rb a given resource bundle
	 * @return Properties a populated properties object
	 */
	public static Properties convertBundleToProperties(ResourceBundle rb) {
		Properties props = new Properties();

		for ( Enumeration keys = rb.getKeys(); keys.hasMoreElements(); ) {
			String key = (String) keys.nextElement();
			props.put(key, rb.getString(key));
		}

		return props;
	}

	/**
	 * Converts list to string concatenating list elements with the delimiter
	 *
	 * @param list      List to convert to string
	 * @param delimiter Delimiter to put between elements
	 * @return List members, delimited by specifed delimiter.
	 */
	public static String convertListToString(List list, String delimiter) {
		return convertListToString(list, delimiter, null);
	}

	/**
	 * Converts list to string concatenating list elements with the delimiter.
     * Each element is additionally enclosed in <code>encloser</code> chars.
	 *
	 * @param list      List to convert to string
	 * @param delimiter Delimiter to put between elements
	 * @param encloser  Character to enclose each element
	 * @return List members, delimited by specifed delimiter. Each element enclosed with specified character
	 */
	public static String convertListToString(List list, String delimiter, char encloser) {
		return convertListToString(list, delimiter, new Character(encloser));
	}

	/**
	 * Converts list to string concatenating list elements with the delimiter.
     * Each element is additionally enclosed in <code>encloser</code> chars.
	 *
	 * @param list      List to convert to string
	 * @param delimiter Delimiter to put between elements
	 * @param encloser  Character to enclose each element
	 * @return List members, delimited by specifed delimiter. Each element enclosed with specified character
	 */
	private static String convertListToString(List list, String delimiter, Character encloser) {

		if ( list == null || list.size() == 0 ) {
			return new String();
		}

		StringBuffer sb = new StringBuffer();
		for ( Iterator i = list.iterator(); i.hasNext(); ) {
			String next = String.valueOf(i.next());
			if ( encloser != null ) {
				next = encloser + next + encloser;
			}
			sb.append(next);
			if ( i.hasNext() ) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	/**
	 * Converts string to list. The string is assumed to be a sequence of some
     * elements separated with delimiter.
	 *
	 * @param string    String to convert to list
	 * @param delimiter Delimiter of list elements
	 * @param trim      Whether or not to trim tokens befor putting them in list
	 * @return List
	 */
	public static List convertStringToList(String string, String delimiter, boolean trim) {
		if ( string == null || string.length() == 0 ) {
			return new LinkedList();
		}
		String[] members = string.split(delimiter);
		List list = Collections.synchronizedList(new LinkedList());
		for ( int i = 0; i < members.length; i++ ) {
			String member = members[i];
			if ( trim ) {
				member = member.trim();
			}
			list.add(member);
		}
		return list;
	}

	/**
	 * Creates set from array of objects
	 *
	 * @param anArray Array of objects to create set from
	 * @return Set that contains objects from array
	 */
	public static Set convertArrayToSet(Object[] anArray) {
		Set set = new HashSet();
		for ( int i = 0; i < anArray.length; i++ ) {
			set.add(anArray[i]);
		}
		return set;
	}

	/**
	 * Converts all String values in specified list to <code>java.lang.Long</code>.
	 * If there is instance of another than <code>java.lang.String</code> class is found, <code>java.lang.ClassCastException</code> will be thrown.
	 *
	 * @param values List of values to convert to <code>java.lang.Long[]</code>
	 * @return <code>java.lang.Long[]</code>
	 */
	public static Long[] convertToLongArray(List values) {
		Long[] result = new Long[values.size()];
		for ( int i = 0; i < values.size(); i++ ) {
			Object value = values.get(i);
			if ( !(value instanceof String) ) {
				throw new ClassCastException("Unable to convert instance of class " + value.getClass().getName() + " to java.lang.Long");
			}
			result[i] = Long.valueOf((String) value);
		}
		return result;
	}

	/**
	 * Converts byte array to string using default encoding
	 *
	 * @param content Byte array to convert to string
	 * @return string resulted from converting byte array using defalt encoding
	 */
	public static String convertToString(byte[] content) {
		return convertToString(content, null);
	}

	/**
	 * Converts byte array to string according to specified encoding
	 *
	 * @param content  Byte array to convert to string
	 * @param encoding Encoding string, if <code>null</code> default is used
	 * @return string resulted from converting byte array
	 */
	public static String convertToString(byte[] content, String encoding) {
		if ( content == null ) {
			return null;
		}
		if ( encoding == null ) {
			encoding = Constants.DEFAULT_ENCODING;
		}

		String value = null;
		try {
			value = new String(content, encoding);
		} catch ( UnsupportedEncodingException ex ) {
			return new String(content);
		}
		return value;
	}


	/**
	 * Converts mapping; only values are converted (from byte arrays to strings,
     * keys remain same). Default encoding is used.
	 *
	 * @param map Map to convert
	 * @return converted map
	 */
    public static Map convertMapToStrings(Map map) {
        return convertMapToStrings(map, null);
    }


	/**
	 * Converts mapping; only values are converted (from byte arrays to strings,
     * keys remain same). Given encoding is used.
	 *
	 * @param map Map to convert
     * @param encoding Encoding to use when converting
	 * @return converted map
	 */
    public static Map convertMapToStrings(Map map, String encoding) {
        Map result = new HashMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            byte[] content = (byte[]) map.get(key);
            result.put(key, convertToString(content, encoding));
        }
        return result;
    }


	/**
	 * Converts string to byte array using default encoding
	 *
	 * @param content String to convert to array
	 * @return byte array resulted from converting string using default encoding
	 */
	public static byte[] convertToByteArray(String content) {
		return convertToByteArray(content, null);
	}

	/**
	 * Converts string to byte array according to specified encoding
	 *
	 * @param content  String to convert to array
	 * @param encoding Encoding string, if <code>null</code> default is used
	 * @return byte array
	 */
	public static byte[] convertToByteArray(String content, String encoding) {

		Log log = LogFactory.getLog(ConvertUtil.class);

		if ( content == null ) {
			return null;
		}
		if ( encoding == null ) {
			encoding = Constants.DEFAULT_ENCODING;
		}

		if ( log.isDebugEnabled() ) {
			log.debug("Converting to byte array using: " + encoding);
		}

		byte[] value;
		try {
			value = content.getBytes(encoding);
		} catch ( UnsupportedEncodingException ex ) {
			if ( log.isWarnEnabled() ) {
				log.warn(ex);
			}
			return content.getBytes();
		}
		return value;
	}


	/**
	 * Converts mapping; only values are converted (from strings to byte arrays,
     * keys remain same). Default encoding is used.
	 *
	 * @param map Map to convert
	 * @return converted map
	 */
    public static Map convertMapToByteArrays(Map map) {
        return convertMapToByteArrays(map, null);
    }


	/**
	 * Converts mapping; only values are converted (from strings to byte arrays,
     * keys remain same). Given encoding is used.
	 *
	 * @param map Map to convert
     * @param encoding Encoding to use when converting
	 * @return converted map
	 */
    public static Map convertMapToByteArrays(Map map, String encoding) {
        Map result = new HashMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            String content = (String) map.get(key);
            result.put(key, convertToByteArray(content, encoding));
        }
        return result;
    }


	/**
	 * Converts primitive array to array of objects. Each element in returned array will have run-time class
	 * equivalent to its primitive type (e.g. <code>java.lang.Integer</code> is object equivalent to <code>int</code>,
	 * <code>java.lang.Boolean</code> is object equivalent to <code>boolean</code>, etc.)
	 *
	 * @param primitiveArray Array of primitives which needs to be converted to objects
	 * @return Array of object, each element is object equivalent to corresponding primitive value
	 * @throws IllegalArgumentException if specified argument is not a primitive array
	 */
	public static Object[] convertPrimitivesToObjects(Object primitiveArray) {
		if ( primitiveArray == null ) {
			return null;
		}

		if ( !primitiveArray.getClass().isArray() ) {
			throw new IllegalArgumentException("Specified object is not array");
		}

		if ( primitiveArray instanceof Object[] ) {
			throw new IllegalArgumentException("Specified object is not primitive array");
		}

		int length = Array.getLength(primitiveArray);
		Object[] result = new Object[length];
		for ( int i = 0; i < length; i++ ) {
			result[i] = Array.get(primitiveArray, i);
		}

		return result;
	}

	/**
	 * Converts collection, specified in argument to the instance of <code>java.util.List</code>. Supported types
	 * include: <ul type="disc">
	 * <li><code>java.util.Collection, java.util.Set, java.util.SortedSet, java.util.List</code> - result list will contain all elements from specified collection, set or list</li>
	 * <li><code>java.util.Enumeration</code> - result list will contain all elements from this enumeration in the same order</li>
	 * <li><code>java.util.Iterator</code> - result list will contain all elements from collection, iterated by this iterator in the same order</li>
	 * <li><code>java.util.Map, java.util.SortedMap</code> - result list will contain all entries (instances of <code>java.util.Map$Entry</code>)</li>
	 * <li><code>java.lang.String</code> - result list will contain all characters, each one wrapped in <code>java.lang.Character</code></li>
	 * <li><code>java.lang.Object[]</code> - result list will be dynamic equivalent for specified array</li>
	 * <li>any primitive array - result list will contain elements from array, each wrapped in instance of equivalent class
	 * (e.g. <code>java.lang.Integer</code> is object equivalent to <code>int</code>,
	 * <code>java.lang.Boolean</code> is object equivalent to <code>boolean</code>, etc.)</li>
	 * </ul>
	 * @param collection Collection to convert to list
	 * @return List, containing all elements from collection according to rules, specified above
	 */
	public static List convertCollectionToList(Object collection) {

		if ( collection == null ) {
			return null;
		}

		List list = null;

		if ( collection instanceof Collection ) {
			list = new ArrayList((Collection) collection);
		} else if ( collection instanceof Enumeration ) {
			list = new ArrayList();
			Enumeration e = (Enumeration) collection;
			while ( e.hasMoreElements() ) {
				list.add(e.nextElement());
			}
		} else if ( collection instanceof Iterator ) {
			list = new ArrayList();
			Iterator i = (Iterator) collection;
			while ( i.hasNext() ) {
				list.add(i.next());
			}
		} else if ( collection instanceof Map ) {
			list = new ArrayList(((Map) collection).entrySet());
		} else if ( collection instanceof String ) {
			list = Arrays.asList(convertPrimitivesToObjects(((String) collection).toCharArray()));
		} else if ( collection instanceof Object[] ) {
			list = Arrays.asList((Object[]) collection);
		} else if ( collection.getClass().isArray() ) {
			list = Arrays.asList(convertPrimitivesToObjects(collection));
		} else {
			// type is not supported
			throw new IllegalArgumentException("Class '" + collection.getClass().getName() + "' is not convertable to java.util.List");
		}

		return list;
	}
}
