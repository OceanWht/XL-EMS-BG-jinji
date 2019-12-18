package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseWarningInfo<M extends BaseWarningInfo<M>> extends Model<M> implements IBean {

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

	public void setDtype(java.lang.Integer dtype) {
		set("dtype", dtype);
	}
	
	public java.lang.Integer getDtype() {
		return getInt("dtype");
	}

	public void setWarningtitle(java.lang.String warningtitle) {
		set("warningtitle", warningtitle);
	}
	
	public java.lang.String getWarningtitle() {
		return getStr("warningtitle");
	}

	public void setDid(java.lang.String did) {
		set("did", did);
	}
	
	public java.lang.String getDid() {
		return getStr("did");
	}

	public void setWarningtype(java.lang.Integer warningtype) {
		set("warningtype", warningtype);
	}
	
	public java.lang.Integer getWarningtype() {
		return getInt("warningtype");
	}

	public void setWarningdate(java.lang.String warningdate) {
		set("warningdate", warningdate);
	}
	
	public java.lang.String getWarningdate() {
		return getStr("warningdate");
	}

	public void setDatakey(java.lang.String datakey) {
		set("datakey", datakey);
	}
	
	public java.lang.String getDatakey() {
		return getStr("datakey");
	}

	public void setReadflag(java.lang.Integer readflag) {
		set("readflag", readflag);
	}
	
	public java.lang.Integer getReadflag() {
		return getInt("readflag");
	}

	public void setDname(java.lang.String dname) {
		set("dname", dname);
	}
	
	public java.lang.String getDname() {
		return getStr("dname");
	}

	public void setWarningname(java.lang.String warningname) {
		set("warningname", warningname);
	}
	
	public java.lang.String getWarningname() {
		return getStr("warningname");
	}

	public void setAddtime(java.util.Date addtime) {
		set("addtime", addtime);
	}
	
	public java.util.Date getAddtime() {
		return get("addtime");
	}

	public void setWarningvalue(java.lang.String warningvalue) {
		set("warningvalue", warningvalue);
	}
	
	public java.lang.String getWarningvalue() {
		return getStr("warningvalue");
	}

	public void setWaringsettingvalue(java.lang.String waringsettingvalue) {
		set("waringsettingvalue", waringsettingvalue);
	}
	
	public java.lang.String getWaringsettingvalue() {
		return getStr("waringsettingvalue");
	}

}
