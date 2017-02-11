/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Sat Nov 23 13:22:59 WIT 2013
 */

package com.tenma.model.common;

import com.tenma.auth.model.CommunityMemberModel;

public class TaskMemberModel extends CommunityMemberModel {
    private String taskId;
    private Integer todoStatus;
    private String picMember;

    public String getPicMember() {
        return picMember;
    }

    public void setPicMember(String picMember) {
        this.picMember = picMember;
    }

    public Integer getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(Integer todoStatus) {
        this.todoStatus = todoStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}