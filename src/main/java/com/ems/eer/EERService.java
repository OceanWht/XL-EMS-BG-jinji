package com.ems.eer;

import com.ems.common.model.EerBase;
import com.ems.common.model.EerCoal;
import com.ems.common.model.EerUnit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * 本 ems 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * WarningService
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class EERService {
	
	/**
	 * 所有的 dao 对象也放在 Service 中，并且声明为 private，避免 sql 满天飞
	 * sql 只放在业务层，或者放在外部 sql 模板，用模板引擎管理：
	 * 			http://www.jfinal.com/doc/5-13
	 */
	private EerBase dao = new EerBase().dao();
	private EerCoal cdao = new EerCoal().dao();
	private EerUnit udao = new EerUnit().dao();


	public Page<EerBase> paginate(String sdt, String edt,String dtype,String userid,String uid) {
		return dao.paginate(1, 100, "select *", "from eer_base where TO_DAYS(dt)>=TO_DAYS('"+sdt+"')   and  TO_DAYS(dt)<=TO_DAYS('"+edt+"') and dtype='"+dtype+"' and uid="+uid+"   and userid='"+userid+"' order by dt asc");
	}

	public Page<EerBase> mpaginate(String sdt, String edt,String dtype,String userid,String uid) {
		return dao.paginate(1, 100, "select id,dt as mon,consumption as consumption ", "from eer_base where char_length(dt)=7  and cast(left(dt,4) as signed) >= cast(left(\'"+sdt+"\',4) as signed) and cast(right(dt,2) as signed) >= cast(right(\'"+sdt+"\',2) as signed) and  cast(left(dt,4) as signed) <= cast(left(\'"+edt+"\',4) as signed) and  cast(right(dt,2) as signed) <= cast(right(\'"+edt+"\',2) as signed) and dtype=\'"+dtype+"\' and uid=\'"+uid+"\'  and  userid=\'"+userid+"\'  group by dt  order by dt asc");
	}

	public Page<EerCoal> cpaginate(int pageNumber, int pageSize,String dtype,String userid) {
		return cdao.paginate(pageNumber, pageSize, "select *", "from eer_coal where  dtype='"+dtype+"' and userid='"+userid+"'");
	}

	public EerUnit  getunit(String userid, String dtype, String uid) {
		return udao.findFirst("select * from eer_unit where dtype='"+dtype+"' and  uid="+uid+" and  userid='"+userid+"'");
	}
	public EerBase findById(int id) {
		return dao.findById(id);
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}

	public void deleteUnit(String userid, String dtype,String  uid){
		Db.delete("delete from eer_unit where uid="+uid+" and  dtype='"+dtype+"' and userid='"+userid+"'");
	}
	public void  deleteCoal(String userid, String dtype){

		Db.delete("delete from eer_coal where dtype='"+dtype+"' and userid='"+userid+"'");

	}

}
