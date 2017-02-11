package com.tenma.common.util;

import com.tenma.auth.util.Converter;
import com.tenma.common.TA;
import com.tenma.common.bean.lang_label_value.LangLabelValueHelper;
import com.tenma.common.bean.systemproperty.SystemPropertyHelper;
import com.tenma.common.model.LangLabelValueModel;
import com.tenma.common.model.SystemPropertyModel;
import com.tenma.tools.util.TenmaConstants;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:05 AM
 */
public class Params implements Serializable {
    private static final String messagesFileName = "lang.uangkita_messages";
//    private static final String labelsFileName = "lang.uangkita_labels";

    private static Params params;
    private static HashMap mapOfMessages = new HashMap(1);
    private static HashMap mapOfLabels = new HashMap();
    private HashMap mapOfSystemProperty = null;
    private Locale local = null;

    private ResourceBundle messages;

    private String languageCode = "en";

    private long max = 0;
    private long total = 0;
    private long ctr = 0;
    private long ctrc = 0;

    private ConcurrentHashMap notDefineLabel = new ConcurrentHashMap();

    private Params(Locale locale) {
        this.local = locale;
        List list = null;
        SystemPropertyHelper helper = null;
        SystemPropertyModel systemProperty = null;
        try {

            messages = ResourceBundle.getBundle(messagesFileName);

            mapOfMessages.put("default", messages);
            languageCode = locale.getLanguage();

            System.out.println("*** languageCode = " + languageCode);

            helper = new SystemPropertyHelper();
            list = helper.listSystemProperty();
            int size = 0;
            if (list != null && list.size() > 0) {
                size = list.size();
                mapOfSystemProperty = new HashMap();
                for (int i = 0; i < size; i++) {
                    systemProperty = (SystemPropertyModel) list.get(i);
                    mapOfSystemProperty.put(systemProperty.getSystemPropertyName(), systemProperty.getSystemPropertyValue());
                }
            }

            //populateLanguageMaps(languageCode);

        } catch (Exception e) {
            throw new RuntimeException("Description.  Cause: " + e.getMessage(), e);
        } finally {
            list = null;
            helper = null;
            systemProperty = null;
        }
        notDefineLabel.clear();
    }

    public static synchronized Params getInstance() {
        return params;
    }

