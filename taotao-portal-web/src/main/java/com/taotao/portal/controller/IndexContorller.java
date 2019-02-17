package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 网站门户展示Contorller
 * @author 10309
 *
 */
@Controller
public class IndexContorller {
	
	@RequestMapping("/index")
	public String showIndex() {
		return "index";
	}

}
