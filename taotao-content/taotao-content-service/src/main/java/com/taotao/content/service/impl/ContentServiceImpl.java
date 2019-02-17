package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.EasyUITreeNode;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbContentExample.Criteria;

/**
 * 内容管理Service
 * 
 * @author 10309
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public TaotaoResult addContent(TbContent tbcontent) {
		// 补全pojo的属性
		tbcontent.setCreated(new Date());
		tbcontent.setUpdated(new Date());
		tbContentMapper.insert(tbcontent);
		return null;
	}

	@Override
	public EasyUIDataGridResult listContent(long parentId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(parentId);
		List<TbContent> list = tbContentMapper.selectByExample(example );
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		for (TbContent tbContent : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContent.getId());
			//node.setState(tbContent.getId());
			node.setText(tbContent.getTitle());
			result.setRows(list);
		}
		//设置分页条件
		//PageHelper.startPage(page, rows);
		//执行查询
		//TbItemExample example = new TbItemExample();
		//List<TbItem> list = itemMapper.selectByExample(example);
		//取查询结果
//		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
//		EasyUIDataGridResult result = new EasyUIDataGridResult();
//		result.setRows(list);
//		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
	}

}
