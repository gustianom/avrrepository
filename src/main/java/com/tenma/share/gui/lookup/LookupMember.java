package com.tenma.share.gui.lookup;

import com.tenma.auth.model.CommunityMemberModel;
import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.TenmaWindow;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/30/13
 * Time: 7:20 PM
 */
public class LookupMember extends HorizontalLayout implements Button.ClickListener {
    private LookupMember.LOOKUP_MEMBER_TYPE lookupMemberType;
    private TenmaPanel parentPanelComponent;
    private TenmaWindow parentWindowComponent;
    private List<CommunityMemberModel> listSelectedMember;
    private Button buttonInvitation;
    private String caption;
    private String onUpdatedCaption;
    private boolean isCurrentUserIncluded;
    private boolean isRegisteredAsUserOnly;
    private boolean isMultipleSelect;

    public LookupMember(TenmaPanel parentComponent, List<CommunityMemberModel> listSelectedMember, LOOKUP_MEMBER_TYPE lookupMemberType, boolean isRegisteredAsUserOnly, boolean isMultipleSelect) {
        initializeLayout(parentComponent, listSelectedMember, lookupMemberType, false, isRegisteredAsUserOnly, isMultipleSelect);
    }

    public LookupMember(TenmaPanel parentComponent, List<CommunityMemberModel> listSelectedMember, LookupMember.LOOKUP_MEMBER_TYPE lookupMemberType, boolean isCurrentUserIncluded, boolean isRegisteredAsUserOnly, boolean isMultipleSelect) {
        initializeLayout(parentComponent, listSelectedMember, lookupMemberType, isCurrentUserIncluded, isRegisteredAsUserOnly, isMultipleSelect);
    }

    public LookupMember(TenmaWindow parentWindow, List<CommunityMemberModel> listSelectedMember, LookupMember.LOOKUP_MEMBER_TYPE lookupMemberType, boolean isRegisteredAsUserOnly, boolean isMultipleSelect) {
        initializeLayout(parentWindow, listSelectedMember, lookupMemberType, false, isRegisteredAsUserOnly, isMultipleSelect);
    }

    public LookupMember(TenmaWindow parentWindow, List<CommunityMemberModel> listSelectedMember, LookupMember.LOOKUP_MEMBER_TYPE lookupMemberType, boolean isCurrentUserIncluded, boolean isRegisteredAsUserOnly, boolean isMultipleSelect) {
        initializeLayout(parentWindow, listSelectedMember, lookupMemberType, isCurrentUserIncluded, isRegisteredAsUserOnly, isMultipleSelect);
    }

    private void initializeLayout(TenmaWindow parentWindow, List<CommunityMemberModel> listSelectedMember, LOOKUP_MEMBER_TYPE lookupMemberType, boolean isCurrentUserIncluded, boolean isRegisteredAsUserOnly, boolean isMultipleSelect) {
        this.parentWindowComponent = parentWindow;
        continueInitialization(listSelectedMember, lookupMemberType, isCurrentUserIncluded, isRegisteredAsUserOnly, isMultipleSelect);
    }

    private void initializeLayout(TenmaPanel parentComponent, List<CommunityMemberModel> listSelectedMember, LOOKUP_MEMBER_TYPE lookupMemberType, boolean isCurrentUserIncluded, boolean isRegisteredAsUserOnly, boolean isMultipleSelect) {
        this.parentPanelComponent = parentComponent;
        continueInitialization(listSelectedMember, lookupMemberType, isCurrentUserIncluded, isRegisteredAsUserOnly, isMultipleSelect);
    }

    private void continueInitialization(List<CommunityMemberModel> listSelectedMember, LOOKUP_MEMBER_TYPE lookupMemberType, boolean isCurrentUserIncluded, boolean isRegisteredAsUserOnly, boolean isMultipleSelect) {
        this.isCurrentUserIncluded = isCurrentUserIncluded;
        this.listSelectedMember = listSelectedMember;
        this.lookupMemberType = lookupMemberType;
        this.isRegisteredAsUserOnly = isRegisteredAsUserOnly;
        this.isMultipleSelect = isMultipleSelect;

        buttonInvitation = new Button("button", this);
        addComponent(buttonInvitation);
        setComponentAlignment(buttonInvitation, Alignment.MIDDLE_LEFT);

    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
        updateCaption();
    }

    public void setOnUpdatedCaption(String onUpdatedCaption) {
        this.onUpdatedCaption = onUpdatedCaption;
        updateCaption();
    }

    @Override
    public void setIcon(Resource icon) {
        buttonInvitation.setIcon(icon);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

        MemberParticipant dialog = retreiveMember();
        dialog.setCaption(TA.getCurrent().getParams().getLabel("task.whoshoulddo"));
        dialog.setWidth("70%");
        dialog.setModal(true);
        dialog.setHeight(UI.getCurrent().getPage().getBrowserWindowHeight() - 200 + "px");
        dialog.setResizable(false);
        UI.getCurrent().addWindow(dialog);
    }

    private MemberParticipant retreiveMember() {
        MemberParticipant iedMemberParticipant = null;
//        if (lookupMemberType.equals(LOOKUP_MEMBER_TYPE.COMMUNITY))
//            iedMemberParticipant = new CommunityIedMemberParticipant(tenmaApplication, this, listSelectedMember, isCurrentUserIncluded, isRegisteredAsUserOnly, isMultipleSelect);
//        else if (lookupMemberType.equals(LOOKUP_MEMBER_TYPE.EMPLOYEE))
//            iedMemberParticipant = new EmployeeIedMemberParticipant(tenmaApplication, this, listSelectedMember, isCurrentUserIncluded, isRegisteredAsUserOnly, isMultipleSelect);
        return iedMemberParticipant;
    }

    public void actionOnSelectionUpdate(Object taskObject) {
        updateCaption();

        if (parentPanelComponent != null)
            parentPanelComponent.refreshTable(taskObject);
        else if (parentWindowComponent != null)
            parentWindowComponent.doRefresh(taskObject);
    }

    private void updateCaption() {
        if (listSelectedMember != null) {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < listSelectedMember.size(); i++) {
                CommunityMemberModel m = listSelectedMember.get(i);
                if (i != 0)
                    buf.append(", ");
                buf.append(m.getMemberName());
            }
            buttonInvitation.setDescription(buf.toString());
            if (listSelectedMember.size() == 0)
                buttonInvitation.setCaption(caption);
            else
                buttonInvitation.setCaption(new StringBuilder().append(listSelectedMember.size()).append(" ").append(onUpdatedCaption).toString());
        }
    }

    public static enum LOOKUP_MEMBER_TYPE {
        COMMUNITY(10), EMPLOYEE(20), USER(30);

        private int methodvalue = 10;

        private LOOKUP_MEMBER_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }

    }
}
