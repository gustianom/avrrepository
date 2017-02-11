package com.tenma.common.gui.main.task;

import com.google.gson.Gson;
import com.tenma.auth.model.MemberModel;
import com.tenma.auth.util.logon.MemberHelper;
import com.tenma.common.TA;
import com.tenma.common.TenmaRootApplication;
import com.tenma.common.util.Constants;
import com.tenma.share.gui.display.component.MemberAutoField;
import com.tenma.share.gui.display.component.MemberSelection;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma-01 on 27/01/16.
 */
public class CCSelection extends VerticalLayout implements MemberSelection {
    List listMemberCc = new ArrayList<>();
    private MemberAutoField tCc;
    private MemberCCSelection parents;
    private VerticalLayout lyCc = new VerticalLayout();
    private HashMap mapCc = new HashMap();


    public CCSelection() {
        GUI();
    }

    private void GUI() {
        tCc = new MemberAutoField(this);
        addComponent(tCc);
        addComponent(lyCc);
    }

    @Override
    public void setMemberModel(MemberModel memberModel) {
        if (!mapCc.containsKey(memberModel.getMemberId())) {
            mapCc.put(memberModel.getMemberId(), memberModel.getMemberName());
            listMemberCc.add(memberModel.getMemberId());
        }
        generateMemberCc();
    }

    public String getStringJsonMemberCc() {
        String strJson = new Gson().toJson(listMemberCc);
        return strJson;
    }

    public void setListMemberCc(List list) {
        this.listMemberCc.addAll(list);
        if (listMemberCc.size() > 0) {
            setMemberMapCc();
            generateMemberCc();
        }
    }


    private void setMemberMapCc() {
        MemberHelper memberHelper = new MemberHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List list = memberHelper.getListRenderer(map, false);
        for (int x = 0; x < listMemberCc.size(); x++) {
            Object obj = listMemberCc.get(x);
            mapCc.put(obj, obj);
        }

        for (int x = 0; x < list.size(); x++) {
            MemberModel memberModel = (MemberModel) list.get(x);
            if (mapCc.containsKey(memberModel.getMemberId())) {
                mapCc.put(memberModel.getMemberId(), memberModel.getMemberName());
            }
        }
    }

    private void generateMemberCc() {
        System.out.println("TaskModify.generateMemberCc");
        lyCc.removeAllComponents();
        lyCc.setMargin(false);
        if (listMemberCc.size() > 0) {
            int counter = 0;
            HorizontalLayout hz = new HorizontalLayout();
            lyCc.addComponent(hz);
            for (int i = 0; i < listMemberCc.size(); i++) {
                Object m = listMemberCc.get(i);
                counter++;
                if (counter > 2) {
                    counter = 0;
                    hz = new HorizontalLayout();
                    lyCc.addComponent(hz);
                }
                hz.setMargin(false);
                Button btn = new Button();
                btn.addClickListener(click -> actionDeleteMember(m));
                btn.setIcon(new ThemeResource("layouts/images/16/closed.png"));
                btn.setPrimaryStyleName("btnClosedwelcome");
                Label space = new Label();
                space.setWidth("20px");
                HorizontalLayout h = new HorizontalLayout(new Label(String.valueOf(mapCc.get(m))), space, btn);
                h.setStyleName("studentroot");
                hz.addComponent(h);
            }
        }
    }

    private void actionDeleteMember(Object m) {
        listMemberCc.remove(m);
        generateMemberCc();
    }


}
