package com.tenma.util.sms.services;

import com.tenma.model.sms.SMSModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Anom
 * Date: Mar 6, 2012
 * Time: 3:56:36 PM
 * To change this template use File | Settings | File Templates.
 */
class SMSProviderUtil {


    public static synchronized String sendSms(SMSModel model) throws Exception {
        //System.out.println("SendSMSUtil.sendSms" + model.getSmsTo());
        URL url = new URL("http://tenma.zenziva.net/api/sendsms.php?userkey=t54549&passkey=exbc999&tipe=reguler&nohp=" + model.getSmsTo() + "&pesan=" + URLEncoder.encode(model.getSmsMessage(), "UTF-8"));
        return doSendSMS(url, model);
    }

    public static synchronized String checkTenmaProviderBalance() throws Exception {
        URL url = new URL("http://tenma.zenziva.net/api/credit.php?userkey=t54549&passkey=exbc999");
        return doSendSMS(url, null);
    }

    public static synchronized String getSMSInbound() throws Exception {
//        URL url = new URL("http://tenma.zenziva.net/api/inboxgetall.php?userkey=t54549&passkey=exbc999");
        URL url = new URL("http://tenma.zenziva.net/api/readsms.php?userkey=t54549&passkey=exbc999&status=unread");
        return doSendSMS(url, null);
    }

    public static synchronized String deleteInboxSMS(int smsId) throws Exception {
        URL url = new URL("http://tenma.zenziva.net/api//inboxdelete.php?userkey=t54549&passkey=exbc999&id=" + smsId);
        return doSendSMS(url, null);
    }

    private static String doSendSMS(URL url, SMSModel model) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


        // Set properties of the connection
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("Content-Type", "text/plain");
        urlConnection.setRequestProperty("charset", "UTF-8");
        if (model != null)
            urlConnection.setRequestProperty("Content-Length", String.valueOf(model.getSmsMessage().length()));

        // Form the POST parameters

        String message = "";
        if (model != null) message = model.getSmsMessage();
        OutputStream outputStream = urlConnection.getOutputStream();
        outputStream.write(message.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();

        // Retrieve the output
        int responseCode = urlConnection.getResponseCode();

        //System.out.println("");
        //System.out.println("");
        //System.out.println("");
        //System.out.println("SendSMSUtil.sendSms1 RESPONSE CODE " + responseCode);
        //System.out.println("urlConnection.getResponseMessage() = " + urlConnection.getResponseMessage());
        InputStream inputStream;

        inputStream = urlConnection.getInputStream();

        String string;
        StringBuilder outputBuilder = new StringBuilder();
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (null != (string = reader.readLine())) {
                outputBuilder.append(string).append('\n');
            }
        }
        //System.out.println("SMSProviderUtil response " + outputBuilder);
        return outputBuilder.toString();
    }

    public static void main(String[] args) {
        SMSModel mdl = new SMSModel();
        mdl.setSmsFrom("SYSTEM");
        //mdl.setSmsTo("+628567282248");
        mdl.setSmsTo("+6281574402591");
        //mdl.setSmsTo("+6281585504243");
        //mdl.setSmsTo("+6285694483218");
        mdl.setSmsMessage("In my specific case and with your method");
        mdl.setDeliveryDate(new Date());
        try {
            for (int i = 0; i < 200; i++)
                SMSProviderUtil.deleteInboxSMS(i);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

