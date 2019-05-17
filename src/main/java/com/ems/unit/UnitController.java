package com.ems.unit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.ems.common.BaseController;
import com.ems.common.EmsInterceptor;
import com.ems.dnyapi.DNYAPI;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Before(EmsInterceptor.class)
	public class UnitController extends BaseController {

	@Inject
	UnitService service;

	private  JSONObject jsonObject = new JSONObject();

	public void treelist() {

		int dataid =getParaToInt("dataid");
		int io =getParaToInt("io",-1);

		String key = "unitlist"+"-"+getUID()+"-"+dataid+"-"+io;

		List<Unitlink> list = CacheKit.get("Unitlink", key);
		if (list == null) {

			list = service.getUnitlink(0,io,dataid,getUID());
			for (Unitlink unitlink:list){
				setchild(unitlink);
			}
			CacheKit.put("Unitlink", key, list);
		}

		JSONObject resp = new JSONObject();
		resp.put("code",0);
		resp.put("msg","");
		resp.put("data",list);

		renderJson(resp.toJSONString());
		//renderJson(JSON.toJSONString(list));
	}
	public void  getDeviceByDataID(){
		int dataid =getParaToInt("dataid");
		List<Devices> list = service.getDeviceByDataID(dataid,getUID());
		setDefaultAtt(0,"",list.size(),list);
		renderJson();
	}
	private void setchild(Unitlink unitlink){

		unitlink.setChilds();
		for (Unitlink unitlink1:unitlink.children){
			setchild(unitlink1);
		}
	}

	public void getAnalogDayData(){
		String aid = getPara("aid");
		String sdt = getPara("sdt");
		String edt = getPara("edt");
		String iurl = getPara("iurl");
		String token = getHeader("token");

		String res = null;
		JSONArray body = null;
		if (iurl.equals("day")){
			res = DNYAPI.GetAnalogDayData(aid,sdt,edt,token);

		}
		else if (iurl.equals("month")){
			res = DNYAPI.GetAnalogMonthData(aid,sdt,edt,token);
		}

		//System.out.println(res);
		/*JSONObject jsonObject = JSONObject.parseObject(res);
		String status = jsonObject.getJSONObject("header").getString("status");
		if (status.equals("0")){
			body = jsonObject.getJSONArray("body");
			System.out.println(body);
		}*/

		renderJson(res);
	}

}


