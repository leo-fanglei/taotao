package com.taotao.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class PageHelperTest {
	
	@Test
	public void testPageHelper() {
		//配置插件
		//设置分页条件
		PageHelper.startPage(1, 10);
		//执行查询
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example );
		//取结果
		PageInfo<TbItem> info = new PageInfo<TbItem>(list);
		long total = info.getTotal();
		System.out.println("总记录数"+total);
		System.out.println("总记页数"+info.getPages());
		
	}

}
