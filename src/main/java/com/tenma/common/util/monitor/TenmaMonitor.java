/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.common.util.monitor;

import com.tenma.common.TA;
import com.tenma.common.bean.activitylog.TmpActivityHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.sequence.SequenceTool;
import com.tenma.model.common.TenmaMonitorModel;
import com.tenma.model.common.TmpActivityModel;
import com.tenma.model.common.UserLoadActivityModel;

import java.util.*;

/**
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/13/14
 * Time: 11:10 AM
 * <p>
 * Note : Currently only support 1 listener, if required more than one listener, listener varible update into list of listner.
 */

//TODO = add dev mode status to avoid logging
public class TenmaMonitor implements Runnable {
    private static HashMap rootLog;
    private static HashMap sessionUserMap;
    private static HashMap userStatTime;
    //    private static HashMap pageLoadMap;
    private static TenmaMonitor instance;
    private static boolean monitor = true;
    private TenmaMonitorListener listener;
    private String listenerProcessId;

    private TenmaMonitor() {
        monitor = true;
        rootLog = new HashMap();
        sessionUserMap = new HashMap();
        userStatTime = new HashMap();
        startThread();
    }


    public static TenmaMonitor getInstance() {
        if (instance == null) {
            instance = new TenmaMonitor();
        }

        if (TA.getCurrent().getClientInfo() != null) {
            String userId = TA.getCurrent().getClientInfo().getClientUserModel().getUserId();
            if (rootLog.get(userId) == null) {
                HashMap newMap = new HashMap();
                rootLog.put(userId, newMap);
            }
        }
        return instance;
    }

    public static void shutDown() {
        monitor = false;

    }

    public void setStartTime(String user, long time) {
//        System.out.println("Tenma Monitor reg userId = " + user);
//        System.out.println("time = " + time);
        userStatTime.put(user, new Long(time));
    }

    public void registerLog(String sessionId, String userId) {
//        System.out.println("Tenma Monitor reg userId = " + userId);
//        System.out.println("Session id = " + sessionId);
        sessionUserMap.put(sessionId, userId);
        if (listener != null) {
            listener.newUserLogOn(userId);
        }
    }

    public synchronized void pageLoaded() {
        if (TA.getCurrent().getClientInfo() != null)  //not yet logged
            if (TA.getCurrent().getClientInfo().getSessionProcessId() == null) {
                System.out.println("Tenma ProcessId is not set, set super(setProcessId(tenmaApplication, \"processId\"), booleanVar) on construtor");
//                (new Exception("Tenma ProcessId is not set, set super(setProcessId(tenmaApplication, \"processId\"), booleanVar) on construtor")).printStackTrace();
            } else {
                if (!TA.getCurrent().getClientInfo().getSessionProcessId().equals(Constants.SYSTEM_PAGE_LOAD)) {
                    pageLoaded(TA.getCurrent().getClientInfo().getSessionProcessId());
                }
            }

    }

    public void startThread() {    // this thread purpose just for unit testing
//        new Thread(this).start();
    }

    public void registerListener(TenmaMonitorListener alistener, String processId) {
        listener = alistener;
        listenerProcessId = processId;
    }

