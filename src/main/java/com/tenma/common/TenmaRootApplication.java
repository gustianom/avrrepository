package com.tenma.common;

import com.tenma.auth.model.CommunityModel;
import com.tenma.common.gui.main.TopPanelAction;
import com.tenma.common.util.Params;
import com.tenma.common.util.web.ClientInfo;
import com.vaadin.ui.UI;

import java.util.Locale;

/**
 * Copyright@2013 - PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 8/8/13
 * Time: 1:28 AM
 */
public interface TenmaRootApplication {

    public String getServerFullHost();

    public String getRemoteAddress();

    public Params getParams();

    public TopPanelAction getTopPanel();

    public ClientInfo getClientInfo();

    public CommunityModel getCommunityModel();

    public void setCommunityModel(CommunityModel m);

    public TenmaRootApplication getTenmaApplication();

    public Locale getLocale();

    public UI getUI();

    public void setErrorFieldMessage(String errroFieldMessage);

}
