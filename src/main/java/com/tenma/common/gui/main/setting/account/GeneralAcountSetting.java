package com.tenma.common.gui.main.setting.account;

import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;
import com.tenma.common.gui.main.setting.account.pa.PersonalSetup;
import com.tenma.common.gui.main.setting.common.CommonSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustianom on 3/28/14.
 */
public class GeneralAcountSetting extends CommonSetting {
    public GeneralAcountSetting() {

        setTitle(param.getLabel("setting.generalAccount"));
        setSettingDescription(param.getLabel("setting.desc"));//Description of general account setting");
        initializeGeneralAccount();
    }

    private void initializeGeneralAccount() {
        List<UserModel> ls = new ArrayList<UserModel>();
        ls.add(TA.getCurrent().getClientInfo().getClientUserModel());
        addGroupSetting(new PersonalSetup(this, "Personal Setup"));
    }
}
