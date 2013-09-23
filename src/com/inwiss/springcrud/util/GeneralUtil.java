/*******************************************************************************
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the NCI * style
 * license a copy of which has been included with this distribution in * the
 * LICENSE.txt file. * *
 ******************************************************************************/

package com.inwiss.springcrud.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






/*
 * 工具方法,一些常用的相对独立的处理方法
 * @author <a href="mailto:raidery@hotmail.com">Raider Yang </a> Created on
 * 2004-2-25 在此添加详细注释
 */
public class GeneralUtil {
	private static Logger logger = LoggerFactory.getLogger(GeneralUtil.class);
	public static final String DEFAULT_VAR_PREFIX = "${";
	public static final String DEFAULT_VAR_SUFFIX = "}";
	
	public static final String TEMP_VAR_PREFIX = "$#";
	public static final String TEMP_VAR_SUFFIX = "##";

	private String varPrefix = DEFAULT_VAR_PREFIX;
	private String varSuffix = DEFAULT_VAR_SUFFIX;
	
	public static final String CONTEXT_DIR = "framework.system.context.dir";
    public static final String EMPTY_STRING = "";


    


    
    
    public static String qualifier(String qualifiedName) {
        int loc = qualifiedName.lastIndexOf(".") + 1;
        if ( loc<0 ) {
            return EMPTY_STRING;
        }
        else {
            return qualifiedName.substring(loc);
        }
    }
    
	/**
	 * 
	 * @param date
	 * @param defalut
	 * @return @throws
	 *         ParseException
	 */
	public static Date getFormateDate(String sdate, String format)  {
        if(format == null) format="yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date rdate = new Date();
		try {
          if(sdate != null)
             return sdf.parse(sdate);
          
          } catch (ParseException pex) {
        	  logger.error("解析日期出错: "+pex.getMessage());
        }
        return rdate;
	}

