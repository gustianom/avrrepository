package com.tenma.common.gui.main.setting.common;

import com.tenma.common.TA;
import com.tenma.common.gui.display.component.ConfirmationDialog;
import com.tenma.common.gui.factory.TenmaMasterFormLayout;
import com.tenma.common.gui.main.generalappprop.GeneralPropertyUtil;
import com.tenma.common.util.Constants;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;

/**
 * Created by gustianom on 3/28/14.
 */
public abstract class CommonSettingContent extends Panel implements Button.ClickListener {
    private final CommonSetting mediaSetting;
    private final VerticalLayout socialMediaList;
    private final Button btnAdd;
    private final Label contentTitle;
    public GeneralPropertyUtil propertyUtil;
    private Window window;

    public CommonSettingContent(CommonSetting mediaSetting, String title, boolean allowAdd) {
        this.mediaSetting = mediaSetting;
        propertyUtil = new GeneralPropertyUtil();


        contentTitle = new Label(title);

        btnAdd = new Button(TA.getCurrent().getParams().getLabel(Constants.LABEL_NEW));
        btnAdd.setStyleName(BaseTheme.BUTTON_LINK);
        btnAdd.setVisible(allowAdd);
        btnAdd.addClickListener(this);

        HorizontalLayout ly = new HorizontalLayout(contentTitle, btnAdd);
        ly.setComponentAlignment(contentTitle, Alignment.MIDDLE_LEFT);
        ly.setComponentAlignment(btnAdd, Alignment.MIDDLE_RIGHT);
        ly.setWidth("100%");

        socialMediaList = new VerticalLayout();
        socialMediaList.setWidth(80, Unit.PERCENTAGE);
        socialMediaList.setStyleName("");

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setSpacing(true);

        layout.addComponent(ly);
        layout.addComponent(socialMediaList);

        layout.setComponentAlignment(ly, Alignment.TOP_LEFT);
        layout.setComponentAlignment(socialMediaList, Alignment.MIDDLE_RIGHT);

        setWidth("100%");
        setContent(layout);
    }

    public final void addSettingContent(Object model, int uiType, boolean includeDelete) {
        VerticalLayout form = preparingSocialMediaContent(model, MODIFY_MODE.UPDATE, uiType, includeDelete);
        form.setWidth("100%");

        Panel p = new Panel(form);
        p.setWidth("100%");
        socialMediaList.addComponent(p);
    }

