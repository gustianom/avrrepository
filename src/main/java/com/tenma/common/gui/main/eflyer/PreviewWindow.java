package com.tenma.common.gui.main.eflyer;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Window;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/27/2015
 * Time    : 10:17 AM
 * Project : udw
 * Package : share.tenma.common.gui.main.eflyer
 */
public class PreviewWindow extends Window {
    public PreviewWindow(String url) {
        this.setCaption("PREVIEW");
        createUI(url);
    }

    private void createUI(String url) {
        BrowserFrame bf = new BrowserFrame("", new ExternalResource(url));
        bf.setSizeFull();
        setContent(bf);
        setSizeFull();
    }
}
