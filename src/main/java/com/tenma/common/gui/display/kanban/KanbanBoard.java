package com.tenma.common.gui.display.kanban;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

import java.util.HashMap;

/**
 * User: ndwijaya
 * Date: 5/30/13
 * Time: 10:42 PM
 */

public class KanbanBoard extends CustomComponent {
    private static KanbanDS instanceDs;
    HashMap columnMap = new HashMap();
    private HorizontalLayout boardLayout = new HorizontalLayout();
    private Object exeAddObject = null;

    public KanbanBoard(Object exeAdd) {
        instanceDs = KanbanDS.getInstance();
        exeAddObject = exeAdd;
        setCompositionRoot(boardLayout);

    }

    public void addNewColumn(String arg, boolean addBtn) {
        KanbanColumn column = new KanbanColumn(arg, exeAddObject, addBtn);
        boardLayout.addComponent(column);
        columnMap.put(arg, column);
        instanceDs.addNewColumn(arg);
    }

    public void addNewColumn(String arg, boolean addBtn, int newHight) {
        KanbanColumn column = new KanbanColumn(arg, exeAddObject, addBtn, newHight);
        boardLayout.addComponent(column);
        columnMap.put(arg, column);
        instanceDs.addNewColumn(arg);
    }

    public void addNewColumn(String argColumId, KanbanColumnHeader columnHeader) {
        KanbanColumn column = new KanbanColumn(argColumId, columnHeader);
        boardLayout.addComponent(column);
        columnMap.put(argColumId, column);
        instanceDs.addNewColumn(argColumId);
    }

    public boolean addKanbanItem(String column, KanbanPanel panel) {
        KanbanColumn kcolumn = (KanbanColumn) columnMap.get(column);
        if (kcolumn != null) {
            kcolumn.addBoardItem(panel);
        }
        return false;
    }


}
