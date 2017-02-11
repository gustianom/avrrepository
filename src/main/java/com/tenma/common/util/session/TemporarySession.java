package com.tenma.common.util.session;


import com.tenma.common.bean.session.SessionCounterListenerHelper;
import com.tenma.common.model.SessionCounterModel;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/28/13
 * Time: 1:51 PM
 */
public class TemporarySession implements Serializable {

    private static TemporarySession instance;
    private HashMap map;
    private ConcurrentHashMap mapSession;
    private boolean thread;

    private TemporarySession() {
        map = new HashMap();
        mapSession = new ConcurrentHashMap();
    }

    public static synchronized TemporarySession getInstance() {
        if (instance == null) {
            instance = new TemporarySession();
        }
        return instance;
    }

    public synchronized void putData(String sessionId, Object data) {
        map.put(sessionId, data);
    }

    public synchronized Object getData(String sessionId) {
        System.out.println("TemporarySession.getData " + map);
        return map.get(sessionId);
    }

    public synchronized void removeData(final String sessionId) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                doRemoveData(sessionId);
                doRemoveSession(sessionId);
            }
        };
        Thread th = new Thread(run);
        th.start();
    }

    private void doRemoveData(String sessionId) {
        if (map.containsKey(sessionId)) {
            SessionCounterModel m = new SessionCounterModel();
            m.setSessionId(sessionId);
            deletePhysicalFile(m);
            map.remove(sessionId);
        }
    }

    private void doRemoveSession(String sessionId) {
        if (mapSession.containsKey(sessionId)) {
            mapSession.remove(sessionId);
        }
    }

    private void deletePhysicalFile(SessionCounterModel m) {
        try {
            SessionCounterListenerHelper counterHelper = new SessionCounterListenerHelper();
            List ls = counterHelper.getSessionCounterList(m);
            if (ls != null) {
                for (int i = 0; i < ls.size(); i++) {
                    SessionCounterModel tmp = (SessionCounterModel) ls.get(i);
                    File fl = new File(new StringBuffer().append(tmp.getSessionPath()).append(File.separator).append(tmp.getTemporaryFileId()).toString());
                    System.out.println("tmp.getTemporaryFileId() = " + tmp.getTemporaryFileId());
                    System.out.println("fl.getAbsolutePath()+ \" \" + fl.getName() = " + fl.getAbsolutePath() + " " + fl.getName());
                    fl.setExecutable(true);
                    fl.setReadable(true);
                    fl.setWritable(true);
                    fl.delete();
                }
            }

            counterHelper.physicalDeleteSession(m);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // saving session with Id
    public void regSession(HttpSession session) {
        if (session != null) {
            mapSession.put(session.getId(), session);
        }
    }

    public synchronized void fireOnMessageEvent(Object o, OnMessageEvent event) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                doFireEvent(o, event);
            }
        };
        Thread th = new Thread(run);
        th.start();
    }

    private void doFireEvent(Object o, OnMessageEvent event) {
        mapSession.forEach((id, osession) -> {
            if (osession != null) {
                HttpSession session = (HttpSession) osession;
                System.out.println("Fire to " + session.getId());
                event.fireOnMessgeEvent(o, session);
            }
        });
    }


}