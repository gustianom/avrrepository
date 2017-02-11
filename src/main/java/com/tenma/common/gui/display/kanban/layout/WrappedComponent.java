package com.tenma.common.gui.display.kanban.layout;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

/**
 * User: ndwijaya
 * Date: 5/31/13
 * Time: 4:46 PM
 */
public class WrappedComponent extends DragAndDropWrapper {

    private final DropHandler dropHandler;

    public WrappedComponent(final Component content,
                            final DropHandler dropHandler) {
        super(content);
        this.dropHandler = dropHandler;
        setDragStartMode(DragStartMode.WRAPPER);
    }

    @Override
    public DropHandler getDropHandler() {
        return dropHandler;
    }

}
