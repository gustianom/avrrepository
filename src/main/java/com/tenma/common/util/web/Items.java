package com.tenma.common.util.web;

import java.io.Serializable;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:12 AM
 */
public class Items implements Serializable {
    private String label;
    private Object value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Items setGetItems(String newLabel, String newValue) {
        this.label = newLabel;
        this.value = newValue;
        return this;
    }
}