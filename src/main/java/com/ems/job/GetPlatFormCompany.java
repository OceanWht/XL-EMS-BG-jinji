package com.ems.job;

import com.ems.dnyapi.GetEleData;
import com.ems.dnyapi.GetWaterData;
import com.jfinal.plugin.cron4j.ITask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetPlatFormCompany implements ITask {
    public void run() {

        System.out.println("自动任务获取账号信息开始");
        GetWaterData wd= new GetWaterData();
        try {wd.GetPlatFormCompany();}catch(Exception ex){ System.out.println("错误：该功能只能在内网运行！"); }
        System.out.println("自动任务获取账号信息结束");

    }
    public void stop() {
        // 这里的代码会在 task 被关闭前调用
    }
}
