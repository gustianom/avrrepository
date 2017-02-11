package com.tenma.common.util;

import com.tenma.tools.util.TenmaConstants;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/8/13
 * Time: 6:19 PM
 */
public interface Constants extends TenmaConstants {
    public static final String AWS_GGM_APPLICATION_NAME = "TenmaCourse";
    public static final String AWS_GGM_APPLICATION_PLATFORMNAME = "ApplicationPlatform";

    int DEF_DIALOG_PAGING_SIZE = 18;
    String DEF_WHAREHOUSE = "DEF_WHAREHOUSE";
    String DEF_RETUR_WHAREHOUSE = "DEF_RETUR_WHAREHOUSE";
    String OWN_PRODUCT = "ownProduct";
    String EFLYER_KEY = "3211.service";
    String MAX_METRO_COLOUMN = "maxMetroCol";
    String MAX_METRO_ROW = "maxMetroRow";
    String SMS_REMAINDER_PAYMENT = "smsRemainderPayment";

    //DO NOT CHANGE RESTRICTED LINE BELOW
    String ERROR_CALLBACK_VALIDATION_FAILED = "validationFailed";
    String ERROR_CALLBACK_NOTVALID = "notValid";
    String TASK_TRANSMITTED = "TASK_TRANSMITTED";
    String DATA_TRANSMITTED = "DATA_TRANSMITTED";
    String DATADTL_TRANSMITTED = "DATADTL_TRANSMITTED";

    String TENMA_APPLICATION = "TENMA_APPLICATION";
    String EDU_APPS_SESSION = "EDU_APPS_SESSION";
    String BROADCAST_SMS_FROM = "BROADCAST_SMS_FROM";
    String BROADCAST_SMS_MESSAGE = "BROADCAST_SMS_MESSAGE";
    Integer ID_MEMBER_FACEBOOK = 1;
    Integer ID_MEMBER_TWITTER = 2;
    String TEMPORARY_FILE_UPLOAD = "tempFileUpload";
    String COURSE_DEF_COMMUNITY_ID = "structureCourse";
    //END OF LINE RESTRICTED LINE

    /**
     * Parameter for CRUD result (CREATE, READ, UPDATE, DELETE)
     */

    Integer SYSTEM_PAGE_LOAD = 0;//"MAIN_PAGE";

    String MODIFIED_MODE = "MODIFIED_MODE";

    String UPLOAD_FILE_FOLDER = "UPLOAD_FILE";

    String LABEL_NEW = "default.new";
    String LABEL_DELETE = "default.delete";
    String LABEL_UPDATE = "default.update";
    String LABEL_PRINT = "default.print";
    String LABEL_SAVE = "default.save";
    String LABEL_SUBMIT = "default.submit";
    String LABEL_SAVEASDRAFT = "default.saveasdraft";
    String LABEL_CANCEL = "default.cancel";
    String LABEL_BACK = "default.back";
    String LABEL_SELECT = "default.select";
    String LABEL_COPY = "default.copy";
    String LABEL_VIEW = "default.view";
    String LABEL_ADD_LINE = "add.line";
    String LABEL_SENT = "default.sent";
    String LABEL_EXCEL = "default.excel";

//    public final String LIST_TITLE_ICON = "layouts/images/16/list16.png";
//    public final String ADD_TITLE_ICON = "layouts/images/16/022.png";
//    public final String UPDATE_TITLE_ICON = "layouts/images/16/018.png";
//    public final String ADD_ICON = "layouts/images/16/103.png";
//    public final String REFRESH_ICON = "layouts/images/16/123.png";
//    public final String DELETE_ICON = "layouts/images/16/101.png";
//    public final String PRINT_ICON = "layouts/images/16/062.png";
//    public final String SELECT_ICON = "layouts/images/apply.png";
//    public final String BACK_ICON = "layouts/images/16/118.png";
//    public final String SKIP_ICON = "layouts/images/16/135.png";
//    public final String SAVE_ICON = "layouts/images/16/096.png";

