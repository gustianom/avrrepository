package com.tenma.auth.util;

import java.awt.*;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 8/15/12
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Converter extends DateWording {
    /**
     * 0 > create
     * 1 > read
     * 2 > update
     * 3 > delete
     * 4 > print
     *
     * @param authCode
     * @return
     */
    public static boolean[] parseAuthorizationCode(int authCode) {
        String s = decimalToBinary(authCode);
        if (s.length() > 5) s = s.substring(s.length() - 5, s.length());
        s = leadingZero(s, 5);
        boolean[] bol = new boolean[5];
        for (int i = s.length() - 1; i >= 0; i--) {
            int ind = Integer.parseInt(String.valueOf(s.charAt(i)));
            bol[i] = ind == 1 ? true : false;
        }
        return bol;
    }

    public static String decimalToBinary(int decimal) {
        if (decimal == 0) {
            return "0";
        }
        String binary = "";
        while (decimal > 0) {
            int rem = decimal % 2;
            binary = rem + binary;
            decimal = decimal / 2;
        }
        return binary;
    }

    public static int binaryToDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }

    public static String formatFileSize(Integer size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String generateRandomPassword(boolean isNumericOnly, int passwordLength) {
        int PASSWORD_LENGTH = 4;
        if (passwordLength > PASSWORD_LENGTH) PASSWORD_LENGTH = passwordLength;
        Random RANDOM = new SecureRandom();
        String letters = "";
        if (isNumericOnly) letters = "1234567890";
        else letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String pw = "";
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        return pw;
    }

    /**
     * getList of command
     *
     * @param str
     * @param delimeterStart
     * @param delimeterEnd
     * @return
     */
    public static List getCommand(String str, String delimeterStart, String delimeterEnd) {

        List arr = new ArrayList();
        try {
            while (true) {
                int idx1 = str.indexOf(delimeterStart); //start
                str = str.substring(idx1 + delimeterStart.length());
                idx1 = str.indexOf(delimeterEnd); //end
                String cmd = str.substring(0, idx1);
                if (cmd.trim().length() != 0)
                    arr.add(cmd.trim());
                str = str.substring(idx1 + 1);
            }
        } catch (Exception e) {
        }
        return arr;

    }

    /**
     * Format given double number to 0000.00# format.
     * ex : 1.0E78000000.0 -----> 10000000000.00
     *
     * @param number
     * @return String formatted number
     */
    public static String formatNumber(double number) {
        NumberFormat nf = new DecimalFormat("#0.0000#");
        return nf.format(number);
    }

    /**
     * round number value<br>
     * example : <br>
     * round(12345.871,2) ---------> 12345.87
     *
     * @param number
     * @param X
     * @return double
     */
    public static double roundNumber(double number, int X) {
        double mresult = number * Math.pow(10, X);
        mresult = Math.round(mresult);
        mresult = mresult / Math.pow(10, X);
        return mresult;
    }

    public static boolean isNumber(String stringNumber) {
        String tm = "0123456789";
        boolean ret = true;
        if (stringNumber != null) {
            for (int i = 0; i < stringNumber.length(); i++) {
                if (tm.indexOf(stringNumber.charAt(i)) == -1) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    public static boolean isCharacter(String stringChar) {
        String rule = " \"%";
        boolean ret = true;
        if (stringChar != null) {
            for (int i = 0; i < stringChar.length(); i++) {
                for (int c = 0; c < rule.length(); c++) {
                    if (stringChar.charAt(i) == rule.charAt(c)) {
                        ret = false;
                        break;
                    }

                }
            }
        }
        return ret;
    }

    public static boolean isNumberWithDot(String stringNumber) {
        String tm = "0123456789.";
        boolean ret = true;
        if (stringNumber != null) {
            for (int i = 0; i < stringNumber.length(); i++) {
                if (tm.indexOf(stringNumber.charAt(i)) == -1) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    public static boolean isEmail(String email) {
        boolean ret = true;
        if (email != null) {
            String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
                    "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
                    "(\\.[A-Za-z]{2,})$";
            Pattern p = Pattern.compile(EMAIL_PATTERN);
            Matcher m = p.matcher(email);
            boolean b = m.matches();
            if (b == true) {
                ret = true;
            } else {
                ret = false;
            }
        }
        return ret;
    }

    public static String decimal2hex(int decimal) {
        String digits = "0123456789ABCDEF";
        if (decimal == 0) return "0";
        String hex = "";
        while (decimal > 0) {
            int digit = decimal % 16;
            hex = digits.charAt(digit) + hex;
            decimal = decimal / 16;
        }
        return hex;
    }

    public static Color getRgbColor(String lineColor) {
        Color c = new Color(0, 0, 0);
        if (lineColor != null) {
            lineColor = lineColor.trim();
            if (lineColor.startsWith("#")) lineColor = lineColor.substring(1, lineColor.length());
            if (lineColor.length() == 6) {
                int R = hex2decimal(lineColor.substring(0, 2));
                int G = hex2decimal(lineColor.substring(2, 4));
                int B = hex2decimal(lineColor.substring(4, 6));
                c = new Color(R, G, B);
            }
        }
        return c;
    }


    public static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    public static String formatNumber(String number, int maxPractionDigits) {
        if (number == null || "".equals(number))
            return "";

        String practionDigit = "#,##0";
        maxPractionDigits = Math.abs(maxPractionDigits);
        if (maxPractionDigits == 0) {
            practionDigit = "#,##";
        } else {
            practionDigit += ".";
            for (int i = 0; i < maxPractionDigits; i++) {
                practionDigit += "0";
            }
        }
        practionDigit += "#";

        DecimalFormat df = new DecimalFormat(practionDigit);
        df.setMaximumFractionDigits(maxPractionDigits);
        return df.format(stringToDouble(number));
    }

    /**
     * Format given string number to #,##0.00# format.
     * ex : 100000 -----> 100,000.00
     *
     * @param number
     * @return String formatted number
     */
    public static String formatNumber(String number) {
        DecimalFormat df = new DecimalFormat("#,##0.00#");
        df.setMaximumFractionDigits(2);
        if (number == null || "".equals(number))
            return "";

        return df.format(stringToDouble(number));
    }

    public static double stringToDouble(String number) {
        double d = 0D;
        if (number == null || "".equals(number)) return d;
        try {
            NumberFormat format = NumberFormat.getInstance();
            d = format.parse(number).doubleValue();
        } catch (Exception ex) {
        }

        return d;
    }

    public static String number2String(double numb, int maxPractionDigits) {
        StringBuilder pattern = new StringBuilder()
                .append("###");

        if (maxPractionDigits < 0) maxPractionDigits = 0;

        for (int i = 0; i < maxPractionDigits; i++)
            pattern.append(i == 0 ? ".#" : "#");

        DecimalFormat format = new DecimalFormat(pattern.toString());
        String s = format.format(numb);
        return s;

    }

    public static String number2String(double numb) {
        return number2String(numb, 0);
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


    public static String getNextSequenceId(int currSeq) {
        int nextId = currSeq;
        String sI = String.valueOf(nextId);

        sI = leadingZero(sI, 2);
        Calendar cal = Calendar.getInstance();
        StringBuilder sNextId = new StringBuilder();
        sNextId.append(cal.get(Calendar.YEAR))
                .append(leadingZero(String.valueOf((cal.get(Calendar.MONTH)) + 1), 2))
                .append(leadingZero(String.valueOf(cal.get(Calendar.DATE)), 2))
                .append(leadingZero(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)), 2))
                .append(leadingZero(String.valueOf(cal.get(Calendar.MINUTE)), 2))
                .append(leadingZero(String.valueOf(cal.get(Calendar.SECOND)), 2))
                .append(sI).toString();
        return sNextId.toString();
    }

    public static String generateVariableName(String fieldName, String delimeter) {
        List ls = new ArrayList();

        if (fieldName.indexOf(delimeter) == -1)
            ls.add(fieldName);
        else {
            try {
                while (true) {
                    int idx1 = fieldName.indexOf(delimeter); //start
                    String cmd = fieldName.substring(0, idx1);
                    ls.add(cmd);
                    fieldName = fieldName.substring(idx1 + delimeter.length());
                    if (fieldName.indexOf(delimeter) == -1) ls.add(fieldName);
                }
            } catch (Exception e) {
            }
        }

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < ls.size(); i++) {
            String cmd = (String) ls.get(i);
            if (i == 0) {
                buf.append(String.valueOf(cmd.charAt(0)).toUpperCase()).append(cmd.substring(1).toLowerCase());
            } else
                buf.append(String.valueOf(cmd.charAt(0)).toUpperCase()).append(cmd.substring(1).toLowerCase());
        }
        return buf.toString();
    }


    public static long toLocalTime(long utcTime, TimeZone to) {
        return convertTime(utcTime, TimeZone.getTimeZone("UTC"), to);
    }

    public static long toLocalTime(Date utcDateTime, TimeZone to) {
        return convertTime(utcDateTime.getTime(), TimeZone.getTimeZone("UTC"), to);
    }

    public static long toLocalTime(Date utcDateTime, Locale locale) {
        Calendar cal = Calendar.getInstance(locale);
        return convertTime(utcDateTime.getTime(), TimeZone.getTimeZone("UTC"), cal.getTimeZone());
    }

    public static Date getUTCDate(Date localDate, Locale locale) {
        Calendar cal = Calendar.getInstance(locale);
        cal.setTime(localDate);

        long dateUTC = toUTC(localDate.getTime(), cal.getTimeZone());
        cal.setTimeInMillis(dateUTC);
        return cal.getTime();
    }

    public static Date getUTCDate(Date localDate, TimeZone localTimeZone) {
        long dateUTC = toUTC(localDate.getTime(), localTimeZone);

        Calendar cal = Calendar.getInstance(localTimeZone);
        cal.setTimeInMillis(dateUTC);
        return cal.getTime();
    }

    private static long toUTC(long time, TimeZone localTimeZone) {
        return convertTime(time, localTimeZone, TimeZone.getTimeZone("UTC"));
    }

    public static long convertTime(long time, TimeZone from, TimeZone to) {
        return time + getTimeZoneOffset(time, from, to);
    }

    private static long getTimeZoneOffset(long time, TimeZone from, TimeZone to) {
        int fromOffset = from.getOffset(time);
        int toOffset = to.getOffset(time);
        int diff = 0;

        if (fromOffset >= 0) {
            if (toOffset > 0) {
                toOffset = -1 * toOffset;
            } else {
                toOffset = Math.abs(toOffset);
            }
            diff = (fromOffset + toOffset) * -1;
        } else {
            if (toOffset <= 0) {
                toOffset = -1 * Math.abs(toOffset);
            }
            diff = (Math.abs(fromOffset) + toOffset);
        }
        return diff;
    }

    public String generatePasswordCode() {
        Random random = new Random();
        String code = new String("");
        for (int i = 0; i < 2; i++) {
            code += (char) (random.nextInt(10) + '0');
        }
        return code;
    }

//    public static void main(String[] args) {
//        Calendar cal = Calendar.getInstance();
//        cal.set(2015, Calendar.MAY, 9, 14, 40);
//
//
//        String s = Converter.dateToStringWord(cal.getTime(), true, Locale.getDefault());
//
//        System.out.println("Converter.main "+cal.getTime());
//        System.out.println("s = " + s);
//
//
//    }
}
