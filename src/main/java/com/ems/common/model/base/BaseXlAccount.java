package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXlAccount<M extends BaseXlAccount<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setPid(java.lang.String pid) {
		set("pid", pid);
	}
	
	public java.lang.String getPid() {
		return getStr("pid");
	}

	public void setUid(java.lang.Integer uid) {
		set("uid", uid);
	}
	
	public java.lang.Integer getUid() {
		return getInt("uid");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public void setPwd(java.lang.String pwd) {
		set("pwd", pwd);
	}
	
	public java.lang.String getPwd() {
		return getStr("pwd");
	}

}
