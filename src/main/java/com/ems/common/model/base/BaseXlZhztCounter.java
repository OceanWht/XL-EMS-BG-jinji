package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXlZhztCounter<M extends BaseXlZhztCounter<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}
	
	public java.lang.String getId() {
		return getStr("id");
	}

	public void setAccountName(java.lang.String accountName) {
		set("account_name", accountName);
	}
	
	public java.lang.String getAccountName() {
		return getStr("account_name");
	}

	public void setAcountNo(java.lang.String acountNo) {
		set("acount_no", acountNo);
	}
	
	public java.lang.String getAcountNo() {
		return getStr("acount_no");
	}

}