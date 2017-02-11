package com.tenma.common.util.web;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gustianom on 06/10/15.
 */
public class JSONReader {


    public static JSONObject getJSONFromUrl(String urlStr) {
        JSONObject jObj = null;
        InputStream in = null;
        String json = "";
        // Making HTTP request
        try {
            URL url = new URL(urlStr);
            ;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println(line);
            }
            in.close();
            json = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            System.out.println("error on parse data in jsonparser.java");
            e.printStackTrace();
        }

        // return JSON String
        return jObj;

    }
}