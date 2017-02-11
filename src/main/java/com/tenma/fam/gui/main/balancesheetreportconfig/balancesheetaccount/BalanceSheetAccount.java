package com.tenma.fam.gui.main.balancesheetreportconfig.balancesheetaccount;

import com.tenma.common.TA;

import com.tenma.common.util.Constants;
import com.tenma.fam.bean.account.AccountHelper;
import com.tenma.model.fam.AccountModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma-01 on 07/01/16.
 */
public class BalanceSheetAccount extends Panel {
    VerticalLayout root = new VerticalLayout();
    VerticalLayout accountLayout = new VerticalLayout();
    //    HorizontalLayout rowAccountLayout = new HorizontalLayout();
    private List lsAccount = new ArrayList<>();
    private int col = 5;

    public BalanceSheetAccount() {
        setContent(accountLayout);
        accountLayout.setSizeFull();
        loadAccount();
        generateAccountUI();
    }

    private void loadAccount() {
        AccountHelper helper = new AccountHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put("max", 10);
        map.put("skip", 0);
        lsAccount = helper.getListRenderer(map, true);
//        for (int x= 0; x < 8; x++){
//            AccountModel m = new AccountModel();
//            System.out.println("x ----------------------->>>> = " + x);
//            m.setAccountId(String.valueOf(x));
//            m.setAccountName("KAS " + x);
//            lsAccount.add(m);
//        }

    }

    private void generateAccountUI() {
        System.out.println("BalanceSheetAccount.generateAccountUI");
        System.out.println("*****************************************************************");
        accountLayout.removeAllComponents();
        if (lsAccount.size() == 0) {
            // define only Add class item
            HorizontalLayout hz = new HorizontalLayout();
            accountLayout.addComponent(hz);
        } else {

            System.out.println("lsAccount.size() = " + lsAccount.size());
            System.out.println("lsAccount.size() = " + lsAccount.size());
            System.out.println("lsAccount.size() = " + lsAccount.size());
            int counter = 0;
            HorizontalLayout hz = new HorizontalLayout();
            accountLayout.addComponent(hz);
//            hz.setMargin(true);
            for (int i = 0; i < lsAccount.size(); i++) {
                AccountModel accountModel = (AccountModel) lsAccount.get(i);
                counter++;
                if (counter > col) {
                    counter = 0;
                    hz = new HorizontalLayout();
//                    hz.setMargin(true);
                    accountLayout.addComponent(hz);
                }

                AccountBoxItem item = new AccountBoxItem(accountModel);
//                item.addStudentHistoryRewardListener(this);
//                item.addClickListener(createListener());
                hz.addComponent(item);
            } // end for

//            counter++;
//            if (counter > col) {
//                counter = 0;
//                hz = new HorizontalLayout();
//                hz.setMargin(true);
//                accountLayout.addComponent(hz);
//            }
        }
    }

}
