package com.taotao.common_pojo;

import java.io.Serializable;

/**
 * EasyUI中easy-tree(异步树)控件要求的返回数据类型[{"id":1,"text":"","state":"closed","children":[{"id":1,"text":""},{}]},{...}]
 * @author 10309
 *
 */
public class EasyUITreeNode implements Serializable{
	
	private long id;
	private String text;
	private String state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
