package com.tenma.common.util.s3;

import java.util.Date;

/**
 * Created by ndwijaya on 4/18/2016.
 */
public class TenmaS3DocModel {
    private String docName;
    private long docSize;
    private Date lastModify;
    private boolean isBucket;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public long getDocSize() {
        return docSize;
    }

    public void setDocSize(long docSize) {
        this.docSize = docSize;
    }

    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify(Date lastModify) {
        this.lastModify = lastModify;
    }

    public boolean isBucket() {
        return isBucket;
    }

    public void setIsBucket(boolean isBucket) {
        this.isBucket = isBucket;
    }
}
