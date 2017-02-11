package com.tenma.common.gui.main.lang_label_value;

import com.tenma.common.TA;
import com.tenma.common.bean.lang_label_value.LangLabelValueHelper;
import com.tenma.common.gui.display.TenmaMasterList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.model.LangLabelValueModel;
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
 * Generated on Mon Jul 08 21:46:32 ICT 2013
 */
public class LangLabelValueList extends TenmaMasterList {
    private final String COLUMN_FIELD_LANG_ID = "langId";
    private final String COLUMN_FIELD_LABEL_ID = "labelId";
    private final String COLUMN_FIELD_LABEL_VALUE = "labelValue";
    private TenmaTableContainer container;

    public LangLabelValueList() {

        setTitle("LangLabelValue.title");
    }

    @Override
    public void doModify(TenmaPanel parentContainer, int update_mode) {
        try {
            LangLabelValueModify modify = new LangLabelValueModify(this, update_mode);
            TA.getCurrent().updateWorkingArea(modify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int doDeletion() throws Exception {
        LangLabelValueModel m = (LangLabelValueModel) getSelectedObject();
        setAuditTrail(m);
        LangLabelValueHelper help = new LangLabelValueHelper();
        int res = help.deleteLangLabelValue(m);
        return res;
    }

    @Override
    public void refreshTable() {
        doRefreshData();
    }

    public void executingDataPreparation() {
        container = new TenmaTableContainer();

        container.addContainerProperty(COLUMN_FIELD_LANG_ID, Integer.class, 0);
        container.addContainerProperty(COLUMN_FIELD_LABEL_ID, Integer.class, 0);
        container.addContainerProperty(COLUMN_FIELD_LABEL_VALUE, String.class, "");

        getTableProperty().setContainerDataSource(container);
        getTableProperty().commit();

        getTableProperty().setColumnHeaders(new String[]{
                        param.getLabel("LANG_ID"),
                        param.getLabel("LABEL_ID"),
                        param.getLabel("LABEL_VALUE")
                }
        );
        getTableProperty().setSelectable(true);
        getTableProperty().setImmediate(true);
        getTableProperty().addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                LangLabelValueModel sel = new LangLabelValueModel();
                sel.setLangId((Integer) event.getProperty().getValue());
                Item item = getTableProperty().getItem(sel.getLangId());
                sel.setLangId((Integer) item.getItemProperty(COLUMN_FIELD_LANG_ID).getValue());
                sel.setLabelId((Integer) item.getItemProperty(COLUMN_FIELD_LABEL_ID).getValue());
                sel.setLabelValue((String) item.getItemProperty(COLUMN_FIELD_LABEL_VALUE).getValue());
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

        LangLabelValueHelper help = new LangLabelValueHelper();

        List ls = help.getListRenderer(p, false);

        for (int i = 0; i < ls.size(); i++) {
            LangLabelValueModel m = (LangLabelValueModel) ls.get(i);
            Item id = container.addItem(m.getLangId());
            id.getItemProperty(COLUMN_FIELD_LANG_ID).setValue(m.getLangId());
            id.getItemProperty(COLUMN_FIELD_LABEL_ID).setValue(m.getLabelId());
            id.getItemProperty(COLUMN_FIELD_LABEL_VALUE).setValue(m.getLabelValue());
        }

        return container;
    }
}
