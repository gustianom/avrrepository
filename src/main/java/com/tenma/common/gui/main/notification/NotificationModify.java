package com.tenma.common.gui.main.notification;

import com.tenma.common.TA;
import com.tenma.common.bean.notification.NotificationHelper;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.gui.fieldfactory.NotificationFormField;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.CrudCode;
import com.tenma.model.common.NotificationModel;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Notification;

import java.util.Arrays;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Fri Jan 17 15:30:16 WIB 2014
 */
public class NotificationModify extends TenmaMasterFormModify {
    private NotificationModel modifyModel;
    private NotificationHelper helper = new NotificationHelper();

    public NotificationModify(TenmaPanel parentContainer, int modifyMode) throws Exception {
        super(parentContainer, modifyMode);
        setTitle("Notification.title");

        if (modifyMode == ADD_MODE) {
            modifyModel = new NotificationModel();
            modifyModel.setNotifyId(0);
            modifyModel.setCommunityId("");
            modifyModel.setNotifyFrom("");
            modifyModel.setNotifyTo("");
            modifyModel.setNotifySubject("");
            modifyModel.setNotifyType(0);
            modifyModel.setViewStatus(0);
        } else {
            modifyModel = (NotificationModel) ((TenmaPanel) parentContainer).getSelectedObject();

            NotificationModel tmp = new NotificationModel();
            tmp.setNotifyId(modifyModel.getNotifyId());
            modifyModel = helper.getNotificationDetail(tmp);
            if (modifyModel == null)
                throw new Exception(String.valueOf(CrudCode.READ));
        }
        BeanItem<NotificationModel> bitem = new BeanItem<NotificationModel>(modifyModel);
        List visibleField = Arrays.asList(new String[]{"notifyId", Constants.COMMUNITY_ID, "notifyFrom", "notifyTo", "notifySubject", "notifyType"});

        NotificationFormField formField = new NotificationFormField(param);
        doPublishUI(bitem, visibleField, formField);
    }

    public void doCancel() {
        doBack2List();
    }

    public void doSave() {
        setAuditTrail(modifyModel);
        int res = -1;
        int crudCode = CrudCode.CREATE;
        if (ADD_MODE == getModifyMode())
            crudCode = CrudCode.CREATE;
        else
            crudCode = CrudCode.UPDATE;
        try {
            if (ADD_MODE == getModifyMode())
                res = helper.insertNewNotification(modifyModel);
            else
                res = helper.updateNotification(modifyModel);

            String msg = new StringBuffer().append(TA.getCurrent().getParams().getMessage("system.info.success"))
                    .append(" ").append(TA.getCurrent().getParams().getMessage("system.info.saving"))
                    .toString();
            commonMessageHandler(Notification.Type.TRAY_NOTIFICATION, crudCode, msg, TA.getCurrent().getParams().getMessage("system.info.success"));
            doBack2List();
        } catch (Exception e) {
            commonMessageHandler(Notification.Type.ERROR_MESSAGE, crudCode, e.getMessage(), TA.getCurrent().getParams().getMessage("system.error.saving"));
            e.printStackTrace();
        }

    }

    private void doBack2List() {
        NotificationGridList list = (NotificationGridList) getParentContainer();
        TA.getCurrent().updateWorkingArea(list);
        list.refreshTable();
    }
}
