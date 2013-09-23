package org.inwiss.platform.util;

import org.springframework.web.context.WebApplicationContext;

public class SpringUtil {
	

	protected static WebApplicationContext applicationContext = null;

	

	public static Object getBean(String beanName){
         return applicationContext.getBean(beanName);
    } 
	
	
	
	/**
	 * @param applicationContext
	 *            the applicationContext to set
	 */
	public static void setApplicationContext(
			WebApplicationContext applicationContext) {
		SpringUtil.applicationContext = applicationContext;
	}
	
	/**
	 * @return the applicationContext
	 */
	public static WebApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
