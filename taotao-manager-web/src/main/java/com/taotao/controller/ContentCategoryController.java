package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common_pojo.EasyUITreeNode;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;

/**
 * 内容分类管理Controller
 * @author 10309
 *
 */
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	/**
	 * 初始化.easyui-tree,显示商品内容目录树
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}
	
	/**
	 * 新增内容分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult addContentCategory(Long parentId,String name) {
		TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}
	
	/**
	 * 更新内容分类的名称
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long parentId,Long id,String name) {
		TaotaoResult result = contentCategoryService.updateContentCategory(parentId,id, name);
		return result;
	}
}
