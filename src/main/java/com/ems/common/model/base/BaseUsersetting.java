package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUsersetting<M extends BaseUsersetting<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setDefaultdataid(java.lang.String defaultdataid) {
		set("defaultdataid", defaultdataid);
	}
	
	public java.lang.String getDefaultdataid() {
		return getStr("defaultdataid");
	}

	public void setUserid(java.lang.String userid) {
		set("userid", userid);
	}
	
	public java.lang.String getUserid() {
		return getStr("userid");
	}

	public void setDatacode(java.lang.String datacode) {
		set("datacode", datacode);
	}
	
	public java.lang.String getDatacode() {
		return getStr("datacode");
	}

}
