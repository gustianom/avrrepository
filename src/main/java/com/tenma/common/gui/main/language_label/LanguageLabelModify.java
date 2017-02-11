package com.tenma.common.gui.main.language_label;

import com.tenma.auth.model.CommunityMemberModel;
import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.util.logon.CommunityHelper;
import com.tenma.common.TA;
import com.tenma.common.bean.community.CommunityMemberHelper;
import com.tenma.common.bean.lang_label_value.LangLabelValueHelper;
import com.tenma.common.bean.language_label.LanguageLabelHelper;
import com.tenma.common.bean.languages.LanguagesHelper;
import com.tenma.common.dao.lang_label_value.LangLabelValueDao;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormLayout;
import com.tenma.common.gui.factory.TenmaMasterNewFormModify;
import com.tenma.common.model.LangLabelValueModel;
import com.tenma.common.model.LanguageLabelModel;
import com.tenma.common.model.LanguagesModel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.CrudCode;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:42:32 ICT 2013
 */
public class LanguageLabelModify extends TenmaMasterNewFormModify {
    private final String COLUMN_FIELD_LANG_CODE = "langCode";
    private final String COLUMN_FIELD_LANG_NAME = "langName";
    private final String COLUMN_FIELD_LANG_VALUE = "langValue";
    private List<CommunityMemberModel> listCommunityMember;
    private boolean isviewSpecificCommunity = false;
    private Label lTitle;
    private LanguageLabelModel modifyModel;
    private LanguageLabelHelper helper = new LanguageLabelHelper();
    private TenmaTableContainer container;
    private Table tableProperty = new Table();
    private List listLanguage;
    private int langnumber = 0;

    private NativeSelect specificCommunitySelect = new NativeSelect();

    private TenmaMasterFormLayout formLayout;

    public LanguageLabelModify(TenmaPanel parentContainer, int modifyMode) throws Exception {
        super(parentContainer, modifyMode);
        getHeader().setVisible(false);
        tableProperty.setWidth("100%");
        formLayout = new TenmaMasterFormLayout();
        if (modifyMode == ADD_MODE) {
            modifyModel = new LanguageLabelModel();
            modifyModel.setLabelId(0);
            modifyModel.setLabelName("");
            modifyModel.setLabelDesc("");
        } else {
            modifyModel = (LanguageLabelModel) ((TenmaPanel) parentContainer).getSelectedObject();

            LanguageLabelModel tmp = new LanguageLabelModel();
            tmp.setLabelId(modifyModel.getLabelId());
            modifyModel = helper.getLanguageLabelDetail(tmp);
            if (modifyModel == null)
                throw new Exception(String.valueOf(CrudCode.READ));
        }
        LanguagesHelper hlp = new LanguagesHelper();
        listLanguage = hlp.getListRenderer(new HashMap(), false);

        BeanItem<LanguageLabelModel> bitem = new BeanItem<LanguageLabelModel>(modifyModel);
        formLayout.setItemDataSource(bitem);

        StringBuffer buf = new StringBuffer().append(getLabel("language.label.name")).append(" : ");
        TextField labelName = new TextField(buf.toString());
        labelName.setMaxLength(150);
        labelName.setRequired(true);


        StringBuffer bufdesc = new StringBuffer().append(getLabel("default.description")).append(" : ");
        TextField fieldDesc = new TextField(bufdesc.toString());
        fieldDesc.setMaxLength(50);

        formLayout.addComponent(labelName);
        formLayout.addComponent(fieldDesc);

        formLayout.getFieldGroup().bind(labelName, "labelName");
        formLayout.getFieldGroup().bind(fieldDesc, "labelDesc");

        populateSpecificCommunitySelection();

        lTitle = new Label(param.getLabel("LanguageLabel.title"));
        if (modifyMode != ADD_MODE) {
            labelName.setReadOnly(true);
            fieldDesc.focus();
        } else
            labelName.focus();
        preparingTable();
        populateLanguageLabel();
    }

