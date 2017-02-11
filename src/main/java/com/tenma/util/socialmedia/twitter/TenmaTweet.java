package com.tenma.util.socialmedia.twitter;


/**
 * Created with IntelliJ IDEA.
 * User: gustianom
 * Date: 12/20/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */


import com.tenma.model.twitter.TwitterModel;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gustianom
 * Date: 12/19/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TenmaTweet {

    public static void broadCast(final String message, final TwitterModel model) {
        if (message != null && message.length() != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        doBroadCast(message, model);
                    } catch (Exception e) {
                        //System.out.println("TenmaTweet.run "+e.getMessage());
                    }
                }
            }).start();
        }
    }

    private static synchronized void doBroadCast(String message, TwitterModel model) {
        List ls = coolTweet(message);
        if (ls != null)
            for (int i = 0; i < ls.size(); i++) {
                try {

                    String consumerKey = "YKLkA7rasHHF4RVj11iNg";
                    String consumerSecret = "gw6qLAtwNZkrxgpkKQ8YYu2n8hbRyyz9suumraIhw";
                    String accessToken = "2253092892-ppM0trgcIfNTCflYejXwEJvv9jiilQ6a3V9Vdeg";
                    String accessSecret = "GvJIHE5W3fIWbmoTOR8Y0x1qYHjICAfUBfZn0V30guzDX";

//                    String consumerKey = model.getConsumerKey();
//                    String consumerSecret = model.getConsumerSecret();
//                    String accessToken = model.getAccessToken();
//                    String accessSecret = model.getAccessSecret();

                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setDebugEnabled(true)
                            .setOAuthConsumerKey(consumerKey)
                            .setOAuthConsumerSecret(consumerSecret)
                            .setOAuthAccessToken(accessToken)
                            .setOAuthAccessTokenSecret(accessSecret);

                    TwitterFactory factory = new TwitterFactory(cb.build());
                    Twitter twitter = factory.getInstance();

                    //System.out.println(twitter.getScreenName());
                    int ln = 140;
                    if (message.length() > ln)
                        message = message.substring(0, ln);
                    Status status = twitter.updateStatus(message.substring(0));
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

    }

    private static List coolTweet(String message) {
        int maxchar = 140;
        List ls = new ArrayList();
        if (message != null && message.length() != 0) {
            if (message.length() <= maxchar)
                ls.add(message);
            else {
                String msg1;
                int maxLn = maxchar;
                while (message.length() >= 0) {
                    if (message.length() <= maxLn) maxLn = message.length();
                    else maxLn = maxchar;

                    msg1 = message.substring(0, maxLn);
                    char x1 = msg1.charAt(msg1.length() - 1);
                    if (x1 != ' ') {
                        int indx1 = msg1.lastIndexOf(" ");
                        if (indx1 != -1) {
                            msg1 = message.substring(0, indx1);
                            msg1 = msg1.trim();
                        }
                    }
                    message = message.substring(msg1.length(), message.length());
                    message = message.trim();
                    ls.add(msg1);
                    if (message.length() <= maxchar) {
                        ls.add(message);
                        break;
                    }
                }
            }
        }
        return ls;
    }

    public static void main(String[] args) {
        TenmaTweet.broadCast("6a7acb1304d75896553cf43600e8ddd8", null);

    }

}
