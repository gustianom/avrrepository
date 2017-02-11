package com.tenma.common.gui.fieldfactory;

import com.tenma.common.gui.factory.TenmaFieldFactory;
import com.tenma.common.util.Params;
import com.vaadin.data.Item;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 19:32:27 ICT 2013
 */
public class LanguagesFormField extends TenmaFieldFactory {


    public LanguagesFormField(Params params) {
        super(params);
    }

    @Override
    public Field createField(Item item, Object propertyId) {
        Field f;
        String caption = (String) propertyId;
        StringBuffer msgPrefixError = new StringBuffer().append(params.getMessage("please.enter.field")).append(" ");
        StringBuffer msgValidatorError = new StringBuffer();

        if ("langCode".equals(propertyId))
            f = super.createComboField(propertyId);
        else
            f = super.createTextField(item, propertyId);

        if ("langId".equals(propertyId))
            f.setVisible(false);
        else if ("langCode".equals(propertyId)) {
            caption = params.getLabel("language.name");
            Locale[] locales = Locale.getAvailableLocales();
            HashMap map = new HashMap();
            int i = 0;
            NativeSelect tf = (NativeSelect) f;
            tf.setRequired(true);
            tf.setWidth("250px");
            tf.setCaption(caption);

            for (Locale locale : locales) {
                String lCode = locale.getLanguage();
                String name = locale.getDisplayLanguage(new Locale(lCode));

                System.out.println("iso = " + lCode + "  -- " + name + " -- lang " + locale.getLanguage() + " -Country " + locale.getCountry() + " - isoLang " + locale.getISO3Language() + "toString " + locale.toString());
                i++;

                if (!"".equals(lCode) && !"".equals(name)) {
                    if (!map.containsKey(lCode)) {
                        map.put(lCode, lCode);
                        tf.addItem(lCode);
                        tf.setItemCaption(lCode, name);
//                        String icon =  locale.getCountry().trim().toLowerCase() +".png";
//                        tf.setItemIcon(lCode, new ThemeResource("layouts/images/flag/"+icon));
                    }
                }
            }

        } else if ("langImg".equals(propertyId)) {
            caption = params.getLabel("language.flag");
            TextField fl = (TextField) f;
            fl.setMaxLength(50);
        }
        f.setWidth("25%");
        f.setCaption(caption);
        f.setRequiredError(msgPrefixError.append(params.getLabel(caption)).toString());
        return f;
    }
}
