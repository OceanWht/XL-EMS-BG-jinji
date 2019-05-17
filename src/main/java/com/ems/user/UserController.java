package com.ems.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ems.common.BaseController;
import com.ems.common.EmsInterceptor;
import com.ems.common.OnlineUser;
import com.ems.common.model.*;
import com.ems.dnyapi.DNYUser;
import com.ems.dnyapi.DNYAPI;
import com.ems.dnyapi.GetWaterData;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.PropKit;

import java.sql.Date;
import java.sql.Timestamp;


@Before(EmsInterceptor.class)
public class UserController extends BaseController {
	@Inject
	UserService service;

	public void login() {

		//调用新联接口登录
		String name = getPara("name");
		String pass = getPara("pass");
		String loginstring = DNYAPI.LoginStr(name,pass);
		JSONObject respjson = JSON.parseObject(loginstring);
		DNYUser xluser=new DNYUser();
		if(respjson.getJSONObject("header").getInteger("status")==0){

			xluser =  ((JSONObject) respjson.getJSONArray("body").get(0)).toJavaObject(DNYUser.class);
			//判断是否是能源用户
			if (!service.checkUser(xluser.getUID())){
				//如果不是新联能源用户用测试账号登录,并保存至表
				EerUserinfoTemp userinfoTemp = new EerUserinfoTemp();
				userinfoTemp.setName(name);
				userinfoTemp.setPass(pass);
				userinfoTemp.setUid(xluser.getUID());
				userinfoTemp.setUserid(xluser.getUserID());
				userinfoTemp.setToken(xluser.getToken());
				userinfoTemp.setCreateTime(new Timestamp(System.currentTimeMillis()));

				EmsCompany company = service.getCompany(xluser.getUID());
				if (company != null)
				{
					userinfoTemp.setCompanyName(company.getNAME());
				}

				userinfoTemp.save();

				loginstring = DNYAPI.LoginStr(PropKit.get("testAccount"),PropKit.get("testPassword"));
				respjson = JSON.parseObject(loginstring);
				xluser =  ((JSONObject) respjson.getJSONArray("body").get(0)).toJavaObject(DNYUser.class);
//				xluser.setToken(((JSONObject) respjson.getJSONArray("body").get(0)).get("token").toString());
				respjson.getJSONObject("header").put("msg2","该用户不是综合能源用户，现在使用测试账号登录!");
				loginstring=respjson.toJSONString();
				renderJson(loginstring);
			}


			//如果是能源用户
			Userinfo userinfo = service.findByUserId(xluser.getUserID());
			//如果本地库没有该用户，新建用户
			if (userinfo==null) {
				userinfo = new Userinfo();
				userinfo.setName(name);
				userinfo.setPass(pass);
				userinfo.setUid(xluser.getUID());
				userinfo.setToken(xluser.getToken());
				userinfo.setUserid(xluser.getUserID());
				userinfo.save();
				//创建用户基础数据
				new GetWaterData().getBaseDataByUser(userinfo);
			}


			OnlineUser.put(userinfo);
			//支持token多次使用
			Usertoken utoken = new Usertoken();

			utoken.setName(name);
			utoken.setPass(pass);
			utoken.setUid(xluser.getUID());
			utoken.setToken(xluser.getToken());
			utoken.setUserid(xluser.getUserID());
			utoken.save();
		}
		renderJson(loginstring);

	}

	private String  dealLogin(String name,String pass){


		String loginstring = DNYAPI.LoginStr(name,pass);

		return loginstring;

	}
	public void getuser(){

		renderJson(getUser());

	}

	public void addSetting(){

		String dataid= getPara("dataid");
		String datacode= getPara("datacode");
		service.deleteSetting(getUser().getUserid(),datacode);
		Usersetting model = new Usersetting();
		model.setDefaultdataid(dataid);
		model.setDatacode(datacode);
		model.setUserid(getUser().getUserid());
		model.save();
		setDefaultAtt();
		renderJson();
	}

	public void getSetting(){

		String datacode= getPara("datacode");
		String userid = getUser().getUserid();
		setDefaultAtt(0,"",1,service.getSetting(getUser().getUserid(),datacode));
		renderJson();
	}
	public void getlist() {
		renderJson();
	}



}