    public final String ICON_DETAIL = "layouts/images/16/049.png";
    String DOWNLOAD_CIRCLE_ICON = "layouts/images/metro/20/download.png";
    String ADD_CIRCLE_ICON = "layouts/images/metro/20/add.png";
    String UPDATE_CIRCLE_ICON = "layouts/images/metro/20/edit.png";
    String DELETE_CIRCLE_ICON = "layouts/images/metro/20/delete.png";
    String B2H_CIRCLE_ICON = "layouts/images/metro/16/back.png"; // Back To Home
    String ADD_ITEM_ICON = "layouts/images/16/add.png"; // Back To Home
    public final String PDF_ICON = "layouts/images/16/pdf.gif";
    public final String EXCEL_ICON = "layouts/images/16/excel2013.png";
    String LIST_TITLE_ICON = "layouts/images/metro/16/list.png";
    String ADD_TITLE_ICON = "layouts/images/metro/16/add.png";
    String UPDATE_TITLE_ICON = "layouts/images/metro/16/update.png";
    String ADD_ICON = "layouts/images/metro/16/add2.png";
    String RUPIAH_ICON = "layouts/images/metro/16/rupiah.png";
    String UPDATE_ICON = "layouts/images/metro/16/update.png";
    String REFRESH_ICON = "layouts/images/metro/16/refresh.png";
    String PAPER_ICON = "layouts/images/metro/16/paper.png";
    String DELETE_ICON = "layouts/images/metro/20/delete.png";
    String DEF_PICTURE_PROFILE = Constants.DELETE_ICON;
    String PRINT_ICON = "layouts/images/metro/16/print.png";
    String SEARCH_ICON = "layouts/images/metro/16/search.png";
    String ANNOUNCEMENT_ICON = "layouts/images/metro/16/announcement.png";
    String SELECT_ICON = "layouts/images/metro/16/check.png";
    String BACK_ICON = "layouts/images/metro/16/back.png";
    String SKIP_ICON = "layouts/images/metro/16/skip.png";
    String SAVE_ICON = "layouts/images/metro/16/save.png";
    String ADD_METRO_ICON = "layouts/images/metro/20/add.png";
    String UPDATE_METRO_ICON = "layouts/images/metro/20/edit.png";
    String DELETE_METRO_ICON = "layouts/images/metro/20/delete.png";
    String BACK_METRO_ICON = "layouts/images/metro/16/back.png";
    String DO_MOBILE_LOGIN = "tenmamobile.login";
    String SERVER_RESPONSE_TYPE = "SERVER_RESPONSE_TYPE";
    String SERVER_RESPONSE_ERROR = "ERROR-WEB-REQUEST-RESPONSE";
    String SERVER_RESPONSE_SUSSCESS = "SUCCESS-WEB-REQUEST-RESPONSE";
    String SERVER_RESPONSE_MESSAGE = "SERVER_RESPONSE_MESSAGE";
    String SERVER_RESPONSE_IFO = "INFO-WEB-REQUEST-RESPONSE";
    String QUERY = "query";
    String VIEWPAGE_SIZE = "pageSize";
    String TENMA_DEFAULT_EMAIL = "tenmaEmail";
    String TENMA_DEFAULT_MOBILE = "tenmaMobile";
    String LOGON_COUNTER = "logonCounter";
    String RATIO = "ratio";
    String TASK_ADD = "ADD";
    String TASK_UPDATE = "UPDATE";
    String TASK_DELETE = "DELETE";
    String CLIENT_INFO = "CLIENT_INFO";
    String ADD_FIELD = "ADD";
    String UPDATE_FIELD = "UPDATE";
    String DELETE_FIELD = "DELETE";
    String ADD_CHILD_FIELD = "ADD_CHILD";
    String userId = "userId";
    String CUSTOMER_SUBSCIBTION_TYPE = "CUSTSBT";
    String SELF_REGISTRATION_SEQ = "2013042416261701";
    String type2Search = "Type to search";
    String VIEWPAGE_CURRENCY_LIST = "/currency/currencyList?faces-redirect=true";
    String VIEWPAGE_REGISTRATION = "/main/succes?faces-redirect=true";
    String VIEWPAGE_MOBILEEMAILACTIVATION = "/main/mobileEmailActivation?faces-redirect=true";
    String VIEWPAGE_DASHBOARD = "/logon/authenticated?faces-redirect=true";
    String VIEW_PAGE_USER_ACTIVATION = "main/userVerification";
    String RESP_SYSTEM002 = "RESP_SYSTEM002";
    String LINKEMAIL = "linkEmail";
    String LINKINVITATION = "linkInvitationConfirm";
    String EMAIL$$ = "&email=";
    String CONFIRM = "c0nf1rM";
    String PROFILE_MYRESOURCE = "myresource";
    String APPLICATIONCODE = "applicationCode";
    String SMSACTIVATION1 = "smsActivation1";
    String SMSACTIVATION2 = "smsActivation2";
    String SUBJECTINVITATION = "invitation uangkita";
    String EMAILFROMINVITATION = "invitation@uangkita.share";
    String PASSDEFAULT = "password default anda :";
    String UPLOAD_DIR_FILE = "uploadFileAppServerDirectory";
    String DEPLOYMENT_PATH_TMP_IMAGE = "DeployImageTmpDirectory";
    String FIXEDASSET_UPLOAD = "fixAssetUpload";
    String UPLOAD_DIR_PROFILE_PICTURE = "uploadUserProfileDirectory";
    ///FIXEDASSET
    String FIXED_ASSET_DIR = "fixedAsset";
    String PROFILE_PICTURE = "profilepicture/";
    String DEFAULT_LOGON_PAGE = "../logon/logon.xhtml";
    String VIEWPAGE_SUCCES = "/main/succes?faces-redirect=true";
    String URL = "URL : www.uangkita.share";
    String MAIN_WINDOW = "MAIN_WINDOW";
    String IMAGE_DETAIL = "layouts/images/16/049.png";
    Integer ACTIVE = 1;
    Integer MAX_PROFILE_FILE_UPLOAD = 1024000;

