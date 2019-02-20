package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common_pojo.SearchItem;
import com.taotao.common_pojo.SearchResult;

/**
 * 商品搜索Dao
 * @author 10309
 *
 */
@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws Exception{
		
		//查询
		QueryResponse response = solrServer.query(query);
		//取结果   并封装
		SolrDocumentList results = response.getResults();
		long numFound = results.getNumFound();
		SearchResult searchResult = new SearchResult();
		searchResult.setNumFound(numFound);
		ArrayList<SearchItem> arrayList = new ArrayList<SearchItem>();
		for (SolrDocument solrDocument : results) {
			SearchItem item = new SearchItem();
			item.setId((String) solrDocument.get("id"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			//仅取一张图片
			String image = (String)solrDocument.get("item_image");
			if (StringUtils.isNotBlank(image)) {
				String[] split = image.split(",");
				image = split[0];
			}
			item.setImage(image);
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if(list == null || list.size() ==0) {
				item.setTitle((String) solrDocument.get("item_sell_point"));
			}else {
				item.setTitle(list.get(0));
			}
			//添加至list
			arrayList.add(item);
		}
		//返回
		searchResult.setItemList(arrayList);
		return searchResult;
		
	}

}
