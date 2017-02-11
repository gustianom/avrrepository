package com.tenma.common.util;

import com.tenma.auth.model.AuditModel;
import com.tenma.auth.model.CommunityModel;
import com.tenma.common.bean.communitysosmed.CommunitySosmedHelper;
import com.tenma.common.bean.emailtype.EmailTypeHelper;
import com.tenma.common.util.web.ServerUtility;
import com.tenma.model.common.CommunitySosmedModel;
import com.tenma.model.common.EmailTypeModel;
import com.tenma.model.email.EmailModel;
import com.tenma.model.sms.SMSModel;
import com.tenma.model.twitter.TwitterModel;
import com.tenma.util.SMS_Constants;
import com.tenma.util.email.EmailService;
import com.vaadin.ui.Notification;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by gustianom on 3/14/14.
 */
public class NotificationTools {
    private final Params params;
    private String communityId;
    //
    private List listSocialMedia;

    private Locale locale;
    private String userId;
    private String clientIPAddress;

    public NotificationTools(Locale locale, String communityId, String userId, String clientIPAddress) {
        this.locale = locale;
        this.userId = userId;
        this.clientIPAddress = clientIPAddress;
        this.communityId = communityId;
        params = Params.getInstance(locale);
    }

    public static String preparingEmailBody(String message, HashMap data) {
        String s = message;
        String delimeterStart = "#@@";
        String delimeterEnd = "@@#";


        List ls = TenmaConverter.getCommand(s, delimeterStart, delimeterEnd);
        for (int i = 0; i < ls.size(); i++) {
            String cd = (String) ls.get(i);
            String cmd = new StringBuffer().append(delimeterStart).append(cd).append(delimeterEnd).toString();
            try {
                Object o = data.get(cd);
                String vl = "";
                if (o instanceof Date)
                    vl = TenmaConverter.dateToString((Date) o);
                else if (o instanceof Double) {
                    Double db = (Double) o;
                    vl = TenmaConverter.formatNumber(db.toString(), 2);
                } else if (o instanceof Integer) {
                    Integer db = (Integer) o;
                    vl = TenmaConverter.formatNumber(db.toString(), 2);
                } else if (o instanceof Long) {
                    Long db = (Long) o;
                    vl = TenmaConverter.formatNumber(db.toString(), 2);
                } else if (o instanceof Float) {
                    Float db = (Float) o;
                    vl = TenmaConverter.formatNumber(db.toString(), 2);
                } else
                    vl = (String) o;


                cmd = cmd.trim();
                System.out.println("TenmaPanel.preparingEmailBody -> " + cd + " = " + vl);
                s = s.replaceAll(cmd, vl);
            } catch (Exception e) {
                System.out.println("--------------------> TenmaPanel.preparingEmailBody " + e.getMessage());
                s = s.replaceAll(cmd, "-");
            }
        }
        return s;
    }

    // sms
    public final static boolean doSendUserNotificationSMS(CommunityModel cmModel, HashMap data, String creatorId, String smsTo, String smsType) throws Exception {
        boolean rest = false;
//        SmsTypeModel tmp = new SmsTypeModel();
//        tmp.setSmsType(smsType);
//        tmp.setCommunityStructure(cmModel.getCommunityStructure());
//        if (!((tmp.getSmsType() != null) && (tmp.getSmsType().trim().length() != 0)))
//            tmp.setSmsType(Constants.SMS_TYPE.ALL_REGISTRATION.getValue());
//        SmsTypeHelper typeHelper = new SmsTypeHelper();
//
//
//        SMSModel smsModel = new SMSModel();
//
//        tmp = typeHelper.getSmsTypeDetail(tmp);
//        if (tmp != null) {
//            String s = preparingEmailBody(tmp.getSmsDesc(), data);
//            smsModel.setSmsMessage(s);
//            smsModel.setSmsTo(smsTo);
//            smsModel.setSmsFrom("SYSTEM");
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println("smsModel = " + smsModel.getSmsMessage());
//            SMS_Constants.SMS_RESPONSE_CODE code = ServerUtility.sendSms(creatorId, cmModel.getCreatedFrom(), smsModel, cmModel.getCommunityId(), SMS_Constants.SMS_SOURCE_TYPE.NORMAL);
//            rest = code == SMS_Constants.SMS_RESPONSE_CODE.DELIVERED;
//        }
        return rest;
    }

