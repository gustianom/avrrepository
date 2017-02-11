package com.tenma.common.gui.main;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by gustianom on 02/02/15.
 */
public abstract class TopPanelAction extends Panel {
    public abstract void updateCommunityTopContent();

    public abstract VerticalLayout getLayoutTemporaryDirectPrint();
}