	public static String getTime(String pattern) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(now);
	}
	
	public String testMethod(String str) {
		return "distinct: " + str;
	}
    
    


	
	/**
	 * Converts all instances of ${...} in <code>expression</code> to the
	 * value returned be found on the stack (null is returned), then the entire
	 * variable ${...} is not displayed, just as if the item was on the stack
	 * but returned an empty string.
	 * 
	 * @param expression
	 *            an expression that hasn't yet been translated
	 * @return the parsed expression
	 */
	public static String translateVariables(String expression,String vardef, String value) {
        while (true) {
            int x = expression.indexOf(DEFAULT_VAR_PREFIX);
            int y = expression.indexOf(DEFAULT_VAR_SUFFIX, x);

            if ((x != -1) && (y != -1)) {
                String var = expression.substring(x + 2, y);
                if (var != null && var.equals(vardef)) {
                    expression = expression.substring(0, x) + value + expression.substring(y + 1);
                } else {
                    // the variable doesn't exist, so don't display anything
                    expression = expression.substring(0, x) + TEMP_VAR_PREFIX+var+TEMP_VAR_SUFFIX+ expression.substring(y + 1);
                }
            } else {
                break;
            }
        }
        expression = replace(expression,TEMP_VAR_PREFIX,DEFAULT_VAR_PREFIX);
        expression = replace(expression,TEMP_VAR_SUFFIX,DEFAULT_VAR_SUFFIX);
        return expression;
    }
	/**
     * 
     * @param expression
     * @param vardef
     * @return
	 */
    public static String transVar(String expression) {
        String var = null;
         int x = expression.indexOf(DEFAULT_VAR_PREFIX);
         int y = expression.indexOf(DEFAULT_VAR_SUFFIX, x);

         if ((x != -1) && (y != -1)) {
               var = expression.substring(x + 2, y);
         } 
        return var;
    }
	/**
	 * replace ${vara} to ${varb}
	 * @param expression
	 *            an expression that hasn't yet been translated
	 * @return the parsed expression
	 */
	public static String replaceVariables(String expression,String oldvar, String newvar) {
        while (true) {
            int x = expression.indexOf(DEFAULT_VAR_PREFIX);
            int y = expression.indexOf(DEFAULT_VAR_SUFFIX, x);

            if ((x != -1) && (y != -1)) {
                String var = expression.substring(x + 2, y);
                if (var != null && var.equals(oldvar)) {
                	expression = expression.substring(0, x) + TEMP_VAR_PREFIX+newvar+TEMP_VAR_SUFFIX+ expression.substring(y + 1);
                } else {
                    // the variable doesn't exist, so don't display anything
                    expression = expression.substring(0, x) + TEMP_VAR_PREFIX +var+TEMP_VAR_SUFFIX+ expression.substring(y + 1);
                }
            } else {
                break;
            }
        }
        expression = replace(expression,TEMP_VAR_PREFIX,DEFAULT_VAR_PREFIX);
        expression = replace(expression,TEMP_VAR_SUFFIX,DEFAULT_VAR_SUFFIX);
        return expression;
    }

	
	/**
     * Return the replaced string 在一个字符串中替换子串。 For example: strnew = replaceEq("hello the
     * world","he","good"); the strnew now is "goodllo tgood world"; Replace the string with
     * another string,these two string's length must equal
     *
     * @param sReplace  你想进行替换操作的字符串。
     * @param sOld      将要被替换掉的字符串。
     * @param sNew      将要替换上去的字符串。
     *
     * @return 返回替换以后的字符串，如果替换不成功将返回原始的字符串。
     */
	public static String replace(String template, String placeholder, String replacement) {
		return replace(template, placeholder, replacement, false);
	}

	public static String replace(String template, String placeholder, String replacement, boolean wholeWords) {
		int loc = template.indexOf(placeholder);
		if (loc<0) {
			return template;
		}
		else {
			final boolean actuallyReplace = !wholeWords ||
				loc + placeholder.length() == template.length() ||
				!Character.isJavaIdentifierPart( template.charAt( loc + placeholder.length() ) );
			String actualReplacement = actuallyReplace ? replacement : placeholder;
			return new StringBuffer( template.substring(0, loc) )
				.append(actualReplacement)
				.append( replace(
					template.substring( loc + placeholder.length() ),
					placeholder,
					replacement,
					wholeWords
				) ).toString();
		}
	}
	/**
     * Return the replaced string 在一个字符串中替换子串。 For example: strnew = replaceEq("hello the
     * world","he","good"); the strnew now is "goodllo tgood world"; Replace the string with
     * another string,these two string's length must equal
     *
     * @param sReplace  你想进行替换操作的字符串。
     * @param sOld      将要被替换掉的字符串。
     * @param sNew      将要替换上去的字符串。
     *
     * @return 返回替换以后的字符串，如果替换不成功将返回原始的字符串。
     */
    public static String replaceString(String sReplace, String sOld, String sNew) {
        if ((sReplace == null) || (sOld == null) || (sNew == null)) {
            return null;
        }
        int index = -1;
        int iLenOldStr = sOld.length();

        if ((sReplace.length() <= 0) || (iLenOldStr <= 0) ||
                (sNew.length() < 0)) {
            return sReplace;
        }

        String sTemp = sReplace;

        while ((index = sTemp.indexOf(sOld)) != -1) {
            sTemp = sTemp.substring(0, index) + sNew +
                    sTemp.substring(index + iLenOldStr);
        }

        return sTemp;
    }
    /**
     * 把字符串按照指定字符串分割成数字
     * @param seperators   定义分割符
     * @param list	       字符串
     * @return			   数组	
     */
	public static String[] split(String seperators, String list) {
		return split(seperators, list, false);
	}
	 
	/**
     * 把字符串按照指定字符串分割成数字
     * @param seperators   定义分割符
     * @param list	       字符串
     * @return			   数组	
     */
	public static String[] split(String seperators, String list, boolean include) {
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String[] result = new String[ tokens.countTokens() ];
		int i=0;
		while ( tokens.hasMoreTokens() ) {
			result[i++] = tokens.nextToken().trim();
		}
		return result;
	}
	/**
	 * 把a=1&b=2的字符串按"&"拆分成键值a=1,b=2的Map
	 * @param sStr
	 * @return Map
	 */
	public static Map splittomap(String sStr){
	 return splittomap(sStr,"&");    
	}
	/**
	 * 把a=1&b=2的字符串拆分成键值a=1,b=2的Map
	 * @param sStr
	 * @param seperators
	 * @return
	 */
	public static Map splittomap(String sStr,String seperators){
	    Map context = new HashMap();
	    StringTokenizer st = new StringTokenizer(sStr,seperators);
	    while (st.hasMoreTokens()) {
	       String exp = st.nextToken();
	      context.put(exp.split("=")[0],exp.split("=")[1]);
	    }
	    return context;
	}
	public static List splitcomma(String sStr){
		 return split2list(sStr,",");    
	}
	
	public static List split2list(String sStr,String seperators){
	    List context = new ArrayList();
	    StringTokenizer st = new StringTokenizer(sStr,seperators);
	    while (st.hasMoreTokens()) {
	       String exp = st.nextToken();
	      context.add(exp);
	    }
	    return context;
	}
	
	public static Object arrayFirst(Object obj){
	    if(obj instanceof String[])
          return ((String[])obj)[0];
	    else
	        return obj;
        
	}
	/**
	 * 根据配置构造要转入的URL
	 * @param gMap 在执行脚本后新构造的参数
	 * @return
	 */
	 public static String getPageUrl(Map gMap){
	     String redirect = (String)gMap.get("redirect");
	     
	    return "/pages/viewpage.action?pageId=" + 10;
	  }	
	  	/**
		 * 根据配置构造要转入的URL
		 * @param gMap 在执行脚本后新构造的参数
		 * @return
		 */
		 public static String getRedirectUrl(Map paramsMap,String autoAction){
		     String redirect = (String)paramsMap.get("redirect");
		     //去掉key我redirect的键
		     //paramsMap.remove("redirect");
		     
		     //拼装参数处理过的要Redirect的URL
		     StringBuffer sb = new StringBuffer(autoAction);
		     
		     int pos = autoAction.indexOf("?");
		     
		     if(pos == -1) sb.append("?");
		        
		      Iterator iter = paramsMap.keySet().iterator();
		      while (iter.hasNext()) {
		        String currentKey = (String)iter.next();
		        String currentValue = (String)paramsMap.get(currentKey);
		        String exp = currentKey + "=" + currentValue + "&";
		        sb.append(exp);
		      }
		    return sb.toString();
		}		 
	 /**
	  * 解析国际化设置,根据正则表达式解析字符串,zh_CN,解析为array=[zh,CN]
	  * @param language zh_CN
	  * @return Locale
	  */
	 public static Locale getLocale(String language){
		 
		 String[] localeArray = language.split("[-|_]");
         String country = "";
         String variant = "";
			for(int i=0; i<localeArray.length; i++)
			{
				if(i == 0)
				{
					language = localeArray[i];
				}
				else if(i == 1)
				{
					country = localeArray[i];
				}
				else if(i == 2)
				{
					variant = localeArray[i];
				}
			}
         // TODO Set the prefered locale to user's persistent storage if not anon user
         return new Locale(language, country, variant);
		 
	 }
	/**
     * 获取当前日期
     * @param formater yyyy-MM-dd  HH:mm:ss或者yyyy-MM-dd
     * @return String 当前日期
	 */
     public static String getCurrDate(String formater){
         SimpleDateFormat df =new SimpleDateFormat(formater);
         return df.format(new Date());
     }
     
     /**
      * 获取当前时间,用于日历控件
      * @return String 当前时间
      */
     public static String getTime(){
         Long reVar = new Long(new Date().getTime());
         
         return reVar.toString();
     }
     
     
    public static DateUtil getDateUtil() {
        return DateUtil.getInstance();
    }

    public static Date today() {
        return new Date();
    }
    
}