package com.common.plugins.rapidframework;

import javacommon.xsqlbuilder.SafeSqlProcesser;
import javacommon.xsqlbuilder.SafeSqlProcesserFactory;
import javacommon.xsqlbuilder.XsqlBuilder;
import javacommon.xsqlbuilder.safesql.DirectReturnSafeSqlProcesser;

import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class XsqlBuilderFactory extends HibernateDaoSupport{
	private static XsqlBuilder instance ;
	public  XsqlBuilder  getXsqlBuilder(){
		if(null!=instance)
			return instance;
		return createbuild(); 
	}
	
	private  XsqlBuilder createbuild(){
		SessionFactoryImpl sf = (SessionFactoryImpl)(this.getSession().getSessionFactory());
		Dialect dialect = sf.getDialect();
		
		//or SafeSqlProcesserFactory.getMysql();
		SafeSqlProcesser safeSqlProcesser = SafeSqlProcesserFactory.getFromCacheByHibernateDialect(dialect); 
		XsqlBuilder builder = new XsqlBuilder(safeSqlProcesser);
		
		if(builder.getSafeSqlProcesser().getClass() == DirectReturnSafeSqlProcesser.class) {
			System.err.println("XsqlBuilder: 故意报错,你未开启Sql安全过滤,单引号等转义字符在拼接sql时需要转义,不然会导致Sql注入攻击的安全问题，请修改源码使用new XsqlBuilder(SafeSqlProcesserFactory.getDataBaseName())开启安全过滤");
		}
		return builder;
	}

}
