package com.tenma.common.gui.display.menu;

import com.tenma.common.TA;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by ndwijaya on 7/17/2016.
 */
public class MenuTitleBox extends VerticalLayout {
    private Label lTitle = new Label();
    private Button icon = new Button();

    public MenuTitleBox(String title) {

        lTitle.setWidth("125px");
        lTitle.setValue(TA.getCurrent().getParams().getLabel(title));
        lTitle.setCaptionAsHtml(true);


        icon.setWidth("25px");
        //  lIcon.setIcon(new ThemeResource("layouts/images/icon/Expand.png"));
        icon.setIcon(new ThemeResource("layouts/images/16/132.png"));
//        icon.setIcon(FontAwesome.DIAMOND);
        icon.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        icon.setWidth("30px");
        icon.setHeight("30px");


        HorizontalLayout hTitle = new HorizontalLayout(icon, lTitle);
        hTitle.setWidth("100%");
        hTitle.setMargin(new MarginInfo(false, true, false, false));
        hTitle.setComponentAlignment(lTitle, Alignment.MIDDLE_CENTER);
        hTitle.setComponentAlignment(icon, Alignment.MIDDLE_LEFT);
        hTitle.setExpandRatio(lTitle, 1.0f);
        hTitle.setSpacing(true);
//        VerticalLayout vMenuItem = new VerticalLayout();
        this.addComponent(hTitle);


        this.addStyleName("parentMenu");
        this.setHeight("35px");
        this.setWidth("100%");
    }

    public void setMenuMode(boolean simple) {
        System.out.println("MenuItemBox.setMenuMode " + simple);
        lTitle.setVisible(!simple);
        icon.setVisible(!simple);
        if (simple) {
            this.setStyleName("simpleparentMenu");
            this.setHeight("5px");
        } else {
            this.setStyleName("parentMenu");
            this.setHeight("35px");
        }
    }
}

