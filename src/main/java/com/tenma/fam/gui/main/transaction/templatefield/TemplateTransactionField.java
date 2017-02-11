package com.tenma.fam.gui.main.transaction.templatefield;

import com.tenma.common.TA;
import com.tenma.common.gui.display.component.AutoComplete;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.template.TransactionTemplateHelper;
import com.tenma.fam.gui.main.transaction.TemplateSelection;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.TransactionTemplateModel;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Yos on 24/4/15.
 */
public class TemplateTransactionField extends AutoComplete {
    public static final String BANK_ID = "bankID";
    public static final String BANK_NAME = "bankName";

    private TemplateSelection parent;


    private TransactionTemplateModel selectedModel;

    public TemplateTransactionField(TemplateSelection aparent) {
        this.parent = aparent;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(btn)) {
            resultset = getList(filterFld.getValue());
            setResultsTableValues();
            popupView.setPopupVisible(true);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        lookupTextId.setEnabled(enabled);
        btn.setEnabled(enabled);
    }

    private void setAccount(TransactionTemplateModel model) {
        selectedModel = model;
        lookupTextId.setValue(model.getTemplateName());
        parent.selectModel(model);
    }

    public TransactionTemplateModel getTransactionTemplate() {
        return selectedModel;
    }

    public void setTemplateId(String templateId) {
        System.out.println("bankId = " + templateId);
        TransactionTemplateHelper help = new TransactionTemplateHelper();

        TransactionTemplateModel p = new TransactionTemplateModel();
        p.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        p.setTemplateId(templateId);
        TransactionTemplateModel m = help.getTemplateHeaderDetail(p);
        selectedModel = m;
        this.lookupTextId.setValue(m.getTemplateName());
    }


    public void setSelectedModel(TransactionTemplateModel model) {
        selectedModel = model;
        lookupTextId.setValue(model.getTemplateName());
        parent.selectModel(selectedModel);
    }


    @Override
    protected void initTable() {
        resultsTable = new Table();
        resultsTable.setSelectable(true);
        container = new TenmaTableContainer();

        container.addContainerProperty(BANK_ID, String.class, 0);
        container.addContainerProperty(BANK_NAME, String.class, "");

        resultsTable.setContainerDataSource(container);
        resultsTable.commit();

        resultsTable.setWidth("300px");
        resultsTable.setImmediate(true);
        resultsTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        resultsTable.setVisibleColumns(new Object[]{BANK_NAME});
        resultsTable.addItemClickListener(
                new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent event) {
                        Item item = event.getItem();
                        tableRowSelected = true;
                        popupView.setPopupVisible(false);
                        filterFld.setValue("");
                        if (item != null) {
                            TransactionTemplateModel pcm = new TransactionTemplateModel();
                            pcm.setTemplateId(item.getItemProperty(BANK_ID).getValue().toString());
                            pcm.setTemplateName(item.getItemProperty(BANK_NAME).getValue().toString());
                            pcm.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                            setAccount(pcm);
                            setSelectedObject(pcm);
                        }
//
                    }
                }
        );

    }

    public void setSelectedObject(Object obj) {
        if (obj != null) {
            selectedModel = (TransactionTemplateModel) obj;
        }
    }

    @Override
    public void setResultsTableValues() {
        container.removeAllItems();

        for (int i = 0; i < resultset.size(); i++) {
            TransactionTemplateModel mdl = (TransactionTemplateModel) resultset.get(i);
            Item id = container.addItem(i);
            id.getItemProperty(BANK_ID).setValue(mdl.getTemplateId());
            id.getItemProperty(BANK_NAME).setValue(mdl.getTemplateName());
        }
        if (resultset.size() > 10)
            resultsTable.setPageLength(10);
        else
            resultsTable.setPageLength(resultset.size());
        resultsTable.setSizeFull();
        setSizeUndefined();
    }

    @Override
    public Object getValue() {
        return selectedModel;
    }

    @Override
    public boolean isValid() {
        boolean rs = false;
        if (selectedModel != null) rs = true;
        return rs;
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        super.validate();
        if (selectedModel != null) throw new Validator.InvalidValueException("Transaction Template is not selected");
    }

    @Override
    protected List<Object> getList(String inputKey) {
        TransactionTemplateHelper help = new TransactionTemplateHelper();
        HashMap map = new HashMap();
        Integer skip = new Integer(0);
        int pageSize = 20;
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "templateName");
        map.put(Constants.RECORDSELECT_SORTORDER, "ASCENDING");
        map.put("searchValue", "%" + inputKey.trim() + "%");
        map.put(Constants.RECORDSELECT_SKIP, skip);
        map.put(Constants.RECORDSELECT_MAX, pageSize);
        map.put("autoOnly", Constants.TEMPLATE_TRANSACTION_TYPE.MANUAL.getValue());
        List ls = help.getListRenderer(map, true);
        System.out.println("Size = " + ls.size() + " : " + inputKey);
        return ls;
    }

}
