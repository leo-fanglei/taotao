package com.taotao.content.service;

import java.util.List;

import com.taotao.common_pojo.EasyUITreeNode;
import com.taotao.common_pojo.TaotaoResult;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCategoryList(Long parentId);
	TaotaoResult addContentCategory(Long parentId,String name);
	TaotaoResult updateContentCategory(Long id, String name);
	TaotaoResult deleteContentCategory(Long id);
}
