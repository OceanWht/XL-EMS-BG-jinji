package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXlZhztUser<M extends BaseXlZhztUser<M>> extends Model<M> implements IBean {

	public void setCustomer(java.lang.String customer) {
		set("customer", customer);
	}
	
	public java.lang.String getCustomer() {
		return getStr("customer");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public void setToken(java.lang.String token) {
		set("token", token);
	}
	
	public java.lang.String getToken() {
		return getStr("token");
	}

	public void setIndex(java.lang.Integer index) {
		set("index", index);
	}
	
	public java.lang.Integer getIndex() {
		return getInt("index");
	}

}
