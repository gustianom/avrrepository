package com.tenma.common.bean;

import com.tenma.auth.util.db.DaoHelper;
import com.tenma.common.bean.applicationproperty.ApplicationPropertyHelper;
import com.tenma.common.bean.generalProperty.GeneralPropertyHelper;
import com.tenma.common.model.ApplicationPropertyModel;
import com.tenma.common.model.GeneralPropertyModel;
import com.tenma.tools.util.TenmaConstants;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 08/02/15.
 */
public abstract class TenmaDaoHelper extends DaoHelper {

    /**
     * List Items if applicationPropertyGroup
     *
     * @param appGroupName -- the default group type is 2000 (ALL DATA provided to Client)
     * @return
     */
    public List getListApplicationPropertyByGroupItem(String appGroupName) {
        List list = null;
        ApplicationPropertyHelper HLP = null;
        try {
            HLP = new ApplicationPropertyHelper();
            list = HLP.listApplicationPropertyItemsByGroup(appGroupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * List Items if applicationPropertyGroup
     *
     * @param appGroupName
     * @param groupType    type of groups, the value 1000 for SYSTEM, 2000 (ALL DATA provided to Client), 3000 others may belong to specific comminityId
     * @return
     */
    public List<ApplicationPropertyModel> getListApplicationPropertyByGroupItem(String appGroupName, Integer groupType, String communityId) {
        List list = null;
        ApplicationPropertyHelper HLP = null;
        try {
            HLP = new ApplicationPropertyHelper();
            HashMap map = new HashMap();
            map.put("appGrpCd", appGroupName);
            if (groupType == null)
                groupType = TenmaConstants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT.getValue();
            map.put("appGroupType", groupType);

            if (communityId != null)
                map.put(TenmaConstants.COMMUNITY_ID, communityId);

            map.put("sortField", "fieldOrder");

            list = HLP.getListApplicationPropertyByGroupItem(map, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * List Items if applicationPropertyGroup
     *
     * @param map
     * @return
     */
    public List<ApplicationPropertyModel> getListApplicationPropertyByGroupItem(HashMap map, boolean isNavigated) {
        List list = null;
        ApplicationPropertyHelper HLP = null;
        try {
            HLP = new ApplicationPropertyHelper();
            list = HLP.getListRenderer(map, isNavigated);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * List Items if generalPropertyGroup
     *
     * @param appGroupName -- the default group type is 2000 (ALL DATA provided to Client)
     * @return
     */
    public List getListGeneralPropertyByGroupItem(String appGroupName) {
        List list = null;
        GeneralPropertyHelper HLP = null;
        try {
            HLP = new GeneralPropertyHelper();
            list = HLP.listGeneralPropertyItemsByGroup(appGroupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * List Items if generalPropertyGroup
     *
     * @param appGroupName
     * @param groupType    type of groups, the value 1000 for SYSTEM, 2000 (ALL DATA provided to Client), 3000 others may belong to specific comminityId
     * @return
     */
    public List<GeneralPropertyModel> getListGeneralPropertyByGroupItem(String appGroupName, Integer groupType, String communityId) {
        List list = null;
        GeneralPropertyHelper HLP = null;
        try {
            HLP = new GeneralPropertyHelper();
            HashMap map = new HashMap();
            map.put("groupCode", appGroupName);
            if (groupType == null)
                groupType = TenmaConstants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT.getValue();
            map.put("groupType", groupType);

            if (communityId != null)
                map.put(TenmaConstants.COMMUNITY_ID, communityId);

            map.put("sortField", "fieldOrder");

            list = HLP.getListGeneralPropertyByGroupItem(map, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * List Items if generalPropertyGroup
     *
     * @param map
     * @return
     */
    public List<GeneralPropertyModel> getListGeneralPropertyByGroupItem(HashMap map, boolean isNavigated) {
        List list = null;
        GeneralPropertyHelper HLP = null;
        try {
            HLP = new GeneralPropertyHelper();
            list = HLP.getListRenderer(map, isNavigated);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}