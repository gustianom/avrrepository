package com.tenma.common.gui.main.eflyer;


import com.tenma.model.common.EflyerModel;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/17/2015
 * Time    : 10:39 AM
 * Project : udw
 * Package : share.tenma.common.gui.main.eflyer
 */
public interface IFlyer {
    public String getDataContent();

    public void setDataContent(String value);

    public EflyerModel getModel();
}
