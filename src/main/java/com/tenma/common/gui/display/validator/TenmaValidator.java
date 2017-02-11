package com.tenma.common.gui.display.validator;

import com.tenma.auth.model.MemberModel;
import com.tenma.auth.util.logon.MemberHelper;
import com.tenma.common.util.TenmaConverter;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 4/27/13
 * Time: 7:23 AM
 */
public class TenmaValidator implements Validator {
    public static int VALIDATOR_TYPE_INTEGER = 0;
    public static int VALIDATOR_TYPE_DATE = 1;
    public static int VALIDATOR_TYPE_TIME = 2;
    public static int VALIDATOR_TYPE_EMAIL = 3;
    public static int VALIDATOR_TYPE_CELLPHONE = 4;
    public static int VALIDATOR_TYPE_STRING = 5;

    public static int VALIDATOR_TYPE_UNIQUE_PHONE = 6;
    public static int VALIDATOR_TYPE_UNIQUE_EMAIL_ADDRESS = 7;
    public static int VALIDATOR_TYPE_UNIQUE_USER_NAME = 8;
    public static int VALIDATOR_TYPE_DOUBLE = 9;

    private String errorMessage;
    private int validationType;
    private boolean valid;
    private boolean addMode;

    public TenmaValidator(String errorMessage, int validationType) {
        prepareValidator(errorMessage, validationType);
    }

    public TenmaValidator(String errorMessage, int validationType, boolean addMode) {
        prepareValidator(errorMessage, validationType);
        this.addMode = addMode;
    }

    private void prepareValidator(String errorMessage, int validationType) {
        this.errorMessage = errorMessage;
        this.validationType = validationType;

    }

    private boolean isValid(Object value) {
        if (value == null || !(value instanceof String)) {
            return false;
        }
        String s = "";
        try {
            switch (validationType) {
                case 0:
                    Integer.parseInt((String) value);
                    break;
                case 1:
                    s = (String) value;
                    TenmaConverter.stringToDate(s, "dd-MMM-yyyy");
                    break;
                case 2:
                    s = (String) value;
                    TenmaConverter.stringToDate(s, "hh:mm");
                    break;
                case 3:
                    s = (String) value;
                    validateEmail(s);
                    break;
                case 4:
                    s = (String) value;
                    validateCellPhone(s);
                    break;
                case 6:
                    s = (String) value;
                    validateUniquePhone(s);
                    break;
                case 7:
                    s = (String) value;
                    validateUniqueEmail(s);
                    break;
                case 8:
                    break;
                case 9:
                    s = (String) value;
                    Double.valueOf(s);
                    break;
                default:
                    s = (String) value;
                    break;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void validateCellPhone(String phoneNumber) throws Exception {
        if (!(TenmaConverter.isNumber(phoneNumber) && (phoneNumber.length() > 10 && phoneNumber.length() <= 12)))
            throw new Exception(errorMessage);
    }

    private void validateUniquePhone(String phone) throws Exception {
        validateCellPhone(phone);
        boolean result = false;

        MemberHelper memberHelper = new MemberHelper();
        MemberModel memberModel = new MemberModel();
        memberModel.setMobilePhone(phone);

        memberModel = memberHelper.getMemberDetail(memberModel);
        if (memberModel == null)
            result = true;
        else {
            if (!addMode)
                result = true;
        }
        if (!result)
            throw new Exception(errorMessage);

    }

    private void validateUniqueEmail(String email) throws Exception {
        validateEmail(email);
        MemberModel memberModel = new MemberModel();
        memberModel.setEmailAddress(email);

        MemberHelper hlp = new MemberHelper();
        memberModel = hlp.getMemberDetail(memberModel);
        boolean result = false;
        if (memberModel == null)
            result = true;
        else {
            if (!addMode)
                result = true;
        }
        if (!result)
            throw new InvalidValueException(errorMessage);

    }

    private void validateEmail(String emailAddress) throws Exception {
        EmailValidator validator = new EmailValidator(errorMessage);
        validator.validate(emailAddress);
        if (validator.isValid(emailAddress))
            throw new Exception(errorMessage);
    }

    public void validate(Object value) throws InvalidValueException {
        valid = isValid(value);
        if (!valid) {
            throw new InvalidValueException(errorMessage);
        }
    }

    public boolean isValid() {
        return valid;
    }

}
