/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.audittrail;

import com.tenma.common.TA;
import com.tenma.common.util.web.ClientInfo;
import com.tenma.model.common.AuditTrailModel;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:19 PM
 */
public class TenmaLog implements Serializable {
    public static final int LOG_FOR_VIEW = 0;  // SQL QUERY
    public static final int LOG_FOR_ADD = 1;   // SQL INSERT
    public static final int LOG_FOR_UPDATE = 2; // SQL UPDATE
    public static final int LOG_FOR_DELETE = 3; // SQL DELETE
    public static final int LOG_FOR_MIXED = 4; // MIXED more than 1 SQL QUERY/DELETE/UPDATE
    public static final int LOG_FOR_INFO = 5; // Info Log
    public static final int LOG_FOR_ERROR = 6; // Info Log
    private static TenmaLog instance;

    private Random random = new Random();
    private HashMap openlog;
    private long now;
    private TenmaLogBuffer logBuffer;
    private int counter;

    private TenmaLog() {
        openlog = new HashMap();
        logBuffer = new TenmaLogBuffer();
        counter = 0;
        //logger.info("Tenma Statistical Log initilized...");
    }

    public TenmaLog(int a) {
        //logger.info(generateLogCode());
    }

    public static synchronized TenmaLog getInstance() {
        if (instance == null) {
            instance = new TenmaLog();
        }
        return instance;
    }

    public static void main(String[] args) {
        new TenmaLog(100);
    }

    /**
     * Generate uniq code for logging and the uniq code is needed when closed the log
     * uniq ocde contain : random (Long) + int digit random;
     *
     * @return
     */
    private String generateLogCode() {
        try {
            Calendar cal = Calendar.getInstance(TA.getCurrent().getLocale());
            now = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            now = Calendar.getInstance().getTimeInMillis();
        }
        counter++;
        String strCounter = String.valueOf(counter);
        while (strCounter.length() < 7) {
            strCounter = "0" + strCounter;
        }
        StringBuilder buf = new StringBuilder();

        buf.append(now);
        buf.append(strCounter);
        //logger.info("Generate Log Code : " + buf.toString());
        return buf.toString();
    }

    public HashMap getLogBuffer() {
        return logBuffer.getLogBuffer();
    }

    public synchronized void logInfo(String info, HttpSession session) {
        String userId = null;
        String custId = null;
        Integer processId = null;
        String reqIp = null;

        if (session != null) {
            ClientInfo u = TA.getCurrent().getClientInfo();
            userId = u.getClientUserModel().getUserId();
            processId = u.getSessionProcessId();
            reqIp = u.getIpAddress();
        }

        if (userId == null) userId = "";
//        if (processId == null) processId = "";
        if (reqIp == null) reqIp = "";

        AuditTrailModel model = new AuditTrailModel();
        model.setAuditId(generateLogCode());
        model.setCrudType(TenmaLog.LOG_FOR_VIEW);
        model.setLogBy(userId);
        model.setProcessId(processId);
        model.setUserId(userId);
        model.setLogFrom(reqIp);
        model.setDesc(info);
        model.setLogDate(Calendar.getInstance(TA.getCurrent().getLocale()).getTime());
        logBuffer.addNewLog(model);
    }

    public synchronized String openLog() {
        String code = generateLogCode();
        openlog.put(code, now);
        return code;
    }

    public void restoreLogToBuffer(AuditTrailModel logModel) {
        logBuffer.addNewLog(logModel);
    }

    /**
     * @param logCode
     * @param logtype
     * @param session
     * @param comment
     * @return
     * @deprecated
     */
    public long log(String logCode, int logtype, HttpSession session, String comment) {
        String userId = null;
        Integer processId = null;
        Integer moduleId = null;
        String reqIp = null;

        ClientInfo u = TA.getCurrent().getClientInfo();
        if (u != null) {
            userId = u.getClientUserModel().getUserId();
            processId = u.getSessionProcessId();
            reqIp = u.getIpAddress();

            if (userId == null) userId = "";
//            if (processId == null) processId = "";
//            if (moduleId == null) moduleId = "";
            if (reqIp == null) reqIp = "";
        }

        return log(logCode, logtype, userId, moduleId, processId, reqIp, comment);
    }

    public long log(String logCode, int logtype, String comment) {
        String userId = null;
        Integer processId = null;
        Integer moduleId = null;
        String reqIp = null;

        ClientInfo u = TA.getCurrent().getClientInfo();
        if (u != null) {
            userId = u.getClientUserModel().getUserId();
            processId = u.getSessionProcessId();
            moduleId = u.getSessionProcessGroupId();
            reqIp = u.getIpAddress();
        }

        if (userId == null) userId = "";
//        if (processId == null) processId = "";
//        if (moduleId == null) moduleId = "";
        if (reqIp == null) reqIp = "";

        return log(logCode, logtype, userId, moduleId, processId, reqIp, comment);
    }

    public void cancelLog(String logCode) {
        openlog.remove(logCode);
    }

    public long log(String logCode, int logtype, String userid, Integer moduleId, Integer processId, String reqIp, String comment) {
        Calendar cal = Calendar.getInstance(TA.getCurrent().getLocale());
        long logtime = cal.getTimeInMillis();
        Object o = openlog.get(logCode);
        long time = 0;
//        if (moduleId == null) moduleId = "0";
//        else if (moduleId.length() == 0) moduleId = "0";

        if (!((o == null) || (logtype < 0 || logtype > 6))) {

            //logger.info("o = " + o);
            long opentime = Long.parseLong(o.toString());
            time = logtime - opentime;
            AuditTrailModel model = new AuditTrailModel();
            model.setModuleId(moduleId);
            model.setAuditId(logCode);
            model.setCrudType(logtype);
            model.setProcessId(processId);
            model.setUserId(userid);
            model.setLogBy(userid);
            model.setLogFrom(reqIp);
            model.setLogDate(Calendar.getInstance(TA.getCurrent().getLocale()).getTime());
            model.setProcessTime((int) time);
            model.setDesc(comment);
            logBuffer.addNewLog(model);

//            helper.insertNewAuditTrail(model);
//           put log into buffer

            //logger.info("Log " + logtype + ":" + userid + "." + customerid + "." + companyid + "." + time + "." + comment);
            openlog.remove(logCode);
        } else {
            //logger.info("Log failed");
        }
        return time;
    }

    public long getNumLog() {
        return logBuffer.bufferSize();
    }

    public void logflush() {
        //TODO add fucntion to flush all logbuffer before App Server is shuting down;
    }


}

