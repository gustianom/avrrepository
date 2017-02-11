package com.tenma.common.gui.main.setting.common;


import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.main.generalappprop.GeneralPropertyUtil;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by gustianom on 3/28/14.
 */
public class CommonSetting extends TenmaPanel {

    public Component selectedButton = null;
    public Component selectedForm = null;
    private Label labelTitle;
    private GeneralPropertyUtil propertyUtil;
    private VerticalLayout groupSetupLayout;
    private Label labelDescription;


    public CommonSetting() {

        propertyUtil = new GeneralPropertyUtil();

        labelTitle = new Label(param.getLabel("default.setting"));
//        labelTitle.setContentMode(ContentMode.HTML);
        labelTitle.setStyleName(ValoTheme.LABEL_H2);

        VerticalLayout socialMediaContent = new VerticalLayout();
        socialMediaContent.setStyleName("settingBox");
        socialMediaContent.setWidth(100, Unit.PERCENTAGE);

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setSpacing(true);
        layout.setMargin(true);

        layout.addComponent(labelTitle);
        layout.addComponent(socialMediaContent);

        layout.setComponentAlignment(labelTitle, Alignment.TOP_LEFT);
        layout.setComponentAlignment(socialMediaContent, Alignment.MIDDLE_LEFT);

        preparingBody(socialMediaContent);
        setCompositionRoot(layout);
//        groupSetupLayout.setMargin(true);
    }

    public final void setTitle(String title) {
        labelTitle.setValue(title);
    }

    private void preparingBody(VerticalLayout socialMediaBody) {

        labelDescription = new Label("description of setting");
        labelDescription.setContentMode(ContentMode.HTML);
        labelDescription.setPrimaryStyleName("rowContentDescription");

        VerticalLayout panelSocialMedia = createSocialPanel();

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vl.setMargin(true);
//        vl.addComponent(new Label());
        vl.addComponent(labelDescription);
        vl.addComponent(panelSocialMedia);
//        vl.addComponent(new Label());
        vl.setComponentAlignment(labelDescription, Alignment.TOP_LEFT);
        vl.setComponentAlignment(panelSocialMedia, Alignment.MIDDLE_LEFT);
        vl.setSizeFull();


//        Panel panel = new Panel();
//        panel.setWidth("100%");
//        panel.setContent(vl);

        socialMediaBody.addComponent(vl);
    }

    public final void setSettingDescription(String description) {
        labelDescription.setValue(description);
    }

    private VerticalLayout createSocialPanel() {
        groupSetupLayout = new VerticalLayout();
        groupSetupLayout.setWidth(100, Unit.PERCENTAGE);
        groupSetupLayout.setSpacing(true);
        return groupSetupLayout;
    }

    public final void addGroupSetting(CommonSettingContent setup) {
        groupSetupLayout.addComponent(setup);
    }


}
