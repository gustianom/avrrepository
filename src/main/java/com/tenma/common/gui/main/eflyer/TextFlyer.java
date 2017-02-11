package com.tenma.common.gui.main.eflyer;


import com.tenma.common.util.Constants;
import com.tenma.model.common.EflyerModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;


/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/17/2015
 * Time    : 10:38 AM
 * Project : udw
 * Package : share.tenma.common.gui.main.eflyer
 */
public class TextFlyer extends Panel implements IFlyer, Button.ClickListener {
    private Button btnEdit;
    private Label lbl;
    private IFlyer master;
    private AbsoluteLayout layout;

    public TextFlyer(IFlyer master) {
        this.master = master;
        createUI();
    }

    private void createUI() {
        btnEdit = new Button(new ThemeResource(Constants.ADD_ICON));
        btnEdit.addClickListener(this);
        lbl = new Label("");
        lbl.setContentMode(ContentMode.HTML);
        layout = new AbsoluteLayout();
        layout.addComponent(btnEdit, "" +
                "left: 0px; " +
                "top: 0px;" +
                " z-index: 100;");
        layout.addComponent(lbl, "" +
                "left: 0px; " +
                "top: 0px;" +
                " z-index: 99;");
        this.setStyleName("imageFlyer");
        this.setContent(layout);
    }


    @Override
    public String getDataContent() {
        return lbl.getValue();
    }

    @Override
    public void setDataContent(String value) {
        lbl.setValue(value);
    }

    @Override
    public EflyerModel getModel() {
        return master.getModel();
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton().equals(btnEdit)) {
            WindowFlyer wf = new WindowFlyer(this);
            UI.getCurrent().getUI().addWindow(wf);
        }
    }

    @Override
    public void setWidth(float width, Unit unit) {
        super.setWidth(width, unit);
        if (layout != null) layout.setWidth(width, unit);
    }

    @Override
    public void setHeight(float height, Unit unit) {
        super.setHeight(height, unit);
        if (layout != null) layout.setHeight(height, unit);
    }


}
