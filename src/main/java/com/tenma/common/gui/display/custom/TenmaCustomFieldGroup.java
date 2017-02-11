/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.common.gui.display.custom;

import com.google.gson.Gson;
import com.tenma.model.common.TenmaCustomFieldsModel;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;

/**
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/14/14
 * Time: 7:03 PM
 */
public class TenmaCustomFieldGroup extends Panel {
    private List<TenmaCustomFieldsModel> lsFieldModel;
    private HashMap component;
    private String customFields[];
    private TenmaCustomGenericModel model;

    public TenmaCustomFieldGroup(List<TenmaCustomFieldsModel> listModel) {
        model = new TenmaCustomGenericModel();
        component = new HashMap();
        setCustomFields(listModel);
        generateUI();
    }

    private void setCustomFields(List<TenmaCustomFieldsModel> listModel) {
        lsFieldModel = listModel;
        setField();
    }

    private void setField() {
        String[] fiels = new String[lsFieldModel.size()];
        for (int i = 0; i < lsFieldModel.size(); i++) {
            TenmaCustomFieldsModel tf = lsFieldModel.get(i);
            fiels[i] = tf.getFieldName();
        }
        customFields = fiels;
        model.initModel(customFields);
    }

    public Object getFieldValue(String field) {
        return model.getModelValue(field);
    }

    public void setFieldValue(String field, Object o) {
        model.setModelValue(field, o);
    }

    public void addValue(String field, Object o) {

    }

    public void fireData() {  // setUI value with model value
        for (int i = 0; i < customFields.length; i++) {
            Object uic = component.get(customFields[i]);
            if (uic instanceof TextField) {
                TextField textField = (TextField) uic;
                String value = textField.getValue();
                model.setModelValue(customFields[i], value);
            } else if (uic instanceof TextArea) {
                TextArea textArea = (TextArea) uic;
                String value = textArea.getValue();
                model.setModelValue(customFields[i], value);
            } else if (uic instanceof NativeSelect) {
                NativeSelect natiive = (NativeSelect) uic;
                Object value = natiive.getValue();
                model.setModelValue(customFields[i], value);
            }
        }
    }

    public String getGSon() {
        fireData();
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    public void setGSon(String jsonObject) {
        Gson gson = new Gson();
        TenmaCustomGenericModel model = gson.fromJson(jsonObject, TenmaCustomGenericModel.class);

    }

    public void prepareModelData() {  // set model value with UI value
        for (int i = 0; i < customFields.length; i++) {
            Object uic = component.get(customFields[i]);
            if (uic instanceof TextField) {
                TextField textField = (TextField) uic;
                Object value = model.getModelValue(customFields[i]);
                String svalue = String.valueOf(value);
                textField.setValue(svalue);
            } else if (uic instanceof TextArea) {
                TextArea textArea = (TextArea) uic;
                Object value = model.getModelValue(customFields[i]);
                String svalue = String.valueOf(value);
                textArea.setValue(svalue);
            } else if (uic instanceof NativeSelect) {
                NativeSelect natiive = (NativeSelect) uic;
                Object value = model.getModelValue(customFields[i]);
                natiive.setValue(value);
            }
        }
    }

    private void generateUI() {
        System.out.println("TenmaCustomFieldGroup.generateUI");
        if (lsFieldModel != null) {
            VerticalLayout vl = new VerticalLayout();
            vl.setSpacing(true);
            for (int i = 0; i < lsFieldModel.size(); i++) {
                TenmaCustomFieldsModel tf = lsFieldModel.get(i);
                int type = tf.getFieldType().intValue();
                System.out.println("type = " + type);
                switch (type) {
                    case 1:
                        TextField txtfield = new TextField();
                        HorizontalLayout hz1 = new HorizontalLayout();
                        hz1.addComponent(txtfield);
                        hz1.setCaption(tf.getFieldLabel());
                        if (tf.getFieldWidth() != 0) {
                            txtfield.setWidth(tf.getFieldWidth() + "px");
                        }
                        vl.addComponent(hz1);
                        component.put(tf.getFieldName(), txtfield);
                        break;
                    case 2:
                        TextArea areaField = new TextArea();
                        HorizontalLayout hz2 = new HorizontalLayout();
                        hz2.addComponent(areaField);
                        hz2.setCaption(tf.getFieldLabel());
                        vl.addComponent(hz2);
                        if (tf.getFieldWidth() != 0) {
                            areaField.setWidth(tf.getFieldWidth() + "px");
                        }
                        component.put(tf.getFieldName(), areaField);
                        break;
                    case 3:
                        // TODO - grouping
                        NativeSelect nativeSelect = new NativeSelect();
                        HorizontalLayout hz3 = new HorizontalLayout();
                        hz3.addComponent(nativeSelect);
                        hz3.setCaption(tf.getFieldLabel());
                        vl.addComponent(hz3);
                        if (tf.getFieldWidth() != 0) {
                            nativeSelect.setWidth(tf.getFieldWidth() + "px");
                        }
                        component.put(tf.getFieldName(), nativeSelect);
                        break;
                    case 4:
                        //TODO support for date, radio,checkbox,  boolean, customLookup
                        break;
                }
            }
            HorizontalLayout hl = new HorizontalLayout();
            hl.setMargin(true); // parameterized later
            // TODO has more than 1 clumn
            hl.addComponent(vl);
            setContent(hl);
        }
    }

    public Component getComponent(String field) {
        return (Component) component.get(field);
    }

}
