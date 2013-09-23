/*
 * Created on 2005-11-30
 */
package com.inwiss.springcrud.support.dictionary;

import java.util.*;

/**
 * 字段数据获取器。
 * 
 * <p>该接口是对字典工厂（DictFactory）接口的一个有益补充，它可以实现根据SQL直接获取字典数据。
 * 这里的字典数据，和字典工厂中所指的完全相同，即一个Map结构，使用其中的对偶来描述一个字典项。
 * 
 * <p>建议使用该接口的客户（Client）将相应的Sql外部化，以获取更好的可配置性。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 * @see com.inwiss.springcrud.support.dictionary.DictFactory
 */
public interface DictDataRetriever
{
    /**
     * 根据传入Sql语句获取相应的字典Map。
     * @param sql 获取字典数据的sql语句
     * @return 字典对应的Map
     * @throws ProbableBadSqlForDictException 当从数据库中获取字典数据发生异常时抛出该异常
     */
    public Map getDictDataWithSql(String sql);
}