    String CREATED_BY = "createdBy";

    //COMMUNITY_MEMBER
    Integer INACTIVE = 2;
    /**
     * Please use Constants.COMMUNITY_MEMBER_STATUS
     */
    @Deprecated
    Integer COMMUNITY_MEMBER_STATUS_JOINED = 0;
    /**
     * Please use Constants.COMMUNITY_MEMBER_STATUS
     */
    @Deprecated
    Integer COMMUNITY_MEMBER_STATUS_INVITE = 1;
    /**
     * Please use Constants.COMMUNITY_MEMBER_STATUS
     */
    @Deprecated
    Integer COMMUNITY_MEMBER_STATUS_WAITING = 4;
    /**
     * Please use Constants.COMMUNITY_MEMBER_STATUS
     */
    @Deprecated
    Integer COMMUNITY_MEMBER_STATUS_REJECTED = 2;
    /**
     * Please use Constants.COMMUNITY_MEMBER_STATUS
     */
    @Deprecated
    Integer COMMUNITY_MEMBER_STATUS_BLACKLIST = 3;
    /**
     * Please use Constants.COMMUNITY_MEMBER_STATUS
     */
    @Deprecated
    boolean COMMUNITY_MEMBER_EMPLOYEE_TEACHER = true;
    /**
     * Please use Constants.COMMUNITY_MEMBER_STATUS
     */
    @Deprecated
    boolean COMMUNITY_MEMBER_EMPLOYEE_NOT_TEACHER = false;
    int MAXIMUM_VALUE_INTEGER = 2147483647;
    int MINIMUM_VALUE_INTEGER = -2147483648;
    int DEFAULT_VALUE_INTEGER = 0;
    int ADULT_SEVENTEEN = 17;
    /**
     * use Constants.MEMBER_ROLE_TYPE
     */
    @Deprecated
    Integer COMMUNITY_MEMBER_KASTA_ADMIN = 0;
    /**
     * use into Constants.MEMBER_ROLE_TYPE
     */
    @Deprecated
    Integer COMMUNITY_MEMBER_KASTA_MEMBER = 1;
    String VERIFICATIONCODE_LIFE_TIME_PROPERTY = "verificationCode.lifeTime";
    int MENU_FAV = 1;
    String PARAMETER_CONSTANTS = "parameter";
    String TIME_FORMATTING = "timeFormatting";
    String INTERVAL_DAYS = "1 day";
    // SMS SUBSRIBTION Type
    String INTERVAL_WEEK = "1 week";
    String INTERVAL_MONTH = "1 month";
    String INTERVAL_QUARTER = "4 months";
    String INTERVAL_SEMESTER = "6 months";
    String INTERVAL_YEAR = "1 year";
    String REQ_COMMUNITY_PROFILE = "REQ_COMMUNITY_PROFILE";
    int MAX_YEAR_FOR_TIMESTAMP = 9999;

