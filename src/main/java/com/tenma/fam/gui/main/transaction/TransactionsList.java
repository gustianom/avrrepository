package com.tenma.fam.gui.main.transaction;

import com.tenma.common.TA;
import com.tenma.common.TenmaRootApplication;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.custom.TenmaPanelCustomList;
import com.tenma.common.util.CommonPagingListener;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.jurnal.JournalHeaderHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.JournalHeaderModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * User: derry.irmansyah
 * Date: 5/13/13
 * Time: 12:33 PM
 */
public class TransactionsList extends TenmaPanelCustomList implements Button.ClickListener, CommonPagingListener {
    private static final String[] dbColumn = {"journalHeaderNumber", "trxDate", "trxDesc", "trxCurr", "amount", "status", "updateStatus"};
    private final String TRX_DESC = "trxDesc";
    private final String STATUS = "status";
    private final String JOURNAL_HEADER_NUMBER = "journalHeaderNumber";
    private final String TRX_DATE = "trxDate";
    private final String TRX_CURR = "trxCurr";
    private final String AMOUNT = "amount";
    private final String UPDATE_STATUS = "updateStatus";

    private final HorizontalLayout hTitle = new HorizontalLayout();


    Object myObjectProperty;
    private JournalHeaderModel ac;
    private TenmaTableContainer ic;
    private Button viewReport;
    private boolean addMode;
    private Table table;

    public TransactionsList() {
        super();
        setHeaderCaption(new Label(param.getLabel("menu.transactionslist")));
        setUI();

    }

