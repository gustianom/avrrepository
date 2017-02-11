package com.tenma.common.util.session;

import javax.servlet.http.HttpSession;

/**
 * Created by ndwijaya on 2/18/2016.
 */
public interface OnMessageEvent {
    public void fireOnMessgeEvent(Object o, HttpSession session);
}
