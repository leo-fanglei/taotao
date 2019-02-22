package com.taotao.search.service;

import com.taotao.common_pojo.TaotaoResult;

public interface SearchItemService {
	
	TaotaoResult importItems2Index();

	TaotaoResult importItem2IndexById(Long itemId);

}
