package com.tenma.common.util.error;

import com.tenma.common.TA;
import com.tenma.common.util.Constants;
import com.tenma.common.util.NotificationTools;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.email.EmailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Created by ndwijaya on 8/18/15.
 */
public class TenmaStackTraceManager implements Runnable {
    private static int DEF_SLEEP_TIME = 60000; // default 1 minute;
    private static int SLEEP_TIME = 60000; // default 1 minute;
    private static int SEND_EMAIL_EVERY = 60; // in minutes = every hour
    private static int SEND_EARLY_IF_ERROR = 10; // if stack is more than 10 send before 1 hours
    private static TenmaStackTraceManager instance;
    private static Vector<LogExceptionModel> mapLog;
    private static String footerHTMLTABLE = "" +
            "            </tbody>\n" +
            "        </table>\n" +
            "    </main>\n" +
            "</div>\n" +
            "\n" +
            "</body>\n" +
            "</html>";
    private static String footerHTMLTABLESUMMARY = "" +
            "            </tbody>\n" +
            "        </table>\n";
    private static String headerTableHTMLSUMMARY_COMMUNITY = "" +
            " <table class=\"pure-table pure-table-horizontal\">\n" +
            "            <thead>\n" +
            "            <tr>\n" +
            "                <th>Community</th>\n" +
            "                <th>Total</th>\n" +
            "            </tr>\n" +
            "            </thead>\n" +
            "            <tbody>\n" +
            "            ";
    private static String headerTableHTMLSUMMARY_IP = "" +
            " <table class=\"pure-table pure-table-horizontal\">\n" +
            "            <thead>\n" +
            "            <tr>\n" +
            "                <th>IP</th>\n" +
            "                <th>Total</th>\n" +
            "            </tr>\n" +
            "            </thead>\n" +
            "            <tbody>\n" +
            "            ";
    private static String headerTableHTMLSUMMARY_USER = "" +
            " <table class=\"pure-table pure-table-horizontal\">\n" +
            "            <thead>\n" +
            "            <tr>\n" +
            "                <th>User</th>\n" +
            "                <th>Total</th>\n" +
            "            </tr>\n" +
            "            </thead>\n" +
            "            <tbody>\n" +
            "            ";
    private static String headerTableHTMLSUMMARY_DAY = "" +
            " <table class=\"pure-table pure-table-horizontal\">\n" +
            "            <thead>\n" +
            "            <tr>\n" +
            "                <th>DAY OF YEAR</th>\n" +
            "                <th>Total</th>\n" +
            "            </tr>\n" +
            "            </thead>\n" +
            "            <tbody>\n" +
            "            ";
    private static String headerTableHTML = "" +
            " <table class=\"pure-table pure-table-horizontal\">\n" +
            "            <thead>\n" +
            "            <tr>\n" +
            "                <th>When</th>\n" +
//            "                <th>Exception</th>\n" +
            "                <th>Community</th>\n" +
            "                <th>User</th>\n" +
            "                <th>Ip Addrs</th>\n" +
            "                <th>Module</th>\n" +
            "            </tr>\n" +
            "            </thead>\n" +
            "            <tbody>\n" +
            "            ";
    private static String headerHTML =
            " <html> " +
                    " <head> " +
                    " <style> " +
                    " table a:link { " +
                    " color: #666;" +
                    " font-weight:bold;" +
                    " text-decoration:none;}" +
                    " table a:visited{ " +
                    " color: #999999; " +
                    " font-weight:bold; " +
                    " text-decoration:none; " +
                    " } " +
                    " table a:active, " +
                    " table a:hover{ " +
                    " color: #bd5a35; " +
                    " text-decoration:underline; }" +
                    " table{" +
                    " font-family:Arial,Helvetica,sans-serif;" +
                    " color:#666;" +
                    " font-size:12px;" +
                    " text-shadow:1px 1px 0px #fff;" +
            /*background:#eaebec;*/
                    " margin:20px;" +
                    " border:#ccc 1px solid;" +

                    " -moz-border-radius:3px;" +
                    " -webkit-border-radius:3px;" +
                    " border-radius:3px;" +

                    " -moz-box-shadow:0 1px 2px #d1d1d1;" +
                    " -webkit-box-shadow:0 1px 2px #d1d1d1;" +
                    " box-shadow:0 1px 2px #d1d1d1;} " +
                    " table th{" +
                    " padding:21px 25px 22px 25px;" +
                    " border-top:1px solid #fafafa;" +
                    " border-bottom:1px solid #e0e0e0;" +

