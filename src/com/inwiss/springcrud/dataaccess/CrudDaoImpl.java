/*
 * Created on 2005-8-2
 */
package com.inwiss.springcrud.dataaccess;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * CRUD框架的DAO接口的缺省实现类。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class CrudDaoImpl extends SqlSessionDaoSupport implements CrudDao
{
    private static final Log log = LogFactory.getLog(CrudDaoImpl.class);
    
    /**
     * @see com.inwiss.springcrud.dataaccess.CrudDao#getDataList(java.lang.String)
     */
    public List getDataList(String sql)
    {
        if ( log.isDebugEnabled() )
            log.debug("try to get data list using:["+sql+"]");
       
        
        //return this.getSqlMapTemplate().executeQueryForList("crud.getDataList", sql);
        return this.getSqlSession().selectList("crud.getDataList", sql);
    }
    
    /**
     * @see com.inwiss.springcrud.dataaccess.CrudDao#getDataList(java.lang.String, int, int)
     */
    public List getDataList(String sql, int nOffset, int nSize)
    {
        if ( log.isDebugEnabled() )
            log.debug("try to get data list using:["+sql+"], nOffset:["+nOffset+"], nSize:["+nSize+"]");
        //return this.getSqlMapClientTemplate().queryForList("crud.getDataList", sql, nOffset,nSize);
        return this.getSqlSession().selectList("crud.getDataList", sql, new RowBounds(nOffset,nSize));
    }

    /**
     * @see com.inwiss.springcrud.dataaccess.CrudDao#getDataCount(java.lang.String)
     */
    public int getDataCount(String sql)
    {
        if ( log.isDebugEnabled() )
            log.debug("try to get data count using:["+sql+"]");
        
        return ((Integer)(this.getSqlSession().selectOne("crud.getDataCount", sql))).intValue();
    }

    /**
     * @see com.inwiss.springcrud.dataaccess.CrudDao#doUpdate(java.lang.String)
     */
    public void doUpdate(String sql)
    {   
        if ( log.isDebugEnabled() )
            log.debug("try to update data using:["+sql+"]");
        
        //this.getSqlMapTemplate().executeUpdate("crud.update",sql);
        this.getSqlSession().update("crud.update",sql);
    }

}
