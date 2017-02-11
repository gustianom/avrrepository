
package com.tenma.common.gui.display;

import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

import java.text.DecimalFormat;
import java.text.ParseException;

/*
 * Copyright (c) 2013. PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 12/10/13
 * Time: 9:14 AM
 * Version 0.1 - 10.12.13
 * Version 0.2 - 15.12.13
 *  Added features
 *   - right Alignment
 *   - option currency symbol is next to  the amount
 *   - default value is zero
 *   - support logo $, S$, Rp, E
 *
 */

public class TenmaMoneyField extends CustomField {
    private String defCurrency;
    private Label cur;
    private TextField famount;
    private double amount;
    private DecimalFormat df;
    private boolean viewOnly;
    private boolean hideCur;
    private PriceListener parent;
    private HorizontalLayout hz;

    public TenmaMoneyField(String defaultCurrency, PriceListener plisten) {
        defCurrency = defaultCurrency;
        df = new DecimalFormat("#,##0;(#,##0)");
        viewOnly = false;
        parent = plisten;
        hideCur = false;
        createUI(viewOnly); // textfield
        setValue(0d);
    }

    public TenmaMoneyField(String defaultCurrency, PriceListener plisten, Boolean hideCur) {
        defCurrency = defaultCurrency;
        df = new DecimalFormat("#,##0;(#,##0)");
        viewOnly = false;
        this.hideCur = hideCur;
        parent = plisten;
        createUI(viewOnly); // textfield
        setValue(0d);
    }

    public TenmaMoneyField(String defaultCurrency) {
        defCurrency = defaultCurrency;
        df = new DecimalFormat("#,##0;(#,##0)");
        viewOnly = false;
        hideCur = false;
        createUI(viewOnly); // textfield
        setValue(0d);
    }

    public TenmaMoneyField(String defaultCurrency, boolean aviewOnly, Boolean hideCur) {
        defCurrency = defaultCurrency;
        this.viewOnly = aviewOnly;
        this.hideCur = hideCur;
        df = new DecimalFormat("#,##0;(#,##0"); // default formatting
        createUI(viewOnly); // view only using label
        setValue(0d);

    }

    public TenmaMoneyField(String defaultCurrency, boolean aviewOnly) {
        defCurrency = defaultCurrency;
        this.viewOnly = aviewOnly;
        hideCur = false;
        df = new DecimalFormat("#,##0;(#,##0"); // default formatting
        createUI(viewOnly); // view only using label
        setValue(0d);

    }

    public void seNewtFormat(String format) {
        DecimalFormat tdf = new DecimalFormat(format);
        try {
            tdf.format(999999d);
        } catch (Exception e) {
            e.printStackTrace();
            tdf = df;
        } finally {
            df = tdf;
        }
        refreshValue();
    }

    private void createUI(boolean viewOnly) {
        hz = new HorizontalLayout();

        cur = new Label(defCurrency);
        cur.setWidth("20px");
        famount = new TextField();
        famount.setImmediate(true);
        if (parent != null) {
            famount.addTextChangeListener(new FieldEvents.TextChangeListener() {
                @Override
                public void textChange(FieldEvents.TextChangeEvent event) {
                    try {
//                        parent.setPrice(df.parse(event.getText()).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            famount.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        }
        if (viewOnly) {
            famount.setEnabled(false);
        }
        famount.setStyleName("tenmaMoneyView");

        if (!hideCur) {
            hz.addComponent(cur);
            hz.setComponentAlignment(cur, Alignment.TOP_LEFT);
        }

        hz.addComponent(famount);
        hz.setComponentAlignment(famount, Alignment.TOP_RIGHT);

        hz.setSpacing(true);
        setStyleName("tenmaMoneyField");
    }

    private void refreshValue() {
        if (famount != null) {
            String formatedValue = df.format(amount);
            famount.setValue(formatedValue);
        }
    }

    public void setCurVisible(boolean visible) {
        cur.setVisible(visible);
    }

    public void setCurrenncy(String newCurrency) {
        cur.setValue(newCurrency);
    }

    public String getCurrency() {
        return defCurrency;
    }

    public double getAmount() {
        double rs = 0;
        try {
            rs = df.parse(famount.getValue()).doubleValue();
        } catch (ParseException e) {
            if (cur.isVisible())
                cur.setCaption("E");
        }
        return rs;
    }

    @Override
    public float getWidth() {
        return hz.getWidth();
    }

    @Override
    public void setWidth(String width) {
        famount.setWidth(width);
    }

    @Override
    public Class getType() {
        return famount.getType();
    }

    public void addValueChangeListener(ValueChangeListener listener) {
        famount.addValueChangeListener(listener);
    }

    public void addTextChangeListener(FieldEvents.TextChangeListener textChangeListener) {
        famount.addTextChangeListener(textChangeListener);
        famount.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
    }

    @Override
    protected Component initContent() {
        return hz;
    }

    @Override
    public boolean isValid() {
        return famount.getValue() != null;
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        super.validate();
    }

    @Override
    public Object getValue() {
        return famount.getValue();
    }

    @Override
    public void setValue(Object newFieldValue) throws ReadOnlyException, Converter.ConversionException {
        amount = (double) newFieldValue;
        refreshValue();
    }
}
