package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TetsFreemarker {
	
	@Test
	public void testFreemarker() throws IOException, TemplateException {
		//创建模板文件
		//创建Configuration文件
		Configuration configuration = new Configuration(Configuration.getVersion());
		//设置Configuration
		configuration.setDefaultEncoding("utf-8");
		configuration.setDirectoryForTemplateLoading(new File("J:\\Eclipse\\WorkSpace\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		//Configuration加载模板
		Template template = configuration.getTemplate("hello.ftl");
		//创建输出对象
		Map data = new HashMap<>();
		data.put("hello", "HELLO FREEMAKER");
		//创建输出流
		FileWriter out = new FileWriter(new File("J:\\MyTemp\\hello.txt"));
		//使用模板对象process
		template.process(data, out);
		//关闭资源
		out.close();
	}
	/*@Test
	public void testSpringFreemarker() throws Exception {
		
	}*/
}
