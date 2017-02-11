package com.tenma.auth.model;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Fri Oct 24 14:43:08 WIB 2014
 */

public class AccountCommunityModel extends AuditModel {
    private String communityId;
    private String accountReceiveable;
    private String accountPayable;
    private String advanceSales;
    private String salesDiscount;
    private String realizedGainLoss;
    private String unrealizedGainLoss;
    private String advancePurchase;
    private String defInventory;
    private String defSales;
    private String defSalesReturn;
    private String defSalesItemDiscount;
    private String defGoodInTransit;
    private String defCogs;
    private String defPurchaseReturn;
    private String defUnbilledGood;
    private String defFixedAsset;
    private String defDepreciation;
    private String defExpDepreciation;
    private String uninvoicedPayable;

    public String getUninvoicedPayable() {
        return uninvoicedPayable;
    }

    public void setUninvoicedPayable(String uninvoicedPayable) {
        this.uninvoicedPayable = uninvoicedPayable;
    }

    public String getDefExpDepreciation() {
        return defExpDepreciation;
    }

    public void setDefExpDepreciation(String defExpDepreciation) {
        this.defExpDepreciation = defExpDepreciation;
    }

    public String getDefDepreciation() {
        return defDepreciation;
    }

    public void setDefDepreciation(String defDepreciation) {
        this.defDepreciation = defDepreciation;
    }

    public String getDefFixedAsset() {
        return defFixedAsset;
    }

    public void setDefFixedAsset(String defFixedAsset) {
        this.defFixedAsset = defFixedAsset;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getAccountReceiveable() {
        return this.accountReceiveable;
    }

    public void setAccountReceiveable(String accountReceiveable) {
        this.accountReceiveable = accountReceiveable;
    }

    public String getAccountPayable() {
        return this.accountPayable;
    }

    public void setAccountPayable(String accountPayable) {
        this.accountPayable = accountPayable;
    }

    public String getAdvanceSales() {
        return this.advanceSales;
    }

    public void setAdvanceSales(String advanceSales) {
        this.advanceSales = advanceSales;
    }

    public String getSalesDiscount() {
        return this.salesDiscount;
    }

    public void setSalesDiscount(String salesDiscount) {
        this.salesDiscount = salesDiscount;
    }

    public String getRealizedGainLoss() {
        return this.realizedGainLoss;
    }

    public void setRealizedGainLoss(String realizedGainLoss) {
        this.realizedGainLoss = realizedGainLoss;
    }

    public String getUnrealizedGainLoss() {
        return this.unrealizedGainLoss;
    }

    public void setUnrealizedGainLoss(String unrealizedGainLoss) {
        this.unrealizedGainLoss = unrealizedGainLoss;
    }

    public String getAdvancePurchase() {
        return this.advancePurchase;
    }

    public void setAdvancePurchase(String advancePurchase) {
        this.advancePurchase = advancePurchase;
    }

    public String getDefInventory() {
        return this.defInventory;
    }

    public void setDefInventory(String defInventory) {
        this.defInventory = defInventory;
    }

    public String getDefSales() {
        return this.defSales;
    }

    public void setDefSales(String defSales) {
        this.defSales = defSales;
    }

    public String getDefSalesReturn() {
        return this.defSalesReturn;
    }

    public void setDefSalesReturn(String defSalesReturn) {
        this.defSalesReturn = defSalesReturn;
    }

    public String getDefSalesItemDiscount() {
        return this.defSalesItemDiscount;
    }

    public void setDefSalesItemDiscount(String defSalesItemDiscount) {
        this.defSalesItemDiscount = defSalesItemDiscount;
    }

    public String getDefGoodInTransit() {
        return this.defGoodInTransit;
    }

    public void setDefGoodInTransit(String defGoodInTransit) {
        this.defGoodInTransit = defGoodInTransit;
    }

    public String getDefCogs() {
        return this.defCogs;
    }

    public void setDefCogs(String defCogs) {
        this.defCogs = defCogs;
    }

    public String getDefPurchaseReturn() {
        return this.defPurchaseReturn;
    }

    public void setDefPurchaseReturn(String defPurchaseReturn) {
        this.defPurchaseReturn = defPurchaseReturn;
    }

    public String getDefUnbilledGood() {
        return this.defUnbilledGood;
    }

    public void setDefUnbilledGood(String defUnbilledGood) {
        this.defUnbilledGood = defUnbilledGood;
    }

    @Override
    public String toString() {
        return "CommunityID=" + communityId + ", accountReceiveable=" + accountReceiveable + " accountPayable=" + accountPayable + ", defInventory=" + defInventory;
    }
}