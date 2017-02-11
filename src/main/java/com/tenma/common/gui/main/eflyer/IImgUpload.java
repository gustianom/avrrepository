package com.tenma.common.gui.main.eflyer;

import java.io.File;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/16/2015
 * Time    : 5:34 PM
 * Project : udw
 * Package : share.tenma.common.gui.main.announcement
 */
public interface IImgUpload {
    public void finishUpload(File file);

    public void updateSize(float img1Width, float img1Height);
}
