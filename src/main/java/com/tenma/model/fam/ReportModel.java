/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: sigit
 * Date: 12/12/12
 * Time: 2:55 PM
 */
public class ReportModel extends AuditModel {
    private Double spending, income, totalTransaction;
    private String userId, transactionId;

    public Double getSpending() {
        return spending;
    }

    public void setSpending(Double spending) {
        this.spending = spending;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(Double totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
