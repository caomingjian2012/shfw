package com.common.plugins.myframework;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ServiceInterface<Entity,PK extends Serializable>  {
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
	public Entity getByPk(PK pk);    
    /*************************findAll******************/  
    public List<Entity> findAll();
    public List<Entity> findAll(String orderString);
    public List<Entity> findAllByPropertys(Map<String, Object> paramsMap,String orderbystr);
   
}
