package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.item.pojo.ItemDetail;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		
		TbItem item = itemService.getItemById(itemId);
		ItemDetail itemDetail = new ItemDetail(item);
		TbItemDesc desc = itemService.getDescById(itemId);
		
		model.addAttribute("item",itemDetail);
		model.addAttribute("itemDesc",desc);
		
		return "item";
		
	}
	
}
