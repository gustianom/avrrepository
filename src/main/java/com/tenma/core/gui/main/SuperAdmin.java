package com.tenma.core.gui.main;

import com.tenma.common.TA;

import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.core.bean.menu.MenuListHelper;
import com.tenma.model.core.MenuModel;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.lang.*;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuffer;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 02/12/14.
 */
public class SuperAdmin extends TenmaPanel implements LayoutEvents.LayoutClickListener {

    private VerticalLayout groupSetupLayout;
    private String[] groupMetroStyle = new String[]{
            METRO_STYLE.A.getValue()
            , METRO_STYLE.B.getValue()
            , METRO_STYLE.C.getValue()
            , METRO_STYLE.D.getValue()
            , METRO_STYLE.E.getValue()
            , METRO_STYLE.F.getValue()
            , METRO_STYLE.G.getValue()
            , METRO_STYLE.H.getValue()
            , METRO_STYLE.I.getValue()
            , METRO_STYLE.J.getValue()
            , METRO_STYLE.K.getValue()
            , METRO_STYLE.L.getValue()
            , METRO_STYLE.M.getValue()
            , METRO_STYLE.N.getValue()
    };

    public SuperAdmin() {

        groupSetupLayout = new VerticalLayout();
        groupSetupLayout.setWidth(100, Unit.PERCENTAGE);
        groupSetupLayout.setSpacing(true);

        initializeContent();
        setCompositionRoot(groupSetupLayout);
    }

    private void initializeContent() {
        String[][] superAction = new String[][]
                {
                        {"default.menugroup", "share.tenma.core.gui.main.menu.MenuGroupList"}
                        , {"Core Screen", "share.tenma.core.gui.main.screen.ScreenList"}
                        , {"Core Community Structure", "share.tenma.core.gui.main.communitystruc.CommunityStrucList"}
                        , {"coreusergroup", "share.tenma.core.gui.main.reference.community.UserGroupRegistration"}
                        , {"coremenuregistration", "share.tenma.core.gui.main.menu.registration.CommunityMenuRegistration"}
                        , {"default.user", "share.tenma.common.gui.main.user.UserList"}
                        , {"default.language", "share.tenma.common.gui.main.languages.LanguagesList"}
                        , {"LanguageLabel.title", "share.tenma.common.gui.main.language_label.LanguageLabelList"}
                        , {"menu.communityreg", "share.course.gui.main.registration.communityreg.CommunityRegisterList"}
                        , {"menu.profession", "share.tenma.uk.gui.main.profession.ProfessionList"}
                        , {"menu.question", "share.tenma.uk.gui.main.questions.QuestionsList"}
                        , {"menu.activequestion", "share.tenma.hcm.gui.main.activequestions.ActiveQuestionsModify"}
                        , {"menu.emailType", "share.tenma.uangkita.gui.main.emailsetting.EmailTypeList"}
                        , {"menu.absentaction", "share.course.gui.main.absent_action.AbsentActionModify"}
                        , {"menu.paymentTemplate", "share.course.gui.main.payment.setting.CourseTypeTemplateUIForm"}
                        , {"menu.searchdoc", "share.tenma.sem.gui.SEDoc"}
                        , {"Push Message Test", "share.TestPush"}
                        , {"Document List", "share.tenma.common.util.s3.TenmaS3Document"}
                        , {"Course AWS Message Simulator", "share.course.util.CourseGCMSimulator"}
                        , {"Duplicate User", "share.tenma.core"}
                };

        MenuListHelper hlp = new MenuListHelper();
        HashMap mp;

        int icount = 0;
        int indx = 1;
        CssLayout vw = new CssLayout();
        vw.setPrimaryStyleName("tile-group four");
        for (int i = 0; i < superAction.length; i++) {

            Resource rsc = new ThemeResource("layouts/images/64/noimage.png");
            mp = new HashMap();
            mp.put("menuAction", superAction[i][1]);

            MenuModel m = null;
            List lt = hlp.getListRenderer(mp, false);
            if (lt != null && lt.size() != 0) {
                m = new MenuModel();
                m.setMenuAction(superAction[i][1]);
                m = hlp.getMenuListDetail(m);
            } else {
                m = new MenuModel();
                m.setMenuAction(superAction[i][1]);
                m.setMenuName(superAction[i][0]);
            }

            if (m != null && m.getMenuImage() != null && m.getMenuImage().trim().length() != 0)
                rsc = new ThemeResource(m.getMenuImage().trim());

            Image img = new Image(null, rsc);

            CssLayout himage = new CssLayout(img);
            himage.setPrimaryStyleName("tile-content icon");

            if (icount >= groupMetroStyle.length)
                icount = 0;

            CssLayout c1 = new CssLayout();
            c1.addComponent(himage);

            StringBuffer title = new StringBuffer()
                    .append("<div class=\"brand\">")
                    .append("<div class=\"label\">").append(param.getLabel(m.getMenuName())).append("</div>")
                    .append("</div>");
            Label labelMenuName = new Label(title.toString());
            labelMenuName.setContentMode(ContentMode.HTML);
            c1.addComponent(labelMenuName);
            c1.setPrimaryStyleName(groupMetroStyle[icount]);
            c1.setData(m);
            c1.addLayoutClickListener(this);

            vw.addComponent(c1);
            icount++;
            indx++;
        }

        CssLayout mainL = new CssLayout();
        mainL.setPrimaryStyleName("metro");
        mainL.addComponent(vw);


        groupSetupLayout.removeAllComponents();

        groupSetupLayout.addComponent(getTitle());
        groupSetupLayout.addComponent(mainL);

    }

    @Override
    public void layoutClick(LayoutEvents.LayoutClickEvent layoutClickEvent) {
        CssLayout ly = (CssLayout) layoutClickEvent.getSource();
        MenuModel m = (MenuModel) ly.getData();

        if (m.getMenuAction() != null && m.getMenuAction().trim().length() != 0) {

            try {
                Class panelClass = Class.forName(m.getMenuAction());
                Constructor moduleConstructor = panelClass.getConstructor();
                TenmaPanel panel = (TenmaPanel) moduleConstructor.newInstance();
                panel.setWidth(100, Unit.PERCENTAGE);
                TA.getCurrent().updateWorkingArea(panel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Label getTitle() {
        StringBuffer title = new StringBuffer()
                .append("<h1 class=\"fg-active-darkOrange\">")
                .append(param.getLabel("default.superAdmin"))
                .append("</h1>");


        Label label = new Label(title.toString());
        label.setContentMode(ContentMode.HTML);
        return label;

    }

    private enum METRO_STYLE {
        A("tile double double-vertical bg-lightBlue live"),
        B("tile double bg-violet"),
        C("tile bg-teal"),
        D("tile bg-green"),
        E("tile bg-steel"),
        F("tile bg-darkRed"),
        G("tile double bg-darkOrange"),
        H("tile double bg-darkOrange"),
        I("tile double bg-darkOrange"),
        J("tile double bg-darkOrange"),
        K("tile double bg-darkOrange"),
        L("tile double bg-darkOrange"),
        M("tile double bg-darkOrange"),
        N("tile double bg-darkOrange");

        String methodValue = "tile double bg-lightBlue live";

        METRO_STYLE(String value) {
            methodValue = value;
        }

        public String getValue() {
            return String.valueOf(methodValue);
        }

        @Override
        public String toString() {
            return String.valueOf(methodValue);
        }
    }

}
