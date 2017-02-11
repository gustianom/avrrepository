package com.tenma.tools.util;

import com.tenma.auth.util.AuthConstants;

/**
 * Created by gustianom on 2/5/14.
 */
public interface TenmaConstants extends AuthConstants {
    public final String VIEWPAGE_SIZE = "pageSize";
    public final String SECURED_LIMITATION_PACKAGE_CONSTRAINT = "2000";
    public final String OBJEC_NOT_EXIST = "5000";
    public final String ADD_ALREADY_EXIST = "7000";
    public final String ADD_FAILED = "8000";
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

    public final String TENMA_COMMUNITY_STRUCTURE_ID = "TENMASTRUCSYS001";
    public final String TENMA_COMMUNITY_ID = "TENMACOMMSYS0001";

    public final String PERSONAL_COMMUNITY_STRUCTURE_ID = "FAMSTRUCSYS001";
    public final String PERSONAL_COMMUNITY_ID = "FAMCOMMSYS0001";

    public final String DEFAULT_SINGUP_CORPORATE_ID = "CORP_DEFA_SIGNUP";
    public final String DEFAULT_SINGUP_COMMUNITY_ID = "COMM_DEFA_SIGNUP";

    public final String SYSTEM = "SYSTEM";

    public final String HEADER_SEARCH = "searchValue";
    public final String COMMUNITY_ID = "communityId";
    public final String PARENT_COMMUNITY_ID = "parentCommunityId";
    public final String COMMUNITY_STRUCTURE_ID = "communityStructure";
    public final String RECORDSELECT_SKIP = "skip";
    public final String RECORDSELECT_MAX = "max";
    public final String RECORDSELECT_SORTEDFIELD = "sortField";
    public final String RECORDSELECT_SORTORDER = "sortOrder";

    public final String DEFAULT_DOMAIN_NAME = "defaultDomain";

    enum APPLICATION_PROP_GROUP_TYPE {
        COMMUNITY(3000), ALL_CLIENT(2000), SYSTEM(1000);

        int methodvalue = 3000;

        private APPLICATION_PROP_GROUP_TYPE(int value) {
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

}