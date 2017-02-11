package com.tenma.common.gui.display;

import com.tenma.common.TA;
import com.tenma.common.gui.display.component.ConfirmationDialog;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.CrudCode;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.tenma.share.gui.TenmaRoadMap;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;


/**
 * User: anom - Date: 10/27/12 Time: 3:51 PM
 * User: Yos - 11/15/2014 - Adding Filter Button, implementation on every list extend tenma masterlist
 */
public abstract class TenmaList extends TenmaListPaging {

    private Button deleteButton;
    private Button addButton;
    private Button updateButton;
    private TextField textSearch;
    private Button downloadButton;
    private Button uploadButton;
    private VerticalLayout leftComponent = new VerticalLayout();
    private Button filterButton;
    private Button refreshButton;

    private VerticalLayout vl;
    //    private HorizontalLayout topMapLy;
    private TenmaHeaderLayout topMapLy;

    private Button searchButton;
    private HorizontalLayout buttonLayout;
    private Button printButton;

    private Component searchitem;
    private VerticalLayout vltop;
    private HorizontalLayout topArea = new HorizontalLayout();


    private VerticalLayout vFilter;
    private Component listComponent = new HorizontalLayout();
    private boolean setting;
    private TenmaRoadMap roadMap = new TenmaRoadMap();
    private int clickButton = 0;
    private VerticalLayout vFilterBox = new VerticalLayout();

    private Button iconBtn = new Button();

    public TenmaList() {
        super();
        preparingTenmaListLayout();
    }

