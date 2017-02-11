package com.tenma.util.email;

/**
 * Created by gustianom on 2/4/14.
 */

import com.tenma.common.util.Params;
import com.tenma.mail.bean.undeliveredmail.UndeliveredMailHelper;
import com.tenma.model.email.CollectionEmailModel;
import com.tenma.model.email.EmailModel;
import com.tenma.model.email.UndeliveredMailModel;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 6/2/12
 * Time: 3:14 PM
 */
public class EmailService {
    public static final int TO = 4002;
    public static final int BCC = 4003;
    public static final int CC = 4004;

    public static final String EMAIL_DELIMITER = ";";


    /**
     * undelivered email akan disimpan pada database untuk bisa dikirim ulang
     *
     * @param model
     * @param fullFilePath
     * @return
     * @throws Exception
     */
    public static boolean sendOneMail(final CollectionEmailModel model, final String fullFilePath) throws Exception {
        return sendOneMail(model, fullFilePath, null, null, true);
    }

    /**
     * undelivered email akan disimpan pada database untuk bisa dikirim ulang
     *
     * @param model
     * @param fullFilePath
     * @return
     * @throws Exception
     */
    public static boolean sendOneMail(final CollectionEmailModel model, String replayToEmailAddress, String replyToName, final String fullFilePath) throws Exception {
        return sendOneMail(model, fullFilePath, replayToEmailAddress, replyToName, true);
    }

    /**
     * Keterangan parameter <p/>jika parameter isNew = true, maka undelivered mail akan disimpan pada database untuk bisa dikirim ulang, jika false maka undelivered mail tidak akan disimpan pada database
     *
     * @param model
     * @param fullFilePath
     * @param isNewMail
     * @return
     * @throws Exception
     */
    public static boolean sendOneMail(final CollectionEmailModel model, String replayToEmailAddress, String replyToName, final String fullFilePath, boolean isNewMail) throws Exception {
        boolean result = false;
        Params params = Params.getInstance(Locale.ENGLISH);

        String smtpClient = params.getProperty("smtp.address");
        String smtpPortClient = params.getProperty("smtp.port");

        String sourceClientEmail = params.getProperty("notificationEmail");
        String sourceClientPasswordEmail = params.getProperty("notificationEmailPassword");
        String sourceClientTitle = params.getProperty("notificationEmailTitle");

        String hostGmail = "smtp.gmail.share";
        String portGmail = "587"; //was 587


        String auth = "false";
        String starttls = "false";


        if (sourceClientEmail == null) {
            sourceClientEmail = "tenmasys.info@gmail.share";
            sourceClientPasswordEmail = "eojzzazqvefsnapq";
            sourceClientTitle = "Tenmasys Information";
        }

        if (sourceClientEmail.toLowerCase().endsWith("gmail.share")) {
            auth = "true";
            starttls = "true";
            smtpClient = hostGmail;
            smtpPortClient = portGmail;
        }

        String fromMail = sourceClientEmail;
        String fromEmailPasswd = sourceClientPasswordEmail;
        String fromEmailTitle = sourceClientTitle;

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttls);
        properties.put("mail.smtp.host", smtpClient);
        properties.put("mail.smtp.port", smtpPortClient);


        Session session = null;
//        if (sourceClientEmail.toLowerCase().endsWith("gmail.share")) {
        session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromMail, fromEmailPasswd);
                    }
                });
