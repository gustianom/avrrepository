package com.tenma.common.gui.display.component;

import com.tenma.auth.model.CommunityModel;
import com.tenma.common.TA;
import com.tenma.common.bean.currency.CommunityCurrencyHelper;
import com.tenma.common.bean.currency.CurrencyHelper;
import com.tenma.common.util.web.Items;
import com.vaadin.ui.NativeSelect;

import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: sigit
 * Date: 4/14/13
 * Time: 5:49 PM
 */
public class TenmaCurrency extends NativeSelect {
    private List listCurrency;

    public TenmaCurrency() {
        try {
            prepareData();
            generateUI();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private void prepareData() throws Exception {
        CommunityModel m = new CommunityModel();
        String communitySession = TA.getCurrent().getCommunityModel().getParentCommunityId() == null ? TA.getCurrent().getCommunityModel().getCommunityId() : TA.getCurrent().getCommunityModel().getParentCommunityId();
        m.setCommunityId(communitySession);
        CommunityCurrencyHelper hlp = new CommunityCurrencyHelper();
        List listCommunityCur = hlp.listCurrencyCommunityItems(TA.getCurrent().getCommunityModel().getCommunityId());
        if (listCommunityCur == null || listCommunityCur.size() == 0) {
            CurrencyHelper curhelper = new CurrencyHelper();
            listCurrency = curhelper.listCurrencyItems();
        } else
            listCurrency = listCommunityCur;
    }

    private void generateUI() {
        boolean moreThanOne = listCurrency.size() >= 1;
        this.setEnabled(TA.getCurrent().getCommunityModel().isMultipleCurrency() || moreThanOne);
        for (Object aListCurrency : listCurrency) {
            Items t = (Items) aListCurrency;
            String curr = t.getValue().toString().trim();
            this.addItem(curr);
            this.setItemCaption(curr, curr);
        }
        this.setValue(TA.getCurrent().getCommunityModel().getBasedCurrency());
        this.setNullSelectionAllowed(false);
    }

    @Override
    public String getValue() {
        return (String) super.getValue();
    }
}
