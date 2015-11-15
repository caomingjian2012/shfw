package com.common.plugins.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import com.alibaba.druid.util.DaemonThreadFactory;

public  class KafkaMessageConsumer  {
	private ConsumerConfig consumerConfig;
	private String topic;
	
	private ConsumerConnector connector;
	private ExecutorService threadPool = null;
	
	private MessageHandler handler;
	
	
	public void init(){
		System.out.println("~~init~~start threadpool");
		connector = Consumer.createJavaConsumerConnector(consumerConfig);
		
		Map<String,Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams 
					= connector.createMessageStreams(topicCountMap);
		
		threadPool =Executors.newFixedThreadPool(2,new DaemonThreadFactory("kfConsumer"));
		 
		  List<KafkaStream<byte[], byte[]>> streams =   topicMessageStreams.get(topic);
		  for(final KafkaStream<byte[], byte[]> stream :streams ){
			  threadPool.submit(new Runnable() {
					public void run() {
						
	                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
						while (it.hasNext()) {
							try{
								String message = new String(it.next().message());
								handler.handle(message);
							}catch(Throwable t){
								t.printStackTrace();
							}
							
						}
	                }
	            });
		  }
			  
			 
		 


	}
	
	public void destroy()throws Throwable{
		threadPool.shutdownNow();
		connector.shutdown();
		System.out.println("~~destroy~~shutdown threadpool");
	}

	public ConsumerConfig getConsumerConfig() {
		return consumerConfig;
	}

	public void setConsumerConfig(ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public MessageHandler getHandler() {
		return handler;
	}

	public void setHandler(MessageHandler handler) {
		this.handler = handler;
	}

	
	
	
}