                    " background: #ededed;" +
                    " background:-webkit-gradient(linear,left top,left bottom,from(#ededed),to(#ebebeb));" +
                    " background:-moz-linear-gradient(top,  #ededed,  #ebebeb);" +
                    " }" +
                    " table th:first-child{" +
                    " text-align:left;" +
                    " padding-left:20px;" +
                    " }" +
                    " table tr:first-child th:first-child{" +
                    " -moz-border-radius-topleft:3px;" +
                    " -webkit-border-top-left-radius:3px;" +
                    " border-top-left-radius:3px;" +
                    " }" +
                    " table tr:first-child th:last-child{" +
                    " -moz-border-radius-topright:3px;" +
                    " -webkit-border-top-right-radius:3px;" +
                    " border-top-right-radius:3px;" +
                    " }" +
                    " table tr{" +
                    " text-align:center;" +
                    " padding-left:20px;" +
                    " }" +
                    " table td:first-child{" +
                    " text-align:left;" +
                    " padding-left:20px;" +
                    " border-left:0;" +
                    " }" +
                    "  table td{" +
                    " padding:18px;" +
                    " text-align:left;" +
                    " border-top:1px solid #ffffff;" +
                    " border-bottom:1px solid #e0e0e0;" +
                    " border-left:1px solid #e0e0e0;" +

                    " }" +
                    " table tr.even td{" +

                    " }" +
                    " table tr:last-child td{" +
                    " border-bottom:0;" +
                    " }" +
                    " table tr:last-child td:first-child{" +
                    " -moz-border-radius-bottomleft:3px;" +
                    " -webkit-border-bottom-left-radius:3px;" +
                    " border-bottom-left-radius:3px;" +
                    " }" +
                    " table tr:last-child td:last-child{" +
                    " -moz-border-radius-bottomright:3px;" +
                    " -webkit-border-bottom-right-radius:3px;" +
                    " border-bottom-right-radius:3px;" +
                    " }" +
                    " table tr:hover td{" +
                    " background: #f2f2f2;" +
                    " background:-webkit-gradient(linear,left top,left bottom,from(#f2f2f2),to(#f0f0f0));" +
                    " background:-moz-linear-gradient(top,  #f2f2f2,  #f0f0f0);" +
                    " }" +
                    " </style>" +
                    " </head>" +
                    " <body> " +

                    " <div id=\"box\"> " +
                    " <main id=\"center\">  ";
    private final Logger logger = LoggerFactory.getLogger(TenmaStackTraceManager.class);
    private Vector<LogExceptionModel> stackTraceError = new Vector();
    private boolean running = true;
    private int emailSendCountDown;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public TenmaStackTraceManager() {
        this.running = true; // init start the thread
        new Thread(this).start();
    }

    public static TenmaStackTraceManager getInstance() {
        if (instance == null) {
            instance = new TenmaStackTraceManager();
            mapLog = new Vector();
        }
        return instance;
    }

    public static boolean isInstance() {
        if (instance == null) {
            return false;
        }
        return true;
    }

    public void stopStackManager() {
        running = false;
    }

