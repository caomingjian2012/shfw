package com.common.plugins.rapidframework;

import static com.common.plugins.rapidframework.XSqlRemoveUtils.removeFetchKeyword;
import static com.common.plugins.rapidframework.XSqlRemoveUtils.removeSelect;
import static com.common.plugins.rapidframework.XSqlRemoveUtils.removeXsqlBuilderOrders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javacommon.xsqlbuilder.XsqlBuilder;
import javacommon.xsqlbuilder.XsqlBuilder.XsqlFilterResult;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.page.PageQuery;
import cn.org.rapid_framework.util.page.Paginator;

public class PageQueryUtils<Entity> {
	/**
	 * 分页查询，查询超过总数时，返回最后一页
	 * @param hql
	 * @param pageQuery
	 * @param xsqlBuilderFactory
	 * @param sessionFactory
	 * @return
	 */
	public Page<Entity> findPage(String hql,PageQuery pageQuery,
			XsqlBuilderFactory xsqlBuilderFactory, SessionFactory sessionFactory) {
		
		XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
		XsqlFilterResult queryXsqlResult = builder.generateHql(hql,
				new HashMap<String, Object>(), pageQuery);
		hql = queryXsqlResult.getXsql();
		String hqlCount = "select count(*) "
				+ removeSelect(removeFetchKeyword(removeXsqlBuilderOrders(hql)));
		Query query = sessionFactory.getCurrentSession().createQuery(hqlCount);
		query = query.setProperties(pageQuery);
		 Object obj =query.uniqueResult();
			Integer totalCount = obj == null?0:((Number)obj).intValue();

		Paginator page = new Paginator(pageQuery.getPage(),
				pageQuery.getPageSize(), totalCount);
		if (totalCount <= 0) {
			return new Page<Entity>(pageQuery.getPage(),
					pageQuery.getPageSize(), totalCount, new ArrayList<Entity>(
							0));

		}  else {
			Query listquery = sessionFactory.getCurrentSession().createQuery(hql);
			listquery = listquery.setProperties(pageQuery);

			listquery.setFirstResult(page.getOffset());
			listquery.setMaxResults(page.getPageSize());
			List<Entity> result = (List<Entity>) listquery.list();

			return new Page<Entity>(pageQuery.getPage(),pageQuery.getPageSize(), totalCount, result);
		}

	}
	/**
	 * 分页查询，查询超过总数时，返回空列表
	 * @param hql
	 * @param pageQuery
	 * @param xsqlBuilderFactory
	 * @param sessionFactory
	 * @return
	 */
	public Page<Entity> findPageWithEmpty(String hql,PageQuery pageQuery,
			XsqlBuilderFactory xsqlBuilderFactory, SessionFactory sessionFactory) {
		
		
		XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
		XsqlFilterResult queryXsqlResult = builder.generateHql(hql,
				new HashMap<String, Object>(), pageQuery);
		hql = queryXsqlResult.getXsql();
		String hqlCount = "select count(*) "
				+ removeSelect(removeFetchKeyword(removeXsqlBuilderOrders(hql)));
		Query query = sessionFactory.getCurrentSession().createQuery(hqlCount);
		query = query.setProperties(pageQuery);
		 Object obj =query.uniqueResult();
			Integer totalCount = obj == null?0:((Number)obj).intValue();

		Paginator page = new Paginator(pageQuery.getPage(),
				pageQuery.getPageSize(), totalCount);
		if (totalCount <= 0) {
			return new Page<Entity>(pageQuery.getPage(),
					pageQuery.getPageSize(), totalCount, new ArrayList<Entity>(
							0));

		} else if (totalCount < (pageQuery.getPage() - 1)
				* pageQuery.getPageSize()) {
			return new Page<Entity>(pageQuery.getPage(),
					pageQuery.getPageSize(), totalCount, new ArrayList<Entity>(
							0));
		} else {
			Query listquery = sessionFactory.getCurrentSession().createQuery(hql);
			listquery = listquery.setProperties(pageQuery);

			listquery.setFirstResult(page.getOffset());
			listquery.setMaxResults(page.getPageSize());
			List<Entity> result = (List<Entity>) listquery.list();

			return new Page<Entity>(pageQuery.getPage(),pageQuery.getPageSize(), totalCount, result);
		}

	}
	
	/**
	 * 分页查询，查询超过总数时，返回空列表
	 * @param sql
	 * @param pageQuery
	 * @param xsqlBuilderFactory
	 * @param sessionFactory
	 * @return
	 */
	public Page<Entity> findSQLPageWithEmpty(Class clazz,String sql,PageQuery pageQuery,
			XsqlBuilderFactory xsqlBuilderFactory, SessionFactory sessionFactory) {
		
		
		XsqlBuilder builder = xsqlBuilderFactory.getXsqlBuilder();
		XsqlFilterResult queryXsqlResult = builder.generateHql(sql,
				new HashMap<String, Object>(), pageQuery);
		sql = queryXsqlResult.getXsql();
		String sqlCount = "select count(*) "
				+ removeSelect(removeFetchKeyword(removeXsqlBuilderOrders(sql)));
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlCount);
		query = query.setProperties(pageQuery);
		 Object obj =query.uniqueResult();
		Integer totalCount = obj == null?0:((Number)obj).intValue();

		Paginator page = new Paginator(pageQuery.getPage(),
				pageQuery.getPageSize(), totalCount);
		if (totalCount <= 0) {
			return new Page<Entity>(pageQuery.getPage(),
					pageQuery.getPageSize(), totalCount, new ArrayList<Entity>(
							0));

		} else if (totalCount < (pageQuery.getPage() - 1)
				* pageQuery.getPageSize()) {
			return new Page<Entity>(pageQuery.getPage(),
					pageQuery.getPageSize(), totalCount, new ArrayList<Entity>(
							0));
		} else {
			Query listquery = sessionFactory.getCurrentSession().createSQLQuery(sql);
			listquery.setResultTransformer(Transformers.aliasToBean(clazz));
			listquery = listquery.setProperties(pageQuery);

			listquery.setFirstResult(page.getOffset());
			listquery.setMaxResults(page.getPageSize());
			List<Entity> result = (List<Entity>) listquery.list();

			return new Page<Entity>(pageQuery.getPage(),pageQuery.getPageSize(), totalCount, result);
		}

	}
}
