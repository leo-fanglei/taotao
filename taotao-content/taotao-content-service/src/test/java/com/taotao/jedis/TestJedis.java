package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	
	@Test
	public void testJedis() throws Exception{
		//创建jedis对象,需要定制端口号和ip
		Jedis jedis = new Jedis("192.168.25.128",6379);
		//直接操作数据库
		jedis.set("jedis-key", "1234");
		String string = jedis.get("jedis-key");
		System.out.println(string);
		//关闭jedis
		jedis.close();
	}
	@Test
	public void testJedisPool() throws Exception{
		//创建连接池对象,需要定制端口号
		JedisPool jedisPool = new JedisPool("192.168.25.128",6379);
		//从连接池获取链接
		Jedis jedis = jedisPool.getResource();	
		//操作数据库(方法级别使用)
		String string = jedis.get("jedis-key");
		System.out.println(string);
		//关闭连接
		jedis.close();
		//系统关闭前关闭连接池
		jedisPool.close();
	}
	@Test
	public void TestJedisCluster() throws Exception{
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes );
		jedisCluster.set("keyClu2", "5678");
		String string = jedisCluster.get("keyClu2");
		System.out.println(string);
		jedisCluster.close();
	}

}
