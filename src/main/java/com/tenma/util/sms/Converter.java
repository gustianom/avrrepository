package com.tenma.util.sms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Anom
 * Date: Mar 29, 2012
 * Time: 7:12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Converter {
    public static String DEFAULT_DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";

    public static Date string2Date(String dt, String format) throws ParseException {
        //System.out.println("");
        //System.out.println("");
        //System.out.println("");
        //System.out.println("");
        //System.out.println("Converter.string2Date " + dt + " --- " + format);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dt);
    }

    public static String getDefaultSubscriber(String smsMsg) {
        String def = null;
        if ((smsMsg != null) && (smsMsg.trim().length() != 0)) {
            String[] arr = smsMsg.split(" ");
            if (arr.length != 0)
                def = arr[0];
        }
        return def;
    }

    public static String leadingZero(String str, int maxLength) {
        if (str != null) {
            int slen = maxLength - str.length();
            for (int i = 0; i < slen; i++) {
                str = (" 0" + str).trim();
            }
            //System.out.println("str = " + str);
            return str;
        } else
            return "";
    }

    public static void main(String[] args) {
        String st = "REg2 01-03-2012 12:12:12";

        //System.out.println("Converter. = " + Converter.getDefaultSubscriber("REG2 dd-MM-yyyy hh:mm:ss"));

    }

    public static String date2String(Date dt) {
        SimpleDateFormat dte = new SimpleDateFormat("dd-MMM-yyyy");
        return dte.format(dt);
    }

    public static String replaceInString(String masterString, String oldString, String newString) {
        StringTokenizer tokenizer = new StringTokenizer(masterString, oldString);
        masterString = "";
        int isize = tokenizer.countTokens();
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            i++;
            masterString += tokenizer.nextToken();
            if (i < isize) masterString += newString;
        }
        return masterString;
    }

    public static boolean checkIsNumber(String num) {
        String snum = "0123456789";
        boolean res = true;
        if ((num != null) && (num.trim().length() != 0)) {
            for (int i = 0; i < num.length(); i++) {
                char c = num.charAt(i);
                if (snum.indexOf(String.valueOf(c)) == -1) {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }

    public static int countSMS(String message) {
        int smsCount = 0;
        int smsvalidlength = 160;
        if ((message != null) && (message.length() != 0)) {
            smsCount = message.length() / smsvalidlength;
            int md = message.length() % smsvalidlength;
            if (md != 0) smsCount++;

        }

        return smsCount;
    }


}
