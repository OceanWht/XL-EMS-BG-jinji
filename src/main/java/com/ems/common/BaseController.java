package com.ems.common;

import com.ems.common.model.Userinfo;
import com.jfinal.core.Controller;

public class BaseController extends Controller {

    protected String getUID() {
        String token = getRequest().getHeader("token");
        return OnlineUser.get(token).getUid();
    }

    protected void setDefaultAtt() {
        setDefaultAtt(0, "", 0, null);
    }

    protected Userinfo getUser() {

        String token = getRequest().getHeader("token");
        return OnlineUser.get(token);
    }

    protected void setDefaultAtt(int code, String msg, int count, Object data) {

        setAttr("code", code);
        setAttr("msg", msg);
        setAttr("count", count);
        setAttr("data", data);
    }

}
