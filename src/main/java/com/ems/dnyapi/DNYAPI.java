package com.ems.dnyapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ems.common.model.Userinfo;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DNYAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(DNYAPI.class);

    //新联接口地址
    public static String APIUrl = PropKit.get("xlapiuri");

    //xl-moudles后台，发布时要改  http://energy.chinadny.com:8106/xl-manage/
    public static String SYNCUrl = PropKit.get("moudlesuri");


    public static String syncLog(String logMsg){
        Map<String,String> req = new HashMap<>();
        req.put("msg",logMsg);
        String resp = HttpKit.get(SYNCUrl+"system/syncdata/SyncLog",req);
        return resp;
    }


    public  static Map<String,String> GetHead(String token){

        Map<String,String> head  = new HashMap<String,String>();
        head.put("token",token);
        head.put("Content-Type","application/json");
        return head;

    }

    public  static Map<String,String> GetHead(){

        Map<String,String> head  = new HashMap<String,String>();
        head.put("Content-Type","application/json");
        return head;

    }

    public  static Map<String,String> GetLoginHead(){

        Map<String,String> head  = new HashMap<String,String>();
        head.put("Content-Type","application/x-www-form-urlencoded");
        return head;

    }

    //用户登录
    public  static DNYUser Login(String username, String password){

        String respstr  = HttpKit.post(APIUrl+"Account/Login","name="+username+"&pwd="+password+"&client_id=dny_power",GetLoginHead());
        JSONObject respjson = JSON.parseObject(respstr);
        if(respjson.getJSONObject("header").getInteger("status")==0){
            return  ((JSONObject) respjson.getJSONArray("body").get(0)).toJavaObject(DNYUser.class);
        }else{
            return null;
        }
    }

    public  static String LoginStr(String username,String password){

        return HttpKit.post(APIUrl+"Account/Login","name="+username+"&pwd="+password+"&client_id=dny_power",GetLoginHead());

    }
    //2	获取系统标题与用户信息
    public  static String SystemMenu(String userid,String password){

        Map<String,String> body  = new HashMap<String,String>();
        body.put("userid",userid);
        return HttpKit.post(APIUrl+"Account/SystemMenu",JsonKit.toJson(body),GetLoginHead());
    }

    //3.1	获取能源用户
    public static String GetPlatFormCompany(){


        DNYUser user= DNYAPI.Login(PropKit.get("testAccount"),PropKit.get("testPassword"));
        Map<String,String> body  = new HashMap<String,String>();
        body.put("pid","7");
        String respstr  = HttpKit.post(APIUrl+"Archive/GetPlatFormCompany",JsonKit.toJson(body),GetHead(user.getToken()));

//        String respstr  = HttpKit.post(APIUrl+"Account/GetAccountFeePublic","");

        return respstr;
    }
    //3.1	获取能源用户
    public static String GetAccountFeePublic(String uids){
        DNYUser user= DNYAPI.Login(PropKit.get("testAccount"),PropKit.get("testPassword"));
        Map<String,String> body  = new HashMap<String,String>();
        body.put("pid","7");
        body.put("uid",uids);
        String respstr  = HttpKit.post(APIUrl+"Account/GetAccountFeePublic",JsonKit.toJson(body),GetHead(user.getToken()));

        return respstr;
    }
    //3.1	获取用电结构
    public static String GetUnitLink(String UID,String token){

        Map<String,String> body  = new HashMap<String,String>();
        body.put("uid",UID);
        String respstr  = HttpKit.post(APIUrl+"Archive/GetUnitLink",JsonKit.toJson(body),GetHead(token));

        return respstr;
    }

    //3.2	获取单元日电量数据
    public static String GetUnitElectricDayData(String uid, Date sdt, Date edt,String io,String efc, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("uid",uid);
        body.put("sdt",sdt);
        body.put("edt",edt);
        body.put("io",io);
        body.put("efc",efc);
        String respstr  = HttpKit.post(APIUrl+"Unit/GetUnitElectricDayData",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }

    //3.3	获取单元月电量数据
    public static String GetUnitElectricMonthData(String uid, Date sdt, Date edt,String io,String efc, String token) {

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("uid", uid);
        body.put("sdt", sdt);
        body.put("edt", edt);
        body.put("io", io);
        body.put("efc", efc);
        String respstr = HttpKit.post(APIUrl + "Unit/GetUnitElectricMonthData", JsonKit.toJson(body), GetHead(token));
        return respstr;
    }


    //3.4	获取单元功率曲线数据
    public static String GetUnitPowerCurveData(String uid, Date sdt, Date edt,String io,String efc, String token) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("uid", uid);
        body.put("sdt", sdt);
        body.put("edt", edt);
        body.put("io", io);
        body.put("efc", efc);
        String respstr = HttpKit.post(APIUrl + "Unit/GetUnitPowerCurveData", JsonKit.toJson(body), GetHead(token));
        return respstr;
    }


    //3.5	获取单元输入关系
    public static String GetUnitIn(String uid, String token) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("uid", uid);
        String respstr = HttpKit.post(APIUrl + "Archive/GetUnitIn", JsonKit.toJson(body), GetHead(token));
        return respstr;
    }   //3.5	获取单元输入关系
    public static String GetUnitOut(String uid, String token) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("uid", uid);
        String respstr = HttpKit.post(APIUrl + "Archive/GetUnitOut", JsonKit.toJson(body), GetHead(token));
        return respstr;
    }
    //3.6	获取单元输出关系
    public static String GetUnitPowerCurveData(String uid, String token) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("uid", uid);
        String respstr = HttpKit.post(APIUrl + "Unit/GetUnitOut", JsonKit.toJson(body), GetHead(token));
        return respstr;
    }
    //3.7	获取取测量点当日/月电量数据
    public static String GetMeterElectricRealData(String did,String dtr, String token) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("uid", did);
        body.put("uid", dtr);
        String respstr = HttpKit.post(APIUrl + "Meter/GetMeterElectricRealData", JsonKit.toJson(body), GetHead(token));
        return respstr;
    }


    //3.8获取测量点曲线数据
    public static String GetMeterCurveData(String uid, Date sdt, Date edt,String di, String token) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("uid", uid);
        body.put("sdt", sdt);
        body.put("edt", edt);
        body.put("di", di);
        String respstr = HttpKit.post(APIUrl + "Meter/GetMeterCurveData", JsonKit.toJson(body), GetHead(token));
        return respstr;
    }

    //3.9	获取企业的异常告警数据
    public static String GetCompanyAbnormalAlarmData(String uid, Date sdt, Date edt,  String token) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("uid", uid);
        body.put("sdt", sdt);
        body.put("edt", edt);
        String respstr = HttpKit.post(APIUrl + "/Meter/GetCompanyAbnormalAlarmData", JsonKit.toJson(body), GetHead(token));
        return respstr;
    }


    ///4.1	获取计算组数据类别（可燃气、非可燃气中气体类别如氢气、天燃气等）
    public static String GetDataCode(String token){

        String respstr  = HttpKit.post(APIUrl+"Archive/GetDataCode","",GetHead(token));

        return respstr;
    }
    ///4.2	获取企业树结构（根据单元（能源）类型
    public static String GetUnitLinkByDataType(String uid,String dt,int io,String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("uid",uid);
        body.put("dt",dt);
        body.put("io", io );
        String respstr  = HttpKit.post(APIUrl+"Archive/GetUnitLinkByDataType",JsonKit.toJson(body),GetHead(token));

        return respstr;
    }

    //4.3	获取单元的计算组档案
    public static String GetUnitCalcGroup(String uid,String dataid,int ioo,int isall,String token){


        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("uid",uid);
        body.put("dataid",dataid);
        body.put("ioo", ioo );
        body.put("isall",isall);
        String respstr  = HttpKit.post(APIUrl+"Archive/GetUnitCalcGroup",JsonKit.toJson(body),GetHead(token));

        return respstr;
    }
    //4.4	获取计算组和模拟量关系
    public static String GetGroupAnalog(String groupid, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("groupid",groupid);
        String respstr  = HttpKit.post(APIUrl+"Archive/GetGroupAnalog",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }
    //4.5	根据模拟量id查找上级所属设备
    public static String GetDidByAnalogId(String aid, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("aid",String.valueOf(aid));
        String respstr  = HttpKit.post(APIUrl+"Archive/GetDidByAnalogId",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }
    //4.6	获取测量点基本档案
    public static String GetMeterArchive(String did, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("did",did);
        String respstr  = HttpKit.post(APIUrl+"Archive/GetMeterArchive",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }
    //4.7	获取指定设备下的所有模拟量
    public static String GetAnalogByDid(String did, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("did",did);
        String respstr  = HttpKit.post(APIUrl+"Archive/GetAnalogByDid",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }
    //4.8	获取模拟量档案
    public static String GetAnalogDoc(String aid, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("aid",aid);
        String respstr  = HttpKit.post(APIUrl+"Archive/GetAnalogDoc",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }
    //4.9	获取模拟量曲线数据
    public static String GetMeterAnalogTSCurveData(String did, Date sdt, Date edt, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("did",did);
        body.put("sdt",sdt);
        body.put("edt",edt);
        String respstr  = HttpKit.post(APIUrl+"Meter/GetMeterAnalogTSCurveData",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }
    //4.10	获取模拟量日数据
    public static String GetAnalogDayData(String aid, String sdt, String edt,String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("aid",aid);
        body.put("sdt",sdt);
        body.put("edt",edt);
        String respstr = null;
        try {

            respstr  = HttpKit.post(APIUrl+"Meter/GetAnalogDayData",JsonKit.toJson(body),GetHead(token));
        }
        catch (Exception e)
        {
            LOGGER.error("Meter/GetAnalogDayData has error:"+e.getMessage());
        }

        return respstr;
    }
    //4.11	获取模拟量月数据
    public static String GetAnalogMonthData(String aid, String sdt, String edt, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("aid",aid);
        body.put("sdt",sdt);
        body.put("edt",edt);
        String respstr  = HttpKit.post(APIUrl+"Meter/GetAnalogMonthData",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }
    //4.12	获取计算组曲线数据
    public static String GetUnitGroupCurveData(String uid, Date sdt, Date edt,int io,int dateid, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("uid",uid);
        body.put("sdt",sdt);
        body.put("edt",edt);
        body.put("io",io);
        body.put("dateid",dateid);
        String respstr  = HttpKit.post(APIUrl+"Unit/GetUnitGroupCurveData",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }

    //4.13	获取计算组日数据
    public static String GetUnitGroupDayData(String uid, Date sdt, Date edt,int io,int dateid, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("uid",uid);
        body.put("sdt",sdt);
        body.put("edt",edt);
        body.put("io",io);
        body.put("dateid",dateid);
        String respstr  = HttpKit.post(APIUrl+"Unit/GetUnitGroupDayData",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }


    //4.14	获取计算组月数据
    public static String GetUnitGroupMonthData(String uid, Date sdt, Date edt,int io,int dateid, String token){

        Map<String,Object> body  = new HashMap<String,Object>();
        body.put("uid",uid);
        body.put("sdt",sdt);
        body.put("edt",edt);
        body.put("io",io);
        body.put("dateid",dateid);
        String respstr  = HttpKit.post(APIUrl+"Unit/GetUnitGroupMonthData",JsonKit.toJson(body),GetHead(token));
        return respstr;
    }

    public static String TokenValid(Userinfo userinfo)
    {
        String token = userinfo.getToken();
        Map<String,Object> body  = new HashMap<String,Object>();
        if (token != null && token.length() != 0)
        {
            try {
                String respstr = HttpKit.post(APIUrl+"Account/TokenValid",JsonKit.toJson(body),GetHead(token));
                JSONObject respjson = JSON.parseObject(respstr);
                JSONObject headerMap=  (JSONObject)respjson.get("header");
                Integer status = (Integer) headerMap.get("status");
                if (status.equals(0))
                {
                    return token;
                }
            }
            catch (Exception e)
            {
                return null;
            }

        }

        return null;
    }





}
