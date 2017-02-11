package com.tenma.common.gui.display.component;

/**
 * Created by ndwijaya on 10/20/2015.
 */
public interface TenmaImageUploadEvent {
    public void imageUploadEvent(byte[] bytes, String fileName, String mimeType, long size);
}
