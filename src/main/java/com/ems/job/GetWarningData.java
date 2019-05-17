package com.ems.job;


import com.ems.warning.WaringGen;
import com.jfinal.plugin.cron4j.ITask;

import java.sql.SQLException;


public class GetWarningData implements ITask {
    @Override
    public void run() {


        WaringGen wg = new WaringGen();
        try {
            wg.call();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void stop() {

    }




}
