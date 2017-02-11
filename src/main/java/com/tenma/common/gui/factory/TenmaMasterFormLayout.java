package com.tenma.common.gui.factory;


import com.tenma.common.TA;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

import java.util.List;


/**
 * Created by PT Tenma Bright Sky
 * User: sigit
 * Date: 11/3/12
 * Time: 11:51 AM
 */
public class TenmaMasterFormLayout extends FormLayout {
    private FieldGroup fieldGroup = new FieldGroup();
    private BeanItem itemDataSource;
    private List<String> visibleItemProperties;
    private TenmaField fieldFactory;

    private String errroFieldMessage = "";
    private Label labelError = new Label();
    private VIEW_ERROR_MESSAGE viewErrorFieldOnMessageLocation;
    private TenmaMasterFormModify parent;


    public TenmaMasterFormLayout() {
        super();
        this.parent = parent;
        viewErrorFieldOnMessageLocation = VIEW_ERROR_MESSAGE.ON_CURRENT_FORM;
        preparingForm();
    }

    public TenmaMasterFormLayout(TenmaMasterFormModify parent) {
        super();
        this.parent = parent;
        viewErrorFieldOnMessageLocation = VIEW_ERROR_MESSAGE.ON_CURRENT_FORM;
        preparingForm();
    }

    public TenmaMasterFormLayout(VIEW_ERROR_MESSAGE viewErrorFieldOnMessageLocation) {
        super();
        this.viewErrorFieldOnMessageLocation = viewErrorFieldOnMessageLocation;
        preparingForm();
    }

    private void preparingForm() {
        labelError.setStyleName("labelError");

        addComponent(labelError);
        labelError.setVisible(false);
    }

    public final boolean commit() {
        boolean result = false;

        try {
            System.out.println("share.tenma.common.gui.factory.TenmaMasterFormLayout.commit");
            doUpdateFormStatus("");
            fieldGroup.commit();
            if (parent != null) {
                if (parent.isValidAfter()) {
                    result = true;
                } else {
                    result = false;
                }
            } else {
                result = true;
            }
        } catch (FieldGroup.CommitException e) {
            e.printStackTrace();

            System.out.println("share.tenma.common.gui.factory.TenmaMasterFormLayout.commit");
            String msg = TA.getCurrent().getParams().getLabel("message.requiredFieldIsEmpty");//"Required field is empty";
            try {
                if (e.getCause() != null) {
                    msg = e.getCause().getMessage();
                    if (msg != null)
                        if (msg.trim().length() == 0)
                            msg = e.getMessage();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
//            this.fieldGroup.getFields();
            doUpdateFormStatus(msg);
            Notification.show("Save Failed", msg, Notification.Type.TRAY_NOTIFICATION);
            //e.printStackTrace();
        }

        return result;
    }

    public void addComponent(Component c, boolean fieldgroup) {
        try {
            super.addComponent(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addComponent(Component c) {
        if (c instanceof Field) {
            Field f = (Field) c;
            if (f.isRequired()) {
                if (!(f.getRequiredError() != null && f.getRequiredError().trim().length() != 0)) {
                    StringBuffer buf = new StringBuffer();
                    String caption = f.getCaption();
                    if (caption != null && caption.trim().length() != 0)
                        buf.append(caption).append(" ");
                    else {
                        if (f.getParent() != null) {
                            caption = f.getParent().getCaption();
                            if (caption != null && caption.trim().length() != 0)
                                buf.append(caption).append(" ");
                            else
                                buf.append("*").append(" ");
                        } else
                            buf.append("*").append(" ");
                    }

                    buf.append(TA.getCurrent().getParams().getLabel("default.required"));
                    f.setRequiredError(buf.toString());
                }
            }
        }
        super.addComponent(c);
    }

    public final String getStatus() {
        return errroFieldMessage;
    }

    public final void setItemDataSource(BeanItem itemDataSource) {
        this.itemDataSource = itemDataSource;
        fieldGroup.setItemDataSource(itemDataSource);
    }

    public final void setVisibleItemProperties(List<String> visibleItemProperties) {
        this.visibleItemProperties = visibleItemProperties;
    }

    public final void setFieldFactory(TenmaField fieldFactory) {
        this.fieldFactory = fieldFactory;
    }

    public FieldGroup getFieldGroup() {
        return fieldGroup;
    }

    public final void discard() {
        fieldGroup.discard();
    }

    public final void bindUI() {
        if (visibleItemProperties != null) {
            for (int i = 0; i < visibleItemProperties.size(); i++) {
                String fieldId = visibleItemProperties.get(i);
                Field<?> f = fieldFactory.createField(itemDataSource, fieldId);
                fieldGroup.bind(f, fieldId);
                addComponent(f);
            }
        }
    }

    public VIEW_ERROR_MESSAGE getViewErrorFieldOnMessageLocation() {
        return viewErrorFieldOnMessageLocation;
    }

    public void setViewErrorFieldOnMessageLocation(VIEW_ERROR_MESSAGE viewErrorFieldOnMessageLocation) {
        this.viewErrorFieldOnMessageLocation = viewErrorFieldOnMessageLocation;
    }

    public Field<?> getField(String fieldId) {
        return fieldGroup.getField(fieldId);
    }

    public final void doUpdateFormStatus(String fieldMessage) {
        if (fieldMessage == null) fieldMessage = "";
        errroFieldMessage = fieldMessage;
        if (VIEW_ERROR_MESSAGE.ON_CURRENT_FORM.equals(getViewErrorFieldOnMessageLocation())) {

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
            labelError.setVisible(false);
//            TA.getCurrent().setErrorFieldMessage("");
            Notification.show(errroFieldMessage, Notification.Type.HUMANIZED_MESSAGE);
        }

    }

    public static enum VIEW_ERROR_MESSAGE {
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
