package com.tenma.common.gui.display.custom;

import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.component.ConfirmationDialog;
import com.tenma.common.util.CommonPaging;
import com.tenma.common.util.CommonPagingListener;
import com.tenma.common.util.Constants;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.HashMap;

/**
 * Created by Andrian
 * on 23/06/15  10:36
 */
public abstract class TenmaPanelCustomList extends TenmaPanel implements CommonPagingListener {

    private Panel headerPanel;
    private HorizontalLayout pHeader, hFilter, hButtonLay, hTitle, hPaging;
    private Button searchButton, back2HomeButton, addButton, updateButton, deleteButton;
    private CommonPaging commonPaging;
    private TextField tSearchText = new TextField();
    private VerticalLayout vHeader;
    private TenmaCustomFilter filterBtn;
    private String menuGroupId = "default";
    private Table table;


    public TenmaPanelCustomList() {
        super();
        initializeUI();
    }


    private void initializeUI() {

        table = new Table();
        table.setSizeFull();
        commonPaging = new CommonPaging(this);
        headerPanel = new Panel();
        vHeader = new VerticalLayout();
        hButtonLay = new HorizontalLayout();
        hPaging = new HorizontalLayout();
        pHeader = new HorizontalLayout();
        hTitle = new HorizontalLayout();
        tSearchText.setInputPrompt(param.getLabel("default.searchBy"));
        searchButton = new Button(param.getLabel("default.search"), this);


        back2HomeButton = new Button("", this);


        addButton = new Button("", this);
        updateButton = new Button("", this);
        deleteButton = new Button("", this);

        HorizontalLayout hSearch = getSearchArea();

        hFilter = new HorizontalLayout();
        hFilter.setSpacing(true);

        configureFooterBefore();
        hPaging.addComponent(commonPaging);
        hPaging.addComponent(hSearch);
//        hPaging.setWidth(800, Unit.PIXELS);
        hPaging.setWidth("100%");
        hPaging.setComponentAlignment(commonPaging, Alignment.MIDDLE_LEFT);
        hPaging.setComponentAlignment(hSearch, Alignment.BOTTOM_RIGHT);
        hPaging.setMargin(new MarginInfo(false, false, false, true));


        configureToolbarBefore();
        hButtonLay.addComponents(addButton, updateButton, deleteButton, back2HomeButton);
        hButtonLay.setMargin(false);
        configureToolbarAfter();


        back2HomeButton.setDescription(getLabel("default.back"));
        addButton.setDescription(getLabel("default.add"));
        deleteButton.setDescription(getLabel("default.delete"));
        updateButton.setDescription(getLabel("default.update"));

        filterBtn = new TenmaCustomFilter(this);
        filterBtn.setVisible(false);

        hFilter.addComponent(filterBtn);

//        hPaging.setWidth(800, Unit.PIXELS);
        headerPanel.setWidth("100%");
        pHeader.setWidth(100, Unit.PERCENTAGE);

        pHeader.addComponent(hTitle);
        pHeader.addComponent(hButtonLay);

        pHeader.setComponentAlignment(hTitle, Alignment.MIDDLE_LEFT);
        pHeader.setComponentAlignment(hButtonLay, Alignment.MIDDLE_RIGHT);

        System.out.println("commonPaging.getPageSize() ===============>" + commonPaging.getPageSize());
        vHeader.addComponent(pHeader);
        if (commonPaging.getPageSize() <= 10) {
            vHeader.addComponent(commonPaging);
        } else {
            vHeader.removeComponent(commonPaging);
            commonPaging.setVisible(false);
        }

        setStyle();

        headerPanel.setContent(vHeader);

    }


    public Table getTableProperty() {
        return table;
    }

    public void setHeaderCaption(Label lTitile) {
        hTitle.addComponent(lTitile);
        lTitile.setPrimaryStyleName("metroHeader");

    }


    private HorizontalLayout getSearchArea() {
        System.out.println("--------------------------------------->getSearch Area");
        HorizontalLayout hGroup = new HorizontalLayout();
//        tSearchText = new TextField();
        searchButton = new Button(param.getLabel("default.search"), this);
        hGroup.addComponent(tSearchText);
        hGroup.addComponent(searchButton);
        hGroup.setComponentAlignment(tSearchText, Alignment.MIDDLE_RIGHT);
        return hGroup;
    }

