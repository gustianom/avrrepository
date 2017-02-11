package com.tenma.common.gui.display.component;

import com.tenma.common.TA;
import com.tenma.common.gui.DialogModal;
import com.tenma.common.util.CommonPaging;
import com.tenma.common.util.CommonPagingListener;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.util.Collection;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: sigit
 * Date: 5/11/13
 * Time: 5:22 PM
 */

@Deprecated  // use TenmaLookupField and make extend implementation
public class TenmaFieldWithButton extends CustomComponent implements Button.ClickListener {
    private static int FIRST_COL = 0;
    private TextField value = new TextField();

    private Button button;
    private Item itemField;
    private String visibleValueOnText;
    private String[] header;
    private Object temporaryValue;
    private TenmaTableContainer tenmaTableContainer;
    private HorizontalLayout content;
    //    private VerticalLayout mainLayout;
    private String dialogCaption = "";
    private TenmaDialogInterface dialogInterface;
    private TenmaFieldWithButtonInterface container;
    private AbstractField filterComponent;
    private boolean newInterface;
    private Table tableModify = new Table();
    private boolean refresh = false;


    @Deprecated
    public TenmaFieldWithButton() {
        button = new Button(TA.getCurrent().getParams().getLabel("default.view"), this);
        this.newInterface = false;
        this.visibleValueOnText = null;
        this.tenmaTableContainer = null;

        initLayout();
    }

    public TenmaFieldWithButton(TenmaDialogInterface tenmaTableContainer, String defaultValue) {
        button = new Button(TA.getCurrent().getParams().getLabel("default.view"), this);
        this.visibleValueOnText = defaultValue;
        this.newInterface = false;
        this.dialogInterface = tenmaTableContainer;

        initLayout();
    }

    public TenmaFieldWithButton(TenmaFieldWithButtonInterface tenmaTableContainer, String defaultValue) {
        button = new Button(TA.getCurrent().getParams().getLabel("default.view"), this);
        this.visibleValueOnText = defaultValue;
        this.newInterface = true;
        this.container = tenmaTableContainer;

        initLayout();
    }


    private void initLayout() {
        content = new HorizontalLayout();
        content.setWidth("125px");
        value.setEnabled(false);
//        mainLayout = new VerticalLayout();
//        mainLayout.setSpacing(true);
//        mainLayout.addComponent(value);
//        content.addComponent(mainLayout);
        content.addComponent(value);
        button.setVisible(true);
        content.addComponent(button);
        content.setSizeFull();
        content.setSizeUndefined();
        setCompositionRoot(content);

    }

    public String getValue() {
        return getTextValue().getValue();
    }

    public void setValue(Object o) {
        value.setValue(o.toString());
    }

    @Deprecated
    public void addValidator(Validator validator) {
        value.addValidator(validator);
    }

    public void addValueChangeListener(Property.ValueChangeListener property) {
        value.addValueChangeListener(property);
    }

    @Override
    public void focus() {
        value.focus();
    }

    public Button getButton() {
        return button;
    }

    public TextField getTextValue() {
        return value;
    }

    public Item getData() {
        return itemField;
    }

    @Override
    public void setData(Object object) {

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("TenmaFieldWithButton.setData");
        if (!newInterface) {
            itemField = dialogInterface.container().getItem(object);
            temporaryValue = object;
            if (itemField != null)
                doSelectedItem();
        } else {

        }

    }

    @Deprecated
    public void setHeader(String[] header) {
        this.header = header;
    }

    public Object getTemporaryValue() {
        return temporaryValue;
    }

    public void setWindowCaption(String caption) {
        dialogCaption = caption;
    }


    public void setContainer(TenmaDialogInterface tenmaTableContainer) {
        newInterface = false;
        refresh = true;
        this.dialogInterface = tenmaTableContainer;
    }

    private void doSelectedItem() {

        if (visibleValueOnText != null)
            setValue(itemField.getItemProperty(visibleValueOnText));
        else {
            Collection collection = itemField.getItemPropertyIds();
            setValue(itemField.getItemProperty(collection.toArray()[FIRST_COL]));
        }
    }

    public void filterComponent(final AbstractField filter) {
        filterComponent = filter;
    }


