
package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User: derry.irmansyah
 * Date: 2/25/13
 * Time: 4:54 PM
 */
public class JournalHeaderModel extends AuditModel {
    private String journalHeaderNumber;
    private Date trxDate;
    @NotNull
    private String trxDesc;
    private Integer status;
    private String communityId;
    private Date entryDate;
    private String ref;
    private String templateId;
    private String templateName;
    private String trxCurr;
    private String memberName;
    private Date postDate;
    private Double baseAmount;
    private Date fromDate;
    private Date toDate;
    private Date startDate;
    private String customData;

    public String getJournalHeaderNumber() {
        return journalHeaderNumber;
    }

    public void setJournalHeaderNumber(String journalHeaderNumber) {
        this.journalHeaderNumber = journalHeaderNumber;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public String getTrxDesc() {
        return trxDesc;
    }

    public void setTrxDesc(String trxDesc) {
        this.trxDesc = trxDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getTrxCurr() {
        return trxCurr;
    }

    public void setTrxCurr(String trxCurr) {
        this.trxCurr = trxCurr;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }
}
