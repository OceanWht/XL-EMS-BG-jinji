package com.ems.electric;

import com.ems.common.BaseController;
import com.ems.common.EmsInterceptor;
import com.ems.common.model.XlElectric;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Page;

@Before(EmsInterceptor.class)
public class ElectricController extends BaseController {
	
	@Inject
    ElectricService service;

    public void electriclist(){
        String uid = getPara("uid");
        String calctype = getPara("calctype");
        String day = getPara("day");
        Page<XlElectric> page =  service.paginate(getParaToInt("page",1), getParaToInt("limit",10),uid,calctype,day);
        setDefaultAtt(0,"",page.getTotalRow(),page.getList());
        renderJson();
    }


}