    enum SERVLET_COMMAND {
        DO_LOGOUT("tenma.logout"), DO_LOGIN("tenma.login"), DO_LOGIN_GOOGLE("google.login"),
        DO_MOBILE_ACTIVATION("activation.transmit"),
        DO_STUDENT_REGISTRATION("student.register"),
        DO_ACADEMY_PLANING("academy.register"),
        DO_SUCCESS_REGISTRATION("success.register"),
        DO_CONFIRM_COURSE_REGISTRATION(".cmr"),
        DO_CONFIRM_PERSONAL_REGISTRATION(".psr"),
        DO_CONFIRM_PARENT_REGISTRATION(".cpr"),
        DO_CONFIRM_PARENT_REGISTRATION_FROM_MOBILE(".cprm"), // confirm parent registration from mobile
        DO_CONFIRM_STUDENT_REGISTRATION(".std"),
        DO_CONFIRM_TEACHER_MOBILE_REGISTRATION(".trm"),
        DO_CONFIRM_STUDENT_MOBILE_REGISTRATION(".srm"),
        DO_DOWNLOAD(".download");
        private final String value;

        SERVLET_COMMAND(String methodValue) {
            this.value = methodValue;
        }

        public String getValue() {
            return value;
        }
    }

    enum IMG_COMMAND {
        STORY("story"),
        PROFILE("profile"),
        ORGANIZATION("orgprofile"),
        CLASS("class");
        private final String value;

        IMG_COMMAND(String methodValue) {
            this.value = methodValue;
        }

        public String getValue() {
            return value;
        }
    }

    enum BUTTON_CIRCLE_ICON {
        ADD("layouts/images/metro/20/add.png"),
        UPDATE("layouts/images/metro/20/edit.png"),
        SEARCH("layouts/images/metro/16/search.png"),
        DELETE("layouts/images/metro/20/delete.png");

        private final String value;

        BUTTON_CIRCLE_ICON(String methodValue) {
            this.value = methodValue;
        }

        public String getValue() {
            return value;
        }
    }

    enum STORY_COMMAND {
        STORY("story"),
        LIKE("like");
        private final String value;

        STORY_COMMAND(String methodValue) {
            this.value = methodValue;
        }

        public String getValue() {
            return value;
        }
    }

    enum CHART_COMMAND {
        STUDENT_REWARD("studentreward");
        private final String value;

        CHART_COMMAND(String methodValue) {
            this.value = methodValue;
        }

        public String getValue() {
            return value;
        }
    }


    enum MEMBER_ROLE_TYPE {
        ADMIN(2), MANAGEMENT(1), COMMON(0);

        int methodvalue = 0;

        MEMBER_ROLE_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

    enum POST_IT {
        POSTED(2), SKIPED(1);

        int methodvalue = 1;

        POST_IT(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }

    enum APPLICATION_PROP_GROUP_TYPE {
        COMMUNITY(3000), ALL_CLIENT(2000), SYSTEM(1000);

        int methodvalue = 3000;

        APPLICATION_PROP_GROUP_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(methodvalue);
        }
    }

    enum SORTING_TYPE {
        ASCENDING("ASCENDING"), DESCENDING("DESCENDING");
        String methodvalue = "ASCENDING";

        SORTING_TYPE(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

    }

    enum EMAIL_TYPE {
        ALL_REGISTRATION("REGALL"),
        REGISTER_USER("REG_EMPLOYEE"),
        NOTIFY_RESET_PASSWORD("RSTPASWDNOTIFY"),
        VERIFY_RESET_PASSWORD("RSTPASWDVERIFY"),
        VERIFY_CHANGE_EMAIL("CHGEMAILVERIFY"),
        VERIFY_CHANGE_PHONE("CHGPHONEVERIFY"),
        NOTIFY_SIGNUP_USER_VERIFICATION_CODE("SIGNUSRVERIFICD"),
        NOTIFY_SIGNUP_THANKYOU("SIGNUPTHANKYOU"),
        NOTIFY_SIGNUP_PRIVATE_THANKYOU("SIGNUPRIVATETHX"),
        NOTIFY_SIGNUP_PARENT_THANKYOU("SIGNUPARENTTHX");

