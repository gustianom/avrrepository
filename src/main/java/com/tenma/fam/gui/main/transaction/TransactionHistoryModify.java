package com.tenma.fam.gui.main.transaction;

import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.util.logon.CommunityHelper;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.currency.CurrencyHelper;
import com.tenma.common.gui.display.PriceListener;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.Constants;
import com.tenma.common.util.web.Items;
import com.tenma.fam.bean.jurnal.JournalDetailHelper;
import com.tenma.fam.bean.jurnal.JournalHeaderHelper;
import com.tenma.fam.bean.template.TransactionTemplateDetailHelper;
import com.tenma.fam.bean.template.TransactionTemplateHelper;
import com.tenma.fam.gui.main.transaction.template.MenuTemplate;
import com.tenma.fam.gui.main.transaction.template.TemplateClickListener;
import com.tenma.fam.gui.main.transaction.templatefield.TemplateTransactionField;
import com.tenma.model.fam.JournalDetailModel;
import com.tenma.model.fam.JournalHeaderModel;
import com.tenma.model.fam.TransactionTemplateDetailModel;
import com.tenma.model.fam.TransactionTemplateModel;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created on 29/10/15.
 */
public class TransactionHistoryModify extends TenmaMasterFormModify implements PriceListener, TemplateSelection, TemplateClickListener {

    private TenmaPanel container;
    private Label lTitle = new Label();
    private boolean addMode;
    private String templateId;
    private String journalHeaderNumber;
    private TemplateTransactionField selectTemplateField;
    private TextField amount;

    private TextArea trxDesc;
    private TextField ref;
    private PopupDateField trxDate;
    private PopupDateField entryDate;
    private PopupDateField postDate;
    private NativeSelect currency;
    private Button btnSave;
    private Button btnCancel;
    private boolean amountValid;
    private List templateDetailList;
    private Integer modifyMode;
    private String JournalHeaderNumber;
    private JournalHeaderModel journalHeaderModel;


    public TransactionHistoryModify(TenmaPanel container, int modifyMode) throws Exception {
        super(container, modifyMode);
        this.modifyMode = modifyMode;
        this.container = container;
        preparingUI();
    }

    public TransactionHistoryModify() {
        super(null, ADD_MODE);
        System.out.println("transaction.title");
        this.modifyMode = ADD_MODE;
        preparingUI();

    }

