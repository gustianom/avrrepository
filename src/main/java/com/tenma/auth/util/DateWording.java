package com.tenma.auth.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by gustianom on 16/05/15.
 */
public class DateWording extends NumericWording {
    public static String dateToStringWord(Date dt, Locale local) {
        return dateToStringWord(dt, true, local);
    }

    public static String dateToStringWord(Date dt, boolean displayTime, Locale local) {
        if (dt != null) {
            String moment = "";
            Calendar calNOW = Calendar.getInstance(local);
            int yearNow = calNOW.get(Calendar.YEAR);
            int monthNow = calNOW.get(Calendar.MONTH);
            int dayNow = calNOW.get(Calendar.DAY_OF_MONTH);
            int hourNow = calNOW.get(Calendar.HOUR_OF_DAY);
            int minuteNow = calNOW.get(Calendar.MINUTE);
            int secondNow = calNOW.get(Calendar.SECOND);

            Calendar calLocal = Calendar.getInstance(local);
            calLocal.setTime(dt);
            int yearBefore = calLocal.get(Calendar.YEAR);
            int monthBefore = calLocal.get(Calendar.MONTH);
            int dayBefore = calLocal.get(Calendar.DAY_OF_MONTH);
            int hourBefore = calLocal.get(Calendar.HOUR_OF_DAY);
            int minuteBefore = calLocal.get(Calendar.MINUTE);
            int secondBefore = calLocal.get(Calendar.SECOND);

            HashMap map = calculateYear(calLocal, calNOW);
            int yr = (Integer) map.get(Calendar.YEAR);
            int mn = (Integer) map.get(Calendar.MONTH);
            int days = (Integer) map.get(Calendar.DATE);
            int hrs = (Integer) map.get(Calendar.HOUR);
            int mnt = (Integer) map.get(Calendar.MINUTE);
            int scn = (Integer) map.get(Calendar.SECOND);

            if (days < 7) {
                if (yr == 0 && mn <= 1) {
                    if (days == 0) {
                        if (hrs == 0) {
                            if (mnt <= 1)
                                moment = "a moment ago";
                            else if (mnt == 1)
                                moment = new StringBuilder().append(mnt).append(" minute").toString();
                            else
                                moment = new StringBuilder().append(mnt).append(" minutes").toString();
                        } else if (hrs == 1)
                            moment = new StringBuilder().append(hrs).append(" hour").toString();
                        else
                            moment = new StringBuilder().append(hrs).append(" hours").toString();
                    } else {
                        if (days == 1)
                            moment = "yesterday";
                        else
                            moment = new StringBuilder().append(days).append(" days").toString();
                    }
                } else {
                    if (yearBefore != yearNow)
                        moment = dateToString(dt, "dd MMM yyyy", local);
                    else
                        moment = dateToString(dt, "dd MMM", local);
                }
            } else {
                if (yearBefore != yearNow)
                    moment = dateToString(dt, "dd MMM yyyy", local);
                else
                    moment = dateToString(dt, "dd MMM", local);
            }
            if (displayTime) {
                String mt = new StringBuilder().append(moment).append(" @").append(dateToString(dt, "HH:mm", local)).toString();
                moment = mt;
            }

            return moment;
        } else
            return null;
    }


    public static String dateToStringWordRemaining(Date dt, Locale local) {
        if (dt != null) {
            String moment = "";
            Calendar cal = Calendar.getInstance(local);
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayNow = cal.get(Calendar.DAY_OF_MONTH);
            int hourNow = cal.get(Calendar.HOUR_OF_DAY);
            int minuteNow = cal.get(Calendar.MINUTE);
            int secondNow = cal.get(Calendar.SECOND);

            cal.setTime(dt);
            int yearAfter = cal.get(Calendar.YEAR);
            int monthAfter = cal.get(Calendar.MONTH);
            int dayAfter = cal.get(Calendar.DAY_OF_MONTH);
            int hourAfter = cal.get(Calendar.HOUR_OF_DAY);
            int minuteAfter = cal.get(Calendar.MINUTE);
            int secondAfter = cal.get(Calendar.SECOND);

            if (yearAfter == yearNow) {
                if (monthAfter == monthNow) {
                    if (dayAfter == dayNow) {
                        if (hourAfter == hourNow) {
                            if (minuteAfter == minuteNow) {
                                moment = " a moment left";
                            } else {
                                int unit = minuteAfter - minuteNow;
                                if (unit == 1)
                                    moment = "a minute left";
                                else moment = unit + " minutes left";
                            }
                        } else {
                            int unit = hourAfter - hourNow;
                            if (unit == 1)
                                moment = "an hour left";
                            else moment = unit + " hours left";
                        }
                    } else {
                        int unit = dayAfter - dayNow;
                        if (unit == 1)
                            moment = "tomorrow";
                        else moment = unit + " days left";
                    }
                } else {
                    int unit = monthAfter - monthNow;
                    if (unit == 1)
                        moment = "a month left";
                    else moment = unit + " months left";
                }
            } else {
                int unit = yearAfter - yearNow;
                if (unit == 1)
                    moment = "a year left";
                else moment = unit + " years left";
            }
            return moment;
        } else
            return "";

    }

    /**
     * Hashmap contains Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static HashMap calculateYear(Calendar startDate, Calendar endDate) {
        HashMap map = calculateDays(startDate, endDate);

        int year1 = startDate.get(Calendar.YEAR);
        int month1 = startDate.get(Calendar.MONTH);
        int year2 = endDate.get(Calendar.YEAR);
        int month2 = endDate.get(Calendar.MONTH);

        int calcMonth = 0;
        int calcYear = 0;

        boolean isCOontinue = false;
        if (year1 == year2)
            isCOontinue = (month1 < month2);
        else if (year1 < year2)
            isCOontinue = true;

        while (isCOontinue) {
            month1++;
            if (month1 > 11) {
                month1 = 0;
                year1++;
                calcYear++;
            }
            calcMonth++;
            Calendar tmp = Calendar.getInstance();
            tmp.set(year1, month1, 1);


            year1 = tmp.get(Calendar.YEAR);
            month1 = tmp.get(Calendar.MONTH);
            if ((year1 == year2) && (month1 == month2)) {
                isCOontinue = false;
                break;
            }
        }

        map.put(Calendar.YEAR, calcYear);
        map.put(Calendar.MONTH, calcMonth);
        return map;
    }

    /**
     * Hashmap contains Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static HashMap calculateDays(Calendar startDate, Calendar endDate) {
        long milsecs1 = startDate.getTimeInMillis();
        long milsecs2 = endDate.getTimeInMillis();
        long diff = milsecs2 - milsecs1;
        int dsecs = (int) (diff / 1000);
        int dminutes = (int) (diff / (60 * 1000));
        int dhours = (int) (diff / (60 * 60 * 1000));
        int ddays = (int) (diff / (24 * 60 * 60 * 1000));

        HashMap map = new HashMap();
        map.put(Calendar.SECOND, dsecs);
        map.put(Calendar.MINUTE, dminutes);
        map.put(Calendar.HOUR, dhours);
        map.put(Calendar.DATE, ddays);

        return map;
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

    public static Date string2Date(String dt, String pattern) throws Exception {
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        return f.parse(dt);
    }


}
