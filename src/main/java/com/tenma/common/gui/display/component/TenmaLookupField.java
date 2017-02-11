package com.tenma.common.gui.display.component;

import com.tenma.common.TA;
import com.tenma.common.util.CommonPaging;
import com.tenma.common.util.CommonPagingListener;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ndwijaya on 12/20/2014.
 * Current Version : 0.3
 * <p/>
 * <p/>
 * History & Future Development:
 * Version 0.1 created by Sigit (5.11.13)
 * - class TenmaFieldWithButton is deprecated
 * - lookup using listParameter interface & default column
 * - expose inner TextField of outside modification
 * - using TenmaWindow for inner list
 * -
 * version 0.2 is design in order to replace TenmaFieldWithButton
 * TenmaFieldWidthButton(TFWB) will be marked as Deprecated (version 0.1)
 * here are some area that need enhancement
 * - TFWB make boiler code cause valuechangelistener is outside of the component
 * - TFWB implementation required outside list parameter and make many duplicate code
 * - TFWB implementation need to be more standard and easy to be extend for diff need
 * <p/>
 * <p/>
 * Version 0.2 enhancement by ndwijaya
 * Enhancement area : (20.12.2014)
 * - build in ValueChangeListener inside the component (can be Optional)
 * - change API using Object rather than Item, the object is the expected Model of the selected row
 * - the component is defined as abstract class and need to be extend for implementation
 * - more optimized layout
 * - support custom paging size
 * <p/>
 * <p/>
 * version 0.3 (31.12.2014)
 * - include build in Validator function & Error Message
 * - FieldGroup integration ready
 * - Buildin Internal Converter
 * - support optionally (add dialog - dialog action on each implementation )
 * <p/>
 * version 0.4 shall be added these features:
 * - TBD support multiple selection (set with API)
 * - TBD possible to blend with auto complete feature (optionally)
 * setAutoComplete(
 * - TBD styling with diff background colour for Mandatory
 * - TBD styling for auto complete (not found key in)
 * <p/>
 * <p/>
 * version 0.5
 * - TBD support Keyboard shortcut, keyboard selection
 * - TBD support lazy load for better initial load response
 * <p/>
 * <p/>
 * version 0.6
 * - TBD support column sort Database refresh (Optional)
 * - TBD stabilization and bug maintenance after more than dozen implementation
 * - TBD review API for more efficient (if required)
 * - TBD optimization getModel and SetData proses
 * - TBD support multiple mixing Model
 * <p/>
 * <p/>
 * <p/>
 * version 1.0 (Nice to Have)
 * - TBD support predefine init filter
 * - TBD build in cache for better response with API
 * - TBD reusable instance trough singgleton pattern
 * - TBD more optimization API
 * - TBD support parent - detail selection (One to Many)
 * <p/>
 * version 1.1 (Advanced)
 * - silent statistically used of the component on production and may  be persitanced to file for analysis purposes
 * (must be designed with small overhead performance)
 * - hit/minute
 * - avg loadlookup
 * - number of active instance timeline
 * - memory consumed
 * - % of using autocomplete keyin vs % lookup lookupButton
 * -
 */


public abstract class TenmaLookupField extends CustomField<String> implements CommonPagingListener, Button.ClickListener {


    protected TenmaTableContainer container;
    // Autocomplete field related
    protected Panel popUpLayout;
    protected int top = 0;
    protected PopupView popupView;
    protected TextField lookupTextId;
    protected Table resultsTable;
    protected TextField filterFld;
    protected boolean tableRowSelected;
    protected String[] strCaptHeader;
    TenmaTableContainer autoContainer;
    List<Object> resultset;
    private HorizontalLayout layout = new HorizontalLayout();
    private TextField value = new TextField();
    private Button lookupButton;
    private Button addButton;
    private HashMap selectedObject = new HashMap();
    private String dialogCaption;
    private Component filterComponent;
    private Object[] visibleColumns;
    private InnerDialog innerDialog;
    private TenmaFieldButtonValueListener listener;
    private Class amodel;
    private CommonPaging paging;
    private String visibleValueOnText;
    private boolean multipleSelection = false;
    private boolean addOption = false;
    private TenmaAddLookupFieldListener addListener;
    private String inputValue;
    private boolean autoSuggestListening = true;
    private String parm;
    private boolean isauto = false;
    // end of autocomplete field

    public TenmaLookupField() {
        super();
    }

    public TenmaLookupField(String defaultValue, String[] strCapt) {
        lookupButton = new Button("..", this);
        strCaptHeader = strCapt;
//        lookupButton.setIcon(new ThemeResource("layouts/images/16/lookup.png"));
        addButton = new Button("", this);
        addButton.setIcon(new ThemeResource("layouts/images/16/add_16.png"));
//        lookupButton.setPrimaryStyleName("lookupFiledButton");
        addButton.setPrimaryStyleName("lookupFiledButton");
        addButton.setVisible(addOption);
        setLookupFieldButtonStyle();
        this.visibleValueOnText = defaultValue;
        container = initTableContainer();

        paging = new CommonPaging(this);
        paging.setPageIndex(1);
        innerDialog = new InnerDialog("");
        initLayout();
        value.setValidationVisible(false);
        value.setNullRepresentation("");
    }

    public void addOptionListner(TenmaAddLookupFieldListener addListener) {
        this.addListener = addListener;
        addOption = true;
        addButton.setVisible(addOption);
    }

    public void addAutoComplete(boolean auto) {
        isauto = auto;
    }


    public void setLookupFieldButtonStyle() {
        Page.Styles styles = Page.getCurrent().getStyles();
        StringBuffer buf = new StringBuffer();
        buf.append(".lookupFiledButton {");
        buf.append("   border: none;");
        buf.append("   width : 20px;");
        buf.append("   cursor: pointer;");
        buf.append(" }");
        buf.append(".lookupFiledButton .v-icon {");
        buf.append("   vertical-align: middle;");
        buf.append(" }");
        buf.append(" ");
        styles.add(buf.toString());
    }

    @Override
    public Class getType() {
        return value.getType();
    }

    public void setMultipleSelection(boolean multiple) {
        this.multipleSelection = true;
    }

    @Override
    protected Component initContent() {
        return layout;
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        super.validate();
        if (isRequired()) {
            if (selectedObject.size() == 0)
                throw new Validator.InvalidValueException(visibleValueOnText + " is not selected");
        }
    }

    @Override
    public boolean isValid() {
        boolean rs = false;
        if (selectedObject.size() > 0)
            rs = true;
        System.out.println("Lookup Filed " + getCaption() + " = " + rs);
        return rs;
    }

    private void initLayout() {
        layout.addComponent(value);
        layout.addComponent(lookupButton);
        layout.addComponent(addButton);
        value.setEnabled(false); // temporary on migration from old component
        value.addValueChangeListener(createValueChangeListener());
    }

    public void setPropertyDS(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);
        System.out.println("TenmaLookupField.setPropertyDS");
    }

    @Override
    public abstract void setPropertyDataSource(Property newDataSource);

//    @Override
//    public abstract Property getPropertyDataSource();

    public void addValueChangeListner(TenmaFieldButtonValueListener alistner, Class model) {
        this.listener = alistner;
        this.amodel = model;
    }

    private ValueChangeListener createValueChangeListener() {
        ValueChangeListener listener = new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                valueChangeListener(event);
            }
        };
        return listener;
    }

    private void valueChangeListener(Property.ValueChangeEvent event) {
        if (listener != null) {
            listener.valueChangeListener(getModel(amodel));
        }
    }

    public void focus() {
        value.focus();
    }

    public void filterComponent(Component filter) {
        filterComponent = filter;
    }

    /*
        set Object Model into the component
     */
    public void setData(Object object) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("TenmaLookupField.setData");
        Method[] methods = object.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("get")) {
                String field = method.getName().substring(3, method.getName().length());
                try {
                    Object value = method.invoke(object);
                    selectedObject.put(field.toLowerCase(), value);
                    System.out.println(field + " " + value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        doSelectedItem();
    }

    public void setWindowCaption(String caption) {
        dialogCaption = caption;
        innerDialog.setCaption(dialogCaption);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(lookupButton)) {
            refreshUI();
            innerDialog.setVisibleColumn(visibleColumns);
            innerDialog.center();
            UI.getCurrent().getUI().addWindow(innerDialog);
        } else if (event.getButton().equals(addButton)) {
            addListener.addAction(this);
        }
    }

    public CommonPaging getPaging() {
        return paging;
    }

    public void setVisibleColumn(Object[] visibleColumns) {
        this.visibleColumns = visibleColumns;
    }

    public abstract void reloadData();

    public abstract TenmaTableContainer initTableContainer();

    private void doSelectedItem() {
        if (visibleValueOnText != null)
            value.setValue(selectedObject.get(visibleValueOnText.toLowerCase()).toString());
        else {
            value.setValue(selectedObject.get("").toString());
        }
        parentSelect();
    }

    public void parentSelect() {
    }

    /*
        @model - give the model class as parameter than the return is object model as selected
     */
    public final Object getModel(Class model) {
        Object rs = null;
        try {
            Constructor m = model.getConstructor();
            Object o = m.newInstance();
            Method[] methods = model.getMethods();
            System.out.println("\n\n");
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getName().startsWith("set")) {
                    String field = method.getName().substring(3, method.getName().length());
                    Object val = selectedObject.get(field.toLowerCase());
                    if (val != null) {
                        System.out.println("Set " + field + " = " + val);
                        Class[] ptype = method.getParameterTypes();
                        Class type = ptype[0];
                        try {
                            method.invoke(o, val);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error column type Table vs Model set/get");
                        }
//                        }
                    }
                }
            }
            rs = o;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public void refreshUI() {
        reloadData();
    }

    class InnerDialog extends Window implements Button.ClickListener {
        private Button select;
        private Button cancel;

        private Table tableModify = new Table();

        public InnerDialog(String caption) {
            super(caption);
            preparingUI();
            preparingTable();
            setResizable(false);
            setClosable(true);
            setModal(true);
            setSizeUndefined();
            setIcon(new ThemeResource("layouts/images/16/031.png"));
        }

        public void setVisibleColumn(Object[] arg) {
            if (arg != null) {
                tableModify.setVisibleColumns(arg);
            }
        }

        private void preparingUI() {
            Panel p = new Panel();
            cancel = new Button();
            cancel.setDescription(TA.getCurrent().getParams().getLabel(Constants.LABEL_CANCEL));
            cancel.addClickListener(this);

            select = new Button();
            select.setDescription(TA.getCurrent().getParams().getLabel(Constants.LABEL_SELECT));
            select.addClickListener(this);

            select.setIcon(new ThemeResource(Constants.SELECT_ICON));
            cancel.setIcon(new ThemeResource(Constants.BACK_ICON));

            select.setStyleName("circle");
            cancel.setStyleName("circle");

            select.setEnabled(false);
            HorizontalLayout hr = new HorizontalLayout();
            hr.setSpacing(true);
            hr.addComponent(select);
            hr.addComponent(cancel);
            VerticalLayout verLay = new VerticalLayout();
            verLay.setSpacing(true);
            verLay.addComponent(paging);

            if (filterComponent != null) {
                HorizontalLayout filterContent = new HorizontalLayout();
                Label filetlbl = new Label("Filter by:");
                filterContent.addComponent(filetlbl);
                filterContent.addComponent(filterComponent);
                verLay.addComponent(filterContent);
            }

            verLay.addComponent(tableModify);
            VerticalLayout v = new VerticalLayout();
            v.setMargin(true);
            v.setSpacing(true);
            v.addComponent(verLay);
            v.addComponent(hr);
            v.setSizeUndefined();
            setContent(v);
        }

        private void preparingTable() {
            tableModify.setSelectable(true);
            tableModify.setImmediate(true);
            tableModify.setContainerDataSource(container);
            tableModify.setColumnHeaders(strCaptHeader);
            tableModify.setPageLength(container.size());
            tableModify.addValueChangeListener(new ValueChangeListener() {
                public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                    Object value = valueChangeEvent.getProperty().getValue();
                    if (value != null) {
                        Item itemField = tableModify.getContainerDataSource().getItem(value);
                        select.setEnabled(true);
                        convertToObject(itemField);
                    } else {
                        select.setEnabled(false);
                    }

                }
            });

        }

        private void convertToObject(Item item) {
            HashMap map = new HashMap();
            Collection columheader = tableModify.getContainerPropertyIds();
            Iterator iter = columheader.iterator();
            System.out.println("columheader = " + columheader);
            for (int i = 0; iter.hasNext(); i++) {
                Object o = iter.next();
                Object v = item.getItemProperty(o).getValue();
                map.put(o.toString().toLowerCase(), v);
            }
            selectedObject = map;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Button b = event.getButton();
            if (b.equals(select)) {
                System.out.println("InnerDialog.buttonClick");
                doSelectedItem();
                close();
            } else {
                close();
            }
        }

    }
}
