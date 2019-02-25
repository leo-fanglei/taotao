package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 页面跳转Controller
 * @author 10309
 *
 */
@Controller
public class PageController {
	
	/**
	 * 跳转至注册页面
	 * @return
	 */
	@RequestMapping("/page/register")
	public String regist() {
		return "register";
	}
	
	/**
	 * 跳转至登录页面
	 */
	@RequestMapping("/page/login")
	public String login() {
		return "login";
	}

}
