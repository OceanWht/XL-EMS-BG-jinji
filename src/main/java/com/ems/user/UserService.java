package com.ems.user;

import com.ems.common.model.*;
import com.ems.dnyapi.DNYUser;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * 本 ems 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * WarningService
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class UserService {
	
	/**
	 * 所有的 dao 对象也放在 Service 中，并且声明为 private，避免 sql 满天飞
	 * sql 只放在业务层，或者放在外部 sql 模板，用模板引擎管理：
	 * 			http://www.jfinal.com/doc/5-13
	 */
	private Userinfo dao = new Userinfo().dao();

	public Userinfo findById(int id) {
		return dao.findById(id);
	}

	public Userinfo findByUserId(String userid) {
		return dao.findFirst("select * from userinfo where userid='"+userid+"'");
	}

	public void loginSuccess(DNYUser user,String pwd) {

		Db.update("update userinfo set token=\'"+user.getToken()+"\',pass= \'"+pwd+"\'where userid="+user.getUserID());
	}

	public List<Userinfo> findUserInfo(XlAccount xlAccount)
	{
		return new Userinfo().dao().find("select * from userinfo where name=\'"+xlAccount.getName()+"\' and uid=\'"+xlAccount.getUid()+"\'");
	}

	public List<Userinfo> findAllUser(){

 		return dao.find("select * from UserInfo");

	}

	public void deleteSetting(String userid,String datacode){

		Db.delete("delete from usersetting  where datacode='"+datacode+"' and userid='"+userid+"'");
	}

	public Usersetting getSetting(String userid,String datacode){

		return new Usersetting().findFirst("select * from usersetting  where datacode='"+datacode+"' and  userid='"+userid+"'");

	}

	public boolean checkUser(String uid){

		XlAccount xlaccount = new XlAccount().dao().findFirst("select  * from xl_account where uid = '"+uid+"'");
		if(xlaccount!=null){

			return true;
		}else{

			return false;
		}

	}

	public EmsCompany getCompany(String uid)
	{
		//return new EmsCompany().findFirst("select * from ems_company where UID = '"+uid+"'");
		EmsCompany company = new EmsCompany().findFirst("select * from ems_company where UID = '"+uid+"'");
		if (company != null)
		{
			return company;
		}
		return null;
	}

}
