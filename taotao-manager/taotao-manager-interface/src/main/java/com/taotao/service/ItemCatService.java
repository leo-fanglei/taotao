package com.taotao.service;

import java.util.List;

import com.taotao.common_pojo.EasyUITreeNode;

public interface ItemCatService {
	
	List<EasyUITreeNode> getItemCatList(long parentId);

}
