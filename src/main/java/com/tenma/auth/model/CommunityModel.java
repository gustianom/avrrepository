package com.tenma.auth.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * <p/>
 * Version 1.0.
 * <p/>
 * Generated on Thu Dec 12 16:34:58 ICT 2012
 * <p/>
 * Community Model Description
 * <p/><ul>
 * <li>communityId;</li>
 * <li>String communityName;</li>
 * <li>String communityDesc;</li>
 * <li>String parentCommunityId;</li>
 * <li>String communityStructure;</li>
 * <li>String communityStructureName; </li>
 * <li>int communityDashboard;</li>
 * <li>boolean communityType;</li>
 * <li>String userId;</li>
 * <li>boolean multipleCurrency;</li>
 * <li>String basedCurrency;</li>
 * <li>int timeZone;</li>
 * <li>Integer startFinancialYear;</li>
 * <li>Integer financialClosingType; // 100 manual 200 automatic</li>
 * <li>Integer financialRevaluationType; // 300 manual 400 automatic</li>
 * <li>Date financialRevaluationDate;</li>
 * <li>String returnEarningAccount, realizedRevaluationAccount, unRealizedRevaluationAccount, interCompanyAccount;</li>
 * <li>Integer exchangeRateId; //collected from table of exchange rate (contains Yahoo, etc)</li>
 * <li>boolean sms, notification, email;</li>
 * <li>String accountPettyCash;</li>
 * <li> String accountPettyCashExpend;</li>
 * <li>Integer licenseStatus; </li>
 * <li> Integer gracePeriod;</li>
 * <li> String communityLogo;</li>
 * <li> Date licenseValidDate</li>
 * <li> char treatmentType -> how to handle current customer, when the license status is updated</li>
 * <li> subscribedModeration;// flag for subscribtion request need/not need moderation</li>
 * <li>communityDomain -> for community Domain</li>
 * <li>communityDomain -> for community Domain</li>
 * </ul>
 */
public class CommunityModel extends AuditModel implements Cloneable {
    private String communityId;

    @NotNull(message = "Community field must have a value")
    @Size(min = 3, max = 100, message = "Please enter a community name")
    private String communityName;

    @Size(min = 3, max = 50, message = "Please enter a nick name")
    private String communityNickName;

    @Size(min = 0, max = 200, message = "Please enter a community desc")
    private String communityDesc;
    private String parentCommunityId;

    private String communityStructureType;
    private String communityStructure;
    private String communityStructureName;
    private boolean communityType;
    private String userId;

    private boolean multipleCurrency;
    private String basedCurrency;
    private int timeZone;

    private Integer startFinancialYear;
    private Integer financialClosingType; // 100 manual 200 automatic
    private Integer financialRevaluationType; // 300 manual 400 automatic
    private Date financialRevaluationDate;

    private String returnEarningAccount, realizedRevaluationAccount, unRealizedRevaluationAccount, interCompanyAccount;

    private Integer exchangeRateId; //collected from table of exchange rate (contains Yahoo, etc)

    private boolean sms, notification, email;
    private String accountPettyCash;
    private String accountPettyCashExpend;

    private String communityLogo;

    private Date licenseValidDate;
    private Integer licenseStatus;

    private char treatmentType;
    private boolean subscribedModeration;// flag for subscribtion request need/not need moderation

    /**
     * F For Free Community P for paid community
     */
    @Size(min = 1, max = 1)
    @NotNull
    private String commercialType;
    private Integer communitySupport;
    private Integer supportType;

    @Size(min = 0, max = 50, message = "Please enter a community domain")
    private String communityDomain;

    private boolean fullCommunityDomain;
    private Integer movementType;
    private Integer defDepreciationMethod;
    private Integer defLanguage;
    private Boolean defInventoryInterest;
    private String parallelAccounting;
    private AccountCommunityModel communityAccount = new AccountCommunityModel();
    private Boolean useTax;
    private Boolean discountPerItem;
    private Boolean salesMulticurrency;
    private Integer defWarehouse;

