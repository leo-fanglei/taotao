package com.taotao.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.item.pojo.ItemDetail;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemChangeListener implements MessageListener{
	
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Override
	public void onMessage(Message message) {
		 
		
		//取message
		try {
			if (message instanceof TextMessage) {
				String string = ((TextMessage) message).getText();
				//转换格式
				Long itemId = Long.parseLong(string);
				
				//等待事务完成
				Thread.sleep(1000);
			
				//更具id查询信息
				TbItem tbItem = itemService.getItemById(itemId);
				ItemDetail itemDetail = new ItemDetail(tbItem);
				TbItemDesc itemDesc = itemService.getDescById(itemId);
				
				//创建模板
				//创建configure
				Configuration configuration = freeMarkerConfigurer.getConfiguration();
				//加载模板
				Template template = configuration.getTemplate("item.ftl");
				//创建输出对象
				Map data = new HashMap<>();
				data.put("item", itemDetail);
				data.put("itemDesc", itemDesc);
				//创建输出流
				FileWriter fileWriter = new FileWriter(new File(HTML_OUT_PATH+string+".html"));
				//process输出静态页面
				template.process(data, fileWriter);
				//关闭流
				fileWriter.close();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
