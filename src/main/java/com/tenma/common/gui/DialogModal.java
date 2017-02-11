package com.tenma.common.gui;


import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.CrudCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: sigit
 * Date: 9/17/12
 * Time: 12:37 PM
 */
@Deprecated
public abstract class DialogModal extends TenmaWindow implements Button.ClickListener {
    public final static int BORDER_TOP = 0;
    public final static int BORDER_LEFT = 1;
    public final static int BORDER_RIGHT = 2;
    public final static int BORDER_BOTTOM = 3;
    public final static int BORDER_ALL = 4;
    private GridLayout layout = new GridLayout(3, 3);

    public DialogModal() {
        prepareUI();

    }


    public DialogModal(String caption) {
        this.setCaption(caption);
        prepareUI();

    }

    private void prepareUI() {
        this.center();
        this.setModal(true);
        this.setResizable(false);
        this.setClosable(false);
    }

//    public void setContent(AbstractComponent co) {
//        addComponent(co);
//    }

//    public void addComponent(AbstractComponent co) {
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println("DialogModal.addComponent");
//        layout.addComponent(new Label(), 0, 0);
//        layout.addComponent(new Label(), 2, 2);
//        layout.addComponent(co);
//        setBorder(DialogModal.BORDER_ALL, "10px");
//        super.setContent(layout);
//    }

//    public void setBorder(int borderLocation, String value) {
//        if (borderLocation == DialogModal.BORDER_BOTTOM)
//            settingHeight(2, 2, value);
//        else if (borderLocation == DialogModal.BORDER_LEFT)
//            settingWidth(0, 0, value);
//        else if (borderLocation == DialogModal.BORDER_RIGHT)
//            settingWidth(2, 2, value);
//        else if (borderLocation == DialogModal.BORDER_TOP)
//            settingHeight(0, 0, value);
//        else if (borderLocation == DialogModal.BORDER_ALL) {
//            settingHeight(0, 0, value);
//            settingHeight(2, 2, value);
//            settingWidth(2, 2, value);
//            settingWidth(0, 0, value);
//        }
//
//    }

    private void settingWidth(int x, int y, String value) {
        layout.getComponent(x, y).setWidth(value);
    }

    private void settingHeight(int x, int y, String value) {
        layout.getComponent(x, y).setHeight(value);
    }


    public void buttonClick(Button.ClickEvent clickEvent) {
    }


    public final void commonMessageHandler(Notification.Type severity, int errorCode, String message, String detail) {
        String msgTitle = "";
        if (Notification.Type.ERROR_MESSAGE.equals(severity))
            msgTitle = param.getMessage("system.error");
        else if (Notification.Type.WARNING_MESSAGE.equals(severity))
            msgTitle = param.getMessage("system.warning");
        else if (Notification.Type.HUMANIZED_MESSAGE.equals(severity))
            msgTitle = param.getMessage("system.info");

        int logType = TenmaLog.LOG_FOR_ADD;

        if (CrudCode.CREATE == errorCode)
            logType = TenmaLog.LOG_FOR_ADD;
        else if (CrudCode.READ == errorCode)
            logType = TenmaLog.LOG_FOR_VIEW;
        else if (CrudCode.UPDATE == errorCode)
            logType = TenmaLog.LOG_FOR_UPDATE;
        else if (CrudCode.DELETE == errorCode)
            logType = TenmaLog.LOG_FOR_DELETE;

        if (detail == null) detail = "";
        String sDetail = detail;
        System.out.println("CommonWebBean.commonMessageHandler " + detail);
        boolean errorInCoverage = true;
        if (detail.contains(Constants.ADD_ALREADY_EXIST))
            sDetail = param.getMessage("item.alreadyExist");
        else if (detail.contains(Constants.UPDATE_FAILED))
            sDetail = param.getMessage("system.error.update");
        else if (detail.contains(Constants.ADD_FAILED))
            sDetail = param.getMessage("system.error.saving");
        else if (detail.contains(Constants.DELETE_FAILED))
            sDetail = param.getMessage("system.error.delete");
        else if (detail.contains(Constants.DELETE_FAILED_HAVE_REFERENCE))
            sDetail = param.getMessage("system.delete.hasreference");
        else if (detail.contains(Constants.SECURED_LIMITATION_PACKAGE_CONSTRAINT))
            sDetail = param.getMessage("system.limitation.constraint");
        else if (detail.contains(Constants.DATE_IS_AFTER_FAILED))
            sDetail = param.getMessage("system.date.isafter");
        else if (detail.contains(Constants.DATE_IS_BEFORE_FAILED))
            sDetail = param.getMessage("system.date.isbefore");
        else if (detail.contains(Constants.DATE_END_IS_AFTER_FAILED))
            sDetail = param.getMessage("system.endDdate.isafter");
        else if (detail.contains(Constants.DATE_END_IS_BEFORE_FAILED))
            sDetail = param.getMessage("system.endDdate.isbefore");
        else if (detail.contains(Constants.DATE_START_IS_AFTER_FAILED))
            sDetail = param.getMessage("system.startDate.isafter");
        else if (detail.contains(Constants.DATE_START_IS_BEFORE_FAILED))
            sDetail = param.getMessage("system.startDate.isbefore");
        else if (detail.contains(Constants.DATE_END_MUST_HIGHER))
            sDetail = param.getMessage("error.endDateMusthigher");
        else
            errorInCoverage = false;

        System.out.println("CommonWebBean.commonMessageHandler " + sDetail);
        if (errorInCoverage)
            Notification.show(sDetail, severity);
        else
            Notification.show(sDetail, message, severity);
    }

    public void doRefresh(Object newData) {

    }

    public Object getSelectedObject() {
        return null;
    }

    public void setSelectedObject(Object obj) {
    }

    public String createLabel(String label, String prefix, String suffix) {
        StringBuilder buf = new StringBuilder();
        if ((prefix != null) && (prefix.trim().length() != 0))
            buf.append(prefix).append(" ");

        buf.append(param.getLabel(label));

        if ((suffix != null) && (suffix.trim().length() != 0))
            buf.append(" ").append(suffix);

        return buf.toString();
    }

    public String createLabel(String label) {
        StringBuilder buf = new StringBuilder();
//        if ((prefix != null) && (prefix.trim().length() != 0))
//            buf.append(prefix).append(" ");

        buf.append(param.getLabel(label));

//        if ((suffix != null) && (suffix.trim().length() != 0))
//            buf.append(" ").append(suffix);

        return buf.toString();
    }


}