    private String gcmApplicationPlatform;
    private String gcmRoutingARN;

    public CommunityModel() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public boolean isFullCommunityDomain() {
        return fullCommunityDomain;
    }

    public void setFullCommunityDomain(boolean fullCommunityDomain) {
        this.fullCommunityDomain = fullCommunityDomain;
    }

    public Integer getDefWarehouse() {
        return defWarehouse;
    }

    public void setDefWarehouse(Integer defWarehouse) {
        this.defWarehouse = defWarehouse;
    }

    public Boolean getSalesMulticurrency() {
        return salesMulticurrency;
    }

    public void setSalesMulticurrency(Boolean salesMulticurrency) {
        this.salesMulticurrency = salesMulticurrency;
    }

    public Boolean getDiscountPerItem() {
        return discountPerItem;
    }

    public void setDiscountPerItem(Boolean discountPerItem) {
        this.discountPerItem = discountPerItem;
    }

    public Object getOne() throws CloneNotSupportedException {
        return this.clone();
    }

    public Boolean getUseTax() {
        return useTax;
    }

    public void setUseTax(Boolean useTax) {
        this.useTax = useTax;
    }

    public String getParallelAccounting() {
        return parallelAccounting;
    }

    public void setParallelAccounting(String parallelAccounting) {
        this.parallelAccounting = parallelAccounting;
    }

    public Boolean getDefInventoryInterest() {
        return defInventoryInterest;
    }

    public void setDefInventoryInterest(Boolean defInventoryInterest) {
        this.defInventoryInterest = defInventoryInterest;
    }

    public Integer getDefLanguage() {
        return defLanguage;
    }

    public void setDefLanguage(Integer defLanguage) {
        this.defLanguage = defLanguage;
    }

    public Integer getDefDepreciationMethod() {
        return defDepreciationMethod;
    }

    public void setDefDepreciationMethod(Integer defDepreciationMethod) {
        this.defDepreciationMethod = defDepreciationMethod;
    }

    public String getParentCommunityId() {
        return parentCommunityId;
    }

    public void setParentCommunityId(String parentCommunityId) {
        this.parentCommunityId = parentCommunityId;
    }

    public String getCommunityStructureName() {
        return communityStructureName;
    }

    public void setCommunityStructureName(String communityStructureName) {
        this.communityStructureName = communityStructureName;
    }

    public String getCommunityStructureType() {
        return communityStructureType;
    }

