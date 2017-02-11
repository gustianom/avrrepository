package com.tenma.fam.gui.main.transaction.template;

import com.tenma.common.TA;
import com.tenma.model.fam.TransactionTemplateModel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created on 29/10/15.
 */
public class MenuTemplate extends VerticalLayout implements TabSheet.SelectedTabChangeListener, TemplateClickListener {
    private final TemplateClickListener master;
    private TabSheet tabsheet = new TabSheet();
    private GnaTemplate gnaTemplate;
    private RegTemplate regTemplate;
    private OtherTemplate otherTemplate;

    public MenuTemplate(TemplateClickListener master) {
        this.master = master;
        generateUI();
    }

    private void generateUI() {
        gnaTemplate = new GnaTemplate();
        regTemplate = new RegTemplate();
        otherTemplate = new OtherTemplate();

        gnaTemplate.addClickListener(this);
        regTemplate.addClickListener(this);
        otherTemplate.addClickListener(this);

        tabsheet.setStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        tabsheet.addStyleName(ValoTheme.TABSHEET_COMPACT_TABBAR);
        tabsheet.addStyleName("classdetail");
        tabsheet.setSelectedTab(0);
        tabsheet.addSelectedTabChangeListener(this);
        tabsheet.setWidth("100%");
        tabsheet.addTab(gnaTemplate, TA.getCurrent().getParams().getLabel("template.gna"), null);
        tabsheet.addTab(regTemplate, TA.getCurrent().getParams().getLabel("template.reg"), null);
        tabsheet.addTab(otherTemplate, TA.getCurrent().getParams().getLabel("template.other"), null);
        addComponent(tabsheet);
        setPrimaryStyleName("classroot");
    }

    @Override
    public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
        System.out.println("MenuTemplate.selectedTabChange event");
    }

    @Override
    public void TemplateClickEvent(TransactionTemplateModel model) {
        master.TemplateClickEvent(model);
    }
}
