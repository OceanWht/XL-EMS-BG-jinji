package com.ems.dnyapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ems.common.model.*;
import com.ems.user.UserService;
import com.mysql.jdbc.StringUtils;
import net.sf.cglib.core.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetWaterData {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetWaterData.class);

    DataService service = new DataService();
    UserService uservice = new UserService();

    static List<Userinfo> users;

    public GetWaterData() {
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

    public void getBaseDataFromMoudlues(String uid) {
        users = uservice.findAllUser();
        List<XlDatacode> datacodes;
        Set<Userinfo> userinfoSet = new HashSet<>();
        for (Userinfo userinfo : users) {
            if (uid.equals(userinfo.getUid())) {
                userinfoSet.add(userinfo);
            }
        }
        if (userinfoSet.size() == 0) {
            System.out.println("当前获取基本数据的UID：" + uid + ",当前企业的用户数据信息为空，无法同步数据");
            DNYAPI.syncLog("当前获取基本数据的UID：" + uid + ",当前企业的用户数据信息为空，无法同步数据");
            return;
        }
        for (Userinfo info : userinfoSet) {
            try {
                System.out.println("当前获取基本数据的用户名：" + info.getName());
                DNYAPI.syncLog("当前获取基本数据的用户名：" + info.getName());
                getBaseDataByUser(info);
                DNYAPI.syncLog("当前用户名：" + info.getName() + "获取基本数据结束");
                System.out.println("当前用户名：" + info.getName() + "获取基本数据结束");
            } catch (Exception e) {
                LOGGER.error("GetWaterData getBaseData has error:" + e.getMessage());
                System.out.println("当前用户名：" + info.getName() + "获取基本数据错误：" + e.getMessage());
                DNYAPI.syncLog("当前用户名：" + info.getName() + "获取基本数据错误：" + e.getMessage());
                continue;
            }
        }

    }

    //获取基础数据
    public void getBaseData() {
        LOGGER.info("GetWaterData getBaseData begin....");
        users = uservice.findAllUser();
        List<XlDatacode> datacodes;
        //获取企业档案
        GetPlatFormCompany();

        for (Userinfo user : users) {

            try {
                getBaseDataByUser(user);
            } catch (Exception e) {
                LOGGER.error("GetWaterData getBaseData has error:" + e.getMessage());
                System.out.println("GetWaterData getBaseData has error:" + e.getMessage());
                continue;
            }

        }
    }

    public void getBaseDataByUser(Userinfo user) throws RuntimeException {
        LOGGER.info("GetWaterData getBaseDataByUser begin....");
        List<XlDatacode> datacodes = getDateCode(user);
        getUnitLink(user, datacodes);
        getUnitCalcGroup(user, datacodes);
        getGroupAnalog(user);
        getDevice(user);
        getAnalogDoc(user);

        service.DealBaseData();
    }

    public void GetPlatFormCompany() {

        String resp = DNYAPI.GetAccountFeePublic(service.getUIDs());

        if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {
            // service.deleteAccount();
            List<XlAccount> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlAccount.class);
            for (XlAccount item : icollection) {
                List<XlAccount> xlAccountList = service.queryAccount(item);
                if (xlAccountList != null && xlAccountList.size() != 0) {
                    service.updateAccount(item);
                } else {
                    item.setPid("7");
                    item.save();
                }

            }
        }

    }

    public void getDayDataByDate(String sdt, String edt) {

        //System.out.println("getDayDataByDate("+sdt+" "+ edt +") users size " + users.size());
        for (Userinfo user : users) {

            String getAnalogIDString = service.getAnalogIDString(user.getUid());
            // String  resp = DNYAPI.GetAnalogDayData(getAnalogIDString,sdt,edt,user.getToken());
            if (null == getAnalogIDString || "".equals(getAnalogIDString)) {
                System.out.println("无模拟量档案getDayDataByDate(" + sdt + " " + edt + ")：" + user.getUid() + " " + user.getUserid() + " " + user.getName());
                DNYAPI.syncLog("无模拟量档案getDayDataByDate(" + sdt + " " + edt + ")：" + user.getUid() + " " + user.getUserid() + " " + user.getName());
                continue;
            }
            String resp = DNYAPI.GetAnalogDayData(getAnalogIDString, sdt, edt, user.getToken());
            //System.out.println("模拟量日数据返回结果："+ user.getUid() +" "+ user.getUserid() +" "+ user.getName() +" "+ resp);
            if (null == resp || "".equals(resp)) {
                System.out.println("无模拟量日数据getDayDataByDate(" + sdt + " " + edt + ")：" + user.getUid() + " " + user.getUserid() + " " + user.getName());
                DNYAPI.syncLog("无模拟量日数据getDayDataByDate(" + sdt + " " + edt + ")：" + user.getUid() + " " + user.getUserid() + " " + user.getName());
                continue;
            }

            if (JSON.parseObject(resp).getJSONArray("body") != null && !JSON.parseObject(resp).getJSONArray("body").isEmpty()) {
                service.deleteDayData(user.getUid(), sdt);
                List<XlDaydata> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlDaydata.class);
                for (XlDaydata item : icollection) {
                    item.setUserid(user.getUid());
                    item.setSdt(sdt);
                    item.setEdt(edt);
                    item.save();
                }
            }
        }


    }

    public void getMonthDataByDate(String sdt, String edt) {
        //System.out.println("getMonthDataByDate("+sdt+" "+ edt +") users size " + users.size());
        for (Userinfo user : users) {

            String getAnalogIDString = service.getAnalogIDString(user.getUid());
            if (null == getAnalogIDString || "".equals(getAnalogIDString)) {
                System.out.println("无模拟量档案getMonthDataByDate(" + sdt + " " + edt + ")：" + user.getUid() + " " + user.getUserid() + " " + user.getName());
                DNYAPI.syncLog("无模拟量档案getMonthDataByDate(" + sdt + " " + edt + ")：" + user.getUid() + " " + user.getUserid() + " " + user.getName());
                continue;
            }
            String resp = DNYAPI.GetAnalogMonthData(getAnalogIDString, sdt, edt, user.getToken());
            //System.out.println("模拟量月数据返回结果："+ user.getUid() +" "+ user.getUserid() +" "+ user.getName() +" "+ resp);
            if (null == resp || "".equals(resp)) {
                System.out.println("无模拟量月数据getMonthDataByDate(" + sdt + " " + edt + ")：" + user.getUid() + " " + user.getUserid() + " " + user.getName());
                DNYAPI.syncLog("无模拟量月数据getMonthDataByDate(" + sdt + " " + edt + ")：" + user.getUid() + " " + user.getUserid() + " " + user.getName());
                continue;
            }

            if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {
                service.deleteMonthData(user.getUid(), sdt);
                List<XlMonthdata> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlMonthdata.class);

                for (XlMonthdata item : icollection) {
                    item.setUserid(user.getUid());
                    item.setSdt(sdt);
                    item.setEdt(edt);
                    item.save();
                }
            }
        }


    }

    public List<XlDatacode> getDateCode(Userinfo user) throws RuntimeException {

        //模拟登录，获取有效token
      /* String token = AnalogLogin.analogLogin(user);
        if (null == token) {
            System.out.println("user"+ user);
            return null;
        }*/

        String resp = DNYAPI.GetDataCode(user.getToken());

        if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {

            service.deleteDateCode(user.getUid());
            List<XlDatacode> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlDatacode.class);

            for (XlDatacode item : icollection) {

                item.setUserid(user.getUid());
                List<XlDatacode> datacodeList = service.queryDatacode(item, user.getUid());
                if (datacodeList != null && datacodeList.size() != 0) {
                    continue;
                } else {
                    item.save();
                }


            }
            return icollection;
        } else {
            System.out.println("当前用户：" + user.getName() + " 获取计算组数据类别（可燃气、非可燃气中气体类别如氢气、天燃气等）为空");
            DNYAPI.syncLog("当前用户：" + user.getName() + " 获取计算组数据类别（可燃气、非可燃气中气体类别如氢气、天燃气等）为空");
            return null;
        }

    }

    public String getUnitLink(Userinfo user, List<XlDatacode> datacodes) throws RuntimeException {

        //模拟登录，获取有效token
        /*String token = AnalogLogin.analogLogin(user);
        if (null == token) {
 			System.out.println("user"+ user);
 			return null;
        } */
        for (XlDatacode datacode : datacodes) {
            System.out.println("删除deleteUnitLink========================================" +
                    "==========================删除deleteUnitLink=============================================" +
                    "==========================删除deleteUnitLink=============================================" +
                    "===========================删除deleteUnitLink=============================================" +
                    "================================删除deleteUnitLink==========================================================");
            service.deleteUnitLink(user.getUid(), datacode.getDataid());
            String resp = DNYAPI.GetUnitLinkByDataType(user.getUid(), String.valueOf(datacode.getDataid()), 1, user.getToken());
            if (user.getUid().equals("110236")){
                System.out.println("GetUnitLinkByDataType=============IO是1===========================" +user.getName()+
                        "==========================GetUnitLinkByDataType================IO是1=============================" +user.getPass()+
                        "==========================GetUnitLinkByDataType================IO是1=============================" +user.getUid()+
                        "===========================GetUnitLinkByDataType===============IO是1==============================" +
                        "================================GetUnitLinkByDataType============IO是1=============================================="+resp);
            }


            dealUnitLink(user, datacode, 1, JSON.parseObject(resp));

            resp = DNYAPI.GetUnitLinkByDataType(user.getUid(), String.valueOf(datacode.getDataid()), 0, user.getToken());
            if (user.getUid().equals("110236")){
                System.out.println("GetUnitLinkByDataType============resp1=========IO是0===================" +user.getName()+
                        "==========================GetUnitLinkByDataType=============resp1==========IO是0======================"  +user.getPass()+
                        "==========================GetUnitLinkByDataType===============resp1=========IO是0=====================" +user.getUid()+
                        "===========================GetUnitLinkByDataType==============resp1==========IO是0=====================" +
                        "================================GetUnitLinkByDataType===============resp1===========IO是0================================"+resp);
            }

            dealUnitLink(user, datacode, 0, JSON.parseObject(resp));
        }

        return service.getUnitLinkString(user.getUid());
    }

    public void dealUnitLink(Userinfo user, XlDatacode datacode, int io, JSONObject jsonObject) {
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
            unitlink.setUserid(user.getUid());
            unitlink.setDataid(datacode.getDataid());
            unitlink.setIo(io);
            unitlink.save();
            if (user.getUid().equals("110236")){
                System.out.println("unitlink保存============unitlink保存=======银邦金属=====================" +
                        "==========================unitlink保存=============unitlink保存===================银邦金属=============" +
                        "==========================unitlink保存===============unitlink保存=================银邦金属=============" +
                        "===========================unitlink保存==============unitlink保存==================银邦金属=============" +
                        "================================unitlink保存===============unitlink保存============银邦金属===============================");
            }


            if (cujo.containsKey("cu")) {
                dealUnitLink(user, datacode, io, cujo);
            }

        }

    }

    public void getUnitCalcGroup(Userinfo user, List<XlDatacode> datacodes) {
        //模拟登录，获取有效token
        /*String token = AnalogLogin.analogLogin(user);
        if (null == token) {
 			System.out.println("user"+ user);
 			return;
        }*/

        service.deleteCalcGroup(user.getUid());
        String units = service.getUnitLinkString(user.getUid());

        for (XlDatacode datacode : datacodes) {
            String resp = DNYAPI.GetUnitCalcGroup(units, String.valueOf(datacode.getDataid()), 0, 1, user.getToken());


            if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {

                List<XlUnitcalcgroup> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlUnitcalcgroup.class);

                for (XlUnitcalcgroup item : icollection) {
                    item.setUserid(user.getUid());
                    List<XlUnitcalcgroup> unitcalcgroupList = service.queryUnitcalcgroup(item);
                    if (unitcalcgroupList != null && unitcalcgroupList.size() != 0) {
                        continue;
                    } else {
                        item.save();
                    }

                }
            } else {
                System.out.println("当前用户：" + user.getName() + " 能源ID:" + datacode.getDataid() + " 获取单元计算组档案为空");
                DNYAPI.syncLog("当前用户：" + user.getName() + " 能源ID:" + datacode.getDataid() + " 获取单元计算组档案为空");
            }
        }
    }

    public void getGroupAnalog(Userinfo user) {

        //模拟登录，获取有效token
       /* String token = AnalogLogin.analogLogin(user);
        if (null == token) {
 			System.out.println("user"+ user);
 			return;
        }*/
        service.deleteGroupAnalog(user.getUid());
        String gids = service.getGroupidsString(user.getUid());
        if (StringUtils.isNullOrEmpty(gids)) {
            LOGGER.error("getGroupAnalog gid is null");
            return;
        }
        String resp = DNYAPI.GetGroupAnalog(gids, user.getToken());
        if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {
            List<XlGroupanalog> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlGroupanalog.class);

            for (XlGroupanalog item : icollection) {
                item.setUserid(user.getUid());
                item.save();
            }
        } else {
            System.out.println("当前用户：" + user.getName() + " groupids:" + gids + " 获取计算组和模拟量关系为空");
            DNYAPI.syncLog("当前用户：" + user.getName() + " groupids:" + gids + " 获取计算组和模拟量关系为空");
        }
    }

    public void getDevice(Userinfo user) {

        //模拟登录，获取有效token
        /*String token = AnalogLogin.analogLogin(user);
        if (null == token) {
 			System.out.println("user"+ user);
 			return;
        }*/
        String aids = service.getGroupAnalogString(user.getUid());

        List<XlGroupanalog> analoglist = service.getGroupAnalog(user.getUid());
        service.deleteDevices(user.getUid());
        String resp = DNYAPI.GetDidByAnalogId(aids, user.getToken());


        String dids = "";
        if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {
            service.deleteAidDia(user.getUid());
            for (int i = 0; i < JSON.parseObject(resp).getJSONArray("body").size(); i++) {

                service.insertAidDia(JSON.parseObject(resp).getJSONArray("body").getJSONObject(i).getString("aid"),
                        JSON.parseObject(resp).getJSONArray("body").getJSONObject(i).getString("did"),
                        user.getUid()
                );
                if (JSON.parseObject(resp).getJSONArray("body").size() == i + 1) {
                    dids += JSON.parseObject(resp).getJSONArray("body").getJSONObject(i).getString("did");
                } else {
                    dids += JSON.parseObject(resp).getJSONArray("body").getJSONObject(i).getString("did") + ",";
                }
            }
        } else {
            System.out.println("当前用户：" + user.getName() + " aids:" + aids + " 获取 根据模拟量id查找上级所属设备 为空");
            DNYAPI.syncLog("当前用户：" + user.getName() + " aids:" + aids + " 获取 根据模拟量id查找上级所属设备 为空");
        }
        resp = DNYAPI.GetMeterArchive(dids, user.getToken());


        if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {

            List<XlDevices> icollection = JSONObject.parseArray(JSON.parseObject(resp).getJSONArray("body").toJSONString(), XlDevices.class);

            for (XlDevices item : icollection) {
                item.setUserid(user.getUid());
                item.save();
            }
        } else {
            System.out.println("当前用户：" + user.getName() + " dids:" + dids + " 获取 测量点基本档案 为空");
            DNYAPI.syncLog("当前用户：" + user.getName() + " dids:" + dids + " 获取 测量点基本档案 为空");
        }
    }

    public void getAnalogDoc(Userinfo user) {

        //模拟登录，获取有效token
       /* String token = AnalogLogin.analogLogin(user);
        if (null == token) {
 			System.out.println("user"+ user);
 			return;
        }*/
        String dids = service.getDidsString(user.getUid());

        service.deleteAnalogDoc(user.getUid());
        String resp = DNYAPI.GetAnalogByDid(dids, user.getToken());


        String aids = "";
        String aid = "";
        if (!JSON.parseObject(resp).getJSONArray("body").isEmpty()) {
            for (int i = 0; i < JSON.parseObject(resp).getJSONArray("body").size(); i++) {
                aid = JSON.parseObject(resp).getJSONArray("body").getJSONObject(i).getString("aid");
                String did = JSON.parseObject(resp).getJSONArray("body").getJSONObject(i).getString("did");

                String resp1 = DNYAPI.GetAnalogDoc(aid, user.getToken());

                if (!JSON.parseObject(resp1).getJSONArray("body").isEmpty()) {

                    List<XlAnalogdoc> icollection = JSONObject.parseArray(JSON.parseObject(resp1).getJSONArray("body").toJSONString(), XlAnalogdoc.class);

                    for (XlAnalogdoc item : icollection) {
                        item.setUserid(user.getUid());
                        item.setDid(did);
                        item.save();
                    }
                } else {
                    System.out.println("当前用户：" + user.getName() + " aid: " + aid + " 获取模拟量档案 为空");
                    DNYAPI.syncLog("当前用户：" + user.getName() + " aid: " + aid + " 获取模拟量档案 为空");
                }

            }
        } else {
            System.out.println("当前用户：" + user.getName() + " dids: " + dids + " 获取指定设备下的所有模拟量 为空");
            DNYAPI.syncLog("当前用户：" + user.getName() + " dids: " + dids + " 获取指定设备下的所有模拟量 为空");
        }

    }
}
