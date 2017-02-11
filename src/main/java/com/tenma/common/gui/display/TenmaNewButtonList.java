package com.tenma.common.gui.display;


import com.tenma.common.gui.display.component.ConfirmationDialog;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.CrudCode;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.AlignmentInfo;
import com.vaadin.ui.*;
import org.vaadin.peter.contextmenu.ContextMenu;

/**
 * User: ndwijaya
 * Date: 12/7/13
 * Time: 3:30 PM
 */
public abstract class TenmaNewButtonList extends TenmaPanel implements LayoutEvents.LayoutClickListener {
    private Button deleteButton;
    private Button addButton;
    private Button updateButton;
    private Button printButton;
    private TextField textSearch;
    private Panel layoutPanel;
    private Button searchButton;
    private HorizontalLayout buttonLayout;
    private Panel panelList;
    private Component title;
    private Component search;
    private boolean titleStatus;
    private boolean buttonStatus;
    private boolean searchStatus;
    private boolean contentStatus;

    public TenmaNewButtonList() {

        textSearch = new TextField();
        textSearch.addShortcutListener(new ShortcutListener("textSeacrGrid" + getId(), ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                if (target.equals(textSearch)) {
                    doSearch();
                }
            }
        });
        layoutPanel = new Panel();
        titleStatus = true;
        buttonStatus = true;
        searchStatus = true;
        contentStatus = true;

