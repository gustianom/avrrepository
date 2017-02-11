package com.tenma.common.gui.display;

import com.tenma.auth.model.AuditModel;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.session.SessionCounterListenerHelper;
import com.tenma.common.gui.display.component.ConfirmationDialog;
import com.tenma.common.model.SessionCounterModel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.NotificationTools;
import com.tenma.common.util.Params;
import com.tenma.common.util.TenmaConverter;
import com.tenma.common.util.customlabel.CustomStructureLabel;
import com.tenma.common.util.error.CrudCode;
import com.tenma.common.util.web.ClientInfo;
import com.tenma.common.util.web.TenmaReportUtility;
import com.tenma.core.bean.reference.CommunityUserGroupMenuFunctionHelper;
import com.tenma.core.bean.reference.CommunityUserGroupMenuHelper;
import com.tenma.model.core.CommunityUserGroupMenuModel;
import com.tenma.model.core.UserGroupMenuFunctionModel;
import com.tenma.model.email.EmailModel;
import com.tenma.model.sms.SMSModel;
import com.tenma.util.SMS_Constants;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * User: anom
 * Date: 8/9/12
 * Time: 8:58 PM
 * TODO - move report, sms, email to tenma Application - tag as ticket #1023
 */

public abstract class TenmaPanel extends Panel implements TenmaInterface, Button.ClickListener, View {
    public final Logger log;
    public Params param;
    private Object selectedObject;
    private TenmaLog logger;
    private Label message;
    private String taskId; // populated when the current Panel is executed from task action
    private Integer menuId;
    private TenmaReportUtility tenmaReportUtility;
    private Button btnGoHome;
    private TenmaFooterPanel footer = new TenmaFooterPanel();
    private TenmaHeaderPanel header = new TenmaHeaderPanel();
    private String viewName = null;


    public TenmaPanel() {
        log = LoggerFactory.getLogger(TenmaPanel.class);
        initializeUI();
    }

    public static void setProcessId(Integer processId) {
        TA.getCurrent().getClientInfo().setSessionProcessId(processId);
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String argViewName) {
        this.viewName = argViewName;
    }

    public Button getBtnGoHome() {
        btnGoHome = new Button();
        btnGoHome.addClickListener(this);
        btnGoHome.setDescription(getLabel("back2Home"));
        btnGoHome.setIcon(new ThemeResource(Constants.B2H_CIRCLE_ICON));
        btnGoHome.setStyleName("circle");
        btnGoHome.setId("GO_HOME");

        return btnGoHome;
    }

    public void setBtnGoHome(Button btnGoHome) {
        this.btnGoHome = btnGoHome;
    }

    private final void initializeUI() {
        param = Params.getInstance(TA.getCurrent().getLocale());
        logger = TenmaLog.getInstance();
        tenmaReportUtility = new TenmaReportUtility();
        pupulateSecurityMenu();
    }

    public String getLabel(String argLabel) {
        if (argLabel.toUpperCase().startsWith("STATIC.")) {
            return argLabel.substring(7);
        }
        return param.getLabel(argLabel);
    }

    public String getLabel(String structureId, String argLabel) {
        return CustomStructureLabel.getInstance().getStructureLabel(structureId, argLabel);
    }

    public void registerFooterButton(Component aButton) {
        footer.registerButton(aButton);
    }

    public void registerHeaderComponent(Component aButton, boolean reset) {
        header.registerComponent(aButton, reset);
    }

    public void registerHeaderComponent(Component aButton) {
        header.registerComponent(aButton);
    }

    public void setTittle(Component title) {
        header.registerTitle(title);
    }


    public boolean isList() {
        return false;
    }

    public Component getLeftComponent() {
        return null;
    }

    public void showNewMessage(String msg) {
        message.setValue(msg);
    }

    public Object getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(Object selectedObject) {
        this.selectedObject = selectedObject;
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        String id = clickEvent.getButton().getId();
        if ("PRINT_DO".equals(id)) {
//                JavaScript.getCurrent().execute("var x = document.getElementsByTagName('IFRAME'); var y = (x[0].contentWindow || x[0].contentDocument); y.print();");
            JavaScript.getCurrent().execute("var x = document.getElementsByTagName('IFRAME'); var y = (x[1].contentWindow || x[1].contentDocument); y.onload(y.self.print());");
//                Window  window = (Window) clickEvent.getButton().getData();
//                window.close();
        } else if ("GO_HOME".equals(id)) {
            TA.getCurrent().goHome();
        }
    }

    public final String getTaskId() {
        return this.taskId;
    }

    public final void setTaskId(String taskID) {
        this.taskId = taskID;
    }

