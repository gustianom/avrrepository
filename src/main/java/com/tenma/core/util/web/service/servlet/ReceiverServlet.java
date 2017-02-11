/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.util.web.service.servlet;

import com.google.gson.Gson;
import com.tenma.auth.util.logon.TenmaAuth;
import com.tenma.auth.util.logon.action.LoginTool;
import com.tenma.auth.util.web.AuthInfo;
import com.tenma.common.TA;
import com.tenma.common.model.SecureCheckModel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.tenma.common.util.TenmaSecureChecker;
import com.tenma.common.util.session.TemporarySession;
import com.tenma.common.util.web.JSONReader;
import com.tenma.model.core.TicketModel;
import com.vaadin.ui.UI;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:03 PM
 */
public abstract class ReceiverServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(ReceiverServlet.class);

    private String getTicketREST(HttpServletRequest request, String user, String password) {
        TicketModel ticketSon = getRESTTicket(request, user, password);
        return ticketSon.getTicketId();
    }

    public TicketModel getRESTTicket(HttpServletRequest request, String user, String password) {
        //        logger.info("RestMain.signIn");
        TicketModel ticketSon = new TicketModel();
        try {
            String userParam = "?user=" + user;
            String passwordParam = "&password=" + password;

            logger.info("Calling REST to get Ticket..");
            String domainName = new StringBuilder()
                    .append(request.getScheme()).append("://")
                    .append(request.getServerName())
                    .append(request.getServerPort() == 80 ? "" : ":")
                    .append(request.getServerPort() == 80 ? "" : request.getServerPort())
                    .append(request.getContextPath())
                    .append("/ws/rest/signin")
                    .append(userParam)
                    .append(passwordParam)
                    .toString();

            String uri = domainName + userParam + passwordParam;
            logger.info("URL REST = " + domainName);
            URL url = new URL(domainName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");


            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            logger.info("br = " + br);
            String output;
            logger.info("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                logger.info("output = " + output);
                System.out.println(output);
                ticketSon = new Gson().fromJson(output, TicketModel.class);
            }
            logger.info("ticketSon = " + ticketSon);

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticketSon;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processingHandler(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(503, e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processingHandler(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(503, e.getMessage());
            throw new ServletException(e.getMessage());
        }

    }

    private void processingHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        String commandName = request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1);

        logger.info("ReceiverServlet.processingHandler " + commandName);
        if (Constants.SERVLET_COMMAND.DO_LOGOUT.getValue().equals(commandName))
            doLogout(request, response);
        else if (Constants.SERVLET_COMMAND.DO_LOGIN.getValue().equals(commandName))
            doLogin(request, response);
        else if (commandName.endsWith(Constants.SERVLET_COMMAND.DO_DOWNLOAD.getValue()))
            doDownloadFile(commandName, request, response);
        else {
            ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(request.getInputStream()));
            Map receivedMap = (HashMap) ois.readObject();
            String task = (String) receivedMap.get(Constants.TASK_TRANSMITTED);
            HashMap data = (HashMap) receivedMap.get(Constants.DATA_TRANSMITTED);
            if (data == null) data = new HashMap();

            if (Constants.SERVLET_COMMAND.DO_MOBILE_ACTIVATION.getValue().equals(task)) {
                doMobileActivation(data);
            }
        }
    }

    private void doDownloadFile(String commandName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int indx = commandName.indexOf(Constants.SERVLET_COMMAND.DO_DOWNLOAD.getValue());
        boolean result = false;
        int BYTES_DOWNLOAD = 1024;
        if (indx != -1) {
            Params params = Params.getInstance(request.getLocale());
            String filePath = new StringBuffer().append(params.getProperty(Constants.UPLOAD_DIR_FILE)).append("/").append(commandName.substring(0, indx)).toString();

//            logger.info("ReceiverServlet.doDownloadFile " + filePath);

            File downloadFile = new File(filePath);
            FileInputStream inStream = new FileInputStream(downloadFile);

            String relativePath = getServletContext().getRealPath("");
//            logger.info("relativePath = " + relativePath);

            ServletContext context = getServletContext();

            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
//            logger.info("MIME type: " + mimeType);

            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        }

    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String userName = request.getParameter("username");
            String paswd = request.getParameter("passwd");
            String uresponse = request.getParameter("g-recaptcha-response");

            logger.info("==========>> doLogin <<==========");
            logger.info("userName = " + userName);
            logger.info("paswd = " + paswd);

            String privateKey = getServletContext().getInitParameter("tenma.PRIVATE_CAPTCHA_KEY");
            boolean isContinue = false;
            if (uresponse != null) {
                if (isRECaptchaValid(request, response))
                    isContinue = true;
                else {
                    updateFailedCounter(request, userName);
                    logger.error("share.tenma.core.util.web.service.service.ReceiverServlet.doLogin  804");
                    response.sendError(804, "Invalid Captcha Answer");
                }
            } else
                isContinue = true;

            if (isContinue) {
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                logger.info("share.tenma.core.util.web.service.service.ReceiverServlet.doLogin CONTINUE ");

                if ((userName != null) && (userName.trim().length() != 0)) {
                    String authenticationSource = Params.getInstance(request.getLocale()).getProperty(TenmaAuth.SYSTEM_AUTHENTICATION_SOURCE);

                    if (authenticationSource == null) {
                        authenticationSource = TenmaAuth.AUTHENTICATION_BY_DATABASE;
                    } else if (authenticationSource.trim().length() == 0)
                        authenticationSource = TenmaAuth.AUTHENTICATION_BY_DATABASE;
                    else {
                        if (TenmaAuth.AUTHENTICATION_BY_DATABASE.equals(authenticationSource))
                            authenticationSource = TenmaAuth.AUTHENTICATION_BY_DATABASE;
                        else if (TenmaAuth.AUTHENTICATION_BY_LDAP.equals(authenticationSource))
                            authenticationSource = TenmaAuth.AUTHENTICATION_BY_LDAP;
                    }

                    LoginTool loginTool = new LoginTool(authenticationSource, request.getLocale(), request.getRemoteAddr());
                    AuthInfo authInfo = loginTool.isAuthenticated(userName, paswd);
                    if (authInfo == null) {
                        logger.info("share.tenma.core.util.web.service.service.ReceiverServlet.doLogin-Invalid credential");
                        updateFailedCounter(request, userName);
                        response.sendError(402, "Invalid credential");
                    } else {
//                logger.info("ReceiverServlet.doLogin " + request.getSession().getId());

                        StringBuilder myservletPath = new StringBuilder()
                                .append(request.getScheme())
                                .append("://").append(request.getServerName());

                        if (!(request.getServerPort() == 80 || request.getServerPort() == 443))
                            myservletPath.append(":")
                                    .append(request.getServerPort()).append(request.getContextPath());

                        TenmaSecureChecker.getInstance().remove(userName);
                        String ticketId = getTicketREST(request, userName, paswd);
                        logger.info("ticketId = " + ticketId);
                        authInfo.setTicketId(ticketId);
                        logger.info("authInfo.getTicketId() = " + authInfo.getTicketId());

                        TemporarySession.getInstance().putData(request.getSession().getId(), authInfo);
                        request.getSession().setAttribute(Constants.DATA_TRANSMITTED, authInfo);
                        HashMap data = loginTool.doCollectClientDetail(authInfo);
                        request.getSession().setAttribute(Constants.DATADTL_TRANSMITTED, data);
                        logger.info("RESULT :" + data.get(LoginTool.LOGON_RESULT));
                        TA.initSession(request).init(data, authInfo);

// ------ Temporary remark due mobile version of WEB is not ready yet, native mobile apps is prioritized----------------
// --------------- noted by Nengah - 6 Jan 2016-------------------------------------------------------------------------
//                        boolean mobile = isMobile(request);
//                        if (mobile) {
//                            int memberId = authInfo.getClientAuthUserModel().getMemberId();
//                            logger.info("mobile = " + mobile + " memberId = " + memberId);
//                            if (isParent(memberId)) {
//                                StringBuilder mobilePath = new StringBuilder()
//                                        .append(request.getScheme())
//                                        .append("://").append(request.getServerName());
//                                if (!(request.getServerPort() == 80 || request.getServerPort() == 443))
//                                    mobilePath.append(":")
//                                            .append(request.getServerPort()).append("/m"); //TODO - define mobile path in config
//                                logger.info("redirect to " + mobilePath.toString());
//                                response.sendRedirect(mobilePath.toString());
//                            }
//                        }
//  -----------------------------------------end----------------------------------------------------------
                        response.sendRedirect(myservletPath + "/main/login");
                    }
                } else
                    response.sendError(401, "Login");
            }
        } catch (Exception e) {
            logger.info("FAIL to load first loading screen...");
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

//    private boolean isMobile(HttpServletRequest request) {
//        String userAgent = request.getHeader("user-agent").toLowerCase();
//        logger.info("userAgent = " + userAgent);
//        return userAgent != null
//                && userAgent.toLowerCase().matches(
//                ".*(iphone|ipad|android|mobile).*");
//    }

    private void updateFailedCounter(HttpServletRequest request, String userName) {
        SecureCheckModel mdl = TenmaSecureChecker.getInstance().doCheckValidity(userName, request.getLocale());
        int cnt = mdl.getCountFailedEnter();
        cnt++;
        mdl.setCountFailedEnter(cnt);
    }

    private void doMobileActivation(HashMap data) {
        String from = (String) data.get(Constants.BROADCAST_SMS_FROM);
        String msg = (String) data.get(Constants.BROADCAST_SMS_MESSAGE);

        int inx = from.indexOf("%2B");
        if (inx != -1)
            from = new StringBuffer().append(from.substring(inx + 3, from.length())).toString();

        logger.info("new from = " + from);


        if ((msg != null) && (msg.trim().length() != 0)) {
/*
            UserHelper bn = new UserHelper();
            bn.activeByMobile(from, msg);
*/
        } else {
/*
            InvitationHelper bn = new InvitationHelper();
            bn.activeByMobile(from);
*/
        }

    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
        Enumeration x = request.getAttributeNames();
        while (x.hasMoreElements()) {
            String ky = (String) x.nextElement();
            request.removeAttribute(ky);
        }
        request.getSession().invalidate();
        try {
            getServletConfig().getServletContext().getRequestDispatcher("/").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constants.SERVER_RESPONSE_TYPE, Constants.SERVER_RESPONSE_ERROR);
            request.setAttribute(Constants.SERVER_RESPONSE_MESSAGE, e.getMessage());
        }
        UI.getCurrent().detach();
    }

    private URLConnection getServletConnection(String url) throws MalformedURLException, IOException {
        URL servletURL = null;
        URLConnection con = null;
        try {
            servletURL = new URL(url);
            con = servletURL.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-java-serialized-object");
            con.setAllowUserInteraction(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            servletURL = null;
        }
        return con;
    }

    private boolean isRECaptchaValid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = "https://www.google.share/recaptcha/api/siteverify";
//        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String uresponse = request.getParameter("g-recaptcha-response");

        String privateKey = getServletContext().getInitParameter("tenma.PRIVATE_CAPTCHA_KEY");
        String publicKey = getServletContext().getInitParameter("tenma.PUBLIC_CAPTCHA_KEY");

        logger.info("SignUpBean.isRECaptchaValid privateke " + privateKey);

        String remoteAddr = request.getRemoteAddr();
        String newUrl = new StringBuffer().append(url)
                .append("?secret=").append(URLEncoder.encode(privateKey, "UTF-8"))
                .append("&response=").append(URLEncoder.encode(uresponse, "UTF-8"))
                .append("&remoteip=").append(URLEncoder.encode(remoteAddr, "UTF-8"))
                .toString();
        JSONObject json = JSONReader.getJSONFromUrl(newUrl);
        boolean bolres = json.getBoolean("success");
        return bolres;

    }
}

