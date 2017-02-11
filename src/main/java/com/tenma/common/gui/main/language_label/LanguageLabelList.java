package com.tenma.common.gui.main.language_label;

import com.tenma.common.TA;
import com.tenma.common.bean.language_label.LanguageLabelHelper;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.custom.TenmaPanelCustomList;
import com.tenma.common.model.LanguageLabelModel;
import com.tenma.common.util.CommonPagingListener;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaBootLabel;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:42:32 ICT 2013
 */
public class LanguageLabelList extends TenmaPanelCustomList implements Button.ClickListener, CommonPagingListener {
    private final String COLUMN_FIELD_LABEL_ID = "labelId";
    private final String COLUMN_FIELD_LABEL_NAME = "labelName";
    private final String COLUMN_FIELD_LABEL_DESC = "labelDesc";
    private final String COLUMN_FIELD_LABEL_VALUE = "labelValue";

    private TenmaTableContainer container;
    private Table tableProperty;

    private Button rebuildBtn = new Button();


    public LanguageLabelList() {

        setHeaderCaption(new Label(param.getLabel("LanguageLabel.title")));
        setUI();
    }

    public Table getTableProperty() {
        return tableProperty;
    }


    private void setUI() {
        if (TA.getCurrent().isSuperAdmin()) {
            getAddButton().setVisible(true);
            getDeleteButton().setVisible(true);
        } else {
            getAddButton().setVisible(false);
            getDeleteButton().setVisible(false);
        }
        rebuildBtn.setIcon(new ThemeResource("layouts/images/metro/20/refresh.png"));
        rebuildBtn.setStyleName("circle");
        rebuildBtn.addClickListener(this);

        HorizontalLayout hLay = new HorizontalLayout();
        tableProperty = new Table();
        tableProperty.setSizeFull();


        gethButtonLay().addComponent(rebuildBtn, 0);

        preparingAreaSearch();
        Panel header = getHeaderPanel();
        HorizontalLayout hPaging = getCustomPaging();
        VerticalLayout vMain = new VerticalLayout();
        vMain.setWidth(90, Unit.PERCENTAGE);
        vMain.addComponent(header);
        vMain.addComponent(tableProperty);
        vMain.addComponent(hPaging);
        vMain.setMargin(new MarginInfo(true, false, false, false));
        hLay.addComponent(vMain);
        hLay.setWidth("100%");
        hLay.setComponentAlignment(vMain, Alignment.TOP_CENTER);

        setTableUI();
        this.setContent(hLay);
    }

