/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/19/14
 * Time: 3:10 PM
 */

package com.tenma.fam.gui.main.template;

import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.gui.display.TenmaMasterList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.template.TransactionTemplateHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.TransactionTemplateModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.List;

public class TransactionTemplateList extends TenmaMasterList {
    private static final String[] dbColumn = {"templateName", "templateDesc", "currency"};
    private final String TEMPLATE_NAME = "templateName";
    private final String CURRENCY = "currency";
    private final String TEMPLATE_DESC = "templateDesc";

    private TenmaTableContainer ic;

    public TransactionTemplateList() {
        super(dbColumn);
        setTitle("default.transactiontemplate");
        VerticalLayout ly = new VerticalLayout();
        ly.addComponent(getTableProperty());
        addListComponent(ly);
        getDeleteButton().setEnabled(true);

    }

    @Override
    public void performReloadForTableSort() {
        doRefreshData();
    }

    @Override
    public void doModify(TenmaPanel parentContainer, int update_mode) {
        TransactionTemplateModify modify = new TransactionTemplateModify(this, update_mode);
        TA.getCurrent().updateWorkingArea(modify);
    }


    @Override
    public void refreshTable() {
        doRefreshData();
    }


    @Override
    public void executingDataPreparation() {
        ic = new TenmaTableContainer();
        ic.addContainerProperty(TEMPLATE_NAME, String.class, "");
        ic.addContainerProperty(TEMPLATE_DESC, String.class, "");
        ic.addContainerProperty(CURRENCY, String.class, "");
        getTableProperty().setContainerDataSource(ic);
        getTableProperty().commit();

        getTableProperty().setColumnHeaders(
                getLabel("template.name"),
                getLabel("default.description"),
                getLabel("default.currency")
        );
        getTableProperty().setSelectable(true);
        getTableProperty().setImmediate(true);
        getTableProperty().setMultiSelect(false);
        getTableProperty().addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Item item = getTableProperty().getItem(valueChangeEvent.getProperty().getValue());
                if (item != null) {
                    TransactionTemplateModel ac = new TransactionTemplateModel();
                    ac.setTemplateName((String) item.getItemProperty(TEMPLATE_NAME).getValue());
                    ac.setTemplateId((String) valueChangeEvent.getProperty().getValue());
                    ac.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                    System.out.println("-------------------------\n\n\n template idnya adalah ---> " + ac.getTemplateId());
                    setSelectedObject(ac);
                } else {
                    System.out.println(getLabel("default.selectRowerr"));
                }

            }
        });
        doRefreshData();
    }

    @Override
    public void doSearch() {
        setPageIndex(0);
        doRefreshData();
    }

    private void doRefreshData() {
        ic.removeAllItems();
        ic = collectData();
    }

    private TenmaTableContainer collectData() {
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        TransactionTemplateHelper help = new TransactionTemplateHelper();

        List ls = loadPagingData(p, "templateId", Constants.SORTING_TYPE.ASCENDING, "TransactionTemplateList", help);
        for (int i = 0; i < ls.size(); i++) {
            TransactionTemplateModel m = (TransactionTemplateModel) ls.get(i);
            Item id = ic.addItem(m.getTemplateId());

            ic.getContainerProperty(m.getTemplateId(), TEMPLATE_NAME).setValue(m.getTemplateName());
            ic.getContainerProperty(m.getTemplateId(), TEMPLATE_DESC).setValue(m.getTemplateDesc());
            ic.getContainerProperty(m.getTemplateId(), CURRENCY).setValue(m.getCurrency());
        }

        return ic;
    }

    public void doPrint() {
        TransactionTemplateHelper help = new TransactionTemplateHelper();
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List list = help.getListRenderer(p, false);
        openReport(param.getLabel("templateHeader.title"), "template/templateHeader.jasper", list, null, Constants.REPORT_MIME_TYPES.PDF);
    }


    @Override
    public int doDeletion() throws Exception {
        TransactionTemplateModel m = (TransactionTemplateModel) getSelectedObject();
        TransactionTemplateHelper helper = new TransactionTemplateHelper();
        setAuditTrail(m);
        int res = helper.deleteTemplateHeader(m);
        if (res != 0) {
            String lc = getLogger().openLog();
            getLogger().log(lc, TenmaLog.LOG_FOR_DELETE, "Delete Template = " + TA.getCurrent().getCommunityModel().getCommunityId() + "/" + m.getTemplateId());

        }
        return res;
    }


}
