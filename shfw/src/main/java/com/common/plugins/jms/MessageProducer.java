package com.common.plugins.jms;

import org.springframework.jms.JmsException;

public interface MessageProducer {
	
	
	void sendObject(String topic ,Object message) throws JmsException;
	void sendText(String topic,String message) throws JmsException;

}
