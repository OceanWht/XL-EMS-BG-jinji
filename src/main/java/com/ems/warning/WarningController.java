package com.ems.warning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ems.common.BaseController;
import com.ems.common.EmsInterceptor;
import com.ems.common.model.WarningInfo;
import com.ems.common.model.WarningSetting;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.*;


@Before(EmsInterceptor.class)
public class WarningController extends BaseController {
	
	@Inject
    WarningService service;

	public void warningsettinglist() {

	    int dtype = getParaToInt("dtype");

        Page<WarningSetting> list =  service.paginate(getParaToInt(0, 1), 10, getUID(),dtype);

        setDefaultAtt(0,"",list.getTotalRow(),list.getList());

		renderJson();
	}

    public void  warningsettingadd() {

        String jsonString = HttpKit.readData(getRequest());
        int dtype = JSON.parseObject(jsonString).getInteger("dtype");
        if(!JSON.parseObject(jsonString).getJSONArray("settings").isEmpty()){
            List<WarningSetting> icollection = JSONObject.parseArray(JSON.parseObject(jsonString).getJSONArray("settings").toJSONString(), WarningSetting.class);

            service.deleteWarningSetting(dtype,getUID());
            for (WarningSetting item:icollection) {
                    item.setId(null);
                    item.setUserid(getUID());
                    item.setDtype(dtype);
                    item.save();
            }
        }
        setDefaultAtt();
        renderJson();
    }

    public void warninglist(){
        int type = getParaToInt("dtype");
         int wtype = getParaToInt("wtype");
        String sdt = getPara("sdt");
        String edt = getPara("edt")+" 23:59:59";
        Page<WarningInfo> list =  service.ipaginate(getParaToInt("page"), getParaToInt("limit"),getUID(),type,wtype,sdt,edt);
        service.readmsg(type,getUID());
        setDefaultAtt(0,"",list.getTotalRow(),list.getList());
        renderJson();
    }


    public void warningcount(){

        int type = getParaToInt("dtype");
        int wtype = getParaToInt("wtype");
        int read = getParaToInt("read");
        String warningdate = getPara("warningdate");
        List<Record> list =  service.warningcount(type,wtype,read,warningdate,getUID());
        service.readmsg(type,getUID());
        setDefaultAtt(0,"",list.size(),list);
        renderJson();
    }


    public void overview(){

        int dtype = getParaToInt("dtype");

        Record records =  service.overview(dtype,getUID());


        setDefaultAtt(0,"",1,records);
        renderJson();

    }

    public void overview2(){
        Map<String,String> map = new  HashMap<String,String>();
        List<Record>  records  = service.overview2(getUID());
        for(Record record:records){
            map.put(record.getStr("dataname"),record.getStr("num"));
        }
        for (int i=0;i<8;i++){
            if(!map.containsKey("t_dataid"+i)){
                map.put("t_dataid"+i,"0");
            }
            if(!map.containsKey("y_dataid"+i)){
                map.put("y_dataid"+i,"0");
            }
        }
        if(!map.containsKey("y_count")){
            map.put("y_count","0");
        }
        if(!map.containsKey("t_count")){
            map.put("t_count","0");
        }
        //t_dataid5
        setDefaultAtt(0,"",1,map);
        renderJson();

    }



}


