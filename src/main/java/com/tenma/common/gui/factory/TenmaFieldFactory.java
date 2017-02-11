package com.tenma.common.gui.factory;

import com.tenma.common.TA;
import com.tenma.common.util.Params;
import com.vaadin.data.Item;
import com.vaadin.ui.*;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 10/27/12
 * Time: 4:08 PM
 */
public abstract class TenmaFieldFactory implements TenmaField {
    public Params params;

    public TenmaFieldFactory(Params params) {
        this.params = params;
    }

    public TenmaFieldFactory() {
        this.params = TA.getCurrent().getParams();
    }

    public abstract Field createField(Item item, Object propertyId);

    public Field createAreaField(Object propertyId) {
        TextArea area = new TextArea();
        area.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
        return area;
    }


    public Field createRichAreaField(Object propertyId) {
        RichTextArea area = new RichTextArea();
        area.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
        return area;
    }

    public Field createOptionsField(Object propertyId) {
        OptionGroup area = new OptionGroup();
        area.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
        return area;
    }

    public Field createPopupDateField(Object propertyId) {
        PopupDateField area = new PopupDateField();
        if (propertyId != null)
            area.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
        area.setDateFormat("dd-MMM-yyyy");
        area.setTextFieldEnabled(false);
        return area;
    }

    public Field createPopupDateField() {
        return createPopupDateField(null);
    }

    public Field createTextField(Item item, Object propertyId) {
        TextField field = new TextField();
        return field;
    }

    public Field createComboField(Object propertyId) {
        NativeSelect area = new NativeSelect();
        area.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
        return area;
    }

    public Field createPasswordField(Object propertyId) {
        PasswordField area = new PasswordField();
        area.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
        return area;
    }

    public Field createCheckField(Object propertyId) {
        CheckBox area = new CheckBox();
        area.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
        return area;
    }


}

