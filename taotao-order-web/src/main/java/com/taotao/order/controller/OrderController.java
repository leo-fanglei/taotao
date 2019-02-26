package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common_utils.CookieUtils;
import com.taotao.common_utils.JsonUtils;
import com.taotao.pojo.TbItem;

/**
 * 订单Contrlloer
 * @author 10309
 *
 */
@Controller
public class OrderController {

	@Value("${CART_NAME}")
	private String CART_NAME;

	@RequestMapping("/order/order-cart")
	public String toOrder(HttpServletRequest request) {
		//获取用户信息
		//从cookie中取token，然后根据token查询用户信息。需要调用sso系统的服务。
		//根据用户id查询收货地址列表
		//从cookie中获取其原来的购物车
		List<TbItem> cartList = this.getItemListFromCart(request);
		//传递给页面
		System.out.println(cartList);
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "order-cart";
	}

	/**
	 * 从cookie中获取cartList
	 * @param request
	 * @return
	 */
	private List<TbItem> getItemListFromCart(HttpServletRequest request) {
		String cartjson = CookieUtils.getCookieValue(request, CART_NAME, true);
		if (StringUtils.isBlank(cartjson)) {
			//如果没有则返回空列表
			return new ArrayList<TbItem>();
		}
		List<TbItem> cartList = JsonUtils.jsonToList(cartjson, TbItem.class);
		return cartList;
	}
}
