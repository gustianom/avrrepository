package com.tenma.fam.gui.main.balancesheetreportconfig.balancesheetaccount;


import com.tenma.model.fam.AccountModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by tenma-01 on 07/01/16.
 */
public class AccountBoxItem extends HorizontalLayout implements Button.ClickListener {
    private final AccountModel accountModel;
    private VerticalLayout root = new VerticalLayout();
    private Button icon;

    public AccountBoxItem(AccountModel model) {
        this.accountModel = model;
        prepareAccountBoxItem();
    }

    private void prepareAccountBoxItem() {
        icon = new Button();
        icon.setVisible(false);
        icon.setIcon(new ThemeResource("layouts/images/16/closed.png"));
        icon.setPrimaryStyleName("btnClosedwelcome");
        icon.addClickListener(this);
        generateUI();
    }

    public void generateUI() {
        root.setHeight("80");
        root.setWidth("170px");
        root.setMargin(new MarginInfo(false, false, false, true));
        root.setStyleName("studentitemborder");
        addComponent(root);
        addComponent(icon);
        setStyleName("studentroot");

        Button labelName = new Button();
//        Button labelAccountId = new Button();
//        labelName.setPrimaryStyleName("roommaster");
//        labelAccountId.setPrimaryStyleName("roommaster");
//        labelAccountId.addClickListener(click -> showAccountId());
//        labelAccountId.setWidth(null);
        labelName.setWidth("60px");
        labelName.setStyleName(ValoTheme.BUTTON_LINK);
        VerticalLayout vName = new VerticalLayout(labelName);

//        labelAccountId.setCaption(accountModel.getAccountId());
        labelName.setCaption(accountModel.getAccountName());

        HorizontalLayout hz = new HorizontalLayout();
        hz.addComponent(vName);
        hz.setComponentAlignment(vName, Alignment.MIDDLE_RIGHT);
        root.addComponent(hz);
        hz.setWidth("100%");
    }


    @Override
    public void buttonClick(Button.ClickEvent event) {

    }
}
