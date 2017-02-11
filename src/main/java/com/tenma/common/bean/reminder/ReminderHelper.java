package com.tenma.common.bean.reminder;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.reminder.ReminderDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.ReminderModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Wed Jan 15 16:35:09 WIT 2014
 */
public class ReminderHelper extends TenmaHelper {
    public int insertNewReminder(ReminderModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewReminder(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewReminder(SqlSession session, ReminderModel model) throws Exception {
        int result = 0;
        ReminderDao dao = new ReminderDao(session);
        result = dao.insertReminder(model);
        return result;
    }

    public int updateReminder(ReminderModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ReminderDao dao = new ReminderDao(session);
            ret = dao.updateReminder(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ReminderDao dao = new ReminderDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ReminderDao dao = new ReminderDao(session);
            return dao.listAvailableReminderMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public ReminderModel getReminderDetail(ReminderModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ReminderDao dao = new ReminderDao(session);
            return dao.getReminder(model);
        } finally {
            session.close();
        }
    }

    public int deleteReminder(ReminderModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = deleteReminder(session, m);
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

    public int deleteReminder(SqlSession session, ReminderModel m) throws Exception {
        int ret = 0;
        ReminderDao dao = new ReminderDao(session);
        ret = dao.deleteReminder(m);
        return ret;

    }

    public List listReminder() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ReminderDao dao = new ReminderDao(session);
            return dao.listReminder();
        } finally {
            session.close();
        }
    }
}