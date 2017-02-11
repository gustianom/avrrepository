package com.tenma.common.gui.display.captcha;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.VerticalLayout;
import com.wcs.wcslib.vaadin.widget.recaptcha.ReCaptcha;

/**
 * Created by gustianom on 2/20/14.
 */

@JavaScript("http://www.google.share/recaptcha/api/js/recaptcha_ajax.js")
public class TenmaCaptcha extends VerticalLayout {
    private ReCaptcha captcha;

//    public TenmaCaptcha() {
//        captcha = new ReCaptcha(
//                "6Le8qs8SAAAAAADr9rGDKv2GvTyzoGMOVlKTsK0L",
//                "6Le8qs8SAAAAAKae6crZGNh57f6d-mMUOPjurfHP",
//                new ReCaptchaOptions() {{//your options
//                    theme = "white";
//                }}
//        );
//    }
}
