package com.ems.job;

import com.jfinal.plugin.cron4j.ITask;

public class test implements ITask {
    public void run() {
        System.out.println("每分钟执行一次");
    }
    public void stop() {
        // 这里的代码会在 task 被关闭前调用
    }

}
