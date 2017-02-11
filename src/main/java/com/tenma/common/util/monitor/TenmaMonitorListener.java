/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.common.util.monitor;

import com.tenma.model.common.TenmaMonitorModel;

/**
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/14/14
 * Time: 11:12 AM
 */
public interface TenmaMonitorListener {

    public void newPageLoad(TenmaMonitorModel page, String userId);

    public void newUserLogOn(String userId);

    public void newUserLogOff(String userId);
}
