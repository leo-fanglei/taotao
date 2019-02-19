package com.taotao.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrj {
	@Test
	public void TestAddDocument() throws SolrServerException, IOException {
		//创建一个SolrServer对象.
		//指定url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//创建文档对象SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.addField("id", "test001");
		document.addField("title", "测试商品001");
		document.addField("price", 1000);
		//把文档写入到索引库中
		solrServer.add(document);
		//提交
		solrServer.commit();

	}

	@Test
	public void TestDelDocumentById() throws SolrServerException, IOException {
		//创建一个SolrServer对象.
		//指定url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//删除
		solrServer.deleteById("test001");
		//提交
		solrServer.commit();
	}

	@Test
	public void TestDelDocumentByQuery() throws SolrServerException, IOException {
		//创建一个SolrServer对象.
		//指定url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//删除
		solrServer.deleteByQuery("id:test001");
		//提交
		solrServer.commit();
	}
	
}