    private void preparingUI() {
        System.out.println("TransactionHistoryModify.preparingUI");
        getHeader().setVisible(false);
        setPriceValid(true);
        lTitle = new Label(param.getLabel("transaction.title"));
        selectTemplateField = new TemplateTransactionField(this);

        trxDesc = new TextArea();
        ref = new TextField();
        trxDate = new PopupDateField();
        Locale locale = TA.getCurrent().getLocale();
        Calendar cal = Calendar.getInstance(locale);
        trxDate.setValue(cal.getTime());
        trxDate.setDateFormat("dd-MMM-yyyy");
        trxDate.setWidth("150px");
        trxDate.setHeight("30");

        entryDate = new PopupDateField();
        entryDate.setEnabled(false);
        entryDate.setValue(cal.getTime());
        entryDate.setDateFormat("dd-MMM-yyyy");
        entryDate.setWidth("100");
        entryDate.setHeight("30");

        postDate = new PopupDateField();
        postDate.setValue(cal.getTime());
        postDate.setDateFormat("dd-MMM-yyyy");
        postDate.setWidth("100");
        postDate.setHeight("30");

        amount = new TextField();
        amount.setWidth("150px");
        amount.setImmediate(true);
        amount.setInputPrompt("0");

//        amount.addListener(new Listener() {
//            public void componentEvent(Event event) {
//                boolean isn = TenmaConverter.isNumber(amount.getValue().toString());
//                if (isn) {
//                    setPriceValid(true);
//                } else if (!isn) {
//                    System.out.println("isn nilainya nihhh --->>  = " + isn);
//                    setPriceValid(false);
//                }
//            }
//        });

        currency = new NativeSelect();
        List list;
        CurrencyHelper hlp = new CurrencyHelper();
        CommunityModel communityModel = new CommunityModel();
        CommunityHelper communityHelper = new CommunityHelper();
        communityModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        communityModel = communityHelper.getCommunityDetail(communityModel);
        if (communityModel.isMultipleCurrency()) {

            list = hlp.listCurrencyItems();
            if (list != null)
                for (Object aList : list) {
                    Items items = (Items) aList;
                    currency.addItem(items.getValue());
                    currency.setItemCaption(items.getValue(), items.getLabel());
                }
            currency.setValue(communityModel.getBasedCurrency());
        } else {
            list = hlp.listCurrencyItems();
            if (list != null)
                for (Object aList : list) {
                    Items items = (Items) aList;
                    currency.addItem(items.getValue());
                    currency.setItemCaption(items.getValue(), items.getLabel());
                }
            currency.setValue(communityModel.getBasedCurrency());
            currency.setEnabled(false);
        }

        btnCancel = getButCancel();
        btnSave = getButSave();

        FormLayout formLeft = new FormLayout();
        VerticalLayout vlRight = new VerticalLayout();

        selectTemplateField.setCaption(param.getLabel("template.name"));
        formLeft.addComponent(selectTemplateField);

        trxDesc.setCaption(param.getLabel("transaction.desc"));
        formLeft.addComponent(trxDesc);

        ref.setCaption(param.getLabel("default.reference"));
        formLeft.addComponent(ref);

        trxDate.setCaption(param.getLabel("default.transactiondate"));
        formLeft.addComponent(trxDate);

        HorizontalLayout hr = new HorizontalLayout();
        hr.setCaption(param.getLabel(getLabel("default.amount")));
        hr.addComponent(currency);
        hr.addComponent(amount);
        formLeft.addComponent(hr);

        HorizontalLayout hHeader = new HorizontalLayout();
        lTitle.setPrimaryStyleName("headerTitle");

        hHeader.addComponent(lTitle);
        hHeader.setWidth("800px");
        hHeader.setHeight("35px");
        hHeader.setStyleName("metro-slate-grey");

        HorizontalLayout hFooter = new HorizontalLayout();
        HorizontalLayout hBtnLay = new HorizontalLayout();
        hBtnLay.addComponent(btnSave);
        hBtnLay.addComponent(btnCancel);
        hFooter.addComponent(hBtnLay);
        hFooter.setComponentAlignment(hBtnLay, Alignment.MIDDLE_RIGHT);
        hFooter.setWidth("800px");
        hFooter.setHeight("75px");
        hFooter.setStyleName("metro-metro-grey");

        VerticalLayout customUI = new VerticalLayout();

        getPersonForm().setWidth("800px");
        getPersonForm().addComponent(hFooter);
        getPersonForm().setPrimaryStyleName("metro-layout");

        MenuTemplate menuTemplate = new MenuTemplate(this);
        vlRight.addComponent(menuTemplate);

        HorizontalLayout hzRoot = new HorizontalLayout(formLeft, vlRight);
        hzRoot.setWidth("100%");

        getPersonForm().addComponent(hzRoot);

        customUI.addComponent(hHeader);
        customUI.addComponent(getPersonForm());
        customUI.addComponent(hFooter);
        customUI.setComponentAlignment(hHeader, Alignment.TOP_CENTER);
        customUI.setComponentAlignment(getPersonForm(), Alignment.TOP_CENTER);
        customUI.setComponentAlignment(hFooter, Alignment.BOTTOM_CENTER);
        customUI.setMargin(new MarginInfo(true, true, false, true));

        getPersonForm().setSpacing(true);
        getPersonForm().setMargin(true);
        this.setContent(customUI);


        if (modifyMode == UPDATE_MODE) {
            lTitle = new Label(param.getLabel("update.transaction"));
            journalHeaderModel = (JournalHeaderModel) container.getSelectedObject();
            JournalHeaderHelper helper1 = new JournalHeaderHelper();
            JournalHeaderModel m = journalHeaderModel;
            m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
            m = helper1.getJournalHeaderDetail(m);
            trxDate.setValue(m.getTrxDate());
            postDate.setValue(m.getPostDate());
            entryDate.setEnabled(false);
            entryDate.setValue(cal.getTime());
            ref.setValue(m.getRef());
            trxDesc.setValue(m.getTrxDesc());
            TransactionTemplateHelper tempHelper = new TransactionTemplateHelper();
            TransactionTemplateModel tempModel = new TransactionTemplateModel();
            tempModel.setTemplateId(m.getTemplateId());
            tempModel.setCommunityId(m.getCommunityId());
            tempModel = tempHelper.getTemplateHeaderDetail(tempModel);
//            templateName.setValue(tempModel.getTemplateName());
            selectTemplateField.setTemplateId(m.getTemplateId());

            currency.setValue(communityModel.getBasedCurrency());
            DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(new Locale("fi"));
            df.applyPattern("#");
//            amount.setValue(df.format(m.getAmount()));
//            currency.setValue(m.getTrxCurr());
            setTemplateId(m.getTemplateId());
            setJournalHeaderNumber(m.getJournalHeaderNumber());
            TransactionTemplateDetailHelper helper = new TransactionTemplateDetailHelper();
            HashMap p = new HashMap();
            p.put("templateId", m.getTemplateId());
            p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
            templateDetailList = helper.getListRenderer(p, false);

            JournalDetailHelper jDHelp = new JournalDetailHelper();
            HashMap pJ = new HashMap();
            pJ.put("journalHeaderNumber", m.getJournalHeaderNumber());
            pJ.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
            List pJList = jDHelp.getListRenderer(pJ, false);
            Double amountDob = 0.0;
            for (int det = 0; det < pJList.size(); det++) {
                JournalDetailModel detModel = (JournalDetailModel) pJList.get(det);
                amountDob = detModel.getBaseAmount();
            }

            System.out.println("amountDob = " + amountDob);
            amount.setValue(String.valueOf(amountDob));
        }

    }


    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCatalogPriceId() {
        return journalHeaderNumber;
    }

