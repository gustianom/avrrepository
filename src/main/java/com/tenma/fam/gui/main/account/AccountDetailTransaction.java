package com.tenma.fam.gui.main.account;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaConverter;
import com.tenma.fam.bean.jurnal.JournalDetailHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.AccountModel;
import com.tenma.model.fam.JournalDetailModel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ndwijaya on 12/17/2014.
 */
public class AccountDetailTransaction extends TenmaWindow {
    private static final String DTL_DATE = "date";
    private static final String DTL_DESC = "desc";
    private static final String DTL_AMOUNT = "amount";

    private Table table;
    //    private JournalDetailModel model;
    private AccountModel model;
    private TenmaTableContainer container = new TenmaTableContainer();

    public AccountDetailTransaction(AccountModel model) {
        super();
        setCaption(getLabel("bankaccount.detailtitle"));
        this.model = model;
        initTable();
        createUI();
        populateData();
        setClosable(true);
    }

    private void createUI() {
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(table);
        setContent(vl);
    }

    private void initTable() {
        table = new Table();
        container.addContainerProperty(DTL_DATE, String.class, "");
        container.addContainerProperty(DTL_DESC, String.class, "");
        container.addContainerProperty(DTL_AMOUNT, Double.class, 0);
        table.setContainerDataSource(container);
        table.commit();
        table.setColumnHeaders(param.getLabel("default.date"),
                param.getLabel("default.description"),
                param.getLabel("default.amount"));
        table.setColumnAlignment(DTL_AMOUNT, Table.Align.RIGHT);
    }

    private void populateData() {
        JournalDetailHelper helper = new JournalDetailHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put("accountId", model.getAccountId());
        List ls = helper.getListRenderer(map, false);
        fireUI(ls);
    }

    private void fireUI(List ls) {
        int counter = 0;
        for (int j = 0; j < ls.size(); j++) {
            JournalDetailModel m = (JournalDetailModel) ls.get(j);
            container.addItem(m.getJournalDetailNumber());
            counter++;
            container.getContainerProperty(m.getJournalDetailNumber(), DTL_DATE).setValue(TenmaConverter.dateToString(m.getTrxDate(), "dd-MMM-YYYY"));
            container.getContainerProperty(m.getJournalDetailNumber(), DTL_DESC).setValue(m.getTrxDesc());
            container.getContainerProperty(m.getJournalDetailNumber(), DTL_AMOUNT).setValue(m.getBalance().intValue() == 0 ? m.getBaseAmount() : -m.getBaseAmount());
        }
        table.setPageLength(counter == 0 ? 1 : counter);
    }

}
