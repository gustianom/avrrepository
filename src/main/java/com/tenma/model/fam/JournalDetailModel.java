package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

/**
 * User: derry.irmansyah
 * Date: 2/25/13
 * Time: 4:59 PM
 */
public class JournalDetailModel extends AuditModel implements java.io.Serializable {
    private String journalHeaderNumber;
    private int orderNumber;
    private String trxDesc;
    private String trxCurr;
    private String accountId;
    private Double orgAmount;
    private Double baseAmount;
    private Integer balance;
    private String journalDetailNumber;


    private String journalDetailNumberOld;
    private String accountName;
    private String communityId;
    private boolean dirty;
    private boolean deleted;

    private String reconcile;


    //JUST FOR REPORT BASED ON BASE AMOUNT ;
    private double creditAmount;
    private double debitAmount;
    private String ref;
    private Date trxDate;
    private double totalAmount; // saldo .

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getJournalHeaderNumber() {
        return journalHeaderNumber;
    }

    public void setJournalHeaderNumber(String journalHeaderNumber) {
        this.journalHeaderNumber = journalHeaderNumber;
    }


    public String getTrxDesc() {
        return trxDesc;
    }

    public void setTrxDesc(String trxDesc) {
        this.trxDesc = trxDesc;
    }

    public String getTrxCurr() {
        return trxCurr;
    }

    public void setTrxCurr(String trxCurr) {
        this.trxCurr = trxCurr;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    public Double getOrgAmount() {
        return orgAmount;
    }

    public void setOrgAmount(Double orgAmount) {
        this.orgAmount = orgAmount;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getJournalDetailNumber() {
        return journalDetailNumber;
    }

    public void setJournalDetailNumber(String journalDetailNumber) {
        this.journalDetailNumber = journalDetailNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getJournalDetailNumberOld() {
        return journalDetailNumberOld;
    }

    public void setJournalDetailNumberOld(String journalDetailNumberOld) {
        this.journalDetailNumberOld = journalDetailNumberOld;
    }

    public String getReconcile() {
        return reconcile;
    }

    public void setReconcile(String reconcile) {
        this.reconcile = reconcile;
    }
}
