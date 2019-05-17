package com.ems.unit;

import com.ems.common.model.XlAnalogdoc;
import com.ems.common.model.XlUnitlink;

import java.util.List;

/**
 * 本 ems 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * WarningService
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class UnitService {
	
	/**
	 * 所有的 dao 对象也放在 Service 中，并且声明为 private，避免 sql 满天飞
	 * sql 只放在业务层，或者放在外部 sql 模板，用模板引擎管理：
	 * 			http://www.jfinal.com/doc/5-13
	 */
	public List<XlUnitlink> getUnit(String userid){

		return new XlUnitlink().dao().find("select * from xl_unitlink where userid='"+userid+"'");

	}
	public List<XlUnitlink> getChildUnit(int puid,String userid){

		return new XlUnitlink().dao().find("select * from xl_unitlink where parentunitid="+puid  +" and userid='"+userid+"'");

	}
	public List<Unitlink> getUnitlink(int puid,int io,int dataid,String userid){

		String sql ="";
		if (io>-1){
			sql = " and  io="+io;
		}
		return new Unitlink().dao().find("select * from xl_unitlink where  dataid='"+dataid+"'  and  parentunitid="+puid  +" and userid='"+userid+"'"+sql+" order by uid asc");

	}

	public List<Devices> getDeviceByDataID(int dataid,String userid){

		return new Devices().dao().find("select * from v_ud where  dataid='"+dataid+"'  and   userid='"+userid+"'");

	}
	public List<Devices> getChildevices(int uid,int dataid,String userid){

//		return new Devices().dao().find("select * from v_ud where  ioo=2 and  dataid='"+dataid+"'  and  uid  ="+uid+"  and userid='"+userid+"'");
		return new Devices().dao().find("select * from v_ud where dataid='"+dataid+"'  and  uid  ="+uid+"  and userid='"+userid+"'");

	}
	public List<XlAnalogdoc> getChildAidDoc(String did, String userid){

		return new XlAnalogdoc().dao().find("select * from xl_analogdoc where did="+did+" and userid='"+userid+"'");

	}

}