    private synchronized void pageLoaded(Integer menuId) {
        System.out.println("PAGE - " + menuId);
        if (!monitor) {
            monitor = true;
            startThread();
        }

        String userId = TA.getCurrent().getClientInfo().getClientUserModel().getUserId();
        // Time duration vs prev load
        long now = Calendar.getInstance().getTimeInMillis();
        if (rootLog.get("MN" + userId) == null) {   // first userLoadPage  save state
            rootLog.put("MN" + userId, menuId);
        } else {
            Integer oldMenu = (Integer) rootLog.get("MN" + userId);
            System.out.println("OLD menu Id = " + oldMenu);
            // put the new one
            rootLog.put("MN" + userId, menuId);

            HashMap pageLoad = (HashMap) rootLog.get(userId);
            if (pageLoad != null && oldMenu != null) {  // update duration
                TenmaMonitorModel otm = (TenmaMonitorModel) pageLoad.get(oldMenu);
                if (otm != null) {
                    otm.setEndLog(now);
                    otm.setNextMenu(menuId);
                    pageLoad.put(otm.getMenuId(), otm);
                    rootLog.put(userId, pageLoad);
                }
            }
        }

        // Counter
        if (rootLog.get(userId) == null) {
            HashMap newMap = new HashMap();
            rootLog.put(userId, newMap);
        }
        HashMap pageLoadMap = (HashMap) rootLog.get(userId);
        Object om = pageLoadMap.get(menuId);
        TmpActivityHelper tmpHelper = new TmpActivityHelper();
        if (om != null) {
            TenmaMonitorModel oc = (TenmaMonitorModel) om;
            oc.addCounter();
            oc.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
            pageLoadMap.put(menuId, oc);
            if (listener != null && !!menuId.equals(listenerProcessId)) {
                listener.newPageLoad(oc, userId);
            }
            try {
                int i = tmpHelper.updateTmpActivity(userId, oc);
                System.out.println("update = " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            TenmaMonitorModel nc = new TenmaMonitorModel(menuId);
            nc.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
            pageLoadMap.put(menuId, nc);
            if (listener != null && !menuId.equals(listenerProcessId)) {
                listener.newPageLoad(nc, userId);
            }
            try {
                int i = tmpHelper.insertNewTmpActivity(userId, nc);
                System.out.println("insert = " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rootLog.put(userId, pageLoadMap);
    }

    @Override
    public void run() {
        while (monitor) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Size = " + rootLog.size() + " Page Load = " + getCount(rootLog));
        }
    }

    private String getCount(HashMap map) {
        Collection cm = map.values();
        Iterator iter = cm.iterator();
        StringBuffer counter = new StringBuffer();
        while (iter.hasNext()) {
            Object o = iter.next();
            if (o instanceof HashMap) {
                HashMap map2 = (HashMap) o;
                if (map2 != null) {
                    Collection cm2 = map2.values();
                    Iterator iter2 = cm2.iterator();
                    while (iter2.hasNext()) {
                        TenmaMonitorModel c = (TenmaMonitorModel) iter2.next();
//            System.out.println("c = " + c);
                        if (c != null) {
                            counter.append(" ");
                            counter.append(c.getMenuId() + "=");
                            counter.append(c.getCounter());
                            counter.append(" Duration : ");
                            counter.append(c.getEndLog() - c.getStartLog());
                            counter.append("\n");

                        }
                    }
                }
            }
        }
        return counter.toString();
    }

    public List getUserLog(String session) {
        List<UserLoadActivityModel> ls = new ArrayList<UserLoadActivityModel>();
        String userId = (String) sessionUserMap.get(session);
        if (userId != null) {
            setLastEndLog(userId);
//            System.out.println("Tenma Monitor reg userId = " + userId);
//            System.out.println("Session id = " + session);
            long time = ((Long) userStatTime.get(userId)).longValue();
            if (userId != null) {
                if (rootLog.get(userId) != null) {
                    HashMap pageLoadMap = (HashMap) rootLog.get(userId);
                    pageLoadMap.values().stream().forEach(e -> {
                        TenmaMonitorModel c = (TenmaMonitorModel) e;
                        UserLoadActivityModel m = new UserLoadActivityModel();
                        m.setCommunityId(c.getCommunityId());
                        m.setUserId(userId);
                        m.setMenuItemId(c.getMenuId());
                        m.setLogStart(new Date(time));
                        m.setLogEnd(new Date(Calendar.getInstance().getTimeInMillis()));
                        m.setHitCount(c.getCounter());
                        m.setItemDuration((int) (c.getEndLog() - c.getStartLog()));
                        m.setErrorCount(0);
                        m.setNextMenu(c.getNextMenu());
                        m.setLogId(SequenceTool.getInstance().getNewCounter("0", "LGID", true));
                        ls.add(m);
                    });
                }
            }
        }
//        System.out.println("ls.size() = " + ls.size());
        return ls;
    }

    private void setLastEndLog(String userId) {
//        System.out.println("TenmaMonitor.setLastEndLog "+userId);
        if (rootLog.get("MN" + userId) != null) {   // setendLog when logout
            try {
                Integer oldMenu = (Integer) rootLog.get("MN" + userId);
                long now = Calendar.getInstance().getTimeInMillis();
                // put the new one
                HashMap pageLoad = (HashMap) rootLog.get(userId);
                if (pageLoad != null) {  // update duration
                    TenmaMonitorModel otm = (TenmaMonitorModel) pageLoad.get(oldMenu);
                    otm.setEndLog(now);
                    pageLoad.put(otm.getMenuId(), otm);
                    rootLog.put(userId, pageLoad);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void plushSessionMap(String id, String communityId) {
        String userId = (String) sessionUserMap.get(id);
        sessionUserMap.remove(id);
        rootLog.remove(userId);
        rootLog.remove("MN" + userId);
        userStatTime.remove(userId);
        try {
            TmpActivityHelper helper = new TmpActivityHelper();
            TmpActivityModel m = new TmpActivityModel();
            m.setUserId(id);
            m.setCommunityId(communityId);
            helper.deleteTmpActivity(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("User Activity Log Status => " + getCount(rootLog));
        if (listener != null) {
            listener.newUserLogOn(userId);
        }
    }
}
