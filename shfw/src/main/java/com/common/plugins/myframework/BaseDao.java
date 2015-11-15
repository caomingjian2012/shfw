package com.common.plugins.myframework;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
/**
 * 必须是抽象的，否则clazz强转回出错
 * @author caomj
 *
 * @param <Entity>
 * @param <PK>
 */
@Repository("baseDao")
public abstract class BaseDao<Entity, PK extends Serializable> extends SqlDaoImpl<Entity, PK>  {


}
