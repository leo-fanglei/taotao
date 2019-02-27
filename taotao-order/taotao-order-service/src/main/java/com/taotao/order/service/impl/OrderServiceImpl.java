package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common_pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.sso.jedis.JedisClient;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	
	@Value("${ORDER_ID_KEY}")
	private String ORDER_ID_KEY;
	@Value("${ORDER_ID_VALUE}")
	private String ORDER_ID_VALUE;
	@Value("${ORDER_ITEM_ID_KEY}")
	private String ORDER_ITEM_ID_KEY;
	
	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		//1、接收表单的数据
		//2、生成订单id
		//String order_id_key = jedisClient.get(ORDER_ID_KEY);
		if (!jedisClient.exists(ORDER_ID_KEY)) {
			jedisClient.set(ORDER_ID_KEY, ORDER_ID_VALUE);
		}
		Long orderId = jedisClient.incr(ORDER_ID_KEY);
		//补全属性
		orderInfo.setOrderId(orderId.toString());
		orderInfo.setPostFee("0");
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		//3、向订单表插入数据。
		tbOrderMapper.insert(orderInfo);
		//4、向订单明细表插入数据
		//补全属性
		List<TbOrderItem> items = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : items) {
			Long itemId = jedisClient.incr(ORDER_ITEM_ID_KEY);
			tbOrderItem.setId(itemId.toString());
			tbOrderItem.setOrderId(orderId.toString());
			tbOrderItemMapper.insert(tbOrderItem);
		}
		//5、向订单物流表插入数据。
		//补全属性
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId.toString());
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		tbOrderShippingMapper.insert(orderShipping);
		//6、返回TaotaoResult
		return TaotaoResult.ok(orderId.toString());
	}

}
