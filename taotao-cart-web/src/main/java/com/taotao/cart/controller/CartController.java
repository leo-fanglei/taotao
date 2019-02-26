package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.TTCCLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common_pojo.TaotaoResult;
import com.taotao.common_utils.CookieUtils;
import com.taotao.common_utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
/**
 * 购物车Controller
 * @author 10309
 *
 */
@Controller
public class CartController {

	@Value("${CART_NAME}")
	private String CART_NAME;
	@Value("${CART_EXPIRE}")
	private Integer CART_EXPIRE;

	@Autowired
	private ItemService itemService;

	/**
	 * 添加商品到购物车
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String add2Cart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response) {

		//从cookie中获取其原来的购物车
		List<TbItem> cartList = this.getItemListFromCart(request);
		/*if (itemId == null) {
			return "error";
		}*/

		boolean flag = false;
		//判断添加的物品是否存在于原来的购物车
		for (TbItem tbItem : cartList) {
			if (itemId == tbItem.getId().longValue()) {
				//如果有,则数量叠加
				tbItem.setNum(num + tbItem.getNum());
				flag = true;
				break;
			}
		}
		if (!flag) {
			//如果没有,则添加新的购物项(注意设置数量)
			TbItem itemById = itemService.getItemById(itemId);
			itemById.setNum(num);
			//取一张图片
			String images = itemById.getImage();
			if (StringUtils.isNotBlank(images)) {
				String image = images.split(",")[0];
				itemById.setImage(image);
			}
			cartList.add(itemById);
		}
		//写入cookie
		CookieUtils.setCookie(request, response, CART_NAME, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
		//返回逻辑视图
		return "cartSuccess";
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
	 * 跳转到购物车页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String goToCart(HttpServletRequest request) {
		//从cookie中获取cart
		List<TbItem> cart = this.getItemListFromCart(request);
		//把购物车传递给jsp
		request.setAttribute("cartList", cart);
		return "cart";
	}

	/**
	 * 商品数量+1-1(改变数量)
	 * @param itemId
	 * @param finalNum
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("cart/update/num/{itemId}/{finalNum}")
	@ResponseBody
	public TaotaoResult updateNum(@PathVariable Long itemId, @PathVariable Integer finalNum, HttpServletRequest request, HttpServletResponse response) {
		//从cookie中获取cart
		List<TbItem> cart = this.getItemListFromCart(request);
		for (TbItem tbItem : cart) {
			if (itemId == tbItem.getId().longValue()) {
				tbItem.setNum(finalNum);
				break;
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, CART_NAME, JsonUtils.objectToJson(cart), CART_EXPIRE, true);
		return TaotaoResult.ok();
	}

	@RequestMapping("/cart/delete/{itemId}")
	public String delItemfromCart(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		//从cookie中获取cart
		List<TbItem> cart = this.getItemListFromCart(request);
		for (TbItem tbItem : cart) {
			if (itemId == tbItem.getId().longValue()) {
				cart.remove(tbItem);
				break;
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, CART_NAME, JsonUtils.objectToJson(cart), CART_EXPIRE, true);
		return "redirect:/cart/cart.html";
	}
}
