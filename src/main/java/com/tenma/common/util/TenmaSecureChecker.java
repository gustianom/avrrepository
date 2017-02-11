package com.tenma.common.util;

import com.tenma.common.model.SecureCheckModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by gustianom on 2/20/14.
 */
public class TenmaSecureChecker implements Serializable {
    private static TenmaSecureChecker secure = null;
    private static HashMap mapData;

    private TenmaSecureChecker() {
        mapData = new HashMap();
    }

    public static TenmaSecureChecker getInstance() {
        if (secure == null)
            secure = new TenmaSecureChecker();
        return secure;
    }

    public static SecureCheckModel doCheckValidity(String userName, Locale local) {
        SecureCheckModel model = null;
        userName = userName.toUpperCase();

        Calendar cal = Calendar.getInstance(local);
        if (mapData.containsKey(userName)) {
            model = (SecureCheckModel) mapData.get(userName);
            int cnt = model.getCountAccess();
            cnt++;
            model.setCountAccess(cnt);
        } else {
            model = new SecureCheckModel();
            model.setUserName(userName);
            model.setCountFailedEnter(0);
            model.setCountAccess(1);
            model.setLastAccessDate(cal.getTime());
            mapData.put(userName, model);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("share.tenma.common.util.TenmaSecureChecker.doCheckValidity " + model);
        return model;
    }


    public static boolean isViewCaptcha(SecureCheckModel model, Locale local) {
        System.out.println("share.tenma.common.util.TenmaSecureChecker.isViewCaptcha " + model);
        Calendar calCurrent = Calendar.getInstance(local);
        Integer one_day = 1000 * 60 * 60 * 24;

        Date date1_ms = model.getLastAccessDate();
        Date date2_ms = calCurrent.getTime();

        long difference_ms = date2_ms.getTime() - date1_ms.getTime();
        difference_ms = difference_ms / 1000;
        double seconds = Math.floor(difference_ms % 60);
        difference_ms = difference_ms / 60;
        double minutes = Math.floor(difference_ms % 60);
        difference_ms = difference_ms / 60;
        double hours = Math.floor(difference_ms % 24);
        double days = Math.floor(difference_ms / 24);

        boolean isView = false;
//        if (days == 0 && hours == 0 && minutes <= 2 && model.getCountAccess() > 2)
//            isView = true;
//        else {
        if (model.getCountFailedEnter() > 2)
            isView = true;
//        }
        return isView;
    }

    public void remove(String userName) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("share.tenma.common.util.TenmaSecureChecker.remove " + userName);
        try {
            mapData.remove(userName);
            System.out.println("share.tenma.common.util.TenmaSecureChecker.remove " + mapData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
