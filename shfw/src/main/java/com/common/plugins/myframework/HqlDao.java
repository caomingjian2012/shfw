package com.common.plugins.myframework;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.page.PageQuery;



public interface HqlDao<Entity,PK extends Serializable> {
	
	public Number hql4Number(String hql);
	public Number hql4Number(String hql,String paramName, Object paramValue);
	public Number hql4Number(String hql, Map<String, Object> paramsMap);
	public Number hql4Number(String hql, Object query);
	/**find**/
	public Entity hqlFind(String hql);
	public Entity hqlFindByProperty(String hql,String paramName, Object paramValue);
	public Entity hqlFindByPropertys(String hql, Map<String, Object> paramsMap);
	

	/**findAll**/
	public List<Entity> hqlFindAll( String hql);
	public List<Entity> hqlFindAll( String hql,PageQuery pageQuery);
	
	public List<Entity> hqlFindAllByProperty( String hql,String paramName, Object paramValue);
	public List<Entity> hqlFindAllByPropertys(String hql, Map<String, Object> paramsMap);
	
	/**pagefindAll**/
	/**
	 * 分页HQL查询
	 * @hql 
	 * @pageRequest 分页请求参数，所有变量都在里面
	 */
	public Page<Entity> hqlPageFindAll(  String hql,PageQuery pageQuery);
	/**
	 * 
	 * @param hql 查记录HQL
	 * @param hqlCount 查总数HQL
	 * @param pageQuery 分页条件
	 * @return
	 */
	public Page<Entity> hqlPageFindAll(  String hql,String hqlCount,PageQuery pageQuery);
	/**
	 * 
	 * @param hql
	 * @param totleCount
	 * @param pageQuery
	 * @return
	 */
	Page<Entity> hqlPageFindAll(String hql, int totleCount, PageQuery pageQuery);
	/**
	 * 查询偏移量firstResult后的maxSize数据
	 * @param hql
	 * @param firstResult 偏移量
	 * @param maxSize 最大数量
	 * @param pageQuery 分页条件
	 * @return
	 */
	List<Entity> hqlPageFindAll(String hql, int firstResult, int maxSize,PageQuery pageQuery);
	/**
	 * 分页数
	 * @param hqlcount
	 * @param pageRequest
	 * @return
	 */
	Integer hqlFindCount(String hqlcount, PageQuery pageQuery);
	

	

}
