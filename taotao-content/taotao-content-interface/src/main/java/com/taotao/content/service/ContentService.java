package com.taotao.content.service;

import com.taotao.common_pojo.EasyUIDataGridResult;
import com.taotao.common_pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	
	TaotaoResult addContent(TbContent tbcontent);
	
	EasyUIDataGridResult listContent(int page, int rows, Long parentId);

	TaotaoResult updateContent(TbContent tbContent);

	TaotaoResult deleteContent(String ids);
	
}
