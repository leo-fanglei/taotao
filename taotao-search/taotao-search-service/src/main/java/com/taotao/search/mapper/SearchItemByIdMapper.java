package com.taotao.search.mapper;

import com.taotao.common_pojo.SearchItem;

public interface SearchItemByIdMapper {
	
	SearchItem getItemById(Long itemId);

}
