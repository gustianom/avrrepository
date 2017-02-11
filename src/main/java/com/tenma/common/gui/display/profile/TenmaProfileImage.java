package com.tenma.common.gui.display.profile;

import com.vaadin.event.LayoutEvents;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import net.coobird.thumbnailator.Thumbnails;


/**
 * Created by ndwijaya on 9/30/15.
 */
public class TenmaProfileImage extends VerticalLayout implements LayoutEvents.LayoutClickListener, TenmaProfileImageListener {
    private Resource resourceImage;
    private HorizontalLayout hImageLayout = new HorizontalLayout();

    private String jsonImage;
    private boolean isCroped = false;

    public TenmaProfileImage() {
        setWidth("140px");
        setHeight("120px");
        setStyleName("profileBox");
        BoxOver box = new BoxOver();
        addComponent(hImageLayout);
        addComponent(box);
        this.addLayoutClickListener(this);
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getCropStyle() {
        return "boxtextprofile";
    }

    @Override
    public void layoutClick(LayoutEvents.LayoutClickEvent event) {
        ProfileCropDialog profileDialog = new ProfileCropDialog(this, 140, 120);
        profileDialog.center();
        profileDialog.setClosable(true);
        profileDialog.setResizable(false);
        UI.getCurrent().addWindow(profileDialog);
    }

    @Override
    public void CropNotReady() {
        isCroped = false;
    }

    public boolean isImageReady() {
        return isCroped;
    }

    @Override
    public void ApplySelectedImageProfile(Image profile, String json) {
        isCroped = true;
        hImageLayout.removeAllComponents();
        ProfileImageTools imageTools = new ProfileImageTools();
        profile = imageTools.generateImage(json);
        hImageLayout.addComponent(profile);
        jsonImage = json;
        System.out.println("JSON length = " + json.length());
        String s = Thumbnails.of(imageTools.getSteam(json))
                .size(100, 100)
                .outputFormat("jpg").toString();
        System.out.println("s length = " + s.length());
    }

    public String getJSONBImage() {
        return jsonImage;
    }

    public void setPicture(Image img) {
        hImageLayout.removeAllComponents();
        hImageLayout.addComponent(img);
    }

    class BoxOver extends HorizontalLayout {
        private Label text = new Label();

        BoxOver() {
            Resource classImg = new ThemeResource("layouts/images/16/fotoicon.gif");
            Image image = new Image(null, classImg);
            image.setWidth("20px");
            VerticalLayout vl = new VerticalLayout();
            vl.addComponent(image);
            setWidth("140px");
            setHeight("40px");
            setStyleName("boxover");
            text.setWidth("100px");
            text.setContentMode(ContentMode.HTML);
            text.setPrimaryStyleName("boxtextprofile");
            text.setValue("Update profile picture");
            vl.setWidth("20px");
            addComponent(vl);
            addComponent(text);
            setExpandRatio(vl, 0.2f);
            setExpandRatio(text, 0.8f);
        }


    }
}