    public void registerRoadMap(Button btn, int posistion) {
        roadMap.register(btn, posistion);
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public Component getLeftComponent() {

        return leftComponent;
    }

    private void preparingTenmaListLayout() {
        vl = new VerticalLayout();
        vl.setStyleName("templateList");
        topMapLy = new TenmaHeaderLayout();
        topMapLy.setStyleName("tenmapanelTop");
        topMapLy.setWidth("100%");
        initialize();
        preparingData();
        preparingUI();
        configureUIContent();
        prepareFilterLayout();
        setSecureButton();
        executingDataPreparation();
    }

    private void setSecureButton() {
        if (TA.getCurrent().getClientInfo().getSecurityModel().getAuthenticationCode() != null && TA.getCurrent().getClientInfo().getSecurityModel().getAuthenticationCode() != 0) {
            if (getAddButton() != null)
                getAddButton().setEnabled(isCreateAccessEnable());
            if (getUpdateButton() != null)
                getUpdateButton().setEnabled(isUpdateAccessEnable());
            if (getDeleteButton() != null)
                getDeleteButton().setEnabled(isDeleteAccessEnable());
            if (getPrintButton() != null)
                getPrintButton().setEnabled(isPrintAccess());
        } else {
            if (getAddButton() != null)
                getAddButton().setEnabled(true);
            if (getUpdateButton() != null)
                getUpdateButton().setEnabled(true);
            if (getDeleteButton() != null)
                getDeleteButton().setEnabled(true);
            if (getPrintButton() != null)
                getPrintButton().setEnabled(true);
        }
    }

    public abstract void initialize();

    private void configureUIContent() {
        addListComponent(pagingLayout);
    }

    /**
     * set variable before executing data
     */
    public void preparingData() {
    }

    private FieldEvents.FocusListener createFocusListener() {
        FieldEvents.FocusListener lst = new FieldEvents.FocusListener() {
            @Override
            public void focus(FieldEvents.FocusEvent event) {
            }
        };
        return lst;
    }

    public void setRefreshButton() {
        leftComponent.addComponent(refreshButton);
    }

    private final void preparingUI() {
        vFilter = new VerticalLayout();
        vFilter.setVisible(false);
        addButton = new Button(param.getLabel(Constants.LABEL_NEW), this);
        updateButton = new Button(param.getLabel(Constants.LABEL_UPDATE), this);
        deleteButton = new Button(param.getLabel(Constants.LABEL_DELETE), this);
        printButton = new Button(param.getLabel(Constants.LABEL_PRINT), this);
        downloadButton = new Button(param.getLabel("default.download"), this);
        uploadButton = new Button(param.getLabel("default.upload"), this);
        filterButton = new Button("", this);
        refreshButton = new Button("", this);
        filterButton.setId("filterId");


        String image = TA.getCurrent().getClientInfo().getSessionProcessImageId();
        if (image == null) image = "layouts/images/16/091.png";

        addButton.setIcon(new ThemeResource(Constants.ADD_ICON));
        updateButton.setIcon(new ThemeResource("layouts/images/16/pencil.png"));
        deleteButton.setIcon(new ThemeResource(Constants.DELETE_ICON));
        printButton.setIcon(new ThemeResource(Constants.PRINT_ICON));
        downloadButton.setIcon(new ThemeResource("layouts/images/16/174.png"));
        uploadButton.setIcon(new ThemeResource("layouts/images/16/173.png"));
        filterButton.setIcon(new ThemeResource("layouts/images/filter.png"));
        refreshButton.setIcon(new ThemeResource(Constants.REFRESH_ICON));
        filterButton.setWidth("20px");
        filterButton.setPrimaryStyleName("filterbtn");
        leftComponent.addComponent(filterButton);
        leftComponent.setComponentAlignment(filterButton, Alignment.TOP_LEFT);
        refreshButton.setPrimaryStyleName("refreshbtn");
        refreshButton.setWidth("20px");

        searchButton = new Button(param.getLabel("default.search"), this);
        searchButton.setIcon(new ThemeResource("layouts/images/16/049.png"));

        buttonLayout = new HorizontalLayout();
        configureToolbarBefore();
        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(updateButton);
        buttonLayout.addComponent(deleteButton);
        buttonLayout.addComponent(printButton);
        buttonLayout.addComponent(downloadButton);
        buttonLayout.addComponent(uploadButton);

        printButton.setVisible(false);
        downloadButton.setVisible(false);
        uploadButton.setVisible(false);
        filterButton.setVisible(false);
        configureToolbarAfter();
//        topHz.addComponent(favButton);
        searchitem = createSearchLayout();

        vltop = new VerticalLayout();

        renderUI();
    }

    private void renderUI() {
        vl = new VerticalLayout();

//        Label space1 = new Label("");
//        Label space2 = new Label("");
//        space1.setHeight("5px");
//        space2.setHeight("1px");
//        vltop.addComponent(space1);
//        vltop.addComponent(buttonLayout);
//        vltop.addComponent(space2);

        HorizontalLayout hztop = new HorizontalLayout();
        hztop.addComponent(buttonLayout);
//        hztop.setMargin(new MarginInfo(false, true, false, true));

        hztop.addComponent(searchitem);
        hztop.setComponentAlignment(buttonLayout, Alignment.MIDDLE_LEFT);
        hztop.setComponentAlignment(searchitem, Alignment.MIDDLE_RIGHT);
        hztop.setPrimaryStyleName("buttonHeaderArea");

        hztop.setWidth("100%");

        registerHeaderComponent(hztop, true);

        HorizontalLayout hzm = new HorizontalLayout();
        HorizontalLayout hzm2 = new HorizontalLayout();
        HorizontalLayout hzm3 = new HorizontalLayout();


        vFilterBox.addComponent(vFilter);
        vFilterBox.setPrimaryStyleName("filterBox-disable");


        Label spaceW = new Label("");
        spaceW.setWidth("4px");
        hzm.setMargin(new MarginInfo(true, true, true, false));
        hzm2.addComponent(vFilterBox);
        hzm2.addComponent(spaceW);
        hzm3.addComponent(listComponent);

        hzm.addComponent(hzm2);
        hzm.addComponent(hzm3);

        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(topArea);
        vl.addComponent(hzm);
//        vl.setSizeFull();
        vl.setExpandRatio(hzm, 1.0f);
        setCompositionRoot(vl);
    }

    public HorizontalLayout getTopLayout() {
        return topArea;
    }


    public void configureToolbarBefore() {
    }

    public void configureToolbarAfter() {
    }

    public HorizontalLayout getButtonArea() {
        return buttonLayout;
    }


    public VerticalLayout getFilterArea() {
        return vFilter;
    }

    public void removeSearchLayout() {
        searchitem.setVisible(false);
    }

    public void removeTopLayout() {
        vltop.setVisible(false);
    }

    private Component createSearchLayout() {
        HorizontalLayout layoutSearch = new HorizontalLayout();
        textSearch = new TextField();
        textSearch.setStyleName("input-medium search-query placeholder=\"Search\"");
        textSearch.addShortcutListener(new ShortcutListener("textSeacrList" + getId(), ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                if (target.equals(textSearch)) {
                    doSearch();
                }
            }
        });
        textSearch.setInputPrompt(param.getLabel("default.searchBy"));
//        Label labelSearch = new Label(param.getLabel("default.searchBy"));
//        labelSearch.setStyleName("");
        Label space = new Label("");
        space.setWidth("10px");

//        layoutSearch.addComponent(filterButton);
        layoutSearch.addComponent(space);
//        layoutSearch.addComponent(labelSearch);
        layoutSearch.addComponent(textSearch);
        searchButton.setPrimaryStyleName("btn");
        layoutSearch.addComponent(searchButton);
        layoutSearch.setSpacing(true);
        layoutSearch.setMargin(new MarginInfo(false, false, false, true));

//        layoutSearch.setComponentAlignment(labelSearch, new Alignment(AlignmentInfo.Bits.ALIGNMENT_VERTICAL_CENTER | AlignmentInfo.Bits.ALIGNMENT_LEFT));
        return layoutSearch;
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        System.out.println("_______________________________________________________________________________________________com.tenma.common.gui.display.TenmaList.buttonClick");
        if (clickEvent.getButton().equals(addButton))
            clickModify(TenmaMasterFormModify.ADD_MODE);
        else if (clickEvent.getButton().equals(updateButton))
            clickModify(TenmaMasterFormModify.UPDATE_MODE);
        else if (clickEvent.getButton().equals(deleteButton))
            clickDelete();
        else if (clickEvent.getButton().equals(searchButton)) {
            TenmaMonitor.getInstance().pageLoaded();
            doSearch();
        } else if (clickEvent.getButton().equals(printButton)) {
            TenmaMonitor.getInstance().pageLoaded();
            doPrint();
        } else if (clickEvent.getButton().equals(downloadButton)) {
            doDownload();
        } else if (clickEvent.getButton().equals(uploadButton)) {
            doUpload();
        } else if (clickEvent.getButton().equals(filterButton)) {
            doFilter();
        } else if (clickEvent.getButton().equals(refreshButton)) {
            dorefresh();
        }
    }

