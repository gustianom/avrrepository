package com.tenma.common.gui.display.menu;

import com.tenma.model.core.CommunityUserGroupMenuModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

/**
 * Created by ndwijaya on 7/17/2016.
 */
public class MenuItemBox extends HorizontalLayout {

    private Label lblMenu = new Label();
    private HorizontalLayout hzimg = new HorizontalLayout();
    private Image img;
    private String groupTitle;
    private Label space = new Label("");
    private boolean collapse;
    private Integer group;

    public MenuItemBox(CommunityUserGroupMenuModel model) {
        collapse = model.isCollapse();
        group = model.getMenuGrpId();
        lblMenu.setContentMode(ContentMode.HTML);
        lblMenu.setValue("<H3>" + model.getMenuName() + "</H3>");
        System.out.println("model.getMenuImage() = " + model.getMenuImage());
        if (model.getMenuImage() == null) {
            img = new Image(null, new ThemeResource("layouts/images/16/set-blue.png"));
        } else {
            img = new Image(null, new ThemeResource(model.getMenuImage()));
        }
        img.setWidth("40px");
        hzimg.addComponent(img);
        hzimg.setWidth("50px");

        space.setWidth("2px");
        this.setPrimaryStyleName("menuleft-itembox");
        this.setMargin(false);
        this.setSpacing(true);
        this.addComponent(space);
        this.addComponent(hzimg);
        this.addComponent(lblMenu);
        this.setComponentAlignment(space, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(hzimg, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(lblMenu, Alignment.MIDDLE_RIGHT);

        this.setWidth("220px");
        this.setHeight("60px");
//        this.setExpandRatio(hzimg, 1.0f);
        this.setExpandRatio(lblMenu, 1.0f);
        groupTitle = model.getMenuGrpName();
    }

    public void setMenuMode(boolean simple) {
        System.out.println("MenuItemBox.setMenuMode " + simple);
        lblMenu.setVisible(!simple);
        if (simple) {
            System.out.println("setWidth(70px);");
            this.setWidth("70px");
            this.setComponentAlignment(space, Alignment.MIDDLE_LEFT);
            this.setComponentAlignment(hzimg, Alignment.MIDDLE_CENTER);
            this.setExpandRatio(hzimg, 1.0f);
        } else {
            System.out.println("setWidth(220px);");
            this.setWidth("220px");
            this.setComponentAlignment(space, Alignment.MIDDLE_LEFT);
            this.setComponentAlignment(hzimg, Alignment.MIDDLE_CENTER);
            this.setComponentAlignment(lblMenu, Alignment.MIDDLE_RIGHT);
            this.setExpandRatio(hzimg, 0f);
            this.setExpandRatio(lblMenu, 1.0f);
        }
    }


    public String getGroupTitle() {
        return groupTitle;
    }

    public boolean isCollapse() {
        return collapse;
    }

    public Integer getGroup() {
        return group;
    }
}
