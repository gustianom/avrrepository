package com.tenma.model.twitter;

import com.tenma.auth.model.AuditModel;

/**
 * Created with IntelliJ IDEA.
 * User: gustianom
 * Date: 12/20/13
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class TwitterModel extends AuditModel {
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessSecret;

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
