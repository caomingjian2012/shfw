package com.common.plugins.zk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.common.plugins.tools.IdCreator;

public class ZkIdCreator implements Watcher,IdCreator {
	
	private ZooKeeper zk;
	 private CountDownLatch latch;
	 private  String root;
	 private String host;

	/**
	 * 
	 * @param host eg:192.168.1.1:2181
	 * @param path eg:/locks/ad
	 */
	public ZkIdCreator(String host,String path) {
		this.root =path;
		this.host = host;
		try {
			latch =new CountDownLatch(1); 
			zk = new ZooKeeper(host, 1000, (Watcher) this);
			latch.await();
			
			Stat stat = zk.exists(root, false);
            if(stat == null){
                // 创建根节点
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	


	void reset(){
		try {
			 zk.close();
			 zk = new ZooKeeper(host, 1000, (Watcher) this);
				 
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
	}
	Long getId() throws KeeperException, InterruptedException {
			
			String myZnode = zk.create(root+"/" , new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
			zk.delete(myZnode, -1);
			
			return Long.parseLong(myZnode.replaceAll(root+"/", ""));
		
	}
	
	  long getSeq(){
		
		try{ 
			long seq = getId(); 
			return seq;
		}catch(Exception e){
			reset();
			
		}
		return  (int)(Math.random()*1000000);
	}

	
	
	@Override
	public void process(WatchedEvent event) {
		 if(this.latch != null) {  
	            this.latch.countDown();  
	     }
	}

	
//	public static void main(String[] args) {
//		for(int i=0;i<10;i++){
//			new Thread(){
//
//				@Override
//				public void run() {
//					IdCreator idCreator = new ZkIdCreator("192.168.1.166:2181","/locks/ad");
//					
//					for(;;){
//						
//						try {
//							System.out.println(idCreator.createId());
//							Thread.sleep(100);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					
//				}
//				
//			}.start();
//		}
//		
//		
//	}

	@Override
	public long createId() {
		
		Date now =new Date();
		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		
		long datePre = Long.parseLong(format.format(now));
		long dbtail =getSeq();
		long seq = datePre*1000000+dbtail%1000000;
		return seq;
	}
}
