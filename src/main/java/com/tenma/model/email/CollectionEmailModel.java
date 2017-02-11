package com.tenma.model.email;


import com.tenma.auth.model.AuditModel;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 6/9/12
 * Time: 12:12 PM
 */
public class CollectionEmailModel extends AuditModel {
    private String emailFrom;
    private List<String> emailTo;
    private List<String> emailCC;
    private List<String> emailBCC;
    private String subjectMessage;
    private String bodyMessage;
    private Date deliverDate;
    private List<String> fileAttached;


    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public List<String> getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(List<String> emailTo) {
        this.emailTo = emailTo;
    }

    public List<String> getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(List<String> emailCC) {
        this.emailCC = emailCC;
    }

    public List<String> getEmailBCC() {
        return emailBCC;
    }

    public void setEmailBCC(List<String> emailBCC) {
        this.emailBCC = emailBCC;
    }

    public String getSubjectMessage() {
        return subjectMessage;
    }

    public void setSubjectMessage(String subjectMessage) {
        this.subjectMessage = subjectMessage;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public List<String> getFileAttached() {
        return fileAttached;
    }

    public void setFileAttached(List<String> fileAttached) {
        this.fileAttached = fileAttached;
    }
}
