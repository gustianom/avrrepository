package com.tenma.common.gui.display.component;

import com.tenma.common.util.TenmaConverter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by tenma05 on 16/11/15.
 */
public class TenmaImageHost {
    private static TenmaImageHost tenmaImageHost;
    private HashMap map;

    private TenmaImageHost() {
        map = new HashMap();
    }

    public static TenmaImageHost getInstance() {
        if (tenmaImageHost == null) {
            tenmaImageHost = new TenmaImageHost();
        }
        return tenmaImageHost;
    }

    public synchronized byte[] getData(String numberKey) {
//        System.out.println("\n\n\n GET DATA ===> " + map.get(numberKey));
        return (byte[]) map.get(numberKey);
    }

    public synchronized String putData(Object dataObject) {
//        System.out.println("\n\n\n PUT DATA Tenma Image HOST ");
        String generateNumber = null;
        try {
            generateNumber = TenmaConverter.generateRandomPassword(true, 6);
            byte[] image = IOUtils.toByteArray((InputStream) dataObject);
            map.put(generateNumber, image);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return generateNumber;
    }
}
