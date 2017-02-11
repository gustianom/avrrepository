package com.tenma.common.gui.fieldfactory;

import com.tenma.common.gui.factory.TenmaFieldFactory;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.vaadin.data.Item;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Fri Jan 17 15:30:16 WIB 2014
 */
public class NotificationFormField extends TenmaFieldFactory {


    public NotificationFormField(Params params) {
        super(params);
    }

    @Override
    public Field createField(Item item, Object propertyId) {
        Field f;
        String caption = (String) propertyId;
        StringBuffer msgPrefixError = new StringBuffer().append(params.getMessage("please.enter.field")).append(" ");
        StringBuffer msgValidatorError = new StringBuffer();

        f = super.createTextField(item, propertyId);

        if ("notifyId".equals(propertyId))
            caption = "notifyId";
        else if (Constants.COMMUNITY_ID.equals(propertyId)) {
            caption = Constants.COMMUNITY_ID;
            TextField fl = (TextField) f;
            fl.setMaxLength(16);
        } else if ("notifyFrom".equals(propertyId)) {
            caption = "notifyFrom";
            TextField fl = (TextField) f;
            fl.setMaxLength(16);
        } else if ("notifyTo".equals(propertyId)) {
            caption = "notifyTo";
            TextField fl = (TextField) f;
            fl.setMaxLength(16);
        } else if ("notifySubject".equals(propertyId)) {
            caption = "notifySubject";
            TextField fl = (TextField) f;
            fl.setMaxLength(200);
        } else if ("notifyType".equals(propertyId))
            caption = "notifyType";
        f.setWidth("25%");
        f.setCaption(caption);
        f.setRequiredError(msgPrefixError.append(caption).toString());
        return f;
    }
}
