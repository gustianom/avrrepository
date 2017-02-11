package com.tenma.fam.gui.main.transaction.template;


import com.tenma.model.fam.TransactionTemplateModel;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created on 30/10/15.
 */
public class TemplateItemHeader extends HorizontalLayout implements Button.ClickListener {

    private final TemplateItem parent;
    private TransactionTemplateModel model;
    private Label labelClassTitle = new Label();

    public TemplateItemHeader(TransactionTemplateModel model, TemplateItem parent) {
        this.model = model;
        this.parent = parent;
        generateUI(true);
    }


    private void generateUI(boolean title) {
//        btnCommand.setPrimaryStyleName("classsettigbutton");
        labelClassTitle.setValue(model.getTemplateName());
        labelClassTitle.setContentMode(ContentMode.HTML);
        labelClassTitle.setStyleName(ValoTheme.LABEL_TINY);
        labelClassTitle.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        labelClassTitle.setWidth(null);
        if (title) {
            addComponent(labelClassTitle);
            setComponentAlignment(labelClassTitle, Alignment.MIDDLE_LEFT);
        }
//        addComponent(btnCommand);
//        setComponentAlignment(btnCommand, Alignment.TOP_RIGHT);
        setWidth("100%");
        setHeight("10px");
        setMargin(new MarginInfo(true, true, false, true));
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

    }
}
