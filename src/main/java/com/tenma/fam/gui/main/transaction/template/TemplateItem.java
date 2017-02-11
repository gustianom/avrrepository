package com.tenma.fam.gui.main.transaction.template;

import com.google.gson.Gson;

import com.tenma.common.gui.display.profile.ProfileImageTools;
import com.tenma.fam.gui.main.transaction.ClassImageUtils;
import com.tenma.model.fam.TransactionTemplateModel;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.Random;

/**
 * Created on 29/10/15.
 */
public class TemplateItem extends Panel {
    private final TransactionTemplateModel model;
    private TemplateItemHeader header;
    private VerticalLayout vm = new VerticalLayout();


    public TemplateItem(TransactionTemplateModel model) {
        this.model = model;
        header = new TemplateItemHeader(model, this);
        generateUITemplate(model);
    }

    private void generateUITemplate(TransactionTemplateModel model) {
        Label lblClassName = new Label(this.model.getTemplateName());
        vm.setStyleName("classmasteritem");

        Image temp = null;
        Image image;

        if (model.getImage() != null) {
            Gson gson = new Gson();
            byte[] bytes = gson.fromJson(model.getImage(), byte[].class);
            if (bytes != null && bytes.length > 0) {
                ProfileImageTools tools = new ProfileImageTools();
                temp = tools.generateImage(bytes);
            }
            image = new Image(null, temp.getSource());
        } else {
            Random random = new Random();
            int clasrandomImage = random.nextInt(17);
            image = ClassImageUtils.getClassImage(clasrandomImage);
        }

        image.setHeight("100px");
        image.setStyleName("profileaccount");
        image.setWidth(40, Unit.PIXELS);
        image.setHeight(40, Unit.PIXELS);
        CssLayout himage = new CssLayout(image);
        himage.setPrimaryStyleName("classimage");

        HorizontalLayout hzi = new HorizontalLayout();
        hzi.setWidth("100%");
        hzi.addComponent(himage);
        hzi.setComponentAlignment(himage, Alignment.TOP_CENTER);
        hzi.setMargin(new MarginInfo(true, false, false, false));


        HorizontalLayout hzname = new HorizontalLayout();
        lblClassName.setWidth(null);
        lblClassName.setContentMode(ContentMode.HTML);
        lblClassName.setStyleName("classname");
        hzname.setWidth("100%");
        hzname.addComponent(lblClassName);
        hzname.setComponentAlignment(lblClassName, Alignment.BOTTOM_CENTER);


        VerticalLayout vm2 = new VerticalLayout(hzname);
        setWidth("80px");
        vm.addComponent(hzi);
        vm.addComponent(vm2);
        vm.setExpandRatio(hzi, 2.0f);
        vm.setExpandRatio(vm2, 1.0f);
        vm.setComponentAlignment(vm2, Alignment.BOTTOM_CENTER);
        vm.setHeight("80px");
        vm.setSpacing(true);
        setContent(vm);
    }

    public TransactionTemplateModel getModel() {
        return model;
    }
}
