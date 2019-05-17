package com.ems.moudles;


import com.ems.common.BaseController;
import com.ems.common.EmsInterceptor;
import com.ems.dnyapi.DNYAPI;
import com.ems.dnyapi.GetEleData;
import com.ems.dnyapi.GetWaterData;
import com.jfinal.aop.Before;

import java.text.SimpleDateFormat;
import java.util.Date;

@Before(EmsInterceptor.class)
public class MoudlesController extends BaseController {

    public void getBaseDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        String uid = getPara("uid");

        System.out.println("自动任务获取基础信息开始,当前企业UID：" + uid);
        DNYAPI.syncLog("自动任务获取基础信息开始,当前企业UID：" + uid);
        GetWaterData wd = new GetWaterData();
        wd.getBaseDataFromMoudlues(uid);
        GetEleData ed = new GetEleData();
        ed.getBaseData();
        System.out.println("自动任务获取基础信息结束");
        DNYAPI.syncLog("自动任务获取基础信息结束,当前企业UID：" + uid);
        renderJson("同步成功");
    }

}
