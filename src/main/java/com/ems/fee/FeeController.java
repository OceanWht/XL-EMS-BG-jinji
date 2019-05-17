package com.ems.fee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ems.common.BaseController;
import com.ems.common.EmsInterceptor;
import com.ems.common.model.*;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.json.FastJson;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;


@Before(EmsInterceptor.class)
public class FeeController extends BaseController {
	
	@Inject
	FeeService service;

	public void list() {
		Page<FeeItem> list =  service.paginate(getParaToInt(0, 1), 20,getUID(),getPara("type"));
		setDefaultAtt(0,"",list.getTotalRow(),list.getList());
		renderJson();
	}
	@Before(FeeValidator.class)
	public void  add(){
		String jsonString = HttpKit.readData(getRequest());

		int type = JSON.parseObject(jsonString).getInteger("type");
		service.deleteItem(type,getUID());
		if(!JSON.parseObject(jsonString).getJSONArray("feeitems").isEmpty()){
			List<FeeItem> icollection = JSONObject.parseArray(JSON.parseObject(jsonString).getJSONArray("feeitems").toJSONString(), FeeItem.class);
			for (FeeItem item:icollection) {
				item.setId(null);
				item.setDtype(type);
				item.setUserid(getUID());
				item.save();
			}
		}
 		setAttr("code",0);
 		setAttr("msg", "");
 		setAttr("count",1);
 		setAttr("data", null);
 		renderJson();
	}


}


