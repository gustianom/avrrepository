package com.tenma.common.gui.main.eflyer;

import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/16/2015
 * Time    : 3:53 PM
 * Project : udw
 * Package : share.tenma.common.gui.main.announcement
 */
public class ImageFlyer extends Panel implements IImgUpload {
    private ImageUpload up;
    private Image img;
    private AbsoluteLayout layout;
    private byte[] data;

    public ImageFlyer() {
        createUI();
    }

    private void createUI() {
        up = new ImageUpload(this);
        img = new Image();
        layout = new AbsoluteLayout();
        layout.addComponent(up, "" +
                "left: 0px; " +
                "top: 0px;" +
                " z-index: 100;");
        layout.addComponent(img, "" +
                "left: 0px; " +
                "top: 0px;" +
                " z-index: 99;");
        this.setStyleName("imageFlyer");
        this.setContent(layout);
    }

    @Override
    public void finishUpload(File file) {
        try {
            float img1Width = img.getWidth();
            float img1Height = img.getHeight();
            layout.removeComponent(img);
            FileResource fl = new FileResource(file);
            InputStream ios = new BufferedInputStream(new FileInputStream(file));
            data = IOUtils.toByteArray(ios);
            img = new Image(null, fl);
            updateSize(img1Width, img1Height);
            layout.addComponent(img, "" +
                    "left: 0px; " +
                    "top: 0px;" +
                    " z-index: 99;");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSize(float img1Width, float img1Height) {
        layout.setWidth(img1Width, Unit.MM);
        layout.setHeight(img1Height, Unit.MM);
        img.setWidth(img1Width, Unit.MM);
        img.setHeight(img1Height, Unit.MM);
    }

    public byte[] getObj() {
        return data;
    }

    public void setObj(String string) {
        if (string != null) {
            this.data = Base64.decodeBase64(string);
            StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                public InputStream getStream() {
                    return new ByteArrayInputStream(data);
                }
            };
            StreamResource sf = new StreamResource(streamSource, "");
            try {
                streamSource.getStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sf.setCacheTime(0);
            img.setSource(sf);
        }
    }
}