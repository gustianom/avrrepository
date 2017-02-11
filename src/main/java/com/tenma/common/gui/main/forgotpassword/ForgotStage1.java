package com.tenma.common.gui.main.forgotpassword;

import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.util.TenmaConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 10/18/13
 * Time: 5:36 PM
 */
public class ForgotStage1 extends TenmaNewPanel implements ForgotInterface {
    private final ForgotPassword parentComponent;
    private TextField field;

    public ForgotStage1(ForgotPassword components) {
        super();
        parentComponent = components;
        setCompositionRoot(createLayoutForgot());
    }

    private FormLayout createLayoutForgot() {
        FormLayout lay = new FormLayout();
        lay.setWidth("100%");
        lay.setSpacing(true);


        StringBuffer buf = new StringBuffer()
                .append(param.getLabel("default.email"))
                .append(" ")
                .append(param.getLabel("default.or"))
                .append(" ")
                .append(param.getLabel("default.mobilePhone"));
/*                .append(", ")
                .append(param.getLabel("user.userName"));*/

        field = new TextField();
        field.setWidth("80%");
        field.setRequired(true);
        field.addShortcutListener(new ShortcutListener("textSeacrList" + getId(), ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                parentComponent.doButtonContinue();
            }
        });


        Label labelExplanation = new Label(param.getLabel("forgotPassword.explanation"));
        labelExplanation.setContentMode(ContentMode.HTML);

        Label labelItem = new Label(buf.toString());
        labelItem.setContentMode(ContentMode.HTML);

        lay.addComponent(labelExplanation);
        lay.addComponent(new Label());
        lay.addComponent(labelItem);
        lay.addComponent(field);

        lay.setComponentAlignment(labelExplanation, Alignment.TOP_LEFT);
        lay.setComponentAlignment(field, Alignment.MIDDLE_LEFT);
        lay.setMargin(true);

        return lay;
    }

    public void doFieldFocus() {
        field.focus();
    }

    public boolean saveCurrentStage() throws Exception {
        boolean isCont = false;
        if ((field.getValue() != null) && (field.getValue().trim().length() != 0)) {
            UserHelper hlp = new UserHelper();
            String vlsearch = field.getValue();
            if (vlsearch.startsWith("0"))
                vlsearch = TenmaConverter.getMobileNumber(vlsearch);
            else
                vlsearch = vlsearch.toLowerCase();

            UserModel mdl = hlp.findForgottenPassword(vlsearch, TA.getCurrent().getAuthenticationSource());
            if (mdl != null) {
                isCont = true;
                parentComponent.dataForgot.put(parentComponent.USER_MODEL, mdl);
            } else
                Notification.show(param.getLabel("forgotPassword.notfound"), Notification.Type.HUMANIZED_MESSAGE);
        }
        return isCont;
    }

}
