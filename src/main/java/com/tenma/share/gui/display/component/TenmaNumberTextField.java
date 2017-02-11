package com.tenma.share.gui.display.component;

import com.vaadin.event.FieldEvents;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ndwijaya on 12/19/2014.
 * Features :
 * 1. support style background color for invalid number textfield
 * 2. support right alignment by default
 * 3. automatic immediate true, null representation is "0", EAGER changeEventMode
 * 4. support formatting with thousand delimiter on set Value and lost focus
 * 5. default required error message
 */
public class TenmaNumberTextField extends TextField implements FieldEvents.BlurListener {
    protected PopupView popupView;

    public TenmaNumberTextField() {
        init();
    }

    public TenmaNumberTextField(String caption) {
        super(caption);
        init();
    }

    private void init() {
        setImmediate(true);
        setNullRepresentation("0");
        setTextChangeEventMode(TextChangeEventMode.EAGER);
        addTextChangeListener(createTextChangeListener());
        addBlurListener(this);
        setRequiredError(getCaption() + " is required");
        setTextStyle();
    }

    private FieldEvents.TextChangeListener createTextChangeListener() {
        FieldEvents.TextChangeListener l = new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                doTextChangeAction(event);
            }
        };
        return l;
    }

    private void doTextChangeAction(FieldEvents.TextChangeEvent event) {
        int pos = event.getCursorPosition();
        String text = event.getText();
        System.out.println(pos + " " + text);
        if (!isNumber(text)) {
            setbackgroundRed();
        } else {
            setbackgroundNormal();
        }
    }

    private boolean isNumber(String text) {
        boolean isnumber = StringUtils.isNumeric(text);

        System.out.println("isnumber = " + isnumber);
        return isnumber;
    }

    @Override
    public void blur(FieldEvents.BlurEvent event) {
        String value = getValue();
        System.out.println("Value on lost focus = " + value);
        if (!isNumber(value)) {
            Notification.show(getCaption().concat(" is invalid number"));
            setbackgroundRed();
            focus();

        }
    }

    private void setbackgroundRed() {
        System.out.println("TenmaNumberTextField.setbackgroundRed");
        Page.Styles styles = Page.getCurrent().getStyles();
        StringBuilder buf = new StringBuilder();
        buf.append(".numbertextfield {");
        buf.append(" background-color: pink; ");
        buf.append(" text-align: right; ");
        buf.append("}");
        styles.add(buf.toString());
    }


    private void setbackgroundNormal() {
        System.out.println("TenmaNumberTextField.setbackgroundNormal");
        Page.Styles styles = Page.getCurrent().getStyles();
        StringBuilder buf = new StringBuilder();
        buf.append(".numbertextfield {");
        buf.append(" background-color: white; ");
        buf.append("}");
        styles.add(buf.toString());
    }

    private void setTextStyle() {
        System.out.println("TenmaNumberTextField.setbackgroundNormal");
        this.setPrimaryStyleName("numbertextfield");
        Page.Styles styles = Page.getCurrent().getStyles();
        StringBuilder buf = new StringBuilder();
        buf.append(".numbertextfield {");
        buf.append("  ");
        buf.append(" text-align: right; ");
        buf.append("}");
        styles.add(buf.toString());
    }


}
