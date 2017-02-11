package com.tenma.common.gui.display.profile;

import com.tenma.common.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ndwijaya on 10/22/2015.
 */
public class TenmaImageServletTools extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String test = req.getParameter("test");
        if (test != null) {
            Object o = req.getSession().getAttribute("imageupload");
            if (o != null) {
                resp.setStatus(200);
            } else {
                resp.sendError(1180, "buffer is not ready");
            }
        } else {
            Object o = req.getSession().getAttribute("imageupload");
            if (o == null) {
                System.out.println("TenmaImageServletTools.doGet, imageupload = null");
                resp.sendError(1180, "buffer is not ready");
            } else {
                byte[] bytes = (byte[]) o;
                resp.setContentLength(bytes.length);
                resp.setContentType(Constants.MIME_TYPES.PNG.getValue());
                OutputStream outputStream = resp.getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
            }
        }
    }


}
