package com.common.plugins.myframework;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;



public class BaseMongoImpl<T>{

	protected static Log log = LogFactory.getLog(BaseMongoImpl.class);
    @Autowired
	MongoTemplate mongoTemplate;
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	 /**
     * 分页查询   对应mongodb操作中的  db.member.find().skip(10).limit(10);
     *
     *                
     * @param member
     *                     查询的条件
     * @param start    
     *                     用户分页查询的起始值
     * @param size
     *                     查询的数据数目
     * 
     * @return
     *                     返回查询到的数据集合
     */
    public List<T> queryPage(Query query,Class<T> cls, Integer start, Integer size) {
        return getPage(query,(start-1)*size,size,cls);
    }

    /**
     * 查询满足分页的记录总数
     *                
     * @param member
     *                     查询的条件
     * @return
     *                     返回满足条件的记录总数
     */
    public Long queryPageCount(Query query,Class<T> cls){
        return this.getPageCount(query,cls);
    }
  
	
	
	
	
	/**
     * 保存一个对象
     * @param t
     * @return
     */
    public void saveObject(Object obj){
        log.info("[Mongo Dao ]save:" + obj);
        mongoTemplate.insert(obj);
    }
    
    /**
     * 保存一个对象
     * @param t
     * @return
     */
    public void saveBatch(List<T> list){
        log.info("[Mongo Dao ]saveBauch Size :" + list.size());
        mongoTemplate.insertAll(list);
    }
    
  /**
   * 查询 主键_id的最大值
   * @param query
   * @param update
   * @param cls
   * @return
   */
    public Object findAndModify(Query query,Update update,Class<T> cls){
        log.info("[Mongo Dao ]findAndModify");
        return mongoTemplate.findAndModify(query, update, cls);
    }
  
	
  	/**
     * 根据Id从Collection中查询对象
     * @param id
     *                 实体对象的Id,对应Collection中记录的_id字段. 
     *                 <p>
     *                     需要说明的是,Mongdo自身没有主键自增机制.解决方法
     *                     <ol>
     *                         <li>实体入库的时候,程序中为实体赋主键值.
     *                         <li>实体入库的时候,在mongodb中自定义函数实现主键自增机制.定义方法同js代码类似
     *                     </ol>
     *                 </p>
     * @return
     */
    public Object queryById(Class<T> cls,String id) {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id);
        query.addCriteria(criteria);
        log.info("[Mongo Dao ]queryById:" + query);
        return mongoTemplate.findOne(query, cls);
    }
  	
    /**
     * 根据条件查询集合
     * @param query        
     *                     查询条件
     * @return
     *                     满足条件的集合
     */
    public List<T> queryList(Class<T> cls,Query query){
        log.info("[Mongo Dao ]queryList:" + query);
        return mongoTemplate.find(query,cls);
    }
    /**
     * 通过条件查询单个实体
     * @param query
     * @return
     */
    public Object queryOne(Class<T> cls,Query query){
        log.info("[Mongo Dao ]queryOne:" + query);
        return this.mongoTemplate.findOne(query, cls);
    }
	
    /**
     * 通过条件进行分页查询
     *                
     * @param query
     *                     查询条件
     * @param start
     *                     查询起始值 
     *                     <strong> 类似mysql查询中的 limit start, size 中的 start</strong>
     * @param size
     *                     查询大小
     *                     <strong> 类似mysql查询中的 limit start, size 中的 size</strong>
     * @return
     *                     满足条件的集合
     */
    public List<T> getPage(Query query, Integer start, Integer size,Class<T> cls){
    	query.skip(start);
        query.limit(size);
        query.sort().on("_id", Order.DESCENDING);
        log.info("[Mongo Dao ]queryPage:" + query + "(" + start +"," + size +")");
        List<T> lists = mongoTemplate.find(query, cls);
        return lists;
    }
    
    /**
     * 根据条件查询库中符合记录的总数,为分页查询服务
     *
     * @param query
     *                     查询条件
     * @return
     *                     满足条件的记录总数
     */
    public Long getPageCount(Query query,Class<T> cls){
        log.info("[Mongo Dao ]queryPageCount:" + query);
        return this.mongoTemplate.count(query,cls);
    }
    
    /**
	 * 根据id删除
	 */
	public void deleteObject(Class<?> cls,Serializable id) {
		log.info("[Mongo Dao ]deleteById:" + id);
		mongoTemplate
				.remove(new Query(Criteria.where("id").is(id)), cls);
	}
	
    /**
     * 删除对象
     * @param t
     */
    public void deleteObject(Object obj){
        log.info("[Mongo Dao ]delete:" + obj);
        this.mongoTemplate.remove(obj);
    }
    
    /**
     * 更新满足条件的第一个记录
     *
     * @param query
     * @param update
     */
    public void updateFirst(Query query,Update update,Class<T> cls){
        log.info("[Mongo Dao ]updateFirst:query(" + query + "),update(" + update + ")");
        this.mongoTemplate.updateFirst(query, update, cls);
    }
	
    /**
     * 查找更新,如果没有找到符合的记录,则将更新的记录插入库中
     *                
     * @param query
     * @param update
     */
    public void updateInser(Query query, Update update,Class<T> cls){
        log.info("[Mongo Dao ]updateInser:query(" + query + "),update(" + update + ")");
        mongoTemplate.upsert(query, update,cls);
    }
    
    /**
     * 更新满足条件的所有记录
     *
     * @param query
     * @param update
     */
    public void updateMulti(Query query, Update update,Class<T> cls){
        log.info("[Mongo Dao ]updateMulti:query(" + query + "),update(" + update + ")");
        this.mongoTemplate.updateMulti(query, update,cls);
    }
	
	
	/**
	 * 获得所有的对象
	 */
	public List<T> getAllObjects(Class<T> cls) {
		return mongoTemplate.findAll(cls);
	}

	

	/**
	 * 通过id进行查找
	 */
	public Object getObject(Class<?> cls,Serializable id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),cls);
	}
	
	


	/**
	 * 如果collection不存在则建立
	 */
	public void createCollection(Class<?> cls) {
		if (!mongoTemplate.collectionExists(cls)) {
			mongoTemplate.createCollection(cls);
		}
	}

	/**
	 * 如果collection存在则删除之
	 */
	public void dropCollection(Class<?> cls) {
		if (mongoTemplate.collectionExists(cls)) {
			mongoTemplate.dropCollection(cls);
		}
	}

	
	
	
}
