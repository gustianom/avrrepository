package com.tenma.common.gui.display.kanban;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;

/**
 * User: ndwijaya
 * Date: 5/30/13
 * Time: 10:41 PM
 */
public class KanbanDS {
    private static KanbanDS instance;
    private static int board;
    private HashMap boardMap = new HashMap();
    private HashMap layoutMap = new HashMap();

    private KanbanDS() {
        board = 0;
    }

    public static KanbanDS getInstance() {
        if (instance == null) {
            instance = new KanbanDS();
        }
        return instance;
    }

    public void regLayout(String title, VerticalLayout verticalLayout) {
        layoutMap.put(title, verticalLayout);
    }

    public void addNewColumn(String columnId) {
    }

    public boolean isEnableToAdd(String title, int boardId) {
        boolean isEnable = false;
        String column = (String) boardMap.get(boardId);
        if (!(title.equals(column))) {
            isEnable = true;
        }
        return isEnable;
    }

    public void removeComponentAtOthers(Component component, int id, String newColumn) {
        String column = (String) boardMap.get(id);
        if (column != null) {
            VerticalLayout layout = (VerticalLayout) layoutMap.get(column);
            layout.removeComponent(component);
        }
    }

    public void addItemBoard(String columId, KanbanPanel panel) {
        boardMap.put(panel.getBoardId(), columId);
    }

    public synchronized int getNewId() {
        board++;
        System.out.println("board = " + board);
        return board;
    }
}
