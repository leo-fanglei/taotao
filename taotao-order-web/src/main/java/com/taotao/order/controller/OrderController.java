package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common_pojo.TaotaoResult;
import com.taotao.common_utils.CookieUtils;
import com.taotao.common_utils.JsonUtils;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;

/**
 * 订单Contrlloer
 * @author 10309
 *
 */
@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@Value("${CART_NAME}")
	private String CART_NAME;

	/**
	 * 跳转到订单页面
	 * @param request
	 * @return
	 */
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
	/**
	 * 生成订单
	 * @param orderInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, Model model) {
		//1、接收表单提交的数据OrderInfo。
		//2、补全用户信息。
		//3、调用Service创建订单。
		TaotaoResult result = orderService.createOrder(orderInfo);
		//4、返回逻辑视图展示成功页面
		model.addAttribute("orderId", result.getData().toString());
		model.addAttribute("payment", orderInfo.getPayment());
		//	返回预计送达时间
		DateTime dateTime = new DateTime();
		DateTime plusDays = dateTime.plusDays(3);
		model.addAttribute("date",dateTime);
		//5.展示成功页面
		return "success";
	}
}
