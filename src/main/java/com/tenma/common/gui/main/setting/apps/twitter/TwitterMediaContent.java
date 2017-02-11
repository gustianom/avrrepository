package com.tenma.common.gui.main.setting.apps.twitter;

import com.tenma.common.TA;
import com.tenma.common.bean.communitysosmed.CommunitySosmedHelper;
import com.tenma.common.gui.factory.TenmaMasterFormLayout;
import com.tenma.common.gui.main.setting.apps.SocialMediaSetting;
import com.tenma.common.gui.main.setting.common.CommonSettingContent;
import com.tenma.common.util.Constants;
import com.tenma.model.common.CommunitySosmedModel;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.TextField;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 3/28/14.
 */
public class TwitterMediaContent extends CommonSettingContent {


    public TwitterMediaContent(SocialMediaSetting mediaSetting, String title, List<CommunitySosmedModel> listMedia) {
        super(mediaSetting, title, true);
        initializeDataList(listMedia);
    }

    private void initializeDataList(List<CommunitySosmedModel> listMedia) {
        if (listMedia != null)
            for (CommunitySosmedModel model : listMedia)
                addSettingContent(model, 0, true);
    }

    @Override
    protected boolean doSave(Object modifyModel, CommonSettingContent.MODIFY_MODE modifyMode, int uiType) throws Exception {
        boolean iscontinue = false;
        if (MODIFY_MODE.ADD_NEW.equals(modifyMode))
            iscontinue = addNewTwitterItem(modifyModel);
        else if (MODIFY_MODE.UPDATE.equals(modifyMode))
            iscontinue = updateTwitterItem(modifyModel);
        return iscontinue;
    }

    private boolean updateTwitterItem(Object modifyModel) throws Exception {
        CommunitySosmedModel model = (CommunitySosmedModel) modifyModel;
        CommunitySosmedHelper hlp = new CommunitySosmedHelper();

        CommunitySosmedModel updatedModel = new CommunitySosmedModel();
        updatedModel.setSosmedName(model.getSosmedName());
        updatedModel.setSosmedAccessSecreet(model.getSosmedAccessSecreet());
        updatedModel.setSosmedId(model.getSosmedId());
        updatedModel.setCommunityId(model.getCommunityId());
        updatedModel.setSosmedType(model.getSosmedType());

        TA.getCurrent().setAuditTrail(updatedModel);
        int res = hlp.updateCommunitySosmed(updatedModel);
        return res != 0 ? true : false;
    }

    private boolean addNewTwitterItem(Object modifyModel) throws Exception {
        CommunitySosmedModel model = (CommunitySosmedModel) modifyModel;
        CommunitySosmedHelper hlp = new CommunitySosmedHelper();
        CommunitySosmedModel updatedModel = new CommunitySosmedModel();
        updatedModel.setSosmedName(model.getSosmedName());
        updatedModel.setSosmedAccessSecreet(model.getSosmedAccessSecreet());
        updatedModel.setSosmedAccessSecreet(model.getSosmedAccessSecreet());
        updatedModel.setSosmedAccessToken(model.getSosmedAccessToken());
        updatedModel.setSosmedConsumerKey(model.getSosmedConsumerKey());
        updatedModel.setSosmedConsumerSecreet(model.getSosmedConsumerSecreet());
        updatedModel.setSosmedId(model.getSosmedId());
        updatedModel.setCommunityId(model.getCommunityId());
        updatedModel.setSosmedType(model.getSosmedType());

        TA.getCurrent().setAuditTrail(updatedModel);
        int res = hlp.insertNewCommunitySosmed(updatedModel);
        return res != 0 ? true : false;
    }

    @Override
    public TenmaMasterFormLayout preparingSocialMediaForm(Object model, int uiType) {
        CommunitySosmedModel m = (CommunitySosmedModel) model;

        BeanItem<CommunitySosmedModel> beanItem = new BeanItem<CommunitySosmedModel>(m);
        TenmaMasterFormLayout formLayout = new TenmaMasterFormLayout();
        formLayout.setItemDataSource(beanItem);

        TextField sosmedName = new TextField(propertyUtil.generateCaption("default.name"));
        TextField sosmedAccessSecreet = new TextField(propertyUtil.generateCaption("twitter.accessSecreet"));
        TextField sosmedAccessToken = new TextField(propertyUtil.generateCaption("twitter.accessToken"));
        TextField sosmedConsumerSecreet = new TextField(propertyUtil.generateCaption("twitter.consumerSecreet"));
        TextField sosmedConsumerKey = new TextField(propertyUtil.generateCaption("twitter.consumerKey"));

        sosmedName.setRequired(true);
        sosmedAccessSecreet.setRequired(true);
        sosmedAccessToken.setRequired(true);
        sosmedConsumerSecreet.setRequired(true);
        sosmedConsumerKey.setRequired(true);

        formLayout.getFieldGroup().bind(sosmedName, "sosmedName");
        formLayout.getFieldGroup().bind(sosmedConsumerKey, "sosmedConsumerKey");
        formLayout.getFieldGroup().bind(sosmedConsumerSecreet, "sosmedConsumerSecreet");
        formLayout.getFieldGroup().bind(sosmedAccessToken, "sosmedAccessToken");
        formLayout.getFieldGroup().bind(sosmedAccessSecreet, "sosmedAccessSecreet");

        formLayout.addComponent(sosmedName);
        formLayout.addComponent(sosmedConsumerKey);
        formLayout.addComponent(sosmedConsumerSecreet);
        formLayout.addComponent(sosmedAccessToken);
        formLayout.addComponent(sosmedAccessSecreet);
        return formLayout;
    }

    @Override
    protected String getModuleTitle(Object model, int uiType) {
        CommunitySosmedModel c = (CommunitySosmedModel) model;
        return c.getSosmedName();
    }

    @Override
    protected CommunitySosmedModel preparingNewDataModel() {
        CommunitySosmedModel modifyModel = new CommunitySosmedModel();
        modifyModel.setSosmedId(0);
        modifyModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        modifyModel.setSosmedType(Constants.SOCIAL_MEDIA_TYPE.TWITTER.getValue());

        modifyModel.setSosmedPageid("");
        modifyModel.setSosmedAppid("");
        modifyModel.setSosmedName("");
        modifyModel.setSosmedAccessSecreet("");
        modifyModel.setSosmedAccessToken("");
        modifyModel.setSosmedConsumerSecreet("");
        modifyModel.setSosmedConsumerKey("");
        return modifyModel;
    }

    @Override
    public void doRefreshContent() {
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put("sosmedType", Constants.SOCIAL_MEDIA_TYPE.TWITTER.getValue());

        CommunitySosmedHelper help = new CommunitySosmedHelper();
        List<CommunitySosmedModel> lsTw = help.getListRenderer(map, false);
        initializeDataList(lsTw);
    }

    @Override
    public boolean doDelete(Object model) throws Exception {
        CommunitySosmedModel c = (CommunitySosmedModel) model;

        CommunitySosmedModel tmp = new CommunitySosmedModel();
        tmp.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        tmp.setSosmedId(c.getSosmedId());
        TA.getCurrent().setAuditTrail(tmp);

        CommunitySosmedHelper help = new CommunitySosmedHelper();
        int res = help.deleteCommunitySosmed(tmp);
        boolean isContinue = (res != 0);
        return isContinue;
    }
}
