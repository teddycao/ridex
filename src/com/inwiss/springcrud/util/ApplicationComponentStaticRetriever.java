/*
 * Created on 2005-10-27
 */
package com.inwiss.springcrud.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 应用程序组件静态获取器。
 * 
 * <p>ApplicationComponentStaticRetriever通过Spring的回调方法获取ApplicationContext，从而将ApplicationContext中
 * 的组件通过静态方法提供给调用者。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class ApplicationComponentStaticRetriever implements ApplicationContextAware
{
    private static final Log logger = LogFactory.getLog(ApplicationComponentStaticRetriever.class);

    private static ApplicationContext appCtx;
    
    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        appCtx = applicationContext;
        if (logger.isInfoEnabled() )
            logger.info("ApplicationContext has been successfully set to ApplicationComponentStaticRetriever, now ready for static call...");
    }
    
    /**
     * 根据组件名称获取相应实例。
     * 
     * @param componentName 在ApplicationContext中配置的组件名称
     * @return 组件对应的对象
     */
    public static Object getComponentByItsName(String componentName)
    {
        if ( appCtx == null )
            throw new IllegalStateException("getComponentByItsName方法必须在ApplicationContext成功初始化以后调用，" 
                    + "可能没有在ApplicationContext中配置ApplicationComponentStaticRetriever组件！");
        
        return appCtx.getBean(componentName);
    }
}