//        } else
//            session = Session.getDefaultInstance(properties);


        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(fromMail, fromEmailTitle, "UTF-8"));
        if (replayToEmailAddress != null && replayToEmailAddress.trim().length() != 0) {
            mimeMessage.setReplyTo(new javax.mail.Address[]
                    {
                            new javax.mail.internet.InternetAddress(replayToEmailAddress)
                    });
            fromEmailTitle = replyToName;
            sourceClientTitle = replyToName;
        }
        StringBuffer destinationTO = new StringBuffer();
        StringBuffer destinationCC = new StringBuffer();
        StringBuffer destinationBCC = new StringBuffer();

        if (model.getEmailTo() != null && model.getEmailTo().size() != 0) {
            for (String email : model.getEmailTo()) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                destinationTO.append(destinationTO.length() != 0 ? EMAIL_DELIMITER : "").append(email);
            }
        }
        if (model.getEmailBCC() != null && model.getEmailBCC().size() != 0) {
            for (String email : model.getEmailBCC()) {
                mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(email));
                destinationBCC.append(destinationBCC.length() != 0 ? EMAIL_DELIMITER : "").append(email);
            }
        }
        if (model.getEmailCC() != null && model.getEmailCC().size() != 0) {
            for (String email : model.getEmailCC()) {
                mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
                destinationCC.append(destinationCC.length() != 0 ? EMAIL_DELIMITER : "").append(email);
            }
        }
        mimeMessage.setSubject(model.getSubjectMessage());

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(model.getBodyMessage(), "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        String filesAttached = null;
        if ((model.getFileAttached() != null) && (model.getFileAttached().size() != 0)) {
//            for (String fl : model.getFileAttached())
            filesAttached = model.getFileAttached().get(0);
            addAtachments(model.getFileAttached().get(0), fullFilePath, multipart);
        }

        mimeMessage.setContent(multipart);
        session.setDebug(false);
        try {
            Transport.send(mimeMessage);
            result = true;
        } catch (Exception e) {
            if (isNewMail) {
                UndeliveredMailModel um = new UndeliveredMailModel();
                um.setEmailBCC(destinationBCC.toString());
                um.setEmailCC(destinationCC.toString());
                um.setEmailTo(destinationTO.toString());
                um.setSubjectMessage(mimeMessage.getSubject());
                um.setBodyMessage(model.getBodyMessage());

                String fullPath = fullFilePath;
                if (filesAttached != null && filesAttached.trim().length() != 0) {
                    if (fullPath == null) fullPath = "";
                    fullPath = fullPath.trim();
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    StringBuffer fl = new StringBuffer().append(fullPath).append(fullPath.trim().length() == 0 ? "" : File.separator).append(filesAttached);
                    um.setFileAttached(fl.toString());
                }

                saveUndeliveredEmail(um);
            }
            result = false;
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return result;
    }

    /**
     * recipient Type: TO, BCC, or CC
     *
     * @param model
     * @param recipientType
     * @throws Exception
     */
    public static boolean sendMail(final EmailModel model, int recipientType, final String fullFilePath) throws Exception {
        return sendMail(model, null, null, recipientType, fullFilePath);
    }

    /**
     * recipient Type: TO, BCC, or CC
     *
     * @param model
     * @param recipientType
     * @throws Exception
     */

    public static boolean sendMail(final EmailModel model, final String replayToEmailAddress, final String replyToName, int recipientType, final String fullFilePath) throws Exception {

        Message.RecipientType recType = Message.RecipientType.TO;
        switch (recipientType) {
            case EmailService.TO:
                recType = Message.RecipientType.TO;
                break;
            case EmailService.BCC:
                recType = Message.RecipientType.BCC;
                break;
            case EmailService.CC:
                recType = Message.RecipientType.CC;
                break;
        }

        boolean rst = true;
        final Message.RecipientType finalRecType = recType;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doSendEmail(model.getEmailFrom(), model.getEmailTo(), model.getSubjectMessage(), replayToEmailAddress, replyToName, model.getBodyMessage(), finalRecType, model.getFileAttached(), fullFilePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        //System.out.println("EmailService.sendMail");
        return rst;
    }

    /*
    * recipient Type: TO, BCC, or CC
    *
            * @param model
    * @param recipientType
    * @throws Exception
    */
    public static boolean sendMail(final EmailModel model, int recipientType) throws Exception {
        return sendMail(model, null, null, recipientType);
    }

    /*
    * recipient Type: TO, BCC, or CC
    *
            * @param model
    * @param recipientType
    * @throws Exception
    */
    public static boolean sendMail(final EmailModel model, final String replayToEmailAddress, final String replyToName, int recipientType) throws Exception {
        Message.RecipientType recType = Message.RecipientType.TO;
        switch (recipientType) {
            case EmailService.TO:
                recType = Message.RecipientType.TO;
                break;
            case EmailService.BCC:
                recType = Message.RecipientType.BCC;
                break;
            case EmailService.CC:
                recType = Message.RecipientType.CC;
                break;
        }

        boolean rst = true;

        final Message.RecipientType finalRecType = recType;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doSendEmail(model.getEmailFrom(), model.getEmailTo(), model.getSubjectMessage(), replayToEmailAddress, replyToName, model.getBodyMessage(), finalRecType, null, null);
                } catch (Exception e) {
                    System.out.println("EmailService.run Error " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();


        //System.out.println("EmailService.sendMail");
        return rst;
    }

    private static synchronized boolean doSendEmail(String source1, String destination, String subject, String replayToEmailAddress, String replyToName, String emailMessage, Message.RecipientType recipientType, String filesAttached, String fullFilePath) throws Exception {
        boolean result = false;
        Params params = Params.getInstance(Locale.ENGLISH);

        String smtpClient = params.getProperty("smtp.address");
        String smtpPortClient = params.getProperty("smtp.port");

        String sourceClientEmail = params.getProperty("notificationEmail");
        String sourceClientPasswordEmail = params.getProperty("notificationEmailPassword");
        String sourceClientTitle = params.getProperty("notificationEmailTitle");

        String hostGmail = "smtp.gmail.share";
        String portGmail = "587"; //was 587


        String auth = "false";
        String starttls = "false";


        if (sourceClientEmail == null) {
            sourceClientEmail = "tenmasys.info@gmail.share";
            sourceClientPasswordEmail = "eojzzazqvefsnapq";
            sourceClientTitle = "Tenmasys Information";
        }

        if (sourceClientEmail.toLowerCase().endsWith("gmail.share")) {
            auth = "true";
            starttls = "true";
            smtpClient = hostGmail;
            smtpPortClient = portGmail;
        }

        final String fromMail = sourceClientEmail;
        final String fromEmailPasswd = sourceClientPasswordEmail;
        String fromEmailTitle = sourceClientTitle;

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttls);
        properties.put("mail.smtp.host", smtpClient);
        properties.put("mail.smtp.port", smtpPortClient);


        Session session = null;
//        if (source.toLowerCase().endsWith("gmail.share")) {
        session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromMail, fromEmailPasswd);
                    }
                });
