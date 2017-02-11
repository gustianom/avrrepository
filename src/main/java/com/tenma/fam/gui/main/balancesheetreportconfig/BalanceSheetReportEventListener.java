package com.tenma.fam.gui.main.balancesheetreportconfig;

import com.tenma.model.fam.BalanceSheetModel;

/**
 * Created by tenma-01 on 06/01/16.
 */
public interface BalanceSheetReportEventListener {
    public void BoxSheetRemoveEventListener(BalanceSheetModel model);

    public void AddEventListener();
}
