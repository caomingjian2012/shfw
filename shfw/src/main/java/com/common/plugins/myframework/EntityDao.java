package com.common.plugins.myframework;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 实体dao接口
 * 
 * @author caomingjian
 * @createdate Jun 1, 2013
 */
public interface EntityDao<Entity,PK extends Serializable> {
	/**single**/
	public void save(Entity entity) ;
	public void saveOrUpdate(Entity entity);
	public void update(Entity entity);
	public void delete(Entity entity) ;
	public void deleteByPk(PK pk);
	/***batch**/
	public int saveAll(Collection<Entity> entities);
	public int saveOrUpdateAll(Collection<Entity> entities);
	public int updateAll(Collection<Entity> entities);
	public int deleteAll(Collection<Entity> entities);
	

	/********************find**********************/
	public void refresh(Entity entity);
	public Entity getByPk(PK pk);
	public Entity findByProperty(String propertyName, Object propertyValue);
    public Entity findByPropertys(Map<String, Object> paramsMap);
    
    
    /*************************findAll******************/  
    public List<Entity> findAll(String orderbystr);
    public List<Entity> findAllByProperty(String propertyName, Object propertyValue,String orderbystr);
    public List<Entity> findAllByPropertys(Map<String, Object> paramsMap,String orderbystr);

}
