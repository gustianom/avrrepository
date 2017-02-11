package com.tenma.common.gui.display.component;

import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

/**
 * Created by PT Tenma Bright Sky
 * User: administrator
 * Date: 5/21/13
 * Time: 2:55 PM
 */
public class TenmaButtonPanel extends CustomComponent {
    public static int ICON_LOCATION_TOP = 10;
    public static int ICON_LOCATION_LEFT = 20;
    public static int ICON_LOCATION_BOTTOM = 30;
    public static int ICON_LOCATION_RIGHT = 40;
    public static int SELECTION_TYPE_OPTION_GROUP = 1000;
    public static int SELECTION_TYPE_OPTION_CHECKBOX = 2000;

    private Image resourceImage;
    private Object data;
    private String myComponentId;
    private LayoutEvents.LayoutClickListener layoutClickListener = null;
    private boolean isCheckBoxAvailable = false;
    private boolean isAvailableCheckBoxSelected = false;


    private int iconLocation;
    private Layout detailContex;

    private VerticalLayout generalLayout = new VerticalLayout();
    private String frameStyleName, contentStyleName;
    private CheckBox checkBox;
    private Component shortcutImageProfile;
    private Component titleCaptionComponent;

    public TenmaButtonPanel(String componentId, Component titleCaptionComponent, Image resourceImage, Object data, LayoutEvents.LayoutClickListener layoutClickListener) {
        initalizeParameter(componentId, titleCaptionComponent, resourceImage, data, layoutClickListener, false, false);
    }

    public TenmaButtonPanel(String componentId, Component titleCaptionComponent, Image resourceImage, Object data, LayoutEvents.LayoutClickListener layoutClickListener, boolean isCheckBoxAvailable, boolean isAvailableCheckBoxSelected) {
        initalizeParameter(componentId, titleCaptionComponent, resourceImage, data, layoutClickListener, isCheckBoxAvailable, isAvailableCheckBoxSelected);
    }

    public TenmaButtonPanel(String componentId, Component titleCaptionComponent, Image resourceImage, Object data) {
        initalizeParameter(componentId, titleCaptionComponent, resourceImage, data, null, false, false);
    }

    /**
     * this costructor is for button which checkbox inside, the usage is for selection of available list.
     * this method execute isCheckBoxAvailable into true value, the default is false;
     *
     * @param componentId
     * @param titleCaptionComponent
     * @param resourceImage
     * @param data
     * @param isCheckBoxSelected
     */
    public TenmaButtonPanel(String componentId, Component titleCaptionComponent, Image resourceImage, Object data, boolean isCheckBoxSelected) {
        initalizeParameter(componentId, titleCaptionComponent, resourceImage, data, null, true, isCheckBoxSelected);
    }

    public Object getData() {
        return data;
    }


    private void initalizeParameter(String componentId, Component titleCaptionComponent, Image resourceImage, Object data, LayoutEvents.LayoutClickListener layoutClickListener, boolean isCheckBoxAvailble, boolean isCheckBoxSelected) {
        this.titleCaptionComponent = titleCaptionComponent;
        setTitleCaptionComponent(titleCaptionComponent);
        this.resourceImage = resourceImage;
        this.layoutClickListener = layoutClickListener;
        this.data = data;
        this.myComponentId = componentId;
        this.isCheckBoxAvailable = isCheckBoxAvailble;
        this.isAvailableCheckBoxSelected = isCheckBoxSelected;

    }

    public void doGenerateUI(int iconLocation, Layout detailContex) {
        this.detailContex = detailContex;
        setIconLocation(iconLocation);
        preparintUI();
    }

    public Component getTitleCaptionComponent() {
        return titleCaptionComponent;
    }

    public void setTitleCaptionComponent(Component titleCaptionComponent) {
        this.titleCaptionComponent = titleCaptionComponent;
    }

    public void setShortcutImageProfile(Component shortcutImageProfile) {
        this.shortcutImageProfile = shortcutImageProfile;
    }

    private void preparintUI() {
        if (resourceImage == null)
            resourceImage = new Image(null, new ThemeResource("layouts/images/noimage.png"));


        resourceImage.setData(data);
//        resourceImage.setWidth("80px");
//        resourceImage.setHeight("80px");

        VerticalLayout imageLayout = new VerticalLayout();
        imageLayout.addComponent(resourceImage);
        if (shortcutImageProfile != null) {
            imageLayout.addComponent(shortcutImageProfile);
            imageLayout.setComponentAlignment(shortcutImageProfile, Alignment.MIDDLE_CENTER);

        }

        imageLayout.setComponentAlignment(resourceImage, Alignment.MIDDLE_CENTER);

        if (detailContex == null) detailContex = new VerticalLayout();

        VerticalLayout detail = new VerticalLayout();
        detail.setData(data);
        if (getTitleCaptionComponent() != null) {
            VerticalLayout hl = new VerticalLayout();
            hl.addComponent(getTitleCaptionComponent());
            hl.setData(data);
            detail.addComponent(hl);
            detail.setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
        }

        Layout layout = new VerticalLayout();
        if ((iconLocation == ICON_LOCATION_TOP) || (iconLocation == ICON_LOCATION_BOTTOM)) {
            layout = new VerticalLayout();
        } else {
            layout = new HorizontalLayout();
        }

        if ((iconLocation == ICON_LOCATION_TOP) || (iconLocation == ICON_LOCATION_LEFT)) {
            layout.addComponent(imageLayout);

        }

        layout.addComponent(detailContex);

        if ((iconLocation == ICON_LOCATION_BOTTOM) || (iconLocation == ICON_LOCATION_RIGHT))
            layout.addComponent(imageLayout);

        detail.addComponent(layout);
        if (isCheckBoxAvailable) {
            checkBox = new CheckBox();
            checkBox.setValue(isAvailableCheckBoxSelected);
            detail.addComponent(checkBox);
            detail.setComponentAlignment(checkBox, Alignment.MIDDLE_CENTER);
        }
        generalLayout.setData(data);

        if (layoutClickListener != null)
            generalLayout.addLayoutClickListener(layoutClickListener);

        layout.setStyleName(getContentStyleName());
        generalLayout.setStyleName(getFrameStyleName());
        generalLayout.addComponent(detail);
        setCompositionRoot(generalLayout);
    }


    public CheckBox getCheckBox() {
        return checkBox;
    }

    public String getFrameStyleName() {
        return frameStyleName;
    }

    public void setFrameStyleName(String frameStyleName) {
        this.frameStyleName = frameStyleName;
    }

    public String getContentStyleName() {
        return contentStyleName;
    }

    public void setContentStyleName(String contentStyleName) {
        this.contentStyleName = contentStyleName;
    }

    public VerticalLayout getLayout() {
        return generalLayout;
    }

    public int getIconLocation() {
        return iconLocation;
    }

    private void setIconLocation(int iconLocation) {
        this.iconLocation = iconLocation;
    }

}