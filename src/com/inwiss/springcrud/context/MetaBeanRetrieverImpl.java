/*
 * Created on 2005-9-2
 */
package com.inwiss.springcrud.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.inwiss.springcrud.metadata.*;
import com.inwiss.springcrud.support.excelinput.ExcelImportMeta;

/**
 * 该类维持两个静态Map，分别用于存放系统中定义的CrudMeta和ImportMeta组件
 * 
 * @author wuzixiu
 */
public class MetaBeanRetrieverImpl implements MetaBeanRetriever, ApplicationContextAware
{	
	private ApplicationContext applicationContext;
	
	/**存放CrudMeta组件的容器*/
	private Map crudMetaMap;
	
	/**存放ImportMeta组件的容器*/
	private Map importMetaMap;
	
	

	/**存放HandCraftMeta组件的容器*/
	private Map handCraftMetaMap;

	/**
	 * 初始化方法，从ApplicationContext中获取CrudMeta组件，以及ImportMeta组件，并将其置入相应的容器之中。
	 */
	public void init()
	{
	    //处理CrudMeta组件
	    crudMetaMap = applicationContext.getBeansOfType(CrudMeta.class);
	    
	    //处理ImportMeta组件
	    importMetaMap = new HashMap();
	    Map importMetas = applicationContext.getBeansOfType(ImportMeta.class);
		Iterator iterator = importMetas.values().iterator();
		while(iterator.hasNext())
		{
			ImportMeta importMeta = (ImportMeta)iterator.next();
			importMetaMap.put(importMeta.getCrudMeta().getBeanName(),importMeta);
		}
		importMetas = null;
		
	    

		
		
		
		//处理HandCraftMeta组件的容器
		handCraftMetaMap = new HashMap();
		Map handCraftMetas = applicationContext.getBeansOfType(HandCraftMeta.class);
		iterator = handCraftMetas.values().iterator();
		while ( iterator.hasNext() )
		{
		    HandCraftMeta handCraftMeta = (HandCraftMeta)iterator.next();
		    handCraftMetaMap.put(handCraftMeta.getCrudMeta().getBeanName(), handCraftMeta);
		}
		
		handCraftMetas = null;
		iterator = null;
	}
	
	/**
	 *  set the applicationContext and initialize the constant two maps used to hold meta beans.
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException 
	{
		this.applicationContext = applicationContext;
	}
	
    /**
     * 根据名称获取相应的CrudMeta组件。
     * @see com.inwiss.springcrud.context.MetaBeanRetriever#getCrudMetaBean(java.lang.String)
     */
    public CrudMeta getCrudMetaBean(String crudMetaName)
    {
        return (CrudMeta)crudMetaMap.get(crudMetaName);
    }
    
    /**
     * 根据名称获取相应的ImportMeta组件。
     * @see com.inwiss.springcrud.context.MetaBeanRetriever#getImportMetaBean(java.lang.String)
     */
    public ImportMeta getImportMetaBean(String corrCrudMetaName)
    {
        return (ImportMeta)importMetaMap.get(corrCrudMetaName);
    }
    
    
    /**
     * 根据名称获取相应的HandCraftMeta组件。
     * @see com.inwiss.springcrud.context.MetaBeanRetriever#getHandCraftMetaBean(java.lang.String)
     */
    public HandCraftMeta getHandCraftMetaBean(String corrCrudMetaName)
    {
        return (HandCraftMeta)handCraftMetaMap.get(corrCrudMetaName);
    }
    


}
