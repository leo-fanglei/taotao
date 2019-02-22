package com.taotao.common_pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索页面返回结果pojo
 * @author 10309
 *
 */
public class SearchResult implements Serializable{
	private Long totalPages;
	private List<SearchItem> itemList;
	private Long numFound;
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public Long getNumFound() {
		return numFound;
	}
	public void setNumFound(Long numFound) {
		this.numFound = numFound;
	}

	

}
