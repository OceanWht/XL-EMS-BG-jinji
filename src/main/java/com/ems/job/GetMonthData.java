package com.ems.job;

import com.ems.dnyapi.GetWaterData;
import com.jfinal.plugin.cron4j.ITask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetMonthData implements ITask {
    
    public void run() {

        System.out.println("自动任务获取月数据开始");
        GetWaterData wd= new GetWaterData();

        String sdt="";

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);//取当前日期的前一天.

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");


        sdt = format.format(cal.getTime());
        System.out.println(sdt);
        wd.getMonthDataByDate(sdt,sdt);
        System.out.println("自动任务获取月数据结束");


    }
    public void stop() {
        // 这里的代码会在 task 被关闭前调用
    }
}
