package com.taotao.common_pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{
	private Integer totalPagas;
	private List<SearchItem> itemList;
	private Long numFound;
	public Integer getTotalPagas() {
		return totalPagas;
	}
	public void setTotalPagas(Integer totalPagas) {
		this.totalPagas = totalPagas;
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
