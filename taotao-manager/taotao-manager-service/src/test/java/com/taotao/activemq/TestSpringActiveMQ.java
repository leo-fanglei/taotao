package com.taotao.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestSpringActiveMQ {
	
	@Test
	public void testTemplate() throws Exception{
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//获得jmstemplate
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		//获得Destination
		Destination destination = (Destination) applicationContext.getBean("queueDestination");
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage("spring active send queue message");
				return message;
			}
		});
	}

}
