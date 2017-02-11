package com.tenma.common.gui.main.task;


import com.tenma.common.TA;
import com.tenma.common.util.TenmaConverter;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/15/13
 * Time: 4:41 PM
 */
public abstract class ColumnDetailViewer extends FormLayout implements Button.ClickListener {
    private final String BUTTON_MORE = "TASK_BUTTON_MORE";
    private int maxDesc = 250;
    private Label labelDescription;
    private Label contentCaption;

    private HorizontalLayout layout;
    private String contentDetail;
    private HorizontalLayout buttonArea;
    private Button moreDescBtn;
    private Label labelDateLog;
    private Label labelDateRemaining;


    public ColumnDetailViewer() {
        preparingUI();
        setPrimaryStyleName("rowDetailViewer");
    }

    private void preparingUI() {
        VerticalLayout content = buildContent();
        content.setWidth("100%");

        layout = new HorizontalLayout();
        layout.addComponent(content);
        layout.setComponentAlignment(content, Alignment.TOP_LEFT);
        layout.setWidth(100, Unit.PERCENTAGE);

        addComponent(layout);
        setComponentAlignment(layout, Alignment.TOP_LEFT);
        setWidth(100, Unit.PERCENTAGE);
    }

    private VerticalLayout buildContent() {

        contentCaption = new Label();
        contentCaption.setContentMode(ContentMode.HTML);
        contentCaption.setStyleName("employeeNumer");


        labelDescription = new Label();
        labelDescription.setContentMode(ContentMode.HTML);
        labelDescription.setPrimaryStyleName("rowContentDescription");

        moreDescBtn = new Button();
        moreDescBtn.setId(BUTTON_MORE);
        moreDescBtn.setCaption(TA.getCurrent().getParams().getLabel("default.more"));
        moreDescBtn.setPrimaryStyleName("buttonRowView");
        moreDescBtn.addClickListener(this);
        moreDescBtn.setVisible(false);

        VerticalLayout lydesc = new VerticalLayout();
        lydesc.addComponent(labelDescription);
        lydesc.addComponent(moreDescBtn);

        HorizontalLayout commandArea = buildCommandArea();

        VerticalLayout lay = new VerticalLayout();
        lay.setSpacing(true);

        lay.addComponent(contentCaption);
        lay.addComponent(lydesc);
        lay.addComponent(commandArea);
        return lay;
    }

    private HorizontalLayout buildCommandArea() {


        buttonArea = new HorizontalLayout();
        buttonArea.setMargin(true);

        HorizontalLayout ly = new HorizontalLayout();
        ly.setWidth("100%");
        ly.setSpacing(true);

        HorizontalLayout lyDateLog = new HorizontalLayout();
        lyDateLog.setSpacing(true);

        labelDateLog = new Label();
        labelDateLog.setContentMode(ContentMode.HTML);

        labelDateRemaining = new Label();
        labelDateRemaining.setContentMode(ContentMode.HTML);


        lyDateLog.addComponent(labelDateLog);
        lyDateLog.addComponent(labelDateRemaining);

        ly.addComponent(buttonArea);
        ly.addComponent(lyDateLog);

        ly.setComponentAlignment(buttonArea, Alignment.MIDDLE_LEFT);
        ly.setComponentAlignment(lyDateLog, Alignment.MIDDLE_RIGHT);

        return ly;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String id = clickEvent.getButton().getId();
        if (BUTTON_MORE.equals(id))
            doViewMore();
    }

    private void doViewMore() {
        labelDescription.setValue(contentDetail);
        moreDescBtn.setVisible(false);
    }

    public final void setCaption(String caption) {
        layout.setCaption(caption);
    }


    public final void setContentCaption(String caption) {
        contentCaption.setCaption(caption);
    }

    public final void setContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
        String desc = "";
        if (contentDetail != null)
            desc = contentDetail;

        if (desc.length() > maxDesc) {
            if (desc.startsWith("<")) {
                int tmpMax = 1000;
                if (desc.length() > tmpMax)
                    maxDesc = tmpMax;
                else maxDesc = desc.length();

            }
            desc = new StringBuffer().append(desc.substring(0, maxDesc)).append("...").toString();
            moreDescBtn.setVisible(true);
        } else
            moreDescBtn.setVisible(false);

        labelDescription.setValue(desc);
    }

    public HorizontalLayout getButtonArea() {
        return buttonArea;
    }

    public final void setDateLog(Date dt) {
        Calendar calNow = Calendar.getInstance(TA.getCurrent().getLocale());
        Calendar calCheck = Calendar.getInstance(TA.getCurrent().getLocale());
        calCheck.setTime(dt);

        StringBuffer buf = new StringBuffer();
        buf.append(TenmaConverter.dateToStringWord(dt, TA.getCurrent().getLocale()));

        HashMap map = TenmaConverter.calculateYear(calCheck, calNow);
        int mn = (Integer) map.get(Calendar.MONTH);
        int yr = (Integer) map.get(Calendar.YEAR);

        if (yr > 0)
            buf.append(" ").append(TA.getCurrent().getParams().getLabel("default.on")).append(" ").append(TenmaConverter.dateToString(dt, "dd-MMM-yyyy HH:mm"));
        else {
            if (mn > 0)
                buf.append(" ").append(TA.getCurrent().getParams().getLabel("default.on")).append(" ").append(TenmaConverter.dateToString(dt, "dd-MMM-yyyy HH:mm"));
            else {
                buf.append(" ").append(TA.getCurrent().getParams().getLabel("default.on")).append(" ").append(TenmaConverter.dateToString(dt, "HH:mm"));
            }

        }

        labelDateLog.setValue(buf.toString());
    }

    public final void setDateRemaining(Date dt) {
        labelDateRemaining.setValue(TenmaConverter.dateToStringWordRemaining(dt, TA.getCurrent().getLocale()));
    }

}

