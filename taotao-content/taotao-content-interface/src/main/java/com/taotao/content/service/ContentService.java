package com.taotao.content.service;

import java.util.List;

import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	
	TaotaoResult addContent(TbContent tbcontent);
	
	EasyUIDataGridResult listContent(int page, int rows, Long parentId);

	TaotaoResult updateContent(TbContent tbContent);

	TaotaoResult deleteContent(String ids);
	
	//返回TbContent目的在于兼容其他广告位
	List<TbContent> getContentListByCid(Long cid);
	
	
	
}
