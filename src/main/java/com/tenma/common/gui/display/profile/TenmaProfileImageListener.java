package com.tenma.common.gui.display.profile;

import com.vaadin.ui.Image;

/**
 * Created by ndwijaya on 10/5/15.
 */
public interface TenmaProfileImageListener {
    public void ApplySelectedImageProfile(Image profile, String json);

    public Image getImage();

    public String getCropStyle(); // return style if crop result require to be set with spesific style

    public void CropNotReady();
}