    public final void processNotifyByFB(String announcementDescription) {
        CommunitySosmedHelper sosmedHelper = new CommunitySosmedHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, communityId);
        map.put("sosmedType", 7);

        if (listSocialMedia == null)
            listSocialMedia = sosmedHelper.getListRenderer(map, false);

        if (listSocialMedia != null) {
            for (int i = 0; i < listSocialMedia.size(); i++) {
                CommunitySosmedModel m = (CommunitySosmedModel) listSocialMedia.get(i);
                if (7 == m.getSosmedType())
                    try {
//                        Facebook facebook = new FacebookTemplate(m.getSosmedAccessToken());
//                        Facebook facebook = new FacebookTemplate("CAAUIN1b01awBAOevR9UqsWfBrb28RXAcsczLc5MdcVeV3fuWR9O0UyDsy9gZBMZCkfg0NjjcEcKCZCL2gSahNFeaXz08rkBpXXEhfQsN0ciJqNL6tdNdneUyYqyK4QwjZA01zRGmZCWSEDjsYepcgrHCWbqIBfonCZCu7BgiylJKKX6NyA7UcgZBY7J4LfZBFXIFbhVTULQnVQZDZD");
//                        Facebook facebook = new FacebookTemplate("CAACEdEose0cBAGyjlvI2JgFeJenHXFUvHS2jBCMi0cYorMzWZCWX91yaZApygdxccE6ZCkfBeGB2yG9miWaTDoEseib9zoZCHyNAbjMfZAZBcZBeSvjfUtUWwbaWs1YoZAeMMgqt2Ax8vYdImAYm9ukcuIMAoq4ZBzXs5UfXuL9M9JPZAVqPt3p4bqXXUMd3z5ucQZD");
                        Facebook facebook = new FacebookTemplate(m.getSosmedAccessToken());
//                        FacebookConnectionFactory connectionFactory =
//                                new FacebookConnectionFactory("clientId", "clientSecret");
//                        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
//                        OAuth2Parameters params = new OAuth2Parameters();
//                        params.setRedirectUri("https://my-callback-url");
//                        String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, params);
//                        response.sendRedirect(authorizeUrl);


// upon receiving the callback from the provider:
//                        AccessGrant accessGrant = oauthOperations.exchangeForAccess(m.getSosmedAppid(), "https://my-callback-url", null);
//                        Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
//                        Facebook facebook = connection != null ? connection.getApi() : new FacebookTemplate();

                        facebook.feedOperations().updateStatus(announcementDescription);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private String getFacebookToken(String appId, String appSecret) {
        String token = new String();
        try {
            System.out.println("\n\n\n\n\n\n TOKENNYAAAA 1\n\n\n\n");
            String myAppId = "336265999844875";
            String myAppSecret = "57a5aaa291a26a93c6b15878e0bbf55b";
            String redirectURI = "http://localhost:8080/";
            System.out.println("\n\n\n\n\n\n TOKENNYAAAA 2\n\n\n\n");
//                String uri = "https://graph.facebook.com/oauth/access_token?client_id=" +
//                        myAppId +
//                        "&client_secret=" + myAppSecret +
//                        "&grant_type=client_credentials" +
////                        "&redirect_uri=" + redirectURI +
//                        "&scope=user_about_me";
            String uri = "https://graph.facebook.share/100007319731645/";
            System.out.println("\n\n\n\n\n\n TOKENNYAAAA 3\n\n\n\n");
            URL newURL = new URL(uri);
            System.out.println("\n\n\n\n\n\n TOKENNYAAAA 4\n\n\n\n");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = newURL.openStream();
            System.out.println("\n\n\n\n\n\n TOKENNYAAAA 5\n\n\n\n");
            int r;
            while ((r = is.read()) != -1) {
                baos.write(r);
            }
            String response = new String(baos.toByteArray());
            is.close();
            baos.close();
            System.out.println("\n\n\n\n\n\n TOKENNYAAAA 6\n\n\n\n");
            String TOKEN_INDEX = "accesss_token=";
            token = response.substring(TOKEN_INDEX.length() - 1);
            System.out.println("\n\n\n\n\n\n TOKENNYAAAA " + token + "\n\n\n\n");
            //API Call using the application access token
            String graph = "https://graph.facebook.share/v2.2/" + myAppId +
                    "?access_token=" + token;

            URL graphURL = new URL(graph);
            HttpURLConnection myWebClient = (HttpURLConnection) graphURL.openConnection();

            String responseMessage = myWebClient.getResponseMessage();

            if (myWebClient.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println(myWebClient.getResponseCode() + " " + responseMessage);
            } else {
                System.out.println(responseMessage);
            }
            myWebClient.disconnect();

        } catch (Exception e) {
            System.err.println(e);
        }

        return token;
    }

    public final void processNotifyByTwitter(String announcementDescription) {
        CommunitySosmedHelper sosmedHelper = new CommunitySosmedHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, communityId);
        map.put("sosmedType", Constants.SOCIAL_MEDIA_TYPE.TWITTER.getValue());
        if (listSocialMedia == null)
            listSocialMedia = sosmedHelper.getListRenderer(map, false);


        if (listSocialMedia != null) {
            for (int i = 0; i < listSocialMedia.size(); i++) {
                CommunitySosmedModel m = (CommunitySosmedModel) listSocialMedia.get(i);
                if (Constants.SOCIAL_MEDIA_TYPE.TWITTER.getValue() == m.getSosmedType())
                    try {
                        TwitterModel model = new TwitterModel();
                        model.setConsumerKey(m.getSosmedConsumerKey());
                        model.setConsumerSecret(m.getSosmedConsumerSecreet());
                        model.setAccessToken(m.getSosmedAccessToken());
                        model.setAccessSecret(m.getSosmedAccessSecreet());
//                        TenmaTweet.broadCast(announcementDescription, model);
//                        System.out.println("\n\n\n\n\n\n\n Spring Social \n\n\n\n\n");
//                        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
                        Twitter twitter = new TwitterTemplate(model.getConsumerKey(), model.getConsumerSecret(), model.getAccessToken(), model.getAccessSecret());
//                        System.out.println("\n\n\n\n\n\n\nmodel getConsumerKey= " + model.getConsumerKey());
//                        System.out.println("model getConsumerSecret= " + model.getConsumerSecret());
//                        System.out.println("model getAccessToken= " + model.getAccessToken());
//                        System.out.println("model getAccessSecret= " + model.getAccessSecret());
//                        Twitter twitter = new TwitterTemplate("YKLkA7rasHHF4RVj11iNg", "gw6qLAtwNZkrxgpkKQ8YYu2n8hbRyyz9suumraIhw", "2253092892-ppM0trgcIfNTCflYejXwEJvv9jiilQ6a3V9Vdeg", "GvJIHE5W3fIWbmoTOR8Y0x1qYHjICAfUBfZn0V30guzDX");
//                        System.out.println("USER NAME TWITTER ====> "+twitter.userOperations().getScreenName());
                        twitter.timelineOperations().updateStatus(announcementDescription);
                    } catch (Exception e) {
                        System.err.println(e);
                        System.out.println("ERROORRR " + e.getMessage());
                    }
            }
        }
    }

    public final EmailModel generateEmail2Send(String body, String subject, String from, String to, String fileAttached, int emailType) {
        EmailModel tm = new EmailModel();
        tm.setEmailFrom(from);
        if (EmailService.TO == emailType)
            tm.setEmailTo(to);
        else if (EmailService.BCC == emailType)
            tm.setEmailBCC(to);
        else if (EmailService.CC == emailType)
            tm.setEmailCC(to);
        else
            tm.setEmailTo(to);

        tm.setSubjectMessage(subject);
        tm.setBodyMessage(body);
        tm.setFileAttached(fileAttached);
        setAuditTrail(tm);
        return tm;
    }

    private void setAuditTrail(AuditModel model) {
        model.setCreatedBy(userId);
        model.setUpdatedBy(userId);

        model.setCreatedFrom(clientIPAddress);
        model.setUpdatedFrom(clientIPAddress);
    }

    public final SMS_Constants.SMS_RESPONSE_CODE doSendSMSNotification(SMSModel model, SMS_Constants.SMS_SOURCE_TYPE sourceType) throws Exception {
        String creatorId = userId;
        return ServerUtility.sendSms(creatorId, clientIPAddress, model, communityId, sourceType);
    }

    public final void doSendEmail(List<EmailModel> listEmail) throws Exception {

        if (listEmail != null) {
            Notification.show(params.getMessage("please.wait.mail.send"), Notification.Type.HUMANIZED_MESSAGE);
            for (int i = 0; i < listEmail.size(); i++) {
                EmailModel emailModel = (EmailModel) listEmail.get(i);
                doSendEmail(emailModel);
            }
            Notification.show(params.getMessage("email.sendSuccessfully"), Notification.Type.HUMANIZED_MESSAGE);
        }
    }

    public final void doSendEmail(EmailModel emailModel) throws Exception {
        doSendEmail(emailModel, null, null);
    }

    public final void doSendEmail(EmailModel emailModel, String replayToEmailAddress, String replyToName) throws Exception {
        String fullFilePath = params.getProperty("uploadFileAppServerDirectory");

        if ((emailModel.getEmailTo() != null) && (emailModel.getEmailTo().trim().length() != 0))
            EmailService.sendMail(emailModel, replayToEmailAddress, replyToName, EmailService.TO, fullFilePath);
        if ((emailModel.getEmailBCC() != null) && (emailModel.getEmailBCC().trim().length() != 0))
            EmailService.sendMail(emailModel, replayToEmailAddress, replyToName, EmailService.BCC, fullFilePath);
        if ((emailModel.getEmailCC() != null) && (emailModel.getEmailCC().trim().length() != 0))
            EmailService.sendMail(emailModel, replayToEmailAddress, replyToName, EmailService.CC, fullFilePath);
    }

    /**
     * @param cl          is a Model
     * @param classObject is Object of Class
     * @param emailTo     is a Email Recipient
     * @param emailType   as Email Type
     * @throws Exception
     */
    public final void doSendUserNotificationEmail(Class<?> cl, Object classObject, String emailTo, Constants.EMAIL_TYPE emailType) throws Exception {
        doSendUserNotificationEmail(cl, classObject, emailTo, null, null, emailType);
    }

    public final void doSendUserNotificationEmail(Class<?> cl, Object classObject, String emailTo, String replayToEmailAddress, String replyToName, Constants.EMAIL_TYPE emailType) throws Exception {
        HashMap data = preparingEmailData(cl, classObject);
        doSendUserNotificationEmail(data, emailTo, replayToEmailAddress, replyToName, emailType);
    }

    /**
     * @param data      is a HashMap data which contains information about fieldName and Value that required on Email Content
     * @param emailTo   is a Email Recipient
     * @param emailType as Email Type
     * @throws Exception
     */
    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, Constants.EMAIL_TYPE emailType) throws Exception {
        return doSendUserNotificationEmail(data, emailTo, emailType == null ? null : emailType.getValue());
    }

    /**
     * @param data      is a HashMap data which contains information about fieldName and Value that required on Email Content
     * @param emailTo   is a Email Recipient
     * @param emailType as Email Type
     * @throws Exception
     */
    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, String replayToEmailAddress, String replyToName, Constants.EMAIL_TYPE emailType) throws Exception {
        return doSendUserNotificationEmail(data, emailTo, replayToEmailAddress, replyToName, emailType == null ? null : emailType.getValue());
    }

    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, String emailType) throws Exception {
        return doSendUserNotificationEmail(data, emailTo, null, null, emailType, null, null);
    }

    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, Constants.EMAIL_TYPE emailType, String fileAttached, String fileLocationDir) throws Exception {
        return doSendUserNotificationEmail(data, emailTo, null, null, emailType == null ? null : emailType.getValue(), fileAttached, fileLocationDir);
    }

    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, String replayToEmailAddress, String replyToName, Constants.EMAIL_TYPE emailType, String fileAttached, String fileLocationDir) throws Exception {
        return doSendUserNotificationEmail(data, emailTo, replayToEmailAddress, replyToName, emailType == null ? null : emailType.getValue(), fileAttached, fileLocationDir);
    }

    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, String emailType, String fileAttached, String fileLocationDir) throws Exception {
        return doSendUserNotificationEmail(data, emailTo, null, null, emailType, fileAttached, fileLocationDir);
    }

    public final boolean doSendUserNotificationEmail(HashMap data, String emailTo, String replayToEmailAddress, String replyToName, String emailType, String fileAttached, String fileLocationDir) throws Exception {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("NotificationTools.doSendUserNotificationEmail " + emailTo);
        boolean rest = false;
        if (emailType == null)
            emailType = Constants.EMAIL_TYPE.ALL_REGISTRATION.getValue();

        EmailTypeModel tmp = new EmailTypeModel();
        tmp.setEmailType(emailType);
        EmailTypeHelper typeHelper = new EmailTypeHelper();


        EmailModel email = new EmailModel();

        tmp = typeHelper.getEmailTypeDetail(tmp);
        if (tmp != null) {
            String s = preparingEmailBody(tmp.getEmailDetail(), data);
            email.setBodyMessage(s);
            s = preparingEmailBody(tmp.getEmailSubject(), data);
            email.setSubjectMessage(s);
            email.setEmailTo(emailTo);
            if ((fileAttached != null) && (fileAttached.trim().length() != 0))
                email.setFileAttached(fileAttached);

            if ((fileLocationDir != null) && (fileLocationDir.trim().length() != 0))
                rest = EmailService.sendMail(email, replayToEmailAddress, replyToName, EmailService.TO, fileLocationDir);
            else
                rest = EmailService.sendMail(email, replayToEmailAddress, replyToName, EmailService.TO);
        }
        return rest;
    }

    /**
     * get class fields
     *
     * @param cl
     * @return
     */
    public final List<Field> getInheritedFields(Class<?> cl) {
        List<Field> result = new ArrayList<Field>();

        Class<?> i = cl;
        while (i != null && i != Object.class) {
            for (Field field : i.getDeclaredFields()) {
                if (!field.isSynthetic()) {
                    result.add(field);
                }
            }
            i = i.getSuperclass();
        }

        return result;
    }

    /**
     * Preparing Email data with HashMap
     *
     * @param cl
     * @param classObject
     * @return
     * @throws Exception
     */
    public final HashMap preparingEmailData(Class<?> cl, Object classObject) throws Exception {
        HashMap data = new HashMap();
        List<Field> ls = getInheritedFields(cl);

        for (int i = 0; i < ls.size(); i++) {
            Field fl = ls.get(i);
            String fieldName = fl.getName();

            boolean bool = fl.getType().toString().equals("boolean");
            Method t = cl.getMethod(getGetterMethod(fieldName, bool), new Class[]{});
            Object o = t.invoke(classObject);
            data.put(fieldName, o);
        }
        return data;
    }

    private String getGetterMethod(String name, boolean isBoolean) {
        StringBuffer buf = new StringBuffer();
        buf.append(isBoolean ? "is" : "get")
                .append(String.valueOf(name.charAt(0)).toUpperCase())
                .append(name.substring(1));
        return buf.toString();
    }
}
