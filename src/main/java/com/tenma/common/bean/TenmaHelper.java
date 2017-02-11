package com.tenma.common.bean;

import com.tenma.auth.util.db.DaoHelper;
import com.tenma.common.TA;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.TenmaStackTraceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by ndwijaya on 10/22/2015.
 * version 2.0 of TenmaHelper
 * - remove SystemProperty & GeneralProperty rare use on TenmaHelper
 * - add useCurrentCommunityID from TA source
 * - add userCurrentMemberId from TA source
 * - add addParameter
 * version 2.1
 * - set countTotalList(HashMap var1) method to private
 * - set getListRenderer(HashMap var1, boolean var2) method to private
 * - adding @NotNull validation
 * - adding Common Paging Helper
 * version 2.2
 * - build in countTotalList(HashMap var1) in test automation using Mokinto & JUnit
 */
public abstract class TenmaHelper<T> extends DaoHelper {
    protected final Logger logger;
    protected final HashMap map = new HashMap();
    protected boolean paging = false;
    protected boolean error = false;
    protected int start = 0;
    protected int limit = 0;

    public TenmaHelper() {
        super();
        logger = LoggerFactory.getLogger(TenmaHelper.class);
    }

    public T useCurrentCommunityId() {
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        return (T) this;
    }

    public T setCommunityId(String communityId) {
        map.put(Constants.COMMUNITY_ID, communityId);
        return (T) this;
    }

    public T useCurrentMemberId() {
        map.put("memberId", TA.getCurrent().getClientInfo().getClientMemberModel().getMemberId());
        return (T) this;
    }

    public T setMemberId(Integer memberId) {
        map.put("memberId", memberId);
        return (T) this;
    }

    public T sortAscending(String fieldname) {
        map.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.ASCENDING.getValue());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, fieldname);
        return (T) this;
    }

    public T sortDecending(String fieldname) {
        map.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.DESCENDING.getValue());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, fieldname);
        return (T) this;
    }

    public T addParameter(String fieldName, Object value) {
        map.put(fieldName, value);
        return (T) this;
    }

    @Deprecated
    public T withPaging() {
        paging = true;
        return (T) this;
    }

    public T withPaging(int start, int limit) {
        paging = true;
        this.start = start;
        this.limit = limit;
        return (T) this;
    }

    public Optional getRecordDetail() {
        return getListRenderer().stream().findFirst();
    }

    public int countTotalList() {
        try {
            return countTotalList(map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e.fillInStackTrace());
            return 0;
        }
    }

    public abstract int countTotalList(HashMap var1) throws Exception;

    public List getListRenderer() {
        List tmp = new ArrayList<>();
        try {
            if (paging && limit > 0) {
                map.put(Constants.RECORDSELECT_SKIP, start);
                map.put(Constants.RECORDSELECT_MAX, limit);
            }

            map.keySet().stream().forEach(key -> {
                        logger.debug(key + "->" + map.get(key));
                    }
            );

            tmp = getListRenderer(map, paging);
        } catch (Exception e) {
            TenmaStackTraceManager.getInstance().addNewStackTrace(30, e);
            error = true;
        }
        return tmp;
    }

    public abstract List getListRenderer(HashMap var1, boolean var2) throws Exception;

    /* can be call using reflection from tenmatest for all helper registered
     * require to override with specific unit test to call all the method inside the helper
     * challenge need to POC :
     *   - adding annotation on everyhelper @tenmatest("helpername")
      *  - adding helper method annotation @tenmatestid("methodname");
     * */
    public boolean testHelper() {
        return false; // if not implement the testUnit will reported as false testing.
    }
}
