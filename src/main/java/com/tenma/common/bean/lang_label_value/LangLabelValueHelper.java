package com.tenma.common.bean.lang_label_value;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.lang_label_value.LangLabelValueDao;
import com.tenma.common.model.LangLabelValueModel;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.tools.util.TenmaConstants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:46:32 ICT 2013
 */
public class LangLabelValueHelper extends TenmaDaoHelper {
    public int insertNewLangLabelValue(LangLabelValueModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewLangLabelValue(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewLangLabelValue(SqlSession session, LangLabelValueModel model) throws Exception {
        int result = 0;
        LangLabelValueDao dao = new LangLabelValueDao(session);

        boolean isContinue2Insert = false;

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("LangLabelValueHelper.insertNewLangLabelValue");
        System.out.println("model.getLangId() = " + model.getLangId());
        System.out.println("model.getLabelValue() = " + model.getLabelValue());
        System.out.println("model.getLabelName() = " + model.getLabelName());
        if (model.getLabelName().toLowerCase().startsWith("menu."))
            isContinue2Insert = true;
        else {
            HashMap map = new HashMap();
            map.put("langId", model.getLangId());
            map.put("labelValue", model.getLabelValue());

            List<LangLabelValueModel> ls = dao.listAvailableLangLabelValueMap(map, false);
            isContinue2Insert = false;
            LangLabelValueModel tmpSel = null;
            if (ls != null && ls.size() != 0) {
                for (LangLabelValueModel m : ls) {
                    if (tmpSel == null) tmpSel = m;

                    if (m.getLabelName().toLowerCase().startsWith("menu.")) {
                        isContinue2Insert = true;
                        break;
                    }
                }

                if (!isContinue2Insert) {
                    StringBuffer err = new StringBuffer()
                            .append("Value has already used by other labelName [").append(tmpSel.getLangId()).append(",").append(tmpSel.getLabelName()).append("]");
                    throw new Exception(err.toString());
                }

            } else
                isContinue2Insert = true;
        }

        if (isContinue2Insert) {

            result = dao.insertLangLabelValue(model);
            if (result == 0)
                throw new Exception(TenmaConstants.ADD_FAILED);

        }
        return result;
    }

    public int updateLangLabelValue(LangLabelValueModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateLangLabelValue(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(TenmaConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateLangLabelValue(SqlSession session, LangLabelValueModel model) throws Exception {
        LangLabelValueDao dao = new LangLabelValueDao(session);
        return dao.updateLangLabelValue(model);
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LangLabelValueDao dao = new LangLabelValueDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<LangLabelValueModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LangLabelValueDao dao = new LangLabelValueDao(session);
            return dao.listAvailableLangLabelValueMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public LangLabelValueModel getLangLabelValueDetail(LangLabelValueModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LangLabelValueDao dao = new LangLabelValueDao(session);
            if (model.getCommunityId() != null && model.getCommunityId().trim().length() != 0) {
                List<LangLabelValueModel> ls = getLangLabelValueDetailByCommunity(model);
                if (ls != null) {
                    LangLabelValueModel communityLabel = null;
                    LangLabelValueModel defaultLabel = null;
                    for (LangLabelValueModel m : ls) {
                        if (m.getCommunityId() != null && m.getCommunityId().trim().length() != 0) {
                            if (model.getCommunityId().equals(m.getCommunityId()))
                                communityLabel = m;
                        } else
                            defaultLabel = m;
                    }
                    if (communityLabel == null)
                        communityLabel = defaultLabel;
                    return communityLabel;
                } else
                    return null;
            } else
                return dao.getLangLabelValue(model);
        } finally {
            session.close();
        }
    }

    private List<LangLabelValueModel> getLangLabelValueDetailByCommunity(LangLabelValueModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LangLabelValueDao dao = new LangLabelValueDao(session);
            return dao.getLangLabelValueDetailByCommunity(model);
        } finally {
            session.close();
        }
    }

    public int deleteLangLabelValue(LangLabelValueModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            LangLabelValueDao dao = new LangLabelValueDao(session);
            ret = dao.deleteLangLabelValue(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listLangLabelValue() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LangLabelValueDao dao = new LangLabelValueDao(session);
            return dao.listLangLabelValue();
        } finally {
            session.close();
        }
    }
}