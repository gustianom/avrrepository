package com.tenma.auth.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 4/1/12
 * Time: 2:31 PM
 * <p>
 * 10-10-2015 18:32 PM
 * - Adding OwnerId (required for limiting record access by specific user)
 * - remove searchValue
 */
public class AuditModel implements Serializable {
    private String createdFrom, createdBy, updatedFrom, updatedBy;
    private Date createdDate, updatedDate;
    private int ownerId;

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedFrom() {
        return updatedFrom;
    }

    public void setUpdatedFrom(String updatedFrom) {
        this.updatedFrom = updatedFrom;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}

