package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.common_utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

/**
 * 商品管理Service
 * @author 10309
 *
 */
@Service
/* @Transactional */
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired	
	private TbItemDescMapper tbItemDescMapper;
	@Resource(name="jmsTemplate")
	private JmsTemplate jmsTemplate;
	@Resource(name="itemAddTopic")
	private Destination itemAddTopic;

	@Override
	public TbItem getItemById(long itemId) {
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页条件
		PageHelper.startPage(page, rows);
		//执行查询(查所有)
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取查询结果
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		//生成商品id
		long itemId = IDUtils.genItemId();
		//补全属性
		item.setId(itemId);
		item.setStatus((byte) 1);
		item.setUpdated(new Date());
		item.setCreated(new Date());
		//向商品表插入数据
		itemMapper.insert(item);
		//创建商品描述pojo
		TbItemDesc itemDesc = new TbItemDesc();
		//补全商品描述pojo
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDesc.setCreated(new Date());
		//向商品描述表插入数据
		tbItemDescMapper.insert(itemDesc);
		
		//调用消息队列发送信息
		jmsTemplate.send(itemAddTopic, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(itemId+"");
				return message;
			}
		});
		
		//返回TaotaoResult
		return TaotaoResult.ok();
	}

}
