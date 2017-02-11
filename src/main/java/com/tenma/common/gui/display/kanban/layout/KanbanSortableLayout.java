package com.tenma.common.gui.display.kanban.layout;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.*;

/**
 * User: ndwijaya
 * Date: 5/31/13
 * Time: 4:43 PM
 */
public class KanbanSortableLayout extends CustomComponent {

    private final AbstractOrderedLayout layout;
    private final DropHandler dropHandler;

    public KanbanSortableLayout() {
        layout = new VerticalLayout();
        dropHandler = new ReorderLayoutDropHandler(layout);

        final DragAndDropWrapper pane = new DragAndDropWrapper(layout);
        setCompositionRoot(pane);
    }

    public void addComponent(final Component component) {
        final WrappedComponent wrapper = new WrappedComponent(component,
                dropHandler);
        wrapper.setSizeUndefined();
        component.setHeight("100%");
        wrapper.setHeight("100%");
        layout.addComponent(wrapper);
    }
}
