/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.audittrail;

import com.tenma.model.common.AuditTrailModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:25 PM
 */
public class LogPersistanceScheduler implements Runnable {
    private boolean threadlive = true;
    private TenmaLog instanceLog;
    private long loop;

    public LogPersistanceScheduler() {
        instanceLog = TenmaLog.getInstance();
        loop = 0;
    }

    public void stop() {
        threadlive = false;
    }

    public void restart() {
        threadlive = true;
    }

    public void run() {
        int ssleep = 10000;
        //logger.info("Log Scheduler is started");
        while (threadlive) {
            loop++;
            long counter = instanceLog.getNumLog();
            long div = counter;
            long sleep;
            if (counter == 0) div = 1;
            sleep = ssleep / div;
            if (counter > 0) {
                insertDB();
            } else {
                //logger.info("["+loop+"] buffer is free");
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignored) {
            }
        }
        //logger.info("Log Scheduler is stoped");
    }

    private void insertDB() {
        //Todo explore how to submit batch insert using mybatis
        HashMap copyBuffer = instanceLog.getLogBuffer();
        AuditTrailHelper helper = new AuditTrailHelper();
        Collection cbuf = copyBuffer.values();
        Iterator iter = cbuf.iterator();
        while (iter.hasNext()) {
            AuditTrailModel model = (AuditTrailModel) iter.next();
            try {
                int rs = helper.insertNewAuditTrail(model);
                //logger.info("INSERT LOG " + rs + " log id " + model.getAuditId() + " - " + model.getCrudType());
            } catch (Exception e) {
                e.printStackTrace();
                //logger.info("failed to insert log to DB, restore to logbuffer " + model.getAuditId());
                // fail to insert log
                // put back to buffer
                instanceLog.restoreLogToBuffer(model);
            }
        }
    }
}
