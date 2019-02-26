package com.taotao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common_pojo.TaotaoResult;
import com.taotao.common_utils.CookieUtils;
import com.taotao.common_utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//从cookie中获取token
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
		//未取到token
		if (StringUtils.isBlank(token)) {
			//拦截前跳转登陆页面
			String url = request.getRequestURL().toString();
			response.sendRedirect(SSO_LOGIN_URL + "/page/login?redirectURL=" + url);
			//拦截
			return false;
		}
		//取到token
		//查redis
		TaotaoResult result = userService.getUserByToken(token);
		//缓存中未查到用户
		if (result.getStatus() != 200) {
			//拦截前跳转登陆页面
			String url = request.getRequestURL().toString();
			response.sendRedirect(SSO_LOGIN_URL + "/page/login?redirectURL=" + url);
			//拦截
			return false;
		}
		//5.如果取到用户信息。放行。
		// 把用户信息放到request中
		TbUser user = (TbUser) result.getData();
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

}
