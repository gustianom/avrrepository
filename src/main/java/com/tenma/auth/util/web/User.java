package com.tenma.auth.util.web;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:13 AM
 * To change this template use File | Settings | File Templates.
 */
public interface User {
    public HashMap<String, Boolean> getFunctionMap();

    public void setFunctionMap(HashMap<String, Boolean> functionMap);
}