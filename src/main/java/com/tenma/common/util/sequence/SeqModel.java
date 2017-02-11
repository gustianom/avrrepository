package com.tenma.common.util.sequence;

import java.io.Serializable;

public class SeqModel implements Serializable {
    private String communityId;
    private String seqId;
    private Integer lastSeq;
    private String seqYear;

    public SeqModel() {
        seqYear = "0";
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getSeqId() {
        return this.seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Integer getLastSeq() {
        return this.lastSeq;
    }

    public void setLastSeq(Integer lastSeq) {
        this.lastSeq = lastSeq;
    }

    public String getSeqYear() {
        return this.seqYear;
    }

    public void setSeqYear(String seqYear) {
        this.seqYear = seqYear;
    }

}