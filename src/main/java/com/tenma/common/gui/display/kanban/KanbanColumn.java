package com.tenma.common.gui.display.kanban;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * User: ndwijaya
 * Date: 5/30/13
 * Time: 11:27 PM
 */
public class KanbanColumn extends Panel {
    private static KanbanDS instanceDs;
    private static int defaultWidth = 190;
    private static int defaultheight = 500;
    final VerticalLayout slayout = new VerticalLayout();
    DragAndDropWrapper targetWrapper;
    private String columnTitle;
    private KanbanColumnHeader header;

    public KanbanColumn(String title, Object exeAdd, boolean addButton) {
        columnTitle = title;
        targetWrapper = new DragAndDropWrapper(slayout);
        setSize(defaultWidth, defaultheight);
        instanceDs = KanbanDS.getInstance();
        header = new KanbanColumnHeader(columnTitle, exeAdd, addButton);
        createUI();
        setHeaderStyle("kanbancolumheader");
        instanceDs.regLayout(columnTitle, slayout);
    }

    public KanbanColumn(String title, Object exeAdd, boolean addButton, int newhight) {
        defaultheight = newhight;
        columnTitle = title;
        targetWrapper = new DragAndDropWrapper(slayout);
        setSize(defaultWidth, defaultheight);
        instanceDs = KanbanDS.getInstance();
        header = new KanbanColumnHeader(columnTitle, exeAdd, addButton);
        createUI();
        setHeaderStyle("kanbancolumheader");
        instanceDs.regLayout(columnTitle, slayout);
    }

    public KanbanColumn(String title, KanbanColumnHeader header) {
        columnTitle = title;
        targetWrapper = new DragAndDropWrapper(slayout);
        setSize(defaultWidth, defaultheight);
        instanceDs = KanbanDS.getInstance();
        createUI();
        setHeaderStyle("kanbancolumheader");
        instanceDs.regLayout(columnTitle, slayout);
    }

    public void setHeaderStyle(String style) {
        header.setStyleName(style);
    }

    public void setSize(int width, int height) {
        targetWrapper.setWidth(width + "px");
        targetWrapper.setHeight(height + "px");
    }

    private void createUI() {
        slayout.addComponent(header);
        setContent(targetWrapper);
        targetWrapper.setDropHandler(new ColumnDropHandler());
    }

    public void addBoardItem(KanbanPanel panel) {
        DragAndDropWrapper wrapper = new DragAndDropWrapper(panel);
        wrapper.setSizeUndefined(); // Shrink to fit
        // Enable dragging the wrapper
        wrapper.setDragStartMode(DragStartMode.WRAPPER);
//        panel.setIcon(new ThemeResource("layouts/images/16/100.png"));
        slayout.addComponent(wrapper);
        instanceDs.addItemBoard(columnTitle, panel);
    }

    class ColumnDropHandler implements DropHandler {
        ColumnDropHandler() {
        }

        //        @Override
        public void drop(DragAndDropEvent event) {
            WrapperTransferable t =
                    (WrapperTransferable) event.getTransferable();

            TargetDetails tg = event.getTargetDetails();

            // Get the dragged component (not the wrapper)

            KanbanPanel src = null;
            Component c = t.getDraggedComponent();
            if (c instanceof KanbanPanel) {
                src = (KanbanPanel) c;
            } else {
                while (c.getParent() != null) {
                    System.out.println(c.getParent());
                    c = c.getParent();
                    if (c instanceof KanbanPanel) {
                        src = (KanbanPanel) c;
                        break;
                    }
                }
            }


            KanbanPanel panel = src.copyPanel();
            Component parent = src.getParent();
            System.out.println(parent);
            if (instanceDs.isEnableToAdd(columnTitle, panel.getBoardId())) {

                instanceDs.removeComponentAtOthers(parent, panel.getBoardId(), columnTitle);

                DragAndDropWrapper wrapper = new DragAndDropWrapper(panel);
                wrapper.setSizeUndefined(); // Shrink to fit

                // Enable dragging the wrapper
                wrapper.setDragStartMode(DragStartMode.WRAPPER);

                slayout.addComponent(wrapper);
                instanceDs.addItemBoard(columnTitle, panel);
            }
        }


        //        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }
    }
}
