package com.common.plugins.myframework;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.page.PageQuery;

/**
 * SQL的DAO
 * 
 * @author caomj
 * 
 */
public interface SqlDao<Entity, PK extends Serializable> {
	
	
	
	
	public Number sql4Number(String sql);
	public Number sql4Number(String sql,String paramName, Object paramValue);
	public Number sql4Number(String sql, Map<String, Object> paramsMap);
	
	/**
	 * 判断分表是否存在
	 * 
	 * @param numberid
	 * @return true表示tablename表存在
	 */
	public boolean tableIsExist(String tablename);

	public Integer execute(String sql);

	public Integer execute(String sql, List<Object> params);
	
	/**
	 * SQL查询一个数据
	 * @param clazz 返回值列表类型
	 * @param sql SQL语句
	 * @param paramsMap sql语句的参数
	 * @return
	 */
	<T> T sqlFindOne(Class<T> clazz, String sql, Map<String, Object> paramsMap);
	<T> T sqlFindOne(Class<T> clazz, String sql, Object query);
	
	Map sqlFindOne(String sql, Map<String, Object> paramsMap);
	Map sqlFindOne(String sql, Object query);
	
	/**
	 * SQL查询列表
	 * @param clazz 返回值列表类型
	 * @param sql SQL语句
	 * @param paramsMap sql语句的参数
	 * @return
	 */
	 <T> List<T> sqlFindAll(Class<T> clazz, String sql,Map<String, Object> paramsMap);
	 <T> List<T> sqlFindAll(Class<T> clazz, String sql, Object query);
	  List<Map> sqlFindAll(   String sql,Map<String, Object> paramsMap);
	  List<Map> sqlFindAll(   String sql, Object query);

	/**
	 * SQL分页查询
	 * @param clazz 返回值列表类型
	 * @param sql SQL语句 
	 * @param pageQuery sql语句的参数对象
	 * @return
	 */
	public <T> Page<T> sqlPageFindAll(Class<T> clazz, String sql,PageQuery pageQuery);
	/**
	 * SQL分页查总数
	 * @param sqlCount 查总数SQL
	 * @param pageQuery sql语句的参数对象
	 * @return
	 */
	Integer sqlFindCount(String sqlCount, PageQuery pageQuery);
	/**
	 * SQL分页查结果集
	 * @param clazz 返回的结果集封装对象
	 * @param sql 查询SQL
	 * @param offset 分页偏移量
	 * @param pageSize 分页最大数
	 * @param pageQuery 参数对象
	 * @return
	 */
	<T> List<T> sqlPageFindAll(Class<T> clazz, String sql, int offset,
			int pageSize, PageQuery pageQuery);
/**
 * SQL分页查询
 * @param clazz 返回的结果集封装对象
 * @param sql 查询SQL 
 * @param totleCount 总数
 * @param pageQuery 参数对象
 * @return
 */
	<T> Page<T> sqlPageFindAll(Class<T> clazz, String sql, int totleCount,
			PageQuery pageQuery);
/**
 * SQL分页查询
 * @param clazz  返回的结果集封装对象
 * @param sql 查询SQL 
 * @param sqlCount 查总数SQL
 * @param pageQuery 参数对象
 * @return
 */
	<T> Page<T> sqlPageFindAll(Class<T> clazz, String sql, String sqlCount,
			PageQuery pageQuery);




	
	
	
	

}
