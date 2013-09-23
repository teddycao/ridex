package org.inwiss.platform.report.param;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.inwiss.platform.cache.CacheWrapper;
import org.inwiss.platform.service.exception.ServiceException;
import org.inwiss.platform.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service("paramBuildFactory")  
public class ParamBuildFactory implements InitializingBean,ApplicationContextAware{
	private Logger logger = LoggerFactory.getLogger(ParamBuildFactory.class);
	
	protected Collection<ParamDictBean>  rptParamsMap = new LinkedHashSet<ParamDictBean>();
	
	
	@Autowired
	protected CacheWrapper<String, ParamDictBean> paramCache;

	@Autowired
	protected XmlParamDictFactory paramDistFactory = null;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate = null;
	
	protected ApplicationContext appContext = null;

	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		initDone();
	}
	/**
	 * 
	 * @throws ServiceException
	 */
	public void initDone() throws ServiceException{
		Collection<ParamDictBean> paramsList = paramDistFactory.getParamsContent();
		if(paramsList == null || paramsList.size() <= 0) {
			logger.error("paramDictBean is null.");
			throw new ServiceException("paramDictBean is null.");
		}
		logger.info(paramsList.toString());
		for (ParamDictBean param : paramsList) {
			if(param.getType().equals(Param.ENUM)){
				paramCache.put(param.getCode(), param);
			}else if(param.getType().equals(Param.SQL)){
				this.buildSqlTypeParam(param);
			}else if(param.getType().equals(Param.XSQL)){
				this.buildXsqlTypeParam(param);
			}
		}
		
		
	}
	
	public void buildSqlTypeParam(ParamDictBean pdb){
		try {
			DataSource  ds = (DataSource)SpringUtil.getBean(pdb.getDatasource());
			//List<Map<String, Object>> parsms = jdbcTemplate.queryForList(pdb.getParamSql());
			
			final String sqlStr = pdb.getParamSql();
			jdbcTemplate.setDataSource(ds);
			Collection<EntryHashMap> prarms = jdbcTemplate.query(sqlStr, new ParamResultSetExtractor());
			pdb.setEntryList(prarms);
			paramCache.put(pdb.getCode(), pdb);
		} catch (BeansException ex) {
			ex.printStackTrace();
		}
	
		
	}
	
	/**
	 * 
	 * @param pdb
	 */
	public void buildXsqlTypeParam(ParamDictBean pdb){
		
		
	}
	/**
	 * 
	 * @author Administrator
	 *
	 */
	class ParamResultSetExtractor implements ResultSetExtractor{
		public Object extractData(final ResultSet rs) throws SQLException, DataAccessException {
			Collection<EntryHashMap> entrys = new LinkedHashSet<EntryHashMap>();
		      while (rs.next()) {
		    	  entrys.add(new EntryHashMap(rs.getString(1),rs.getString(2)));
		      }
		      rs.close();
		      return entrys;
	      }
	}
	

	//http://www.java2s.com/Code/Java/Spring/ImplementsPreparedStatementCreator.htm
	class MyPreparedStatementCallback implements PreparedStatementCallback {
		  public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
		      ResultSet rs = preparedStatement.executeQuery();
		      List<Long> ids = new LinkedList<Long>();
		      while (rs.next()) {
		          ids.add(rs.getLong(1));
		      }
		      rs.close();
		      return ids;
		  }
	}
	

	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public ParamDictBean getParamByCache(String code){
		return paramCache.get(code);
	}
	
	/**
	 * @return the paramCache
	 */
	public CacheWrapper<String, ParamDictBean> getParamCache() {
		return paramCache;
	}
	/**
	 * @param paramCache the paramCache to set
	 */
	public void setParamCache(CacheWrapper<String, ParamDictBean> paramCache) {
		this.paramCache = paramCache;
	}
	
	/**
	 * @return the rptParamsMap
	 */
	public Collection<ParamDictBean>  getRptParamsMap() {
		return rptParamsMap;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appContext = applicationContext;
		
	}
	
}