        String methodvalue = "REGALL";

        EMAIL_TYPE(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }


    enum SMS_TYPE {
        ALL_REGISTRATION("REGALL"),
        NOTIFY_RESET_PASSWORD("RSTPASWDNOTIFY"),
        VERIFY_RESET_PASSWORD("RSTPASWDVERIFY"),
        NOTIFY_SIGNUP_USER_VERIFICATION_CODE("SIGNUSRVERIFICD"),
        NOTIFY_SIGNUP_THANKYOU("SIGNUPTHANKYOU");


        String methodvalue = "REGALL";

        SMS_TYPE(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }

    /**
     * FROM_DRAFT_MEMBER is new registered member which does not exist in all existing community
     * <br/>
     * FROM_EXISTING_M_MEMBER already registered in oneALREADY_MEMBER or more existing community member
     */
    public enum REGISTERED_MEMBER_TYPE {
        FROM_DRAFT_MEMBER(0), FROM_EXISTING_M_MEMBER(1);

        int methodvalue = 0;

        private REGISTERED_MEMBER_TYPE(int value) {
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

    // Company Calendar
    enum CALENDAR_TYPE {
        PUBLIC_HOLIDAY(100), COMPANY_HOLIDAY(200), COMPANY_EVENT(300), PERSONAL(400);
        int methodvalue = 100;

        CALENDAR_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }


    enum TASK_MEMBER_TYPE {
        PIC(100), CC(200), PUBLIC(300);

        int methodvalue = 100;

        TASK_MEMBER_TYPE(int value) {
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

    enum TASK_PRIORITY_TYPE {
        LOW(0), NORMAL(1), HIGHT(2);

        int methodvalue = 0;

        TASK_PRIORITY_TYPE(int value) {
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

    enum TODO_STATUS {
        NEW(0), DONE(1), POSTPONE(2), ARCHIEVE(3);

        int methodvalue = 1;

        TODO_STATUS(int value) {
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


    enum BARCODE_READER_CATEGORY {
        VOUCHER(1), STUDENT_ATTENDANCE(2);

        int methodvalue = 0;

        BARCODE_READER_CATEGORY(int value) {
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

    enum REPORT {
        TITLE("titleReport"), PRINTED_BY("titlePrintedBy");
        String methodvalue = "titleReport";

        REPORT(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

    }

    enum VERIFICATION_MEDIA {
        BY_PHONE("BY_PHONE"), BY_EMAIL("BY_EMAIL");

        String methodvalue = "BY_PHONE";

        VERIFICATION_MEDIA(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

    }

    /**
     * User Registration Level
     */
    enum USER_REGISTRATION_STATUS {
        NEW_REGISTER(0), ACCOUNT_ACTIVATED(1);

        int methodvalue = 0;

        USER_REGISTRATION_STATUS(int value) {
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
    enum USER_STATUS {
        ACTIVE(0), IN_ACTIVE(1), LOCKED(2);

        int methodvalue = 0;

        USER_STATUS(int value) {
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

    enum COMMUNITY_TYPE {
        PUBLIC(true), PRIVATE(false);

        boolean methodvalue = true;

        COMMUNITY_TYPE(boolean value) {
            methodvalue = value;
        }

        public final boolean getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }


    enum COMMUNITY_COMMERCIAL_TYPE {
        FREE_COMMUNITY("F"), PAID_COMMUNITY("P");

        String methodvalue = "F";

        COMMUNITY_COMMERCIAL_TYPE(String value) {
            methodvalue = value;
        }

        public final String getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }

    enum COMMUNITY_STRUCTURE_CATEGORY {
        PUBLIC("0"), PRIVATE("1");

        String methodvalue = "0";

        COMMUNITY_STRUCTURE_CATEGORY(String value) {
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

    enum SESSION_TYPE {
        PAGE("0"), FILE_UPLOAD("1");

        String methodvalue = "0";

        SESSION_TYPE(String value) {
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

    enum PRODUCT_CATALOG_PRICING_METHOD {
        INDEPENDENT_PRICE(40), AVERAGE_PRICE(50);

        private int methodvalue = 40;

        PRODUCT_CATALOG_PRICING_METHOD(int value) {
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


    enum COMMUNITY_STRUCTURE_TYPE {
        TENMA_SYSTEM("500100"), COMMUNITY("500200"), CORPORATE("500300"), PERSONAL("500400");

        String methodvalue = "500200";

        COMMUNITY_STRUCTURE_TYPE(String value) {
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


    enum TASK_STATUS {
        OPEN(0), CLOSE(1);
        int methodvalue = 0;

        TASK_STATUS(int value) {
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

    enum PRODUCT_CATALOG_MOVEMENT_TYPE {
        FIFO(60), LIFO(70), AVERAGE(80);

        private int value;

        PRODUCT_CATALOG_MOVEMENT_TYPE(int vl) {
            value = vl;
        }

        public int getValue() {
            return value;
        }
    }


    enum ANNOUNCEMENT_DESTINATION {
        SMS(0), EMAIL(1), FACEBOOK(2), TWITTER(3);
        int methodvalue = 0;

        ANNOUNCEMENT_DESTINATION(int value) {
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

    enum ANNOUNCEMENT_SEND {
        SENT(1), NOT_SENT(2);
        int methodvalue = 1;

        ANNOUNCEMENT_SEND(int value) {
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


    enum SOCIAL_MEDIA_TYPE {
        FACEBOOK(6), TWITTER(7), EMAIL(8);

        int methodvalue = 6;

        SOCIAL_MEDIA_TYPE(int value) {
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

    enum REMINDER_DUE_FACTOR {
        MINUTE(0), HOUR(1), DAY(2), WEEK(3), MONTH(4);

        int methodvalue = 0;

        REMINDER_DUE_FACTOR(int value) {
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

    enum REMINDER_MEDIA {
        DASHBOARD(0), SMS(1), EMAIL(2), ALL(3);

        int methodvalue = 0;

        REMINDER_MEDIA(int value) {
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

    enum NOTIFICATION_TYPE {
        REMINDER(0), TASK(1), APPROVAL_DIRECTORY_MEMBER(2);

        int methodvalue = 0;

        NOTIFICATION_TYPE(int value) {
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

    enum REMINDER_RECURRING_FACTOR {
        DAILY(1), WEEKLY(2), MONTHLY(3), YEARLY(4);

        int methodvalue = 0;

        REMINDER_RECURRING_FACTOR(int value) {
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


    //authority config mybattis

    enum REMINDER_RECURING_ENDTYPE {
        NEVER(1), AFTER_OCCURENCES(2), ON_SPECIFIED_DATE(3);

        int methodvalue = 0;

        REMINDER_RECURING_ENDTYPE(int value) {
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

    enum AUTHORITY_CONFIG {
        DEFAULT(1), READ_ONLY(2);

        int methodvalue = 1;

        AUTHORITY_CONFIG(int value) {
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
     * USE IT FOR THE FIRST LOGIN
     */
    enum TASK_SYSTEM_MANDATORY_STATUS {
        NORMAL("0"), MANDATORY("1");

        String methodvalue = "0";

        TASK_SYSTEM_MANDATORY_STATUS(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }

    enum PAYMENT_METHOD {
        DEBIT_CARD(1), CREDIT_CARD(2), CASH(3), TRANSFER(4);
        private int value;

        PAYMENT_METHOD(int val) {
            value = val;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }

    enum INVITATION_STATUS {
        DRAFT(0), SENT(1), CONFIRM(2);
        private int value;

        INVITATION_STATUS(int val) {
            value = val;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }

    enum INVITATION_LINK_STATUS {
        NOT_VALID(0), CANCEL(2), CONFIRM(1), USED(4), PASSWORD_CHANGE(3);
        private int value;

        INVITATION_LINK_STATUS(int val) {
            value = val;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }


    enum INVITATION_TYPE {
        EMAIL(0), MOBILE(1), ALL(2);
        private int value;

        INVITATION_TYPE(int val) {
            value = val;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }


    enum MIME_TYPES {
        JPG("image/jpeg"), JPE("image/jpeg"), JPEG("image/jpeg"), PNG("image/png"),
        EXCEL_STANDARD("application/vnd.ms-excel"), EXCEL_XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        PDF("application/pdf");
        private String value;

        MIME_TYPES(String val) {
            value = val;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }


    enum REPORT_MIME_TYPES {
        PDF("application/pdf"), TEXT_HTML("text/html");
        private String value = "text/html";

        REPORT_MIME_TYPES(String val) {
            value = val;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }


    enum REPORT_MIME_TYPES_FILE_EXTENTION {
        PDF("pdf"), TEXT_HTML("html");
        private String value = "html";

        REPORT_MIME_TYPES_FILE_EXTENTION(String val) {
            value = val;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }


    enum TASK_GENERATE_ID {
        CORPORATE_SETUP_ACCOUNT(1), //share.tenma.common.gui.main.signup.corporate.SetupAccount
        CORPORATE_SETUP_SYSTEM_ACCOUNT(2),
        COMMUNITY_INVITE_MEMBER(3),
        SMS_THRESHOLD_REACHED(4),
        SMS_BALANCE_EMPTY(5),
        APPROVAL_AUTHORITY_MEMBER(7); //using class share.tenma.bcm.gui.main.view.question.approval.AuthorityApproval

        private int methodvalue = 1;

        TASK_GENERATE_ID(int value) {
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

    enum COMMUNITY_MEMBER_GROUP_TYPE {
        STAFF(0), TEACHER(1), STUDENT(2), PARENT(3);

        int methodvalue = 1;

        COMMUNITY_MEMBER_GROUP_TYPE(int value) {
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

    enum COURSE_JOB_POSITION_DEFAULT {
        STAFF(1), TEACHER(2);

        int methodvalue = 1;

        COURSE_JOB_POSITION_DEFAULT(int value) {
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


    enum COMMUNITY_SUPPORT {
        NO(0), YES(1);

        private int methodvalue = 1;

        COMMUNITY_SUPPORT(int value) {
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

    enum MODIFY_MODE {
        ADD_MODE(1000), UPDATE_MODE(2000), VIEW_MODE(3000), LOOKUP_MODE(4000);

        private int methodvalue = 1000;

        MODIFY_MODE(int value) {
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


    enum TENMA_TRANSFER_MODE {
        UPLOAD_ONLY(1000), DOWNLOAD_ONLY(2000), UPLOAD_DOWNLOAD(3000);

        private int methodvalue = 1000;

        TENMA_TRANSFER_MODE(int value) {
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

    enum GENERAL_PROPERTY_GROUP_CODE {
        IDENTITY_TYPE("IDNTYPE"), FAMILY_GENDER("GNDRGRP"), FAMILY_MARITAL_STATUS("MRTSTS"),
        FAMILY_RELATION_TYPE("FAMREL"), EMPLOYEE_LEAVE_TYPE("LVTYPE"), FAMILY_BLOOD_GROUP("BLDGRP"),
        FAMILY_RELIGION_TYPE("RELIGI"), BANK_ACCOUNT_GROUP("GRPBANK"), EMPLOYMENT_TYPE_CONS("EMPLTYPE"),
        EMPLOYEE_STATUS("EMPSTS"), EMPLOYMENT_STATUS("EMPLYSTS"), WORKING_SCHEDULE_GROUP("WKSGRP"),
        WORKING_SCHEDULE("WKSCHE"), EDUCATION_LEVEL("EDULVL"), SKILL_LANG_FLUENCY_LEVEL("FLCLVL"),
        SKILL_GRADE_LEVEL("SKILLVL"), EMPLOYEE_LICENSE_TYPE("LCNTYP"), EMPLOYMENT_CATEGORY_CONS("EMPLCATG"),
        UDWREG("UDWREG"), WAREHOUSE_TYPE("WREHSTPE"), DIMENSION_TYPE("DMSTYPE"),
        RETURN_SO_DO_TYPE("RETTYPE"), SALES_GROUP("SALESGROUP"), STAFF_GROUP("STAFFGROUP"),
        SUPERADMIN_GROUP("SUPERADMINGROUP");


        String methodvalue = "IDNTYPE";

        GENERAL_PROPERTY_GROUP_CODE(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }


        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }

    enum PRODUCT_WINDOW_MODE {
        HIDEPRICE(0), VIEWALL(1), HIDEPRICEANDONLYONE(2), VIEWALLANDONLYONE(3);

        private int methodvalue = 0;

        PRODUCT_WINDOW_MODE(int value) {
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

    enum DEPRECIATION_METHOD {
        LINIER(0), DOUBLEDECLINE(1);

        private int methodvalue = 0;

        DEPRECIATION_METHOD(int value) {
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

    enum VIEW_REGISTRATION_MENU {
        COMMUNITY_ADMINISTRATOR("ADM"), COMMUNITY_USER("USR");

        private String methodvalue = "USR";

        VIEW_REGISTRATION_MENU(String value) {
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

    enum SET_PAGGING_VALUE {
        FIVE(5), TEN(10), FIFTEEN(15);

        int methodvalue = 5;

        SET_PAGGING_VALUE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(methodvalue);
        }
    }

    public static enum MENU_TYPE {
        SYSTEM(0), CLIENT(1), DASHBOARD(2);

        private int methodvalue = 0;

        private MENU_TYPE(int value) {
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

    public enum REGISTERED_SIGNUP_STRUCTUREID {
        UANGKITA("2015072314243401"), COURSE("2013011916072701"), EDU("2013011916072702");
        private String value = "";

        REGISTERED_SIGNUP_STRUCTUREID(String methodValue) {
            this.value = methodValue;
        }

        public String getValue() {
            return value;
        }
    }

    public enum REST_RESPONSE_CODE {
        OK(100), TICKET_NOT_VALID(302), FAILED(400), MEMBER_NOT_EXIST(401), INVALID_CREDENTIAL(402), SYSTEM_MAINTENANCE_MODE(403);


        int methodvalue = 100;

        private REST_RESPONSE_CODE(int value) {
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


    public enum TICKET_SUPPORT_STATUS {
        OPEN(1), REPLY(2), ON_PROGRESS(3), CLOSED(4), RE_OPEN(5), NOT_CLOSED(99);

        int methodvalue = 0;

        private TICKET_SUPPORT_STATUS(int value) {
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

    public enum HTML_INFO_CODE {
        OK_CONTINUE(200),
        BAD_REQUEST(400), NOT_AUTHORIZED(401), INVALID_CREDENTIAL(402), FORBIDDEN(403), PAGE_NOTFOUND(404),
        ACCOUNT_EXPIRED(600), ACCOUNT_MUST_CHANGE_PASSWD(601), ACCOUNT_LOCKED(602), SESSION_EXPIRED(603),
        INVALID_CAPTCHA_KEY(804);
        private int methodValue;

        HTML_INFO_CODE(int value) {
            this.methodValue = value;
        }

        public int getValue() {
            return this.methodValue;
        }


        @Override
        public String toString() {
            return String.valueOf(this.getValue());
        }
    }


    public enum SUBSCRIBTION_PERSONAL_TYPE {
        PARENT_SUBSCRIBE(10), PRIVATE_TEACHER_SUBSCRIBE(20), STUDENT_SUBSCRIBE(30);

        private int value;

        SUBSCRIBTION_PERSONAL_TYPE(int methodValue) {
            this.value = methodValue;
        }

        public int getValue() {
            return value;
        }
    }

    public enum DAY_INTEGER {
        MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);

        private int value;

        DAY_INTEGER(int methodValue) {
            this.value = methodValue;
        }

        public int getValue() {
            return value;
        }
    }

    enum TEMPLATE_TRANSACTION_TYPE {
        AUTO(1), MANUAL(2);

        int methodvalue = 1;

        TEMPLATE_TRANSACTION_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(methodvalue);
        }
    }

    enum TEMPLATE_TYPE {
        GNA(1), REG(2), OTHER(3);

        int methodvalue = 0;

        TEMPLATE_TYPE(int value) {
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

    public enum ITEM_CRUD {
        CREATE(1000), READ(3000), UPDATE(2000), DELETE(4);

        int methodvalue = 1;

        private ITEM_CRUD(int value) {
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


}