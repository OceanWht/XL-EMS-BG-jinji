package com.ems.job;

import com.ems.common.model.Userinfo;
import com.ems.common.model.XlAccount;
import com.ems.dnyapi.DNYAPI;
import com.ems.dnyapi.DNYUser;
import com.ems.dnyapi.GetWaterData;
import com.ems.user.UserService;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.cron4j.ITask;

import java.util.List;

public class GetUserToken implements ITask {
    public void run() {


        UserService uservice = new UserService();

        System.out.println("自动登录获取用户token开始");
        GetWaterData wd= new GetWaterData();
        try {
            //获取用户数据包括密码
            wd.GetPlatFormCompany();

        }catch(Exception ex){ System.out.println("错误：该功能只能在内网运行！"); }

        List<XlAccount> accounts = (new XlAccount().dao()).find("select * from xl_account");


        for (XlAccount account : accounts) {

            DNYUser user= DNYAPI.Login(account.getName(),account.getPwd());
            if (user!=null){
                List<Userinfo> userinfoList = uservice.findUserInfo(account);
                if (userinfoList != null && userinfoList.size() != 0)
                {
                    uservice.loginSuccess(user,account.getPwd());
                }
                else
                {
                    Userinfo userinfo = new Userinfo();
                    userinfo.setName(account.getName());
                    userinfo.setPass(account.getPwd());
                    userinfo.setUid(String.valueOf(account.getUid()));
                    userinfo.setToken(user.getToken());
                    userinfo.setUserid(user.getUserID());
                    userinfo.save();
                }


            }
        }
        
        System.out.println("自动登录获取用户token");

    }
    public void stop() {
        // 这里的代码会在 task 被关闭前调用
    }
}
