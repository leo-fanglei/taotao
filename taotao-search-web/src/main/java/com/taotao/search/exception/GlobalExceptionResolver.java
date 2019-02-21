package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		// TODO Auto-generated method stub
		//控制台输出异常
		e.printStackTrace();
		//写入文件
		logger.info("全局异常处理器:info系统发生异常", e);
		logger.debug("全局异常处理器:debug+"+handler, e);
		logger.error("全局异常处理器:error系统发生异常", e);
		//发邮件
		//发短信
		//展示错误页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("系统繁忙,请稍后再试");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
