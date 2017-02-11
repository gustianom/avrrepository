package com.tenma.common.gui.display.kanban;

import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;


/**
 * User: ndwijaya
 * Date: 5/30/13
 * Time: 11:33 PM
 */

public class KanbanColumnHeader extends Panel implements Button.ClickListener {
    private static final int defaultWidth = 187;
    private static final int defaultHeight = 30;
    Button button;
    Object exeAddObject = null;
    private String columnHeader;
    private boolean addButton;

    public KanbanColumnHeader(String columnHeader, Object exeAdd, boolean addBtn) {
        this.columnHeader = columnHeader;
        this.exeAddObject = exeAdd;
        this.addButton = addBtn;
        createUI();
    }

    private void createUI() {
        HorizontalLayout layout = new HorizontalLayout();
        Label columnLabel = new Label(columnHeader);
        columnLabel.setPrimaryStyleName("columHeaderTitle");
//        Label space = new Label(" ");

        button = new Button();
//        space.setPrimaryStyleName("columHeaderSpace");
//        space.setWidth();
        button.setIcon(new ThemeResource(Constants.ADD_ICON));
        button.setPrimaryStyleName("columHeaderAdd");
        button.addClickListener(this);
        layout.addComponent(columnLabel);
//        layout.addComponent(space);
//        space.setSizeFull();

        if (addButton) {
            layout.addComponent(button);
        }
        setWidth(defaultWidth + "px");
        setHeight(defaultHeight + "px");
        layout.setComponentAlignment(columnLabel, Alignment.MIDDLE_LEFT);
        if (addButton) {
            layout.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
        }
        layout.setWidth("100%");
        setContent(layout);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getSource().equals(button)) {
            if (exeAddObject != null) {
                TenmaWindow dialog = (TenmaWindow) exeAddObject;
                dialog.setModal(true);
                dialog.setResizable(false);
                UI.getCurrent().addWindow(dialog);
            }
        }

    }
}
