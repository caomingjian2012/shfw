package com.common.plugins.myframework;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

public  class EntityDaoImpl<Entity, Pk extends Serializable> implements EntityDao<Entity, Pk >{
	protected static Logger log = Logger.getLogger(EntityDaoImpl.class);
	@Resource(name = "hibernateTemplate")
	protected HibernateTemplate hibernateTemplate;
	protected Class<Entity> clazz;
	
	@SuppressWarnings("unchecked")
	public EntityDaoImpl() {
		clazz = (Class<Entity>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	@Override
	public void save(Entity entity) {
		
		hibernateTemplate.save(entity);
		
	}
	@Override
	public void saveOrUpdate(Entity entity) {
		hibernateTemplate.saveOrUpdate(entity);
		
	}
	
	@Override
	public void update(Entity entity) {
		hibernateTemplate.update(entity);
		
	}
	
	@Override
	public void delete(Entity entity) {
		hibernateTemplate.delete(entity);
		
	}

	@Override
	public void deleteByPk(Pk pk) {
		Entity entity = getByPk(pk);
		delete(entity);
		
	}
	@Override
	public int saveAll(final Collection<Entity> entities) {
		 Integer result = hibernateTemplate.execute(new HibernateCallback<Integer>(){   
			    public Integer doInHibernate(Session session){   
				    int rows=0;
				    int count = 0;
				    Transaction transaction = null;
				    try{
				    	transaction = session.beginTransaction();
				    	transaction.begin();
		                for(Object t: entities){   
		                  session.save(t);   
		                  rows++;   
		                  count ++;
		                  if(rows%50 == 0){
		                     session.flush();   
		                     session.clear();   
		                     count = 0;
		                  }     
		                }		                
		                if(count > 0){
		                     session.flush();   
		                     session.clear();   
		                }		                
		                transaction.commit();
				    }catch(Exception e){
				    	return new Integer(0);
				    }

	              return new Integer(rows);   
			   }   
			 });
		return null==result?0:result;
		
	}



	@Override
	public int saveOrUpdateAll(final Collection<Entity> entities) {
		Integer result = hibernateTemplate.execute(new HibernateCallback<Integer>(){   
		    public Integer doInHibernate(Session session){   
			    int rows=0;
			    int count = 0;
			    Transaction transaction = null;
			    try{
			    	transaction = session.beginTransaction();
			    	transaction.begin();
	                for(Object t: entities){   
	                  session.saveOrUpdate(t);   
	                  rows++;   
	                  count ++;
	                  if(rows%50 == 0){
	                     session.flush();   
	                     session.clear();   
	                     count = 0;
	                  }     
	                }
	                
	                if(count > 0){
	                     session.flush();   
	                     session.clear();   
	                }
	                
	                transaction.commit();
			    }catch(Exception e){
			    	return new Integer(0);
			    }

              return new Integer(rows);   
		   }   
		 });
	return null==result?0:result;	
	}
	

	@Override
	public int updateAll(final Collection<Entity> entities) {
		Integer result = hibernateTemplate.execute(new HibernateCallback<Integer>(){   
		    public Integer doInHibernate(Session session){   
			    int rows=0;
			    int count = 0;
			    Transaction transaction = null;
			    try{
			    	transaction = session.beginTransaction();
			    	transaction.begin();
	                for(Object t: entities){   
	                  session.update(t);   
	                  rows++;   
	                  count ++;
	                  if(rows%50 == 0){
	                     session.flush();   
	                     session.clear();   
	                     count = 0;
	                  }     
	                }
	                
	                if(count > 0){
	                     session.flush();   
	                     session.clear();   
	                }
	                
	                transaction.commit();
			    }catch(Exception e){
			    	return new Integer(0);
			    }

              return new Integer(rows);   
		   }   
		 });
	return null==result?0:result;
		
	}

	

	

	@Override
	public int deleteAll(final Collection<Entity> entities) {
		Integer result = hibernateTemplate.execute(new HibernateCallback<Integer>(){   
		    public Integer doInHibernate(Session session){   
			    int rows=0;
			    int count = 0;
			    Transaction transaction = null;
			    try{
			    	transaction = session.beginTransaction();
			    	transaction.begin();
	                for(Object t: entities){   
	                  session.delete(t);   
	                  rows++;   
	                  count ++;
	                  if(rows%50 == 0){
	                     session.flush();   
	                     session.clear();   
	                     count = 0;
	                  }     
	                }
	                
	                if(count > 0){
	                     session.flush();   
	                     session.clear();   
	                }
	                
	                transaction.commit();
			    }catch(Exception e){
			    	return new Integer(0);
			    }

              return new Integer(rows);   
		   }   
		 });
	return null==result?0:result;
		
	}

	@Override
	public Entity getByPk(Pk pk) {
		
		 return hibernateTemplate.get(clazz, pk);
	}

	@Override
	public void refresh(Entity entity) {
		hibernateTemplate.refresh(entity);
		
	}
	@Override
	public Entity findByProperty(final String propertyName, final Object propertyValue) {
		  return hibernateTemplate.execute(new HibernateCallback<Entity>() {
				@SuppressWarnings("unchecked")
				public Entity doInHibernate(Session session) throws HibernateException, SQLException {
					return (Entity)session.createCriteria(clazz)
						.add(Restrictions.eq(propertyName, propertyValue))
						.uniqueResult();
				}
	        });
	}

	@Override
	public Entity findByPropertys(final Map<String, Object> paramsMap) {
		return hibernateTemplate.execute(new HibernateCallback<Entity>() {
			
			 @SuppressWarnings({ "unchecked", "rawtypes" })
			public Entity doInHibernate(Session session) throws HibernateException, SQLException {
					 Criteria ceriteria =session.createCriteria(clazz);
					 Iterator iter = paramsMap.entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next();
							String propName = entry.getKey().toString();
							Object value = entry.getValue();
							if(null != value)
								ceriteria.add(Restrictions.eq(propName,value));
						}
					return (Entity)ceriteria.uniqueResult();
				}
	        });
	}

	@Override
	public List<Entity> findAll(final String orderbystr) {
		 return hibernateTemplate.execute(new HibernateCallback<List<Entity>>() {
				@SuppressWarnings("unchecked")
				public List<Entity> doInHibernate(Session session) throws HibernateException, SQLException {
					List<Order> orders =Utils.parseOrders(orderbystr);//TODO 排序
					Criteria criteria =session.createCriteria(clazz);
					for(Order order:orders){
						criteria.addOrder(order);
					}
					return  criteria.list();
				}
	        });
	}

	@Override
	public List<Entity> findAllByProperty(final String propertyName,
			final Object propertyValue,final String orderbystr) {
		
		 return hibernateTemplate.execute(new HibernateCallback<List<Entity>>() {
				@SuppressWarnings("unchecked")
				public List<Entity> doInHibernate(Session session) throws HibernateException, SQLException {
					List<Order> orders =Utils.parseOrders(orderbystr);//TODO 排序
					Criteria criteria =session.createCriteria(clazz)
							.add(Restrictions.eq(propertyName, propertyValue));
					for(Order order:orders){
						criteria.addOrder(order);
					}
					return  criteria.list();
				}
	        });
	}

	@Override
	public List<Entity> findAllByPropertys(final Map<String, Object> paramsMap,final String orderbystr) {
		return hibernateTemplate.execute(new HibernateCallback<List<Entity>>() {
			
			 @SuppressWarnings({ "unchecked", "rawtypes" })
			public List<Entity> doInHibernate(Session session) throws HibernateException, SQLException {
				  	List<Order> orders =Utils.parseOrders(orderbystr);//TODO 排序
				     Criteria criteria =session.createCriteria(clazz);
					 Iterator iter = paramsMap.entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next();
							String propName = entry.getKey().toString();
							Object value = entry.getValue();
							if(null != value)
								criteria.add(Restrictions.eq(propName,value));
						}
						for(Order order:orders){
							criteria.addOrder(order);
						}
					return criteria.list();
				}
	        });
	}

	
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public Class<Entity> getClazz() {
		return clazz;
	}
	public void setClazz(Class<Entity> clazz) {
		this.clazz = clazz;
	}

	
}

class Utils{
	static List<Order> parseOrders(String orderbyStr){
		List<Order> list =new ArrayList<Order>();
		if(StringUtils.isBlank(orderbyStr)){
			return list;
		}
		String[] orderItems =orderbyStr.trim().split(",");
		for(String item:orderItems){
			String[] pair =item.trim().split("\\s+");
			
			Assert.isTrue(pair.length==1 || pair.length == 2,"array length must be 1 or 2. the pair is "+pair);
			//TRIM
			Order order = null;
			if(pair.length==2){
				if(pair[1].equalsIgnoreCase("ASC")){
					order =Order.asc(pair[0]);
				}else if(pair[1].equalsIgnoreCase("DESC")){
					order =Order.desc(pair[0]);
				}else{
					Assert.isTrue(false,"order string err:"+item);
				}
			}
			if(pair.length==1){
				order =Order.asc(pair[0]);
			}
			
			list.add(order);
		}
		
		return list;
	}
}
