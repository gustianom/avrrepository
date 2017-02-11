package com.tenma.common.gui.display.profile;

import com.tenma.common.TA;
import com.tenma.common.gui.main.setting.account.GeneralAcountSetting;
import com.tenma.common.util.Constants;
import com.tenma.core.gui.main.SuperAdmin;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


/**
 * Created by ndwijaya on 10/6/15.
 */
public class MemberProfileDialog extends Window implements Button.ClickListener, MouseEvents.ClickListener, Window.CloseListener {
    private VerticalLayout root = new VerticalLayout();
    private Image profile;

    private VerticalLayout vlimg = new VerticalLayout();

    private int imageWidth;
    private int imageHeight;

    private HorizontalLayout hzButton = new HorizontalLayout();
    private Button account = new Button();
    private Button admin = new Button();
    private Button logout = new Button();
    private TenmaProfileImageListener parent;
    private MemberProfileCloseListener closeListener;

    public MemberProfileDialog(TenmaProfileImageListener parent, MemberProfileCloseListener closeListener, int width, int height) {

        this.imageWidth = width;
        this.imageHeight = height;
        this.parent = parent;
        this.closeListener = closeListener;
        setWidth("300px");
//        setHeight("165px");


        account.setCaption(TA.getCurrent().getClientInfo().getClientMemberModel().getMemberName());
        account.setStyleName(ValoTheme.BUTTON_LINK);
        admin.setCaption(TA.getCurrent().getParams().getLabel("super.admin"));
        admin.setIcon(new ThemeResource("layouts/images/tool.png"));
        logout.setCaption(TA.getCurrent().getParams().getLabel("default.logout"));
        logout.setIcon(new ThemeResource("layouts/images/logout.png"));

        account.addClickListener(this);
        admin.addClickListener(this);
        logout.addClickListener(this);

        profile = new Image(null, parent.getImage().getSource());
        profile.addClickListener(this);

        profile.setStyleName("circle-image");
        profile.setWidth(imageWidth, Unit.PIXELS);
        profile.setHeight(imageHeight, Unit.PIXELS);
        vlimg.addComponent(profile);

        HorizontalLayout hzImage = new HorizontalLayout();
        hzImage.setWidth("100%");
        hzImage.addComponent(vlimg);
        hzImage.addComponent(account);
        hzImage.setExpandRatio(vlimg, 1.0f);
        hzImage.setExpandRatio(account, 2.0f);
        hzImage.setComponentAlignment(vlimg, Alignment.MIDDLE_LEFT);
        hzImage.setComponentAlignment(account, Alignment.MIDDLE_LEFT);
        hzImage.setMargin(true);

        hzButton.setWidth("100%");
        hzButton.setMargin(true);
        hzButton.setSpacing(true);
//        hzButton.addComponent(account);

        admin.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        logout.setStyleName(ValoTheme.BUTTON_PRIMARY);

        hzButton.addComponent(admin);
        hzButton.addComponent(logout);
        hzButton.setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);
        hzButton.setComponentAlignment(admin, Alignment.MIDDLE_LEFT);
        hzButton.setStyleName("hzbuttonprofile");

        root.addComponent(hzImage);
        root.addComponent(hzButton);

        setContent(root);
        admin.setVisible(false);
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue()) {
                admin.setVisible(true);
            }

        this.addCloseListener(this);

    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(logout)) {
            String host = TA.getCurrent().getServerFullHost();
            System.out.println("host = " + host);
            getUI().getPage().setLocation(host + "/logout.jsp");
//            VaadinSession.getCurrent().getSession().invalidate();
//            VaadinService.reinitializeSession(VaadinService.getCurrentRequest());

        } else if (event.getButton().equals(admin)) {
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setWidth(100, Unit.PERCENTAGE);
            TA.getCurrent().updateWorkingArea(superAdmin);
            this.close();
        } else if (event.getButton().equals(account)) {
            GeneralAcountSetting settingModify = new GeneralAcountSetting();
            Window wsetting = new Window();
            wsetting.setContent(settingModify);
            wsetting.center();
            wsetting.setWidth("640px");
            wsetting.setHeight("320px");
            wsetting.setModal(true);
            UI.getCurrent().addWindow(wsetting);
//            TA.getCurrent().updateWorkingArea(settingModify);
//            this.close();
        }
    }

    @Override
    public void click(MouseEvents.ClickEvent event) {
        if (event.getSource().equals(profile)) {
            ProfileCropDialog profileDialog = new ProfileCropDialog(parent, 80, 80);
            profileDialog.center();
            this.close();
            UI.getCurrent().addWindow(profileDialog);
        }
    }

    @Override
    public void windowClose(CloseEvent e) {
        closeListener.closeWindow();
    }
}
