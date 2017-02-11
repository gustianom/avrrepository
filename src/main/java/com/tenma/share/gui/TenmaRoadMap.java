package com.tenma.share.gui;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by ndwijaya on 11/8/14.
 */
public class TenmaRoadMap extends HorizontalLayout {
    public static final int FIRST = 0;
    public static final int MID = 1;
    public static final int LAST = 2;
    private boolean empty = true;

    public TenmaRoadMap() {
        empty = true;
    }

    public void register(Button btn, int position) {
        switch (position) {
            case FIRST:
                btn.setPrimaryStyleName("b1");
                break;
            case MID:
                btn.setPrimaryStyleName("b2");
                break;
            case LAST:
                btn.setPrimaryStyleName("b3");
                break;
        }
        addComponent(btn);
        empty = false;
    }

    public boolean isEmpty() {
        return empty;
    }
}
