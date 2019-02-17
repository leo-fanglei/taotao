package com.taotao.content.service;

import java.util.List;

import com.taotao.common_pojo.EasyUITreeNode;
import com.taotao.common_pojo.TaotaoResult;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCategoryList(long parentId);
	TaotaoResult addContentCategory(Long parentId,String name);
}
