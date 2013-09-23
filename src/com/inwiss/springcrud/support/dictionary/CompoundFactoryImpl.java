/*
 * Created on 2005-10-14
 */
package com.inwiss.springcrud.support.dictionary;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;

/**
 * 组合字典工厂，负责整合数据库字段工厂和文本字典工厂，给客户端提供一个统一的视图。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class CompoundFactoryImpl implements DictFactory, InitializingBean
{
    private static final Log logger = LogFactory.getLog(CompoundFactoryImpl.class);

    private DictFactory dbDictFactory;
    
    private DictFactory textDictFactory;
    
    private boolean dbDictFactoryFirst = true;
    
    
    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception
    {
        if ( dbDictFactory == null && textDictFactory == null )
            throw new BeanInitializationException("文本方式的字典工厂、数据库字典工厂不可以全部为空。请检查组件配置文件！");
    }
    
    
    /**
     * @see com.inwiss.springcrud.support.dictionary.DictFactory#containsDict(java.lang.String)
     */
    public boolean containsDict(String dictName)
    {
        if ( dbDictFactory == null )
            return textDictFactory.containsDict(dictName);
        else if ( textDictFactory == null )
            return dbDictFactory.containsDict(dictName);
        else
            return dbDictFactory.containsDict(dictName) || textDictFactory.containsDict(dictName);
    }
    
    /**
     * @see com.inwiss.springcrud.support.dictionary.DictFactory#getDictionaryByName(java.lang.String)
     */
    public Map getDictionaryByName(String dictName)
    {
        if ( !this.containsDict(dictName) )
        {
            logger.error("The dictionary requested:["+dictName+"] dose not exists, please check.");
            return null;
        }
        
        if ( dbDictFactoryFirst )
        {
            if ( dbDictFactory.containsDict(dictName))
                return dbDictFactory.getDictionaryByName(dictName);
            else
                return textDictFactory.getDictionaryByName(dictName);
        }
        else
        {
            if ( textDictFactory.containsDict(dictName) )
                return textDictFactory.getDictionaryByName(dictName);
            else
                return dbDictFactory.getDictionaryByName(dictName);
        }

    }
    
    /**
     * @param dbDictFactory The dbDictFactory to set.
     */
    public void setDbDictFactory(DictFactory dbDictFactory)
    {
        this.dbDictFactory = dbDictFactory;
    }
    /**
     * @param dbDictFactoryFirst The dbDictFactoryFirst to set.
     */
    public void setDbDictFactoryFirst(boolean dbDictFactoryFirst)
    {
        this.dbDictFactoryFirst = dbDictFactoryFirst;
    }
    /**
     * @param textDictFactory The textDictFactory to set.
     */
    public void setTextDictFactory(DictFactory textDictFactory)
    {
        this.textDictFactory = textDictFactory;
    }
}
