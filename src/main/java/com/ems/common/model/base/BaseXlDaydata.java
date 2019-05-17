package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXlDaydata<M extends BaseXlDaydata<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setAid(java.lang.String aid) {
		set("aid", aid);
	}
	
	public java.lang.String getAid() {
		return getStr("aid");
	}

	public void setDt(java.lang.String dt) {
		set("dt", dt);
	}
	
	public java.lang.String getDt() {
		return getStr("dt");
	}

	public void setSum(java.lang.String sum) {
		set("sum", sum);
	}
	
	public java.lang.String getSum() {
		return getStr("sum");
	}

	public void setMax(java.lang.String max) {
		set("max", max);
	}
	
	public java.lang.String getMax() {
		return getStr("max");
	}

	public void setDid(java.lang.String did) {
		set("did", did);
	}
	
	public java.lang.String getDid() {
		return getStr("did");
	}

	public void setUid(java.lang.String uid) {
		set("uid", uid);
	}
	
	public java.lang.String getUid() {
		return getStr("uid");
	}

	public void setUserid(java.lang.String userid) {
		set("userid", userid);
	}
	
	public java.lang.String getUserid() {
		return getStr("userid");
	}

	public void setSdt(java.lang.String sdt) {
		set("sdt", sdt);
	}
	
	public java.lang.String getSdt() {
		return getStr("sdt");
	}

	public void setEdt(java.lang.String edt) {
		set("edt", edt);
	}
	
	public java.lang.String getEdt() {
		return getStr("edt");
	}

}
