package com.ems.job;

import com.ems.dnyapi.GetWaterData;
import com.jfinal.plugin.cron4j.ITask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDayData implements ITask {
    public void run() {
        System.out.println("自动任务获取当日数据开始");
        GetWaterData wd= new GetWaterData();
        String sdt="";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sdt = format.format(cal.getTime());
         wd.getDayDataByDate(sdt,sdt);
        System.out.println("自动任务获取当日数据结束");
        System.out.println("自动任务获取当月数据开始");
        cal = Calendar.getInstance();
        format = new SimpleDateFormat("yyyy-MM");
        sdt = format.format(cal.getTime());
        System.out.println("取数据月份："+sdt);
        wd.getMonthDataByDate(sdt,sdt);
        System.out.println("自动任务获取当月数据结束");

    }
    public void stop() {
        // 这里的代码会在 task 被关闭前调用
    }
}
