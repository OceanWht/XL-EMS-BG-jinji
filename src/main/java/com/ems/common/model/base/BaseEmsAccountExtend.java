package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseEmsAccountExtend<M extends BaseEmsAccountExtend<M>> extends Model<M> implements IBean {

	public void setAccountCode(java.lang.String accountCode) {
		set("accountCode", accountCode);
	}
	
	public java.lang.String getAccountCode() {
		return getStr("accountCode");
	}

	public void setType(java.lang.Integer type) {
		set("type", type);
	}
	
	public java.lang.Integer getType() {
		return getInt("type");
	}

	public void setFlag(java.lang.Integer flag) {
		set("flag", flag);
	}
	
	public java.lang.Integer getFlag() {
		return getInt("flag");
	}

}
