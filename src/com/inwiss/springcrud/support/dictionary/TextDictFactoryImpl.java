/*
 * Created on 2005-10-13
 */
package com.inwiss.springcrud.support.dictionary;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 负责生成在properties文件中定义的字典的工厂。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class TextDictFactoryImpl implements DictFactory
{
    private static final Log logger = LogFactory
            .getLog(TextDictFactoryImpl.class);

    private String resourceLocationPattern = "dictionary/*.properties";
    
    private Map mapContainer = new HashMap();
    
    public void init()
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader);
        StringBuffer dictNames = new StringBuffer();
        try
        {
            Resource[] resourceArr = resolver.getResources(resourceLocationPattern);
            for ( int i = 0, nSize = resourceArr.length; i < nSize; i++ )
            {
                String dictName = resourceArr[i].getFilename().substring(0,resourceArr[i].getFilename().lastIndexOf('.'));
                if ( i != 0 )
                    dictNames.append(";");
                dictNames.append(dictName);
                
                ResourceBundle bundle = new PropertyResourceBundle(resourceArr[i].getInputStream());
                Enumeration keyEnum = bundle.getKeys();
                Map mapForDict = new TreeMap();
                while ( keyEnum.hasMoreElements() )
                {
                    String key = (String)keyEnum.nextElement();
                    mapForDict.put(key, bundle.getString(key));
                }
                mapContainer.put(dictName, mapForDict);
                mapForDict = null;
            }
        } 
        catch (IOException e)
        {
            throw new DictFactoryInitializationException("初始化文本字典工厂时发生IO异常", e);
        }
        
        if ( logger.isInfoEnabled() )
            logger.info("The following "+ mapContainer.size() + " dictionaries have been loaded :["+dictNames.toString() +"]");
        
    }
    
    
    /**
     * @see com.inwiss.springcrud.support.dictionary.DictFactory#containsDict(java.lang.String)
     */
    public boolean containsDict(String dictName)
    {
        return mapContainer.containsKey(dictName);
    }
    
    public Map getDictionaryByName(String dictName)
    {
        return (Map)mapContainer.get(dictName);
    }
    

    /**
     * @param resourceLocationPattern The resourceLocationPattern to set.
     */
    public void setResourceLocationPattern(String resourceLocationPattern)
    {
        this.resourceLocationPattern = resourceLocationPattern;
    }
}
