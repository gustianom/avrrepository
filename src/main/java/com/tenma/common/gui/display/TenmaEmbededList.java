package com.tenma.common.gui.display;

import com.tenma.common.TA;
import com.tenma.common.util.CommonPaging;
import com.tenma.common.util.CommonPagingListener;
import com.tenma.common.util.Constants;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;


/**
 * User: anom
 * Date: 10/27/12
 * Time: 3:51 PM
 */
public abstract class TenmaEmbededList extends TenmaPanel implements CommonPagingListener {
    protected CommonPaging paging;
    private Button addButton;
    private Button updateButton;
    private TextField textSearch;
    private VerticalLayout vl;
    private Button searchButton;
    private HorizontalLayout buttonLayout;
    private HorizontalLayout searchLayout;
    private Component listComponent;


    public TenmaEmbededList() {

        paging = new CommonPaging(this);
        preparingTenmaListLayout();
    }

    private void preparingTenmaListLayout() {
        vl = new VerticalLayout();
        vl.setStyleName("templateList");
        initialize();
        preparingData();
        preparingUI();
        executingDataPreparation();

    }

    @Override
    public void refreshUI() {
        refreshTable();
    }

    public abstract void initialize();

    public void preparingData() {
    }

    private void preparingUI() {

        addButton = new Button(param.getLabel(Constants.LABEL_NEW), this);
        updateButton = new Button(param.getLabel(Constants.LABEL_UPDATE), this);

        String image = TA.getCurrent().getClientInfo().getSessionProcessImageId();
        if (image == null) image = "layouts/images/16/091.png";

        addButton.setIcon(new ThemeResource(Constants.ADD_ICON));
        updateButton.setIcon(new ThemeResource("layouts/images/16/pencil.png"));

        searchButton = new Button(param.getLabel("default.search"), this);
        searchButton.setIcon(new ThemeResource("layouts/images/16/049.png"));

        updateUI();

    }

    private void updateUI() {
        buttonLayout = new HorizontalLayout();

        configureToolbarBefore();
        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(updateButton);

        configureToolbarAfter();
        searchLayout = createSearchLayout();

        VerticalLayout vComponent = new VerticalLayout();
        vComponent.setSpacing(true);
        vComponent.addComponent(listComponent);
        vComponent.addComponent(buttonLayout);
        vComponent.setComponentAlignment(buttonLayout, Alignment.BOTTOM_RIGHT);

        vl = new VerticalLayout();
        vl.addComponent(searchLayout);
        vl.setMargin(true);
        vl.addComponent(paging);
        vl.addComponent(vComponent);
        vl.setWidth("100%");
        listComponent.setWidth("100%");
        setCompositionRoot(vl);
        setWidth("100%");
    }

    public void addListComponent(Component list) {
        listComponent = list;
        preparingUI();
    }


    public void configureToolbarBefore() {
    }

    public void configureToolbarAfter() {
    }

    public HorizontalLayout getButtonArea() {
        return buttonLayout;
    }

    public void removeSearchLayout() {
        searchLayout.removeAllComponents();
    }

    private HorizontalLayout createSearchLayout() {
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
        Label space = new Label("");
        space.setWidth("10px");

        layoutSearch.addComponent(space);
        layoutSearch.addComponent(textSearch);
        searchButton.setPrimaryStyleName("btn");
        layoutSearch.addComponent(searchButton);
        layoutSearch.setSpacing(true);
        return layoutSearch;
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        if (clickEvent.getButton().equals(searchButton)) {
            TenmaMonitor.getInstance().pageLoaded();
            doSearch();
        }
    }


    public boolean checkBeforeDelete() {
        return true;
    }


    public Layout getSearchLayout() {
        return (Layout) searchLayout;
    }

    public void replaceSearchLayout(Component newSearchItem) {
        System.out.println("share.tenma.common.gui.display.TenmaEmbededList.replaceSearchLayout");
        searchLayout.removeAllComponents();
        searchLayout.addComponent(newSearchItem);
    }

    public TextField getTextSearch() {
        return textSearch;
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


    public abstract void refreshTable();

    public abstract void executingDataPreparation();

    public abstract void doSearch();


}

