package com.common.plugins.myframework;

import java.io.Serializable;
/**
 * Dao 基本接口
 * @author caomj
 *
 * @param <Entity>
 * @param <PK>
 */
public interface DaoInterface<Entity, PK extends Serializable> extends EntityDao<Entity, PK> ,HqlDao<Entity, PK>,SqlDao<Entity, PK>{

}
