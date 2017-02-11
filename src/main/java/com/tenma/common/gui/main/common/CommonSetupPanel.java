package com.tenma.common.gui.main.common;


import com.tenma.common.gui.display.TenmaPanel;
import com.vaadin.ui.*;

/**
 * Created by gustianom on 1/30/14.
 */
public abstract class CommonSetupPanel extends TenmaPanel {
    public Integer indexProcessed = 0;
    private VerticalLayout layout;
    private Panel panelContent;
    private Window windowCancelConfirm;
    private HorizontalLayout buttonArea;

    public CommonSetupPanel() {
        super();
        buttonArea = new HorizontalLayout();
        layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setSpacing(true);

        preparingUI();
        setCompositionRoot(layout);
        setSizeFull();
        setPrimaryStyleName("wizardBackGround");
    }

    public HorizontalLayout getButtonArea() {
        return buttonArea;
    }

    private void preparingUI() {
        preparingContent();
        layout.addComponent(buttonArea);
        layout.setComponentAlignment(buttonArea, Alignment.MIDDLE_CENTER);

    }

    private void preparingContent() {

        panelContent = new Panel(param.getLabel("signup.setupuser"));
        panelContent.setWidth("90%");
        panelContent.setStyleName("tenmaCardFrameView");

        HorizontalLayout hoz = new HorizontalLayout();
        hoz.setWidth("70%");
        hoz.setMargin(true);
        hoz.addComponent(panelContent);
        hoz.setComponentAlignment(panelContent, Alignment.MIDDLE_CENTER);

        VerticalLayout ver = new VerticalLayout();
        ver.setSpacing(true);
        ver.setMargin(true);
        ver.setSizeFull();
        ver.addComponent(hoz);
        ver.setComponentAlignment(hoz, Alignment.MIDDLE_CENTER);

        Label lb = new Label("");
        lb.setHeight("20px");
        layout.addComponent(lb);
        layout.addComponent(ver);
        doDisplayContent();
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
    }

    public abstract void doDisplayContent();

    public final void updateContent(String title, Component panel) {
        panelContent.setCaption(param.getLabel(title));
        panelContent.setContent(panel);

    }
}
