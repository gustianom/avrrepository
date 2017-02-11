package com.tenma.model.email;


import com.tenma.auth.model.AuditModel;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 6/9/12
 * Time: 12:12 PM
 */
public class EmailModel extends AuditModel {
    private String emailFrom;
    private String emailTo, emailCC, emailBCC;
    private String subjectMessage;
    private String bodyMessage;
    private Date deliverDate;
    private String fileAttached;


    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }

    public String getEmailBCC() {
        return emailBCC;
    }

    public void setEmailBCC(String emailBCC) {
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

    public String getFileAttached() {
        return fileAttached;
    }

    public void setFileAttached(String fileAttached) {
        this.fileAttached = fileAttached;
    }
}
