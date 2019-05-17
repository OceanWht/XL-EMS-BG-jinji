package com.ems.common;

import com.ems.common.model.Userinfo;
import com.ems.moudles.MoudlesController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 本 ems 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * WarningInterceptor
 * 此拦截器仅做为示例展示，在本 ems 中并不需要
 */
public class EmsInterceptor implements Interceptor {
	
	public void intercept(Invocation inv) {
		inv.getController().getResponse().addHeader("Access-Control-Allow-Credentials","true");
		inv.getController().getResponse().addHeader("Access-Control-Allow-Origin",inv.getController().getRequest().getHeader("Origin"));
		inv.getController().getResponse().addHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type,token");
		inv.getController().getResponse().addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		inv.getController().getResponse().addHeader("Access-Control-Request-Headers","X-Requested-With,Content-Type,token");

		String token="";
		Controller controller = inv.getController();
		try {
			Userinfo userinfo = null;
			token = inv.getController().getRequest().getHeader("token");
			if (!(controller instanceof MoudlesController)){
                userinfo = OnlineUser.get(token);
                if (!"/user/login".equals(inv.getActionKey())&&userinfo==null){
                    nologin(inv);
                    return;
                }
			}
		}catch (Exception e){
			nologin(inv);
			return;
		}
		inv.invoke();
	}

	private void nologin(Invocation inv){
		inv.getController().setAttr("code",100);
		inv.getController().setAttr("msg", "用户未登陆");
		inv.getController().setAttr("count",1);
		inv.getController().setAttr("data", null);
		inv.getController().renderJson();

	}

}
