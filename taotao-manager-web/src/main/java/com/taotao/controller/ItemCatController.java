package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common_pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;

/**
 * 商品分类Controller
 * @author 10309
 *
 */
@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService ItemCatService;

	//加载商品分类
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id",defaultValue="0") Long parentId){
		List<EasyUITreeNode> list = ItemCatService.getItemCatList(parentId);
		return list;
	}
}
