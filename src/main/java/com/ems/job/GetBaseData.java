package com.ems.job;

import com.ems.dnyapi.GetEleData;
import com.ems.dnyapi.GetWaterData;
import com.jfinal.plugin.cron4j.ITask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetBaseData implements ITask {
    public void run() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

        System.out.println( "自动任务获取用户信息开始");
        GetWaterData wd= new GetWaterData();
        wd.getBaseData();
        GetEleData ed = new GetEleData();
        ed.getBaseData();
        System.out.println("自动任务获取用户信息结束");
    }
    public void stop() {
        // 这里的代码会在 task 被关闭前调用
    }
}
