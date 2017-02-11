/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Wed Jan 15 16:35:09 WIT 2014
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReminderModel extends AuditModel {
    private Integer reminderId;

    @NotNull
    private String reminderSubject;
    private String reminderDescription;

    @NotNull
    private Date reminderDuedate;

    /**
     * 1 SMS, 2 EMAIL, 3 ALL
     */
    @NotNull
    @Min(1)
    private Integer reminderMedia;

    private String reminderSMSDestination;
    private String reminderEmailDestination;
    private String communityId;

    private Boolean reminderRecuring;
    /**
     * IF RECURING IS TRUE the visible recuring factor are DAILY, WEEKLY, MONTHLY, YEARLY
     */
    private Date originalDueDate;
    private Integer originalDueValue;

    /**
     * 0 MINUTES, 1 HOUR, 2 DAYS, 3 WEEK
     */
    private Integer originalDueFactor;

    private Integer reminderRecuringFactor;
    private Integer recuringEvery;
    private Integer recuringEndType;
    private Integer recuringEndAfter;
    private Integer occurenceCount;
    private Date recuringEndDate;

    public ReminderModel() {
    }

    public ReminderModel(String communityId, String reminderSubject, String reminderDescription, Date reminderDuedate, Integer reminderMedia, String reminderSMSDestination, String reminderEmailDestination) {
        this.reminderSubject = reminderSubject;
        this.reminderDescription = reminderDescription;
        this.reminderDuedate = reminderDuedate;
        this.originalDueDate = reminderDuedate;
//        this.originalDueFactor = Constants.REMINDER_DUE_FACTOR.MINUTE.getValue();
        this.originalDueValue = 0;
        this.reminderMedia = reminderMedia;
        this.reminderSMSDestination = reminderSMSDestination;
        this.reminderEmailDestination = reminderEmailDestination;
        this.communityId = communityId;
    }

    public Integer getReminderId() {
        return this.reminderId;
    }

    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderSubject() {
        return this.reminderSubject;
    }

    public void setReminderSubject(String reminderSubject) {
        this.reminderSubject = reminderSubject;
    }

    public String getReminderDescription() {
        return this.reminderDescription;
    }

    public void setReminderDescription(String reminderDescription) {
        this.reminderDescription = reminderDescription;
    }

    public Date getReminderDuedate() {
        return this.reminderDuedate;
    }

    public void setReminderDuedate(Date reminderDuedate) {
        this.reminderDuedate = reminderDuedate;
    }

    public Integer getReminderMedia() {
        return this.reminderMedia;
    }

    public void setReminderMedia(Integer reminderMedia) {
        this.reminderMedia = reminderMedia;
    }

    public String getReminderSMSDestination() {
        return this.reminderSMSDestination;
    }

    public void setReminderSMSDestination(String reminderSMSDestination) {
        this.reminderSMSDestination = reminderSMSDestination;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Boolean getReminderRecuring() {
        return reminderRecuring;
    }

    public void setReminderRecuring(Boolean reminderRecuring) {
        this.reminderRecuring = reminderRecuring;
    }

    public Integer getReminderRecuringFactor() {
        return reminderRecuringFactor;
    }

    public void setReminderRecuringFactor(Integer reminderRecuringFactor) {
        this.reminderRecuringFactor = reminderRecuringFactor;
    }

    public Date getOriginalDueDate() {
        return originalDueDate;
    }

    public void setOriginalDueDate(Date originalDueDate) {
        this.originalDueDate = originalDueDate;
    }

    public Integer getOriginalDueValue() {
        return originalDueValue;
    }

    public void setOriginalDueValue(Integer originalDueValue) {
        this.originalDueValue = originalDueValue;
    }

    public Integer getOriginalDueFactor() {
        return originalDueFactor;
    }

    public void setOriginalDueFactor(Integer originalDueFactor) {
        this.originalDueFactor = originalDueFactor;
    }

    public Integer getRecuringEvery() {
        return recuringEvery;
    }

    public void setRecuringEvery(Integer recuringEvery) {
        this.recuringEvery = recuringEvery;
    }

    public Integer getRecuringEndType() {
        return recuringEndType;
    }

    public void setRecuringEndType(Integer recuringEndType) {
        this.recuringEndType = recuringEndType;
    }

    public Integer getRecuringEndAfter() {
        return recuringEndAfter;
    }

    public void setRecuringEndAfter(Integer recuringEndAfter) {
        this.recuringEndAfter = recuringEndAfter;
    }

    public Date getRecuringEndDate() {
        return recuringEndDate;
    }

    public void setRecuringEndDate(Date recuringEndDate) {
        this.recuringEndDate = recuringEndDate;
    }

    public String getReminderEmailDestination() {
        return reminderEmailDestination;
    }

    public void setReminderEmailDestination(String reminderEmailDestination) {
        this.reminderEmailDestination = reminderEmailDestination;
    }

    public Integer getOccurenceCount() {
        return occurenceCount;
    }

    public void setOccurenceCount(Integer occurenceCount) {
        this.occurenceCount = occurenceCount;
    }
}