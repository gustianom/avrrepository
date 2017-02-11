package com.tenma.common.gui.factory;

import com.tenma.common.gui.display.TenmaHeaderLayout;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.Severity;
import com.tenma.common.util.error.TenmaStackTraceManager;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.virkki.paperstack.PaperStack;

/*
    This class is design to replace TenmaMasterFormModify
    these class below is set to be deprecated :
      - TenmaMasterFormModify
      - TenmaMasterFormLayout
    modification changed:
     * the form is not set as default as TenmaMasterFormLayout is extend based on FormLayout
       so is free to design layout based on requirement insted of force to use FormLayout
       if required to have more than 1 layout is also flexible
     * FieldGroup validation is move to TenmaForm (new Class)
     * the design is changed
       - required to split Modify Implementation and the UI,
         UI will be extend TenmaForm while modify required to extend TenmaMasterNewFormModify
       - UI class has only 3 functionality
         1. define layout UI
         2. set data to UI and get data to UI
         3. set error message and validation
     * TenmaForm is asbstact class that hold the FieldGroup instance, and perform validation and errorMessage bridging
         * Tenma Form force UIForm to have validate() method and setErrorMessage() method
         * Tenma Form will set default error message if the field is required but error message is not set with
             component Caption + label (def.isrequired)
         * Tenma Form has 3 type of Message broadcast :
            * on Tenma Application : show on header Form
            * on current form : show as label on top of form
            * on Notificaiton message : show as notification
 */

public abstract class TenmaMasterNewFormModify extends TenmaPanel {
    public static final int ADD_MODE = 1000;
    public static final int UPDATE_MODE = 2000;
    public static final int VIEW_MODE = 3000;
    public static final int LOOKUP_MODE = 4000;
    HorizontalLayout hzmContent = new HorizontalLayout();
    Button iconBtnTitle = new Button();
    PaperStack paperStack = new PaperStack();
    private Button butSave;
    private Button butCancel;
    private TenmaPanel parentContainer;
    private int modifyMode;
    private Panel layoutPanelTitle;
    private Panel layoutPanelButton;
    private Panel layoutPanelModify;
    private HorizontalLayout topHz;
    private boolean button;
    private TenmaHeaderLayout topHeaderLayout;
    private HorizontalLayout buttonArea;
    private HorizontalLayout hFooter;
    private HorizontalLayout btnCutomArea;
    private HorizontalLayout hHeader;
    private VerticalLayout panelStack;


    public TenmaMasterNewFormModify(TenmaPanel parentContainer, int modifyMode) {

        topHeaderLayout = new TenmaHeaderLayout();
        this.modifyMode = modifyMode;
        generateForm(parentContainer);
    }

    private void generateForm(TenmaPanel parentContainer) {
        this.parentContainer = parentContainer;
        topHeaderLayout = new TenmaHeaderLayout();
        initizalized();
    }

    public void setUIForm(Component uiForm) {
        System.out.println("TenmaMasterNewFormModify.setUIForm");

        hzmContent.removeAllComponents();
        paperStack.setWidth(90, Unit.PERCENTAGE);
        paperStack.setHeight(100, Unit.PERCENTAGE);
        paperStack.setImmediate(true);


        if (panelStack != null) {
            paperStack.addComponent(uiForm);
            paperStack.addComponent(panelStack);
            hzmContent.addComponent(paperStack);

        } else {
            hzmContent.addComponent(uiForm);
        }


        hzmContent.setMargin(new MarginInfo(false, true, false, true));
        hzmContent.setWidth(100.0f, Unit.PERCENTAGE);


    }