    private void populateSpecificCommunitySelection() {
        isviewSpecificCommunity = false;


        CheckBox specificCommunity = new CheckBox();
        specificCommunity.addValueChangeListener(createSpecificCommunityListener());

        specificCommunitySelect.setCaption(getLabel("community.communityname"));
        specificCommunitySelect.setMultiSelect(false);
        specificCommunitySelect.setNullSelectionAllowed(false);
        specificCommunitySelect.setNewItemsAllowed(false);

        listCommunityMember = getListRegisteredCommunity();
        if (listCommunityMember != null && listCommunityMember.size() > 1)
            isviewSpecificCommunity = true;


        if (TA.getCurrent().isSuperAdmin())
            isviewSpecificCommunity = true;

        if (isviewSpecificCommunity) {
            VerticalLayout ly = new VerticalLayout();
            ly.setCaption(getLabel("specificCommunity"));
            ly.addComponent(specificCommunity);
            ly.addComponent(specificCommunitySelect);
            formLayout.addComponent(ly);

            if (listCommunityMember != null && listCommunityMember.size() != 0) {
                if (TA.getCurrent().isSuperAdmin()) {
                    specificCommunitySelect.addItem(Constants.SYSTEM);
                    specificCommunitySelect.setItemCaption(Constants.SYSTEM, "Default");
                }


                CommunityHelper communityHelper = new CommunityHelper();
                for (CommunityMemberModel m : listCommunityMember) {
                    CommunityModel tm = new CommunityModel();
                    tm.setCommunityId(m.getCommunityId());
                    tm = communityHelper.getCommunityDetail(tm);

                    Item item = specificCommunitySelect.addItem(m.getCommunityId());
                    specificCommunitySelect.setItemCaption(m.getCommunityId(), tm != null ? tm.getCommunityName() : getLabel("default.undefined"));
                }
            }
        }
        specificCommunitySelect.setVisible(false);
    }

    private List<CommunityMemberModel> getListRegisteredCommunity() {
        HashMap map = new HashMap();
        map.put("memberId", TA.getCurrent().getClientInfo().getClientMemberModel().getMemberId());

        CommunityMemberHelper hlp = new CommunityMemberHelper();
        List<CommunityMemberModel> ls = hlp.getListRenderer(map, false);
        return ls;
    }

