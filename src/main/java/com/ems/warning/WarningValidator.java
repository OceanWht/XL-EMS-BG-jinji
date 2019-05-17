package com.ems.warning;

import com.ems.common.model.WarningSetting;
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
public class WarningValidator extends Validator {
	
	protected void validate(Controller controller) {

		validateRequiredString("projectname", "projectadd", "请输入方案名称。");
		validateInteger("feetype", "projectadd", "系统错误，费用类型错误。");
	}
	
	protected void handleError(Controller controller) {

		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("data",null);
		respMap.put("code", 1);
		respMap.put("count", 0);
		controller.keepModel(WarningSetting.class);
		
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