//        } else
//            session = Session.getDefaultInstance(properties);


        Message mimeMessage = new MimeMessage(session);
        if (replayToEmailAddress != null && replayToEmailAddress.trim().length() != 0) {
            mimeMessage.setReplyTo(new javax.mail.Address[]
                    {
                            new javax.mail.internet.InternetAddress(replayToEmailAddress)
                    });
            fromEmailTitle = replyToName;
            sourceClientTitle = replyToName;
            mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(replayToEmailAddress));
        }
        mimeMessage.setFrom(new InternetAddress(fromMail, fromEmailTitle, "UTF-8"));
        mimeMessage.addRecipient(recipientType, new InternetAddress(destination));
        mimeMessage.setSubject(subject);

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailMessage, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        if ((filesAttached != null) && (filesAttached.trim().length() != 0))
            addAtachments(filesAttached, fullFilePath, multipart);

        mimeMessage.setContent(multipart);
        session.setDebug(false);
        try {
            Transport.send(mimeMessage);
            result = true;
        } catch (Exception e) {
            UndeliveredMailModel um = new UndeliveredMailModel();
            um.setBodyMessage(emailMessage);
            um.setSubjectMessage(subject);

            if (Message.RecipientType.TO.equals(recipientType))
                um.setEmailTo(destination);
            else if (Message.RecipientType.CC.equals(recipientType))
                um.setEmailCC(destination);
            else if (Message.RecipientType.BCC.equals(recipientType))
                um.setEmailBCC(destination);

            if ((filesAttached != null) && (filesAttached.trim().length() != 0)) {
                if (fullFilePath == null) fullFilePath = "";
                fullFilePath = fullFilePath.trim();
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                StringBuffer fl = new StringBuffer().append(fullFilePath).append(fullFilePath.trim().length() == 0 ? "" : File.separator).append(filesAttached);
                um.setFileAttached(fl.toString());
            }

            saveUndeliveredEmail(um);
            e.printStackTrace();
            result = false;
            throw new Exception(e.getMessage());
        }
        return result;
    }

    private static void saveUndeliveredEmail(UndeliveredMailModel m) {
        UndeliveredMailHelper undeliveredMailHelper = new UndeliveredMailHelper();
        try {
            undeliveredMailHelper.insertNewUndeliveredMail(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addAtachments(String filename, String fullPath, Multipart multipart) throws MessagingException, AddressException {
        if (fullPath == null) fullPath = "";
        fullPath = fullPath.trim();
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        File fl = new File(new StringBuffer().append(fullPath).append(fullPath.trim().length() == 0 ? "" : File.separator).append(filename).toString());
        DataSource source = new FileDataSource(fl);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(filename);
        //add the attachment
        multipart.addBodyPart(attachmentBodyPart);
    }


/*
    static class SMTPAuthenticator extends javax.mail.Authenticator {
        String username;
        String password;
        private SMTPAuthenticator(String authenticationUser, String authenticationPassword) {
            username = authenticationUser;
            password = authenticationPassword;
        }
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
*/


    public static void main(String[] args) throws Exception {
        String from = "%2B839399393";
        int inx = from.indexOf("%2B");
        if (inx != -1)
            from = new StringBuffer().append(from.substring(inx + 3, from.length())).toString();

        //System.out.println("EmailService.main " + from);

        CollectionEmailModel m = new CollectionEmailModel();
        m.setEmailFrom("tenma@gmail.share");
        m.setBodyMessage("test");
        m.setSubjectMessage("test");

        List<String> lsTo = new ArrayList<String>();
        lsTo.add("gusti.anom@tenmasys.share");
        lsTo.add("gusti.anom@gmail.share");
        lsTo.add("ndwijaya@gmail.share");
        lsTo.add("sigit.hadiwibowo@tenmasys.share");

        List<String> lsCC = new ArrayList<String>();
        lsCC.add("andrian@tenmasys.share");
        lsCC.add("jody.riztana@tenmasys.share");

        m.setEmailTo(lsTo);
        m.setEmailCC(lsCC);

        sendOneMail(m, null);

    }

}