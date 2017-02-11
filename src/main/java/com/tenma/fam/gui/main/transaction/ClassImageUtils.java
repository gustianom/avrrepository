package com.tenma.fam.gui.main.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tenma.common.gui.display.profile.ProfileImageTools;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

/**
 * Created by ndwijaya on 10/11/15.
 */
public class ClassImageUtils {
    public static Image getClassImage(String number) {
        int ino = 1;
        try {
            ino = Integer.parseInt(number);
        } catch (NumberFormatException e) {
        }
        return getClassImage(ino);
    }

    public static Image getClassImageJSON(String number) {

        Gson gson = new Gson();
        byte[] b = null;
        try {
            b = gson.fromJson(number, byte[].class);
        } catch (JsonSyntaxException e) {
            return getClassImage(0);
        }
        if (b == null) {
            return getClassImage(0);
        } else if (b.length == 1) {
            return getClassImage(b[0]);
        } else {
            return new ProfileImageTools().generateImage(b);
        }
    }

    public static Image getClassImage(int number) {
        return new Image(null, new ThemeResource("layouts/images/award/class" + number + ".png"));
    }
}
