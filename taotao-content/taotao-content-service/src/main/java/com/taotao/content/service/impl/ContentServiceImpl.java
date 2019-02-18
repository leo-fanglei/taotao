package com.taotao.content.service.impl;

import java.util.ArrayList;
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

	@Override
	public TaotaoResult updateContent(TbContent tbContent) {
		//补全日期属性
		TbContent tbContent2 = tbContentMapper.selectByPrimaryKey(tbContent.getId());
		tbContent.setUpdated(new Date());
		tbContent.setCreated(tbContent2.getCreated());
		//更新
		tbContentMapper.updateByPrimaryKey(tbContent);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(String ids) {
		//切分ids
		String[] strings = ids.split(",");
		for (int i = 0; i < strings.length; i++) {
			
		}
		//转换成long[]
		/*Long[] num = new Long[strings.length];
		for (int i = 0; i < strings.length; i++) {
			num[i] = Long.parseLong(strings[i]);
		}*/
		//转换成ArrayList<Long>
		ArrayList<Long> longList = new ArrayList<Long>();
		for (String id : strings) {
			longList.add(Long.parseLong(id));
		}
		/*for (String id : strings) {
			tbContentMapper.deleteByPrimaryKey(Long.parseLong(id));
		}*/
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(longList);
		tbContentMapper.deleteByExample(example);
		return TaotaoResult.ok();
	}

}
