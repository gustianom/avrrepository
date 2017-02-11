package com.tenma.auth.util;

/**
 * Created by IntelliJ IDEA.
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AuthConstants {

    String DO_LOGOUT = "tenma.logout";
    String DO_LOGIN = "tenma.login";

    String SERVER_RESPONSE_TYPE = "SERVER_RESPONSE_TYPE";
    String SERVER_RESPONSE_ERROR = "ERROR-WEB-REQUEST-RESPONSE";
    String SERVER_RESPONSE_SUSSCESS = "SUCCESS-WEB-REQUEST-RESPONSE";
    String SERVER_RESPONSE_MESSAGE = "SERVER_RESPONSE_MESSAGE";
    String SERVER_RESPONSE_IFO = "INFO-WEB-REQUEST-RESPONSE";
    String USER_ID = "userId";
    String USER_NAME = "userName";
    String EMAIL = "emailId";
    String MOBILE = "phoneId";
    public final String ADD_ALREADY_EXIST = "7000";
    public final String ADD_FAILED = "8000";
    public final String ADD_FAILED_FIELD_PHONE_OR_EMAIL_MUSTNOT_EMPTY = "8100";
    public final String UPDATE_FAILED = "9000";
    public final String DELETE_FAILED = "6000";
    public final String DELETE_FAILED_HAVE_REFERENCE = "6100";
    public final String DATE_START_IS_BEFORE_FAILED = "5110";
    public final String DATE_START_IS_AFTER_FAILED = "5120";

    public final String DATE_END_IS_BEFORE_FAILED = "5210";
    public final String DATE_END_IS_AFTER_FAILED = "5220";
    public final String DATE_END_MUST_HIGHER = "5230";

    public final String DATE_IS_BEFORE_FAILED = "5310";
    public final String DATE_IS_AFTER_FAILED = "5320";
    public final String HEADER_SEARCH = "searchValue";
    public final String COMMUNITY_ID = "communityId";
    public final String PARENT_COMMUNITY_ID = "parentCommunityId";
    public final String COMMUNITY_STRUCTURE_ID = "communityStructure";
    public final String RECORDSELECT_SKIP = "skip";
    public final String RECORDSELECT_MAX = "max";
    public final String RECORDSELECT_SORTEDFIELD = "sortField";
    public final String RECORDSELECT_SORTORDER = "sortOrder";

    public final String DEFAULT_DOMAIN_NAME = "defaultDomain";
    public final String SYSTEM = "SYSTEM";

    /**
     * User Registration Level
     */
    public enum USER_STATUS {
        ACTIVE(0), IN_ACTIVE(1), LOCKED(2);

        int methodvalue = 0;

        private USER_STATUS(int value) {
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

    /**
     * User Registration Level
     */
    public enum USER_REGISTRATION_STATUS {
        NEW_REGISTER(0), ACCOUNT_ACTIVATED(1);

        int methodvalue = 0;

        private USER_REGISTRATION_STATUS(int value) {
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

    public enum DEFAULT_SYSTEM_OPTION {
        NO(0), YES(1);

        int methodvalue = 0;

        private DEFAULT_SYSTEM_OPTION(int value) {
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

    public enum TENMA_USER_TYPE {
        SUPER(83672797), USER_SYSTEM(83662797), CLIENT_SYSTEM(83652797);

        int methodvalue = 0;

        private TENMA_USER_TYPE(int value) {
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

    public enum COMMUNITY_MEMBER_STATUS {
        REGISTERED_BY_THEMSELVES(7),
        PENDING(1), ACTIVE(2), PASSIVE(3), BLOCK(4), SUSPENDED(5), UNREGISTERED(6), ALL_MEMBER(99);

        int methodvalue = 1;

        private COMMUNITY_MEMBER_STATUS(int value) {
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

    public enum AUTHORITY_CONFIG {
        DEFAULT(1), READ_ONLY(2);

        int methodvalue = 1;

        private AUTHORITY_CONFIG(int value) {
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

    public enum MEMBER_ROLE_TYPE {
        ADMIN(2), MANAGEMENT(1), COMMON(0);

        int methodvalue = 0;

        MEMBER_ROLE_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }
}
