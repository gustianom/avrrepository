package com.tenma.common.gui.display.column;


import com.tenma.common.gui.display.TenmaTableRowView;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/15/13
 * Time: 12:23 AM
 */
public class TenmaMessageDetailColumn implements Table.ColumnGenerator {


    public TenmaMessageDetailColumn() {

    }

    @Override
    public Component generateCell(Table components, Object itemId, Object columnId) {
        Property prop = components.getItem(itemId).getItemProperty(columnId);
        return new TenmaTableRowView(prop);
    }
}
