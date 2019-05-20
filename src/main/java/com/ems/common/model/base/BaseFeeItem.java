package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFeeItem<M extends BaseFeeItem<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer Id) {
		set("Id", Id);
	}
	
	public java.lang.Integer getId() {
		return getInt("Id");
	}

	public void setPid(java.lang.Integer pid) {
		set("pid", pid);
	}
	
	public java.lang.Integer getPid() {
		return getInt("pid");
	}

	public void setStarno(java.lang.Integer starno) {
		set("starno", starno);
	}
	
	public java.lang.Integer getStarno() {
		return getInt("starno");
	}

	public void setEndno(java.lang.Integer endno) {
		set("endno", endno);
	}
	
	public java.lang.Integer getEndno() {
		return getInt("endno");
	}

	public void setPrice(java.math.BigDecimal price) {
		set("price", price);
	}
	
	public java.math.BigDecimal getPrice() {
		return get("price");
	}

	public void setDtype(java.lang.Integer dtype) {
		set("dtype", dtype);
	}
	
	public java.lang.Integer getDtype() {
		return getInt("dtype");
	}

	public void setUserid(java.lang.String userid) {
		set("userid", userid);
	}
	
	public java.lang.String getUserid() {
		return getStr("userid");
	}

}