package com.tenma.common.gui.display.component;

import com.tenma.common.TA;
import com.tenma.common.util.Constants;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ndwijaya on 10/20/2015.
 * TenmaImageUpload extend Multiplefile upload and spesific will be return array of bytes of the images
 */

public class TenmaImageUpload extends CustomComponent implements UploadFinishedHandler {

    private MultiFileUpload upload;
    private String path;
    private TenmaImageUploadEvent uploadEvent;

    public TenmaImageUpload(TenmaImageUploadEvent event, String label) {
        uploadEvent = event;
        this.path = TA.getCurrent().getParams().getProperty(Constants.UPLOAD_DIR_FILE);
        String OS = System.getProperty("os.name");
        if (OS.toUpperCase().startsWith("windows".toUpperCase())) {
            path = System.getProperty("java.io.tmpdir"); // temporary for windows only
        }
        generateUI(label);
    }

    private void generateUI(String uploadLabel) {
        UploadStateWindow window = new UploadStateWindow();
        upload = new MultiFileUpload(this, window, false);
        HorizontalLayout hz = new HorizontalLayout();
        if (uploadLabel != null) {
            Label lbl = new Label(uploadLabel);
            hz.setSpacing(true);
            hz.addComponent(lbl);
            hz.setComponentAlignment(lbl, Alignment.MIDDLE_LEFT);
        }
        hz.addComponent(upload);
        hz.setComponentAlignment(upload, Alignment.MIDDLE_RIGHT);
        setCompositionRoot(hz);
    }

    public void setMaxFile(int max) {
        upload.setMaxFileSize(max);
    }

    public final void setMimeType(List<String> mimeTypes) {
        upload.setAcceptedMimeTypes(mimeTypes);
    }

    @Override
    public final void handleFile(InputStream inputStream, String fileName, String mimeType, long size) {
        try {
            System.out.println("TenmaImageUpload.handleFile");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            uploadEvent.imageUploadEvent(bytes, fileName, mimeType, size);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
