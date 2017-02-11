package com.tenma.util.sms.services;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 5/27/12
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class BroadCastService {
    private static Object readInputStream(URLConnection con) {
        Object object = null;
        InputStream in = null;
        ObjectInputStream objectInputStream = null;
        try {
            in = con.getInputStream();
            objectInputStream = new ObjectInputStream(in);
            object = objectInputStream.readObject();
            in.close();
        } catch (EOFException eof) {
            // do nothing
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in = null;
            objectInputStream = null;
        }
        return object;
    }

    private static URLConnection getServletConnection(String url) throws MalformedURLException, IOException {
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

    public void doBroadCastData(HashMap map, String targetPath) {
        OutputStream out = null;
        ObjectOutputStream objectStream = null;
        Object data = null;
        URLConnection con = null;
        try {
            con = getServletConnection(targetPath);
            out = con.getOutputStream();
            objectStream = new ObjectOutputStream(new GZIPOutputStream(out));
            objectStream.writeObject(map);
            objectStream.flush();
            objectStream.close();
            data = readInputStream(con);

            String string;
            StringBuilder outputBuilder = new StringBuilder();
            if (data != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) data));
                while (null != (string = reader.readLine())) {
                    outputBuilder.append(string).append('\n');
                }
            }

            //System.out.println("BroadCastService.doBroadCastData "+outputBuilder.toString());
            out.close();
        } catch (EOFException eof) {
            // do nothing
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con = null;
        }
    }
}
