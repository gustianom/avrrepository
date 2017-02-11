package com.tenma.share.gui.lookup;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.gui.display.component.TenmaButtonPanel;
import com.tenma.common.util.Constants;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/29/13
 * Time: 7:17 PM
 */
public abstract class MemberParticipant extends TenmaWindow implements Button.ClickListener {
    private final boolean isMultipleSelect;
    List<ParticipantViewModel> listViewDetail;
    private boolean isRegisteredUserOnly;
    private boolean currentUserIncluded;
    private List existingSelectedMemberID;
    private LookupMember parentPanel;
    private TextField textSearch;
    private Button searchButton;
    private Button butCancel;
    private Button butSave;
    private Panel panelMemberArea;
    private int MAX_COLUMN = 3;

    /**
     * @param parentPanel
     * @param existingSelectedMemberID
     * @param isCurrentUserIncluded
     * @param isRegisteredUserOnly     (only member who already registered as user (this user available to logon to the system)
     * @param isMultipleSelect
     */
    public MemberParticipant(LookupMember parentPanel, List<Object> existingSelectedMemberID, boolean isCurrentUserIncluded, boolean isRegisteredUserOnly, boolean isMultipleSelect) {

        listViewDetail = new ArrayList();
        this.currentUserIncluded = isCurrentUserIncluded;
        this.parentPanel = parentPanel;
        this.existingSelectedMemberID = existingSelectedMemberID;
        this.isRegisteredUserOnly = isRegisteredUserOnly;
        this.isMultipleSelect = isMultipleSelect;
        initializeData();
        initializeUI(existingSelectedMemberID);

    }


    private void initializeUI(List<Object> existingSelectedMemberID) {
        searchButton = new Button(param.getLabel("default.search"), this);
        searchButton.setIcon(new ThemeResource("layouts/images/16/049.png"));

        textSearch = new TextField();
        textSearch.setWidth("220px");
        textSearch.setMaxLength(50);
        textSearch.setInputPrompt(param.getLabel("default.search"));

        Label labelSearch = new Label(param.getLabel("default.search") + " : ");

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        hl.addComponent(labelSearch);
        hl.addComponent(textSearch);
        hl.addComponent(searchButton);

        hl.setComponentAlignment(labelSearch, Alignment.MIDDLE_LEFT);
        hl.setComponentAlignment(textSearch, Alignment.MIDDLE_LEFT);
        hl.setComponentAlignment(searchButton, Alignment.MIDDLE_LEFT);

        GridLayout memberAvailable = initializeMember();
        panelMemberArea = new Panel(param.getLabel("community.member"));
        panelMemberArea.setContent(memberAvailable);
        panelMemberArea.setStyleName("panelLookupMember");

        butCancel = new Button(param.getLabel(Constants.LABEL_CANCEL), this);
        butCancel.setIcon(new ThemeResource(Constants.BACK_ICON));
        butCancel.setImmediate(true);
        butSave = new Button(param.getLabel(Constants.LABEL_SELECT), this);
        butSave.setIcon(new ThemeResource("layouts/images/16/102.png"));

        HorizontalLayout layButton = new HorizontalLayout();
        layButton.addComponent(butSave);
        layButton.addComponent(butCancel);

        FormLayout layout = new FormLayout();
        layout.addComponent(layButton);
        layout.addComponent(hl);
        layout.addComponent(panelMemberArea);


        layout.setSizeFull();
        setContent(layout);
    }

    private GridLayout initializeMember() {
        HashMap parameter = new HashMap();
        String skey = getTextSearch().getValue();
        if ((skey != null) && (skey.trim().length() != 0))
            parameter.put(Constants.HEADER_SEARCH, "%" + skey + "%");

        List listData = loadingDataLookup(parameter);
        GridLayout p = new GridLayout();
        p.setSpacing(true);

        p.setSizeFull();

        int cl = MAX_COLUMN;

        int tr = listData.size() / cl;

        if ((tr * cl) < listData.size()) tr++;
        int rows = tr;
        if (rows == 0) rows = 1;


        p.setColumns(cl);
        p.setRows(rows);
        p.setSpacing(true);
        p.setHeight("90%");

        preparingDataView(listData, p);
        return p;
    }

    private void preparingDataView(List ls, GridLayout p) {
        if ((ls != null) && (ls.size() != 0)) {
            for (int i = 0; i < ls.size(); i++) {

                VerticalLayout frame = new VerticalLayout();
                frame.setStyleName("tenmaCardFrameView ");
                frame.addComponent(generateMemberView(ls.get(i)));

                p.addComponent(frame);
            }
        }

    }


