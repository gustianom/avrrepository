package com.tenma.common.gui.display.column;

import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 10/19/12
 * Time: 4:46 AM
 */
public class TenmaStringColumn implements Table.ColumnGenerator {
    public Component generateCell(Table source, Object itemId, Object columnId) {
        Property prop = source.getItem(itemId).getItemProperty(columnId);
        return new Label(prop);
    }
}