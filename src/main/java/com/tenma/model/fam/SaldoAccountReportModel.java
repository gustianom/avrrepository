package com.tenma.model.fam;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sigit Hadi Wibowo
 * Date: 26/10/14
 * Time: 16:19
 * Copyright (c) 2014 . PT TENMA BRIGHT SKY. ALL Right Reserved
 */
public class SaldoAccountReportModel extends AccountModel {

    private double amount;
    private List<SaldoAccountReportModel> accountList;

    public List<SaldoAccountReportModel> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<SaldoAccountReportModel> accountList) {
        this.accountList = accountList;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
