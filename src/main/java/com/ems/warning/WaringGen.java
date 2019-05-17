package com.ems.warning;

import com.jfinal.plugin.activerecord.Db;
import java.sql.SQLException;

public class WaringGen {


    public  void call() throws SQLException {
        WaringGenCallback callbackTest  = new WaringGenCallback ();

        Db.execute(callbackTest );

    }
}