    private void preparingAreaSearch() {
        getSearchText().addShortcutListener(new ShortcutListener("textSeacrList" + getId(), ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                if (target.equals(getSearchText())) {
                    doRefreshData();
                }
            }
        });
    }

    public void doModify(TenmaPanel parentContainer, int update_mode) {
        try {
            LanguageLabelModify modify = new LanguageLabelModify(this, update_mode);
            TA.getCurrent().updateWorkingArea(modify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableUI() {
        container = new TenmaTableContainer();

        container.addContainerProperty(COLUMN_FIELD_LABEL_ID, Integer.class, 0);
        container.addContainerProperty(COLUMN_FIELD_LABEL_NAME, String.class, "");
        container.addContainerProperty(COLUMN_FIELD_LABEL_DESC, String.class, "");
        container.addContainerProperty(COLUMN_FIELD_LABEL_VALUE, String.class, "");

        getTableProperty().setContainerDataSource(container);
        getTableProperty().setColumnCollapsingAllowed(true);
        getTableProperty().setColumnCollapsed(COLUMN_FIELD_LABEL_ID, true);
        getTableProperty().commit();

        getTableProperty().setColumnHeaders(
                param.getLabel("default.id"),
                param.getLabel("default.name"),
                param.getLabel("default.description"),
                param.getLabel("default.value"));
        getTableProperty().setSelectable(true);
        getTableProperty().setImmediate(true);
        getTableProperty().addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                LanguageLabelModel sel = new LanguageLabelModel();
                sel.setLabelId((Integer) event.getProperty().getValue());
                Item item = getTableProperty().getItem(sel.getLabelId());
                if (item != null) {
                    sel.setLabelId((Integer) item.getItemProperty(COLUMN_FIELD_LABEL_ID).getValue());
                    sel.setLabelName((String) item.getItemProperty(COLUMN_FIELD_LABEL_NAME).getValue());
                    sel.setLabelDesc((String) item.getItemProperty(COLUMN_FIELD_LABEL_DESC).getValue());

                    setSelectedObject(sel);
                }
            }
        });
        doRefreshData();
    }

    @Override
    public void refreshTable() {
        System.out.println("LanguageLabelList.refreshTable");
        //getTextSearch().setValue("");
        doRefreshData();
    }

    private void doRefreshData() {
        container.removeAllItems();
        container = collectData();
        getTableProperty().setPageLength(getTableProperty().getItemIds().size());
    }

    @Override
    public void doDeletion() throws Exception {
        LanguageLabelModel m = (LanguageLabelModel) getSelectedObject();
        setAuditTrail(m);
        LanguageLabelHelper help = new LanguageLabelHelper();
        int res = help.deleteLanguageLabel(m);
        refreshUI();
    }


    private void doSearch() {
        getCommonPaging().setPageIndex(0);
        doRefreshData();
    }


    private TenmaTableContainer collectData() {
        LanguageLabelHelper help = new LanguageLabelHelper();
        HashMap map = new HashMap();
        map.put("recordStatus", 0);
        map.put("communityId", TA.getCurrent().getCommunityModel().getCommunityId());
        map.put("sortOrder", "labelName");
        String inputKey = getSearchText().getValue();
        map.put("searchValue", "%" + inputKey.trim() + "%");

        List ls = getCommonPaging().loadPagingData(map, "labelName", Constants.SORTING_TYPE.ASCENDING.getValue(), "Language Label List", help);

        int max = 15;

        if (ls != null) {
            for (int i = 0; i < ls.size(); i++) {
                LanguageLabelModel m = (LanguageLabelModel) ls.get(i);

                StringBuffer labelValue = new StringBuffer();
                labelValue.append(m.getViewLabelValue1() != null && m.getViewLabelValue1().trim().length() != 0 ? m.getViewLabelValue1() : "");
                labelValue.append(m.getViewLabelValue2() != null && m.getViewLabelValue2().trim().length() != 0 ? new StringBuffer().append(labelValue.length() != 0 ? "," : "").append(m.getViewLabelValue2()) : "");
                labelValue.append(m.getViewLabelValue3() != null && m.getViewLabelValue3().trim().length() != 0 ? new StringBuffer().append(labelValue.length() != 0 ? "," : "").append(m.getViewLabelValue3()) : "");
                labelValue.append(m.getViewLabelValue4() != null && m.getViewLabelValue4().trim().length() != 0 ? new StringBuffer().append(labelValue.length() != 0 ? "," : "").append(m.getViewLabelValue4()) : "");

                Item id = container.addItem(m.getLabelId());
                id.getItemProperty(COLUMN_FIELD_LABEL_ID).setValue(m.getLabelId());
                id.getItemProperty(COLUMN_FIELD_LABEL_NAME).setValue(m.getLabelName());
                id.getItemProperty(COLUMN_FIELD_LABEL_DESC).setValue(m.getLabelDesc());
                id.getItemProperty(COLUMN_FIELD_LABEL_VALUE).setValue(labelValue.toString());
                if (ls.size() == 15) {
                    break;
                }


            }

            getTableProperty().setPageLength(ls.size());
        } else
            getTableProperty().setPageLength(1);

        return container;
    }

    private void rebuildLabel() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("LanguageLabelList.rebuildLabel");
        TenmaBootLabel.getInstance().rebuildPropertiLabel();
    }


    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        if (clickEvent.getButton().equals(rebuildBtn))
            rebuildLabel();
        else if (clickEvent.getButton().equals(getAddButton()))
            doModify(this, Constants.ITEM_CRUD.CREATE.getValue());
        else if (clickEvent.getButton().equals(getUpdateButton()))
            doModify(this, Constants.ITEM_CRUD.UPDATE.getValue());
        else if (clickEvent.getButton().equals(getSearchButton()))
            doSearch();

    }

    @Override
    public void refreshUI() {
        doRefreshData();

    }
}
