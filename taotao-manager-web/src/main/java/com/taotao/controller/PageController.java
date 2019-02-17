package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 页面展示Controller
 * @author 10309
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
	
	@RequestMapping("/")
	public String showIndex() {
		
		return "index";
	}
	
	/**
	 * 实现<li data-options="attributes:{'url':'item-add'}">跳转到item-add.jsp
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		
		return page;
	}

}