    public final void setAuditTrail(AuditModel m) {
        TA.getCurrent().setAuditTrail(m);
    }

    public void setFocus(Component componentName) {
        System.out.println("TenmaPanel.setFocus");
    }


    public boolean isRowSelected() {
        Object o = getSelectedObject();
        System.out.println("o = " + o);
        boolean result = false;
        String msg = param.getMessage("msg.selectrow");

        if (o == null) {
            commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.READ, msg, null);
        } else result = true;

        return result;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        System.out.println("TenmaPanel.enter " + getViewName());
    }

    public final void commonMessageHandler(Notification.Type severity, int CRUD_TYPE, String message, String caption) {
        if (!((caption != null) && (caption.trim().length() != 0))) {
            String msgTitle = TA.getCurrent().getParams().getMessage("system.info");
            if (Notification.Type.ERROR_MESSAGE.equals(severity))
                msgTitle = TA.getCurrent().getParams().getMessage("system.error");
            else if (Notification.Type.WARNING_MESSAGE.equals(severity))
                msgTitle = TA.getCurrent().getParams().getMessage("system.warning");
            else if (Notification.Type.HUMANIZED_MESSAGE.equals(severity))
                msgTitle = TA.getCurrent().getParams().getMessage("system.info");
            caption = msgTitle;
        }

        int logType = TenmaLog.LOG_FOR_ADD;

        if (CrudCode.CREATE == CRUD_TYPE)
            logType = TenmaLog.LOG_FOR_ADD;
        else if (CrudCode.READ == CRUD_TYPE)
            logType = TenmaLog.LOG_FOR_VIEW;
        else if (CrudCode.UPDATE == CRUD_TYPE)
            logType = TenmaLog.LOG_FOR_UPDATE;
        else if (CrudCode.DELETE == CRUD_TYPE)
            logType = TenmaLog.LOG_FOR_DELETE;

        String sDetail = message;
        System.out.println("TenmaPanel.commonMessageHandler ");
        System.out.println("TenmaPanel.commonMessageHandler caption " + caption);
        System.out.println("TenmaPanel.commonMessageHandler message " + message);
        boolean errorInCoverage = true;
        if (message != null) {
            caption = null;
            if (message.contains("already exist"))
                sDetail = TA.getCurrent().getParams().getLabel("item.alreadyExist");
            else if (message.contains(Constants.ADD_ALREADY_EXIST))
                sDetail = TA.getCurrent().getParams().getLabel("item.alreadyExist");
            else if (message.contains(Constants.OBJEC_NOT_EXIST))
                sDetail = TA.getCurrent().getParams().getLabel("object.doestnotexist");
            else if (message.contains(Constants.UPDATE_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.error.update");
            else if (message.contains(Constants.ADD_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.error.saving");
            else if (message.contains(Constants.DELETE_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.error.delete");
            else if (message.contains(Constants.DELETE_FAILED_HAVE_REFERENCE))
                sDetail = TA.getCurrent().getParams().getLabel("system.delete.hasreference");
            else if (message.contains(Constants.SECURED_LIMITATION_PACKAGE_CONSTRAINT))
                sDetail = TA.getCurrent().getParams().getLabel("system.limitation.constraint");
            else if (message.contains(Constants.DATE_IS_AFTER_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.date.isafter");
            else if (message.contains(Constants.DATE_IS_BEFORE_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.date.isbefore");
            else if (message.contains(Constants.DATE_END_IS_AFTER_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.endDdate.isafter");
            else if (message.contains(Constants.DATE_END_IS_BEFORE_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.endDdate.isbefore");
            else if (message.contains(Constants.DATE_START_IS_AFTER_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.startDate.isafter");
            else if (message.contains(Constants.DATE_START_IS_BEFORE_FAILED))
                sDetail = TA.getCurrent().getParams().getLabel("system.startDate.isbefore");
            else if (message.contains(Constants.DATE_END_MUST_HIGHER))
                sDetail = TA.getCurrent().getParams().getLabel("error.endDateMusthigher");
            else {
                errorInCoverage = false;
                sDetail = message;
            }
        } else {
            sDetail = "";
        }

        if (errorInCoverage)
            Notification.show(sDetail, severity);
        else
            Notification.show(caption, sDetail, severity);
    }

    @Override
    public void setContent(Component content) {
        if (content != null) setCompositionRoot(content);
    }

    public boolean isHeaderEnable() {
        return header.isEnable();
    }

    public TenmaHeaderPanel getHeader() {
        return header;
    }

    public boolean isFooterEnable() {
        return footer.isEnable();
    }

    public TenmaFooterPanel getFooter() {
        return footer;
    }

    public void setCompositionRoot(Component compositionRoot) {
        super.setContent(compositionRoot);
        setStyleName(ValoTheme.PANEL_BORDERLESS);
        setId("workarea");
        setSizeFull();
    }


    public boolean isFunctionEnable(String functionCode) {
        Boolean m = false;

        if (functionCode != null && functionCode.trim().length() != 0)
            m = TA.getCurrent().getClientInfo().getSecurityModel().getCustomAuthenticationCode().containsKey(functionCode);

        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;

        return m.booleanValue();
    }

    public boolean isCreateAccessEnable() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isCreateAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    public boolean isReadAccessEnable() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isReadAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    public boolean isUpdateAccessEnable() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isUpdateAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    public boolean isDeleteAccessEnable() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isDeleteAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    public boolean isPrintAccess() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isPrintAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    private void pupulateSecurityMenu() {
        if (TA.getCurrent().getClientInfo() != null)
            if (TA.getCurrent().getClientInfo().getSessionProcessId() != null) {
                CommunityUserGroupMenuHelper hlp = new CommunityUserGroupMenuHelper();
                List<CommunityUserGroupMenuModel> ls = hlp.queryAuthenticatedMenu(TA.getCurrent().getCommunityModel().getCommunityId(),
                        TA.getCurrent().getClientInfo().getClientMemberModel().getMemberId(),
                        TA.getCurrent().getClientInfo().getSessionProcessId());

                int bigAuth = 0;
                if (ls != null) {
                    for (CommunityUserGroupMenuModel m : ls)
                        if (bigAuth < m.getAuthorizationCode()) bigAuth = m.getAuthorizationCode();
                }
                if (bigAuth == 0)
                    bigAuth = 31; // no security implemented

                TA.getCurrent().getClientInfo().getSecurityModel().setAuthenticationCode(bigAuth);

                populateCustomFuctionalAuthList(TA.getCurrent().getClientInfo());
            }

    }


    private void populateCustomFuctionalAuthList(ClientInfo userInfo) {

        HashMap objFunc = new HashMap();

        HashMap param = new HashMap();
        if (userInfo != null) {
            if (userInfo.getSessionProcessId() != null) {

                CommunityUserGroupMenuFunctionHelper functionHelper = new CommunityUserGroupMenuFunctionHelper();
                List<UserGroupMenuFunctionModel> lsCustomFunction = functionHelper.getListCoreGroupMemberMenuFunction(TA.getCurrent().getCommunityModel().getCommunityId(), userInfo.getSessionProcessId(), userInfo.getClientMemberModel().getMemberId(), false);
                if (lsCustomFunction != null && lsCustomFunction.size() > 0) {
                    for (UserGroupMenuFunctionModel function : lsCustomFunction) {
                        objFunc.put(function.getFunctionCode(), true);
                    }
                }
            }
            userInfo.getSecurityModel().setCustomAuthenticationCode(objFunc);
        }
    }

    public TenmaLog getLogger() {
        return logger;
    }

    public final EmailModel generateEmail2Send(String body, String subject, String from, String to, String fileAttached, int emailType) {
        Locale locale = TA.getCurrent().getLocale();
        String communityId = TA.getCurrent().getCommunityModel() != null ? TA.getCurrent().getCommunityModel().getCommunityId() : "SYSTEM";
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";
        ;
        String clientIPAddress = TA.getCurrent().getRemoteAddress();

        return new NotificationTools(locale, communityId, userId, clientIPAddress).generateEmail2Send(body, subject, from, to, fileAttached, emailType);
    }

    public final SMS_Constants.SMS_RESPONSE_CODE doSendSMSNotification(SMSModel model, String communityId, com.tenma.util.SMS_Constants.SMS_SOURCE_TYPE sourceType) throws Exception {
        Locale locale = TA.getCurrent().getLocale();
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";
        ;
        String clientIPAddress = TA.getCurrent().getRemoteAddress();

        return new NotificationTools(locale, communityId, userId, clientIPAddress).doSendSMSNotification(model, sourceType);
    }

    public final void doSendEmail(List<EmailModel> listEmail) throws Exception {
        Locale locale = TA.getCurrent().getLocale();
        String communityId = TA.getCurrent().getCommunityModel() != null ? TA.getCurrent().getCommunityModel().getCommunityId() : "SYSTEM";
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";
        String clientIPAddress = TA.getCurrent().getRemoteAddress();

        new NotificationTools(locale, communityId, userId, clientIPAddress).doSendEmail(listEmail);
    }

    public final void doSendEmail(EmailModel emailModel) throws Exception {
        Locale locale = TA.getCurrent().getLocale();
        String communityId = TA.getCurrent().getCommunityModel() != null ? TA.getCurrent().getCommunityModel().getCommunityId() : "SYSTEM";
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";

        String clientIPAddress = TA.getCurrent().getRemoteAddress();

        new NotificationTools(locale, communityId, userId, clientIPAddress).doSendEmail(emailModel);
    }

    /**
     * @param cl          is a Model
     * @param classObject is Object of Class
     * @param emailTo     is a Email Recipient
     * @param emailType   as Email Type
     * @throws Exception
     */
    public final void doSendUserNotificationEmail(Class<?> cl, Object classObject, String emailTo, Constants.EMAIL_TYPE emailType) throws Exception {
        Locale locale = TA.getCurrent().getLocale();
        String communityId = TA.getCurrent().getCommunityModel() != null ? TA.getCurrent().getCommunityModel().getCommunityId() : "SYSTEM";
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";
        ;
        String clientIPAddress = TA.getCurrent().getRemoteAddress();

        new NotificationTools(locale, communityId, userId, clientIPAddress).doSendUserNotificationEmail(cl, classObject, emailTo, emailType);
    }

    /**
     * @param data      is a HashMap data which contains information about fieldName and Value that required on Email Content
     * @param emailTo   is a Email Recipient
     * @param emailType as Email Type
     * @throws Exception
     */
    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, Constants.EMAIL_TYPE emailType) throws Exception {
        return doSendUserNotificationEmail(data, emailTo, emailType, null, null);
    }

    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, Constants.EMAIL_TYPE emailType, String fileAttached, String fileLocationDir) throws Exception {
        Locale locale = TA.getCurrent().getLocale();
        String communityId = TA.getCurrent().getCommunityModel() != null ? TA.getCurrent().getCommunityModel().getCommunityId() : "SYSTEM";
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";
        String clientIPAddress = TA.getCurrent().getRemoteAddress();
        return new NotificationTools(locale, communityId, userId, clientIPAddress).doSendUserNotificationEmail(data, emailTo, emailType, fileAttached, fileLocationDir);
    }


    /**
     * Preparing Email data with HashMap
     *
     * @param cl
     * @param classObject
     * @return
     * @throws Exception
     */
    public final HashMap preparingEmailData(Class<?> cl, Object classObject) throws Exception {
        Locale locale = TA.getCurrent().getLocale();
        String communityId = TA.getCurrent().getCommunityModel() != null ? TA.getCurrent().getCommunityModel().getCommunityId() : "SYSTEM";
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";
        String clientIPAddress = TA.getCurrent().getRemoteAddress();
        return new NotificationTools(locale, communityId, userId, clientIPAddress).preparingEmailData(cl, classObject);
    }


    public void refreshTable() {
    }

    public void refreshTable(Object data) {
        System.out.println("TenmaPanel.refreshTable");
    }

    /**
     * call system notification
     *
     * @param functionId
     */
    public void doSystemNotify(String functionId) {

    }

    public void doConfirmationAction(String actionId) {

    }


    /**
     * Create confirmation Dialog, and call method doConfirmationAction, with specific actionId
     *
     * @param actionId
     * @param label
     * @param message
     * @param okCaption
     * @param noCaption
     */
    public Window viewConfirmationDialog(final String actionId, String label, String message, String okCaption, String noCaption) {
        Window confirmDialog = new ConfirmationDialog(
                label,
                message,
                okCaption,
                noCaption,
                new ConfirmationDialog.Callback() {
                    public void onDialogResult(boolean okChoose) {
                        if (okChoose) doConfirmationAction(actionId);
                    }
                }
        );
        confirmDialog.setId(actionId);
        confirmDialog.setWidth("350px");
        confirmDialog.setHeight("200px");
        confirmDialog.setClosable(true);
        confirmDialog.setModal(true);
        confirmDialog.setCaption(label);
        confirmDialog.setResizable(false);
        UI.getCurrent().getUI().addWindow(confirmDialog);
        return confirmDialog;
    }

    private Panel reportButton(Button[] btn, Constants.REPORT_MIME_TYPES mimeTypes, final String caption, final TenmaReportUtility.ReportInterface[] reportInterfaces) {
        Panel p = new Panel();

//        try {
//            Resource res = reportToExcel(caption, reportInterfaces);
//            FileDownloader fd = new FileDownloader(res);
//            fd.extend(btnExcel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        HorizontalLayout ly = new HorizontalLayout(btn);
//        ly.addComponent(btnExcel);


        ly.setStyleName("reportbarstyle");
        p.setContent(ly);
        return p;
    }


    public final void printReportDirectly2Client(String caption, TenmaReportUtility.ReportInterface[] reportInterfaces, Constants.REPORT_MIME_TYPES mimeTypes) {
        try {
            int size = reportInterfaces.length;
            for (int rp = 0; rp < size; rp++) {
                TenmaReportUtility.ReportInterface rpm = reportInterfaces[rp];
                HashMap param = rpm.getParameters();
                if (param == null) param = new HashMap();
                param.put(Constants.REPORT.TITLE.getValue(), rpm.getCaption());
                param.put(Constants.REPORT.PRINTED_BY.getValue(), TA.getCurrent().getClientInfo().getClientMemberModel().getMemberName());
            }
            byte[] byteFile = tenmaReportUtility.generateJasperOutputStream(reportInterfaces, mimeTypes);
            String sname = caption + "." + getFileExtention(mimeTypes);

            String path = param.getProperty("temporaryPrintFolder");
            String fileName = new StringBuilder()
                    .append(TenmaConverter.generateRandomPassword(false, 8))
                    .append(".pdf").toString();

            String encodedUrl = URLEncoder.encode(fileName, "UTF-8");
            String fullPathPrintedFile = new StringBuilder()
                    .append("../qzprint/applet.html?p=")
                    .append(encodedUrl)
                    .toString();

            FileOutputStream fileOuputStream =
                    new FileOutputStream(new StringBuilder().append(path).append(File.separator).append(fileName).toString());
            fileOuputStream.write(byteFile);
            fileOuputStream.close();


            SessionCounterModel model = new SessionCounterModel();
            model.setRecordStatus(0);
            model.setTemporaryFileId(fileName);
            model.setSessionPath(path);
            model.setSessionType("1");
            model.setSessionId(UI.getCurrent().getSession().getSession().getId());
            TA.getCurrent().setAuditTrail(model);
            SessionCounterListenerHelper hlp = new SessionCounterListenerHelper();
            hlp.insertNewSessionCounter(model);


            BrowserFrame browserFrame = new BrowserFrame();
            browserFrame.setImmediate(true);
            browserFrame.setSource(new ExternalResource(fullPathPrintedFile));

            browserFrame.setWidth(1, Unit.PIXELS);
            browserFrame.setHeight(1, Unit.PIXELS);
//          TODO-TMP
//            getTenmaApplication().getTopPanel().getLayoutTemporaryDirectPrint().removeAllComponents();
//            getTenmaApplication().getTopPanel().getLayoutTemporaryDirectPrint().addComponent(browserFrame);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void printReportDirectly2Client(String caption, String fullPathReportName, List data, HashMap parameter) {
        try {
            InputStream rep = getClass().getClassLoader().getResourceAsStream(new StringBuilder().append("report/").append(fullPathReportName).toString());
            if (rep == null) {
                Notification.show("No report ! on " + caption);
            } else {

                BufferedInputStream bufferedInputStream = new BufferedInputStream(rep);
                JasperReport report = (JasperReport) JRLoader.loadObject(bufferedInputStream);


                JasperPrint jasperPrint = null;
                if (data != null) {
                    if (data.size() == 0)
                        jasperPrint = JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
                    else {
                        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(data);
                        jasperPrint = JasperFillManager.fillReport(report, parameter, jrbcds);
                    }
                } else {
                    jasperPrint = JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
                }

                String path = param.getProperty("temporaryPrintFolder");
                String fileName = new StringBuilder()
                        .append(TenmaConverter.generateRandomPassword(false, 8))
                        .append(".pdf").toString();

                String encodedUrl = URLEncoder.encode(fileName, "UTF-8");
                String fullPathPrintedFile = new StringBuilder()
                        .append("../qzprint/applet.html?p=")
                        .append(encodedUrl)
                        .toString();


                SessionCounterModel model = new SessionCounterModel();
                model.setRecordStatus(0);
                model.setTemporaryFileId(fileName);
                model.setSessionPath(path);
                model.setSessionType("1");
                model.setSessionId(UI.getCurrent().getSession().getSession().getId());
                TA.getCurrent().setAuditTrail(model);
                SessionCounterListenerHelper hlp = new SessionCounterListenerHelper();
                hlp.insertNewSessionCounter(model);
                JasperExportManager.exportReportToPdfFile(jasperPrint, new StringBuilder().append(path).append(File.separator).append(fileName).toString());

                BrowserFrame browserFrame = new BrowserFrame();
                browserFrame.setImmediate(true);
                browserFrame.setSource(new ExternalResource(fullPathPrintedFile));

                browserFrame.setWidth(1, Unit.PIXELS);
                browserFrame.setHeight(1, Unit.PIXELS);
//              TODO-TMP
//                getTenmaApplication().getTopPanel().getLayoutTemporaryDirectPrint().removeAllComponents();
//                getTenmaApplication().getTopPanel().getLayoutTemporaryDirectPrint().addComponent(browserFrame);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Print report directly to printer
     *
     * @param caption
     * @param fullPathReportName
     * @param data
     * @param parameter
     * @param paperSize          default is ISO_A4
     * @param viewPrintDialog
     * @param viewPageDialog
     */
    public final void printReportDirectly2PrintServer(String caption, String fullPathReportName, List data, HashMap parameter, MediaSizeName paperSize, boolean viewPrintDialog, boolean viewPageDialog) {
        if (paperSize == null) paperSize = MediaSizeName.ISO_A4;
        try {
            List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
            InputStream rep = getClass().getClassLoader().getResourceAsStream(new StringBuilder().append("report/").append(fullPathReportName).toString());
            if (rep == null) {
                Notification.show("No report ! on " + caption);
            } else {

                BufferedInputStream bufferedInputStream = new BufferedInputStream(rep);
                JasperReport report = (JasperReport) JRLoader.loadObject(bufferedInputStream);


                JasperPrint jasperPrint = null;
                if (data != null) {
                    if (data.size() == 0)
                        jasperPrint = JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
                    else {
                        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(data);
                        jasperPrint = JasperFillManager.fillReport(report, parameter, jrbcds);
                    }
                } else {
                    jasperPrint = JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
                }
                jasperPrintList.add(jasperPrint);
            }
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(paperSize);


            SimpleExporterInput input = SimpleExporterInput.getInstance(jasperPrintList);

            SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
            configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
            configuration.setDisplayPageDialog(viewPageDialog);
            configuration.setDisplayPrintDialog(viewPrintDialog);

            JRPrintServiceExporter exporter = new JRPrintServiceExporter();

            System.out.println("TenmaPanel.printReportDirectly2PrintServer, MARKED VIEW HERE");
            //todo exporter.setConfiguration(configuration);
//            exporter.setExporterInput(input);
//            exporter.exportReport();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public final void openReport(String caption, String fullPathReportName, HashMap parameter, Constants.REPORT_MIME_TYPES mimeTypes) {
        try {
            openReport(caption, fullPathReportName, null, parameter, false, mimeTypes, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void openReport(String caption, String fullPathReportName, HashMap parameter, boolean isShare, Constants.REPORT_MIME_TYPES mimeTypes) {
        try {
            openReport(caption, fullPathReportName, null, parameter, isShare, mimeTypes, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void openReport(String caption, String fullPathReportName, List data, HashMap parameter, Constants.REPORT_MIME_TYPES mimeTypes) {
        openReport(caption, fullPathReportName, data, parameter, false, mimeTypes, false);
    }

    public final void openReport(String caption, TenmaReportUtility.ReportInterface[] reportInterfaces, boolean isShare, Constants.REPORT_MIME_TYPES mimeTypes) {
        generateOpenReportView(caption, reportInterfaces, isShare, mimeTypes, false);
    }

    public final void openReport(final String caption, final String fullPathReportName, final List data, final HashMap parameter, boolean isShare, Constants.REPORT_MIME_TYPES mimeTypes, boolean isDirectPrint) {
        TenmaReportUtility.ReportInterface reportInterface = new TenmaReportUtility.ReportInterface(caption, fullPathReportName, parameter, data);
        TenmaReportUtility.ReportInterface[] prm = new TenmaReportUtility.ReportInterface[]{reportInterface};
        generateOpenReportView(caption, prm, isShare, mimeTypes, isDirectPrint);
    }

    private void generateOpenReportView(String caption, TenmaReportUtility.ReportInterface[] reportInterfaces, boolean isShare, Constants.REPORT_MIME_TYPES mimeTypes, boolean isDirectPrint) {
        try {
            int size = reportInterfaces.length;
            for (int rp = 0; rp < size; rp++) {
                TenmaReportUtility.ReportInterface rpm = reportInterfaces[rp];
                HashMap param = rpm.getParameters();
                if (param == null) param = new HashMap();
                param.put(Constants.REPORT.TITLE.getValue(), rpm.getCaption());
                param.put(Constants.REPORT.PRINTED_BY.getValue(), TA.getCurrent().getClientInfo().getClientMemberModel().getMemberName());
            }

            BrowserFrame browserFrame = new BrowserFrame();
            browserFrame.setId("TENMAFRAMEWINDOW");
            browserFrame.setPrimaryStyleName("TENMAFRAMEWINDOW");

            browserFrame.setImmediate(true);
            browserFrame.setWidth("100%");
            browserFrame.setHeight("100%");

            StreamResource resource = packagingReport(caption, reportInterfaces, mimeTypes, isDirectPrint);
            browserFrame.setSource(resource);

            VerticalLayout ly = new VerticalLayout();
            ly.setSizeFull();

            Button[] btnPan = preparingButtonReport(isShare);

            Panel rptBtn = reportButton(btnPan, mimeTypes, caption, reportInterfaces);

            ly.addComponent(rptBtn);
            rptBtn.setHeight("30px");
            ly.addComponent(browserFrame);

//            ly.setComponentAlignment(rptBtn, Alignment.TOP_LEFT);
//            ly.setComponentAlignment(browserFrame, Alignment.MIDDLE_LEFT);
//            ly.setExpandRatio( rptBtn, 10);
            ly.setExpandRatio(browserFrame, 1.0f);
            browserFrame.setSizeFull();
            Window window = new Window();
            window.setCaption(caption);
            window.setContent(ly);
            window.setWidth((UI.getCurrent().getPage().getBrowserWindowWidth() - 320) + "px");
            window.setHeight((UI.getCurrent().getPage().getBrowserWindowHeight() - 160) + "px");
            window.setModal(true);
            window.setClosable(true);
            window.setStyleName("windowreport");
            UI.getCurrent().addWindow(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Button[] preparingButtonReport(boolean isShare) {
        Button btnExcel = new Button(param.getLabel("default.xlsexport"));
        btnExcel.setIcon(new ThemeResource("layouts/images/16/excel.png"));
        btnExcel.setId("reportExport2Excel");
//        btnExcel.setPrimaryStyleName("btnxls");

        Button printButton = new Button(param.getLabel(Constants.LABEL_PRINT), this);
        printButton.setId("PRINT_DO");
        printButton.setIcon(new ThemeResource(Constants.PRINT_ICON));

        Button share = new Button(param.getLabel("default.share"));
        share.setIcon(new ThemeResource("layouts/images/16/share.png"));

        if (isShare)
            return new Button[]{printButton, share};
        else
            return new Button[]{};
//            return new Button[]{printButton};
    }

    public final StreamResource packagingReport(final String fullPath, final List data, final HashMap parameter, Constants.REPORT_MIME_TYPES mimeTypes, boolean isDirectPrint) {
        StreamResource.StreamSource srs = null;
        TenmaReportUtility.ReportInterface reportInterface = new TenmaReportUtility.ReportInterface(null, fullPath, parameter, data);
        TenmaReportUtility.ReportInterface[] rpm = new TenmaReportUtility.ReportInterface[]{reportInterface};
        srs = tenmaReportUtility.processingReport(rpm, mimeTypes, isDirectPrint);

        //TODO CAPTION
        String sname = doGetFileName("caption", getFileExtention(mimeTypes));


        StreamResource resource = new StreamResource(srs, sname);

        resource.setMIMEType(mimeTypes.getValue());

        resource.setCacheTime(0);
        resource.getStream().setParameter("Content-Disposition", "inline;filename=" + sname);

        return resource;

    }

    private final StreamResource packagingReport(String caption, TenmaReportUtility.ReportInterface[] reportInterfaces, Constants.REPORT_MIME_TYPES mimeTypes, boolean isDirectPrint) {
        StreamResource.StreamSource srs = null;
        srs = tenmaReportUtility.processingReport(reportInterfaces, mimeTypes, isDirectPrint);


//        String sname = doGetFileName(caption, "pdf");
        String sname = caption + "." + getFileExtention(mimeTypes);

        StreamResource resource = new StreamResource(srs, sname);

        resource.setMIMEType(mimeTypes.getValue());

        resource.setCacheTime(0);
        resource.getStream().setParameter("Content-Disposition", "inline;filename=" + sname);

        return resource;

    }

    private String getFileExtention(Constants.REPORT_MIME_TYPES mimeTypes) {
        String ext = Constants.REPORT_MIME_TYPES_FILE_EXTENTION.PDF.getValue();

        if (Constants.REPORT_MIME_TYPES.PDF.equals(mimeTypes))
            ext = Constants.REPORT_MIME_TYPES_FILE_EXTENTION.TEXT_HTML.getValue();
        return ext;
    }

    private String doGetFileName(String fullPathReportName, String extentionFile) {
        int idx = fullPathReportName.lastIndexOf("/");
        if (idx == -1) idx = fullPathReportName.lastIndexOf(File.separator);

        String sname = fullPathReportName;
        if (idx != -1)
            sname = fullPathReportName.substring(idx + 1);

        idx = sname.indexOf(".");
        sname = new StringBuilder()
                .append(sname.substring(0, idx))
                .append(".").append(extentionFile).toString();

        return sname;

    }

    private StreamResource reportToExcel(final String caption, TenmaReportUtility.ReportInterface[] reportInterfaces) throws Exception {
        StreamResource streamResource = null;
        //TODO
//        final String sname = doGetFileName(caption, "xls");
        final String sname = caption + ".xls";

        SessionCounterModel counterModel = new SessionCounterModel();
        setAuditTrail(counterModel);
        counterModel.setSessionId(UI.getCurrent().getSession().getSession().getId());
        counterModel.setSessionType(Constants.SESSION_TYPE.FILE_UPLOAD.getValue());
        counterModel.setSessionPath(param.getProperty("reportFileServerDirectory"));
        counterModel.setTemporaryFileId(new StringBuilder().append(System.currentTimeMillis()).append(sname).toString());
        List<JasperPrint> listPrint = new ArrayList<JasperPrint>();
        for (TenmaReportUtility.ReportInterface rpm : reportInterfaces) {
            InputStream rep = getClass().getClassLoader().getResourceAsStream(new StringBuilder().append("report/").append(rpm.getFullPathReportName()).toString());
            if (rep == null) {
                Notification.show("No report!");
            } else {

                BufferedInputStream bufferedInputStream = new BufferedInputStream(rep);
                JasperReport report = (JasperReport) JRLoader.loadObject(bufferedInputStream);

                JasperPrint jasperPrint = null;
                List data = rpm.getListData();
                HashMap parameter = rpm.getParameters();
                if (data != null) {
                    JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(data);
                    jasperPrint = JasperFillManager.fillReport(report, parameter, jrbcds);
                } else {
                    jasperPrint = JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
                }
                listPrint.add(jasperPrint);
            }

        }
        final String temporaryFileName = counterModel.getTemporaryFileId();

        final JRXlsExporter exporterXls = new JRXlsExporter();
        exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, false);
        exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);
        exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
        exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
        exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
        exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
        //exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, temporaryFileName);
        exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);

        final byte[] output = exportReportToBytes(listPrint, exporterXls);

        streamResource = new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                return new ByteArrayInputStream(output);
            }
        }, counterModel.getTemporaryFileId());


        SessionCounterListenerHelper listenerHelper = new SessionCounterListenerHelper();
        listenerHelper.insertNewSessionCounter(counterModel);
        streamResource.setMIMEType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return streamResource;
    }

    /**
     * Run a Jasper report to CSV format and put the results in a byte array
     *
     * @param jasperPrint The Print object to render as CSV
     * @param exporter    The exporter to use to export the report
     * @return A CSV formatted report
     * @throws JRException If there is a problem running the report
     */
    private byte[] exportReportToBytes(List<JasperPrint> jasperPrint, JRExporter exporter) throws JRException {
        byte[] output;
        ByteArrayOutputStream fao = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fao);

        exporter.exportReport();

        output = fao.toByteArray();
        return output;
    }


    public final void processNotifyByFB(String announcementDescription) {
        Locale locale = TA.getCurrent().getLocale();
        String communityId = TA.getCurrent().getCommunityModel() != null ? TA.getCurrent().getCommunityModel().getCommunityId() : "SYSTEM";
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";
        String clientIPAddress = TA.getCurrent().getRemoteAddress();
        new NotificationTools(locale, communityId, userId, clientIPAddress).processNotifyByFB(announcementDescription);
    }

    public final void processNotifyByTwitter(String announcementDescription) {
        Locale locale = TA.getCurrent().getLocale();
        String communityId = TA.getCurrent().getCommunityModel() != null ? TA.getCurrent().getCommunityModel().getCommunityId() : "SYSTEM";
        String userId = TA.getCurrent().getClientInfo() != null ? TA.getCurrent().getClientInfo().getClientUserModel() != null ? TA.getCurrent().getClientInfo().getClientUserModel().getUserId() : "SYSTEM" : "SYSTEM";
        ;
        String clientIPAddress = TA.getCurrent().getRemoteAddress();

        new NotificationTools(locale, communityId, userId, clientIPAddress).processNotifyByTwitter(announcementDescription);
    }

//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        System.out.println("event.getOldView() = " + event.getOldView());
//        System.out.println("event.getViewName() = " + event.getViewName());
//    }


}