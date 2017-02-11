package com.tenma.common.bean.languages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * User: ndwijaya
 * Date: 11/25/13
 * Time: 3:17 PM
 */
public class LanguageCache {
    private static LanguageCache instance;
    private HashMap cache, time;
    private long acceptedcachetime = 60 * 60 * 3; // 1 hours

    public LanguageCache() {
        cache = new HashMap();
        time = new HashMap();
    }

    public static LanguageCache getInstance() {
        if (instance == null) {
            instance = new LanguageCache();
        }
        return instance;
    }

    public boolean isCache(HashMap map) {
        //System.out.println("MAP SIZE ================= " + map.size());
        boolean ready = false;
        Object o = time.get("NEW");
        if (o != null) {
            long ct = Long.parseLong(o.toString());
            long now = Calendar.getInstance().getTimeInMillis();
            long old = now - ct;
            if (old < (acceptedcachetime)) {
                ready = true;
            }
        }
        return ready;
    }

    public void putcache(List ls, HashMap map) {
        //System.out.println("MAP SIZE ========x ========= " + map.size());
        long now = Calendar.getInstance().getTimeInMillis();
        time.put("NOW", now);
        cache.put("NOW", ls);

    }

    public List listAvailableLanguagesMap() {
        List ls = (List) cache.get("NOW");
        return ls;
    }
}
