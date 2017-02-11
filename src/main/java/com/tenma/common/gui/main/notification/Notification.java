package com.tenma.common.gui.main.notification;

import com.tenma.common.bean.notification.NotificationHelper;
import com.tenma.common.util.Constants;
import com.tenma.model.common.NotificationModel;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by gustianom on 1/14/14.
 */
public class Notification {
    private Notification() {
    }

    public static void addNotification(String communityId, String notifyFrom, String notifyTo, String notifySubject, Constants.NOTIFICATION_TYPE notifyType, String notifySourceId) throws Exception {
        NotificationModel model = new NotificationModel();
        model.setCommunityId(communityId);
        model.setNotifyFrom(notifyFrom);
        model.setNotifyTo(notifyTo);
        model.setNotifySourceId(notifySourceId);
        model.setNotifySubject(notifySubject);
        model.setNotifyType(notifyType.getValue());
        model.setCreatedBy(notifyFrom);
        model.setCreatedFrom(Constants.SYSTEM);
        model.setUpdatedBy(notifyFrom);
        model.setUpdatedFrom(Constants.SYSTEM);
        model.setViewStatus(0);
        addNotification(model);
    }

    /**
     * @param model
     * @throws Exception
     */
    public static void addNotification(NotificationModel model) throws Exception {
        NotificationHelper helper = new NotificationHelper();
        helper.insertNewNotification(model);
    }

    public static void addNotification(List<NotificationModel> listOfNotification) throws Exception {
        if (listOfNotification != null)
            if (listOfNotification.size() != 0) {
                NotificationHelper helper = new NotificationHelper();
                SqlSession session = helper.sqlSessionFactory.openSession();

                try {
                    for (NotificationModel model : listOfNotification)
                        helper.insertNewNotification(session, model);
                    session.commit();
                } catch (Exception e) {
                    session.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }

    }


}
