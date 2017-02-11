package com.tenma.common.gui.factory;

import com.vaadin.data.Item;
import com.vaadin.ui.Field;

import java.io.Serializable;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 6/20/13
 * Time: 3:21 AM
 */
public interface TenmaField extends Serializable {
    public Field createField(Item item, Object propertyId);
}
