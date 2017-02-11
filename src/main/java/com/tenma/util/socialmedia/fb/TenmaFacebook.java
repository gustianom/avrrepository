package com.tenma.util.socialmedia.fb;

/**
 * Created with IntelliJ IDEA.
 * User: gustianom
 * Date: 12/20/13
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import static java.lang.System.out;

/**
 * Examples of RestFB's Graph API functionality.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class TenmaFacebook {
    private final FacebookClient facebookClient;
    private final String pageId;

    public TenmaFacebook(String applicationId, String applicationSecreet, String pageId) {
        this.pageId = pageId;
        FacebookClient.AccessToken token = new DefaultFacebookClient().obtainAppAccessToken(applicationId, applicationSecreet);
        String s = token.getAccessToken();
        facebookClient = new DefaultFacebookClient(s);
    }

    public TenmaFacebook(String accessToken, String pageId) {
        facebookClient = new DefaultFacebookClient(accessToken);
        this.pageId = pageId;
    }

    public static void main(String[] args) {
        String accessToken = "CAACEdEose0cBAHo44nbwbqzSXcyOaEqedYCXmZAigFr5eZATSfOZAsnQRMvGhUSDvZAbSXMbcAzG0PfifVOQEK1qZCj8Sj0ZCvScDW8l3mwy8HRJRYtrA97ONE21tJk0C9weErTuMiIQJKK3fohHUGgWiiAqsTZChhzK1ZBQuU4Rx1wxKujEG6riqtQYWY1KFc0ZD";
        String pageId = "1401436426768201";
        TenmaFacebook tenmaFacebook = new TenmaFacebook(accessToken, pageId);
        tenmaFacebook.publishMessage("http://otomotif.kompas.share/read/2013/12/19/10919/.Jazz.SUV.Sentuhan.Mugen", "#mobil Jazz Baru");
    }

    public final String publishMessage(String url, String message) {
        FacebookType publishMessageResponse =
                facebookClient.publish(pageId + "/links", FacebookType.class,
                        Parameter.with("link", url),
                        Parameter.with("message", message));

        out.println("Published message ID: " + publishMessageResponse.getId());
        return publishMessageResponse.getId();
    }

    public final String publishMessage(String message) {
        FacebookType publishMessageResponse =
                facebookClient.publish(pageId + "/feed", FacebookType.class,
                        Parameter.with("message", message));

        out.println("Published message ID: " + publishMessageResponse.getId());
        return publishMessageResponse.getId();
    }

    public final String publishEvent(String eventName, Date startTime, Date endTime) {
        FacebookType publishEventResponse =
                facebookClient.publish(pageId + "/events", FacebookType.class, Parameter.with("name", eventName),
                        Parameter.with("start_time", startTime), Parameter.with("end_time", endTime));

        out.println("Published event ID: " + publishEventResponse.getId());
        return publishEventResponse.getId();
    }

    public final String publishPhoto(String photoName, String message, File file) {
        try {
            out.println("* Binary file publishing *");

            FileInputStream fileInputStream = new FileInputStream(file);
            FacebookType publishPhotoResponse =
                    facebookClient.publish(pageId + "/photos", FacebookType.class,
                            BinaryAttachment.with(photoName, fileInputStream),
                            Parameter.with("message", message));
            return publishPhotoResponse.getId();
        } catch (Exception e) {

        }
        return null;
    }
}