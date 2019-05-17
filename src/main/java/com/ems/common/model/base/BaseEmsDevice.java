package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseEmsDevice<M extends BaseEmsDevice<M>> extends Model<M> implements IBean {

	public void setID(java.lang.Integer ID) {
		set("ID", ID);
	}
	
	public java.lang.Integer getID() {
		return getInt("ID");
	}

	public void setUID(java.lang.String UID) {
		set("UID", UID);
	}
	
	public java.lang.String getUID() {
		return getStr("UID");
	}

	public void setDID(java.lang.String DID) {
		set("DID", DID);
	}
	
	public java.lang.String getDID() {
		return getStr("DID");
	}

	public void setDT(java.lang.String DT) {
		set("DT", DT);
	}
	
	public java.lang.String getDT() {
		return getStr("DT");
	}

	public void setNAME(java.lang.String NAME) {
		set("NAME", NAME);
	}
	
	public java.lang.String getNAME() {
		return getStr("NAME");
	}

	public void setREMARK(java.lang.String REMARK) {
		set("REMARK", REMARK);
	}
	
	public java.lang.String getREMARK() {
		return getStr("REMARK");
	}

}
