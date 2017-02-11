package com.tenma.common.gui;

import com.tenma.common.TA;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.Collection;

/**
 * Created by ndwijaya on 1/1/2015.
 * TenmaForm is asbstact class that hold the FieldGroup instance, perform validation and errorMessage broadcasting
 * Tenma Form force UIForm to have validate() method and setErrorMessage() method
 * - validate() is require and use before saving process
 * - setErrorMessage is design for setting the customer errormessage for each field on the form
 * Tenma Form will set default error message if the field is required but error message is not set with
 * component Caption + label (def.isrequired)
 * Tenma Form has 3 type of Message broadcast handling :
 * on Tenma Application mean the error message will be shown on header Form
 * on current form mean the error message will be shown as label on top of form
 * on Notificaiton message : show as notification
 * <p/>
 * Future Posibility Enhancement (require some small reseach and POC than plan the design):
 * - multiple error message list broadcasting so can show complete error in one message UI
 * - realtime validation on each field option
 * - statistical error log for enhancement data input (could be file or persistance in database)
 * - support UnDo mechanism togather with commit helper and tenma framework (require good design at begining)
 * - meaven/ant script for replacing tenamtools in creating simple CRUD
 * - buildin Notification API (like SMS, Email, Twitter, Facebook, etc)
 * - Attached with Workflow process as design on TenmaWorkFlow framework
 * for instance (just rough idea) using on implementation
 * setWorkflowID("TENAM-W001"); // TENMA-W001 already set asking approval to Sales Manager/other configurable
 * setStartTriggering(TenmaWorkflow.START_AFTER_SAVE, TenmaTransaction.TRUE );
 * - on the fly deattach/change the workflow if required.
 */
public abstract class TenmaForm extends VerticalLayout {
    protected FieldGroup fieldGroup;


    private Label labelError = new Label();

    private VIEW_ERROR_MESSAGE viewErrorFieldOnMessageLocation;

    /*
        Default constructure, the error message is set as ON Tenma Application
        use this constructor if the error message broadcasting is default and the second constructor if required
        others broadcasting message
     */
    public TenmaForm() {

        viewErrorFieldOnMessageLocation = VIEW_ERROR_MESSAGE.ON_TENMA_APPLICATION;
        //    super.addComponent(labelError);

        this.setWidth(800, Unit.PIXELS);
        this.setPrimaryStyleName("metro-layout");
        labelError.setPrimaryStyleName("validation");

    }

    public TenmaForm(VIEW_ERROR_MESSAGE error_message) {

        viewErrorFieldOnMessageLocation = error_message;
        //   super.addComponent(labelError);
    }

    public String getLabel(String caption) {
        return TA.getCurrent().getParams().getLabel(caption);
    }

    @Override
    public void addComponent(Component c) {
        super.addComponent(c);
        super.setMargin(new MarginInfo(false, true, false, true));

        setValidator();
        setErrorMessage();
    }

    public void resetValidation() {
        Collection c = fieldGroup.getFields();
        for (Object component : c) {
            if (component instanceof AbstractField) {
                final AbstractField ac = (AbstractField) component;
                ac.setValidationVisible(false);

            }
        }
    }

    private void recheckValidator() {
        // avoid NPE for unassignvalidtor
        Collection c = fieldGroup.getFields();
        for (Object component : c) {
            if (component instanceof AbstractField) {
                AbstractField ac = (AbstractField) component;
                ac.setValidationVisible(true);
                if (ac.isRequired() && ac.getRequiredError() == null) {
                    ac.setRequiredError(ac.getCaption() + " " + TA.getCurrent().getParams().getLabel("def.isrequired"));
                }
            }
        }
    }

    public boolean isValidate() {
        boolean rs = false;
        try {
            recheckValidator(); // avoid NPE for unset validator (Temporary)
            fieldGroup.commit();
            rs = true;
        } catch (FieldGroup.CommitException e) {
            Collection c = fieldGroup.getFields();
            for (Object component : c) {
                if (component instanceof AbstractField) {
                    AbstractField ac = (AbstractField) component;
                    System.out.println(ac.getCaption() == null ? ac.getClass().getName() : ac.getCaption() + " validation is >>> " + ac.isValid());
                    System.out.println("error msg ----> " + ac.getRequiredError());
                    if (!ac.isValid()) {
                        if (ac.getValue() != null) {
                            // field is not null mean the entried is invalid
                            if (ac.getRequiredError() == null) {
                                ac.setRequiredError(ac.getCaption() + " " + TA.getCurrent().getParams().getLabel("def.invalid"));
                            }
                            doUpdateFormStatus(ac.getRequiredError());
                        } else {
                            System.out.println("TenmaForm.isValidate");
                            // field is null that mean the field is required not null
                            if (ac.getRequiredError() == null && ac.isRequired()) {
                                ac.setRequiredError(ac.getCaption() + " " + TA.getCurrent().getParams().getLabel("def.isrequired"));
                            }
                            doUpdateFormStatus(ac.getRequiredError());
                        }
                        ac.focus();
                        break; // message is shown for the first error
                    }
                }
            }
        }
        return rs;
    }

    public VIEW_ERROR_MESSAGE getViewErrorFieldOnMessageLocation() {
        return viewErrorFieldOnMessageLocation;
    }

    public final void doUpdateFormStatus(String fieldMessage) {
        System.out.println("TenmaForm.doUpdateFormStatus");
        String errroFieldMessage = "";
        if (fieldMessage == null) fieldMessage = "";
        errroFieldMessage = fieldMessage;
        if (VIEW_ERROR_MESSAGE.ON_CURRENT_FORM.equals(getViewErrorFieldOnMessageLocation())) {

//            System.out.println("sini ke form");
//            TA.getCurrent().setErrorFieldMessage("");
            labelError.setValue(errroFieldMessage);
            if (errroFieldMessage != null && errroFieldMessage.trim().length() != 0)
                labelError.setVisible(true);
            else
                labelError.setVisible(false);
        } else if (VIEW_ERROR_MESSAGE.ON_TENMA_APPLICATION.equals(getViewErrorFieldOnMessageLocation())) {

//            TA.getCurrent().setErrorFieldMessage(errroFieldMessage);
            labelError.setValue("");
            labelError.setVisible(false);
        } else {
            System.out.println("sini ke try");
            labelError.setVisible(false);
//            TA.getCurrent().setErrorFieldMessage("");
            Notification.show(errroFieldMessage, Notification.Type.HUMANIZED_MESSAGE);
        }

    }

    /*
     not required implementation if using BeanImplemention (Model)
     for instance as this :  fieldGroup.bind(fieldCustomerName, "customerName");
     */
    public void setValidator() {

    }

    public abstract void setErrorMessage();

    public enum VIEW_ERROR_MESSAGE {
        ON_CURRENT_FORM(1), ON_MESSAGE_TRY(2), ON_TENMA_APPLICATION(3);

        private int messageValue = 1;

        VIEW_ERROR_MESSAGE(int value) {
            messageValue = value;
        }

        public int getValue() {
            return messageValue;
        }

        @Override
        public String toString() {
            return String.valueOf(messageValue);
        }
    }

}
