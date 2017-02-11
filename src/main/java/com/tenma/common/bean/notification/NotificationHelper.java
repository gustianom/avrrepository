package com.tenma.common.bean.notification;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.notification.NotificationDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
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
public class NotificationHelper extends TenmaHelper {
    public int insertNewNotification(NotificationModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewNotification(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewNotification(SqlSession session, NotificationModel model) throws Exception {
        int result = 0;
        NotificationDao dao = new NotificationDao(session);
        result = dao.insertNotification(model);
        return result;
    }

    public int updateNotification(NotificationModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            NotificationDao dao = new NotificationDao(session);
            ret = dao.updateNotification(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            NotificationDao dao = new NotificationDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            NotificationDao dao = new NotificationDao(session);
            return dao.listAvailableNotificationMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public NotificationModel getNotificationDetail(NotificationModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            NotificationDao dao = new NotificationDao(session);
            return dao.getNotification(model);
        } finally {
            session.close();
        }
    }

    public int deleteNotification(NotificationModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = deleteNotification(session, m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public int deleteNotification(SqlSession session, NotificationModel m) throws Exception {
        NotificationDao dao = new NotificationDao(session);
        int ret = dao.deleteNotification(m);
        return ret;

    }

    public List listNotification() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            NotificationDao dao = new NotificationDao(session);
            return dao.listNotification();
        } finally {
            session.close();
        }
    }
}