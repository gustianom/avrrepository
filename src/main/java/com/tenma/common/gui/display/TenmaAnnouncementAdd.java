package com.tenma.common.gui.display;


import com.tenma.common.util.Constants;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;


/**
 * Created by PT TENMA BRIGHT SKY
 * User: derry.irmansyah
 * Date: 7/24/13
 * Time: 1:15 PM
 */
public abstract class TenmaAnnouncementAdd extends TenmaPanel implements TabSheet.SelectedTabChangeListener {
    private Button saveButton;
    private Button backButton;
    private CustomLayout layout;
    private HorizontalLayout buttonLayout;
    private VerticalLayout list;
    private HorizontalLayout toLayout;
    private CheckBox allItem;
    private CheckBox allMember;
    private CheckBox allStudent;
    private CheckBox allFranchise;
    private TextArea announcementDesc;
    private CheckBox board;
    private CheckBox sms;
    private CheckBox email;
    private CheckBox twitter;
    private CheckBox fb;
    private Label desc = new Label();
    private Label to = new Label();
    private Label with = new Label();
    private TabSheet tabSheet;
    private HorizontalLayout notifLayout;


    public TenmaAnnouncementAdd() {

        layout = new CustomLayout("templateModify");
        preparingUI();
    }


    /**
     * set variable before executing data
     */


    public final void preparingUI() {
        layout.setSizeFull();

        saveButton = new Button(param.getLabel(Constants.LABEL_SAVE), this);
        backButton = new Button(param.getLabel(Constants.LABEL_BACK), this);
        announcementDesc = new TextArea();

        backButton.setIcon(new ThemeResource(Constants.BACK_ICON));
        saveButton.setIcon(new ThemeResource(Constants.SAVE_ICON));

        buttonLayout = new HorizontalLayout();

        configureToolbarBefore();
        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(backButton);
        configureToolbarAfter();

        layout.addComponent(buttonLayout, "contentButton");
        layout.addComponent(createSearchLayout(), "contentModify");
        setCompositionRoot(layout);
    }


    public void configureToolbarBefore() {
    }

    public void configureToolbarAfter() {
    }

    public HorizontalLayout getButtonArea() {
        return buttonLayout;
    }

    public HorizontalLayout getToLayout() {
        return toLayout;
    }

    public void setToLayout(HorizontalLayout toLayout) {
        this.toLayout = toLayout;
    }

    public VerticalLayout getList() {
        return list;
    }

    public void setList(VerticalLayout list) {
        this.list = list;
    }

    public CheckBox getAllItem() {
        return allItem;
    }

    public void setAllItem(CheckBox allItem) {
        this.allItem = allItem;
    }

    public CheckBox getAllMember() {
        return allMember;
    }

    public void setAllMember(CheckBox allMember) {
        this.allMember = allMember;
    }

    public CheckBox getAllStudent() {
        return allStudent;
    }

    public void setAllStudent(CheckBox allStudent) {
        this.allStudent = allStudent;
    }

    public CheckBox getAllFranchise() {
        return allFranchise;
    }

    public void setAllFranchise(CheckBox allFranchise) {
        this.allFranchise = allFranchise;
    }

    public CheckBox getBoard() {
        return board;
    }

    public void setBoard(CheckBox board) {
        this.board = board;
    }

    public CheckBox getSms() {
        return sms;
    }

    public void setSms(CheckBox sms) {
        this.sms = sms;
    }

    public CheckBox getEmail() {
        return email;
    }

    public void setEmail(CheckBox email) {
        this.email = email;
    }

    public CheckBox getTwitter() {
        return twitter;
    }

    public void setTwitter(CheckBox twitter) {
        this.twitter = twitter;
    }

    public CheckBox getFb() {
        return fb;
    }

    public void setFb(CheckBox fb) {
        this.fb = fb;
    }

    public HorizontalLayout getNotifLayout() {
        return notifLayout;
    }

    public void setNotifLayout(HorizontalLayout notifLayout) {
        this.notifLayout = notifLayout;
    }

    public TabSheet getTabSheet() {
        return tabSheet;
    }

    public void setTabSheet(TabSheet tabSheet) {
        this.tabSheet = tabSheet;
    }

    public TextArea getAnnouncementDesc() {
        return announcementDesc;
    }

    public void setAnnouncementDesc(TextArea announcementDesc) {
        this.announcementDesc = announcementDesc;
    }