    public void addFilterChangeListener(final filterChangeListener filterChangeListener) {
        if (filterComponent != null) {
            filterComponent.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    tenmaTableContainer = filterChangeListener.doRefresh(event.getProperty().getValue());
                    tableModify.setContainerDataSource(tenmaTableContainer);

                    tableModify.commit();
                    tableModify.setPageLength(tenmaTableContainer.size());
                }
            });
        }

    }

    //    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        if (refresh) {
            if (!newInterface) {
                tenmaTableContainer = dialogInterface.container();
                refresh = false;
            }

        }
        UI.getCurrent().getUI().addWindow(new InnerDialog());

    }


    public static interface filterChangeListener {
        TenmaTableContainer doRefresh(Object items);
    }

    private class InnerDialog extends DialogModal implements CommonPagingListener {
        private Button select;
        private Button cancel;
        private CommonPaging paging;


        private InnerDialog() {
            super();
            this.setCaption(dialogCaption);
            preparingUI();
            preparingTable();

        }

        private void removeDialogModal() {
            UI.getCurrent().getUI().removeWindow(this);
        }


        public void preparingTable() {
            tableModify.setSelectable(true);
            tableModify.setImmediate(true);
            tableModify.setContainerDataSource(tenmaTableContainer);
            tableModify.setValue(temporaryValue);

//            int containerRow = tenmaTableContainer.getContainerPropertyIds().size();
//            String[] headers = dialogInterface.stringHeader() ;
            tableModify.setPageLength(tenmaTableContainer.size());
            if (header != null) {
                tableModify.setColumnHeaders(header);
            }
            tableModify.addValueChangeListener(new Property.ValueChangeListener() {
                public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                    Object value = valueChangeEvent.getProperty().getValue();
                    if (value != null) {
                        itemField = tableModify.getContainerDataSource().getItem(value);
                        select.setEnabled(true);
                    } else {
                        itemField = null;
                        select.setEnabled(false);
                    }
                    temporaryValue = value;
                }
            });

        }

        private void preparingUI() {
//            CustomLayout layout = new CustomLayout("templateList");
            cancel = new Button(param.getLabel(Constants.LABEL_CANCEL), this);
            select = new Button(param.getLabel("task.select"), this);
            select.setIcon(new ThemeResource("layouts/images/apply.png"));
            cancel.setIcon(new ThemeResource("layouts/images/16/118.png"));
            select.setEnabled(false);
            HorizontalLayout hr = new HorizontalLayout();
            hr.addComponent(select);
            hr.addComponent(cancel);
            VerticalLayout verLay = new VerticalLayout();


            if (tenmaTableContainer == null) {
                if (newInterface) {
                    paging = new CommonPaging(this);
                    paging.setPageIndex(1);
                    verLay.addComponent(paging);
                    paging.setTotalDataRows(container.maxTotalSize());
                    tenmaTableContainer = container.container(1, TA.getCurrent().getParams().getPageSize(), paging);

                } else {
                    tenmaTableContainer = dialogInterface.container();
                }
            }
            if (filterComponent != null) {
                HorizontalLayout filterContent = new HorizontalLayout();

                Label filetlbl = new Label("Filter by:");
                filterContent.addComponent(filetlbl);
                filterContent.addComponent(filterComponent);
                verLay.addComponent(filterContent);
            }

            verLay.addComponent(tableModify);
            VerticalLayout v = new VerticalLayout();
//            layout.addComponent(hr, "contentButton");
//            layout.addComponent(verLay, "contentList");
            v.addComponent(hr);
            v.addComponent(verLay);
            this.setContent(v);
        }

        public void buttonClick(Button.ClickEvent clickEvent) {
            Button b = clickEvent.getButton();
            if (b.equals(select)) {
                doSelectedItem();
                removeDialogModal();
            } else {
                removeDialogModal();
            }
        }

        @Override
        public void refreshUI() {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            int index = paging.getPageIndex();
            int pageSize = param.getPageSize();
            Integer skip = new Integer((index <= 1 ? 0 : index - 1) * pageSize);
            System.out.println("TenmaFieldWithButton$InnerDialog.refreshUI - " + paging.getPageIndex() + " -" + param.getPageSize() + " -" + skip);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            tenmaTableContainer.removeAllItems();
            paging.setTotalDataRows(container.maxTotalSize());
            tenmaTableContainer = container.container(skip, pageSize, paging);
            tableModify.setContainerDataSource(tenmaTableContainer);
            tableModify.commit();
//            tableModify.setContainerDataSource();
//            tableModify.commit();
        }
    }
}