package com.ems.user;

import com.ems.common.model.Userinfo;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * 本 ems 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * WarningValidator.
 */
public class UserValidator extends Validator {
	
	protected void validate(Controller controller) {

		validateRequiredString("username", "userlogin", "请输入用户名。");
		validateInteger("pass", "userlogin", "请输入密码。");
	}
	
	protected void handleError(Controller controller) {

		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("data",null);
		respMap.put("code", 1);
		respMap.put("count", 0);
		controller.keepModel(Userinfo.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/fee/add")){
			respMap.put("msg", controller.getAttr("projectadd").toString());
			controller.renderJson(respMap);
		}
		else if (actionKey.equals("/fee/list")){
			controller.renderJson(respMap);

		}
	}
}
