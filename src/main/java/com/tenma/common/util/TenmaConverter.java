/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.util;

import com.tenma.auth.model.AuditModel;
import com.tenma.auth.util.Converter;
import com.vaadin.ui.NativeSelect;

import java.lang.reflect.Field;
import java.text.*;
import java.util.*;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:18 AM
 */
public class TenmaConverter extends Converter {
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

    public static String dateToString(Date dt, String pattern, Locale locale) {
        DateFormat df = new SimpleDateFormat(pattern, locale);
        return df.format(dt);
    }

    public static String dateToString(Date dt, Locale locale) {
        if (dt != null) {
            String pattern = "dd-MMM-yyyy";
            return dateToString(dt, pattern, locale);
        } else return "";
    }

    public static String dateToString(Date dt) {
        if (dt != null) {
            String pattern = "dd-MMM-yyyy";
            return dateToString(dt, pattern);
        } else return "";
    }

    public static String dateToString(Date dt, String pattern) {
        String s = "";
        if (dt != null) {
            DateFormat df = new SimpleDateFormat(pattern);
            s = df.format(dt);
        }
        return s;
    }

    /**
     * Convert String to Date with specified pattern
     *
     * @param stringDate
     * @param pattern
     * @return
     */
    public static Date stringToDate(String stringDate, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean compareDate(String firstDate, String secondDate, String pattern) {
        Date dt1 = stringToDate(firstDate, pattern);
        Date dt2 = stringToDate(secondDate, pattern);

        boolean result = false;
        if (dt1.before(dt2))
            result = true;
        return result;
    }

    public static boolean compareDate(Date firstDate, Date secondDate) {
        boolean result = false;
        if (firstDate.before(secondDate))
            result = true;
        return result;
    }

    public static String getNextSequenceIdWithYear(int currSeq, Locale local) {
        int nextId = currSeq;
        String sI = String.valueOf(nextId);

        sI = leadingZero(sI, 4);
        Calendar cal = Calendar.getInstance(local);
        StringBuffer sNextId = new StringBuffer();
        sNextId.append(cal.get(Calendar.YEAR))
                .append(leadingZero(String.valueOf((cal.get(Calendar.MONTH)) + 1), 2))
                .append(leadingZero(String.valueOf(cal.get(Calendar.DATE)), 2))
                .append(sI).toString();
        return sNextId.toString();
    }

    public static int stringToInteger(String number) {
        int i = 0;

        try {
            NumberFormat format = NumberFormat.getInstance();
            i = format.parse(number).intValue();
        } catch (Exception ex) {
        }

        return i;
    }

    public static HashMap modelToHashmap(AuditModel mdl) {
        HashMap map = new HashMap();
        Class model = mdl.getClass();
        Field[] fieldlist = model.getDeclaredFields();
        try {
            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                fld.setAccessible(true);
                map.put(Constants.PARAMETER_CONSTANTS + fld.getName(), fld.get(mdl));

                System.out.println(fld.getName() + " --- " + map.get(Constants.PARAMETER_CONSTANTS + fld.getName()));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static NativeSelect generateNativeHour(Locale locale) {
        NativeSelect n = new NativeSelect();
        for (int a = 0; a < 24; a++) {
            n.addItem(a);
            String s = "";
            if (a < 10)
                s = "0";
            n.setItemCaption(a, s + a);
        }
        Calendar cal = Calendar.getInstance(locale);
        n.setValue(cal.get(Calendar.HOUR_OF_DAY));
        return n;
    }

    public static NativeSelect generateNativeMinute(Locale locale) {
        NativeSelect n = new NativeSelect();
        for (int a = 0; a < 60; a++) {
            n.addItem(a);
            String s = "";
            if (a < 10)
                s = "0";
            n.setItemCaption(a, s + a);
        }
        Calendar cal = Calendar.getInstance(locale);
        n.setValue(cal.get(Calendar.MINUTE));
        return n;
    }

    public static NativeSelect generateNativeDay(Locale locale) {
        NativeSelect n = new NativeSelect();
        Calendar cal = Calendar.getInstance(locale);
        for (int a = 0; a < cal.getActualMaximum(Calendar.DAY_OF_MONTH); a++) {
            int st = a + 1;
            n.addItem(st);
            n.setItemCaption(st, "" + st);
        }

        n.setValue(cal.get(Calendar.DATE));
        return n;
    }


    public static NativeSelect generateNativeDayOfWeek(Locale locale) {
        NativeSelect n = new NativeSelect();
        Calendar cal = Calendar.getInstance(locale);
        for (int a = 0; a < cal.getActualMaximum(Calendar.DAY_OF_WEEK); a++) {
            n.addItem(a);
            cal.set(Calendar.DAY_OF_WEEK, a);
            n.setItemCaption(a, cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale));
        }

        n.setValue(cal.get(Calendar.DAY_OF_WEEK));
        return n;
    }

    public static NativeSelect generateNativeYear(Locale locale) {
        NativeSelect n = new NativeSelect();
        int lastCentury = 100;
        Calendar cal = Calendar.getInstance(locale);
        int now = cal.get(Calendar.YEAR);
        for (int a = 0; a < lastCentury; a++) {
            int year = now - a;
            n.addItem(year);
            String s = "";
            n.setItemCaption(year, s + year);
        }
        n.setValue(now);
        return n;
    }

    public static NativeSelect generateNativeYear(Locale locale, int toPast, int toFuture) {
        NativeSelect n = new NativeSelect();
        Calendar cal = Calendar.getInstance(locale);
        int now = cal.get(Calendar.YEAR);
        for (int a = 0; a < toFuture; a++) {
            int year = now + a;
            n.addItem(year);
            String s = "";
            n.setItemCaption(year, s + year);
        }
        for (int a = 0; a < toPast; a++) {
            int year = now - a;
            n.addItem(year);
            String s = "";
            n.setItemCaption(year, s + year);
        }
        n.setValue(now);
        return n;
    }

    public static NativeSelect generateNativeYearToFuture(Locale locale) {
        NativeSelect n = new NativeSelect();
        int lastCentury = 100;
        Calendar cal = Calendar.getInstance(locale);
        int now = cal.get(Calendar.YEAR);
        for (int a = 0; a < lastCentury; a++) {
            int year = now + a;
            n.addItem(year);
            String s = "";
            n.setItemCaption(year, s + year);
        }
        n.setValue(now);
        return n;
    }

    public static NativeSelect generateNativeMonth(Locale locale) {
        NativeSelect n = new NativeSelect();
        Calendar cal = Calendar.getInstance(locale);
        int now = cal.get(Calendar.MONTH);
        for (int a = 0; a < cal.getActualMaximum(Calendar.MONTH); a++) {
            n.addItem(a);
            cal.set(Calendar.MONTH, a);
            n.setItemCaption(a, dateToString(cal.getTime(), "MMM"));
        }
        n.setValue(now);
        return n;
    }

    public static String currencyPattern(Double amount, Locale locale) {
        String values = "";
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
        //MAKE IT SINGLETON
        DecimalFormat format = new DecimalFormat("###,###,##0.00", otherSymbols);
        values = format.format(amount).replaceAll("\\.00$", "");
        return values;
    }


    public static String formatSecuredAccountName(String accName) {
        StringBuffer formattedString = new StringBuffer();

        if ((accName != null) && (accName.trim().length() != 0)) {
            accName = accName.trim();
            if (accName.indexOf("@") != -1) {
                String[] arr = accName.split("@");
                int ifirst = arr[0].length() - 2;
                formattedString.append(arr[0].substring(0, 2));
                for (int i = 3; i < arr[0].length(); i++)
                    formattedString.append("*");
                formattedString.append("@").append(arr[1]);
            } else {
                if (accName.length() <= 3)
                    formattedString.append(accName);
                else {
                    int ifirst = accName.length() - 4;
                    for (int i = 0; i < ifirst; i++)
                        formattedString.append("*");
                    formattedString.append(accName.substring(ifirst));
                }
            }
        }
        return formattedString.toString();
    }

    public static boolean isValidPaymentDate(Date paymentDate, Date periodStart, Date periodEnd, Locale local) throws Exception {
        if (paymentDate == null) throw new Exception("You must specify payment date");
        if (periodStart == null) throw new Exception("You must specify start period the payment is valid");

        Calendar calPayment = Calendar.getInstance(local);
        calPayment.setTime(paymentDate);

        Calendar calStartPeriod = Calendar.getInstance(local);

        calStartPeriod.setTime(periodStart);
        removeCalendarHourProperties(calStartPeriod);

        boolean isValid = true;

        if (calStartPeriod.before(calPayment)) {
            if (periodEnd != null) {
                Calendar calEndPeriod = Calendar.getInstance(local);
                calEndPeriod.setTime(periodEnd);
                calEndPeriod.set(Calendar.HOUR, 23);
                calEndPeriod.set(Calendar.MINUTE, 59);
                calEndPeriod.set(Calendar.SECOND, 59);
                if (calEndPeriod.before(calPayment))
                    isValid = false;
            }
        } else isValid = false;
        return isValid;
    }

    public static void removeCalendarHourProperties(Calendar cal) {
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    public static String getShort(String arg, int control) {
        StringBuffer buf = new StringBuffer();
        StringTokenizer token = new StringTokenizer(arg, " ");
        int ctr = 1;
        while (token.hasMoreTokens() && ctr < control) {
            ctr++;
            buf.append(token.nextToken() + " ");
        }
        buf.append("...");
        return buf.toString();
    }

    public static String getMobileNumber(String mobileNumber) {
        mobileNumber = mobileNumber.trim();
        if (!mobileNumber.startsWith("+")) {
            if (mobileNumber.startsWith("0")) {
                mobileNumber = mobileNumber.replaceFirst("0", "+62");
            } else if (mobileNumber.trim().startsWith("62")) {
                mobileNumber = mobileNumber.replaceFirst("62", "+62");
            }

        }
        return mobileNumber;
    }

    public static String[] splitString(String master, String regex) {
        return master.split("\\" + regex);

    }

    public static int countSelectiveChar(String master, String regex) {
        return splitString(master, regex).length;
    }

    public static int findPositionChar(String master, String regex, int pointer) {
        String[] value = splitString(master, regex);
        int position = 0;
        int loop = pointer - 1;
        if (value.length != 0 && value.length >= loop) {
            for (int a = 0; a <= loop; a++) {
                position = position + value[a].length();
            }
        }
        return position;
    }

    public static String getPercentage(double score, double total) {
        float percentage;
        percentage = (float) (score / total);
        NumberFormat defaultFormat = NumberFormat.getPercentInstance();
        defaultFormat.setMinimumFractionDigits(2);
        return defaultFormat.format(percentage);
    }

    public static String getFraction(Integer score, Integer total) {
        float fraction;
        Double scr = Double.valueOf(score);
        Double ttl = Double.valueOf(total);
        fraction = (float) (scr / ttl);
        NumberFormat defaultFormat = NumberFormat.getNumberInstance();
        defaultFormat.setMinimumFractionDigits(2);
        return defaultFormat.format(fraction);
    }

    public static String getRoman(int number) {
        String roman[] = {"M", "XM", "CM", "D", "XD", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int arab[] = {1000, 990, 900, 500, 490, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (number > 0 || arab.length == (i - 1)) {
            while ((number - arab[i]) >= 0) {
                number -= arab[i];
                result.append(roman[i]);
            }
            i++;
        }
        return result.toString();
    }

    public static String getMoneyFormat(double value) {
        DecimalFormat df = new DecimalFormat("#,##0.00;(#,##0.00)");
        return df.format(value);
    }

    public static final String getWeekProperty(int indexCalendarDay) {
        String weekDesc = "";
        switch (indexCalendarDay) {
            case Calendar.SUNDAY:
                weekDesc = "default.sunday";
                break;
            case Calendar.MONDAY:
                weekDesc = "default.monday";
                break;
            case Calendar.TUESDAY:
                weekDesc = "default.tuesday";
                break;
            case Calendar.WEDNESDAY:
                weekDesc = "default.wednesday";
                break;
            case Calendar.THURSDAY:
                weekDesc = "default.thursday";
                break;
            case Calendar.FRIDAY:
                weekDesc = "default.friday";
                break;
            case Calendar.SATURDAY:
                weekDesc = "default.saturday";
                break;
        }
        return weekDesc;
    }

    public static final String getMonthProperty(int indexCalendarMonth) {
        String monthDesc = "";
        switch (indexCalendarMonth) {
            case Calendar.JANUARY:
                monthDesc = "default.january";
                break;
            case Calendar.FEBRUARY:
                monthDesc = "default.february";
                break;
            case Calendar.MARCH:
                monthDesc = "default.march";
                break;
            case Calendar.APRIL:
                monthDesc = "default.april";
                break;
            case Calendar.MAY:
                monthDesc = "default.may";
                break;
            case Calendar.JUNE:
                monthDesc = "default.june";
                break;
            case Calendar.JULY:
                monthDesc = "default.july";
                break;
            case Calendar.AUGUST:
                monthDesc = "default.august";
                break;
            case Calendar.SEPTEMBER:
                monthDesc = "default.september";
                break;
            case Calendar.OCTOBER:
                monthDesc = "default.october";
                break;
            case Calendar.NOVEMBER:
                monthDesc = "default.november";
                break;
            case Calendar.DECEMBER:
                monthDesc = "default.december";
                break;
        }
        return monthDesc;
    }

    public static Boolean isDateFirstBeforeDateSecond(Date dateFirst, Date date2nd) {
        return dateFirst.before(date2nd);
    }

    public static Boolean isDateFirstAfterDateSecond(Date dateFirst, Date date2nd) {
        return dateFirst.after(date2nd);
    }

    public static Boolean isDateEqual(Date dateFirst, Date date2nd) {
        return dateFirst.equals(date2nd);
    }

    public static String replaceInString(String text, String text2Replace, String replaceWith) {
        System.out.println("TenmaConverter.replaceInString " + text);
        System.out.println("TenmaConverter.replaceInString " + text2Replace);
        System.out.println("TenmaConverter.replaceInString " + replaceWith);
        String s = text.replaceAll(text2Replace, replaceWith);
        System.out.println("TenmaConverter.replaceInString " + s);
        return s;
    }

    private static String realDomain(String domainName) {
        String newDomain = domainName;
        if (domainName != null) {
            String dm = domainName.toUpperCase();
            int icoid = dm.lastIndexOf("CO.ID");
            if (icoid != -1)
                newDomain = domainName.substring(0, icoid - 1);
            else {
                icoid = dm.lastIndexOf("COM");
                if (icoid != -1)
                    newDomain = domainName.substring(0, icoid - 1);
            }
        }
        return newDomain;
    }

    /**
     * @param mainDomain    example course
     * @param enteredDomain
     * @return
     */
    public static Boolean isMasterDomain(String mainDomain, String enteredDomain) {
        boolean isMainCourse = false;
        System.out.println("enteredDomain = " + enteredDomain);
        if (enteredDomain != null) {
            enteredDomain = realDomain(enteredDomain);
            mainDomain = realDomain(mainDomain);

            String[] arrCourse = mainDomain.split("\\.");

            String myName = null;
            if (arrCourse.length != 0) {
                if ("www".equalsIgnoreCase(arrCourse[0]))
                    myName = arrCourse[1];
                else
                    myName = arrCourse[0];
            }

            int indx = enteredDomain.indexOf(".");
            if (indx != -1) {
                String[] comArr = enteredDomain.split("\\.");
                if ("WWW".equalsIgnoreCase(comArr[0])) {
                    if (comArr[1].equals(myName))
                        isMainCourse = true;
                    else isMainCourse = false;
                } else {
                    if (myName.equalsIgnoreCase(comArr[0])) {
                        isMainCourse = true;
                    } else {
                        isMainCourse = false;
                    }
                }
            } else {
                isMainCourse = true;
            }
        } else
            isMainCourse = true;

        return isMainCourse;

    }

    public String generateActivationEmailCode() {
        Random random = new Random();
        String code = new String("");
        for (int i = 0; i < 16; i++) {
            code += (char) (random.nextInt(10) + '0');
        }
        return code;
    }

    public String generatePasswordCode() {
        Random random = new Random();
        String code = new String("");
        for (int i = 0; i < 2; i++) {
            code += (char) (random.nextInt(10) + '0');
        }
        return code;
    }
}

