package com.tenma.common.gui.main.generalappprop;

import com.tenma.common.TA;
import com.tenma.common.bean.generalProperty.GeneralPropertyHelper;
import com.tenma.common.model.GeneralPropertyModel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.vaadin.ui.NativeSelect;

import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/4/13
 * Time: 4:18 AM
 */
public class GeneralPropertyUtil {
    public static String GENERAL_CREATE = "generalCreate";
    private Params param;

    public GeneralPropertyUtil() {
        param = TA.getCurrent().getParams();
    }

    public final String getPropertyLabel(Integer propertyId, Constants.GENERAL_PROPERTY_GROUP_CODE groupCode) {
        return getPropertyLabel(propertyId, groupCode.getValue());
    }

    public final String getPropertyLabel(Integer propertyId, String groupCode) {
        GeneralPropertyHelper helper = new GeneralPropertyHelper();
        GeneralPropertyModel pro = new GeneralPropertyModel();
        pro.setGroupCode(groupCode);
        pro.setPropertyId(propertyId);
        pro = helper.getGeneralPropertyDetail(pro);
        String lbl = "-";
        if (pro != null)
            lbl = pro.getPropertyLabel();
        return lbl;
    }

    public final String generateCaption(String label) {
        StringBuffer buf = new StringBuffer().append(param.getLabel(label)).append(" : ");
        return buf.toString();
    }

    public final NativeSelect generateNativeSelect(Constants.GENERAL_PROPERTY_GROUP_CODE groupCode, boolean isRequired, boolean allowAddNewItem, String communityId) {
        NativeSelect bx = new NativeSelect();
        bx.setId(groupCode.getValue());
//        bx.setRequired(isRequired);
        return reGenerateNativeSelect(bx, groupCode, allowAddNewItem, communityId);
    }

    public final NativeSelect generateNativeSelect(String caption, Constants.GENERAL_PROPERTY_GROUP_CODE groupCode, String communityId, boolean isRequired, boolean allowAddNew) {
        NativeSelect bx = new NativeSelect();
        if ((caption != null) && (caption.trim().length() != 0))
            bx.setCaption(caption);
        bx.setId(groupCode.getValue());
        bx.setRequired(isRequired);
        return reGenerateNativeSelect(bx, groupCode, allowAddNew, communityId);
    }

    public final NativeSelect reGenerateNativeSelect(NativeSelect bx, Constants.GENERAL_PROPERTY_GROUP_CODE groupCode, boolean allowAddNewItem, String communityId) {
        return reGenerateNativeSelect(bx, groupCode.getValue(), allowAddNewItem, communityId);

    }

    public final NativeSelect reGenerateNativeSelect(NativeSelect bx, String groupCode, boolean allowAddNewItem, String communityId) {
        if (bx == null) bx = new NativeSelect();
        else
            bx.removeAllItems();

        Constants.APPLICATION_PROP_GROUP_TYPE groupType = getGeneralPropertyGroupType(groupCode);
        GeneralPropertyHelper helper = new GeneralPropertyHelper();
        List<GeneralPropertyModel> ls = helper.getListGeneralPropertyByGroupItem(groupCode, groupType.getValue(), communityId);
        for (int i = 0; i < ls.size(); i++) {
            GeneralPropertyModel m = ls.get(i);
            bx.addItem(m.getPropertyId());
            bx.setItemCaption(m.getPropertyId(), param.getLabel(m.getPropertyLabel()));
        }

        if (allowAddNewItem) {
            StringBuffer ids = new StringBuffer();
            ids.append(GENERAL_CREATE).append("|").append(groupCode);
            bx.addItem(ids.toString());
            bx.setItemCaption(ids.toString(), new StringBuffer("--- ").append(param.getLabel(Constants.LABEL_NEW)).append(" ---").toString());
        }
        return bx;
    }

    public Constants.APPLICATION_PROP_GROUP_TYPE getGeneralPropertyGroupType(String groupCode) {
        //JANGAN MENGUBAH VALUE DAN KONDISI DI BAWAH,
        // SILAHKAN UNTUK MENAMBAH DENGAN VALUE DAN KONDISI YANG BERBEDA
        Constants.APPLICATION_PROP_GROUP_TYPE groupType = Constants.APPLICATION_PROP_GROUP_TYPE.COMMUNITY;
        if (Constants.GENERAL_PROPERTY_GROUP_CODE.FAMILY_GENDER.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.FAMILY_MARITAL_STATUS.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.FAMILY_RELIGION_TYPE.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.FAMILY_BLOOD_GROUP.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.BANK_ACCOUNT_GROUP.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.IDENTITY_TYPE.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.FAMILY_RELATION_TYPE.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.EMPLOYEE_LEAVE_TYPE.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.COMMUNITY;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.EMPLOYEE_STATUS.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.COMMUNITY;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.EMPLOYMENT_STATUS.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.COMMUNITY;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.WORKING_SCHEDULE_GROUP.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.COMMUNITY;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.WAREHOUSE_TYPE.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        else if (Constants.GENERAL_PROPERTY_GROUP_CODE.DIMENSION_TYPE.equals(groupCode))
            groupType = Constants.APPLICATION_PROP_GROUP_TYPE.ALL_CLIENT;
        return groupType;

    }

}