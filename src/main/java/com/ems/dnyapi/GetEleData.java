package com.ems.dnyapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ems.common.model.*;
import com.ems.user.UserService;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GetEleData {


    private static final Logger LOGGER = LoggerFactory.getLogger(GetEleData.class);
    DataService service = new DataService();
    UserService uservice = new UserService();

    static List<Userinfo> users;

    public GetEleData() {

        users = uservice.findAllUser();
    }

    public List<Userinfo> GetUserFromLDB() {

        new Userinfo().dao().find("select * from userinfo");

        return null;
    }

    //从新联接口获取用户数据
    public void GetUser() {

    }

    public DNYUser UserLogin(String name, String pass) {

        return DNYAPI.Login(name, pass);

    }


    //获取基础数据
    public void getBaseData() {

        users = uservice.findAllUser();
        List<XlDatacode> datacodes;

        for (Userinfo user : users) {

            try {
                getUnitLink(user);
                getDevice(user);
            } catch (Exception e) {
                LOGGER.error("GetEleData getBaseData has error:" + e.getMessage());
                continue;
            }

        }
    }

    public void getDayDataByDate(String sdt, String edt) {


        for (Userinfo user : users) {

            String getAnalogIDString = service.getAnalogIDString(user.getUserid());
            String resp = DNYAPI.GetAnalogDayData(getAnalogIDString, sdt, edt, user.getToken());
            service.deleteDayData(user.getUserid(), sdt);
            if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {

                service.deleteDateCode(user.getUserid());
                List<XlDaydata> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlDaydata.class);

                for (XlDaydata item : icollection) {
                    item.setUserid(user.getUserid());
                    item.setSdt(sdt);
                    item.setEdt(edt);
                    item.save();
                }
            }
        }

    }

    public void getMonthDataByDate(String sdt, String edt) {


        for (Userinfo user : users) {


            String getAnalogIDString = service.getAnalogIDString(user.getUserid());
            String resp = DNYAPI.GetAnalogMonthData(getAnalogIDString, sdt, edt, user.getToken());

            if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {

                service.deleteDateCode(user.getUserid());
                List<XlMonthdata> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlMonthdata.class);

                for (XlMonthdata item : icollection) {
                    item.setUserid(user.getUserid());
                    item.setSdt(sdt);
                    item.setEdt(edt);
                    item.save();
                }
            }
        }


    }


    public String getUnitLink(Userinfo user) {

        //用电数据

        String resp = DNYAPI.GetUnitLink(user.getUid(), user.getToken());

        dealUnitLinkE(user, JSON.parseObject(resp));

        return service.getUnitLinkString(user.getUserid());
    }

    public void dealUnitLinkE(Userinfo user, JSONObject jsonObject) {
        JSONArray jsonarray;
        if (jsonObject.containsKey("body")) {
            jsonarray = jsonObject.getJSONArray("body");
        } else {

            jsonarray = jsonObject.getJSONArray("cu");
        }

        for (int i = 0; i < jsonarray.size(); i++) {

            JSONObject cujo = jsonarray.getJSONObject(i);

            XlUnitlink unitlink = cujo.toJavaObject(XlUnitlink.class);
            if (unitlink.getParentunitid() == null) {
                unitlink.setParentunitid(0);
            }
            unitlink.setUserid(user.getUserid());
            //去重
            List<XlUnitlink> unitlinkList = service.queryUnitLink2(unitlink);
            if (unitlinkList != null && unitlinkList.size() != 0) {
                continue;
            } else {
                unitlink.save();
            }
            if (cujo.containsKey("cu")) {
                dealUnitLinkE(user, cujo);
            }
        }

    }


    public void getDevice(Userinfo user) {

        String uids = service.getUnitLinkEString(user.getUserid());

        service.deleteDevicesE(user.getUserid());


        String resp = DNYAPI.GetUnitIn(uids, user.getToken());

        if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {

            List<XlDevicesE> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlDevicesE.class);

            for (XlDevicesE item : icollection) {
                item.setUserid(user.getUserid());

                item.setIo("1");

                item.save();
            }
        }

        resp = DNYAPI.GetUnitOut(uids, user.getToken());


        if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {

            List<XlDevicesE> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlDevicesE.class);

            for (XlDevicesE item : icollection) {
                item.setUserid(user.getUserid());

                item.setIo("0");

                item.save();
            }
        }
    }

}
