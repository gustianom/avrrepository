package com.tenma.common.gui.display.profile;

import com.google.gson.Gson;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by ndwijaya on 10/5/15.
 */
public class ProfileImageTools {

    public InputStream getSteam(String json) {
        Gson gSon = new Gson();
        byte[] bytes = gSon.fromJson(json, byte[].class);
        Random random = new Random();
        int r = random.nextInt(10000000) + 1000;
        ProfileImageStream stream = new ProfileImageStream(bytes);
//        StreamResource resource =
//                new StreamResource(stream, r + "profile.png");
        return stream.getStream();
    }

    public Image generateImage(String json) {
        Gson gSon = new Gson();
        byte[] bytes = gSon.fromJson(json, byte[].class);
        return generateImage(bytes);
    }

    public Image generateImage(byte[] bytes) {
        Random random = new Random();
        int r = random.nextInt(10000000) + 1000;
        ProfileImageStream stream = new ProfileImageStream(bytes);
        StreamResource resource =
                new StreamResource(stream, r + "profile.png");
        Image img = new Image(null, resource);
        return img;
    }

    class ProfileImageStream implements StreamResource.StreamSource {
        private byte[] buff;

        ProfileImageStream(byte[] bytes) {
            this.buff = bytes;
        }

        @Override
        public InputStream getStream() {
            InputStream inputStream = null;
            try {

                inputStream = new ByteArrayInputStream(
                        buff);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return inputStream;
        }
    }
}
