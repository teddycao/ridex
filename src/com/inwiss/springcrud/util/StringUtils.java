package com.inwiss.springcrud.util;

import java.util.List;

public class StringUtils {
	/**
     * Check if the string supplied is empty.  A String is empty when it is null or when the length is 0
     * @param string The string to check
     * @return true if the string supplied is empty
     */
    public static final boolean isEmpty(String string)
    {
    	return string==null || string.length()==0;
    }
    
    /**
     * Check if the stringBuffer supplied is empty.  A StringBuffer is empty when it is null or when the length is 0
     * @param string The stringBuffer to check
     * @return true if the stringBuffer supplied is empty
     */
    public static final boolean isEmpty(StringBuffer string)
    {
    	return string==null || string.length()==0;
    }
    
    /**
     * Check if the string array supplied is empty.  A String array is empty when it is null or when the number of elements is 0
     * @param string The string array to check
     * @return true if the string array supplied is empty
     */
    public static final boolean isEmpty(String[] strings)
    {
        return strings==null || strings.length==0;
    }

    /**
     * Check if the array supplied is empty.  An array is empty when it is null or when the length is 0
     * @param array The array to check
     * @return true if the array supplied is empty
     */
    public static final boolean isEmpty(Object[] array)
    {
     return array==null || array.length==0;
    }
    
    /**
     * Check if the list supplied is empty.  An array is empty when it is null or when the length is 0
     * @param list the list to check
     * @return true if the supplied list is empty
     */
    public static final boolean isEmpty(List<?> list)
    {
     return list==null || list.size()==0;
    }
    
}
