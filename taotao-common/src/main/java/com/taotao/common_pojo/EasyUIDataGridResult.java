package com.taotao.common_pojo;

import java.io.Serializable;
import java.util.List;

/**
 * EasyUI中datagrid控件要求的返回数据类型{total,rows:{"id":"1"}{...}}
 * @author 10309
 *
 */
public class EasyUIDataGridResult implements Serializable{
	
	private Long total;
	private List rows;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	

}
