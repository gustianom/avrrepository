package com.tenma.common.gui.display.column;

import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 10/19/12
 * Time: 4:46 AM
 */
public class TenmaBooleanColumn implements Table.ColumnGenerator {
    /**
     * Generates the cell containing an open image when boolean is true
     */
    public Component generateCell(Table source, Object itemId, Object columnId) {
        Property prop = source.getItem(itemId).getItemProperty(columnId);
//        if (source.isEditable()) {
        return new CheckBox(null, prop);
//        } else {
//            if (prop.getValue()!=null && (Boolean) prop.getValue()) {
//                return new Embedded("", new ThemeResource("layouts/images/16/121.png"));
//            }
//            return null;
//        }
    }
}