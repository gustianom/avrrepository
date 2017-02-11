package com.tenma.common.gui.display.kanban;

import com.vaadin.ui.Panel;

/**
 * User: ndwijaya
 * Date: 5/30/13
 * Time: 10:39 PM
 */
public abstract class KanbanPanel extends Panel {
    protected int boardId;
    private int defaultWidth = 182;
    private int defaultHeight = 100;

    protected KanbanPanel(int boardId) {
        this.boardId = boardId;
        setWidth(defaultWidth + "px");
        setHeight(defaultHeight + "px");
    }

    protected KanbanPanel(int boardId, int width, int height) {
        this.defaultWidth = width;
        this.defaultHeight = height;
        this.boardId = boardId;
        setWidth(defaultWidth + "px");
        setHeight(defaultHeight + "px");
    }

    public int getBoardId() {
        return boardId;
    }

    abstract public KanbanPanel copyPanel();
}
