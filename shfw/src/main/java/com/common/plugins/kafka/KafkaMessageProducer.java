package com.common.plugins.kafka;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import org.springframework.jms.JmsException;

import com.alibaba.druid.util.DaemonThreadFactory;
import com.alibaba.fastjson.JSON;
import com.common.plugins.jms.MessageProducer;

public class KafkaMessageProducer implements MessageProducer {
	
	private Producer<String, String> producer;
	private ThreadPoolExecutor threadPool = null;

	

	@Override
	public void sendObject(String topic ,Object message) throws JmsException {
		sendText(topic,JSON.toJSONString(message));
		
	}

	@Override
	public void sendText(final String topic,final String message) throws JmsException {
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				KeyedMessage<String, String> data =new KeyedMessage<String,String>(topic, message);
				producer.send(data);
				
			}
		});
		
		
	}

	public Producer<String, String> getProducer() {
		return producer;
	}

	public void setProducer(Producer<String, String> producer) {
		this.producer = producer;
	}

	
	
	
	public void init()throws Throwable{
		threadPool = new  ThreadPoolExecutor(10, 50, 30, TimeUnit.SECONDS,  new LinkedBlockingDeque<Runnable>(),new DaemonThreadFactory("kfProducer"));
		System.out.println("~~init ~~init threadpool");
	}
	
	public void destroy()throws Throwable{
		threadPool.shutdownNow();
		
		System.out.println("~~destroy~~shutdown threadpool");
	}

}
