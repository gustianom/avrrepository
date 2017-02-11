package com.tenma.common.gui.display;

import com.vaadin.ui.*;

/**
 * Created by ndwijaya on 1/7/2015.
 */
public class HorizontalLayoutField extends CustomField {
    HorizontalLayout hz = new HorizontalLayout();

    public void addComponent(Component c) {
        hz.addComponent(c);
    }

    public void setComponentAlignment(Component childComponent,
                                      Alignment alignment) {
        hz.setComponentAlignment(childComponent, alignment);
    }

    public void setExpandRatio(Component component, float ratio) {
        hz.setExpandRatio(component, ratio);
    }

    @Override
    protected Component initContent() {
        return hz;
    }

    @Override
    public Class getType() {
        return Field.class;
    }
}
