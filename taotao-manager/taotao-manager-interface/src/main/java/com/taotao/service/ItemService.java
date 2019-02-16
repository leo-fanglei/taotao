package com.taotao.service;

import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(long itemId);
	
	EasyUIDataGridResult getItemList(int page ,int rows);
	
	TaotaoResult addItem(TbItem item, String desc);

}
