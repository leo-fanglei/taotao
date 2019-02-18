package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

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
		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult listContent(int page, int rows, Long categoryId) {
//		设置分页条件
		PageHelper.startPage(page, rows);
		//		执行查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
//		取查询结果
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
//		返回结果
		return result;
	}

}
