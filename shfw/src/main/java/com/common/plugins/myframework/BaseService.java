package com.common.plugins.myframework;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseService<Entity, PK extends Serializable> implements ServiceInterface<Entity, PK>{
	protected abstract DaoInterface<Entity,PK> getDAO();
	@Override
	public void save(Entity entity) {
		getDAO().save(entity);
		
	}

	@Override
	public void saveOrUpdate(Entity entity) {
		getDAO().saveOrUpdate(entity);
		
	}

	@Override
	public void update(Entity entity) {
		getDAO().update(entity);
		
	}

	@Override
	public void delete(Entity entity) {
		getDAO().delete(entity);
		
	}

	@Override
	public void deleteByPk(PK pk) {
		getDAO().deleteByPk(pk);
		
	}

	@Override
	public int saveAll(Collection<Entity> entities) {
		return getDAO().saveAll(entities);
		
	}

	@Override
	public int saveOrUpdateAll(Collection<Entity> entities) {
		return getDAO().saveOrUpdateAll(entities);
	}

	@Override
	public int updateAll(Collection<Entity> entities) {
		
		return getDAO().updateAll(entities);
	}

	@Override
	public int deleteAll(Collection<Entity> entities) {
		return getDAO().deleteAll(entities);
	}

	

	@Override
	public Entity getByPk(PK pk) {
		
		return getDAO().getByPk(pk);
		
	}

	@Override
	public List<Entity> findAll() {
		
		return  getDAO().findAll("");
	}
	@Override
	public List<Entity> findAll(String orderString) {		
		return  getDAO().findAll(orderString);
	}
	@Override
	public List<Entity> findAllByPropertys(Map<String, Object> paramsMap,
			String orderbystr) {
		
		 return  getDAO().findAllByPropertys(paramsMap,orderbystr);
	}

	
	
}
