package org.inwiss.platform.ibatis.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.inwiss.platform.ibatis.entity.BaseEntity;
import org.inwiss.platform.persistence.pagination.Page;
import org.inwiss.platform.persistence.util.PropertyUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


public abstract class BaseIbatis3Dao<E,PK extends Serializable>   extends SqlSessionDaoSupport {
    protected final Log log = LogFactory.getLog(getClass());
    
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
 
	protected void checkDaoConfig() throws IllegalArgumentException {
		Assert.notNull(sqlSessionFactory,"sqlSessionFactory must be not null");
	}
	
	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * @param o
	 */
    protected void prepareObjectForSaveOrUpdate(E o) {
    }

    public String getCountStatementForPaging(String statementName) {
		return statementName +".count";
	}
    
    
    
	protected Page pageQuery(String statementName, BaseEntity pageRequest) {
		return pageQuery(statementName,getCountStatementForPaging(statementName),pageRequest);
	}
	/**
	 * 
	 * @param sqlSessionTemplate
	 * @param statementName
	 * @param countStatementName
	 * @param pageRequest
	 * @return
	 */
	public  Page pageQuery(String statementName,String countStatementName, BaseEntity pageRequest) {
		
		Number totalCount = (Number) getSqlSession().selectOne(countStatementName,pageRequest);
		if(totalCount == null || totalCount.longValue() <= 0) {
			return new Page(pageRequest,0);
		}
		
		Page page = new Page(pageRequest,totalCount.intValue());
		
		//其它分页参数,用于不喜欢或是因为兼容性而不使用方言(Dialect)的分页用户使用. 与getSqlMapClientTemplate().queryForList(statementName, parameterObject)配合使用
		Map filters = new HashMap();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		
		Map parameterObject = PropertyUtils.describe(pageRequest);
		filters.putAll(parameterObject);
		int offset = page.getFirstResult();
		int limit = page.getPageSize();
		
		List list = getSqlSession().selectList(statementName, filters,new RowBounds(offset,limit));
		page.setResult(list);
		return page;
	}
	
	
	
	
	
}
