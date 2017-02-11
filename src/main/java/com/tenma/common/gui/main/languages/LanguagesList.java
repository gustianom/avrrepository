package com.tenma.common.gui.main.languages;

import com.tenma.common.TA;
import com.tenma.common.bean.languages.LanguagesHelper;
import com.tenma.common.gui.display.TenmaMasterList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.model.LanguagesModel;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 19:32:27 ICT 2013
 */
public class LanguagesList extends TenmaMasterList {
    private final String COLUMN_FIELD_LANG_CODE = "langCode";
    private final String COLUMN_FIELD_LANG_NAME = "langName";
    private TenmaTableContainer container;

    public LanguagesList() {

        setTitle("Languages.title");
    }

    @Override
    public void doModify(TenmaPanel parentContainer, int update_mode) {
        try {
            LanguagesModify modify = new LanguagesModify(this, update_mode);
            TA.getCurrent().updateWorkingArea(modify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int doDeletion() throws Exception {
        LanguagesModel m = (LanguagesModel) getSelectedObject();
        setAuditTrail(m);
        LanguagesHelper help = new LanguagesHelper();
        int res = help.deleteLanguages(m);
        return res;
    }

    @Override
    public void refreshTable() {
        doRefreshData();
    }

    public void executingDataPreparation() {
        container = new TenmaTableContainer();

        container.addContainerProperty(COLUMN_FIELD_LANG_CODE, String.class, "");
        container.addContainerProperty(COLUMN_FIELD_LANG_NAME, String.class, "");

        getTableProperty().setContainerDataSource(container);
        getTableProperty().commit();

        getTableProperty().setColumnHeaders(new String[]{
                        param.getLabel("language.code"),
                        param.getLabel("language.name")
                }
        );
        getTableProperty().setSelectable(true);
        getTableProperty().setImmediate(true);
        getTableProperty().addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                LanguagesModel sel = new LanguagesModel();
                sel.setLangId((Integer) event.getProperty().getValue());
                Item item = getTableProperty().getItem(sel.getLangId());
                sel.setLangCode((String) item.getItemProperty(COLUMN_FIELD_LANG_CODE).getValue());
                sel.setLangName((String) item.getItemProperty(COLUMN_FIELD_LANG_NAME).getValue());
                setSelectedObject(sel);
            }
        });
        doRefreshData();
    }

    @Override
    public void doSearch() {
        setPageIndex(0);
        setPageIndex(0);
        doRefreshData();
    }

    private void doRefreshData() {
        container.removeAllItems();
        container = collectData();
    }

    private TenmaTableContainer collectData() {
        HashMap p = new HashMap();
        String skey = (String) getTextSearch().getValue();
        if ((skey != null) && (skey.trim().length() != 0))
            p.put(Constants.HEADER_SEARCH, "%" + skey + "%");

        LanguagesHelper help = new LanguagesHelper();

        List ls = help.getListRenderer(p, false);

        for (int i = 0; i < ls.size(); i++) {
            LanguagesModel m = (LanguagesModel) ls.get(i);
            Item id = container.addItem(m.getLangId());
            id.getItemProperty(COLUMN_FIELD_LANG_CODE).setValue(m.getLangCode());
            id.getItemProperty(COLUMN_FIELD_LANG_NAME).setValue(m.getLangName());
        }

        return container;
    }
}