    private Property.ValueChangeListener createSpecificCommunityListener() {

        Property.ValueChangeListener listener = new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (isviewSpecificCommunity) {
                    specificCommunitySelect.setVisible((Boolean) valueChangeEvent.getProperty().getValue());
                }
            }
        };

        return listener;
    }


    public Table getTableProperty() {
        return tableProperty;
    }

    private void preparingTable() {
        container = new TenmaTableContainer();

        container.addContainerProperty(COLUMN_FIELD_LANG_CODE, String.class, "");
        container.addContainerProperty(COLUMN_FIELD_LANG_NAME, String.class, "");
        container.addContainerProperty(COLUMN_FIELD_LANG_VALUE, String.class, "");

        getTableProperty().setContainerDataSource(container);
        getTableProperty().commit();

        getTableProperty().setColumnHeaders(new String[]{
                        param.getLabel("language.code"),
                        param.getLabel("language.name"),
                        param.getLabel("language.labelValue")
                }
        );

        getTableProperty().setColumnWidth(COLUMN_FIELD_LANG_CODE, 120);
        getTableProperty().setColumnWidth(COLUMN_FIELD_LANG_NAME, 220);

        getTableProperty().setSelectable(true);
        getTableProperty().setImmediate(true);
        getTableProperty().setTableFieldFactory(new TableFieldFactory() {
            //            @Override
            public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
                Field field = null;
                if (COLUMN_FIELD_LANG_CODE.equals(propertyId)) {
                    field = new TextField((String) propertyId);
                    field.setReadOnly(true);
                    field.setWidth("50px");
                } else if (COLUMN_FIELD_LANG_NAME.equals(propertyId)) {
                    field = new TextField((String) propertyId);
                    field.setReadOnly(true);
                    field.setWidth("120px");
                } else {
                    TextArea area = new TextArea((String) propertyId);
                    area.setData(itemId);
                    area.setWidth("100%");
                    area.setHeight("20%");
                    area.setMaxLength(200);
                    field = area;
                }
                return field;
            }
        });
        tableProperty.setEditable(true);
        doRefreshData();
    }

    private void doRefreshData() {
        container.removeAllItems();
        container = collectData();
    }

    private TenmaTableContainer collectData() {
        if (listLanguage != null)
            langnumber = listLanguage.size();
        for (int i = 0; i < listLanguage.size(); i++) {
            LanguagesModel m = (LanguagesModel) listLanguage.get(i);
            String val = "";
            if (getModifyMode() != ADD_MODE) {
                LangLabelValueModel tmp = new LangLabelValueModel();
                tmp.setLangId(m.getLangId());
                tmp.setLabelId(modifyModel.getLabelId());
                LangLabelValueHelper valueHelper = new LangLabelValueHelper();
                tmp = valueHelper.getLangLabelValueDetail(tmp);
                if (tmp != null)
                    val = tmp.getLabelValue();
            }
            Item id = container.addItem(m.getLangId());
            id.getItemProperty(COLUMN_FIELD_LANG_CODE).setValue(m.getLangCode());
            id.getItemProperty(COLUMN_FIELD_LANG_NAME).setValue(m.getLangName());
            id.getItemProperty(COLUMN_FIELD_LANG_VALUE).setValue(val);
        }

        return container;
    }

    private void populateLanguageLabel() {
        VerticalLayout ly = new VerticalLayout();
//        ly.setWidth("800px");
        ly.setPrimaryStyleName("metro-layout");


        formLayout.getComponent(0).setVisible(false);
        ly.addComponent(formLayout);
        ly.addComponent(tableProperty);
        tableProperty.setPageLength(langnumber);
        formLayout.setWidth(90, Unit.PERCENTAGE);
        tableProperty.setWidth(90, Unit.PERCENTAGE);
//        ly.setSizeFull();


        HorizontalLayout hHeader = getCustomHeader(lTitle);
        HorizontalLayout hFooter = getCustomFooterArea();

        VerticalLayout customUI = new VerticalLayout();

        ly.setMargin(new MarginInfo(false, false, true, false));
        customUI.addComponent(hHeader);
        customUI.addComponent(ly);
        customUI.addComponent(hFooter);

        customUI.setComponentAlignment(hHeader, Alignment.TOP_CENTER);
        customUI.setComponentAlignment(ly, Alignment.TOP_CENTER);
        customUI.setComponentAlignment(hFooter, Alignment.BOTTOM_CENTER);


        customUI.setMargin(true);
        this.setUIForm(customUI);

    }

    public void doCancel() {
        doBack2List();
    }

    public void doSave() {
        formLayout.commit();
        setAuditTrail(modifyModel);
        int res = -1;
        SqlSession session = helper.sqlSessionFactory.openSession();


        String selCommId = null;
        if (isviewSpecificCommunity) {
            selCommId = (String) specificCommunitySelect.getValue();
            if (Constants.SYSTEM.equals(selCommId))
                selCommId = null;
        } else
            selCommId = TA.getCurrent().getCommunityModel().getCommunityId();

        try {
            if (ADD_MODE == getModifyMode()) {
                //check existing with recordstatus all
                LanguageLabelModel m = new LanguageLabelModel();
                m.setLabelName(modifyModel.getLabelName());
                m = helper.getLanguageLabelUnFlagStatus(m);
                if (m != null) {
                    m.setRecordStatus(0);
                    setAuditTrail(m);
                    res = helper.updateLanguageLabel(session, m);
                    modifyModel.setLabelId(m.getLabelId());
                } else
                    res = helper.insertNewLanguageLabel(session, modifyModel);
            } else
                res = helper.updateLanguageLabel(session, modifyModel);

            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("LanguageLabelModify.doSave " + modifyModel.getLabelName());
            int countLength = 0;
            if (res != 0) {
                if (listLanguage != null) {
                    LangLabelValueHelper valueHelper = new LangLabelValueHelper();
                    for (int i = 0; i < listLanguage.size(); i++) {
                        LanguagesModel m = (LanguagesModel) listLanguage.get(i);
                        Item id = container.getItem(m.getLangId());
                        LangLabelValueModel tmp = new LangLabelValueModel();
                        tmp.setLangId(m.getLangId());
                        tmp.setLabelId(modifyModel.getLabelId());
                        tmp.setLabelName(modifyModel.getLabelName());
                        tmp.setCommunityId(selCommId);

                        Object o = id.getItemProperty(COLUMN_FIELD_LANG_VALUE).getValue();
                        tmp.setLabelValue(o == null ? "" : (String) o);

                        if (tmp.getLabelValue().trim().length() != 0) {
                            setAuditTrail(tmp);
                            countLength++;
                            int rs = doCheckOnUpdate(valueHelper, session, tmp);
                            if (rs == 0)
                                valueHelper.insertNewLangLabelValue(session, tmp);
                        }
                    }
                }
            }
            if (countLength == 0)
                throw new Exception("You must enter all the language label");
            session.commit();
            doBack2List();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            commonMessageHandler(Notification.Type.ERROR_MESSAGE, ADD_MODE == getModifyMode() ? CrudCode.CREATE : CrudCode.UPDATE, e.getMessage(), param.getMessage("system.error"));
        } finally {
            session.close();
        }

    }


    private int doCheckOnUpdate(LangLabelValueHelper valueHelper, SqlSession session, LangLabelValueModel model) throws Exception {
        boolean isContinue2Update = false;

        int result = 0;

        if (model.getLabelName().toLowerCase().startsWith("menu."))
            isContinue2Update = true;
        else {
            LangLabelValueDao dao = new LangLabelValueDao(session);


            HashMap map = new HashMap();
            map.put("langId", model.getLangId());
            map.put("labelValue", model.getLabelValue());
            if (model.getCommunityId() != null && model.getCommunityId().trim().length() != 0)
                map.put("communityId", model.getCommunityId());

            List<LangLabelValueModel> ls = dao.listAvailableLangLabelValueMap(map, false);
            if (ls != null && ls.size() != 0) {
                isContinue2Update = true;
            } else {
                isContinue2Update = false;
            }
//                LangLabelValueModel m = null;
//                for (LangLabelValueModel mdl : ls)
//                    if (mdl.getLangId().equals(model.getLangId()) && mdl.getLabelId().equals(model.getLabelId()))
//                        m = mdl;
//
//                if (m != null) //current label
//                    isContinue2Update = true;
//                else {
//                    m = ls.get(0);
//                    if (m.getLabelName().toLowerCase().startsWith("menu."))
//                        isContinue2Update = true;
//                    else {
//                        result = ls.size();
//                        StringBuffer err = new StringBuffer()
//                                .append("Value has already used by other labelName [").append(m.getLangId()).append(",").append(m.getLabelName()).append("]");
//                        throw new Exception(err.toString());
//                    }
//                }
//            } else
//                isContinue2Update = true;
        }

        if (isContinue2Update)
            result = valueHelper.updateLangLabelValue(session, model);
        return result;
    }

    private void doBack2List() {
        LanguageLabelList list = (LanguageLabelList) getParentContainer();
        TA.getCurrent().updateWorkingArea(list);
        list.refreshTable();
        ((LanguageLabelList) getParentContainer()).refreshUI();

    }
}
