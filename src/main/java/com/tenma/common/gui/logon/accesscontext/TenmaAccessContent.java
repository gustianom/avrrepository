package com.tenma.common.gui.logon.accesscontext;

import com.vaadin.ui.Layout;

/**
 * Created by gustianom on 2/25/14.
 */
public interface TenmaAccessContent {
    Layout generateLeftContent(boolean small);

    void refreshMenu(boolean favMenu, String menuGroup);

    void reset();
}