    private void setUI() {
        HorizontalLayout hLay = new HorizontalLayout();
        table = new Table();
        table.setSizeFull();
        preparingAreaSearch();
        Panel header = getHeaderPanel();
        HorizontalLayout hPaging = getCustomPaging();

        VerticalLayout vMain = new VerticalLayout();
        vMain.setWidth(90, Unit.PERCENTAGE);
        vMain.addComponent(header);
        vMain.addComponent(table);
        vMain.addComponent(hPaging);
        vMain.setMargin(new MarginInfo(true, false, false, false));
        hLay.addComponent(vMain);
        hLay.setWidth("100%");
        hLay.setComponentAlignment(vMain, Alignment.TOP_CENTER);

        this.setContent(hLay);
        preparingAreaSearch();
        setTableUI();
        doRefreshData();
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


    public void performReloadForTableSort() {
        doRefreshData();
    }

    public JournalHeaderModel getAc() {
        return ac;
    }

    public void setAc(JournalHeaderModel ac) {
        this.ac = ac;
    }

    public Object getMyObjectProperty() {
        return myObjectProperty;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }


    public void doModify(TenmaRootApplication tenmaApplication, TenmaPanel parentContainer, int update_mode) {
        if (update_mode == 2000) {
            if (getAc().getStatus() == 1) {
                Notification.show("Item can't Update");
            } else {
                try {
                    TransactionHistoryModify modify = new TransactionHistoryModify(this, update_mode);
                    UI.getCurrent().getUI().setContent(modify);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                TransactionHistoryModify modify = new TransactionHistoryModify(this, update_mode);
                UI.getCurrent().getUI().setContent(modify);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doDeletion() throws Exception {
        String lc = getLogger().openLog();
        JournalHeaderModel m = (JournalHeaderModel) getSelectedObject();
        setAuditTrail(m);
        JournalHeaderHelper help = new JournalHeaderHelper();
        m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        int res = help.deleteJournalHeader(m);
        doRefreshData();
        if (res != 0)
            getLogger().log(lc, TenmaLog.LOG_FOR_DELETE, "DELETE TransactionReguler CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());
    }

    @Override
    public void refreshTable() {
        doRefreshData();
    }


    public void configureToolbarAfter() {
        viewReport = new Button(param.getLabel("viewReport"), this);
//        getButtonArea().addComponent(viewReport);
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        if (clickEvent.getButton().equals(viewReport)) {
            doView();

        } else if (clickEvent.getButton().equals(getAddButton())) {
            try {
                TransactionHistoryModify modify = new TransactionHistoryModify(this, Constants.ITEM_CRUD.CREATE.getValue());
                UI.getCurrent().getUI().setContent(modify);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (clickEvent.getButton().equals(getUpdateButton())) {

            if (isRowSelected()) {
                try {
                    TransactionHistoryModify modify = new TransactionHistoryModify(this, Constants.ITEM_CRUD.UPDATE.getValue());
                    UI.getCurrent().getUI().setContent(modify);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (clickEvent.getButton().equals(getSearchButton())) {
            doRefreshData();
        }

    }


    public void doPrint() {

    }

    private void doView() {
    }


    public void setTableUI() {
        ic = new TenmaTableContainer();
        ic.addContainerProperty(JOURNAL_HEADER_NUMBER, String.class, "");
        ic.addContainerProperty(TRX_DATE, String.class, "");
        ic.addContainerProperty(TRX_DESC, String.class, "");
        ic.addContainerProperty(TRX_CURR, String.class, "");
        ic.addContainerProperty(AMOUNT, String.class, "");
        ic.addContainerProperty(STATUS, Integer.class, 0);
        ic.addContainerProperty(UPDATE_STATUS, Integer.class, 0);
        table.setContainerDataSource(ic);
        table.setColumnWidth(TRX_CURR, 120);
        table.commit();

        table.setColumnHeaders(
                getLabel("journal.journalHeaderNumber"),
                getLabel("default.date"),
                getLabel("journal.trxDesc"),
                getLabel("default.currency"),
                getLabel("default.amount"),
                getLabel("default.status"),
                getLabel("journal.updateStatus")

        );
        table.setColumnCollapsingAllowed(true);
        table.setColumnCollapsed(TRX_CURR, true);
        table.setColumnCollapsed(JOURNAL_HEADER_NUMBER, true);
        table.setColumnCollapsed(UPDATE_STATUS, true);
        table.setSelectable(true);
        table.setImmediate(true);
        table.setMultiSelect(false);
        table.addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Item item = table.getItem(valueChangeEvent.getProperty().getValue());
                if (item != null) {
                    ac = new JournalHeaderModel();
                    ac.setJournalHeaderNumber((String) item.getItemProperty(JOURNAL_HEADER_NUMBER).getValue());
                    ac.setTrxDesc((String) item.getItemProperty(TRX_DESC).getValue());
                    ac.setStatus((Integer) item.getItemProperty(UPDATE_STATUS).getValue());
                    ac.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                    setSelectedObject(ac);
                } else {
                    System.out.println("Null");
                }


            }
        });
        doRefreshData();
    }


    public void doSearch() {

        doRefreshData();
    }

    private void doRefreshData() {
        ic.removeAllItems();
        ic = collectData();
        table.setPageLength(table.getItemIds().size());
    }

    private TenmaTableContainer collectData() {
        HashMap p = new HashMap();
        p.put("communityId", TA.getCurrent().getCommunityModel().getCommunityId());
        p.put("templateId", "notNull");
        p.put("sortField", "entryDate");
        p.put("sortOrder", "DESCENDING");
        String inputKey = getSearchText().getValue();
        p.put("searchValue", "%" + inputKey.trim() + "%");
        System.out.println("From Common........###");
        System.out.println(" ****************** IS REMARK DUE UDW - SPLIT ***************");
        System.out.println(" ****************** IS REMARK DUE UDW - SPLIT ***************");
        System.out.println(" ****************** IS REMARK DUE UDW - SPLIT ***************");


        JournalHeaderHelper help = new JournalHeaderHelper();
        int totalSize = help.countTotalList(p);

        System.out.println("totalSize ---->" + totalSize);

        List ls = help.getListRendererTemplate(p, false);

        ls = getCommonPaging().loadPagingData(ls, " List ", false);
        getCommonPaging().setTotalDataRows(totalSize);

        for (int i = 0; i < ls.size(); i++) {
            JournalHeaderModel m = (JournalHeaderModel) ls.get(i);
            Item id = ic.addItem(m.getJournalHeaderNumber());

            SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy");
            id.getItemProperty(JOURNAL_HEADER_NUMBER).setValue(m.getJournalHeaderNumber());
            if (m.getTrxDate() != null) {

                id.getItemProperty(TRX_DATE).setValue(dt.format(m.getTrxDate()));
            }
            id.getItemProperty(TRX_DESC).setValue(m.getTrxDesc());
            id.getItemProperty(TRX_CURR).setValue(m.getTrxCurr());
            DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(new Locale("fi"));
            df.applyPattern("#.00");
            id.getItemProperty(AMOUNT).setValue(m.getBaseAmount() == null ? "0" : df.format(m.getBaseAmount()));
            id.getItemProperty(STATUS).setValue(m.getStatus());

        }
        table.setPageLength(ls.size());
        return ic;
    }


    @Override
    public void refreshUI() {
        doRefreshData();
    }
}



