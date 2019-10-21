package com.ems.test;


import com.ems.common.BaseController;
import com.ems.common.EmsInterceptor;
import com.ems.dnyapi.DNYAPI;
import com.ems.dnyapi.DNYUser;
import com.ems.dnyapi.GetWaterData;
import com.jfinal.aop.Before;

import java.text.SimpleDateFormat;
import java.util.Calendar;


@Before(EmsInterceptor.class)
public class TestController extends BaseController {


	public void  GetDataCode() {

		 new GetWaterData().getBaseData();

		 renderHtml("html");
	}

	public void  GetAccount() {

		new GetWaterData().GetPlatFormCompany();

		renderHtml("html");
	}

	public void  GetBaseData() {

		new GetWaterData().getBaseData();

		renderHtml("html");
	}

	public void  GetMonthData() {
		System.out.println("自动任务获取月数据开始");
		int m= getParaToInt("m",1);
		GetWaterData wd= new GetWaterData();

		String sdt="";

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -m);//取当前日期的前一天.

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");


		sdt = format.format(cal.getTime());
		System.out.println(sdt);
		wd.getMonthDataByDate(sdt,sdt);
		System.out.println("自动任务获取月数据结束");

	}

		public void  GetDayData() {

			int d= getParaToInt("d",1);
		System.out.println("自动任务获取当日数据开始");
		GetWaterData wd= new GetWaterData();
		String sdt="";

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -d);//取当前日期的前一天.

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sdt = format.format(cal.getTime());
		wd.getDayDataByDate(sdt,sdt);

		System.out.println("自动任务获取当日数据结束");

		System.out.println("============================");

		System.out.println("自动任务获取当月数据开始");

		cal = Calendar.getInstance();

		format = new SimpleDateFormat("yyyy-MM");
		sdt = format.format(cal.getTime());
		System.out.println("取数据月份："+sdt);
		wd.getMonthDataByDate(sdt,sdt);
		System.out.println("自动任务获取当月数据结束");

		renderHtml("html");
	}

	public void test1111(){
		String token = "wNXqqND0nw4eyHT1-2V10vP6H_82BvfpCK1jE9lRnC4kFOqHRfh6ejsPDAeRAWbRG9SyiBRw8a_heCVOHIfWbWaMhy7YN88aEDztd0FuVWfSSHFuEqdrBoPOk7AIyZdqvBoMP-YtRF_OULwvv9A3Q-cLt0CU4PzR1uXAEvH--E-JqAynTcVIZ3Gm0iqyhgbRFDQdAaA5YRAEvxkNWfj-m-nBZrLXCJPRyaxdBpaRPKYP-A93O6UXrplvBl-Kg5-C0G5QOejRDtAEFxDxL6CwvO4T3MnyWVIaIuCqDx-9_Te9a2YRbwqQrweDqesXl-mm4s_LzT15r0HomIlN4FX50Q";
		String res = DNYAPI.GetDataCode(token);
		System.out.println(res);
		String name = "xl1";
		String pwd = "klklkl";
		DNYUser dnyUser = DNYAPI.Login(name,pwd);
		System.out.println(dnyUser);
		renderJson();
	}

}