    private Component generateMemberView(Object data) {

        String pathImage = new StringBuilder().append(TA.getCurrent().getParams().getProperty(Constants.UPLOAD_DIR_PROFILE_PICTURE))
                .append(File.separator)
//                .append(m.getProfilePicture())
                .toString();

        Resource rsc = new ThemeResource("layouts/images/kuser.png");
        File fl = new File(pathImage);
        if (fl.exists())
            rsc = new FileResource(fl);

        Image img = new Image(null, rsc);
        img.setWidth("80px");
        img.setHeight("80px");
        FormLayout formMemberDetailLayout = new FormLayout();

        HashMap mapData = translateClassIntoData(data);

        StringBuilder title = new StringBuilder();
        title.append("<h2><span=\"employeeNameHeader\">")
                .append(generateMemberInfoTitle(mapData))
                .append("</span></h2>")
        ;


        if (listViewDetail != null)
            for (ParticipantViewModel pvm : listViewDetail) {
                String vlue = getPropertyValue(mapData, pvm.getPropertyId());
                formMemberDetailLayout.addComponent(createLabelField(new ThemeResource(pvm.getIconLocation()), pvm.getDesc(), vlue, 90));
            }
        formMemberDetailLayout.setStyleName("tenmaCardContentView");

        Label btnLink = new Label(title.toString());
        btnLink.setContentMode(ContentMode.HTML);
        btnLink.setStyleName("employeeNameHeader");

        String memberInfoId = generateMemberInfoId(mapData);
        btnLink.setId(new StringBuilder().append("SHORTCUT_ON_EMPLOYEE_TITLE").append("|").append(memberInfoId).toString());


        TenmaButtonPanel b = new TenmaButtonPanel("buttonEmployeeMember", btnLink, img, data, isMemberAlreadyPictedUp(memberInfoId));
        b.setWidth("95%");
        b.setContentStyleName("tenmaCardContentView");

        b.doGenerateUI(TenmaButtonPanel.ICON_LOCATION_LEFT, formMemberDetailLayout);
        return b;
    }

    public abstract String generateMemberInfoTitle(HashMap mapData);

    public abstract String generateMemberInfoId(HashMap mapData);

    private String getPropertyValue(HashMap mapData, String propertyId) {
        String vl = "";
        if (mapData.containsKey(propertyId))
            vl = (String) mapData.get(propertyId);
        return vl;
    }

    protected abstract HashMap translateClassIntoData(Object data);

    public final void addLabelField(String icon, String labelCaption, String propertyId) {
        listViewDetail.add(new ParticipantViewModel(icon, labelCaption, propertyId));
    }


    protected abstract void initializeData();

    public abstract boolean isMemberAlreadyPictedUp(Object key);

    public abstract List<Object> loadingDataLookup(HashMap param);

    private final Label createLabelField(ThemeResource icon, String description, String value, int widthPCT) {
        Label f = new Label(value);
        f.setIcon(icon);
        f.setWidth(widthPCT + "%");
        if ((description != null) && (description.trim().length() != 0))
            f.setDescription(description);

        return f;
    }

    @Override
    public void doRefresh(Object newData) {

    }

    @Override
    public Object getSelectedObject() {
        return null;
    }

    @Override
    public void setSelectedObject(Object obj) {

    }

    private TextField getTextSearch() {
        return textSearch;
    }

    public List getExistingSelectedMemberID() {
        return existingSelectedMemberID;
    }


    public Button getButCancel() {
        return butCancel;
    }

    public Button getButSave() {
        return butSave;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (butSave.equals(event.getButton()))
            doSelect();
        else if (butCancel.equals(event.getButton()))
            doClose();
        else if (searchButton.equals(event.getButton()))
            doSearch();
    }

    private void doSearch() {
        GridLayout memberAvailable = initializeMember();
        panelMemberArea.setContent(memberAvailable);
    }

    private void doClose() {
        if (parentPanel != null)
            parentPanel.actionOnSelectionUpdate("updateParticipant");
        UI.getCurrent().removeWindow(this);
    }

    private void doSelect() {
        GridLayout gridLayout = (GridLayout) panelMemberArea.getContent();
        Iterator<Component> itr = gridLayout.iterator();
        existingSelectedMemberID.clear();

        boolean isContinue = true;
        while (itr.hasNext()) {
            VerticalLayout layout = (VerticalLayout) itr.next();
            TenmaButtonPanel bp = (TenmaButtonPanel) layout.getComponent(0);

            boolean bol = bp.getCheckBox().getValue();
            if (bol) {
                existingSelectedMemberID.add(bp.getData());
                if (!isMultipleSelect)
                    if (existingSelectedMemberID.size() > 1) {
                        isContinue = false;
                        Notification.show(param.getLabel("select.onlyOneRecord"));
                    }

            }
        }
        if (isContinue)
            if (methodBeforeClose())
                doClose();
    }

    public boolean methodBeforeClose() {
        return true;
    }

    public boolean isCurrentUserIncluded() {
        return currentUserIncluded;
    }

    public boolean isRegisteredUserOnly() {
        return isRegisteredUserOnly;
    }

    public final HashMap preparingClassData(Class<?> cl, Object classObject) throws Exception {
        TenmaPanel panel = new TenmaPanel() {
        };
        return panel.preparingEmailData(cl, classObject);
    }

    class ParticipantViewModel {
        String iconLocation;
        String desc;
        String propertyId;

        ParticipantViewModel(String iconLocation, String desc, String propertyId) {
            this.iconLocation = iconLocation;
            this.desc = desc;
            this.propertyId = propertyId;
        }

        public String getIconLocation() {
            return iconLocation;
        }

        public void setIconLocation(String iconLocation) {
            this.iconLocation = iconLocation;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }
    }
}