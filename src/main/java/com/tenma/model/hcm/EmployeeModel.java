package com.tenma.model.hcm;


import com.tenma.auth.model.CommunityMemberModel;

import java.util.Date;

public class EmployeeModel extends CommunityMemberModel {
    private Integer employeeId;
    private Integer employeeType, parentPosId, posId, bankId, orgId;

    private String orgName;
    private String employeeNumber;
    private String bankAccountNumber;
    private String personalEmail;
    private String posName;
    private Date joinDate;

    private Integer employeeStatus = 0;// community identify , active , scorse, etc. default is 0, active [customized for community]
    private Integer employmentStatus = 0; // for multiple status of employee , eq direct, seconded, etc. Default is 0 for direct
    private Integer employmentType; // permanent/ kontrak / outsource
    private Integer employmentCategory; // teacher/ agent/ seconded
    private String taxStatus;
    private String profile;
    private Integer recordStatus;

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Integer employeeType) {
        this.employeeType = employeeType;
    }

    public Integer getParentPosId() {
        return parentPosId;
    }

    public void setParentPosId(Integer parentPosId) {
        this.parentPosId = parentPosId;
    }

    public Integer getPosId() {
        return posId;
    }

    public void setPosId(Integer posId) {
        this.posId = posId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(Integer employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public Integer getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(Integer employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Integer getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(Integer employmentType) {
        this.employmentType = employmentType;
    }

    public Integer getEmploymentCategory() {
        return employmentCategory;
    }

    public void setEmploymentCategory(Integer employmentCategory) {
        this.employmentCategory = employmentCategory;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "getEmailAddress='" + getEmailAddress() + '\'' +
                "getMobilePhone='" + getMobilePhone() + '\'' +
                "getEmployeeNumber='" + getEmployeeNumber() + '\'' +
                '}';
    }
}


