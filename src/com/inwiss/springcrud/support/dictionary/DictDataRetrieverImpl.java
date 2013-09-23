/*
 * Created on 2005-11-30
 */
package com.inwiss.springcrud.support.dictionary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * DictDataRetriever的缺省实现。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class DictDataRetrieverImpl implements DictDataRetriever
{
    private static final Log logger = LogFactory.getLog(DictDataRetrieverImpl.class);

    private DictDao dictDao;
    
    /**
     * @see com.inwiss.springcrud.support.dictionary.DictDataRetriever#getDictDataWithSql(java.lang.String)
     */
    public Map getDictDataWithSql(String sql)
    {
        try
        {
            if ( logger.isDebugEnabled() )
                logger.debug("try to retrieve dict data using sql:["+sql+"]");
            return dictDao.getAllDictItemsWithSql(sql);
        }
        catch (RuntimeException e)
        {
            throw new ProbableBadSqlForDictException("根据sql语句:["+sql+"]获取字典值出错，请检查相应的sql语句或者sql语句生成逻辑", e);
        }
    }
    

    /**
     * @param dictDao The dictDao to set.
     */
    public void setDictDao(DictDao dictDao)
    {
        this.dictDao = dictDao;
    }
}
