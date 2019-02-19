package com.taotao.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.jedis.JedisClient;

public class TestJedis4Spring {
	
	@Test
	public void testJedis() throws Exception{
		ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
		JedisClient jedisClient = classPathXmlApplicationContext.getBean(JedisClient.class);
		//jedisClient.set("key1","spring1");
		String string = jedisClient.hget("INDEX_CONTENT_AD1", "89");
		System.out.println(string);
	}
}
