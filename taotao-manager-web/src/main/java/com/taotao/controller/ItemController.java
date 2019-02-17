package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 商品管理Controller
 * @author 10309
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	/**
	 * 查询商品功能
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	/**
	 * 查询商品(列表)功能
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	/**
	 * 增加商品
	 * @param item
	 * @param desc
	 * @return taotaoResult
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem item,String desc) {
		TaotaoResult taotaoResult = itemService.addItem(item, desc);
		return taotaoResult;
	}
}
