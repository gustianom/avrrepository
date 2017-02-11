package com.tenma.common.gui.display.custom;

import com.tenma.common.TA;
import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashMap;

/**
 * Created by Andrian
 * on 25/06/15  10:42
 */

public class TenmaCustomFilter extends CustomField implements PopupView.PopupVisibilityListener, Button.ClickListener {
    NativeSelect nSelection;
    private TenmaPanelCustomList parent;
    private Button popBtn;
    private Button addNewLineBtn;
    private Button okBtn;
    private PopupView popupView;
    private Panel popUpLayout;
    private HorizontalLayout mainLay;
    private VerticalLayout vComponent;
    private HorizontalLayout filterComp;
    private HorizontalLayout filterBy;
    private HashMap map;
    private int count = 0;
    private Button clearBtn;

    public TenmaCustomFilter() {
        initFilter();
    }

    public TenmaCustomFilter(TenmaPanelCustomList parent) {
        this.parent = parent;
        initFilter();
    }

    private void initFilter() {
        initFilterSelection();
        initPopUpLayout();
        initPopUpView();

        popBtn = new Button();
        popBtn.setCaption(TA.getCurrent().getParams().getLabel("default.filter"));
        popBtn.addClickListener(this);

        clearBtn = new Button();
        clearBtn.setIcon(new ThemeResource("layouts/images/16/101.png"));
        clearBtn.setPrimaryStyleName("godetail");
        clearBtn.addClickListener(this);
        clearBtn.setVisible(false);

        mainLay = new HorizontalLayout();
        mainLay.addComponent(popBtn);
        mainLay.addComponent(clearBtn);
        mainLay.addComponent(popupView);
        mainLay.setSpacing(true);
        mainLay.setComponentAlignment(clearBtn, Alignment.MIDDLE_CENTER);
        popupView.setHideOnMouseOut(false);
    }

    void initPopUpLayout() {
        addNewLineBtn = new Button();
        addNewLineBtn.setCaption(TA.getCurrent().getParams().getLabel("add.filter"));
        addNewLineBtn.addClickListener(this);
        addNewLineBtn.setPrimaryStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        okBtn = new Button(TA.getCurrent().getParams().getLabel("default.apply"), this);

        vComponent = new VerticalLayout();
        vComponent.setSpacing(true);
        popUpLayout = new Panel();
        popUpLayout.setPrimaryStyleName("popupLayout");
        popUpLayout.setWidth("300px");
        VerticalLayout v = new VerticalLayout();
        v.addComponent(vComponent);
        v.addComponent(addNewLineBtn);
        v.addComponent(okBtn);
        v.setComponentAlignment(vComponent, Alignment.TOP_LEFT);
        v.setComponentAlignment(addNewLineBtn, Alignment.TOP_LEFT);
        v.setComponentAlignment(okBtn, Alignment.BOTTOM_RIGHT);
        v.setMargin(true);
        v.setSpacing(true);
        popUpLayout.setContent(v);
    }

    void initPopUpView() {
        popupView = new PopupView(null, popUpLayout);
        popupView.addPopupVisibilityListener(this);
        popupView.setHideOnMouseOut(false);
    }

    private void initFilterSelection() {
        nSelection = new NativeSelect();
        nSelection.addValueChangeListener(this);
    }

    @Override
    protected Component initContent() {
        return mainLay;
    }

