package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseEmsUser<M extends BaseEmsUser<M>> extends Model<M> implements IBean {

	public void setID(java.lang.Integer ID) {
		set("ID", ID);
	}
	
	public java.lang.Integer getID() {
		return getInt("ID");
	}

	public void setUserName(java.lang.String userName) {
		set("USER_NAME", userName);
	}
	
	public java.lang.String getUserName() {
		return getStr("USER_NAME");
	}

	public void setUserPass(java.lang.String userPass) {
		set("USER_PASS", userPass);
	}
	
	public java.lang.String getUserPass() {
		return getStr("USER_PASS");
	}

	public void setREMARK(java.lang.String REMARK) {
		set("REMARK", REMARK);
	}
	
	public java.lang.String getREMARK() {
		return getStr("REMARK");
	}

}
