package com.tenma.common.gui.main.setting.account.pa;

import com.tenma.auth.model.MemberModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.logon.MemberHelper;
import com.tenma.common.TA;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.gui.factory.TenmaMasterFormLayout;
import com.tenma.common.gui.main.setting.common.CommonSetting;
import com.tenma.common.gui.main.setting.common.CommonSettingContent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

/**
 * Created by gustianom on 3/29/14.
 */
public class PersonalSetup extends CommonSettingContent {

    public PersonalSetup(CommonSetting mediaSetting, String title) {
        super(mediaSetting, title, false);

        MemberModel member = TA.getCurrent().getClientInfo().getClientMemberModel();
        UserModel m = TA.getCurrent().getClientInfo().getClientUserModel();


        addSettingContent(mediaSetting.getLabel("user.fullName"), member, UPDATE_TYPE.FULL_NAME.getValue(), false);
        addSettingContent(mediaSetting.getLabel("default.password"), m, UPDATE_TYPE.PASSWORD.getValue(), false);
        addSettingContent(mediaSetting.getLabel("signUp.mobilePhone"), member, UPDATE_TYPE.MOBILE_PHONE.getValue(), false);

    }

    @Override
    protected boolean doSave(Object modifyModel, MODIFY_MODE modifyMode, int uiType) throws Exception {
        if (UPDATE_TYPE.PASSWORD.getValue() == uiType)
            doChangePassword((UserModel) modifyModel);
        else if (UPDATE_TYPE.FULL_NAME.getValue() == uiType)
            doChangeFullName((MemberModel) modifyModel);
        else if (UPDATE_TYPE.MOBILE_PHONE.getValue() == uiType)
            doChangeMobilePhone((MemberModel) modifyModel);
        return true;
    }

    private boolean doChangeFullName(MemberModel model) throws Exception {
        MemberModel updatedModel = new MemberModel();
        updatedModel.setMemberId(model.getMemberId());
        updatedModel.setMemberName(model.getMemberName());

        TA.getCurrent().setAuditTrail(updatedModel);

        MemberHelper helper = new MemberHelper();
        int res = helper.updateMember(updatedModel);
        boolean isContinue = res != 0 ? true : false;
        return isContinue;
    }

    private boolean doChangeMobilePhone(MemberModel model) throws Exception {
        MemberModel updatedModel = new MemberModel();
        updatedModel.setMemberId(model.getMemberId());
        updatedModel.setMobilePhone(model.getMobilePhone());

        TA.getCurrent().setAuditTrail(updatedModel);

        MemberHelper helper = new MemberHelper();
        int res = helper.updateMember(updatedModel);
        boolean isContinue = res != 0 ? true : false;
        return isContinue;
    }

    private boolean doChangePassword(UserModel model) throws Exception {
        UserModel updatedModel = new UserModel();
        updatedModel.setUserId(model.getUserId());
        updatedModel.setPassword(model.getPassword());
        updatedModel.setConfirmpassword(model.getConfirmpassword());

        TA.getCurrent().setAuditTrail(updatedModel);
        boolean isContinue = true;

        if ((updatedModel.getPassword() != null) && (updatedModel.getPassword().trim().length() != 0))
            if ((updatedModel.getConfirmpassword() != null) && (updatedModel.getConfirmpassword().trim().length() != 0)) {
                if (updatedModel.getPassword().equals(updatedModel.getConfirmpassword())) {
                    UserHelper help = new UserHelper();
                    int res = help.updatePassword(updatedModel, TA.getCurrent().getAuthenticationSource());
                    isContinue = res != 0 ? true : false;
                } else
                    throw new Exception(TA.getCurrent().getParams().getMessage("error.confirmpasswordisnotequal"));
            }
        return isContinue;
    }

    @Override
    public TenmaMasterFormLayout preparingSocialMediaForm(Object model, int uiType) {
        TenmaMasterFormLayout formLayout = new TenmaMasterFormLayout();
        if (UPDATE_TYPE.FULL_NAME.getValue() == uiType) {
            MemberModel m = (MemberModel) model;
            BeanItem<MemberModel> beanItem = new BeanItem<MemberModel>(m);
            formLayout.setItemDataSource(beanItem);

            TextField fieldName = new TextField(propertyUtil.generateCaption("user.fullName"));
            fieldName.setMaxLength(100);
            fieldName.setRequired(true);

            formLayout.getFieldGroup().bind(fieldName, "memberName");
            formLayout.addComponent(fieldName);
        } else if (UPDATE_TYPE.PASSWORD.getValue() == uiType) {
            UserModel m = (UserModel) model;
            BeanItem<UserModel> beanItem = new BeanItem<UserModel>(m);
            formLayout.setItemDataSource(beanItem);

            PasswordField passwd = new PasswordField(propertyUtil.generateCaption("default.password"));
            PasswordField confirmpassword = new PasswordField(propertyUtil.generateCaption("default.confirmpassword"));

            formLayout.getFieldGroup().bind(passwd, "password");
            formLayout.getFieldGroup().bind(confirmpassword, "confirmpassword");

            formLayout.addComponent(passwd);
            formLayout.addComponent(confirmpassword);

        } else if (UPDATE_TYPE.MOBILE_PHONE.getValue() == uiType) {
            MemberModel m = (MemberModel) model;
            BeanItem<MemberModel> beanItem = new BeanItem<MemberModel>(m);
            formLayout.setItemDataSource(beanItem);

            TextField fieldName = new TextField(propertyUtil.generateCaption("signUp.mobilePhone"));
            fieldName.setMaxLength(100);
            fieldName.setRequired(true);

            formLayout.getFieldGroup().bind(fieldName, "mobilePhone");
            formLayout.addComponent(fieldName);
        }
        return formLayout;
    }


    @Override
    protected String getModuleTitle(Object model, int uiType) {
        String title = "";
        if (UPDATE_TYPE.MOBILE_PHONE.getValue() == uiType) {
            MemberModel m = (MemberModel) model;
            title = m.getMobilePhone();
        } else if (UPDATE_TYPE.PASSWORD.getValue() == uiType) {
            UserModel m = (UserModel) model;
            title = "********";
        } else if (UPDATE_TYPE.FULL_NAME.getValue() == uiType) {
            MemberModel m = (MemberModel) model;
            title = m.getMemberName();
        }
        return title;
    }

    @Override
    protected Object preparingNewDataModel() {
        return null;
    }

    private enum UPDATE_TYPE {
        FULL_NAME(1), PASSWORD(2), MOBILE_PHONE(3);

        int methodvalue = 1;

        private UPDATE_TYPE(int value) {
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
