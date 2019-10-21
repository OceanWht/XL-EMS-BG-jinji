package com.ems.common;

import com.ems.common.model.MappingKit;
import com.ems.eer.EERController;
import com.ems.electric.ElectricController;
import com.ems.job.GetPlatFormCompany;
import com.ems.job.GetUserToken;
import com.ems.moudles.MoudlesController;
import com.ems.test.TestController;
import com.ems.unit.UnitController;
import com.ems.user.UserController;
import com.ems.warning.WarningController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.ems.fee.FeeController;

/*
 * API 引导式配置
 */
public class EmsConfig extends JFinalConfig {
	
	static Prop p;
	
	/**
	 * 启动入口，运行此 main 方法可以启动项目，此 main 方法可以放置在任意的 Class 类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		UndertowServer.start(EmsConfig.class);
	}
	
	/**
	 * 先加载开发环境配置，然后尝试加载生产环境配置，生产环境配置不存在时不会抛异常
	 * 在生产环境部署时后动创建 ems-config-pro.txt，添加的配置项可以覆盖掉
	 * ems.config 中的配置项
	 */
	static void loadConfig() {
		if (p == null) {
			p = PropKit.use("ems.config").appendIfExists("ems.config");
		}
	}

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadConfig();

		me.setDevMode(p.getBoolean("devMode", false));

		// 支持 Controller、Interceptor 之中使用 @Inject 注入业务层，并且自动实现 AOP
		me.setInjectDependency(true);
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {

		me.add("/fee", FeeController.class);
		me.add("/user", UserController.class);
		me.add("/moudles", MoudlesController.class);
		me.add("/warning", WarningController.class);
		me.add("/eer", EERController.class);
		me.add("/unit", UnitController.class);
		me.add("/test", TestController.class);
		me.add("/electric", ElectricController.class);

		// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
	}
	
	public void configEngine(Engine me) {
		//me.addSharedFunction("/common/_layout.html");
		//me.addSharedFunction("/common/_paginate.html");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
		me.add(druidPlugin);


		//缓存插件
		EhCachePlugin ehCachePlugin=new EhCachePlugin(getClass().getClassLoader().getResource("ehcache.xml"));
		me.add(ehCachePlugin);


		//定时任务
		/*配置说明
		* 数字 n：表示一个具体的时间点，例如 5 * * * * 表示 5 分这个时间点时执行
		* 逗号 , ：表示指定多个数值，例如 3,5 * * * * 表示 3 和 5 分这两个时间点执行
		* 减号 -：表示范围，例如 1-3 * * * * 表示 1 分、2 分再到 3 分这三个时间点执行
		* 星号 *：表示每一个时间点，例如 * * * * * 表示每分钟执行
		* 除号 /：表示指定一个值的增加幅度。例如 n/m表示从 n 开始，每次增加 m 的时间点执行
		*/
		Cron4jPlugin cp = new Cron4jPlugin();
		me.add(cp);

		// 配置ActiveRecord插件
		cp.addTask("1 1 * * *", new com.ems.job.GetBaseData());//每天1点更新基础信息
		//cp.addTask("*/5 * * * *", new com.ems.job.GetBaseData());
		cp.addTask("1 1 * * *", new com.ems.job.GetLastDayData());//每天1点获取昨日数据
		//cp.addTask("*/6 * * * *", new com.ems.job.GetLastDayData());
		cp.addTask("1,15,30,45 * * * *", new com.ems.job.GetDayData());//每十五分钟获取当日数据和当月数据
		//cp.addTask("*/7 * * * *", new com.ems.job.GetDayData());  //每一分钟执行一次，测试用
		cp.addTask("1 1 1 * *", new com.ems.job.GetMonthData());//每月1号获取上月数据
		//cp.addTask("*/8 * * * *", new com.ems.job.GetMonthData());
		cp.addTask("1,15,30,45 * * * *", new com.ems.job.GetWarningData());//获取预警数据
		//cp.addTask("*/9 * * * *", new com.ems.job.GetWarningData());
	//	cp.addTask("1,15,30,45 * * * *", new GetPlatFormCompany());//获取账号数据
		cp.addTask("1 1 * * *", new GetPlatFormCompany());
		cp.addTask("1 1 * * *", new GetUserToken());//模拟用户登录
		//cp.addTask("*/30 * * * *", new GetUserToken());

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		MappingKit.mapping(arp);
		me.add(arp);


	}
	
	public static DruidPlugin createDruidPlugin() {
		loadConfig();
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
}
