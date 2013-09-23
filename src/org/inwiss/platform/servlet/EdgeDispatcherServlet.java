package org.inwiss.platform.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inwiss.platform.common.PdiServerSystem;
import org.inwiss.platform.util.SpringUtil;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

public class EdgeDispatcherServlet extends DispatcherServlet  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4007384561821138339L;

	protected transient Log log = LogFactory.getLog(EdgeDispatcherServlet.class);

	/**
	 * Struts Menu repository
	 
	protected MenuRepository menuRepository = null;
	*/
	/**
	 * Servlet context of our application
	 */
	protected ServletContext servletContext = null;

	/**
	 * Spring Application context
	 */
	protected ConfigurableWebApplicationContext ctx = null;



	public ConfigurableWebApplicationContext getApplicationContext() {
		return ctx;
	}
	/**
	 * Context path of our application
	 */
	protected String contextPath = null;
	
	/**
	 * Post-process the given WebApplicationContext before it is refreshed
	 * and activated as context for this servlet.
	 * <p>The default implementation is empty. <code>refresh()</code> will
	 * be called automatically after this method returns.
	 * @param wac the configured WebApplicationContext (not refreshed yet)
	 * @see #createWebApplicationContext
	 * @see ConfigurableWebApplicationContext#refresh()
	 */
	@Override
	protected void postProcessWebApplicationContext(ConfigurableWebApplicationContext wac) {
		super.postProcessWebApplicationContext(wac);
		log.info("first execute postProcessWebApplicationContext");
		ctx = wac;
		PdiServerSystem.getInstance().setApplicationContext(wac);
		SpringUtil.setApplicationContext(wac);
		//System.out.println("------------"+ wac.getServletContext());
		
	}
	
	
	/**
	 * This method will be invoked after any bean properties have been set and
	 * the WebApplicationContext has been loaded. The default implementation is empty;
	 * subclasses may override this method to perform any initialization they require.
	 * @throws ServletException in case of an initialization exception
	 */
	@Override
	protected void initFrameworkServlet() throws ServletException {
		super.initFrameworkServlet();
		
		//CmsSystemListener psl = (CmsSystemListener)ctx.getBean("pdiInitListener");
		//psl.startup(ctx);
	}
	
	
	
}
