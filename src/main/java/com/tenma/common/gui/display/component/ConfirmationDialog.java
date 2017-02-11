package com.tenma.common.gui.display.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 9/26/12
 * Time: 9:32 PM
 */
public class ConfirmationDialog extends Window implements Button.ClickListener {

    private Callback callback;
    private Button yes = new Button("", this);
    private Button no = new Button("", this);

    public ConfirmationDialog(String caption, String question, String yesCaption, String noCaption, Callback callback) {
        super(caption);
        yes.setStyleName("circle");
        no.setStyleName("circle");
        yes.setIcon(new ThemeResource("layouts/images/apply.png"));
        no.setIcon(new ThemeResource("layouts/images/cross.png"));
        setModal(true);
        setResizable(false);
        setStyleName("confirmationDialog");
        setWidth(25, Unit.PERCENTAGE);
        VerticalLayout ly = new VerticalLayout();
        ly.setSpacing(true);
        if ((yesCaption != null) && (yesCaption.trim().length() != 0)) yes.setCaption(yesCaption);
        if ((noCaption != null) && (noCaption.trim().length() != 0)) no.setCaption(noCaption);
        this.callback = callback;

        if (question != null && question.trim().length() != 0) {
            Label lbl = new Label(question);
            lbl.setContentMode(ContentMode.HTML);
            ly.addComponent(lbl);
        }

        setClosable(true);

        HorizontalLayout hl = new HorizontalLayout(yes, no);
        hl.setMargin(new MarginInfo(false, true, true, true));
        hl.setSpacing(true);

//        ly.setSpacing(true);
        ly.addComponent(hl);
        ly.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
        ly.setMargin(new MarginInfo(true, true, false, true));

        setContent(ly);
    }

    public void buttonClick(Button.ClickEvent event) {

        callback.onDialogResult(event.getSource() == yes);
        this.close();
    }

    public interface Callback {

        public void onDialogResult(boolean resultIsYes);
    }

}
