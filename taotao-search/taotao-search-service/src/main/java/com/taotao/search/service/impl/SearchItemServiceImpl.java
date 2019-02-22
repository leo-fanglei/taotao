package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common_pojo.SearchItem;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemByIdMapper;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;

@Service
public class SearchItemServiceImpl implements SearchItemService{
	
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SearchItemByIdMapper searchItemByIdMapper;

	/**
	 * 商品数据导入索引库
	 */
	@Override
	public TaotaoResult importItems2Index() {
		try {
			//查询所有商品数据
			List<SearchItem> itemList = searchItemMapper.getItemList();
			//遍历后添加至索引库
			for (SearchItem searchItem : itemList) {
				//创建文档
				SolrInputDocument document = new SolrInputDocument();
				//添加域
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image",  searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_desc", searchItem.getItem_desc());
				//写入索引库
				solrServer.add(document);
			}
			//提交
			solrServer.commit();
		}catch (Exception e) {
			e.printStackTrace();
			//返回失败
			return TaotaoResult.build(500, "数据导入失败");
		}
		//返回成功
		return TaotaoResult.ok();
	}
	
	/**
	 * 商品数据导入索引库
	 */
	@Override
	public TaotaoResult importItem2IndexById(Long itemId) {
		try {
			//查询所有商品数据
			SearchItem searchItem = searchItemByIdMapper.getItemById(itemId);
			//创建文档
			SolrInputDocument document = new SolrInputDocument();
			//添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image",  searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());
			//写入索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		}catch (Exception e) {
			e.printStackTrace();
			//返回失败
			return TaotaoResult.build(500, "数据导入失败");
		}
		//返回成功
		return TaotaoResult.ok();
	}

}
