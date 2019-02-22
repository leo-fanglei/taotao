package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common_pojo.SearchResult;
import com.taotao.search.service.SearchService;
/**
 * 商品收缩Controlller
 * @author 10309
 *
 */
@Controller
public class SearchController {

	@Value("${SEARCH_RESULT_ROWS}")
	private int SEARCH_RESULT_ROWS;

	@Autowired
	private SearchService searchService;

	@RequestMapping("/search")
	public String search(@RequestParam(value = "q", defaultValue = "") String queryString, @RequestParam(value = "page", defaultValue = "1") Integer page, Model model) throws Exception {
		//int a = 1 / 0;
		//解决get乱码
		queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
		//调用service
		SearchResult result = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
		//封装Model
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("page", page);

		//返回逻辑视图
		return "search";
	}
}