    private void setStyle() {
        back2HomeButton.setIcon(new ThemeResource(Constants.B2H_CIRCLE_ICON));
        addButton.setIcon(new ThemeResource(Constants.BUTTON_CIRCLE_ICON.ADD.getValue()));
        deleteButton.setIcon(new ThemeResource(Constants.BUTTON_CIRCLE_ICON.DELETE.getValue()));
        updateButton.setIcon(new ThemeResource(Constants.BUTTON_CIRCLE_ICON.UPDATE.getValue()));
        searchButton.setIcon(new ThemeResource(Constants.BUTTON_CIRCLE_ICON.SEARCH.getValue()));
        searchButton.setPrimaryStyleName("btn");
        headerPanel.setPrimaryStyleName("metro-slate-grey");
        pHeader.setPrimaryStyleName("metro-slate-grey");
        hButtonLay.setPrimaryStyleName("metro-slate-grey");
        hPaging.setStyleName("metroFooter");
        hTitle.setPrimaryStyleName("metroHeader");
        back2HomeButton.setStyleName("circle");
        addButton.setStyleName("circle");
        updateButton.setStyleName("circle");
        deleteButton.setStyleName("circle");

    }


    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);

        if (clickEvent.getButton().equals(back2HomeButton)) {
//            tenmaApplication.goHome();


        } else if (clickEvent.getButton().equals(deleteButton)) {
            if (isRowSelected()) {
                UI.getCurrent().getUI().addWindow(new ConfirmationDialog(
                        param.getLabel(("delete.confirm")),
                        param.getLabel("default.areYouSure"),
                        param.getLabel(""),
                        param.getLabel(""),
                        new ConfirmationDialog.Callback() {
                            @Override
                            public void onDialogResult(boolean resultIsYes) {
                                if (resultIsYes) {
                                    try {
                                        doDeletion();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                ));
            }
        }
    }

    public HorizontalLayout getpHeader() {
        return pHeader;
    }

    public void setpHeader(HorizontalLayout pHeader) {
        this.pHeader = pHeader;
    }


    public void setTable(Table table) {
        this.table = table;
    }

    public abstract void doDeletion() throws Exception;

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public Button getBack2HomeButton() {
        return back2HomeButton;
    }

    public void setBack2HomeButton(Button back2HomeButton) {
        this.back2HomeButton = back2HomeButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(Button updateButton) {
        this.updateButton = updateButton;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }


    public HorizontalLayout gethTitle() {
        return hTitle;
    }

    public void sethTitle(HorizontalLayout hTitle) {
        this.hTitle = hTitle;
    }

    public HorizontalLayout getCustomFilter() {
        return hFilter;
    }

    public HorizontalLayout getCustomPaging() {
        return hPaging;
    }

    public void sethPaging(HorizontalLayout hPaging) {
        this.hPaging = hPaging;
    }

    public HorizontalLayout gethButtonLay() {
        hButtonLay.setMargin(new MarginInfo(true, false, false, false));
        return hButtonLay;
    }

    public void sethButtonLay(HorizontalLayout hButtonLay) {
        this.hButtonLay = hButtonLay;
    }

    public VerticalLayout getCustomHeader() {
        return vHeader;
    }

    public TextField getSearchText() {
        return tSearchText;
    }


    public CommonPaging getCommonPaging() {
        return commonPaging;
    }

    public void setCommonPaging(CommonPaging commonPaging) {
        this.commonPaging = commonPaging;
    }

    public Button getBtnBackMetro() {
        return back2HomeButton;
    }

    public void setBtnBackMetro(Button back2HomeButton) {
        this.back2HomeButton = back2HomeButton;
    }

    public Panel getHeaderPanel() {
        return headerPanel;
    }

    public void configureToolbarBefore() {

    }

    public void configureFooterBefore() {

    }

    public void configureToolbarAfter() {
    }

    public TenmaCustomFilter getFilter() {
        filterBtn.setVisible(true);
        return filterBtn;
    }

    public HashMap getFilterMap() {
        return filterBtn.getMap();
    }

    public void doRefresh() {
    }

    public VerticalLayout setPaperStack(VerticalLayout paperStack, String contentTitle, String caption) {

        paperStack.setWidth(800, Unit.PIXELS);
        paperStack.setHeight(80, Unit.PERCENTAGE);
        StringBuffer title = new StringBuffer()
                .append("<p>").append(param.getLabel(contentTitle))
                .append("</p>");
        Label labelMenuName = new Label(title.toString());
        labelMenuName.setContentMode(ContentMode.HTML);
        paperStack.addComponent(labelMenuName);
        paperStack.setCaptionAsHtml(true);
        paperStack.setImmediate(true);
        paperStack.setCaption(caption);
        paperStack.setMargin(true);
        return paperStack;
    }

    public void setScreenId(String menuGroupId) {
        this.menuGroupId = menuGroupId;
    }
}

