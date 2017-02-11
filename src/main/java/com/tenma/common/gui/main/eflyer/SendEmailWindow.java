package com.tenma.common.gui.main.eflyer;


import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.model.common.EflyerModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/26/2015
 * Time    : 2:28 PM
 * Project : udw
 * Package : share.tenma.common.gui.main.eflyer
 */
class SendEmailWindow extends TenmaWindow implements Button.ClickListener {
    private TextField tfEmail;
    private Button btnOk;
    private Button btnBack;
    private CheckBox chkAsLink;
    private EFlyerEmail eFlyerEmail;

    protected SendEmailWindow() {
        this.setCaption("SEND E-FLYER TO EMAIL");
        eFlyerEmail = new EFlyerEmail();
        createUI();
    }

    private void createUI() {
        tfEmail = new TextField("EMAIL");
        btnOk = new Button(param.getLabel("default.ok"), this);
        btnBack = new Button(param.getLabel(Constants.LABEL_BACK), this);
        chkAsLink = new CheckBox("SEND AS LINK");
        btnOk.setIcon(new ThemeResource(Constants.SELECT_ICON));
        btnBack.setIcon(new ThemeResource(Constants.BACK_ICON));
        VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout(btnOk, btnBack);
        hl.setSpacing(true);
        vl.addComponent(tfEmail);
        vl.addComponent(chkAsLink);
        vl.addComponent(hl);
        vl.setSpacing(true);
        this.setContent(vl);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(btnOk)) {
            if (tfEmail.getValue() != null && !tfEmail.getValue().trim().equals("")) {
                eFlyerEmail.doSendEmail(tfEmail.getValue(), chkAsLink.getValue(), (EflyerModel) this.getSelectedObject());
                this.close();
            }
        } else if (event.getButton().equals(btnBack)) {
            this.close();
        }
    }

}
