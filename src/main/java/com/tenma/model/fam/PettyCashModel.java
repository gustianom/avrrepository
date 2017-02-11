/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generated on Mon Jun 17 19:22:20 ICT 2013
 */

package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

public class PettyCashModel extends AuditModel {
    private Integer pettycashId;
    private String currency;
    private Double amount;
    private Date pettycashDate;
    private String pettycashDesc;
    private String communityId;
    private Integer status;
    private String pettycashFromTo;
    private String voucherId;
    private Date toDate;
    private Date fromDate;
    private Integer voucherTemplate;


    public Integer getPettycashId() {
        return pettycashId;
    }

    public void setPettycashId(Integer pettycashId) {
        this.pettycashId = pettycashId;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPettycashDate() {
        return this.pettycashDate;
    }

    public void setPettycashDate(Date pettycashDate) {
        this.pettycashDate = pettycashDate;
    }

    public String getPettycashDesc() {
        return this.pettycashDesc;
    }

    public void setPettycashDesc(String pettycashDesc) {
        this.pettycashDesc = pettycashDesc;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPettycashFromTo() {
        return pettycashFromTo;
    }

    public void setPettycashFromTo(String pettycashFromTo) {
        this.pettycashFromTo = pettycashFromTo;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getVoucherTemplate() {
        return voucherTemplate;
    }

    public void setVoucherTemplate(Integer voucherTemplate) {
        this.voucherTemplate = voucherTemplate;
    }
}