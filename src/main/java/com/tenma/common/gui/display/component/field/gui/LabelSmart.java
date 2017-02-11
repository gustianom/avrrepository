package com.tenma.common.gui.display.component.field.gui;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Created by gustianom on 12/30/13.
 */
public class LabelSmart extends HorizontalLayout {
    private Label label = new Label();
    private Button lblIcon = new Button();
    private int type = 0;

    public LabelSmart() {
        doLabeling("", null);
    }

    public LabelSmart(String caption) {
        doLabeling(caption, null);
    }

    public LabelSmart(String caption, ThemeResource icon) {
        doLabeling(caption, icon);
    }

    private void doLabeling(String caption, ThemeResource icon) {
        lblIcon.setId("BUTTON_REMOVE_ICON");
        label.setCaption(caption);
        lblIcon.setPrimaryStyleName("buttonNoBorder");
        addComponent(label);
        addComponent(lblIcon);
        if (icon != null)
            lblIcon.setIcon(icon);


    }

    @Override
    public String getCaption() {
        return label.getCaption();
    }

    @Override
    public void setCaption(String caption) {
        label.setCaption(caption);
    }

    @Override
    public Resource getIcon() {
        return lblIcon.getIcon();
    }

    @Override
    public void setIcon(Resource icon) {
        lblIcon.setIcon(icon);
    }

    public Resource getLabelIcon() {
        return label.getIcon();
    }

    public void setLabelIcon(Resource icon) {
        label.setIcon(icon);
    }

    public void addClickListener(Button.ClickListener clickListener) {
        lblIcon.addClickListener(clickListener);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
