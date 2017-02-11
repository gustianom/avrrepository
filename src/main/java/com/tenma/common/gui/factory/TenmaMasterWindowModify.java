package com.tenma.common.gui.factory;

import com.tenma.common.TA;
import com.tenma.common.bean.reftaskmember.TaskMemberHelper;
import com.tenma.common.gui.display.TenmaInterface;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TaskMemberModel;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.util.List;

/**
 * User: anom
 * Date: 10/27/12
 * Time: 4:06 PM
 */
public abstract class TenmaMasterWindowModify extends TenmaWindow implements TenmaInterface, Button.ClickListener {
    private Button butSave;
    private Button butCancel;
    private TenmaPanel parentContainer;
    private Constants.MODIFY_MODE modifyMode;
    private TenmaMasterFormLayout personForm = new TenmaMasterFormLayout();
    private HorizontalLayout buttonHz;
    private VerticalLayout layoutPanelModify;
    private boolean button;
    private boolean modify;

    private HorizontalLayout buttonArea;
    private String taskId;


    public TenmaMasterWindowModify(TenmaPanel parentContainer, Constants.MODIFY_MODE modifyMode) {

        this.parentContainer = parentContainer;
        this.modifyMode = modifyMode;
        initizalized();
    }

    private void initizalized() {
        button = true;
        modify = true;
        personForm.setWidth("100%");

        buttonHz = new HorizontalLayout();
//        layoutPanelModify = new Panel();
        layoutPanelModify = new VerticalLayout();

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

//        Label space = new Label("");
//        space.setHeight("3px");
//        vl.addComponent(space);
        vl.setSpacing(true);
        butCancel = new Button("", this);
        butCancel.setDescription(param.getLabel(Constants.LABEL_CANCEL));
        butCancel.setIcon(new ThemeResource(Constants.BACK_ICON));
        butCancel.setImmediate(true);
        butSave = new Button("", this);
        butSave.setDescription(param.getLabel(Constants.LABEL_SAVE));
        butSave.setIcon(new ThemeResource(Constants.SAVE_ICON));
        butSave.setStyleName("circle");
        butCancel.setStyleName("circle");

        buttonArea = new HorizontalLayout();
        configureToolbarBefore();
        Label spaceb = new Label("");
        spaceb.setWidth("10px");
        buttonArea.addComponent(spaceb);
        buttonArea.addComponent(butSave);
        buttonArea.addComponent(butCancel);
        buttonArea.setSpacing(true);
        configureToolbarAfter();
        buttonHz.addComponent(buttonArea);
        buttonHz.setComponentAlignment(buttonArea, Alignment.BOTTOM_RIGHT);

        HorizontalLayout hzm = new HorizontalLayout();
        Label space1 = new Label("");
        Label space2 = new Label("");
        space1.setWidth("10px");
        space2.setWidth("10px");

        hzm.addComponent(space1);
        hzm.addComponent(personForm);
        hzm.addComponent(space2);

//        layoutPanelModify.setContent(hzm);
        layoutPanelModify.addComponent(hzm);
//        layoutPanelModify.setStyleName("modifycontent");
        HorizontalLayout hzz = new HorizontalLayout();
        Label hspace1 = new Label("");
        Label hspace2 = new Label("");
        hspace1.setWidth("10px");
        hspace2.setWidth("10px");
        hzz.addComponent(hspace1);
        layoutPanelModify.setSizeUndefined();
        hzz.addComponent(layoutPanelModify);
        hzz.addComponent(hspace2);
        hzz.setExpandRatio(layoutPanelModify, 1.0f);
//        hzz.setSizeFull();

//        Label vspace1 = new Label("");
//        Label vspace2 = new Label("");
//        vspace1.setHeight("10px");
//        vspace2.setHeight("10px");
//        vl.addComponent(vspace1);
        vl.addComponent(hzz);
        vl.addComponent(buttonHz);
        vl.setComponentAlignment(buttonHz, Alignment.BOTTOM_RIGHT);
//        vl.addComponent(vspace2);
        setContent(vl);
    }

    public HorizontalLayout getButtonArea() {
        return buttonArea;
    }

    public void configureToolbarBefore() {
    }


    public void configureToolbarAfter() {
    }

//    public CustomLayout getLayout() {
//        return layout;
//    }

    private void refreshComponent() {
//        System.out.println("title = " + title);
//        System.out.println("button = " + button);
//        System.out.println("modify = " + modify);

        VerticalLayout vl = new VerticalLayout();
        Label space = new Label("");
        space.setHeight("10px");
        vl.addComponent(space);
        if (button) vl.addComponent(buttonHz);

        HorizontalLayout hzm = new HorizontalLayout();
        Label space1 = new Label("");
        Label space2 = new Label("");
        space1.setWidth("10px");
        space2.setWidth("10px");
        if (!modify) {
            hzm.addComponent(space1);
            hzm.addComponent(personForm);
            hzm.addComponent(space2);
            layoutPanelModify.removeAllComponents();
            layoutPanelModify.addComponent(hzm);
        } else {
            vl.addComponent(layoutPanelModify);
        }

        setContent(vl);
    }

    public void addContentModifyComponent(Component contentModify) {
        modify = true;
        layoutPanelModify.removeAllComponents();
        layoutPanelModify.addComponent(contentModify);
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

    public void setTitle(String titleString) {
        StringBuffer contentTitle = new StringBuffer();
        contentTitle
                .append(Constants.MODIFY_MODE.ADD_MODE.equals(modifyMode) ? param.getLabel(Constants.LABEL_NEW) : Constants.MODIFY_MODE.UPDATE_MODE.equals(modifyMode) ? param.getLabel(Constants.LABEL_UPDATE) : "")
                .append(" ").append(param.getLabel(titleString));

        setCaption(contentTitle.toString());

    }

    public void setTitle(String parentTitle, String myTitle) {
        StringBuffer contentTitle = new StringBuffer();
        contentTitle
                .append(Constants.MODIFY_MODE.ADD_MODE.equals(modifyMode) ? param.getLabel(Constants.LABEL_NEW) : Constants.MODIFY_MODE.UPDATE_MODE.equals(modifyMode) ? param.getLabel(Constants.LABEL_UPDATE) : "")
                .append(" ")
                .append(param.getLabel(parentTitle)).append("/")
                .append(param.getLabel(myTitle))
                .toString();
        setCaption(contentTitle.toString());


    }

    public Constants.MODIFY_MODE getModifyMode() {
        return modifyMode;
    }

    public void setModifyMode(Constants.MODIFY_MODE modifyMode) {
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
        if (clickEvent.getButton().equals(butSave)) {
            if (personForm.commit()) {
                try {
                    doSave();
                    if (getTaskId() != null && getTaskId().trim().length() != 0)
                        doUpdateSystemTodoList();
                } catch (Exception e) {
                    personForm.doUpdateFormStatus(param.getMessage("system.error.saving"));
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

    //    public CustomComponent getParentContainer() {
    public TenmaPanel getParentContainer() {
        return parentContainer;
    }

    @Override
    public void setFocus(Component componentName) {
        System.out.println("TenmaMasterWindowModify.setFocus");
    }

    public final String getTaskId() {
        return this.taskId;
    }

    public final void setTaskId(String taskID) {
        this.taskId = taskID;
    }

    @Override
    public void doRefresh(Object newData) {

    }

    @Override
    public Object getSelectedObject() {
        return null;
    }

    @Override
    public void setSelectedObject(Object obj) {

    }
}
