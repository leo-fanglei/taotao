package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common_utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AD1Node;

/**
 * 网站门户展示Contorller
 * @author 10309
 *
 */
@Controller
public class IndexContorller {
	
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTHB}")
	private Integer AD1_WIDTHB;
	@Value("${AD1_Height}")
	private Integer AD1_Height;
	@Value("${AD1_HeightB}")
	private Integer AD1_HeightB;
	
	@Autowired
	private ContentService contentService;
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		//根据cid查询内容列表
		List<TbContent> contentList = contentService.getContentListByCid(AD1_CATEGORY_ID);
		//把tbContent列表转换为ADNode列表
		List<AD1Node> ad1NodeList = new ArrayList<>();
		for (TbContent tbContent : contentList) {
			AD1Node ad1Node = new AD1Node();
			ad1Node.setHref(tbContent.getUrl());
			ad1Node.setSrc(tbContent.getPic());
			ad1Node.setSrcB(tbContent.getPic());
			
			ad1Node.setAlt(tbContent.getTitle());
			
			ad1Node.setWidh(AD1_WIDTH);
			ad1Node.setWidtB(AD1_WIDTHB);
			ad1Node.setHeiht(AD1_Height);
			ad1Node.setHeightB(AD1_HeightB);
			
			ad1NodeList.add(ad1Node);
		}
		//继续转换成json数据
		String json = JsonUtils.objectToJson(ad1NodeList);
		//把json数据传递至页面
		model.addAttribute("ad1", json);
		return "index";
	}
}
