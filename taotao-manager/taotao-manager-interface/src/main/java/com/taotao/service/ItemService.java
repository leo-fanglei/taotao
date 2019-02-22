package com.taotao.service;

import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	
	TbItem getItemById(Long itemId);
	
	EasyUIDataGridResult getItemList(int page ,int rows);
	
	TaotaoResult addItem(TbItem item, String desc);
	
	TbItemDesc getDescById(Long itemId);

}
