/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Fri Dec 13 15:28:34 WIT 2013
 */

package com.tenma.sms.model;

import com.tenma.auth.model.AuditModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class SmsCommunityProfileModel extends AuditModel {
    private String communityId;
    private String communityName;
    private String userId;

    private Integer smsBasequota;
    private Integer smsPaymentType;
    private Integer smsCreditLimit;
    private Integer smsUpdateMode;
    private Integer smsUpdateFreq;
    private Integer smsSubscribtionType;
    private Integer smsBalanceCarryForward;
    private Integer smsSystemDeducted;
    private Integer smsBalanceThreshold;
    private Integer smsThresholdValue;
    private Integer smsPaymentGracePeriod;
    private Integer smsCurrentBalance;
    private Integer smsTransactionValue;
    private Double smsPricing;
    private Date smsStartCycleDate;
    private Date lastInvoiceGeneratedDate;
    @NotNull
    @Max(3)
    private String smsPricingCurrency;
    @NotNull
    @Min(1)
    private Integer smsForeignFactor;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    /**
     * <ul>
     * <li>0 -> NORMAL (STANDARD SUBSRIBTION)</li>
     * <li>1 -> PROMO (noT SUBSCRIBED BUT GIVEN PROMO BALANCE > NO TOPUP AND BALANCE NOTIFICATION)</li>
     * <li>2 -> NO SMS at all AND NO NOTIFICATION RELATED TO SMS</li>
     * </ul>
     *
     * @return
     */
    public Integer getSmsSubscribtionType() {
        return smsSubscribtionType;
    }

    /**
     * <ul>
     * <li>0 -> NORMAL (STANDARD SUBSRIBTION)</li>
     * <li>1 -> PROMO (noT SUBSCRIBED BUT GIVEN PROMO BALANCE > NO TOPUP AND BALANCE NOTIFICATION)</li>
     * <li>2 -> NO SMS at all AND NO NOTIFICATION RELATED TO SMS</li>
     * </ul>
     *
     * @param smsSubscribtionType
     */
    public void setSmsSubscribtionType(Integer smsSubscribtionType) {
        this.smsSubscribtionType = smsSubscribtionType;
    }

    public Integer getSmsBasequota() {
        return this.smsBasequota;
    }

    public void setSmsBasequota(Integer smsBasequota) {
        this.smsBasequota = smsBasequota;
    }

    /**
     * 0 PREPAID 1 POSTPAID
     *
     * @return
     */
    public Integer getSmsPaymentType() {
        return this.smsPaymentType;
    }

    /**
     * 0 PREPAID 1 POSTPAID
     *
     * @param smsPaymentType
     */
    public void setSmsPaymentType(Integer smsPaymentType) {
        this.smsPaymentType = smsPaymentType;
    }

    /**
     * BALANCE CREDIT LIMIT APPLY FOR POSTPAID ONLY
     *
     * @return
     */
    public Integer getSmsCreditLimit() {
        return this.smsCreditLimit;
    }

    /**
     * BALANCE CREDIT LIMIT APPLY FOR POSTPAID ONLY
     *
     * @return
     */
    public void setSmsCreditLimit(Integer smsCreditLimit) {
        this.smsCreditLimit = smsCreditLimit;
    }

    /**
     * 0 AUTOMATIC 1 MANUAL
     *
     * @return
     */
    public Integer getSmsUpdateMode() {
        return this.smsUpdateMode;
    }

    /**
     * 0 AUTOMATIC 1 MANUAL
     *
     * @param smsUpdateMode
     */
    public void setSmsUpdateMode(Integer smsUpdateMode) {
        this.smsUpdateMode = smsUpdateMode;
    }

    /**
     * in Days
     *
     * @return
     */
    public Integer getSmsUpdateFreq() {
        return this.smsUpdateFreq;
    }

    /**
     * in days
     *
     * @param smsUpdateFreq
     */
    public void setSmsUpdateFreq(Integer smsUpdateFreq) {
        this.smsUpdateFreq = smsUpdateFreq;
    }

    /**
     * 0 NO,  1 YES FOR TOP UP ONLY,  2 ALL
     *
     * @return
     */
    public Integer getSmsBalanceCarryForward() {
        return this.smsBalanceCarryForward;
    }

    /**
     * 0 NO,  1 YES FOR TOP UP ONLY,  2 ALL
     *
     * @return
     */
    public void setSmsBalanceCarryForward(Integer smsBalanceCarryForward) {
        this.smsBalanceCarryForward = smsBalanceCarryForward;
    }

    public Integer getSmsSystemDeducted() {
        return this.smsSystemDeducted;
    }

    public void setSmsSystemDeducted(Integer smsSystemDeducted) {
        this.smsSystemDeducted = smsSystemDeducted;
    }

    /**
     * 0 NO 1 YES
     *
     * @return
     */
    public Integer getSmsBalanceThreshold() {
        return this.smsBalanceThreshold;
    }

    /**
     * 0 NO 1 YES
     *
     * @param smsBalanceThreshold
     */
    public void setSmsBalanceThreshold(Integer smsBalanceThreshold) {
        this.smsBalanceThreshold = smsBalanceThreshold;
    }

    public Integer getSmsThresholdValue() {
        return this.smsThresholdValue;
    }

    public void setSmsThresholdValue(Integer smsThresholdValue) {
        this.smsThresholdValue = smsThresholdValue;
    }

    public Integer getSmsPaymentGracePeriod() {
        return this.smsPaymentGracePeriod;
    }

    public void setSmsPaymentGracePeriod(Integer smsPaymentGracePeriod) {
        this.smsPaymentGracePeriod = smsPaymentGracePeriod;
    }

    /**
     * Current SMS Balance, updated from realtime sms transaction
     *
     * @return
     */
    public Integer getSmsCurrentBalance() {
        return smsCurrentBalance;
    }

    /**
     * CUrrent SMS Balance, updated from realtime sms transaction
     *
     * @param smsCurrentBalance
     */
    public void setSmsCurrentBalance(Integer smsCurrentBalance) {
        this.smsCurrentBalance = smsCurrentBalance;
    }

    /**
     * the value berapa banyak sms di deduct atau di topup, akan update currentsmsbalance)
     *
     * @return
     */
    public Integer getSmsTransactionValue() {
        return smsTransactionValue;
    }

    /**
     * the value berapa banyak sms di deduct atau di topup, akan update currentsmsbalance)
     *
     * @param smsTransactionValue
     */
    public void setSmsTransactionValue(Integer smsTransactionValue) {
        this.smsTransactionValue = smsTransactionValue;
    }

    public Double getSmsPricing() {
        return smsPricing;
    }

    public void setSmsPricing(Double smsPricing) {
        this.smsPricing = smsPricing;
    }

    public String getSmsPricingCurrency() {
        return smsPricingCurrency;
    }

    public void setSmsPricingCurrency(String smsPricingCurrency) {
        this.smsPricingCurrency = smsPricingCurrency;
    }

    public Integer getSmsForeignFactor() {
        return smsForeignFactor;
    }

    public void setSmsForeignFactor(Integer smsForeignFactor) {
        this.smsForeignFactor = smsForeignFactor;
    }

    public Date getSmsStartCycleDate() {
        return smsStartCycleDate;
    }

    public void setSmsStartCycleDate(Date smsStartCycleDate) {
        this.smsStartCycleDate = smsStartCycleDate;
    }

    /**
     * kapan terakhir sms invoice di generate
     *
     * @return
     */
    public Date getLastInvoiceGeneratedDate() {
        return lastInvoiceGeneratedDate;
    }

    /**
     * kapan terakhir sms invoice di generate
     */
    public void setLastInvoiceGeneratedDate(Date lastInvoiceGeneratedDate) {
        this.lastInvoiceGeneratedDate = lastInvoiceGeneratedDate;
    }
}