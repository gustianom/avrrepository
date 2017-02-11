/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.audittrail;

import com.tenma.model.common.AuditTrailModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:49 PM
 */
public class TenmaLogBuffer implements Serializable {
    private HashMap buffer;

    public TenmaLogBuffer() {
        buffer = new HashMap();
    }

    public HashMap getLogBuffer() {
        HashMap temp = (HashMap) buffer.clone();
        Collection collection = temp.values();
        Iterator iter = collection.iterator();
        while (iter.hasNext()) {
            AuditTrailModel key = (AuditTrailModel) iter.next();
            buffer.remove(key.getAuditId());
        }
        return temp;
    }

    public int bufferSize() {
        return buffer.size();
    }

    public synchronized void addNewLog(AuditTrailModel model) {
        buffer.put(model.getAuditId(), model);
    }


}

