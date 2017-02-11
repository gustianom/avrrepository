package com.tenma.common.gui.display;

import com.tenma.share.gui.TenmaRoadMap;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Created by ndwijaya on 12/9/14.
 */
public class TenmaHeaderLayout extends HorizontalLayout {
    private TenmaRoadMap roadMap;
    private Label labelErr = new Label();
    private Button icon;
    private Label space = new Label("");


    public TenmaHeaderLayout() {
        labelErr.setContentMode(ContentMode.HTML);
        labelErr.setPrimaryStyleName("headerErrorMessage");
    }


    public void setRoadMap(TenmaRoadMap roadMap) {
        this.roadMap = roadMap;
    }

    public void generateLayout(HorizontalLayout hz) {
        addComponent(hz);
        addComponent(labelErr);
        setComponentAlignment(hz, Alignment.MIDDLE_LEFT);
        setComponentAlignment(labelErr, Alignment.MIDDLE_CENTER);
        if (roadMap != null) {
            addComponent(roadMap);
            setComponentAlignment(roadMap, Alignment.MIDDLE_RIGHT);
            setExpandRatio(roadMap, 2.0f);
        }
        setWidth("100%");
    }

    public Label getLabelErr() {
        System.out.println("share.tenma.common.gui.display.TenmaHeaderLayout.getLabelErr");
        return labelErr;
    }

    public void addIconComponent(Button icon) {
        this.icon = icon;

    }

    public void generateModifyLayout(Label l) {

        setSpacing(true);
        if (icon != null) addComponent(icon);
        addComponent(l);
        addComponent(labelErr);
        addComponent(space);

        setStyleName("tenmaHeader");
//        setComponentAlignment(icon, Alignment.TOP_LEFT);
//        setComponentAlignment(l, Alignment.MIDDLE_CENTER);
//        setComponentAlignment(labelErr, Alignment.MIDDLE_CENTER);
//        setComponentAlignment(space, Alignment.TOP_RIGHT);
//        setExpandRatio(l, 1.0f);
//        setExpandRatio(labelErr, 1.0f);
        l.setWidthUndefined();
        labelErr.setSizeUndefined();
        setExpandRatio(space, 4.0f);
        setWidth("100%");
    }


}
