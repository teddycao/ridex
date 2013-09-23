package com.inwiss.springcrud.beanloader;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
 *junit测试类 
 */  

public class DynamicBeanReaderTests {  
    
    
    @Test  
    public void testLoadBean(){  
    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:data-access-config.xml");     
        DynamicLoadBean dynamicBeanLoad =(DynamicLoadBean)ctx.getBean("dynamicLoadBean");  
  
        dynamicBeanLoad.loadBean("data-access-dynamicConfig.xml");  
        //MagazineService magezineService = (MagazineService)ctx.getBean("magazineService");
    }  
}  