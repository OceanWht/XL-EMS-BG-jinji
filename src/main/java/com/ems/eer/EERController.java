package com.ems.eer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ems.common.BaseController;
import com.ems.common.EmsInterceptor;
import com.ems.common.Kits;
import com.ems.common.model.*;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Page;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Before(EmsInterceptor.class)
	public class EERController extends BaseController {

	@Inject
	EERService service;

	public void eerbaselist() {
		String sdt = getPara("sdt");
		String edt = getPara("edt");
		String uid = getPara("uid");
		String dtype = getPara("dtype");
		Page<EerBase> list =  service.paginate(sdt,edt,dtype,getUID(),uid);
		setDefaultAtt(0,"",list.getTotalRow(),list.getList());
		renderJson();
	}

	public void eermonthbaselist() {
		String sdt = getPara("sdt");//+"-01"
		//String edt = Kits.getLastDayOfMonth( getPara("edt"));
		String edt = getPara("edt");
		String dtype = getPara("dtype");
		String uid = getPara("uid");
		String uid1 = getUID();
		Page<EerBase> list =  service.mpaginate(sdt,edt,dtype,getUID(),uid);
		setDefaultAtt(0,"",list.getTotalRow(),list.getList());
		renderJson();
	}

	public void eeryearbaselist() {
		String year = getPara("year");//+"-01"
		String dtype = getPara("dtype");
		String uid = getPara("uid");
		String uid1 = getUID();
		Page<EerBase> list =  service.ypaginate(year,dtype,getUID(),uid);
		setDefaultAtt(0,"",list.getTotalRow(),list.getList());
		renderJson();
	}

	public void  eerbaseadd() throws ParseException {

		String jsonString = HttpKit.readData(getRequest());
		JSONArray icollections = JSONObject.parseArray(JSON.parseObject(jsonString).getJSONArray("eer").toJSONString());
		System.out.println(icollections);
		if (!icollections.isEmpty()){
			for (Object object: icollections){
				if (object instanceof JSONObject){
					JSONObject obj = (JSONObject)object;
					EerBase eerBase = new EerBase();
					eerBase.setUserid(getUID());
					eerBase.setUid(obj.getInteger("uid"));
					eerBase.setSingleconsumption(obj.getString("singleconsumption"));
					String date = obj.getString("dt");
					eerBase.setDt(date);
					eerBase.setDtype(obj.getInteger("dtype"));
					eerBase.setAdddate(new Date(System.currentTimeMillis()));
					eerBase.setConsumption(obj.getFloat("consumption"));
					Object id = obj.get("id");
					if (id == null || id.equals("")){
						eerBase.setId(null);
						eerBase.save();
					}
					else {
						eerBase.setId(obj.getInteger("id"));
						eerBase.update();
					}
				}
			}
		}
		/*if(!JSON.parseObject(jsonString).getJSONArray("eer").isEmpty()){
			List<EerBase> icollection = JSONObject.parseArray(JSON.parseObject(jsonString).getJSONArray("eer").toJSONString(), EerBase.class);

			for (EerBase item:icollection) {
				if(item.getId()==null){
					item.setId(null);
					item.setUserid(getUID());
					item.save();
				}else{
					item.setUserid(getUID());
					item.update();
				}
			}
		}*/
		setDefaultAtt();
		renderJson();
	}

	public void coallist(){
		Page<EerCoal> list =  service.cpaginate(getParaToInt(0, 1), 10,getPara("dtype"),getUID());

		setDefaultAtt(0,"",list.getTotalRow(),list.getList());

		renderJson();

	}

	public void coaladd() {


		service.deleteCoal(getUID(),getPara("dtype"));
		EerCoal item = new EerCoal();
		item.setDtype(getPara("dtype"));
		item.setRadio(getPara("radio"));
		item.setUserid(getUID());
		item.save();
		setDefaultAtt();
		renderJson();
	}
	public void eercount(){

		renderJson();
	}

	public void unitadd(){
		String jsonString = HttpKit.readData(getRequest());

		if(!JSON.parseObject(jsonString).getJSONArray("unit").isEmpty()){
			String dtype = JSON.parseObject(jsonString).getString("dtype");

			int uid = JSON.parseObject(jsonString).getInteger("uid");

				List<EerUnit> icollection = JSONObject.parseArray(JSON.parseObject(jsonString).getJSONArray("unit").toJSONString(), EerUnit.class);
				service.deleteUnit(getUID(),dtype, String.valueOf(uid) );
				for (EerUnit item:icollection){


						item.setId(null);
						item.setDtype(dtype);
						item.setUid(uid);
						item.setUserid(getUID());
						item.save();

			}
		}
		setDefaultAtt();
		renderJson();

	}

	public void getunit(){

		EerUnit model = service.getunit(getUID(),getPara("dtype"),getPara("uid"));
 		setDefaultAtt(0,"",1,model);
		renderJson();

	}




}


