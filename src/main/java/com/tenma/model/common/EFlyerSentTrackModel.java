package com.tenma.model.common;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 4/6/2015
 * Time    : 11:46 AM
 * Project : udw
 * Package : share.share.tenma.model.common
 */
public class EFlyerSentTrackModel extends EflyerSentModel {
    private Integer trackCounter;
    private String memberName;
    private Integer emailId;

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getTrackCounter() {
        return trackCounter;
    }

    public void setTrackCounter(Integer trackCounter) {
        this.trackCounter = trackCounter;
    }
}