    public final void addSettingContent(String titleContent, Object model, int uiType, boolean includeDelete) {
        Label tContent = new Label(titleContent);
        VerticalLayout form = preparingSocialMediaContent(model, MODIFY_MODE.UPDATE, uiType, includeDelete);
        form.setWidth("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout(tContent, form);
        horizontalLayout.setSpacing(true);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setComponentAlignment(tContent, Alignment.TOP_LEFT);
        horizontalLayout.setComponentAlignment(form, Alignment.TOP_CENTER);
        horizontalLayout.setExpandRatio(tContent, 1f);
        horizontalLayout.setExpandRatio(form, 2f);
        Panel p = new Panel(horizontalLayout);
        p.setWidth("100%");
        socialMediaList.addComponent(p);
    }

    private VerticalLayout preparingSocialMediaContent(final Object model, MODIFY_MODE modifyMode, int uiType, boolean includeDelete) {

        Label contentDetailTitle = new Label(getModuleTitle(model, uiType));
        Label contentIcon = new Label();
        contentIcon.setIcon(new ThemeResource("layouts/images/ebfok.png"));
        contentIcon.setVisible(false);

        final HorizontalLayout layoutRowHeader = new HorizontalLayout();
        layoutRowHeader.setWidth("100%");
        layoutRowHeader.setSpacing(true);

        final VerticalLayout vform = preparingSocialMediaDetailForm(model, modifyMode, uiType);
        vform.setVisible(false);
        vform.setWidth("90%");


        Button btnDelete = new Button(TA.getCurrent().getParams().getLabel(Constants.LABEL_DELETE));
        if (includeDelete) {
            btnDelete.setVisible(true);
            btnDelete.setStyleName(BaseTheme.BUTTON_LINK);
            btnDelete.setIcon(new ThemeResource("layouts/images/deletered.png"));
            btnDelete.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    doConfirmDeletionObject(model);
                }
            });
        } else
            btnDelete.setVisible(false);


        Button btnEdit = new Button(TA.getCurrent().getParams().getLabel(Constants.LABEL_UPDATE));
        btnEdit.setStyleName(BaseTheme.BUTTON_LINK);
        btnEdit.setIcon(new ThemeResource("layouts/images/16/pencil.png"));
        btnEdit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (mediaSetting.selectedButton != null) {
                    mediaSetting.selectedButton.setVisible(true);
                    mediaSetting.selectedForm.setVisible(false);
                }
                mediaSetting.selectedButton = layoutRowHeader;
                mediaSetting.selectedForm = vform;
                mediaSetting.selectedButton.setVisible(false);
                mediaSetting.selectedForm.setVisible(true);

            }
        });

        HorizontalLayout layButon = new HorizontalLayout(btnEdit);
        if (includeDelete)
            layButon.addComponent(btnDelete);

        HorizontalLayout layTitle = new HorizontalLayout(contentDetailTitle, new Label(" "), contentIcon);
        layTitle.setComponentAlignment(contentDetailTitle, Alignment.MIDDLE_LEFT);
        layTitle.setComponentAlignment(contentIcon, Alignment.MIDDLE_RIGHT);

        layoutRowHeader.addComponent(layTitle);
        layoutRowHeader.addComponent(layButon);

        VerticalLayout vl = new VerticalLayout(layoutRowHeader, vform);
        vl.setWidth("100%");
        return vl;
    }

    private void doConfirmDeletionObject(final Object model) {
        UI.getCurrent().getUI().addWindow(new ConfirmationDialog(
                mediaSetting.getLabel("default.active"),
                TA.getCurrent().getParams().getMessage("delete.confirm"),
                mediaSetting.getLabel("default.yes"),
                mediaSetting.getLabel("default.no"),
                new ConfirmationDialog.Callback() {
                    public void onDialogResult(boolean okChoose) {
                        if (okChoose) {
                            int res = 0;
                            try {
                                if (doDelete(model)) {
                                    socialMediaList.removeAllComponents();
                                    doRefreshContent();
                                    Notification.show(TA.getCurrent().getParams().getMessage("system.info.success"), Notification.Type.TRAY_NOTIFICATION);
                                }
                            } catch (Exception e) {
                                Notification.show(TA.getCurrent().getParams().getMessage("system.error.delete"), Notification.Type.TRAY_NOTIFICATION);
                                e.printStackTrace();
                            }
                        }
                    }
                }));
    }

    public boolean doDelete(Object model) throws Exception {
        //need to override this method when activate deletion
        throw new UnsupportedOperationException("need to override doDelete method, when activate deletion");
    }

    private VerticalLayout preparingSocialMediaDetailForm(final Object model, final MODIFY_MODE modifyMode, final int uiType) {
//        BeanItem<Object> beanItem = new BeanItem<Object>(model);
//        final TenmaMasterFormLayout formLayout = new TenmaMasterFormLayout();
//        formLayout.setItemDataSource(beanItem);

        final TenmaMasterFormLayout formLayout = preparingSocialMediaForm(model, uiType);

        final VerticalLayout layoutForm = new VerticalLayout();
        layoutForm.setWidth("100%");
        layoutForm.setSpacing(true);

        Button btnSave = new Button(TA.getCurrent().getParams().getLabel(Constants.LABEL_SAVE));
        Button btnCancel = new Button(TA.getCurrent().getParams().getLabel(Constants.LABEL_CANCEL));
        btnCancel.setImmediate(true);
        btnSave.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                preparingSave(formLayout, modifyMode, uiType);
            }
        });

        btnCancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                doClearForm(null, modifyMode, uiType, false);
            }
        });

        HorizontalLayout hl = new HorizontalLayout(btnSave, btnCancel);

        String titleMedia = null;
        if (!MODIFY_MODE.ADD_NEW.equals(modifyMode))
            titleMedia = new StringBuffer().append(mediaSetting.getLabel(Constants.LABEL_UPDATE)).append(" ").append(getModuleTitle(model, uiType)).toString();

        Panel panelModify = new Panel(titleMedia);
        panelModify.setWidth("100%");
        panelModify.setContent(formLayout);

        layoutForm.addComponent(new Label(" "));
        layoutForm.addComponent(panelModify);
        layoutForm.addComponent(hl);
        layoutForm.addComponent(new Label(" "));


        return layoutForm;
    }

    private void preparingSave(TenmaMasterFormLayout formLayout, MODIFY_MODE modifyMode, int uiType) {
        formLayout.doUpdateFormStatus("");
        if (formLayout.commit()) {
            try {
                BeanItem<Object> itm = (BeanItem<Object>) formLayout.getFieldGroup().getItemDataSource();
                Object modifyModel = itm.getBean();

                if (doSave(modifyModel, modifyMode, uiType)) {
                    doClearForm(modifyModel, modifyMode, uiType, true);

                    String addTitle = "system.info.update";
                    if (MODIFY_MODE.ADD_NEW.equals(modifyMode)) addTitle = "system.info.add";

                    Notification.show(TA.getCurrent().getParams().getMessage(addTitle), TA.getCurrent().getParams().getMessage("system.info.success"), Notification.Type.TRAY_NOTIFICATION);
                }


            } catch (Exception e) {
                e.printStackTrace();
                formLayout.doUpdateFormStatus(e.getMessage());
            }
        }
    }

    private void doClearForm(Object modifyModel, MODIFY_MODE modifyMode, int uiType, boolean isFromSaveButton) {
        if (isFromSaveButton) {
            if (MODIFY_MODE.ADD_NEW.equals(modifyMode)) {
                socialMediaList.removeAllComponents();
                doRefreshContent();
                if (window != null) {
                    UI.getCurrent().removeWindow(window);
                    window.close();
                    window = null;
                }
            } else if (MODIFY_MODE.UPDATE.equals(modifyMode)) {
                mediaSetting.selectedButton.setVisible(true);
                HorizontalLayout hl = (HorizontalLayout) mediaSetting.selectedButton;

                HorizontalLayout htitle = (HorizontalLayout) hl.getComponent(0);
                Label blIcon = (Label) htitle.getComponent(2);
                Label lbTitle = (Label) htitle.getComponent(0);
                lbTitle.setValue(getModuleTitle(modifyModel, uiType));
                blIcon.setVisible(true);
                mediaSetting.selectedForm.setVisible(false);
            }
        } else {
            if (mediaSetting.selectedForm != null)
                mediaSetting.selectedForm.setVisible(false);
            if (mediaSetting.selectedButton != null)
                mediaSetting.selectedButton.setVisible(true);

            if (window != null) {
                UI.getCurrent().removeWindow(window);
                window.close();
                window = null;
            }
        }
    }

    public void doRefreshContent() {

    }

    protected abstract boolean doSave(Object modifyModel, MODIFY_MODE modifyMode, int uiType) throws Exception;

    public abstract TenmaMasterFormLayout preparingSocialMediaForm(Object model, int uiType);

    protected abstract String getModuleTitle(Object model, int uiType);

    /**
     * it does not need to implemented when the mode is for update
     *
     * @return
     */
    protected abstract Object preparingNewDataModel();

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(btnAdd))
            doPopupAddItem();
    }

    private void doPopupAddItem() {
        Object model = preparingNewDataModel();
        VerticalLayout vform = preparingSocialMediaDetailForm(model, MODIFY_MODE.ADD_NEW, 0);
        vform.setWidth("90%");

        Panel p = new Panel(vform);
        p.setWidth("90%");

        HorizontalLayout ly = new HorizontalLayout(p);
        ly.setWidth("100%");
        ly.setComponentAlignment(p, Alignment.TOP_CENTER);

        window = new Window(new StringBuffer().append(TA.getCurrent().getParams().getLabel(Constants.LABEL_NEW)).append(" ").append(contentTitle.getValue()).toString());
        window.setContent(ly);
        window.setWidth("60%");
        window.setModal(true);
        UI.getCurrent().addWindow(window);
    }

    public enum MODIFY_MODE {
        UPDATE(1), ADD_NEW(2);

        int methodvalue = 1;

        private MODIFY_MODE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }
}
