package com.tenma.common.util.exrate;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 10/17/13
 * Time: 3:14 PM
 */
public class TenmaExchangeRate implements Runnable {
    private static TenmaExchangeRate instance;
    private HashMap cacheRate;
    private boolean refresh;
    private String[] majorCurrency = {"USD"};   // dev mode
    //    private String[] majorCurrency = {"USD", "SGD", "EUR", "JPY", "AUD", "GBP", "CNY"};
    private long defbuffTime = 1000 * 60 * 120;  // 2 hours
    private long buffTime;
    private boolean init = true;


    private TenmaExchangeRate() {
        refresh = true;
        cacheRate = new HashMap();
        new Thread(this).start();  // define cache of exchange rate USD-SGD-EUR-JPY-AUD-GBP
    }

    public static void main(String[] args) {
        TenmaExchangeRate tm = new TenmaExchangeRate();
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(tm.getRate("USD", "IDR"));
    }

    public static TenmaExchangeRate getTenmaRate() {
        if (instance == null) instance = new TenmaExchangeRate();
        return instance;
    }

    public double getRate(String fromCurrency, String toCurrency, long cacheTime) {
        buffTime = cacheTime;
        return getTheRate(fromCurrency, toCurrency);
    }

    public double getRate(String fromCurrency, String toCurrency) {
        buffTime = defbuffTime;
        return getTheRate(fromCurrency, toCurrency);
    }

    private double getTheRate(String fromCurrency, String toCurrency) {
        long start = Calendar.getInstance().getTimeInMillis();
        double rate = 0;
        boolean reload = true;
        String key = fromCurrency.toUpperCase().trim() + toCurrency.toUpperCase().trim();
        RateModel model = (RateModel) cacheRate.get(key);
        if (model != null) {
            long now = Calendar.getInstance().getTimeInMillis();
            long time = now - model.time;
            System.out.println("cache time is.... " + time + " ms");
            if (time < (buffTime)) {
                reload = false;  // 1 hours chace
                if (time > defbuffTime * 0.9) {
                    // if cachetime is
                    refresh = true;
                    new Thread(this).start();
                    System.out.println("TenmaExchangeRate.getTheRate");
                }
            }
        }
        if (reload) {
            try {
                RateModel m = lookupYahooRate(fromCurrency, toCurrency);
                cacheRate.put(m.from + m.to, m);
                rate = m.rate;
                refresh = true;
                new Thread(this).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            rate = model.rate;
            System.out.println("Using cache....");
        }
        long finish = Calendar.getInstance().getTimeInMillis();
        long time = finish - start;
        DecimalFormat df = new DecimalFormat("#,##0.##;(#,##0.00)");

        System.out.println("TenmaExchangeRate.getRate getRate time = " + df.format(time) + " ms");
        return rate;
    }


    private RateModel lookupYahooRate(String fromCurrency, String toCurrency) throws Exception {
        if (fromCurrency.equalsIgnoreCase("IDR")) {
            RateModel m = new RateModel();
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://quote.yahoo.share/d/quotes.csv?s=" + toCurrency + fromCurrency + "=X&f=l1&e=.csv");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpGet, responseHandler);
            httpclient.getConnectionManager().shutdown();
            double rate = Float.parseFloat(responseBody);
            m.from = fromCurrency.toUpperCase().trim();
            m.to = toCurrency.toUpperCase().trim();
            m.rate = (1 / rate);
            m.time = Calendar.getInstance().getTimeInMillis();
            return m;
        } else {
            RateModel m = new RateModel();
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://quote.yahoo.share/d/quotes.csv?s=" + fromCurrency + toCurrency + "=X&f=l1&e=.csv");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpGet, responseHandler);
            httpclient.getConnectionManager().shutdown();
            double rate = Float.parseFloat(responseBody);
            m.from = fromCurrency.toUpperCase().trim();
            m.to = toCurrency.toUpperCase().trim();
            m.rate = rate;
            m.time = Calendar.getInstance().getTimeInMillis();
            return m;
        }
    }

    @Override
    public void run() {
        String toCurrency = "IDR";
        refresh = true;
        int index = 0;
        DecimalFormat df = new DecimalFormat("#,##0.###;(#,##0.###)");
        System.out.println("loading exchange rate for cache...");
        if (init) {
            boolean first = true;
            while (refresh) {
                if (!first) {
                    try {
                        long start = Calendar.getInstance().getTimeInMillis();
                        System.out.print((index + 1) + ". loading exchange rate " + majorCurrency[index] + " vs " + toCurrency + "...");
                        RateModel m = lookupYahooRate(majorCurrency[index], toCurrency);
                        long finish = Calendar.getInstance().getTimeInMillis();
                        long time = finish - start;
                        System.out.println("[" + df.format(m.rate) + "] in " + df.format(time) + " ms");
                        cacheRate.put(m.from + m.to, m);
                        index++;
                        if (index == majorCurrency.length) refresh = false;
                    } catch (Exception e) {
                        System.out.println("loading rate " + majorCurrency[index] + "is failed");
                        refresh = false;
                    } finally {
                    }
                } else {
                    first = false;
                    System.out.println("Skip first thread cycle !init");
                }
            }
            init = false;
        } else {
            boolean first = true;
            while (refresh) {
                if (!first) {
                    try {
                        long start = Calendar.getInstance().getTimeInMillis();
                        RateModel cm = (RateModel) cacheRate.get(majorCurrency[index] + toCurrency);
                        long ctime = start - cm.time;
                        if (ctime > (0.9 * defbuffTime)) {
                            RateModel m = lookupYahooRate(majorCurrency[index], toCurrency);
                            long finish = Calendar.getInstance().getTimeInMillis();
                            long time = finish - start;
                            System.out.print((index + 1) + ". loading exchange rate " + majorCurrency[index] + " vs " + toCurrency + "...");
                            System.out.println("[" + df.format(m.rate) + "] in " + df.format(time) + " ms");
                            cacheRate.put(m.from + m.to, m);
                        } else {
                            System.out.print(index + 1 + ". exchange rate " + majorCurrency[index] + " vs " + toCurrency + "...is skipped");
                        }
                        index++;
                        if (index == majorCurrency.length) refresh = false;
                    } catch (Exception e) {
                        System.out.println("loading rate " + majorCurrency[index] + "is failed");
                        refresh = false;
                    } finally {
                    }
                } else {
                    first = false;
                    System.out.println("Skip first thread cycle");
                }
            }
        }
        System.out.println(cacheRate.size() + " cache is buffered");
    }

    class RateModel {
        String from;
        String to;
        double rate;
        long time;
    }
}