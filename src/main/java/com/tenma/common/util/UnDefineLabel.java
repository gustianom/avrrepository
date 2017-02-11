package com.tenma.common.util;

import java.util.Date;

/**
 * Created by ndwijaya on 2/12/2015.
 */
public class UnDefineLabel {
    private Date eventDate;
    private int counter;
    private String labelKey;
    private String language;

    public UnDefineLabel(Date eventDate, int counter, String labelKey, String language) {
        this.eventDate = eventDate;
        this.counter = counter;
        this.labelKey = labelKey;
        this.language = language;
    }

    public void addEvent() {
        counter++;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