    public void dorefresh() {
        refreshTable();
    }


    private void clickModify(int update_mode) {
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

    /**
     * must override this method to perform checking before do deletion
     *
     * @return
     */
    public boolean checkBeforeDelete() {
        return true;
    }

    private void clickDelete() {
        try {
            boolean cont = isRowSelected();

            if (cont && checkBeforeDelete()) {
                UI.getCurrent().getUI().addWindow(new ConfirmationDialog(
                        param.getLabel(Constants.LABEL_DELETE),
                        param.getLabel("delete.confirm"),
                        param.getLabel("default.yes"),
                        param.getLabel("default.no"),
                        new ConfirmationDialog.Callback() {
                            public void onDialogResult(boolean okChoose) {
                                if (okChoose) {
                                    try {
                                        int res = doDeletion();
                                        if (res != 0) {
                                            setPageIndex(0);
                                            refreshTable();
                                            TenmaMonitor.getInstance().pageLoaded();
                                            commonMessageHandler(Notification.Type.TRAY_NOTIFICATION, CrudCode.DELETE, param.getLabel("system.info.success"), param.getLabel("system.info.delete"));
                                        } else
                                            commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.DELETE, param.getLabel("system.error.delete"), null);

                                    } catch (Exception err) {
                                        err.printStackTrace();
                                        commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.DELETE, param.getLabel("system.error.delete"), null);
                                    }
                                }

                            }
                        }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addListComponent(Component list) {

        this.listComponent = list;
        renderUI();
    }

    // replace addComponent ( component, "contentButton")
    public void addButtonComponent(Component btn) {
        vltop = new VerticalLayout();
//        vltop.addComponent(topHz);
        vltop.addComponent(btn);
//        vltop.addComponent(searchitem);
        addListComponent(listComponent);
    }

    // replace addComponent ( component, "contentSearch")
    public void addSearchComponent(Component search) {
        vltop = new VerticalLayout();
//        vltop.addComponent(topHz);
//        vltop.addComponent(buttonLayout);
        vltop.addComponent(search);
//        layoutTopPanel.setContent(vltop);
//        vl.addComponent(layoutTopPanel);
//        if (list!=null) vl.addComponent(list);
//        layoutPanel.setContent(vl);
//        setCompositionRoot(layoutPanel);
        addListComponent(listComponent);
    }


    public final void setTitle(String titleLabelCode) {
        StringBuilder contentTitle = new StringBuilder();
        contentTitle
                .append(param.getLabel(titleLabelCode))
                .toString();
        HorizontalLayout hz = new HorizontalLayout();
        Label l = new Label(contentTitle.toString());
        l.setPrimaryStyleName("formtitle");
        iconBtn.setIcon(new ThemeResource(Constants.LIST_TITLE_ICON));
        iconBtn.setPrimaryStyleName("titleicon");
        iconBtn.setWidth("25px");
        hz.addComponent(iconBtn);
        hz.addComponent(l);
        topMapLy.setRoadMap(roadMap);
        topMapLy.generateLayout(hz);
        topMapLy.setStyleName("header-workarea");
        topMapLy.setWidth("100%");
        setTittle(topMapLy);
    }

    public final void setTitle(String parentTitle, String myTitle) {
        StringBuilder contentTitle = new StringBuilder();
        contentTitle
                .append(param.getLabel(myTitle))
                .append(" [").append(param.getLabel(parentTitle)).append("]")
                .toString();
        Label l = new Label(contentTitle.toString());

        l.setStyleName("formtitle");
        topMapLy.addComponent(l);
        setTittle(topMapLy);
    }

    public Layout getSearchLayout() {
        return (Layout) searchitem;
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

    public Button getUploadButton() {
        return uploadButton;
    }

    public Button getDownloadButton() {
        return downloadButton;
    }

    //TODO ADD GETTER FOR PRINT
    public Button getPrintButton() {
        return printButton;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Button getFilterButton() {
        return filterButton;
    }

    public void removeComponent() {

    }

    public void addComponent(Component component) {

    }

    //    public abstract void doModify(TenmaPanel parentContainer, int update_mode);
    public abstract void doModify(TenmaPanel parentContainer, int update_mode);

    public abstract int doDeletion() throws Exception;

    public abstract void refreshTable();

    public void prepareFilterLayout() {

    }

    public abstract void executingDataPreparation();

    public abstract void doSearch();


    public void doPrint() {
        Notification.show("OVERRIDE THIS METHOD TO PRINT");
    }

    public void doFilter() {
        if (clickButton == 0) {
            clickButton = 1;
            vFilter.setVisible(true);
            vFilterBox.setPrimaryStyleName("filterBox");
        } else {
            clickButton = 0;
            vFilter.setVisible(false);
            vFilterBox.setPrimaryStyleName("filterBox-disable");
        }
    }

    private void doUpload() {

    }

    public void doDownload() {

    }


    public final void setShowTitle(boolean isShow) {
        topMapLy.setVisible(isShow);
    }

}

