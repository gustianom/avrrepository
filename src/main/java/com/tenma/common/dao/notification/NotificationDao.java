package com.tenma.common.dao.notification;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.NotificationModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Fri Jan 17 15:30:16 WIB 2014
 */
public class NotificationDao extends Dao {
    public NotificationDao(SqlSession session) {
        super(session);
    }

    public NotificationModel getNotification(NotificationModel model) {
        return (NotificationModel) session.selectOne("getNotification", model);
    }

    public int insertNotification(NotificationModel systemProperty) {
        int result = 0;
        result = session.insert("insertNotification", systemProperty);
        return result;
    }

    public int updateNotification(NotificationModel systemProperty) {
        int result = 0;
        result = session.update("updateNotification", systemProperty);
        return result;
    }

    public int deleteNotification(NotificationModel systemProperty) {
        int result = 0;
        result = session.delete("deleteNotification", systemProperty);
        return result;
    }


    public List listNotification() {
        List list = session.selectList("listNotification", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalNotification", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<NotificationModel> listAvailableNotificationMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listNotificationMap", parameterObject);
        else
            return session.selectList("listAllNotificationMap", parameterObject);
    }
}