    public synchronized void addNewStackTrace(String e) {
        logger.info("Log exception = " + e);
        LogExceptionModel m = new LogExceptionModel();
        m.setExceptionTrace(e);
        m.setExceptionDate(LocalDateTime.now(Clock.systemUTC()));
        try {
            m.setCommunity(TA.getCurrent().getCommunityModel().getCommunityName() == null ? TA.getCurrent().getCommunityModel().getCommunityId() : TA.getCurrent().getCommunityModel().getCommunityName());
        } catch (Exception e1) {
            m.setCommunity("");
        }
        try {
            m.setUserId(TA.getCurrent().getClientInfo().getClientUserModel().getUserId() == null ? "" : TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        } catch (Exception e1) {
            m.setUserId("");
        }
        try {
            m.setModule("N/A");
        } catch (Exception e1) {
            m.setModule("");
        }
        try {
            m.setIp(TA.getCurrent().getRemoteAddress());
        } catch (Exception e1) {
            m.setIp("");
        }
        addNewStackTrace(m);
    }

    public synchronized void addNewStackTrace(int severity, Exception e) {
        logger.info("Log exception = " + e.getMessage());
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        PrintWriter pwriter = new PrintWriter(ostream);
        e.printStackTrace(pwriter);
        pwriter.flush();
        StringBuffer stacktrace = new StringBuffer(ostream.toString());
        LogExceptionModel m = new LogExceptionModel();
        m.setSeverity(severity);
        m.setExceptionTrace(stacktrace.toString());
        m.setExceptionDate(LocalDateTime.now(Clock.systemUTC()));
        try {
            m.setCommunity(TA.getCurrent().getCommunityModel().getCommunityName() == null ? TA.getCurrent().getCommunityModel().getCommunityId() : TA.getCurrent().getCommunityModel().getCommunityName());
        } catch (Exception e1) {
            m.setCommunity("UNKNOWN-null");
        }
        try {
            m.setUserId(TA.getCurrent().getClientInfo().getClientUserModel().getUserId() == null ? "" : TA.getCurrent().getClientInfo().getClientUserModel().getUserFullName());
        } catch (Exception e1) {
            m.setUserId("UNKNOWN-null");
        }
        try {
            m.setModule("N/A");
        } catch (Exception e1) {
            m.setModule("UNKNOWN-null");
        }
        try {
            m.setIp(TA.getCurrent().getRemoteAddress());
        } catch (Exception e1) {
            m.setIp("");
        }
        addNewStackTrace(m);
    }

    public synchronized void addNewStackTrace(LogExceptionModel stacktrace) {
        logger.info("Stacktrace userId = " + stacktrace.getUserId());
        stackTraceError.add(stacktrace);
    }

    @Override
    public void run() {
        LocalDateTime starttime = LocalDateTime.now(Clock.systemUTC());
        emailSendCountDown = SEND_EMAIL_EVERY;
        int repetedError = 0;
        boolean exception = false;
        while (running) {
            try {
                Thread.sleep(SLEEP_TIME);
                emailSendCountDown--; // countdown every minutes
                int errorno = stackTraceError.size();
                if (emailSendCountDown == 0) {
                    emailSendCountDown = SEND_EMAIL_EVERY;
                    if (errorno > 0) {
                        sendEmailError();
                    }
                } else {
                    if (errorno >= SEND_EARLY_IF_ERROR) {
                        sendEmailError();
                    }
                }
                logger.info("StackTrace Log = " + errorno);
                if (exception) {
                    // recover thread sleep
                    exception = false;
                    SLEEP_TIME = DEF_SLEEP_TIME;
                }
            } catch (Exception e) {
                exception = true;
                // make slower by adding 5 minutes more sleep everytime exception thrown
                SLEEP_TIME = SLEEP_TIME + 5 * SLEEP_TIME; // make thread slower if there is exception
                addNewStackTrace(40, e);
                repetedError++;
                if (repetedError > 10) { // will be shutdown the stacktrace manager afer more than 10 hours exception still repeatedly.
                    running = false; // stop when got exception in thred 10x
                    logger.info("Thread Exception repeatedly 10x within more than 10 hours, Stacktracemanager is shutdown");
                }

            }
        }
        LocalDateTime endtime = LocalDateTime.now(Clock.systemUTC());
        Duration duration = Duration.between(starttime, endtime);
        logger.info("StackTrace Logger running = " + TenmaConverter.formatNumber(duration.getSeconds()));
        // write into file and email
    }

    private void sendEmailError() {
        LocalDateTime dateTime = LocalDateTime.now(Clock.systemUTC());
        String title = stackTraceError.size() + " StackTraces at " + dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth() + " " + dateTime.getHour() + ":" + dateTime.getMinute() + " (UTC)";
        String message = defineBodyMessage();
        EmailModel email1 = new EmailModel();
        email1.setEmailTo("gusti.anom@gmail.share"); // temporary hardcoded
        email1.setSubjectMessage(title);
        email1.setBodyMessage(message.toString());
        email1.setFileAttached(null);
        email1.setCreatedBy(Constants.SYSTEM);
        email1.setUpdatedBy(Constants.SYSTEM);

        EmailModel email2 = new EmailModel();
        email2.setEmailTo("ndwijaya@gmail.share"); // temporary hardcoded
        email2.setSubjectMessage(title);
        email2.setBodyMessage(message.toString());
        email2.setFileAttached(null);
        email2.setCreatedBy(Constants.SYSTEM);
        email2.setUpdatedBy(Constants.SYSTEM);

        List<EmailModel> lemail = new ArrayList<EmailModel>();
        lemail.add(email1);
        lemail.add(email2);
//        System.out.println(message.toString());
        NotificationTools tools = new NotificationTools(TA.getCurrent().getLocale(), Constants.SYSTEM, Constants.SYSTEM, "127.0.0.1");
        try {
            tools.doSendEmail(lemail);
            mapLog.addAll(stackTraceError.stream().collect(Collectors.toList()));


            stackTraceError.clear();
        } catch (Exception e) {
            e.printStackTrace();
//            stackTraceError.add(e.getMessage());
        }
    }

    public String defineBodyMessage() {
        LocalDateTime dateTime = LocalDateTime.now(Clock.systemUTC());
        String title = stackTraceError.size() + " StackTraces at " + dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth() + " " + dateTime.getHour() + ":" + dateTime.getMinute() + " (UTC)";
        StringBuilder message = new StringBuilder();
        message.append(headerHTML);
        message.append("<h2>Stace Trace Log : " + title + "</h2>");

        if (stackTraceError.size() > 1) {

            message.append("<BR>");
            message.append("<h3>Summary by Community: </h3>");

            Map<String, Long> couuntCommunity = stackTraceError.stream().map(LogExceptionModel::getCommunity)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            message.append(headerTableHTMLSUMMARY_COMMUNITY);
            couuntCommunity.forEach((k, v) -> {
                message.append("<tr>");
                message.append("<td>");
                message.append(k);
                message.append("</td>");
                message.append("<td>");
                message.append(v);
                message.append("</td>");

            });
            message.append(footerHTMLTABLESUMMARY);

            message.append("<BR>");
            message.append("<h3>Summary by IP: </h3>");

            Map<String, Long> IP = stackTraceError.stream().map(LogExceptionModel::getIp)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            message.append(headerTableHTMLSUMMARY_IP);
            IP.forEach((k, v) -> {
                message.append("<tr>");
                message.append("<td>");
                message.append(k);
                message.append("</td>");
                message.append("<td>");
                message.append(v);
                message.append("</td>");

            });
            message.append(footerHTMLTABLESUMMARY);

            message.append("<BR>");
            message.append("<h3>Summary by UserId: </h3>");

            Map<String, Long> counted = stackTraceError.stream()
                    .map(LogExceptionModel::getUserId)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            message.append(headerTableHTMLSUMMARY_USER);
            counted.forEach((k, v) -> {
                message.append("<tr>");
                message.append("<td>");
                message.append(k);
                message.append("</td>");
                message.append("<td>");
                message.append(v);
                message.append("</td>");

            });
            message.append(footerHTMLTABLESUMMARY);


            message.append("<BR>");
            message.append("<h3>Total All Exception before restart per day:  </h3>");
            message.append(headerTableHTMLSUMMARY_DAY);

            mapLog.stream().map(LogExceptionModel::getExceptionDate).map(e -> e.getDayOfYear()).
                    collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).forEach((k, v) -> {
                message.append("<tr>");
                message.append("<td>");
                message.append(k);
                message.append("</td>");
                message.append("<td>");
                message.append(v);
                message.append("</td>");

            });
            message.append(footerHTMLTABLESUMMARY);


            message.append("<BR>");
        }
        message.append(headerTableHTML);

        stackTraceError.forEach(s -> {
            message.append("<tr>");
            message.append("<td>");
            message.append(s.getExceptionDate().format(formatter));
            message.append("</td>");
            message.append("<td>");
            message.append(s.getCommunity() == null ? "" : s.getCommunity());
            message.append("</td>");
            message.append("<td>");
            message.append(s.getUserId() == null ? "" : s.getUserId());
            message.append("</td>");
            message.append("<td>");
            message.append(s.getIp() == null ? "" : s.getIp());
            message.append("</td>");
            message.append("<td>");
            message.append(s.getModule() == null ? "" : s.getModule());
            message.append("</td>");
            message.append("</tr>");
            message.append("<tr>");
            message.append("<td colspan=\"5\">");
            message.append(s.getExceptionTrace());
            message.append("</td>");
            message.append("</tr>");
        });

        message.append(footerHTMLTABLE);


        return message.toString();
    }
}
