package org.inwiss.platform.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class CmsSystemListener implements InitializingBean {
	
	private Log log = LogFactory.getLog(CmsSystemListener.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		//log.info(pdiConfigBean.getRepositoryName());

	}

	

	/**
	 * 
	 * @param ConfigurableWebApplicationContext
	 *            ctx
	 * @return
	 */
	public boolean startup(ConfigurableWebApplicationContext ctx) {
		// Resource slaveServerConfigFile =
		// PdiServerSystem.getInstance().getApplicationContext().getResource("file:WEB-INF/conf/kettle-conf.properties");
		Resource slaveServerConfigFile = ctx
				.getResource("/WEB-INF/conf/slave-server-config.xml");
		log.info(slaveServerConfigFile.exists());

		  try {
		      //environmentInit();
		    } catch (Throwable th) {
		      th.printStackTrace();
		      log.error(CmsSystemListener.class.getName(), th); //$NON-NLS-1$
		    }
		    
		return true;
	}

	
	protected final  void environmentInit() {
		// init kettle without simplejndi
		//KettleEnvironment.init(false);
	}

	public void shutdown() {
		// Nothing required
	}
}
