package com.common.plugins.myframework;

import static com.common.plugins.rapidframework.XSqlRemoveUtils.removeFetchKeyword;
import static com.common.plugins.rapidframework.XSqlRemoveUtils.removeSelect;
import static com.common.plugins.rapidframework.XSqlRemoveUtils.removeXsqlBuilderOrders;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javacommon.xsqlbuilder.XsqlBuilder;
import javacommon.xsqlbuilder.XsqlBuilder.XsqlFilterResult;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.CollectionUtils;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.page.PageQuery;
import cn.org.rapid_framework.util.page.Paginator;

public  class SqlDaoImpl<Entity,PK extends Serializable> extends HqlDaoImpl<Entity, PK> implements SqlDao<Entity,PK>{

	@Override
	public boolean tableIsExist(String tablename) {
		
		return false;
	}

	@Override
	public Integer execute(final String sql) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
        });
	}

	/**
	 * 没有占位符，SQL中需要替换的SQL 依次用 “?”代替值  
	 * 例如：update article set name = ? where id = ?
	 */
	@Override
	public Integer execute(final String sql, final List<Object> params) {
		return  getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			 
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				for (int i = 0; i < params.size(); i++) {
					if(params.get(i) == null)
						continue;
					sqlQuery.setParameter(i, params.get(i));
				}
				return sqlQuery.executeUpdate();
			}
        });
	}

	//----------------------------------------------查单条记录    -----------------------------------------------------//
	@Override
	public <T> T sqlFindOne(final Class<T> clazz, final String sql,
			final Map<String, Object> paramsMap) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			@SuppressWarnings("unchecked")
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, paramsMap);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(paramsMap);					
				sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
				sqlQuery.setFirstResult(0);
				sqlQuery.setMaxResults(1);
				List list = sqlQuery.list();
				
				return (T)(CollectionUtils.isEmpty(list)?null:list.get(0));
				
			}
        });
	}
	
	
	@Override
	public <T> T sqlFindOne(final Class<T> clazz, final String sql,
			final Object query) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			@SuppressWarnings("unchecked")
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, query);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(query);					
				sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
				sqlQuery.setFirstResult(0);
				sqlQuery.setMaxResults(1);
				List list = sqlQuery.list();
				return (T)(CollectionUtils.isEmpty(list)?null:list.get(0));
				
			}
        });
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Map sqlFindOne( final String sql,final Map<String, Object> paramsMap) {
		return getHibernateTemplate().execute(new HibernateCallback<Map>() {
			
			public Map doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, paramsMap);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(paramsMap);					
				sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				sqlQuery.setFirstResult(0);
				sqlQuery.setMaxResults(1);
				List list = sqlQuery.list();
				
				return (Map)(CollectionUtils.isEmpty(list)?null:list.get(0));
				
			}
        });
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Map sqlFindOne( final String sql,final Object query) {
		return getHibernateTemplate().execute(new HibernateCallback<Map>() {
		
			public Map doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, query);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(query);					
				sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				sqlQuery.setFirstResult(0);
				sqlQuery.setMaxResults(1);
				List list = sqlQuery.list();
				return (Map)(CollectionUtils.isEmpty(list)?null:list.get(0));
				
			}
        });
	}
	
	//----------------------------------------------查全部记录    -----------------------------------------------------//
	
	
	
	@Override
	public <T> List<T> sqlFindAll(final Class<T> clazz, final String sql,
			final Map<String, Object> paramsMap) {
		return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, paramsMap);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(paramsMap);					
				sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
				return sqlQuery.list();
			}
        });
	}
	
	
	@Override
	public <T> List<T> sqlFindAll(final Class<T> clazz, final String sql,
			final Object query) {
		return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, query);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(query);					
				sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
				return sqlQuery.list();
			}
        });
	}

	@Override
	public  List<Map> sqlFindAll( final String sql,
			final Object query) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
			@SuppressWarnings("unchecked")
			public List<Map> doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, query);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(query);					
				sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				return sqlQuery.list();
			}
        });
	}
	
	@Override
	public  List<Map> sqlFindAll( final String sql,
			final Map<String, Object> paramsMap) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
			@SuppressWarnings("unchecked")
			public List<Map> doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, paramsMap);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(paramsMap);					
				sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				return sqlQuery.list();
			}
        });
	}
	
	//----------------------------------------------查全部记录    -----------------------------------------------------//
	
	@Override
	public <T> Page<T> sqlPageFindAll(Class<T> clazz, String sql,
			PageQuery pageQuery) {
		final String sqlCount = "select count(*) " + removeSelect(removeFetchKeyword(removeXsqlBuilderOrders(sql)));
		
		return sqlPageFindAll(clazz,sql,sqlCount,pageQuery);
	}
	@Override
	public <T> Page<T> sqlPageFindAll(Class<T> clazz,String sql, String sqlCount,
			PageQuery pageQuery) {
		int totleCount =sqlFindCount(sqlCount, pageQuery);//查总数
		return sqlPageFindAll(clazz,sql,totleCount,pageQuery);
	
	}
	@Override
	public <T> Page<T> sqlPageFindAll(Class<T> clazz,String sql, int totleCount,
			PageQuery pageQuery) {
		Paginator page = new Paginator(pageQuery.getPage(),pageQuery.getPageSize(),totleCount);
		if(totleCount <= 0) {
			return new Page<T>(page.getPage(),page.getPageSize(),0,new ArrayList<T>());
			
		}else {
			List<T> result =sqlPageFindAll(clazz,sql, page.getOffset(), page.getPageSize(),pageQuery);
			return  new Page<T>(page.getPage(),page.getPageSize(),totleCount,result);
		}
	}
	@Override
	public <T> List<T> sqlPageFindAll(final Class<T> clazz,final String sql, final int offset,final int pageSize,
			final PageQuery pageQuery) {
		return   this.getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {				
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult filterResult = builder.generateHql(sql, pageQuery);
				String sqlFinal = filterResult.getXsql();
				SQLQuery sqlQuery = session.createSQLQuery(sqlFinal);
				sqlQuery.setProperties(pageQuery);					
				sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
				sqlQuery.setFirstResult(offset);
				sqlQuery.setMaxResults(pageSize);	
				return sqlQuery.list();
						
				
			}
		});
	}
	@Override
	public Integer sqlFindCount(final String sqlCount,final PageQuery pageQuery) {
		return  this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {				
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult queryXsqlResult = builder.generateHql(sqlCount,pageQuery);
				SQLQuery query = session.createSQLQuery(queryXsqlResult.getXsql());				
				 query.setProperties(pageQuery);
				 Object obj =query.uniqueResult();
				
				return obj == null?0:((Number)obj).intValue();
			}
		});
	}

	@Override
	public Number sql4Number(String sql) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		return sql4Number(sql,map);
	}

	@Override
	public Number sql4Number(String sql, String paramName, Object paramValue) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put(paramName, paramValue);
		return sql4Number(sql,map);
	}

	@Override
	public Number sql4Number(final String sql, final Map<String, Object> paramsMap) {
		return  getHibernateTemplate().execute(
				new HibernateCallback<Number>() {
					@SuppressWarnings("unchecked")
					@Override
					public Number doInHibernate(Session session)
							throws HibernateException, SQLException {
						XsqlBuilder build = xsqlBuilderFactory.getXsqlBuilder();
						XsqlFilterResult result = build.generateHql(sql.toString(),paramsMap);						
						SQLQuery query = session.createSQLQuery(result.getXsql());		
						Map<String, Object> filterMap =result.getAcceptedFilters();
						for (Serializable key : filterMap.keySet()) {
							query.setParameter((String) key, paramsMap.get(key));
						}		
						Object obj =  query.uniqueResult();
						return  obj==null?0:(Number)obj;
					}					
				}
		
		);
	}

	
	

}
