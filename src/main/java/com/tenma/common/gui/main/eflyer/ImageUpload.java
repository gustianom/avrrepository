package com.tenma.common.gui.main.eflyer;

import com.vaadin.ui.Upload;
import org.vaadin.easyuploads.UploadField;

import java.io.File;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/16/2015
 * Time    : 5:33 PM
 * Project : udw
 * Package : share.tenma.common.gui.main.announcement
 */
public class ImageUpload extends UploadField {
    private IImgUpload master;

    public ImageUpload(IImgUpload master) {
        this.master = master;
        this.setFileDeletesAllowed(false);
        this.setFieldType(FieldType.FILE);
        this.setButtonCaption("EDIT IMAGE");
    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {
        super.uploadStarted(event);
        System.out.println("ImageUpload.uploadStarted");
        System.out.println("event name" + event.getFilename());
        System.out.println("event content" + event.getContentLength());
        System.out.println("event source" + event.getSource());
        System.out.println("event --- " + event.getMIMEType());
    }

    @Override
    public void uploadFinished(Upload.FinishedEvent event) {
        super.uploadFinished(event);
    }

    @Override
    protected void updateDisplay() {
//        super.updateDisplay();
        System.out.println("ImgUpload.updateDisplay");
        master.finishUpload((File) this.getValue());

    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void clear() {

    }
}
