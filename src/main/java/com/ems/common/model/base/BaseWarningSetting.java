package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseWarningSetting<M extends BaseWarningSetting<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setUserid(java.lang.String userid) {
		set("userid", userid);
	}
	
	public java.lang.String getUserid() {
		return getStr("userid");
	}

	public void setDid(java.lang.String did) {
		set("did", did);
	}
	
	public java.lang.String getDid() {
		return getStr("did");
	}

	public void setDtype(java.lang.Integer dtype) {
		set("dtype", dtype);
	}
	
	public java.lang.Integer getDtype() {
		return getInt("dtype");
	}

	public void setDayvalue(java.lang.Float dayvalue) {
		set("dayvalue", dayvalue);
	}
	
	public java.lang.Float getDayvalue() {
		return getFloat("dayvalue");
	}

	public void setMonthvalue(java.lang.Float monthvalue) {
		set("monthvalue", monthvalue);
	}
	
	public java.lang.Float getMonthvalue() {
		return getFloat("monthvalue");
	}

	public void setPressure(java.lang.Float pressure) {
		set("pressure", pressure);
	}
	
	public java.lang.Float getPressure() {
		return getFloat("pressure");
	}

	public void setBattery(java.lang.Float battery) {
		set("battery", battery);
	}
	
	public java.lang.Float getBattery() {
		return getFloat("battery");
	}

	public void setTemperature(java.lang.Float temperature) {
		set("temperature", temperature);
	}
	
	public java.lang.Float getTemperature() {
		return getFloat("temperature");
	}

	public void setDaytrend(java.lang.Integer daytrend) {
		set("daytrend", daytrend);
	}
	
	public java.lang.Integer getDaytrend() {
		return getInt("daytrend");
	}

	public void setMonthtrend(java.lang.Integer monthtrend) {
		set("monthtrend", monthtrend);
	}
	
	public java.lang.Integer getMonthtrend() {
		return getInt("monthtrend");
	}

}
