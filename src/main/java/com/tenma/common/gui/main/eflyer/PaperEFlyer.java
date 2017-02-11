package com.tenma.common.gui.main.eflyer;

import com.google.gson.Gson;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.model.common.EflyerModel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/16/2015
 * Time    : 1:10 PM
 * Project : udw
 * Package : share.tenma.common.gui.main.announcement
 */
public class PaperEFlyer extends TenmaPanel implements IFlyer {
    private ImageFlyer imageFlyer1;
    private ImageFlyer imageFlyer2;
    private TextFlyer tf1;
    private TextFlyer tf2;
    private EflyerModel model;
    private Panel panel;
    private VerticalLayout vlPanel;


    public PaperEFlyer(EflyerModel model) {
        this.model = model;
        createUI();
    }

    private void createUI() {
        VerticalLayout vlMain = new VerticalLayout();
        vlPanel = new VerticalLayout();
        panel = new Panel();
        imageFlyer1 = new ImageFlyer();
        imageFlyer2 = new ImageFlyer();
        tf1 = new TextFlyer(this);
        tf2 = new TextFlyer(this);
        updateEflyer(model.getPaperSize(), model.getOrientation(), model.getTypeTemplate());

        panel.setContent(vlPanel);
        vlPanel.setSpacing(true);
        vlPanel.setMargin(true);
        vlPanel.setWidth("100%");
        vlMain.addComponent(panel);
        vlMain.setComponentAlignment(panel, Alignment.TOP_CENTER);
        this.setContent(vlMain);
    }

    public void updateEflyer(Integer papersize, Integer orientation, Integer template) {
        PaperProperties p = new PaperProperties();
        ArrayList<Float> list = p.getPaperProperties(papersize, orientation, template);
        panel.setWidth(list.get(p.PANELWIDTH), Unit.MM);
        panel.setHeight(list.get(p.PANELHEIGHT), Unit.MM);
        tf1.setWidth(list.get(p.TEXT1WIDTH), Unit.MM);
        tf1.setHeight(list.get(p.TEXT1HEIGHT), Unit.MM);
        tf2.setWidth(list.get(p.TEXT2WIDTH), Unit.MM);
        tf2.setHeight(list.get(p.TEXT2HEIGHT), Unit.MM);
        imageFlyer1.updateSize(list.get(p.IMG1WIDTH), list.get(p.IMG1HEIGHT));
        imageFlyer2.updateSize(list.get(p.IMG2WIDTH), list.get(p.IMG2HEIGHT));
        model.setPaperSize(papersize);
        model.setOrientation(orientation);
        model.setTypeTemplate(template);

        System.out.println("EFlyer.updateEflyer");
        System.out.println("tf1 " + tf1.getWidth());
        System.out.println("tf1 " + tf1.getHeight());
        System.out.println("image1 " + imageFlyer1.getHeight());
        System.out.println("image2 " + imageFlyer2.getHeight());
        vlPanel.removeAllComponents();
        switch (template) {
            case PaperProperties.MODEL1:
                HorizontalLayout hl = new HorizontalLayout(imageFlyer1, new VerticalLayout(tf1));
                hl.setSpacing(true);
                vlPanel.addComponent(hl);
                vlPanel.addComponent(tf2);
                break;
            case PaperProperties.MODEL2:
                vlPanel.addComponent(imageFlyer1);
                vlPanel.addComponent(tf1);
                break;
            case PaperProperties.MODEL3:
                vlPanel.addComponent(tf1);
                vlPanel.addComponent(imageFlyer1);
                break;
            case PaperProperties.MODEL4:
                HorizontalLayout hl1 = new HorizontalLayout(new VerticalLayout(tf1), imageFlyer1);
                hl1.setSpacing(true);
                HorizontalLayout hl2 = new HorizontalLayout(imageFlyer2, new VerticalLayout(tf2));
                hl2.setSpacing(true);
                vlPanel.addComponent(hl1);
                vlPanel.addComponent(hl2);
                break;
            case PaperProperties.MODEL5:
                vlPanel.addComponent(imageFlyer1);
                break;
            default:
                break;
        }
    }

    @Override
    public String getDataContent() {
        return null;
    }

    @Override
    public void setDataContent(String value) {

    }

    @Override
    public EflyerModel getModel() {
        return model;
    }

    public String getJsonData() {
        HashMap map = new HashMap();
        String krt;
        krt = Base64.encodeBase64String(imageFlyer1.getObj());
        map.put("IMAGE1", krt);
        if (model.getTypeTemplate() != PaperProperties.MODEL5) {
            map.put("TEXT1", tf1.getDataContent());
            if (model.getTypeTemplate() == PaperProperties.MODEL4) {
                krt = Base64.encodeBase64String(imageFlyer2.getObj());
                map.put("IMAGE2", krt);
            }
            if (model.getTypeTemplate() == PaperProperties.MODEL1 || model.getTypeTemplate() == PaperProperties.MODEL4)
                map.put("TEXT2", tf2.getDataContent());
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public void setUpdateData() {
        Gson gson = new Gson();
        System.out.println("EFlyer.setUpdateData");
        HashMap map = gson.fromJson(model.getEflyerData(), HashMap.class);
        imageFlyer1.setObj((String) map.get("IMAGE1"));
        if (model.getTypeTemplate() != PaperProperties.MODEL5) {
            tf1.setDataContent((String) map.get("TEXT1"));
            if (model.getTypeTemplate() == PaperProperties.MODEL4) {
                imageFlyer2.setObj((String) map.get("IMAGE2"));
            }
            if (model.getTypeTemplate() == PaperProperties.MODEL1 || model.getTypeTemplate() == PaperProperties.MODEL4)
                tf2.setDataContent((String) map.get("TEXT2"));
        }
    }
}