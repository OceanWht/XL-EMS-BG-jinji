package com.ems.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXlDevices<M extends BaseXlDevices<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setDid(java.lang.String did) {
		set("did", did);
	}
	
	public java.lang.String getDid() {
		return getStr("did");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public void setDt(java.lang.String dt) {
		set("dt", dt);
	}
	
	public java.lang.String getDt() {
		return getStr("dt");
	}

	public void setDataid(java.lang.String dataid) {
		set("dataid", dataid);
	}
	
	public java.lang.String getDataid() {
		return getStr("dataid");
	}

	public void setAid(java.lang.String aid) {
		set("aid", aid);
	}
	
	public java.lang.String getAid() {
		return getStr("aid");
	}

	public void setGid(java.lang.String gid) {
		set("gid", gid);
	}
	
	public java.lang.String getGid() {
		return getStr("gid");
	}

	public void setUid(java.lang.String uid) {
		set("uid", uid);
	}
	
	public java.lang.String getUid() {
		return getStr("uid");
	}

	public void setPid(java.lang.String pid) {
		set("pid", pid);
	}
	
	public java.lang.String getPid() {
		return getStr("pid");
	}

	public void setPname(java.lang.String pname) {
		set("pname", pname);
	}
	
	public java.lang.String getPname() {
		return getStr("pname");
	}

	public void setIo(java.lang.String io) {
		set("io", io);
	}
	
	public java.lang.String getIo() {
		return getStr("io");
	}

	public void setUserid(java.lang.String userid) {
		set("userid", userid);
	}
	
	public java.lang.String getUserid() {
		return getStr("userid");
	}

}