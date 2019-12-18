package com.ems.dnyapi;

import com.ems.common.model.*;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class DataService {

    public  void deleteDateCode(String userid){

        Db.delete("delete from xl_datacode where userid='"+userid+"'");

    }
    public  void deleteUnitLink(String userid,int dt){

        Db.delete("delete from xl_unitlink where dataid='"+dt+"' and userid='"+userid+"'");

    }

    public  void deleteCalcGroup(String userid){

        Db.delete("delete from xl_unitcalcgroup where  userid='"+userid+"'");

    }


    public  void deleteGroupAnalog(String userid){

        Db.delete("delete from xl_groupanalog where  userid='"+userid+"'");

    }

    public  void deleteDayData(String userid,String sdt){

        Db.delete("delete from xl_daydata where sdt='"+sdt+"' and userid='"+userid+"'");

    }
    public  void deleteAccount(){

        Db.delete("delete from xl_account");

    }
    public void updateAccount(XlAccount account){
        Db.update("update xl_account set pwd=\'"+account.getPwd()+"\' where uid=\'"+account.getUid()+"\' and name=\'"+account.getName()+"\'");
    }

    public List<XlAccount> queryAccount(XlAccount account)
    {
       return new XlAccount().dao().find("select * from xl_account where uid=\'"+account.getUid()+"\' and name=\'"+account.getName()+"\'");
    }


    public  void deleteMonthData(String userid,String sdt){

        Db.delete("delete from xl_monthdata where sdt='"+sdt+"' and  userid='"+userid+"'");

    }

    public  void deleteDevices(String userid){

        Db.delete("delete from xl_devices where  userid='"+userid+"'");

    }

    public  void deleteAidDia(String userid){

        Db.delete("delete from xl_aid_did where  userid='"+userid+"'");

    }
    public  void insertAidDia(String aid ,String did,String userid){

        Db.delete("insert into xl_aid_did(aid,did,userid) values("+aid+","+did+",'"+userid+"')");

    }
    public  void deleteDevicesE(String userid){

        Db.delete("delete from xl_devices_e where  userid='"+userid+"'");

    }

    public  void deleteAnalogDoc(String userid){

        Db.delete("delete from xl_analogdoc where  userid='"+userid+"'");

    }


    public List<XlUnitlink> getUnitLinks(String userid){
      return   new XlUnitlink().dao().find("select * from xl_unitlink where  userid='"+userid+"'");
    }

    public List<Record> getUnitLinkIDs(String userid){
        return   Db.find("select distinct uid from xl_unitlink where  userid='"+userid+"'");
    }

    public  String getUnitLinkString(String userid){

        return Db.queryStr("select  GROUP_CONCAT(uid) from  xl_unitlink where userid='"+userid+"'");
    }
    public  String getUnitLinkEString(String userid){

        return Db.queryStr("select  GROUP_CONCAT(uid) from  xl_unitlink_e where userid='"+userid+"'");
    }
    public  String getAnalogIDString(String userid){

        return Db.queryStr("select GROUP_CONCAT(distinct aid)   from xl_analogdoc where userid='"+userid+"'");
    }

    public  String getGroupidsString(String userid){

        return Db.queryStr("select  GROUP_CONCAT(groupid) from  xl_unitcalcgroup where userid='"+userid+"'");
    }

    public  String getGroupAnalogString(String userid){

        return Db.queryStr("select  GROUP_CONCAT(analogid) from  xl_groupanalog where userid='"+userid+"'");
    }
    public  String getDidsString(String userid){

        return Db.queryStr("select  GROUP_CONCAT(did) from  xl_devices where userid='"+userid+"'");
    }
    public  String getDatacodeString(String userid){

        return Db.queryStr("select  GROUP_CONCAT(dataid) from  xl_datacode where userid='"+userid+"'");
    }
    public String getUIDs(){
        return Db.queryStr("select  GROUP_CONCAT(uid) from  ems_company");
    }

    public  String getUids(){

        return Db.queryStr("select  GROUP_CONCAT(uid) from  userinfo");
    }
    public  List<XlUnitcalcgroup> getUnitcalcgroup(String userid){

        return   new XlUnitcalcgroup().dao().find("select * from xl_unitcalcgroup where  userid='"+userid+"'");
    }
    public  List<XlGroupanalog> getGroupAnalog(String userid){

        return   new XlGroupanalog().dao().find("select * from xl_groupanalog where  userid='"+userid+"'");
    }

    public List<EmsCompany> getAllCompany(){
        return new EmsCompany().dao().find("select *  from ems_company");
    }

    public  void DealBaseData(){

        String sql="update xl_groupanalog a  JOIN xl_unitcalcgroup g  on  a.groupid = g.groupid set a.uid = g.uid;";
        Db.update(sql);
        sql   =   "update xl_analogdoc d join xl_groupanalog a on d.aid = a.analogid set d.uid = a.uid;";
        Db.update(sql);
        sql   =   "update xl_aid_did a join xl_groupanalog g on a.aid= g.analogid set a.gid=g.groupid, a.uid = g.uid;";
        Db.update(sql);
        sql   =   "update xl_devices d  join xl_aid_did a on d.did = a.did  set d.uid = a.uid,d.gid=a.gid,d.aid=a.aid;";
        Db.update(sql);
        sql   =   "update xl_devices d  join xl_unitcalcgroup g on d.gid = g.groupid  set d.io = g.ioo,d.dataid=g.dataid;";
        Db.update(sql);


    }

    public List<XlDatacode> queryDatacode(XlDatacode item,String uid) {

        return new XlDatacode().dao().find("select * from xl_datacode where userid=\'"+uid+"\' and datacode=\'"+item.getDatacode()+"\' and dataname=\'"+item.getDataname()+"\' and dataid=\'"+item.getDataid()+"\'");
    }

    public List<XlUnitlink> queryUnitLink(XlUnitlink unitlink) {

        return new XlUnitlink().dao().find(" select * from xl_unitlink where dataid=\'"+unitlink.getDataid()+"\' and name=\'"+unitlink.getName()+"\' " +
                " and parentunitid=\'"+unitlink.getParentunitid()+"\' and userid=\'"+unitlink.getUserid()+"\' " +
                " and io=\'"+unitlink.getIo()+"\'");
    }

    public List<XlUnitlink> queryUnitLink2(XlUnitlink unitlink) {

        return new XlUnitlink().dao().find(" select * from xl_unitlink where name=\'"+unitlink.getName()+"\' " +
                " and parentunitid=\'"+unitlink.getParentunitid()+"\' and userid=\'"+unitlink.getUserid()+"\' " +
                " and ut=\'"+unitlink.getUt()+"\'");
    }

    public List<XlUnitcalcgroup> queryUnitcalcgroup(XlUnitcalcgroup item) {

        return new XlUnitcalcgroup().dao().find("select * from xl_unitcalcgroup where uid=\'"+item.getUid()+"\' " +
                " and uname=\'"+item.getUname()+"\' and ioo=\'"+item.getIoo()+"\' and groupname=\'"+item.getGroupname()+"\' " +
                " and groupid=\'"+item.getGroupid()+"\' and dataid=\'"+item.getDataid()+"\' " +
                " and datacode=\'"+item.getDatacode()+"\'");
    }
}
