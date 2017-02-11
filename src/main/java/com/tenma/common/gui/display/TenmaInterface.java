package com.tenma.common.gui.display;

import com.vaadin.ui.Component;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 9/9/12
 * Time: 3:13 PM
 */
public interface TenmaInterface {
    public void setFocus(Component componentName);

    public String getTaskId();

    public void setTaskId(String taskId);
}