    private void initizalized() {
        button = true;
        layoutPanelTitle = new Panel();
        layoutPanelButton = new Panel();
        layoutPanelModify = new Panel();
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

//        butCancel = new Button(param.getLabel(Constants.LABEL_CANCEL), this);
//        butSave = new Button(param.getLabel(Constants.LABEL_SAVE), this);

        butCancel = new Button();
        butSave = new Button();

        butCancel.setDescription(getLabel(Constants.LABEL_CANCEL));
        butSave.setDescription(getLabel(Constants.LABEL_SAVE));

        butCancel.addClickListener(this);
        butSave.addClickListener(this);

        butCancel.setIcon(new ThemeResource(Constants.BACK_ICON));
        butCancel.setImmediate(true);

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
        buttonArea.setMargin(true);
        buttonArea.setSpacing(true);

        buttonArea.setStyleName("masterFormFooterArea");

        configureToolbarAfter();

        layoutPanelButton.setContent(buttonArea);
        layoutPanelButton.setStyleName(ValoTheme.PANEL_BORDERLESS);
        layoutPanelModify.setContent(hzmContent);
        layoutPanelModify.setStyleName("modifycontent");
        layoutPanelModify.setWidth("100%");
        vl.addComponent(layoutPanelModify);
        vl.setMargin(new MarginInfo(false, true, false, false));

        registerFooterButton(getButCancel());
        registerFooterButton(getButSave());


        setCompositionRoot(vl);
    }

    public HorizontalLayout getButtonArea() {


        return buttonArea;
    }


    public HorizontalLayout getCustomFooterArea() {

        btnCutomArea = new HorizontalLayout(butSave, butCancel);
        btnCutomArea.setMargin(new MarginInfo(false, true, false, false));
        btnCutomArea.setSpacing(true);
        hFooter = new HorizontalLayout();
        hFooter.addComponent(btnCutomArea);
        hFooter.setComponentAlignment(btnCutomArea, Alignment.MIDDLE_RIGHT);
        hFooter.setWidth(800, Unit.PIXELS);
        hFooter.setHeight(100, Unit.PERCENTAGE);
        hFooter.setStyleName("masterFormFooterArea");

        return hFooter;
    }


    public HorizontalLayout getCustomHeader(Label lTitle) {

        hHeader = new HorizontalLayout();
        lTitle.setPrimaryStyleName("headerTitle");
        hHeader.addComponent(lTitle);
        hHeader.setWidth(800, Unit.PIXELS);
        hHeader.setHeight(35, Unit.PIXELS);
        hHeader.setStyleName("metro-slate-grey");
        hHeader.setComponentAlignment(lTitle, Alignment.MIDDLE_LEFT);
        return hHeader;
    }

    public void configureToolbarBefore() {
    }


    public void configureToolbarAfter() {
    }


    public void addTitleComponent(Component titleComp) {
        layoutPanelTitle.setContent(titleComp);
        super.setTittle(layoutPanelTitle);
    }

    public void addButtonComponent(Component buttonComp) {
        button = true;
        layoutPanelButton.setContent(buttonComp);
    }


    public void removeButtonComponent() {
        button = false;
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


    public abstract void doCancel();

    public abstract void doSave() throws Exception;

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        if (clickEvent.getButton().equals(butSave)) {
            try {
                doSave();
            } catch (Exception e) {
                // Serverity 30 - Only impact to Course
                TenmaStackTraceManager.getInstance().addNewStackTrace(Severity.SEVERITY30, e);
//                e.printStackTrace();
                String defaultMessage = param.getLabel("system.error.saving");
                if (e.getMessage() != null) {
                    if (e.getMessage().equals("java.lang.Exception: " + Constants.ADD_ALREADY_EXIST)) {
                        defaultMessage = param.getLabel("msg.alreadyExist");
//                        TA.getCurrent().setErrorFieldMessage(defaultMessage);
                    }
                }
            }
        } else if (clickEvent.getButton().equals(butCancel)) {
            doCancel();
        }
    }


    public TenmaPanel getParentContainer() {
        return parentContainer;
    }


    public final void setShowTitle(boolean isShow) {
        topHz.setVisible(isShow);
    }

    public void setPanelStack(VerticalLayout panelStack, String contentTitle, String caption) {
        panelStack.setWidth(800, Unit.PIXELS);
        panelStack.setHeight(100, Unit.PERCENTAGE);
        VerticalLayout vLy = new VerticalLayout();
        StringBuffer title = new StringBuffer()
                .append("<p>").append(param.getLabel(contentTitle))
                .append("</p>");
        Label labelMenuName = new Label(title.toString());
        labelMenuName.setContentMode(ContentMode.HTML);
        vLy.addComponent(labelMenuName);
        vLy.setCaptionAsHtml(true);
        vLy.setImmediate(true);
        panelStack.setCaption(caption);
        panelStack.setMargin(true);

        this.panelStack = panelStack;
    }
}
