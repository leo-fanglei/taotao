package com.taotao.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {
	
	@Test
	public void testSolrCloud() throws SolrServerException, IOException {
		//创建服务
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.127:2181,192.168.25.127:2182,192.168.25.127:2183");
		//选择索引集
		cloudSolrServer.setDefaultCollection("collection2");
		//创建文档
		SolrInputDocument document = new SolrInputDocument();
		//添加域
		document.addField("id","测试001");
		document.addField("item_title","测试商品");
		//写入索引库
		cloudSolrServer.add(document);
		//提交
		cloudSolrServer.commit();
		
	}

}
