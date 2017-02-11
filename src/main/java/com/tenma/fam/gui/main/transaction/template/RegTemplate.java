package com.tenma.fam.gui.main.transaction.template;

import com.tenma.common.TA;

import com.tenma.common.util.Constants;
import com.tenma.fam.bean.template.TransactionTemplateHelper;
import com.tenma.model.fam.TransactionTemplateModel;
import com.vaadin.event.MouseEvents;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 29/10/15.
 */
public class RegTemplate extends VerticalLayout {
    private VerticalLayout vm = new VerticalLayout();
    private List<TransactionTemplateModel> listTemplate;
    private int col = 2;
    private TemplateClickListener listener;

    public RegTemplate() {
        listTemplate = loadingTemplate();
        System.out.println("listTemplate.size() = " + listTemplate.size());
        generateTemplateUI();
    }

    private List<TransactionTemplateModel> loadingTemplate() {
        TransactionTemplateHelper helper = new TransactionTemplateHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put("autoOnly", Constants.TEMPLATE_TRANSACTION_TYPE.MANUAL.getValue());
        map.put("type", Constants.TEMPLATE_TYPE.REG.getValue());
        List list = helper.getListRenderer(map, false);
        return list;
    }

    private void generateTemplateUI() {
        System.out.println("GnaTemplate.generateTemplateUI");
        this.removeAllComponents();
        if (listTemplate.size() > 0) {

            int counter = 0;
            HorizontalLayout hz = new HorizontalLayout();
            this.addComponent(hz);
            hz.setSpacing(true);
            hz.setMargin(new MarginInfo(true, true, false, true));

            for (int i = 0; i < listTemplate.size(); i++) {
                TransactionTemplateModel model = listTemplate.get(i);
                if (counter > col) {
                    counter = 0;
                    hz = new HorizontalLayout();
                    hz.setSpacing(true);
                    hz.setMargin(new MarginInfo(true, true, false, true));
                    this.addComponent(hz);
                }
                TemplateItem item = new TemplateItem(model);
                item.addClickListener(createListener());
                hz.addComponent(item);
                counter++;
            }

            if (counter > col) {
                hz = new HorizontalLayout();
                hz.setSpacing(true);
                hz.setMargin(new MarginInfo(true, true, false, true));
                this.addComponent(hz);
            }

        }
    }

    private MouseEvents.ClickListener createListener() {
        MouseEvents.ClickListener lst = new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                TemplateItem item = (TemplateItem) event.getSource();
                System.out.println("Reg --> item.getModel().getTemplateName() = " + item.getModel().getTemplateName());
                listener.TemplateClickEvent(item.getModel());
            }
        };
        return lst;
    }

    public void addClickListener(TemplateClickListener listener) {
        this.listener = listener;
    }
}
