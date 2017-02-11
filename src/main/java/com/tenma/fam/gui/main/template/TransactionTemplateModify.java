/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/19/14
 * Time: 3:10 PM
 */

package com.tenma.fam.gui.main.template;

import com.tenma.common.TA;

import com.tenma.common.gui.display.component.TenmaCurrency;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.Constants;
import com.tenma.common.util.sequence.SequenceTool;
import com.tenma.fam.bean.template.TransactionTemplateDetailHelper;
import com.tenma.fam.bean.template.TransactionTemplateHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.TransactionTemplateDetailModel;
import com.tenma.model.fam.TransactionTemplateModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionTemplateModify extends TenmaMasterFormModify {

    private final String ACCOUNT_ID = "accountId";
    private final String ACCOUNT_TYPE = "accountType";
    private final String ACCOUNT_NAME = "accountName";
    private final String TEMPLATE_DETAIL_ID = "templateDetailId";

    private int modifyMode;

    private Button addDetail;
    private Button updDetail;
    private Button delDetail;
    private TextArea templateDesc;
    private TextField templateName;
    private NativeSelect type;
    private NativeSelect currency;
    private TextField templateId;
    private TransactionTemplateList parent;
    private Item item;
    private TransactionTemplateDetailModel tTDM;

    private Table tableProperty = new Table();
    private TenmaTableContainer container;
    private TransactionTemplateModel model;
    private TransactionTemplateHelper helper = new TransactionTemplateHelper();
    private List<TransactionTemplateDetailModel> listTemplateDetail = new ArrayList();
    private List<TransactionTemplateDetailModel> listTemplateUpdateDetail = new ArrayList();


    public TransactionTemplateModify(TransactionTemplateList aparent, int modifyMode) {
        super(null, 0);
        setTitle("default.transactiontemplate");
        setModifyMode(modifyMode);
        this.model = (TransactionTemplateModel) aparent.getSelectedObject();

        this.parent = aparent;
        this.modifyMode = modifyMode;
        tTDM = new TransactionTemplateDetailModel();
        preparingUI();
        preparingDetail();
    }


    private void preparingUI() {
        addDetail = new Button(param.getLabel(Constants.LABEL_NEW), this);
        addDetail.setIcon(new ThemeResource("layouts/images/plus.png"));
        updDetail = new Button(param.getLabel(Constants.LABEL_UPDATE), this);
        updDetail.setIcon(new ThemeResource("layouts/images/16/pencil.png"));
        delDetail = new Button(param.getLabel(Constants.LABEL_DELETE), this);
        delDetail.setIcon(new ThemeResource("layouts/images/deletered.png"));
        templateDesc = new TextArea(param.getLabel("default.description"));
        templateDesc.focus();
        System.out.println("templateDesc.getValue() = " + templateDesc.getValue());
        templateDesc.setWidth("240");
        templateDesc.setHeight("50");
        templateId = new TextField();
        templateId.setVisible(false);
        templateName = new TextField(param.getLabel("template.name"));
        templateName.setRequired(true);
        tableProperty.setRequired(true);
        currency = new TenmaCurrency();
        currency.setCaption(param.getLabel("default.currency"));

        type = new NativeSelect();
        type.setCaption(param.getLabel("default.type"));
        type.setNullSelectionAllowed(false);
        type.addItem(Constants.TEMPLATE_TYPE.GNA.getValue());
        type.setItemCaption(Constants.TEMPLATE_TYPE.GNA.getValue(), "GNA");
        type.addItem(Constants.TEMPLATE_TYPE.REG.getValue());
        type.setItemCaption(Constants.TEMPLATE_TYPE.REG.getValue(), "REG");
        type.addItem(Constants.TEMPLATE_TYPE.OTHER.getValue());
        type.setItemCaption(Constants.TEMPLATE_TYPE.OTHER.getValue(), "OTHER");


        HorizontalLayout lyBtn = new HorizontalLayout();
        lyBtn.addComponent(addDetail);
        lyBtn.addComponent(updDetail);
        lyBtn.addComponent(delDetail);
        lyBtn.setSpacing(true);
        getPersonForm().addComponent(templateName);
        getPersonForm().addComponent(templateDesc);
        getPersonForm().addComponent(currency);
        getPersonForm().addComponent(type);
        getPersonForm().addComponent(lyBtn);
        getPersonForm().addComponent(tableProperty);

        if (getModifyMode() == TenmaMasterFormModify.ADD_MODE) {
            String seq = SequenceTool.getInstance().addZeroPad(SequenceTool.getInstance().getLastSequence(TA.getCurrent().getCommunityModel().getCommunityId(), "TMPX", false), 8);
            templateId.setValue(seq);
        } else {
            model = helper.getTemplateHeaderDetail(model);
            templateId.setValue(model.getTemplateId());
            templateName.setValue(model.getTemplateName());
            templateDesc.setValue(model.getTemplateDesc());
            type.setValue(model.getType() != null ? model.getType() : Constants.TEMPLATE_TYPE.GNA.getValue());
            HashMap p = new HashMap();
            TransactionTemplateDetailHelper helper = new TransactionTemplateDetailHelper();
            p.put("templateId", model.getTemplateId());
            p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
            listTemplateDetail = helper.getListRenderer(p, false);
            for (int i = 0; i < listTemplateDetail.size(); i++) {
                TransactionTemplateDetailModel m = listTemplateDetail.get(i);
                setAuditTrail(m);
                m.setOrderNumber(i + 1);
            }
            listTemplateUpdateDetail = helper.getListRenderer(p, false);
        }
    }


    private void preparingDetail() {
        container = new TenmaTableContainer();
        container.addContainerProperty(TEMPLATE_DETAIL_ID, Integer.class, 0);
        container.addContainerProperty(ACCOUNT_ID, String.class, "");
        container.addContainerProperty(ACCOUNT_NAME, String.class, "");
        container.addContainerProperty(ACCOUNT_TYPE, String.class, "");

        tableProperty.setContainerDataSource(container);
        tableProperty.commit();

        tableProperty.setColumnHeaders(
                param.getLabel("No"),
                param.getLabel("account.id"),
                param.getLabel("account.name"),
                param.getLabel("template.type")
        );
        tableProperty.setSelectable(true);
        tableProperty.setImmediate(true);

        tableProperty.setEditable(false);
        tableProperty.addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (valueChangeEvent.getProperty().getValue() != null) {
                    item = tableProperty.getItem(valueChangeEvent.getProperty().getValue());
                    Integer order = (Integer) item.getItemProperty(TEMPLATE_DETAIL_ID).getValue();
//                int iorder = Integer.parseInt(order);
                    for (int i = 0; i < listTemplateDetail.size(); i++) {
                        TransactionTemplateDetailModel dm = (TransactionTemplateDetailModel) listTemplateDetail.get(i);
                        if (dm.getOrderNumber() == order) {
                            tTDM = dm;
                        }
                    }
                }
            }

        });
        doRefreshData();
    }

    private void doRefreshData() {
        container.removeAllItems();
        container = collectData();
        tableProperty.setPageLength(tableProperty.getItemIds().size());
    }

    private TenmaTableContainer collectData() {
        List<TransactionTemplateDetailModel> list = new ArrayList();


        for (int i = 0; i < listTemplateDetail.size(); i++) {
            TransactionTemplateDetailModel m = listTemplateDetail.get(i);
            Object id = container.addItem();
            container.getContainerProperty(id, ACCOUNT_ID).setValue(m.getAccountId());
            container.getContainerProperty(id, ACCOUNT_NAME).setValue(m.getAccountName());
            container.getContainerProperty(id, ACCOUNT_TYPE).setValue(m.getAccountType() == 0 ? "D" : "C");
            container.getContainerProperty(id, TEMPLATE_DETAIL_ID).setValue(m.getOrderNumber());
        }

        return container;
    }


    public void addNewDetailJournal(TransactionTemplateDetailModel um) {
        if (um.getDirty()) {
            int index = 0;
            for (int i = 0; i < listTemplateDetail.size(); i++) {
                TransactionTemplateDetailModel m = (TransactionTemplateDetailModel) listTemplateDetail.get(i);
                if (m.getOrderNumber() == um.getOrderNumber()) {
                    index = i;
                    break;
                }
            }
//            System.out.println("index = " + index);
            setAuditTrail(um);
            listTemplateDetail.set(index, um);
            doRefreshData();
        } else {
            // add
            setAuditTrail(um);
            listTemplateDetail.add(um);
            doRefreshData();
        }
    }


    public int getNewJournalDetailId() {
        return container.getItemIds().size();
    }

    @Override
    public void doCancel() {
        TransactionTemplateList list = new TransactionTemplateList();
        TA.getCurrent().updateWorkingArea(list);
        list.refreshTable();
    }

    private int validation() {
        return 0;
    }

    @Override
    public void doSave() {
        int rs = validation();
        if (rs == 0) {
            if (checkRequired()) {
                TransactionTemplateHelper helper = new TransactionTemplateHelper();
                TransactionTemplateModel m = new TransactionTemplateModel();
                m.setTemplateId(templateId.getValue());
                m.setTemplateDesc(templateDesc.getValue());
                m.setCurrency((String) currency.getValue());
                m.setTemplateName(templateName.getValue());
                m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                m.setTenmaCustomId("");
                m.setType((Integer) type.getValue());
                setAuditTrail(m);
                if (getModifyMode() == TenmaMasterFormModify.ADD_MODE) {
                    try {
                        int res = helper.insertNewTemplateHeader(m, listTemplateDetail);
                        doCancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        int res = helper.updateTemplateHeader(m, listTemplateUpdateDetail, listTemplateDetail);
                        doCancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        if (clickEvent.getButton().equals(addDetail))
            doAddDetail();
        else if (clickEvent.getButton().equals(updDetail))
            doUpdDetail();
        else if (clickEvent.getButton().equals(delDetail))
            doDelDetail();


    }

    private void doDelDetail() {
        if (modifyMode == TenmaMasterFormModify.ADD_MODE) {
            if (tTDM != null) {
                for (int i = 0; i < listTemplateDetail.size(); i++) {
                    TransactionTemplateDetailModel dm = (TransactionTemplateDetailModel) listTemplateDetail.get(i);
                    if (dm.getOrderNumber() == tTDM.getOrderNumber()) {
                        listTemplateDetail.remove(i);
                        break;
                    }
                }
                System.out.println("\n\n\n\n DELETEEEEE  " + tTDM.getAccountId());
                doRefreshData();
            }
        } else {
            if (tTDM != null) {
                for (int i = 0; i < listTemplateDetail.size(); i++) {
                    TransactionTemplateDetailModel m = (TransactionTemplateDetailModel) listTemplateDetail.get(i);
                    if (m.getOrderNumber() == tTDM.getOrderNumber()) {
                        listTemplateDetail.remove(i);
                        m.setDelete(true);
                        break;
                    }
                }
                doRefreshData();
            }
        }
    }

    private void doUpdDetail() {
        TransactionTemplateDetailModel dm = null;
        for (int i = 0; i < listTemplateDetail.size(); i++) {
            TransactionTemplateDetailModel m = (TransactionTemplateDetailModel) listTemplateDetail.get(i);
            if (m.getOrderNumber() == tTDM.getOrderNumber()) {
                dm = m;
                break;
            }

        }
        TemplateDetailDialog dialog = new TemplateDetailDialog(this, dm);
        UI.getCurrent().getUI().addWindow(dialog);
    }

    private void doAddDetail() {
        TemplateDetailDialog dialog = new TemplateDetailDialog(this, null);
        UI.getCurrent().getUI().addWindow(dialog);
    }


    private Boolean checkRequired() {
        if (templateName.getValue() == null) {
            Notification.show(param.getLabel("default.templatenamecannotempty"), Notification.Type.TRAY_NOTIFICATION);
            return false;
        }
        if (listTemplateDetail.size() == 0) {
            Notification.show(param.getLabel("default.listtemplatecannotempty"), Notification.Type.TRAY_NOTIFICATION);
            return false;
        }
        return true;
    }
}