    public void setCommunityStructureType(String communityStructureType) {
        this.communityStructureType = communityStructureType;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isMultipleCurrency() {
        return multipleCurrency;
    }

    public void setMultipleCurrency(boolean multipleCurrency) {
        this.multipleCurrency = multipleCurrency;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getCommunityStructure() {
        return communityStructure;
    }

    public void setCommunityStructure(String communityStructure) {
        this.communityStructure = communityStructure;
    }

    public boolean isCommunityType() {
        return communityType;
    }

    public void setCommunityType(boolean communityType) {
        this.communityType = communityType;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityDesc() {
        return communityDesc;
    }

    public void setCommunityDesc(String communityDesc) {
        this.communityDesc = communityDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountPettyCash() {
        return accountPettyCash;
    }

    public void setAccountPettyCash(String accountPettyCash) {
        this.accountPettyCash = accountPettyCash;
    }

    public String getAccountPettyCashExpend() {
        return accountPettyCashExpend;
    }

    public void setAccountPettyCashExpend(String accountPettyCashExpend) {
        this.accountPettyCashExpend = accountPettyCashExpend;
    }

    public Integer getStartFinancialYear() {
        return startFinancialYear;
    }

    public void setStartFinancialYear(Integer startFinancialYear) {
        this.startFinancialYear = startFinancialYear;
    }

    public Integer getFinancialClosingType() {
        return financialClosingType;
    }

    public void setFinancialClosingType(Integer financialClosingType) {
        this.financialClosingType = financialClosingType;
    }

    public Integer getFinancialRevaluationType() {
        return financialRevaluationType;
    }

    public void setFinancialRevaluationType(Integer financialRevaluationType) {
        this.financialRevaluationType = financialRevaluationType;
    }

    public Date getFinancialRevaluationDate() {
        return financialRevaluationDate;
    }

    public void setFinancialRevaluationDate(Date financialRevaluationDate) {
        this.financialRevaluationDate = financialRevaluationDate;
    }

    public Integer getExchangeRateId() {
        return exchangeRateId;
    }

    public void setExchangeRateId(Integer exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    public Integer getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(Integer licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getReturnEarningAccount() {
        return returnEarningAccount;
    }

    public void setReturnEarningAccount(String returnEarningAccount) {
        this.returnEarningAccount = returnEarningAccount;
    }

    public String getRealizedRevaluationAccount() {
        return realizedRevaluationAccount;
    }

    public void setRealizedRevaluationAccount(String realizedRevaluationAccount) {
        this.realizedRevaluationAccount = realizedRevaluationAccount;
    }

    public String getUnRealizedRevaluationAccount() {
        return unRealizedRevaluationAccount;
    }

    public void setUnRealizedRevaluationAccount(String unRealizedRevaluationAccount) {
        this.unRealizedRevaluationAccount = unRealizedRevaluationAccount;
    }

    public String getInterCompanyAccount() {
        return interCompanyAccount;
    }

    public void setInterCompanyAccount(String interCompanyAccount) {
        this.interCompanyAccount = interCompanyAccount;
    }

    public String getCommunityLogo() {
        return communityLogo;
    }

    public void setCommunityLogo(String communityLogo) {
        this.communityLogo = communityLogo;
    }

    public Date getLicenseValidDate() {
        return licenseValidDate;
    }

    public void setLicenseValidDate(Date licenseValidDate) {
        this.licenseValidDate = licenseValidDate;
    }

    public String getBasedCurrency() {
        return basedCurrency;
    }

    public void setBasedCurrency(String basedCurrency) {
        this.basedCurrency = basedCurrency;
    }

    public char getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(char treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(String commercialType) {
        this.commercialType = commercialType;
    }

    public Integer getCommunitySupport() {
        return communitySupport;
    }

    public void setCommunitySupport(Integer communitySupport) {
        this.communitySupport = communitySupport;
    }

    public Integer getSupportType() {
        return supportType;
    }

    public void setSupportType(Integer supportType) {
        this.supportType = supportType;
    }

    public String getCommunityDomain() {
        return communityDomain;
    }

    public void setCommunityDomain(String communityDomain) {
        this.communityDomain = communityDomain;
    }

    public Integer getMovementType() {
        return movementType;
    }

    public void setMovementType(Integer movementType) {
        this.movementType = movementType;
    }

    public AccountCommunityModel getCommunityAccount() {
        return communityAccount;
    }

    public void setCommunityAccount(AccountCommunityModel communityAccount) {
        this.communityAccount = communityAccount;
    }

    public String getCommunityNickName() {
        return communityNickName;
    }

    public void setCommunityNickName(String communityNickName) {
        this.communityNickName = communityNickName;
    }

    public boolean isSubscribedModeration() {
        return subscribedModeration;
    }

    public void setSubscribedModeration(boolean subscribedModeration) {
        this.subscribedModeration = subscribedModeration;
    }

    public String getGcmApplicationPlatform() {
        return gcmApplicationPlatform;
    }

    public void setGcmApplicationPlatform(String gcmApplicationPlatform) {
        this.gcmApplicationPlatform = gcmApplicationPlatform;
    }

    public String getGcmRoutingARN() {
        return gcmRoutingARN;
    }

    public void setGcmRoutingARN(String gcmRoutingARN) {
        this.gcmRoutingARN = gcmRoutingARN;
    }
}