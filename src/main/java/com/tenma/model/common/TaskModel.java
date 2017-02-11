package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

/**
 * User: derry.irmansyah
 * Date: 1/21/13
 * Time: 2:34 PM
 */
public class TaskModel extends AuditModel {
    private String taskId;
    private String taskName;
    private Date deadline;
    private String taskDesc;
    private Integer priority;
    private String picMemberType;
    private String ccMemberType;
    private String communityId;

    private Integer taskActionId;
    private String taskActionClass;
    private Integer taskStatus;
    private Boolean isPrivate;
    private Date taskDate;
    private String createdBy;
    private int owner;
    /**
     * task flag for system mandatory setup
     */
    private String systemMandatory;

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPicMemberType() {
        return picMemberType;
    }

    public void setPicMemberType(String picMemberType) {
        this.picMemberType = picMemberType;
    }

    public String getCcMemberType() {
        return ccMemberType;
    }

    public void setCcMemberType(String ccMemberType) {
        this.ccMemberType = ccMemberType;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getTaskActionId() {
        return taskActionId;
    }

    public void setTaskActionId(Integer taskActionId) {
        this.taskActionId = taskActionId;
    }

    public String getTaskActionClass() {
        return taskActionClass;
    }

    public void setTaskActionClass(String taskActionClass) {
        this.taskActionClass = taskActionClass;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * task flag for system mandatory setup
     */
    public String getSystemMandatory() {
        return systemMandatory;
    }

    /**
     * task flag for system mandatory setup
     */
    public void setSystemMandatory(String systemMandatory) {
        this.systemMandatory = systemMandatory;
    }
}

