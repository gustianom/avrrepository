package com.tenma.common.gui.display;

import com.tenma.auth.model.AuditModel;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.util.Params;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * User: anom
 * Date: 6/27/13
 * Time: 4:43 PM
 */
public abstract class TenmaWindow extends Window {
    public Params param;
    public VerticalLayout layout;
    private TenmaLog logger;
    private Object selectedObject;


    public TenmaWindow() {
        TenmaMonitor.getInstance().pageLoaded();
        this.center();
        this.setModal(true);
        this.setResizable(false);
        param = TA.getCurrent().getParams();
        logger = TenmaLog.getInstance();
        layout = new VerticalLayout();
        layout.setMargin(true);
        setClosable(true);
    }


    public TenmaLog getLogger() {
        return logger;
    }

    public void setLogger(TenmaLog logger) {
        this.logger = logger;
    }

    public String getLabel(String argLabel) {
        return param.getLabel(argLabel);
    }

    @Deprecated
    public final void setAuditTrail(AuditModel m) {
        TA.getCurrent().setAuditTrail(m);
    }

    @Override
    public void setContent(Component content) {
        if (content != null) {

            layout.addComponent(content);
            layout.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
//            layout.setSizeFull();
            super.setContent(layout);
        }
    }


    public void doRefresh(Object newData) {

    }

    public Object getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(Object obj) {
        selectedObject = obj;
    }


}
