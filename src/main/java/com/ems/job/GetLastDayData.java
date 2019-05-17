package com.ems.job;

import com.ems.dnyapi.GetWaterData;
import com.jfinal.plugin.cron4j.ITask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetLastDayData implements ITask {
    public void run() {

        System.out.println("自动任务获取昨日数据开始");
        GetWaterData wd= new GetWaterData();
        String sdt="";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);//取当前日期的前一天.
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sdt = format.format(cal.getTime());
         wd.getDayDataByDate(sdt,sdt);
        System.out.println("自动任务获取昨日数据结束");

    }
    public void stop() {
        // 这里的代码会在 task 被关闭前调用
    }
}
