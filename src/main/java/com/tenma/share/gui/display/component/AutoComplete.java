package com.tenma.share.gui.display.component;

import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.vaadin.resetbuttonfortextfield.ResetButtonClickListener;
import org.vaadin.resetbuttonfortextfield.ResetButtonForTextField;

import java.util.List;

/**
 * Currently version 0.2
 * <p/>
 * version 0.1
 * Created by ndwijaya on 11/29/14.
 * <p/>
 * version 0.2
 * - change extend customComponent to AbstractField
 * - support FieldGroup integration
 * - support validator
 * <p/>
 * version 0.3
 * - integration ready with TenmaLookupField version 0.4
 * <p/>
 * version 0.4
 * - support centralized cache for mass component used for performance and memory optimization
 * <p/>
 * version 0.5
 * - option to load content from file/object rather than from database for on memory autocomplete lookup
 */


public abstract class AutoComplete extends CustomField implements Button.ClickListener {
    public TenmaTableContainer container;
    public List<Object> resultset;
    public Button btn = new Button();
    protected HorizontalLayout layout = new HorizontalLayout();
    protected Panel popUpLayout;
    protected int top = 0;
    protected PopupView popupView;
    protected TextField lookupTextId;
    protected Table resultsTable;
    protected TextField filterFld;
    protected boolean tableRowSelected;
    private String inputValue;
    private boolean autoSuggestListening = true;
    private String parm;
    private boolean showResetButton = false;


    protected AutoComplete(String param, Boolean showResetButton) {
        this.parm = param;
        this.showResetButton = showResetButton;
        lookupTextId.setValidationVisible(false);
        lookupTextId.setNullRepresentation("");
    }


    protected AutoComplete() {

        initTable();
        initAutoSuggestFld();
        initFilterFld();
        initPopUpLayout();
        initPopUpView();
        registerListeners();

        layout.addComponent(lookupTextId);
        layout.addComponent(popupView);
        layout.addComponent(btn);
        btn.setStyleName("btnIcon");
        btn.setIcon(new ThemeResource("images/16/search.png"));
        lookupTextId.setWidth("120px");
        btn.addClickListener(this);
    }


    public TenmaTableContainer getContainer() {
        return container;
    }

    public void setContainer(TenmaTableContainer container) {
        this.container = container;
    }

    public List<Object> getResultset() {
        return resultset;
    }

    @Override
    public float getWidth() {
        return layout.getWidth();
    }

    public void setWidth(String width) {
        lookupTextId.setWidth(width);
    }

    @Override
    public Class getType() {
        return lookupTextId.getType();
    }

    @Override
    protected Component initContent() {
        return layout;
    }

    public void setPropertyDS(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);
//        System.out.println("AutoComplete.setPropertyDS");
    }

    @Override
    public void setPropertyDataSource(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);
    }

    abstract protected void initTable();

    abstract protected List<Object> getList(String inputKey);

    public void setTopMargin(int top) {
//        System.out.println("top = " + top);
        Page.Styles styles = Page.getCurrent().getStyles();
        StringBuilder buf = new StringBuilder();
        buf.append("div.boxpopup {");
        buf.append(" position: relative; ");
        buf.append(" float : right; ");
        buf.append(" top : ");
        buf.append(top);
        buf.append("px;");
        buf.append("}");
        styles.add(buf.toString());
//        System.out.println(buf.toString());
        popupView.setPrimaryStyleName("boxpopup");
    }

    public void focus() {
        lookupTextId.focus();
    }

    void registerListeners() {
        registerAutoSuggestFldListeners();
        registerPopUpViewListeners();
        registerFilterFldListeners();
    }

    private void registerFilterFldListeners() {
        filterFld.setImmediate(true);
        filterFld.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        filterFld.addTextChangeListener(createPartnerKeyLisneter());
        ResetButtonForTextField.extend(filterFld);
    }

    private void registerPopUpViewListeners() {
        popupView.addPopupVisibilityListener(new PopupView.PopupVisibilityListener() {
            public void popupVisibilityChange(PopupView.PopupVisibilityEvent event) {
                if (!event.isPopupVisible()) {
                    if (!tableRowSelected) {
                        autoSuggestListening = false;
                        lookupTextId.setValue("");
                        autoSuggestListening = true;
                    }
                    tableRowSelected = false;
                    filterFld.setValue("");
                }
            }
        });
    }

    private FieldEvents.TextChangeListener createPartnerKeyLisneter() {
        FieldEvents.TextChangeListener l = new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                System.out.println(event.getText());
                inputValue = event.getText();
                resultset = getList(inputValue);
                setResultsTableValues();
                margincalculation();
            }
        };
        return l;
    }

    private void registerAutoSuggestFldListeners() {
        lookupTextId.addTextChangeListener(new FieldEvents.TextChangeListener() {
            public void textChange(FieldEvents.TextChangeEvent event) {
                if (event.getText().length() > 0
                        && autoSuggestListening) {
                    popupView.setPopupVisible(true);
                    filterFld.setValue(event.getText());
                    filterFld.focus();
                    filterFld.setCursorPosition(lookupTextId
                            .getCursorPosition());
                    resultset = getList(filterFld.getValue());
                    setResultsTableValues();
                    margincalculation();
                }
            }
        });
    }


    void initAutoSuggestFld() {
        if (parm != null) {
            lookupTextId = new TextField(parm);
        } else {
            lookupTextId = new TextField();
        }
        lookupTextId.setImmediate(true);
//        if (showResetButton) {
        ResetButtonForTextField resetButton = ResetButtonForTextField.extend(lookupTextId);
        ResetButtonClickListener listener = new ResetButtonClickListener() {
            @Override
            public void resetButtonClicked() {
//                    System.out.println("remove is click");
                resetButtonEvent();
            }
        };
        resetButton.addResetButtonClickedListener(listener);
//        }
    }

    public void resetButtonEvent() {
        System.out.println("Override public void resetButtonAction() to capture reset event");
    }

    void initFilterFld() {
        filterFld = new TextField();
        filterFld.setWidth("300px");
        filterFld.setImmediate(true);
    }

    void initPopUpView() {
        popupView = new PopupView(null, popUpLayout);
        popupView.setHideOnMouseOut(false);
    }

    void initPopUpLayout() {
        popUpLayout = new Panel();
        popUpLayout.setPrimaryStyleName("popupLayout");
        popUpLayout.setWidth("300px");
        VerticalLayout v = new VerticalLayout();
        v.addComponent(filterFld);
        v.addComponent(resultsTable);
        popUpLayout.setContent(v);
        resultsTable.setSizeFull();
    }

    private void margincalculation() {
        int topm = resultset.size() / 2 * 15;
        setTopMargin(topm);
    }

    abstract public void setResultsTableValues();

    @Override
    abstract public Object getValue();

    @Override
    public void validate() throws Validator.InvalidValueException {
        super.validate();
    }

    @Override
    abstract public boolean isValid();
}
