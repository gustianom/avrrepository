package com.tenma.share.gui.display.component;


import com.tenma.common.bean.country.CountryHelper;
import com.tenma.common.util.Constants;
import com.tenma.model.common.CountryModel;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma05 on 27/07/15.
 */
public class CountryField extends AutoComplete {
    public static final String ID = "ID";
    public static final String NAME = "Name";

    private CountrySelection parent;


    private CountryModel selectedModel;

    public CountryField(CountrySelection aparent) {

        this.parent = aparent;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(btn)) {
            resultset = getList(filterFld.getValue());
            setResultsTableValues();
            popupView.setPopupVisible(true);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        lookupTextId.setEnabled(enabled);
        btn.setEnabled(enabled);
    }

    private void setAccount(CountryModel model) {
        selectedModel = model;
        lookupTextId.setValue(model.getCountryName());
        parent.setCountryModel(model);
    }

    public CountryModel getCountry() {
        return selectedModel;
    }

    public void setCountry(Integer countryId) {
        CountryHelper help = new CountryHelper();

        CountryModel p = new CountryModel();
        p.setCountryId(countryId);
        CountryModel m = help.getCountryDetail(p);
        selectedModel = m;
        this.lookupTextId.setValue(m.getCountryName());
    }

    public String getVehicleName() {
        if (selectedModel == null) {
            return "";
        } else if (selectedModel.getCountryName() != null) {
            return selectedModel.getCountryName();
        } else {
            return "";
        }
    }

    public void setSelectedModel(CountryModel model) {
        selectedModel = model;
        parent.setCountryModel(selectedModel);
    }


    @Override
    protected void initTable() {
        resultsTable = new Table();
        resultsTable.setSelectable(true);
        container = new TenmaTableContainer();

        container.addContainerProperty(ID, Integer.class, 0);
        container.addContainerProperty(NAME, String.class, "");

        resultsTable.setContainerDataSource(container);
        resultsTable.commit();

        resultsTable.setWidth("300px");
        resultsTable.setImmediate(true);
        resultsTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        resultsTable.setVisibleColumns(new Object[]{NAME});
        resultsTable.addItemClickListener(
                new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent event) {
                        Item item = event.getItem();
                        tableRowSelected = true;
                        popupView.setPopupVisible(false);
                        filterFld.setValue("");
                        if (item != null) {
                            CountryModel pcm = new CountryModel();
                            pcm.setCountryId((Integer) item.getItemProperty(ID).getValue());
                            pcm.setCountryName(item.getItemProperty(NAME).getValue().toString());
                            setAccount(pcm);
                            setSelectedObject(pcm);
                        }
//
                    }
                }
        );

    }

    public void setSelectedObject(Object obj) {
        if (obj != null) {
            selectedModel = (CountryModel) obj;
        }
    }

    @Override
    public void setResultsTableValues() {
        container.removeAllItems();

        for (int i = 0; i < resultset.size(); i++) {
            CountryModel mdl = (CountryModel) resultset.get(i);
            Item id = container.addItem(i);
            id.getItemProperty(ID).setValue(mdl.getCountryId());
            id.getItemProperty(NAME).setValue(mdl.getCountryName());
        }
        if (resultset.size() > 10)
            resultsTable.setPageLength(10);
        else
            resultsTable.setPageLength(resultset.size());
        resultsTable.setSizeFull();
        setSizeUndefined();
    }

    @Override
    public Object getValue() {
        return selectedModel;
    }

    @Override
    public boolean isValid() {
        boolean rs = false;
        if (selectedModel != null) rs = true;
        return rs;
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        super.validate();
        if (selectedModel != null) throw new Validator.InvalidValueException("Bank is not selected");
    }

    @Override
    protected List<Object> getList(String inputKey) {
        CountryHelper phelper = new CountryHelper();
        HashMap map = new HashMap();
        Integer skip = new Integer(0);
        int pageSize = 20;
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "countryName");
        map.put(Constants.RECORDSELECT_SORTORDER, "ASCENDING");
        map.put("searchValue", "%" + inputKey.trim() + "%");
        map.put(Constants.RECORDSELECT_SKIP, skip);
        map.put(Constants.RECORDSELECT_MAX, pageSize);
        List ls = phelper.getListRenderer(map, true);
        System.out.println("Size = " + ls.size() + " : " + inputKey);
        return ls;
    }
}
