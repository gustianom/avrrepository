package com.tenma.common.gui.main.languages;

import com.tenma.common.TA;
import com.tenma.common.bean.languages.LanguagesHelper;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.gui.fieldfactory.LanguagesFormField;
import com.tenma.common.model.LanguagesModel;
import com.tenma.common.util.error.CrudCode;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Notification;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 19:32:27 ICT 2013
 */
public class LanguagesModify extends TenmaMasterFormModify {
    private LanguagesModel modifyModel;
    private LanguagesHelper helper = new LanguagesHelper();

    public LanguagesModify(TenmaPanel parentContainer, int modifyMode) throws Exception {
        super(parentContainer, modifyMode);
        setTitle("Languages.title");

        if (modifyMode == ADD_MODE) {
            modifyModel = new LanguagesModel();
            modifyModel.setLangId(0);
            modifyModel.setLangCode("");
            modifyModel.setLangName("");
            modifyModel.setLangImg("");
        } else {
            modifyModel = (LanguagesModel) ((TenmaPanel) parentContainer).getSelectedObject();

            LanguagesModel tmp = new LanguagesModel();
            tmp.setLangId(modifyModel.getLangId());
            modifyModel = helper.getLanguagesDetail(tmp);
            if (modifyModel == null)
                throw new Exception(String.valueOf(CrudCode.READ));
        }
        BeanItem<LanguagesModel> bitem = new BeanItem<LanguagesModel>(modifyModel);
        List visibleField = Arrays.asList(new String[]{"langId", "langCode"});

        LanguagesFormField formField = new LanguagesFormField(param);
        doPublishUI(bitem, visibleField, formField);
    }

    public void doCancel() {
        doBack2List();
    }

    public void doSave() {
        setAuditTrail(modifyModel);
        int res = -1;
        try {
            Locale local = new Locale(modifyModel.getLangCode());
            modifyModel.setLangName(local.getDisplayLanguage());
            modifyModel.setLangImg(modifyModel.getLangCode().trim() + ".png");
            if (ADD_MODE == getModifyMode())
                res = helper.insertNewLanguages(modifyModel);
            else
                res = helper.updateLanguages(modifyModel);

            doBack2List();
        } catch (Exception e) {
            commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.CREATE, e.getMessage(), param.getMessage("system.info.error"));
        }

    }

    private void doBack2List() {
        LanguagesList list = (LanguagesList) getParentContainer();
        TA.getCurrent().updateWorkingArea(list);
        list.refreshTable();
    }
}
