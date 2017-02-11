package com.tenma.common.gui.main.task;

/**
 * Created by tenma-01 on 26/01/16.
 */
public class SingleTon {
    private static SingleTon instance;
    private Object selectedObject;

    public SingleTon() {
    }

    public static synchronized SingleTon getInstance() {
        if (instance == null) {
            instance = new SingleTon();
        }
        return instance;
    }

    public Object getSelectedObject() {
        return this.selectedObject;
    }

    public void setSelectedObject(Object selectedObject) {
        this.selectedObject = selectedObject;
    }

}
