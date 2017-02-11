package com.tenma.common.gui.display.profile;

import com.tenma.common.gui.display.component.TenmaImageHost;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ndwijaya on 10/16/15.
 */
public class TenmaImageUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getServletPath().substring(req.getServletPath().lastIndexOf('/') + 1);
        showImage(req, resp);
    }

    private void showImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageKey = (String) req.getParameter("imageKey");
        if (imageKey != null) {
            try {
                TenmaImageHost imageHost = TenmaImageHost.getInstance();
                byte[] image = imageHost.getData(imageKey);
//                System.out.println("\n\n\n SERVLET IMAGE UPLOAD =>" + inputStream);
                if (image != null) {
                    OutputStream outputStream = resp.getOutputStream();
                    outputStream.write(image);
                    outputStream.flush();
                } else {
                    resp.getWriter().write("<p>Not Image Story</p>");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.getWriter().write("<p>InternalError</p>");
            }
        } else {
            resp.getWriter().write("<p>imageKey Not Found<</p>");
        }
    }


}