        textSearch.setInputPrompt(param.getLabel("default.search"));
        layoutPanel.setStyleName("templateList");
        preparingData();
        preparingUI();
        executingDataPreparation();
    }

    public void preparingData() {
    }

    private final void preparingUI() {

        addButton = new Button(param.getLabel(Constants.LABEL_NEW), this);
        updateButton = new Button(param.getLabel(Constants.LABEL_UPDATE), this);
        deleteButton = new Button(param.getLabel(Constants.LABEL_DELETE), this);
        printButton = new Button(param.getLabel(Constants.LABEL_PRINT), this);

        addButton.setIcon(new ThemeResource(Constants.ADD_ICON));
        updateButton.setIcon(new ThemeResource("layouts/images/16/pencil.png"));
        deleteButton.setIcon(new ThemeResource("layouts/images/deletered.png"));
        printButton.setIcon(new ThemeResource(Constants.PRINT_ICON));

        searchButton = new Button(param.getLabel("default.search"), this);
        searchButton.setIcon(new ThemeResource("layouts/images/16/049.png"));

        buttonLayout = new HorizontalLayout();
        configureToolbarBefore();
        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(updateButton);
        buttonLayout.addComponent(deleteButton);
        buttonLayout.addComponent(printButton);
        configureToolbarAfter();
        panelList = new Panel();
        panelList.setWidth("100%");
//        panelList.setScrollable(true);
        panelList.setScrollTop(1000000);
        VerticalLayout vl = new VerticalLayout();
        panelList.setContent(createListLayout());
//        layout.addComponent(buttonLayout, "contentButton");
        Panel p = new Panel();
        Label cspace1 = new Label("");
        Label cspace2 = new Label("");
        Label cspace3 = new Label("");
        cspace1.setHeight("10px");
        cspace2.setHeight("10px");
        cspace3.setHeight("10px");
        if (title == null) title = new Label("");
        vl.addComponent(title);
        vl.addComponent(cspace1);
        vl.addComponent(buttonLayout);
        vl.addComponent(cspace2);
//        layout.addComponent(createSearchLayout(), "contentSearch");
        vl.addComponent(createSearchLayout());
        vl.addComponent(cspace3);
//        layout.addComponent(panelList, "contentList");
        vl.addComponent(panelList);
        p.setContent(vl);

        VerticalLayout vml = new VerticalLayout();
        Label vspace1 = new Label("");
        Label vspace2 = new Label("");
        vspace1.setHeight("10px");
        vspace2.setHeight("10px");
        vml.addComponent(vspace1);
        vml.addComponent(p);
        vml.addComponent(vspace2);

        HorizontalLayout hml = new HorizontalLayout();
        Label hspace1 = new Label("");
        Label hspace2 = new Label("");
        hspace1.setWidth("10px");
        hspace2.setWidth("10px");
        hml.addComponent(hspace1);
        hml.addComponent(vml);
        hml.addComponent(hspace2);

        layoutPanel.setContent(hml);
        setCompositionRoot(layoutPanel);
    }

    public void configureToolbarBefore() {
    }

    public void configureToolbarAfter() {
    }

    private void refreshComponent() {
        System.out.println("contentStatus = " + contentStatus);
        System.out.println("buttonStatus = " + buttonStatus);
        Panel p = new Panel();
        p.setStyleName("listpanel");
        Label cspace1 = new Label("");
        Label cspace2 = new Label("");
        Label cspace3 = new Label("");
        cspace1.setHeight("10px");
        cspace2.setHeight("10px");
        cspace3.setHeight("10px");

        VerticalLayout vl = new VerticalLayout();
        if (titleStatus && title != null) {
            vl.addComponent(title);
            vl.addComponent(cspace1);
        }
        if (buttonStatus && buttonLayout != null) {
            vl.addComponent(buttonLayout);
            vl.addComponent(cspace2);
        }
        if (searchStatus && search != null) {
            vl.addComponent(search);
            vl.addComponent(cspace3);
        }
        if (contentStatus && panelList != null) vl.addComponent(panelList);
        p.setContent(vl);

        VerticalLayout vml = new VerticalLayout();
        Label vspace1 = new Label("");
        Label vspace2 = new Label("");
        vspace1.setHeight("10px");
        vspace2.setHeight("10px");
        vml.addComponent(vspace1);
        vml.addComponent(p);
        vml.addComponent(vspace2);

        HorizontalLayout hml = new HorizontalLayout();
        Label hspace1 = new Label("");
        Label hspace2 = new Label("");
        hspace1.setWidth("10px");
        hspace2.setWidth("10px");
        hml.addComponent(hspace1);
        hml.addComponent(vml);
        hml.addComponent(hspace2);

        layoutPanel.setContent(hml);
        setCompositionRoot(layoutPanel);
    }

    public void removeTitleComponent() {
        titleStatus = false;
        refreshComponent();
    }

    public void removeButtonComponent() {
        System.out.println("TenmaNewButtonList.removeButtonComponent");
        buttonStatus = false;
        refreshComponent();
    }

    public void removeSearchComponent() {
        searchStatus = false;
        refreshComponent();
    }

    public void removeContentComponent() {
        contentStatus = false;
        refreshComponent();
    }

    public void addButtonComponent(Component abtn) {
        buttonStatus = true;
        buttonLayout.addComponent(abtn);
        refreshComponent();
    }

    public void addSearchComponent(Component aseach) {
        searchStatus = true;
        search = aseach;
        refreshComponent();
    }

    public void addContentComponent(Panel aContent) {
        contentStatus = true;
        panelList = aContent;
        refreshComponent();
    }

    public AbstractComponentContainer getButtonArea() {
        return buttonLayout;
    }

    private Component createSearchLayout() {
        HorizontalLayout layoutSearch = new HorizontalLayout();
        Label labelSearch = new Label(param.getLabel("default.searchBy"));

        layoutSearch.addComponent(labelSearch);
        layoutSearch.addComponent(textSearch);
        layoutSearch.addComponent(searchButton);
        layoutSearch.setSpacing(true);

        layoutSearch.setComponentAlignment(labelSearch, new Alignment(AlignmentInfo.Bits.ALIGNMENT_VERTICAL_CENTER | AlignmentInfo.Bits.ALIGNMENT_LEFT));
        return layoutSearch;
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton().equals(addButton))
            clickModify(TenmaMasterFormModify.ADD_MODE);
        else if (clickEvent.getButton().equals(updateButton))
            clickModify(TenmaMasterFormModify.UPDATE_MODE);
        else if (clickEvent.getButton().equals(deleteButton))
            clickDelete();
        else if (clickEvent.getButton().equals(searchButton))
            doSearch();
        else if (clickEvent.getButton().equals(printButton))
            doPrint();

    }

    public void clickModify(int update_mode) {
        try {
            boolean cont = true;
            if (TenmaMasterFormModify.UPDATE_MODE == update_mode) cont = isRowSelected();
            if (cont) {
                doModify(this, update_mode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public final void clickDelete() {
        try {
            boolean cont = isRowSelected();
            if (cont) {
                UI.getCurrent().getUI().addWindow(new ConfirmationDialog(
                        param.getLabel(Constants.LABEL_DELETE),
                        param.getMessage("delete.confirm"),
                        param.getLabel("default.yes"),
                        param.getLabel("default.no"),
                        new ConfirmationDialog.Callback() {
                            public void onDialogResult(boolean okChoose) {
                                if (okChoose) {
                                    try {
                                        int res = doDeletion();
                                        if (res != 0) {
                                            refreshTable();
                                            commonMessageHandler(Notification.Type.TRAY_NOTIFICATION, CrudCode.DELETE, param.getMessage("system.info.success"), param.getMessage("system.info.delete"));
                                        } else
                                            commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.DELETE, param.getMessage("system.error.delete"), null);
                                    } catch (Exception err) {
                                        err.printStackTrace();
                                        commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.DELETE, err.getMessage(), null);
                                    }
                                }

                            }
                        }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setTitle(String atitle) {
        StringBuilder contentTitle = new StringBuilder();
        contentTitle
                .append(param.getLabel(atitle))
                .toString();
        Label l = new Label(contentTitle.toString());
//        l.setContentMode(ContentMode.HTML);
        l.setPrimaryStyleName("formtitle");
        titleStatus = true;
        title = l;
    }

    public final void setTitle(String parentTitle, String myTitle) {
        StringBuilder contentTitle = new StringBuilder();
        contentTitle
                .append(param.getLabel(myTitle))
                .append(" [").append(param.getLabel(parentTitle)).append("]")
                .toString();
        Label l = new Label(contentTitle.toString());

        l.setStyleName("formtitle");
        titleStatus = true;
        title = l;
    }

    public TextField getTextSearch() {
        return textSearch;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Panel getPanelList() {
        return panelList;
    }

    public abstract void doModify(TenmaPanel parentContainer, int update_mode);

    public abstract int doDeletion() throws Exception;

    public abstract void refreshTable();

    public abstract void executingDataPreparation();

    public abstract void doSearch();

    public abstract void doPrint();

    public abstract Component createListLayout();

    public void layoutClick(LayoutEvents.LayoutClickEvent event) {
    }

    public void contextMenuItemClicked(ContextMenu.ContextMenuItemClickEvent event) {
    }

}


