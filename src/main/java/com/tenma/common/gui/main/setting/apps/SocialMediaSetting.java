package com.tenma.common.gui.main.setting.apps;

import com.tenma.common.TA;
import com.tenma.common.bean.communitysosmed.CommunitySosmedHelper;
import com.tenma.common.gui.main.setting.apps.facebook.FacebookMediaContent;
import com.tenma.common.gui.main.setting.apps.twitter.TwitterMediaContent;
import com.tenma.common.gui.main.setting.common.CommonSetting;
import com.tenma.common.util.Constants;
import com.tenma.model.common.CommunitySosmedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 3/28/14.
 */
public class SocialMediaSetting extends CommonSetting {

    public SocialMediaSetting() {

        setTitle(param.getLabel("setting.socialMedia"));
        setSettingDescription("Description of social media setting");

        initializeSocialMediaGroup();
    }

    private void initializeSocialMediaGroup() {
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());

        CommunitySosmedHelper help = new CommunitySosmedHelper();
        List<CommunitySosmedModel> lsFb = new ArrayList<CommunitySosmedModel>();
        List<CommunitySosmedModel> lsTw = new ArrayList<CommunitySosmedModel>();

        List<CommunitySosmedModel> ls = help.getListRenderer(map, false);
        if (ls != null)
            for (CommunitySosmedModel model : ls)
                if (Constants.SOCIAL_MEDIA_TYPE.FACEBOOK.getValue() == model.getSosmedType())
                    lsFb.add(model);
                else lsTw.add(model);


        addGroupSetting(new FacebookMediaContent(this, "Facebook", lsFb));
        addGroupSetting(new TwitterMediaContent(this, "Twitter", lsTw));
    }


}
