package com.tenma.common.gui.display.profile;

import com.google.gson.Gson;
import com.tenma.common.TA;
import com.tenma.common.gui.display.component.TenmaFileUpload;
import com.tenma.common.gui.display.component.TenmaFileUploadEvent;
import com.tenma.common.util.Constants;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.vaadin.jcrop.Jcrop;
import org.vaadin.jcrop.selection.JcropSelection;
import org.vaadin.jcrop.selection.JcropSelectionChanged;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ndwijaya on 10/1/15.
 */
public class ProfileCropDialog extends Window implements Button.ClickListener {
    private VerticalLayout root = new VerticalLayout();
    private Image profile;
    private ProfileCustom tab1;
    private Resource defProfile = new ThemeResource("layouts/images/16/cute0.png");

    private VerticalLayout vlimg = new VerticalLayout();
    private String todoDeleteFile;
    private TenmaProfileImageListener parent;
    private Button btnApply = new Button("Apply");
    private String jsonImage = "";

    private int imageWidth;
    private int imageHeight;
    private boolean isCropDone = false;

    public ProfileCropDialog(TenmaProfileImageListener parent, int width, int height) {

        this.parent = parent;
        this.imageWidth = width;
        this.imageHeight = height;

        btnApply.addClickListener(this);
        setWidth("700px");
        setHeight("600px");
        tab1 = new ProfileCustom();
        if (parent.getImage() == null) {
            profile = new Image(null, defProfile);
        } else {
            profile = new Image(null, parent.getImage().getSource());
        }

//        vlimg.setCaption("Profile");

        if (parent.getCropStyle() == null) {
            profile.setStyleName("profileBox");
        } else {
            profile.setStyleName(parent.getCropStyle());
        }

        profile.setWidth(imageWidth, Unit.PIXELS);
        profile.setHeight(imageHeight, Unit.PIXELS);
//        FormLayout form = new FormLayout();
        vlimg.addComponent(profile);
//        form.addComponent(vlimg);
        Label lblProfile = new Label("Profile");
        HorizontalLayout hz = new HorizontalLayout();
        hz.addComponent(lblProfile);
        hz.addComponent(vlimg);
        hz.addComponent(btnApply);
        hz.setComponentAlignment(lblProfile, Alignment.TOP_LEFT);
        hz.setComponentAlignment(lblProfile, Alignment.TOP_CENTER);
        hz.setComponentAlignment(lblProfile, Alignment.MIDDLE_RIGHT);
        hz.setSpacing(true);
        hz.setComponentAlignment(vlimg, Alignment.TOP_LEFT);
        hz.setComponentAlignment(btnApply, Alignment.TOP_RIGHT);
        root.addComponent(hz);
//        root.addComponent(form);
        root.addComponent(tab1);
        root.setSpacing(true);
        root.setMargin(true);
        setContent(root);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(btnApply)) {
            if (isCropDone) {
                parent.ApplySelectedImageProfile(profile, jsonImage);
                removeTempFile();
            }
            this.close();

        }
    }

    private void removeTempFile() {
        if (todoDeleteFile != null) {
            File remove = new File(todoDeleteFile);
            FileUtils.deleteQuietly(remove);
        }
    }

    public void updatedSelectedCropProfile() {
        Random random = new Random();
        StreamResource resource =
                new StreamResource(tab1, (random.nextInt(1000000) + 10) + ".png");
        Image image = new Image(null, resource);
        image.setWidth(imageWidth, Unit.PIXELS);
        image.setHeight(imageHeight, Unit.PIXELS);
        profile = image;
//        System.out.println("jsonImage = " + jsonImage.length());
        vlimg.removeAllComponents();
        vlimg.addComponent(image);
        if (parent.getCropStyle() != null) {
            image.setStyleName(parent.getCropStyle());
        }
        btnApply.setEnabled(true);
    }

    class ProfileCustom extends VerticalLayout implements TenmaFileUploadEvent, StreamResource.StreamSource, Button.ClickListener {
        //        private TenmaImageUpload upload;
        private TenmaFileUpload upload;
        private List<String> mimeType = new ArrayList<>();
        private VerticalLayout vlimage = new VerticalLayout();
        private Jcrop jcrop = new Jcrop();
        private Label lblSelection = new Label();
        private JcropSelection selectionLast;
        private String filenameSelected;
        private Button crop = new Button("Crop");
        private HorizontalLayout hz = new HorizontalLayout();

        ProfileCustom() {
            setMargin(new MarginInfo(false, true, true, true));
            setStyleName("imageBox");
            setSizeFull();

            crop.setEnabled(false);
//            upload = new TenmaImageUpload(tenmaApplication, this, "Upload your foto");
            upload = new TenmaFileUpload(this, "Upload your foto");
            mimeType.add(Constants.MIME_TYPES.JPG.getValue());
            mimeType.add(Constants.MIME_TYPES.PNG.getValue());
            mimeType.add(Constants.MIME_TYPES.JPEG.getValue());
            upload.setMimeType(mimeType);
            upload.setMaxFile(Constants.MAX_PROFILE_FILE_UPLOAD * 4);
//            upload.setCaption("Upload your foto");
            upload.setHeight("50px");
            FormLayout form = new FormLayout();
            form.addComponent(upload);


            hz.setWidth("100%");
            vlimage.setWidth("100%");
            this.setHeight("400px");
            hz.addComponent(lblSelection);
            hz.addComponent(crop);
            crop.addClickListener(this);

            this.addComponent(form);
            this.addComponent(vlimage);
            crop.setEnabled(false);
        }


//        @Override
//        public void imageUploadEvent(byte[] bytes, String fileName, String mimeType, long size) {
//            UI.getCurrent().getSession().setAttribute("imageupload", bytes);
//
//            String servletName = "upload.imgup";
//            String urlhost = TA.getCurrent().getServerFullHost();
//            String urlFileImage = "";
//            try {
//                URL url = new URL(urlhost + "/" + servletName + "?test=imageupload");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                System.out.println("conn.getResponseCode() = " + conn.getResponseCode());
//                if (conn.getResponseCode() == 200) {
//                    urlFileImage = new StringBuilder().append(urlhost).append("/").append("upload.imgup").toString();
//                    System.out.println("urlFileImage = " + urlFileImage);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            this.jcrop.setImageUrl(urlFileImage);
//            this.jcrop.setWidth(650, Unit.PIXELS);
//            this.jcrop.setHeight(400, Unit.PIXELS);
//            this.jcrop.setMaxCropSize(280, 240);
//            this.jcrop.clearSelection();
//            this.jcrop.setMinCropSize(100, 100);
//
//            vlimage.addComponent(jcrop);
//
//            this.jcrop.addListener(new JcropSelectionChanged() {
//                @Override
//                public void selectionChanged(JcropSelection selection) {
//                    lblSelection.setValue("x=" + selection.getX() + ",y=" + selection.getY() + ", width=" + selection.getWidth() + ", height=" + selection.getHeight());
//                    selectionLast = selection;
//                    crop.setEnabled(true);
//                }
//            });
//
//            crop.setEnabled(true);
//        }


        public void afterUploadFinish(String path, String filename) {
            Random random = new Random();
            int r = random.nextInt(10000000) + 1000;
            StringBuilder imageName = new StringBuilder().append(path).append(File.separator).append(filename);
            System.out.println("Upload finish file : " + imageName);
            File fsource = new File(imageName.toString());
            String deployImage = TA.getCurrent().getRealPath();

            StringBuilder imageNewName = new StringBuilder().append(deployImage)
                    .append("VAADIN").append(File.separator)
                    .append("themes").append(File.separator)
                    .append("tenmavalo").append(File.separator)
                    .append("layouts").append(File.separator)
                    .append("images").append(File.separator)
                    .append("tmp").append(File.separator)
                    .append(r).append(filename);
            System.out.println("File is copying To = " + imageNewName);

            File fdest = new File(imageNewName.toString());
            try {
                FileUtils.copyFile(fsource, fdest);
                StringBuilder imageFile = new StringBuilder().append("layouts")
                        .append(File.separator)
                        .append("images")
                        .append(File.separator)
                        .append("tmp")
                        .append(File.separator)
                        .append(r)
                        .append(filename);
                this.removeAllComponents();
                vlimage.removeAllComponents();
                vlimage.addComponent(hz);
                this.addComponent(vlimage);
                int index = TA.getCurrent().getServerFullHost().indexOf("/main/login");
                String temp = "";
                if (index != -1) {
                    temp = TA.getCurrent().getServerFullHost().substring(0, index);
                } else {
                    temp = TA.getCurrent().getServerFullHost();
                }
                StringBuilder urlFileImage = new StringBuilder().append(temp).append("/VAADIN/themes/tenmavalo/").append(imageFile.toString());
                System.out.println("Image ready to show at " + urlFileImage);


                this.jcrop.setImageUrl(urlFileImage.toString());
                this.jcrop.setWidth(650, Unit.PIXELS);
                this.jcrop.setHeight(400, Unit.PIXELS);
                this.jcrop.setMaxCropSize(280, 240);
                this.jcrop.clearSelection();
                this.jcrop.setMinCropSize(100, 100);

                vlimage.addComponent(jcrop);
                filenameSelected = imageNewName.toString();


                this.jcrop.addListener(new JcropSelectionChanged() {
                    @Override
                    public void selectionChanged(JcropSelection selection) {
                        lblSelection.setValue("x=" + selection.getX() + ",y=" + selection.getY() + ", width=" + selection.getWidth() + ", height=" + selection.getHeight());
                        selectionLast = selection;
                        crop.setEnabled(true);
                    }
                });

                FileUtils.deleteQuietly(fsource);
                crop.setEnabled(true);
                todoDeleteFile = imageNewName.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            isCropDone = false;
        }


        @Override
        public InputStream getStream() {
            InputStream inputStream = null;
            try {
//                System.out.println("filenameSelected = " + filenameSelected);
                BufferedImage img = ImageIO.read(new File(filenameSelected));
                BufferedImage imgRs = Scalr.crop(img, selectionLast.getX(), selectionLast.getY(), selectionLast.getWidth(), selectionLast.getHeight(),
                        Scalr.OP_ANTIALIAS);

                ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
                ImageIO.write(imgRs, "png", imagebuffer);

                inputStream = new ByteArrayInputStream(
                        imagebuffer.toByteArray());

            } catch (IOException e) {
                e.printStackTrace();
            }


            return inputStream;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            if (event.getButton().equals(crop)) {
                if (selectionLast != null) {
                    cropToJSON();
                    updatedSelectedCropProfile();
                    crop.setEnabled(false);
                    isCropDone = true;
                } else {
                    Notification.show(TA.getCurrent().getParams().getLabel("crop.image.profile"));
                }
//                System.out.println("share.tenma.common.gui.display.profile.ProfileDialog.ProfileCustom.buttonClick");
            }
        }

        private void cropToJSON() {
            try {
//                System.out.println("filenameSelected = " + filenameSelected);
                BufferedImage img = ImageIO.read(new File(filenameSelected));
                BufferedImage imgRs = Scalr.crop(img, selectionLast.getX(), selectionLast.getY(), selectionLast.getWidth(), selectionLast.getHeight(),
                        Scalr.OP_ANTIALIAS);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(imgRs, "png", baos);
                byte[] bytes = baos.toByteArray();
//                System.out.println("bytes.length = " + bytes.length);

                Gson gson = new Gson();
                jsonImage = gson.toJson(bytes, byte[].class);
//                System.out.println("JSON = " + jsonImage.length());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
