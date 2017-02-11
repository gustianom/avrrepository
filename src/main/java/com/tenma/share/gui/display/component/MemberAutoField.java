package com.tenma.share.gui.display.component;

import com.tenma.auth.model.MemberModel;
import com.tenma.auth.util.logon.MemberHelper;
import com.tenma.common.TA;
import com.tenma.common.gui.display.component.AutoComplete;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ndwijaya on 12/7/14.
 */
public class MemberAutoField extends AutoComplete {
    public static final String MEMBER_ID = "memberID";
    public static final String MEMBER_NAME = "memberName";

    private MemberSelection parent;

    private MemberModel selectedModel;
    private List<Integer> selectedList;

    public MemberAutoField(MemberSelection aparent) {
        selectedList = new ArrayList<Integer>();

        this.parent = aparent;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(btn)) {
            resultset = getList(filterFld.getValue());
            setResultsTableValues();
            popupView.setPopupVisible(true);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        lookupTextId.setEnabled(enabled);
        btn.setEnabled(enabled);
    }

    private void setAccount(MemberModel model) {
        selectedModel = model;
        lookupTextId.setValue(model.getMemberName());
        parent.setMemberModel(model);
    }

    public MemberModel getMemberModel() {
        return selectedModel;
    }

    public void setMemberId(Integer memberId) {
        System.out.println("bankId = " + memberId);
        MemberHelper help = new MemberHelper();

        MemberModel p = new MemberModel();
        p.setMemberId(memberId);
        MemberModel m = help.getMemberDetail(p);
        selectedModel = m;
        this.lookupTextId.setValue(m.getMemberName());
    }

    public String getVehicleName() {
        if (selectedModel == null) {
            return "";
        } else if (selectedModel.getMemberName() != null) {
            return selectedModel.getMemberName();
        } else {
            return "";
        }
    }

    public void setSelectedModel(MemberModel model) {
        selectedModel = model;
        parent.setMemberModel(selectedModel);
    }


    @Override
    protected void initTable() {
        resultsTable = new Table();
        resultsTable.setSelectable(true);
        container = new TenmaTableContainer();

        container.addContainerProperty(MEMBER_ID, Integer.class, 0);
        container.addContainerProperty(MEMBER_NAME, String.class, "");

        resultsTable.setContainerDataSource(container);
        resultsTable.commit();

        resultsTable.setWidth("300px");
        resultsTable.setImmediate(true);
        resultsTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        resultsTable.setVisibleColumns(new Object[]{MEMBER_NAME});
        resultsTable.addItemClickListener(
                new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent event) {
                        Item item = event.getItem();
                        tableRowSelected = true;
                        popupView.setPopupVisible(false);
                        filterFld.setValue("");
                        if (item != null) {
                            MemberModel pcm = new MemberModel();
                            pcm.setMemberId((Integer) item.getItemProperty(MEMBER_ID).getValue());
                            pcm.setMemberName(item.getItemProperty(MEMBER_NAME).getValue().toString());
                            setAccount(pcm);
                            setSelectedObject(pcm);
                        }
//
                    }
                }
        );

    }

    public void setSelectedObject(Object obj) {
        if (obj != null) {
            selectedModel = (MemberModel) obj;
        }
    }

    @Override
    public void setResultsTableValues() {
        container.removeAllItems();

        for (int i = 0; i < resultset.size(); i++) {
            MemberModel mdl = (MemberModel) resultset.get(i);
            Boolean isTrue = true;
            for (int y = 0; y < selectedList.size(); y++) {
                Integer memberId = (Integer) selectedList.get(y);
                if (memberId.equals(mdl.getMemberId())) {
                    isTrue = false;
                }
            }
            if (isTrue) {
                Item id = container.addItem(i);
                id.getItemProperty(MEMBER_ID).setValue(mdl.getMemberId());
                id.getItemProperty(MEMBER_NAME).setValue(mdl.getMemberName());
            }
        }

        int sizePage = resultset.size() - selectedList.size();

        if (sizePage > 10)
            resultsTable.setPageLength(10);
        else
            resultsTable.setPageLength(sizePage);
        resultsTable.setSizeFull();
        setSizeUndefined();
    }

    @Override
    public Object getValue() {
        return selectedModel;
    }

    @Override
    public boolean isValid() {
        boolean rs = false;
        if (selectedModel != null) rs = true;
        return rs;
    }

    public void setWasSelectedMember(List<Integer> memberIdList) {
        selectedList = memberIdList;
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        super.validate();
        if (selectedModel != null) throw new Validator.InvalidValueException("Bank is not selected");
    }

    @Override
    protected List<Object> getList(String inputKey) {
        MemberHelper phelper = new MemberHelper();
        HashMap map = new HashMap();
        Integer skip = new Integer(0);
        int pageSize = 20;
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "memberName");
        map.put(Constants.RECORDSELECT_SORTORDER, "ASCENDING");
        map.put("searchValue", "%" + inputKey.trim() + "%");
        map.put(Constants.RECORDSELECT_SKIP, skip);
        map.put(Constants.RECORDSELECT_MAX, pageSize);
        List ls = phelper.getListRenderer(map, true);
        System.out.println("Size = " + ls.size() + " : " + inputKey);
        return ls;
    }

}
