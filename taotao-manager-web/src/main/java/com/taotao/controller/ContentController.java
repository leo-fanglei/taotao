package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

/**
 * 内容管理Controller
 * @author 10309
 *
 */
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent tbContent) {
		TaotaoResult result = contentService.addContent(tbContent);
		return result;
	}
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult listContent(int page, int rows, Long categoryId) {
		EasyUIDataGridResult result = contentService.listContent(page,rows,categoryId);
		return result;
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent tbContent) {
		TaotaoResult result = contentService.updateContent(tbContent);
		return result;
	}
	
	/**
	 * 注意:此处为真实删除,仅测试数据库用
	 * @param ids
	 * @return
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids) {
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
	}
}
