package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common_pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;
/**
 * 同步所有索引Service
 * @author 10309
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		//判断page,rows
		if(page<1) page=1;
		//创建Solrquery
		SolrQuery solrQuery = new SolrQuery();
		//添加限制条件
		solrQuery.setQuery(queryString);
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		solrQuery.set("df","item_title");
		//设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		//调用dao
		SearchResult result = searchDao.search(solrQuery);
		//计算查询结果的总页数
		long recordCount = result.getNumFound();
		long pages =  recordCount / rows;
		if (recordCount % rows > 0) {
			pages++;
		}
		result.setTotalPages(pages);
		
		return result;
	}

}