    public void setCatalogPriceId(String journalHeaderNumber) {
        this.journalHeaderNumber = journalHeaderNumber;
    }

    public boolean isPriceValid() {
        return amountValid;
    }

    public void setPriceValid(boolean amountValid) {
        this.amountValid = amountValid;
    }

    public Integer getModifyMode() {
        return modifyMode;
    }

    public void setModifyMode(Integer modifyMode) {
        this.modifyMode = modifyMode;
    }

    public String getJournalHeaderNumber() {
        return JournalHeaderNumber;
    }

    public void setJournalHeaderNumber(String journalHeaderNumber) {
        JournalHeaderNumber = journalHeaderNumber;
    }

    @Override
    public void selectModel(TransactionTemplateModel selectedModel) {
        setTemplateId(selectedModel.getTemplateId());
        TransactionTemplateDetailHelper helper = new TransactionTemplateDetailHelper();
        HashMap p = new HashMap();
        p.put("templateId", selectedModel.getTemplateId());
        templateDetailList = helper.getListRenderer(p, false);
    }

    public void doCancel() {
        TA.getCurrent().goHome();
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton().equals(btnSave)) {
            System.out.println("do proses saving...");
            System.out.println("do proses saving...");
            System.out.println("do proses saving...");
            doSave();

        } else if (clickEvent.getButton().equals(btnCancel)) {
            System.out.println("do cancel...");
            System.out.println("do cancel...");
            System.out.println("do cancel...");
            doCancel();
        }
    }

    public void doSave() {
        String lc = getLogger().openLog();
        System.out.println("TransactionHistoryModify.doSave");
//        if (!isPriceValid() || selectTemplateField.getValue() == null || trxDesc.getValue() == "") {
        if (selectTemplateField.getValue() == null || trxDesc.getValue() == "") {
            Notification.show("You can't leave this field empty Or Format Failed!");
        } else {
            JournalHeaderHelper helper = new JournalHeaderHelper();
            JournalHeaderModel m = new JournalHeaderModel();
            m.setTemplateId(getTemplateId());
            m.setTrxDesc(trxDesc.getValue());
            m.setRef(ref.getValue());
            m.setTrxDate(trxDate.getValue());
            m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
            m.setStatus(0);
            m.setPostDate(postDate.getValue());
            m.setEntryDate(entryDate.getValue());

            HashMap p = new HashMap();
            TransactionTemplateDetailHelper templateHelper = new TransactionTemplateDetailHelper();
            p.put("templateId", getTemplateId());
            p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
            List listTemplateDetail = templateHelper.getListRenderer(p, false);
            List listDetail = new ArrayList();
            for (int detail = 0; detail < listTemplateDetail.size(); detail++) {
                TransactionTemplateDetailModel detailModel = (TransactionTemplateDetailModel) listTemplateDetail.get(detail);
                JournalDetailModel jDetailModel = new JournalDetailModel();
                jDetailModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                jDetailModel.setAccountId(detailModel.getAccountId());
                jDetailModel.setAccountName(detailModel.getAccountName());
                jDetailModel.setTrxDesc(detailModel.getTemplateDetailDesc());
                jDetailModel.setBalance(detailModel.getAccountType());
                jDetailModel.setTrxCurr(detailModel.getTemplateDetailCurr());
                if (amount.getValue() == "") {
                    jDetailModel.setOrgAmount(Double.valueOf(0));
                    jDetailModel.setBaseAmount(Double.valueOf(0));

//                    jDetailModel.setOrgAmount(amount.getAmount());
//                    jDetailModel.setBaseAmount(amount.getAmount());
                } else {
                    jDetailModel.setOrgAmount(Double.valueOf(amount.getValue()));
                    jDetailModel.setBaseAmount(Double.valueOf(amount.getValue()));

//                    jDetailModel.setOrgAmount(amount.getAmount());
//                    jDetailModel.setBaseAmount(amount.getAmount());
                }

                jDetailModel.setOrderNumber(detail + 1);
                listDetail.add(jDetailModel);
            }
            setAuditTrail(m);
            int res = 0;
            if (modifyMode == ADD_MODE) {
                try {
                    res = helper.insertNewJournalHeader(m, listDetail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    m.setJournalHeaderNumber(getJournalHeaderNumber());
                    m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                    res = helper.updateJournalHeaderTemplate(m, listDetail, templateDetailList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (res != 0) {
                if (modifyMode == ADD_MODE) {
                    getLogger().log(lc, TenmaLog.LOG_FOR_ADD, "ADD TransactionReguler CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());
                } else {
                    getLogger().log(lc, TenmaLog.LOG_FOR_UPDATE, "UPDATE TransactionReguler CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());

                }
                doBack2List();
            } else {
                Notification.show(param.getMessage("system.error.saving"), Notification.Type.ERROR_MESSAGE);
            }
        }

    }

    private void doBack2List() {
//        TransactionsList list = new TransactionsList(tenmaApplication);
//        TA.getCurrent().updateWorkingArea(list);
//        list.refreshUI();
    }


    @Override
    public void TemplateClickEvent(TransactionTemplateModel model) {
        selectTemplateField.setSelectedModel(model);
        System.out.println("selectTemplateField.getValue()  = " + ((TransactionTemplateModel) selectTemplateField.getValue()).getTemplateName());
    }

    @Override
    public void setPrice(Double val) {

    }
}