    public static synchronized Params getInstance(Locale local) {
        if (params == null) {
            params = new Params(local);
        }
        return params;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    private void populateLanguageMaps(String langCode) {
        langCode = langCode.trim();
        LangLabelValueHelper hlp = new LangLabelValueHelper();

        HashMap p = new HashMap();
        p.put("langCode", langCode.trim());

        List ls = hlp.getListRenderer(p, false);
        if (ls != null) {
            HashMap map = new HashMap();
            for (int i = 0; i < ls.size(); i++) {
                LangLabelValueModel m = (LangLabelValueModel) ls.get(i);

                StringBuffer key = new StringBuffer()
                        .append(m.getLabelName().trim().toLowerCase());

                if (m.getCommunityId() != null)
                    key.append(".").append(m.getCommunityId());

                map.put(key.toString(), m.getLabelValue());
            }
            mapOfLabels.put(langCode, map);
        }
    }

    private String retreiveLanguage(String communityId, String langCode, String labelName) {
        langCode = langCode.trim();
        LangLabelValueHelper hlp = new LangLabelValueHelper();
        LangLabelValueModel tmp = new LangLabelValueModel();
        tmp.setLangCode(langCode);
        tmp.setLabelName(labelName);
        if (communityId != null && communityId.trim().length() != 0)
            tmp.setCommunityId(communityId);

        String myLabelValue = labelName;

        tmp = hlp.getLangLabelValueDetail(tmp);
        if (tmp != null) {
            HashMap map = new HashMap();
            Object o = mapOfLabels.get(langCode);
            if (o == null)
                mapOfLabels.put(langCode, map);
            else
                map = (HashMap) o;

            map.put(tmp.getLabelName().trim().toLowerCase(), tmp.getLabelValue());

            myLabelValue = tmp.getLabelValue();
        }
        return myLabelValue;
    }

    public String getProperty(String propertyName) {
        String result = null;

//        Object o = mapOfSystemProperty.get(propertyName);
//        if (o != null)
//            result = String.valueOf(o);

        SystemPropertyModel model = new SystemPropertyModel();
        model.setSystemPropertyName(propertyName);
        SystemPropertyHelper helper = new SystemPropertyHelper();
        model = helper.getSystemPropertyDetail(model);
        if (model != null)
            result = model.getSystemPropertyValue();
        return result;
    }

    public void updateProperty(String propertyName, String propertyValue) {
        if (mapOfSystemProperty.containsKey(propertyName)) {
            mapOfSystemProperty.put(propertyName, propertyValue);
        }
    }

    public int getPageSize() {
        String result = (String) mapOfSystemProperty.get(TenmaConstants.VIEWPAGE_SIZE);
        return Integer.parseInt(result);
    }

    @Deprecated
    public synchronized String getMessage(String key) {
        try {
            if (mapOfMessages.containsKey(local)) {
                messages = (ResourceBundle) mapOfMessages.get(local);
            } else {
                messages = getLocaleMessage(local);
            }
            String result = messages.getString(key);
            return result;
        } catch (Exception e) {
            try {
                messages = (ResourceBundle) mapOfMessages.get("default");
                String result = messages.getString(key);
                return result;
            } catch (Exception e1) {
                System.out.println("------------------> key [" + key + "] NOT FOUND");
            }
        }

        return key;
    }


    public synchronized String getLabel(String key) {
        String result = "";
        if (key != null) {
            key = key.toLowerCase();

            String communityId = null;
            if (TA.getCurrent() != null)
                if (TA.getCurrent().getCommunityModel() != null)
                    if (TA.getCurrent().getCommunityModel().getCommunityId() != null)
                        if (TA.getCurrent().getCommunityModel().getCommunityId().trim().length() != 0)
                            communityId = TA.getCurrent().getCommunityModel().getCommunityId();

            String langCode = getLanguageCode();
            langCode = langCode.trim();
            String res = TenmaBootLabel.getInstance().getLabel(communityId, langCode, key);
            if (key.equals(res)) {
                result = getLabelDetail(communityId, langCode, key);
                if (result.equals(key) && result.contains(".")) {
                    // label is not setup
                    // put on memory and regulary write into text file
                    UnDefineLabel unDefineLabel = (UnDefineLabel) notDefineLabel.get(key);
                    if (unDefineLabel == null) {
                        unDefineLabel = new UnDefineLabel(Calendar.getInstance().getTime(), 1, key, langCode);
                        notDefineLabel.put(key, unDefineLabel);
//                        System.out.println("Undefine Label : "+key);
                    } else {
                        unDefineLabel.addEvent();
                    }
                }
            } else
                result = res;
        }
        return result;
    }

    public synchronized String getLabel(Converter.LANGUAGE_TYPE languageType, String key) {
        String langCode = Converter.LANGUAGE_CODE[languageType.getValue()];
        langCode = langCode.trim();

        String communityId = null;
        if (TA.getCurrent() != null)
            if (TA.getCurrent().getCommunityModel() != null)
                if (TA.getCurrent().getCommunityModel().getCommunityId() != null)
                    if (TA.getCurrent().getCommunityModel().getCommunityId().trim().length() != 0)
                        communityId = TA.getCurrent().getCommunityModel().getCommunityId();

        return getLabelDetail(communityId, langCode, key);
    }

    private synchronized String getLabelDetail(String communityId, String langCode, String key) {
        long start = Calendar.getInstance().getTimeInMillis();
        langCode = langCode.trim();
//        System.out.println(langCode + " Params.getLabel READIN KEY =>> " + key);
        String result = "";
        if ((key != null) && (key.trim().length() != 0)) {
            key = key.trim();
            if (key.toUpperCase().startsWith("STATIC."))
                result = key.substring(7);
            else {
                key = key.toLowerCase();
                result = key;
                Object objLang = mapOfLabels.get(langCode);

//            System.out.println(langCode + " -> " + objLang + " mapOfLabels = " + mapOfLabels);
                if (objLang != null) {
                    HashMap map = (HashMap) objLang;
                    Object o = map.get(key.trim());

                    //System.out.println(key + " o = " + o);
                    if (o != null) {
                        result = (String) o;
                        ctrc++;
                    } else
                        result = retreiveLanguage(communityId, langCode, key);
                } else
                    result = retreiveLanguage(communityId, langCode, key);

                if (result.trim().length() == 0) result = key;
                //System.out.println("Params.getLabel   " + key + " --- " + result);
            }
            long finish = Calendar.getInstance().getTimeInMillis();
            long time = finish - start;
            if (max < time) max = time;
            ctr++;

            total = total + time;
//            java.text.DecimalFormat df = new DecimalFormat("#00,000.##;(#,##0.00)");

//            System.out.println("LABEL CTR = " + df.format(ctr) + " # CTRC = " + df.format(ctrc) + " # " + " MAX = " + df.format(max) + " # AVG = " + df.format(total / ctr) + " #  TOT = " + df.format(total) + " # CUR = " + df.format(time) + " # AVGNC = " + df.format(total / (ctr - ctrc)) + " " + key + "");
        }
        return result;
    }


    private ResourceBundle getLocaleMessage(Locale locale) {
        ResourceBundle result;
        try {
            result = ResourceBundle.getBundle(messagesFileName + "_" + locale.toString());
            mapOfMessages.put(locale.toString(), messages);
        } catch (Exception e) {
            System.out.println("Failed to get error message file with locale = " + locale + "; then it will use the default error message");
            result = (ResourceBundle) mapOfMessages.get("default");
        }

        return result;
    }

    public synchronized ConcurrentHashMap getNotDefineLabel() {
        return notDefineLabel;
    }
}
