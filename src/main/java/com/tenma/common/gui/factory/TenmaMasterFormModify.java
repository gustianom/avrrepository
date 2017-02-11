package com.tenma.common.gui.factory;

import com.tenma.common.TA;
import com.tenma.common.bean.reftaskmember.TaskMemberHelper;
import com.tenma.common.gui.display.TenmaHeaderLayout;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TaskMemberModel;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/**
 * User: anom
 * Date: 10/27/12
 * Time: 4:06 PM
 */
public abstract class TenmaMasterFormModify extends TenmaPanel {
    public static final int ADD_MODE = 1000;
    public static final int UPDATE_MODE = 2000;
    public static final int VIEW_MODE = 3000;
    public static final int LOOKUP_MODE = 4000;
    Button iconBtnTitle = new Button();
    private Button butSave;
    private Button butCancel;
    private TenmaPanel parentContainer;
    private int modifyMode;
    private TenmaMasterFormLayout personForm = new TenmaMasterFormLayout(this);
    private Panel layoutPanelTitle;
    private Panel layoutPanelButton;
    private Panel layoutPanelModify;
    private HorizontalLayout topHz;
    private boolean button;
    private boolean modify;
    private TenmaHeaderLayout topHeaderLayout;
    private HorizontalLayout buttonArea;

    public TenmaMasterFormModify(TenmaPanel parentContainer, int modifyMode) {
        super();
        topHeaderLayout = new TenmaHeaderLayout();
        this.modifyMode = modifyMode;
        generateForm(parentContainer);
    }


    private void generateForm(TenmaPanel parentContainer) {
        this.parentContainer = parentContainer;
        topHeaderLayout = new TenmaHeaderLayout();
        initizalized();
    }


    private void initizalized() {
        button = true;
        modify = true;
        personForm.setWidth("100%");

        layoutPanelTitle = new Panel();
        layoutPanelButton = new Panel();
        layoutPanelModify = new Panel();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        setWidth("100%");

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        VerticalLayout vl = new VerticalLayout();
        setTitle("");

        layoutPanelTitle.setStyleName(ValoTheme.PANEL_BORDERLESS);

        butCancel = new Button("", this);
        butCancel.setIcon(new ThemeResource(Constants.BACK_ICON));
        butCancel.setImmediate(true);
        butCancel.setStyleName("circle");
        butCancel.setDescription(getLabel("default.cancel"));
        butSave = new Button("", this);
        butSave.setIcon(new ThemeResource(Constants.SAVE_ICON));
        butSave.setStyleName("circle");
        butSave.setDescription(getLabel("default.save"));

        buttonArea = new HorizontalLayout();
        configureToolbarBefore();
        Label spaceb = new Label("");
        spaceb.setWidth("10px");
        buttonArea.addComponent(spaceb);
        buttonArea.addComponent(butSave);
        buttonArea.addComponent(butCancel);
        buttonArea.setMargin(true);
        buttonArea.setSpacing(true);
        configureToolbarAfter();

        layoutPanelButton.setContent(buttonArea);
        layoutPanelButton.setStyleName(ValoTheme.PANEL_BORDERLESS);

        HorizontalLayout hzm = new HorizontalLayout();
        Label space1 = new Label("");
        Label space2 = new Label("");
        space1.setWidth("10px");
        space2.setWidth("10px");

        hzm.addComponent(space1);
        hzm.addComponent(personForm);
        hzm.addComponent(space2);
        layoutPanelModify.setContent(hzm);
        layoutPanelModify.setStyleName("modifycontent");
        layoutPanelModify.setWidth("100%");

        Label vspace1 = new Label("");
        Label vspace2 = new Label("");
        vspace1.setHeight("10px");
        vspace2.setHeight("10px");
        vl.addComponent(vspace1);
        vl.addComponent(layoutPanelModify);
        vl.addComponent(vspace2);
        vl.setMargin(new MarginInfo(false, true, false, false));

        registerFooterButton(getButSave());
        registerFooterButton(getButCancel());


        setCompositionRoot(vl);
    }

    public HorizontalLayout getButtonArea() {
        buttonArea.addComponent(getButSave());
        buttonArea.addComponent(getButCancel());
        return buttonArea;
    }

    public void configureToolbarBefore() {
    }


    public void configureToolbarAfter() {
    }

    private void refreshComponent() {
        VerticalLayout vl = new VerticalLayout();
        Label space = new Label("");
        space.setHeight("10px");
        vl.addComponent(space);
        if (button) vl.addComponent(layoutPanelButton);

        if (!modify) {
            layoutPanelModify.setContent(personForm);
        } else {
            vl.addComponent(layoutPanelModify);
        }

        setCompositionRoot(vl);
    }

    public void addTitleComponent(Component titleComp) {
        layoutPanelTitle.setContent(titleComp);
//        super.setTittle(layoutPanelTitle);
    }

    public void addButtonComponent(Component buttonComp) {
        button = true;
        layoutPanelButton.setContent(buttonComp);
        refreshComponent();
    }

