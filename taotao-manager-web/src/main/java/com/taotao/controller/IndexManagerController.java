package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common_pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;

/**
 * 索引库维护Controller
 * @author 10309
 *
 */
@Controller
public class IndexManagerController {
	
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/import")
	@ResponseBody
	public TaotaoResult indexImport() {
		TaotaoResult result = searchItemService.importItems2Index();
		return result;
		
	}

}
