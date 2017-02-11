package com.tenma.common.gui.fieldfactory;

import com.tenma.common.gui.factory.TenmaFieldFactory;
import com.tenma.common.util.Params;
import com.vaadin.data.Item;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:46:33 ICT 2013
 */
public class LangLabelValueFormField extends TenmaFieldFactory {


    public LangLabelValueFormField(Params params) {
        super(params);
    }

    @Override
    public Field createField(Item item, Object propertyId) {
        Field f;
        String caption = (String) propertyId;
        StringBuffer msgPrefixError = new StringBuffer().append(params.getMessage("please.enter.field")).append(" ");
        StringBuffer msgValidatorError = new StringBuffer();

        f = super.createTextField(item, propertyId);

        if ("langId".equals(propertyId))
            caption = "langId";
        else if ("labelId".equals(propertyId))
            caption = "labelId";
        else if ("labelValue".equals(propertyId)) {
            caption = "labelValue";
            TextField fl = (TextField) f;
            fl.setMaxLength(200);
        }
        f.setWidth("25%");
        f.setCaption(caption);
        f.setRequiredError(msgPrefixError.append(params.getLabel(caption)).toString());
        return f;
    }
}