    public void addContentModifyComponent(Component contentModify) {
        modify = true;
        layoutPanelModify.setContent(contentModify);
        refreshComponent();
    }

    public void removeButtonComponent() {
        button = false;
    }

    public void removeModifyComponent() {
        modify = false;
    }

    public Button getButCancel() {
        return butCancel;
    }

    public Button getButSave() {
        return butSave;
    }

    public void setTitle(String titleLabelCode) {
        StringBuffer contentTitle = new StringBuffer();
        contentTitle
                .append(" ").append(param.getLabel(titleLabelCode));

        Label l = new Label(contentTitle.toString());

        l.setPrimaryStyleName("formtitle");

        iconBtnTitle.setPrimaryStyleName("titleicon");
        iconBtnTitle.setWidth("25px");
        if (modifyMode == ADD_MODE) {
            iconBtnTitle.setIcon(new ThemeResource(Constants.ADD_TITLE_ICON));
            topHeaderLayout.addIconComponent(iconBtnTitle);

        } else if (modifyMode == UPDATE_MODE) {
            iconBtnTitle.setIcon(new ThemeResource(Constants.UPDATE_TITLE_ICON));
            topHeaderLayout.addIconComponent(iconBtnTitle);
        }
        topHeaderLayout.generateModifyLayout(l);
        super.setTittle(topHeaderLayout);
    }

    public void setTitle(String parentTitle, String myTitle) {
        StringBuffer contentTitle = new StringBuffer();
        contentTitle
                .append(modifyMode == ADD_MODE ? param.getLabel(Constants.LABEL_NEW) : modifyMode == UPDATE_MODE ? param.getLabel(Constants.LABEL_UPDATE) : "")
                .append(" ")
                .append(param.getLabel(parentTitle)).append("/")
                .append(param.getLabel(myTitle))
                .toString();
        Label l = new Label(contentTitle.toString());

        l.setPrimaryStyleName("formtitle");
        topHz = new HorizontalLayout();
        topHz.addComponent(l);
        super.setTittle(topHz);
    }

    public boolean isAddMode() {
        return modifyMode == ADD_MODE;
    }

    public boolean isUpdateMode() {
        return modifyMode == UPDATE_MODE;
    }

    public boolean isViewMode() {
        return modifyMode == VIEW_MODE;
    }

    public boolean isLookupMode() {
        return modifyMode == LOOKUP_MODE;
    }

    public Integer getModifyMode() {
        return modifyMode;
    }

    public void setModifyMode(int modifyMode) {
        this.modifyMode = modifyMode;
    }

    public final void doPublishUI(BeanItem beanItem, List<String> visibileFields, TenmaField fieldFactory) {
        personForm.setItemDataSource(beanItem);
        personForm.setVisibleItemProperties(visibileFields);
        personForm.setFieldFactory(fieldFactory);
        personForm.bindUI();
    }

    public TenmaMasterFormLayout getPersonForm() {
        return personForm;
    }

    public abstract void doCancel();

    public abstract void doSave() throws Exception;

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);

        if (clickEvent.getButton().equals(butSave)) {
            System.out.println("TenmaMasterFormModify.buttonClick SAVE");
            if (personForm.commit()) {
                try {
                    doSave();
                    if (getTaskId() != null && getTaskId().trim().length() != 0)
                        doUpdateSystemTodoList();
                } catch (Exception e) {
                    e.printStackTrace();
                    String defaultMessage = param.getLabel("system.error.saving");
//                    System.out.println("MSG NOTIF=====================================================================>" + e.getMessage());
                    if (e.getMessage() != null) {
                        if (e.getMessage().equals("java.lang.Exception: " + Constants.ADD_ALREADY_EXIST)) {
                            defaultMessage = param.getLabel("msg.alreadyExist");

                        }
                    }
                    personForm.doUpdateFormStatus(defaultMessage);
                    System.out.println("defaultMessage====================================================================>" + defaultMessage);
                }
            }
        } else if (clickEvent.getButton().equals(butCancel)) {
            personForm.discard();
            doCancel();
        }
    }

    private void doUpdateSystemTodoList() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("share.tenma.common.gui.factory.TenmaMasterFormModify.doUpdateSystemTodoList");
        TaskMemberHelper hlp = new TaskMemberHelper();
        TaskMemberModel taskModel = new TaskMemberModel();
        taskModel.setTaskId(getTaskId());
        taskModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        taskModel.setTodoStatus(Constants.TODO_STATUS.DONE.getValue());
        taskModel.setUserId(TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        setAuditTrail(taskModel);
        try {
            int res = hlp.updateRefTaskMember(taskModel);
            Object b = getParent();
            if (b instanceof Window) {
                Window w = (Window) b;
                UI.getCurrent().removeWindow(w);
                w.close();
            }

            TA.getCurrent().goHome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TenmaPanel getParentContainer() {
        return parentContainer;
    }


    public final void setShowTitle(boolean isShow) {
        topHz.setVisible(isShow);
    }

    public boolean isValidAfter() {
        return true;
    }
}
