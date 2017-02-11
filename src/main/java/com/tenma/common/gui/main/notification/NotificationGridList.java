package com.tenma.common.gui.main.notification;

import com.tenma.common.TA;
import com.tenma.common.bean.notification.NotificationHelper;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.community.CommunityListPaging;
import com.tenma.common.util.Constants;
import com.tenma.model.common.NotificationModel;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Fri Jan 17 15:30:16 WIB 2014
 */

public class NotificationGridList extends TenmaPanel {
    private final CommunityListPaging paging;
    private final Label labelTitle;
    private VerticalLayout layout;

    public NotificationGridList() {

        layout = new VerticalLayout();
        layout.setWidth("90%");
        //layout.setSpacing(true);
        paging = new CommunityListPaging(100) {
            @Override
            protected TextField getTextSearch() {
                return null;
            }

            @Override
            protected void doPrint() {

            }

            public void refreshTable() {
                populateNotificationList();
            }
        };

        paging.addDataList(layout);

        labelTitle = new Label(param.getLabel("default.notification"));
        Panel p = new Panel();
        p.setWidth("100%");
        p.setContent(paging);

        VerticalLayout vl = new VerticalLayout(labelTitle, p);
        vl.setSpacing(true);
        vl.setWidth("100%");
        setContent(vl);
        refreshTable();

    }

    public void setTitle(String title) {
        labelTitle.setValue(title);
    }

    @Override
    public void refreshTable() {
        paging.refreshTable();
    }

    private void populateNotificationList() {
        layout.removeAllComponents();

        HashMap param = new HashMap();
        param.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        param.put("notifyTo", TA.getCurrent().getClientInfo().getClientUserModel().getUserId());


        NotificationHelper helper = new NotificationHelper();
        List<NotificationModel> ls = paging.loadPagingData(param, "createdDate", Constants.SORTING_TYPE.DESCENDING.getValue(), "Notification List View", helper);
        layout.removeAllComponents();
        if ((ls != null) && (ls.size() != 0)) {
            for (NotificationModel m : ls) {
                NotificationRowDetailViewer viewer = new NotificationRowDetailViewer(m);
                layout.addComponent(viewer);
            }
        } else {
            layout.addComponent(new Label(TA.getCurrent().getParams().getMessage("record.notfound"), ContentMode.HTML));
        }

    }
}
