package org.inwiss.platform.common;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

public class PdiServerSystem {

	private static PdiServerSystem ourInstance = null;
	
	protected WebApplicationContext applicationContext = null;

	
	/**
	 * Servlet context of our application
	 */
	protected ServletContext servletContext = null;
	



	/**
	 * Returns instance of PdiServerSystem
	 *
	 * @param request Http request from which to obtain instance
	 * @return Instance of PdiServerSystem
	 */
	public static PdiServerSystem getInstance() {
		if ( ourInstance == null ) {
			ourInstance = new PdiServerSystem();
		}
		return ourInstance;
	}
	
	
	public WebApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
	public void setApplicationContext(WebApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public ServletContext getServletContext() {
		
		if(ourInstance != null){
			return ourInstance.getApplicationContext().getServletContext();
		}
		return servletContext;
	}



}
