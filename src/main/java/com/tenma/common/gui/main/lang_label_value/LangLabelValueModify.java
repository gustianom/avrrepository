package com.tenma.common.gui.main.lang_label_value;

import com.tenma.common.TA;
import com.tenma.common.bean.lang_label_value.LangLabelValueHelper;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.gui.fieldfactory.LangLabelValueFormField;
import com.tenma.common.model.LangLabelValueModel;
import com.tenma.common.util.error.CrudCode;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Notification;
import org.apache.ibatis.session.SqlSession;

import java.util.Arrays;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:46:33 ICT 2013
 */
public class LangLabelValueModify extends TenmaMasterFormModify {
    private LangLabelValueModel modifyModel;
    private LangLabelValueHelper helper = new LangLabelValueHelper();

    public LangLabelValueModify(TenmaPanel parentContainer, int modifyMode) throws Exception {
        super(parentContainer, modifyMode);
        setTitle("LangLabelValue.title");

        if (modifyMode == ADD_MODE) {
            modifyModel = new LangLabelValueModel();
            modifyModel.setLangId(0);
            modifyModel.setLabelId(0);
            modifyModel.setLabelValue("");
        } else {
            modifyModel = (LangLabelValueModel) ((TenmaPanel) parentContainer).getSelectedObject();

            LangLabelValueModel tmp = new LangLabelValueModel();
            tmp.setLangId(modifyModel.getLangId());
            modifyModel = helper.getLangLabelValueDetail(tmp);
            if (modifyModel == null)
                throw new Exception(String.valueOf(CrudCode.READ));
        }
        BeanItem<LangLabelValueModel> bitem = new BeanItem<LangLabelValueModel>(modifyModel);
        List visibleField = Arrays.asList(new String[]{"langId", "labelId", "labelValue"});

        LangLabelValueFormField formField = new LangLabelValueFormField(param);
        doPublishUI(bitem, visibleField, formField);
    }

    public void doCancel() {
        doBack2List();
    }

    public void doSave() {
        setAuditTrail(modifyModel);
        SqlSession session = helper.sqlSessionFactory.openSession();
        int res = -1;
        try {
            if (ADD_MODE == getModifyMode())
                res = helper.insertNewLangLabelValue(session, modifyModel);
            else
                res = helper.updateLangLabelValue(session, modifyModel);

            session.commit();
            doBack2List();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            commonMessageHandler(Notification.Type.ERROR_MESSAGE, ADD_MODE == getModifyMode() ? CrudCode.CREATE : CrudCode.UPDATE, e.getMessage(), param.getMessage("system.error"));
        } finally {
            session.close();
        }

    }

    private void doBack2List() {
        LangLabelValueList list = (LangLabelValueList) getParentContainer();
        TA.getCurrent().updateWorkingArea(list);
        list.refreshTable();
    }
}
