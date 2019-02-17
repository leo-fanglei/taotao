package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common_pojo.EasyUITreeNode;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理Service
 * @author 10309
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		//根据parentId查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			treeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(treeNode);
		}
		return resultList;
	}

	@Override
	public TaotaoResult addContentCategory(Long parentId, String name) {
		//创建一个pojo
		TbContentCategory tbContentCategory = new TbContentCategory();
		//补全属性
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setStatus(1);//1正常,2删除.
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		//插入到数据库
		tbContentCategoryMapper.insert(tbContentCategory);
		//判断父节点状态
		TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			//父节点改为非叶子节点
			parent.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回对象
		return TaotaoResult.ok(tbContentCategory);
	}
}
