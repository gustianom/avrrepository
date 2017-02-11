package com.tenma.common.gui.display.component;

import com.tenma.common.TA;
import com.tenma.common.util.Constants;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Yos Prabowo
 * Date: 10/16/15
 * Time: 15:58
 */
public class TenmaFileUploadMemory extends CustomComponent implements UploadFinishedHandler, Button.ClickListener {
    public static final int DEFAULT_MAX_FILE = 5;
    private MultiFileUpload upload;
    private VerticalLayout uploadedLayout = new VerticalLayout();
    private int maxFile;
    private String path;
    private int modifyMode = Constants.TENMA_TRANSFER_MODE.UPLOAD_DOWNLOAD.getValue();

    private List<String> filenameList = new ArrayList<String>();
    private List<String> sourceFileNameList = new ArrayList<String>();
    private TenmaFileUploadMemoryEvent uploadEvent;
    private boolean showfilename = true;

    public TenmaFileUploadMemory(TenmaFileUploadMemoryEvent event, String label) {
        uploadEvent = event;

        maxFile = DEFAULT_MAX_FILE;
        this.path = TA.getCurrent().getParams().getProperty(Constants.UPLOAD_DIR_FILE);
        String OS = System.getProperty("os.name");
//        System.out.println("OS = " + OS);
        if (OS.toUpperCase().startsWith("windows".toUpperCase())) {
            path = System.getProperty("java.io.tmpdir"); // temporary for windows only
        }
        System.out.println(OS + " - upload DIR path = " + path);
        showfilename = false;
        //generate UI
        generateUI(label);

    }

    public TenmaFileUploadMemory() {

//        param = TA.getCurrent().getParams();
        maxFile = DEFAULT_MAX_FILE;
        this.path = TA.getCurrent().getParams().getProperty(Constants.UPLOAD_DIR_FILE);
        //generate UI
        generateUI(null);
    }

    public TenmaFileUploadMemory(int maxFile, String path) {

//        param = TA.getCurrent().getParams();
        this.maxFile = maxFile;
        this.path = path;
        generateUI(null);
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
        VerticalLayout mainLay = new VerticalLayout();
        mainLay.addComponent(hz);
        if (showfilename) {
            mainLay.addComponent(uploadedLayout);
        }
        mainLay.setSizeFull();
        mainLay.setSizeUndefined();
        setCompositionRoot(mainLay);
    }

    public final void setMimeType(List<String> mimeTypes) {
        upload.setAcceptedMimeTypes(mimeTypes);
    }

    public void setMaxSizeFile(int sizeFile) {
        upload.setMaxFileSize(sizeFile);
    }

    public final void setFileList(List<String> filenameList) {
        this.filenameList = filenameList;
        // if its update it become source file name ;
        this.sourceFileNameList = filenameList;
        refreshVerticalLayout(showfilename);
    }

    public final List<String> getFilenameList() {
        return filenameList;
    }

    public void setMaxFile(int maxFile) {
        this.maxFile = maxFile;
    }

    public void setModifyMode(Constants.TENMA_TRANSFER_MODE mode) {
        this.modifyMode = mode.getValue();
        if (mode != Constants.TENMA_TRANSFER_MODE.DOWNLOAD_ONLY)
            upload.setVisible(true);
        else upload.setVisible(false);
    }

    @Override
    public final void handleFile(InputStream inputStream, String fileName, String mimeType, long size) {
        FileOutputStream fos = null;
        System.out.println("\n\n\n\n INPUT STREAM ==> " + inputStream);
        System.out.println("\n\n\n\n fileName ==> " + fileName);
        TenmaImageHost imageHost = TenmaImageHost.getInstance();
        String revNumber = imageHost.putData(inputStream);

        uploadEvent.afterUploadFinish(revNumber, fileName);


    }

    private void refreshVerticalLayout(boolean showfilename) {
        uploadedLayout.removeAllComponents();
        if (showfilename) {
            if (filenameList != null) {
                int count = filenameList.size();
                int sourceCount = sourceFileNameList.size();
                if (count != 0) {
                    if (count == maxFile)
                        upload.setVisible(false);
                    if (count == sourceCount)
                        for (int a = 0; a < count; a++) {
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            System.out.println("TenmaFileUpload.refreshVerticalLayout - " + filenameList.get(a));
                            HorizontalLayout lay = new HorizontalLayout();
                            if (modifyMode != Constants.TENMA_TRANSFER_MODE.UPLOAD_ONLY.getValue()) {
                                File file = new File(path, filenameList.get(a));
                                if (file.canRead()) {
                                    lay.addComponent(new Label(sourceFileNameList.get(a)));
                                    Resource res = new FileResource(file);
                                    FileDownloader fd = new FileDownloader(res);
                                    Button attachment = new Button();
                                    attachment.setStyleName("circle");
                                    attachment.setIcon(new ThemeResource("layouts/images/16/paperclip.jpg"));
                                    fd.extend(attachment);
                                    lay.addComponent(attachment);
                                }
                            }
                            Button deleteFile = new Button();
                            deleteFile.setStyleName("circle");
                            deleteFile.setIcon(new ThemeResource(Constants.DELETE_ICON));
                            deleteFile.setData(a);
                            deleteFile.addClickListener(this);
                            lay.addComponent(deleteFile);
                            uploadedLayout.addComponent(lay);
                        }
                } else uploadedLayout.addComponent(new Label(TA.getCurrent().getParams().getLabel("default.noFile")));
            } else uploadedLayout.addComponent(new Label(TA.getCurrent().getParams().getLabel("default.noFile")));
        }
    }


    @Override
    public void buttonClick(Button.ClickEvent event) {
        Button but = event.getButton();
        int index = (Integer) but.getData();
        uploadedLayout.removeComponent(uploadedLayout.getComponent(index));
        filenameList.remove(index);
        if (filenameList.size() < maxFile)
            upload.setVisible(true);

    }
}
