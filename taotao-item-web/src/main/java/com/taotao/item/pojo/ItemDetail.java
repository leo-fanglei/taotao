package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;

public class ItemDetail extends TbItem{
	
	public ItemDetail(TbItem tbItem) {
		//初始化属性
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}
	
	public String[] getImages() {
		String images = this.getImage();
		if (images != null && !"".equals(images)) {
			String[] strings =images.split(",");
			return strings;
		}
		return null;
	}
}
