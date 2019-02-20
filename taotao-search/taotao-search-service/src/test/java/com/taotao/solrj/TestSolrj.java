package com.taotao.solrj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.mysql.fabric.xmlrpc.base.Array;

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

	@Test
	public void TestQuery() throws Exception {
		//创建一个SolrServer对象.
		//指定url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//创建solrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		//设置条件
		solrQuery.setQuery("手机");
		solrQuery.setStart(0);
		solrQuery.setRows(10);
		solrQuery.set("df","item_keywords");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<div>");
		solrQuery.setHighlightSimplePost("</div>");
		//执行查询
		QueryResponse response = solrServer.query(solrQuery);
		//获取结果
		SolrDocumentList results = response.getResults();
		long numFound = results.getNumFound();
		System.out.println(numFound);
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if(list==null || list.size()==0) {
				//System.out.println(solrDocument.get("item_title"));
			}else {
				String string = list.get(0);
				System.out.println(string);
			}
			//System.out.println(solrDocument.get("item_sell_point"));
			//System.out.println(solrDocument.get("item_price"));
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}

	}
	
	public static void main(String[] args) {
		HashMap hashMap = new HashMap();
		//hashMap.put("11", "v11");
		//System.out.println(hashMap.get("11"));
		//ArrayList arrayList = new ArrayList();
		List list = new LinkedList();
		System.out.println(list.get(0));
	}

}
