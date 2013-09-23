/*
 * Created on 2005-11-30
 */
package com.inwiss.springcrud.dataaccess;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

//import org.springframework.orm.ibatis.support.SqlMapDaoSupport;

/**
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class GeneralSqlDaoImpl extends SqlSessionDaoSupport implements GeneralSqlDao
{
    private static final Log log = LogFactory.getLog(GeneralSqlDaoImpl.class);

    /**
     * @see com.inwiss.springcrud.dataaccess.GeneralSqlDao#getDataList(java.lang.String)
     */
    public List getDataList(String sql)
    {
        if ( log.isDebugEnabled() )
            log.debug("try to get data list using:["+sql+"]");
        
        return this.getSqlSession().selectList("generalsql.getDataList", sql);
    }

    /**
     * @see com.inwiss.springcrud.dataaccess.GeneralSqlDao#getDataList(java.lang.String, int, int)
     */
    public List getDataList(String sql, int nOffset, int nSize)
    {
        if ( log.isDebugEnabled() )
            log.debug("try to get data list using:["+sql+"], nOffset:["+nOffset+"], nSize:["+nSize+"]");
        
        return this.getSqlSession().selectList("generalsql.getDataList", sql, new RowBounds(nOffset,nSize));
    }

    /**
     * @see com.inwiss.springcrud.dataaccess.GeneralSqlDao#getDataCount(java.lang.String)
     */
    public int getDataCount(String sql)
    {
        if ( log.isDebugEnabled() )
            log.debug("try to get data count using:["+sql+"]");
        
        return ((Integer)(this.getSqlSession().selectOne("generalsql.getDataCount", sql))).intValue();
    }

    /**
     * @see com.inwiss.springcrud.dataaccess.GeneralSqlDao#doUpdate(java.lang.String)
     */
    public int doUpdate(String sql)
    {
        if ( log.isDebugEnabled() )
            log.debug("try to update data using:["+sql+"]");
        
        return this.getSqlSession().update("generalsql.update",sql);
    }

}
