package com.tenma.share.gui.display.component;

import com.tenma.common.util.web.TenmaReportUtility;

import java.util.HashMap;

/**
 * Created by rizt
 * on 12/03/15 - 15:24
 */
public interface ReportFormSelection {

    public TenmaReportUtility.ReportInterface doViewReport(HashMap parameterMap);
}
