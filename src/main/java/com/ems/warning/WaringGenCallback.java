package com.ems.warning;

import com.jfinal.plugin.activerecord.ICallback;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class WaringGenCallback implements ICallback {

    @Override
    public Object call(Connection conn) throws SQLException {
        CallableStatement proc = null;
        try {

            proc = (CallableStatement) conn.prepareCall("{ call genwarning() }"); // borrow为mysql的存储过程名，其中有两个参数，两个返回值

            proc.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }
}