    private Component createSearchLayout() {
        list = new VerticalLayout();
        announcementDesc = new TextArea();
        announcementDesc.setWidth("280");
        tabSheet = new TabSheet();
        allItem = new CheckBox("All");
        allMember = new CheckBox("All Member");
        allStudent = new CheckBox("All Student");
        allFranchise = new CheckBox("All Franchise");

        board = new CheckBox("Board");
        board.setValue(true);
        sms = new CheckBox("SMS");
        email = new CheckBox("Email");
        fb = new CheckBox("Facebook");
        twitter = new CheckBox("Twitter");
        toLayout = new HorizontalLayout();
        desc.setCaption("Announcement Desc");
        to.setCaption("To");
        with.setCaption("Announcement With");


        configureToCheckBoxBefore();
        toLayout.addComponent(allItem);
        Label q = new Label("");
        q.setWidth("50px");
        toLayout.addComponent(q);
        toLayout.addComponent(allMember);
        Label r = new Label("");
        r.setWidth("50px");
        toLayout.addComponent(r);
        toLayout.addComponent(allStudent);
        Label g = new Label("");
        g.setWidth("50px");
        toLayout.addComponent(g);
        toLayout.addComponent(allFranchise);
        configureToCheckBoxAfter();

        tabSheet = new TabSheet();
        tabSheet.addSelectedTabChangeListener(this);

        notifLayout = new HorizontalLayout();
        notifLayout.addComponent(board);
        Label a = new Label("");
        a.setWidth("30px");
        Label b = new Label("");
        b.setWidth("30px");
        Label c = new Label("");
        c.setWidth("30px");
        Label d = new Label("");
        d.setWidth("30px");
        notifLayout.addComponent(a);
        notifLayout.addComponent(sms);
        notifLayout.addComponent(b);
        notifLayout.addComponent(email);
        notifLayout.addComponent(c);
        notifLayout.addComponent(twitter);
        notifLayout.addComponent(d);
        notifLayout.addComponent(fb);


        list.addComponent(desc);
        list.addComponent(announcementDesc);
        Label label1 = new Label("");
        label1.setHeight("10px");
        list.addComponent(label1);
        list.addComponent(to);
        list.addComponent(toLayout);
        list.addComponent(tabSheet);
        Label label2 = new Label("");
        label2.setHeight("10px");
        list.addComponent(label2);
        list.addComponent(with);
        list.addComponent(notifLayout);


        return list;
    }

    private void configureToCheckBoxAfter() {

    }

    private void configureToCheckBoxBefore() {

    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton().equals(saveButton))
            doSave();
        else if (clickEvent.getButton().equals(backButton))
            doBackList();


    }

    protected abstract void doBackList();

    protected abstract void doSave();


    /**
     * must override this method to perform checking before do deletion
     *
     * @return
     */
    public boolean checkBeforeDelete() {
        return true;
    }


    public final void setTitle(String title) {
        StringBuilder contentTitle = new StringBuilder();
        contentTitle
                .append("<span class=\"black\"><b>")
                .append(param.getLabel(title))
                .append("</b></span>")
                .toString();
        Label l = new Label(contentTitle.toString());
        l.setContentMode(ContentMode.HTML);
        layout.addComponent(l, "contentTitle");
    }

    public final void setTitle(String parentTitle, String myTitle) {
        StringBuilder contentTitle = new StringBuilder();
        contentTitle
                .append("<span class=\"black\"><b>")
                .append(param.getLabel(myTitle))
                .append("</b></span>")
                .append("<span class=\"orange\"><b>")
                .append(" [").append(param.getLabel(parentTitle)).append("]")
                .append("</b></span>")
                .toString();
        Label l = new Label(contentTitle.toString());
        l.setContentMode(ContentMode.HTML);

        layout.addComponent(l, "contentTitle");
    }

    public Button getAddButton() {
        return saveButton;
    }

    public Button getUpdateButton() {
        return backButton;
    }


    public CustomLayout getLayout() {
        return layout;
    }

//    public final void setAnnouncementValue(AnnouncementModel m) {
//        m.setAnnouncementDesc(getAnnouncementDesc().getValue());
//        m.setSms(getSms().getValue());
//        m.setEmail(getEmail().getValue());
//        m.setTwitter(getTwitter().getValue());
//        m.setFb(getFb().getValue());
//        m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
//    }


    public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
        TabSheet tabSheet = event.getTabSheet();
        TabSheet.Tab tab = tabSheet.getTab(tabSheet.getSelectedTab());

    }

}

