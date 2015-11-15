package com.common.plugins.myframework;

import cn.org.rapid_framework.util.page.PageQuery;
/**
 * request basequery.that wrapper request conditions
 * eg:sortColumns = id asc,time desc .the query will be order by id asc,time desc 
 * @author caomj
 *
 */
public class BaseQuery extends PageQuery{

	private static final long serialVersionUID = 6788680144803264886L;
	/**
	 * order by regular. 
	 */
	protected String sortColumns = null;
	public String getSortColumns() {
		return sortColumns;
	}
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
