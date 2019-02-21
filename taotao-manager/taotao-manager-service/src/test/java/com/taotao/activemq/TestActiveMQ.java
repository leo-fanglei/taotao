package com.taotao.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

public class TestActiveMQ {
	
	@Test
	public void testQueueProducer() throws Exception{
		//建立连接工厂
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.127:61616");
		//创建连接
		Connection connection = factory.createConnection();
		//开启连接
		connection.start();
		//创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建Destination对象
		Queue queue = session.createQueue("test-queue");
		//创建生产者
		MessageProducer producer = session.createProducer(queue);
		//创建消息
		TextMessage textMessage = session.createTextMessage("Hello ActiveMQ!");
		//发布消息
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	@Test
	public void testQueryCustomer() throws Exception{
		//创建连接工厂
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.127:61616");
		//创建连接
		Connection connection = factory.createConnection();
		//开启连接
		connection.start();
		//创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建Destination
		Queue queue = session.createQueue("test-queue");
		//创建消费者
		MessageConsumer consumer = session.createConsumer(queue);
		//接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//取消息,并打印
				if(message instanceof TextMessage) {}
				TextMessage textMessage = (TextMessage) message;
				try {
					String text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
		System.in.read();//等待键盘输入
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicProducer() throws Exception{
		//创建连接
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.127:61616");
		Connection connection = factory.createConnection();
		connection.start();
		//创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建Destination
		Topic topic = session.createTopic("test-topic");
		//创建Producer
		MessageProducer producer = session.createProducer(topic);
		//创建TextMessage
		TextMessage message = session.createTextMessage("topic task");
		//发送消息
		producer.send(message);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	@Test
	public void testTopicCustomer() throws Exception{
		//创建连接工厂
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.127:61616");
		//创建连接
		Connection connection = factory.createConnection();
		//开启连接
		connection.start();
		//创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建Destination
		Topic topic = session.createTopic("test-topic");
		//创建消费者
		MessageConsumer consumer = session.createConsumer(topic);
		//接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//取消息,并打印
				if(message instanceof TextMessage) {}
				TextMessage textMessage = (TextMessage) message;
				try {
					String text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
		System.out.println("topic1");
		System.in.read();//等待键盘输入
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	@Test
	public void testTopicCustomer2() throws Exception{
		//创建连接工厂
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.127:61616");
		//创建连接
		Connection connection = factory.createConnection();
		//开启连接
		connection.start();
		//创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建Destination
		Topic topic = session.createTopic("test-topic");
		//创建消费者
		MessageConsumer consumer = session.createConsumer(topic);
		//接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//取消息,并打印
				if(message instanceof TextMessage) {}
				TextMessage textMessage = (TextMessage) message;
				try {
					String text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
		System.out.println("topic2");
		System.in.read();//等待键盘输入
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