    @Override
    public Class getType() {
        return null;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(popBtn)) {
            popupView.setPopupVisible(true);
        } else if (event.getButton().equals(addNewLineBtn)) {
            doAddNewLine();
        } else if (event.getButton().equals(okBtn)) {
            populateData();
            parent.doRefresh();
            popBtn.setCaption(count + " filter");
            popupView.setPopupVisible(false);
            clearBtn.setVisible(true);
        } else if (event.getButton().equals(clearBtn)) {
            vComponent.removeAllComponents();
            count = 0;
            populateData();
            parent.doRefresh();
            popBtn.setCaption(TA.getCurrent().getParams().getLabel("default.filter"));
            clearBtn.setVisible(false);
        } else if (event.getButton().getId().equals("del")) {
            Integer index = (Integer) event.getButton().getData();
            vComponent.removeComponent(vComponent.getComponent(index));
            count = count - 1;
        }
    }

    private void populateData() {
        map = new HashMap();
        int count = vComponent.getComponentCount();
        for (int c = 0; c < count; c++) {
            HorizontalLayout ly = (HorizontalLayout) vComponent.getComponent(c);
//            AutoComplete auto = (AutoComplete) ly.getComponent(0);
            NativeSelect selection = (NativeSelect) ly.getComponent(0);
            HorizontalLayout filt = (HorizontalLayout) ly.getComponent(1);
            System.out.println("Auto Value == " + selection.getValue());
            System.out.println("text.getData() = " + filt.getComponent(0));
//            if (nameFilter != null) {
//                if (selection.getValue().equals(nameFilter.getId())) {
//                    nameFilter = (NameFilter) filt.getComponent(0);
//                    map.put(selection.getValue(), ("%"+nameFilter.getValue()+"%"));
//                }
//            }
//            if (genderFilter != null) {
//                if (selection.getValue().equals(genderFilter.getId())){
//                    genderFilter = (GenderFilter) filt.getComponent(0);
//                    map.put(selection.getValue(), genderFilter.getValue());
//                }
//            }
//            if (employmentTypeFilter != null) {
//                if (selection.getValue().equals(employmentTypeFilter.getId())) {
//                    employmentTypeFilter = (EmploymentType) filt.getComponent(0);
//                    map.put(selection.getValue(), employmentTypeFilter.getValue());
//                }
//            }
//            if (addressFilter != null) {
//                if (selection.getValue().equals(addressFilter.getId())) {
//                    addressFilter = (AddressFilter) filt.getComponent(0);
//                    map.put(selection.getValue(), ("%"+addressFilter.getValue()+"%"));
//                }
//            }
//            if (schoolFilter != null) {
//                if (selection.getValue().equals(schoolFilter.getId())) {
//                    schoolFilter = (SchoolFilter) filt.getComponent(0);
//                    map.put(selection.getValue(), ("%"+schoolFilter.getValue()+"%"));
//                }
//            }
            System.out.println("map = " + map);
        }
    }

    void doAddNewLine() {
        count = count + 1;
        int index = count - 1;

        Button deleteButton = new Button();
        deleteButton.setIcon(new ThemeResource("layouts/images/16/101.png"));
        deleteButton.setPrimaryStyleName("godetail");
        deleteButton.addClickListener(this);
        deleteButton.setData(index);
        deleteButton.setId("del");

        filterComp = new HorizontalLayout();
//        filterBy = new HorizontalLayout();
//        filterComp.addComponent(btn);

        HorizontalLayout hLay = generateFilter();
        initFilterSelection();

/*
        if (nameFilter != null) {
            nameFilter = new NameFilter(tenmaApplication);
            nSelection.addItem(nameFilter.getId());
            nSelection.setItemCaption(nameFilter.getId(), tenmaApplication.getParams().getLabel("default.name"));
        }
        if (genderFilter != null) {
//            hLay.addComponent(nSelection);
            genderFilter = new GenderFilter(tenmaApplication);
            nSelection.addItem(genderFilter.getId());
            nSelection.setItemCaption(genderFilter.getId(), tenmaApplication.getParams().getLabel("default.sex"));
        }
        if (employmentTypeFilter != null) {
//            hLay.addComponent(nSelection);
            employmentTypeFilter = new EmploymentType(tenmaApplication);
            nSelection.addItem(employmentTypeFilter.getId());
            nSelection.setItemCaption(employmentTypeFilter.getId(), tenmaApplication.getParams().getLabel("employment.type"));
        }
        if (addressFilter != null) {
//            hLay.addComponent(nSelection);
            addressFilter = new AddressFilter(tenmaApplication);
            nSelection.addItem(addressFilter.getId());
            nSelection.setItemCaption(addressFilter.getId(), tenmaApplication.getParams().getLabel("default.address"));
        }
        if (schoolFilter != null) {
//            hLay.addComponent(nSelection);
            schoolFilter = new SchoolFilter(tenmaApplication);
            nSelection.addItem(schoolFilter.getId());
            nSelection.setItemCaption(schoolFilter.getId(), tenmaApplication.getParams().getLabel("default.school"));
        }
*/

//        HorizontalLayout hLay = new HorizontalLayout();
//        hLay.addComponent(autoComplete);
        hLay.addComponent(filterComp);
        hLay.addComponent(deleteButton);
        hLay.setSpacing(true);
        vComponent.addComponent(hLay);
    }

    @Override
    public void popupVisibilityChange(PopupView.PopupVisibilityEvent event) {

    }

    public void doRefresh() {
    }


    public HorizontalLayout generateFilter() {
        HorizontalLayout filtComp = new HorizontalLayout();
        filtComp.addComponent(nSelection);

        return filtComp;
    }

    public void setFilter(Component component) {
        filterBy = new HorizontalLayout();
        filterBy.removeAllComponents();
        filterBy.addComponent(component);
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        System.out.println("event.getProperty().getValue() = " + event.getProperty().getValue());
/*        if (nameFilter != null) {
            if (event.getProperty().getValue().equals(nameFilter.getId())) {
                filterComp.removeAllComponents();
                nameFilter = new NameFilter(tenmaApplication);
                filterComp.addComponent(nameFilter);
            }
        }
        if (genderFilter != null) {
            if (event.getProperty().getValue().equals(genderFilter.getId())) {
                filterComp.removeAllComponents();
                genderFilter = new GenderFilter(tenmaApplication);
                filterComp.addComponent(genderFilter);
            }
        }
        if (employmentTypeFilter != null) {
            if (event.getProperty().getValue().equals(employmentTypeFilter.getId())) {
                filterComp.removeAllComponents();
                employmentTypeFilter = new EmploymentType(tenmaApplication);
                filterComp.addComponent(employmentTypeFilter);
            }
        }
        if (addressFilter != null) {
            if (event.getProperty().getValue().equals(addressFilter.getId())) {
                filterComp.removeAllComponents();
                addressFilter = new AddressFilter(tenmaApplication);
                filterComp.addComponent(addressFilter);
            }
        }
        if (schoolFilter != null) {
            if (event.getProperty().getValue().equals(schoolFilter.getId())) {
                filterComp.removeAllComponents();
                schoolFilter = new SchoolFilter(tenmaApplication);
                filterComp.addComponent(schoolFilter);
            }
        }*/
    }

    /*   //Add Filter
       public void addFilterGender() {
           genderFilter = new GenderFilter(tenmaApplication);
           nSelection.addItem(genderFilter.getId());
           nSelection.setItemCaption(genderFilter.getId(), tenmaApplication.getParams().getLabel("default.gender"));
       }

       public void addFilterName() {
           nameFilter = new NameFilter(tenmaApplication);
           nSelection.addItem(nameFilter.getId());
           nSelection.setItemCaption(nameFilter.getId(), tenmaApplication.getParams().getLabel("default.name"));
       }

       public void addFilterEmploymentType() {
           employmentTypeFilter = new EmploymentType(tenmaApplication);
           nSelection.addItem(employmentTypeFilter.getId());
           nSelection.setItemCaption(employmentTypeFilter.getId(), tenmaApplication.getParams().getLabel("employment.type"));
       }

       public void addFilterAddress() {
           addressFilter = new AddressFilter(tenmaApplication);
           nSelection.addItem(addressFilter.getId());
           nSelection.setItemCaption(addressFilter.getId(), tenmaApplication.getParams().getLabel("default.address"));
       }

       public void addFilterSchool() {
           schoolFilter = new SchoolFilter(tenmaApplication);
           nSelection.addItem(schoolFilter.getId());
           nSelection.setItemCaption(schoolFilter.getId(), tenmaApplication.getParams().getLabel("default.school"));
       }
   */
    public HashMap getMap() {
        return map;
    }

}
