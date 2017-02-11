package com.tenma.common.gui.display.component.field.gui;

import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.util.Constants;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gustianom on 12/28/13.
 */
public class SearchComboField extends ComboBox {
    public final String FIELD1 = "field1";
    public final String FIELD2 = "field2";
    public final String FIELD3 = "field2";
    public final String FIELD4 = "field4";


    public SearchComboField() {

        setNewItemsAllowed(true);
    }

    @Override
    public Collection<?> getContainerPropertyIds() {
        System.out.println("share.tenma.common.gui.display.component.field.gui.SearchComboField.getContainerPropertyIds");
        return super.getContainerPropertyIds();
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        super.valueChange(event);
        System.out.println("share.tenma.common.gui.display.component.field.gui.SearchComboField.valueChange ");
    }

    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {

        String filterString = (String) variables.get("filter");
        items.removeAllItems();
        if (filterString != null && filterString.length() > 2) {
            preparingSelectionData(filterString);
        }

        super.changeVariables(source, variables);
    }


    public void preparingSelectionData(String searchKey) {
        UserHelper hlp = new UserHelper();

        HashMap parameterMap = new HashMap();
        if ((searchKey != null) && (searchKey.trim().length() != 0))
            if (searchKey.indexOf("%") == -1)
                searchKey = new StringBuffer().append("%").append(searchKey).append("%").toString();
        parameterMap.put(Constants.HEADER_SEARCH, searchKey);

        parameterMap.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());

        SqlSession session = hlp.sqlSessionFactory.openSession();
        try {
            populateWithUser(session, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void populateWithUser(SqlSession session, HashMap parameterMap) {
        UserHelper hlp = new UserHelper();
        List ls = hlp.getListRenderer(session, parameterMap, false);
        if (ls != null) {
            for (int i = 0; i < ls.size(); i++) {
                UserModel m = (UserModel) ls.get(i);
//                Item id = items.addItem(m.getUserId());
//                id.getItemProperty(FIELD1).setValue(m.getUserFullName());
//                id.getItemProperty(FIELD2).setValue(m.getEmailAddress());
                addItem(m.getUserId());
                setItemCaption(m.getUserId(), m.getUserFullName());
            }
        }
    }

}

