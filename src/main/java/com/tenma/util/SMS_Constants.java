package com.tenma.util;

import com.tenma.tools.util.TenmaConstants;

/**
 * Created by gustianom on 08/05/14.
 */
public interface SMS_Constants extends TenmaConstants {
    /**
     * is SMS recepient Type:
     * </p> 0 for local/national phone number
     * </p> 1 for INTERNATIONAL PHONE NUMBER
     */
    public enum SMS_RECIPIENT_NUMBER_TYPE {
        INDONESIAN_NUMBER(0), INTERNATIONAL_NUMBER(1);
        int methodvalue = 0;

        private SMS_RECIPIENT_NUMBER_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

    /**
     * is SMS use for:
     * </p> 0 for SYSTEM Notification
     * </p> 1 for normal usage
     */
    public enum SMS_SOURCE_TYPE {
        SYSTEM(0), NORMAL(1);
        int methodvalue = 1;

        private SMS_SOURCE_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

    /**
     * <li>0 -> NORMAL (STANDARD SUBSRIBTION)</li>
     * <li>1 -> PROMO (noT SUBSCRIBED BUT GIVEN PROMO BALANCE > NO TOPUP AND BALANCE NOTIFICATION)</li>
     * <li>2 -> NO SMS at all AND NO NOTIFICATION RELATED TO SMS</li>
     */
    public enum SMS_PROFILE_SUBSCRIBTION_TYPE {
        NORMAL(0), PROMO(1), NO_SMS(2);
        int methodvalue = 0;

        private SMS_PROFILE_SUBSCRIBTION_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

    // SMS Payment Type
    public enum SMS_PROFILE_PAYMENT_TYPE {
        PREPAID(0), POSTPAID(1);
        int methodvalue = 0;

        private SMS_PROFILE_PAYMENT_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

    public enum SMS_PROFILE_DELIVERY_STATUS {
        SEND(0), PROVIDER_UNKNOWN_NUMBER(10),
        PROVIDER_DELIVERED(20), PROVIDER_FAILED_SEND(30);

        int methodvalue = 0;

        private SMS_PROFILE_DELIVERY_STATUS(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

    public enum DEFAULT_SMS_SIGNUP_SYSTEM_BALANCE_ID {
        COMMUNITY("comunity.sms.balance.default"), CORPORATE("corporate.sms.balance.default");

        String methodvalue = "comunity.sms.default";

        private DEFAULT_SMS_SIGNUP_SYSTEM_BALANCE_ID(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }

    public enum SMS_BALANCE_CARRY_FORWARD {
        NO(0), YES_FOR_TOP_UP_ONLY(1), ALL(2);
        int methodvalue = 0;

        private SMS_BALANCE_CARRY_FORWARD(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }

    public enum SMS_PROFILE_UPDATE_MODE {
        MANUAL(0), AUTOMATIC(1);
        int methodvalue = 0;

        private SMS_PROFILE_UPDATE_MODE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }

    public enum SMS_RESPONSE_CODE {
        SMS_SERVICES_NOT_FOUND(-10), DELIVERED(0), DELIVERED_AND_TRESHOLD_REACHED(1), FAILED_COMMUNITY_NO_SMS_PROFILE(10), FAILED_COMMUNITY_SMS_BALANCE_EMPTY(20), FAILED_COMMUNITY_SMS_BALANCE_EMPTY_AND_REACH_CREDIT_LIMIT(30);

        private int methodvalue = 10;

        private SMS_RESPONSE_CODE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

    public enum SMS_PROFILE_CODE {
        AVAILABLE_FOR_DELIVERY(0), AVAILABLE_FOR_DELIVERY_SMS_THRESHOLD_REACHED(1), FAILED_COMMUNITY_NO_SMS_PROFILE(10), FAILED_COMMUNITY_SMS_BALANCE_EMPTY(20), FAILED_COMMUNITY_SMS_BALANCE_EMPTY_AND_REACH_CREDIT_LIMIT(30);

        private int methodvalue = 10;

        private SMS_PROFILE_CODE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

}
