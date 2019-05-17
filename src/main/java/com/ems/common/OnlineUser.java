package com.ems.common;

import com.ems.common.model.Userinfo;

import java.util.HashMap;
import java.util.Map;

public class OnlineUser {

    private static Map map = new HashMap();

    public static void put(Userinfo user) {
        map.put(user.getToken(), user);
    }

    public static Userinfo get(String token) {

        Userinfo user = (Userinfo) map.get(token);

        if (user == null) {

            user = new Userinfo().getUserinfoByToken(token);
            if (user != null) {
                put(user);
            }

        }

        return user;

    }

    public static Userinfo get2(String token) {

        Userinfo user = (Userinfo) map.get(token);

        if (user == null) {

            user = new Userinfo().getUserByToken(token);
            if (user != null) {
                put(user);
            }

        }

        return user;

    }


}