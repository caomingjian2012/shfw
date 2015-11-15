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

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.page.PageQuery;
import cn.org.rapid_framework.util.page.Paginator;









import com.common.plugins.rapidframework.XsqlBuilderFactory;

public  class HqlDaoImpl<Entity, PK extends Serializable> extends EntityDaoImpl<Entity, PK> implements HqlDao<Entity, PK> {
	protected static Logger log = Logger.getLogger(HqlDaoImpl.class);
	@Resource(name = "xsqlBuilderFactory")
	protected XsqlBuilderFactory xsqlBuilderFactory;
	
	
	@Override
	public Number hql4Number(String hql) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		return hql4Number(hql,map);
	}

	@Override
	public Number hql4Number(String hql, String paramName, Object paramValue) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put(paramName, paramValue);
		return hql4Number(hql,map);
	}

	@Override
	public Number hql4Number(final String hql, final Map<String, Object> paramsMap) {
		return  getHibernateTemplate().execute(
				new HibernateCallback<Number>() {
					@SuppressWarnings("unchecked")
					@Override
					public Number doInHibernate(Session session)
							throws HibernateException, SQLException {
						XsqlBuilder build = xsqlBuilderFactory.getXsqlBuilder();
						XsqlFilterResult result = build.generateHql(hql.toString(),paramsMap);						
						Query query = session.createQuery(result.getXsql());
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
	
	
	@Override
	public Number hql4Number(final String hql, final Object queryObj) {
		return  getHibernateTemplate().execute(
				new HibernateCallback<Number>() {
					@Override
					public Number doInHibernate(Session session)
							throws HibernateException, SQLException {
						XsqlBuilder build = xsqlBuilderFactory.getXsqlBuilder();
						XsqlFilterResult result = build.generateHql(hql.toString(),queryObj);						
						Query query = session.createQuery(result.getXsql());
						query = query.setProperties(queryObj);
						
						Object obj =  query.uniqueResult();
						return  obj==null?0:(Number)obj;
					}					
				}
		
		);
	}
	@Override
	public Entity hqlFind(String hql) {
		return hqlFindByPropertys(hql, new HashMap<String,Object>());
	}

	@Override
	public Entity hqlFindByProperty(String hql, String paramName,
			Object paramValue) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put(paramName, paramValue);
		return hqlFindByPropertys(hql,map);
	}

	@Override
	public Entity hqlFindByPropertys(final String hql, final Map<String, Object> paramsMap) {
		return  getHibernateTemplate().execute(
				new HibernateCallback<Entity>() {
					@SuppressWarnings("unchecked")
					@Override
					public Entity doInHibernate(Session session)
							throws HibernateException, SQLException {
						XsqlBuilder build = xsqlBuilderFactory.getXsqlBuilder();
						XsqlFilterResult result = build.generateHql(hql.toString(),paramsMap);						
						Query query = session.createQuery(result.getXsql());
						Map<String, Object> filterMap =result.getAcceptedFilters();
						for (Serializable key : filterMap.keySet()) {
							query.setParameter((String) key, paramsMap.get(key));
						}												
						return  (Entity)query.uniqueResult();
					}					
				}
		
		);
	}

	@Override
	public List<Entity> hqlFindAll(String hql) {
		return hqlFindAllByPropertys(hql, new HashMap<String,Object>());
	}

	@Override
	public List<Entity> hqlFindAllByProperty(String hql, String paramName,
			Object paramValue) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put(paramName, paramValue);
		return hqlFindAllByPropertys(hql,map);
	}

	@Override
	public List<Entity> hqlFindAllByPropertys(final String hql, final Map<String, Object> paramsMap) {
		return  getHibernateTemplate().execute(
				new HibernateCallback<List<Entity>>() {
					@SuppressWarnings("unchecked")
					@Override
					public List<Entity> doInHibernate(Session session)
							throws HibernateException, SQLException {
						XsqlBuilder build = xsqlBuilderFactory.getXsqlBuilder();
						XsqlFilterResult result = build.generateHql(hql.toString(),paramsMap);
						
						Query query = session.createQuery(result.getXsql());
						Map<String, Object> filterMap =result.getAcceptedFilters();
						for (Serializable key : filterMap.keySet()) {
							query.setParameter((String) key, paramsMap.get(key));
						}
						
						return  (List<Entity>)query.list();
					}					
				}
		
		);
	}

	@Override
	public Page<Entity> hqlPageFindAll(String hql, PageQuery pageQuery) {
		String str =removeXsqlBuilderOrders(hql);
		str =removeFetchKeyword(str);
		str = removeSelect(str);
		final String hqlCount = "select count(*) " + str;
		
		return hqlPageFindAll(hql,hqlCount,pageQuery);
	}

	@Override
	public Page<Entity> hqlPageFindAll(String hql, String hqlCount,
			PageQuery pageQuery) {
		int totleCount =hqlFindCount(hqlCount, pageQuery);//查总数
		return hqlPageFindAll(hql,totleCount,pageQuery);
	}

	@Override
	public Page<Entity> hqlPageFindAll(String hql, int totalCount,
			PageQuery pageQuery)  {
		Paginator page = new Paginator(pageQuery.getPage(),pageQuery.getPageSize(),totalCount);
		if(totalCount <= 0) {
			return new Page<Entity>(pageQuery.getPage(), pageQuery.getPageSize(), totalCount, new ArrayList<Entity>(0));
		
			
		}else {
			List<Entity> result =hqlPageFindAll(hql, page.getOffset(), page.getPageSize(),pageQuery);
			return new Page<Entity>(pageQuery.getPage(), pageQuery.getPageSize(), totalCount, result);
		}
		
	}
	
	/**
	 * 查分页列表
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> hqlPageFindAll(final String hql,final int firstResult,
			final int maxSize, final PageQuery pageQuery) {
		return   this.getHibernateTemplate().execute(new HibernateCallback<List<Entity>>() {
			public List<Entity> doInHibernate(Session session)
					throws HibernateException, SQLException {				
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult queryXsqlResult = builder.generateHql(hql,new HashMap<String, Object>(),pageQuery);
				String hql =queryXsqlResult.getXsql();
				Query query = session.createQuery(hql);
				query = query.setProperties(pageQuery);
			
				query.setFirstResult(firstResult);
				query.setMaxResults(maxSize);				
				return (List<Entity>)query.list();
			}
		});
	}
	@Override
	public Integer hqlFindCount(final String hqlcount,final PageQuery pageQuery){
		return  this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {				
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult queryXsqlResult = builder.generateHql(hqlcount,new HashMap<String, Object>(),pageQuery);
				String hql =queryXsqlResult.getXsql();
				Query query = session.createQuery(hql);				
				query = query.setProperties(pageQuery);
				 Object obj =query.uniqueResult();
				 return 	 obj == null?0:((Number)obj).intValue();
				
			}
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> hqlFindAll(final String hql,final PageQuery pageQuery) {
		return   this.getHibernateTemplate().execute(new HibernateCallback<List<Entity>>() {
			
			public List<Entity> doInHibernate(Session session)
					throws HibernateException, SQLException {				
				XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
				XsqlFilterResult queryXsqlResult = builder.generateHql(hql,new HashMap<String, Object>(),pageQuery);
				String hql =queryXsqlResult.getXsql();
				Query query = session.createQuery(hql);
				query = query.setProperties(pageQuery);
			
				return (List<Entity>)query.list();
			}
		});
	}

	
	
}
