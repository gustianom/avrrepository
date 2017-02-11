package com.tenma.auth.util.logon;

import com.tenma.auth.util.AuthConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogonHandler extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processingHandler(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processingHandler(request, response);
    }

    private void processingHandler(HttpServletRequest request, HttpServletResponse response) {
        String commandName = request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1);
        if (AuthConstants.DO_LOGOUT.equals(commandName))
            doLogout(request, response);
        else {
            //System.out.println("LogonHandler.processingHandler");
            //System.out.println("commandName = " + commandName);
            //System.out.println("UNKNOWN COMMAND");
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
            request.setAttribute(AuthConstants.SERVER_RESPONSE_TYPE, AuthConstants.SERVER_RESPONSE_ERROR);
            request.setAttribute(AuthConstants.SERVER_RESPONSE_MESSAGE, e.getMessage());
        }
    }
}

