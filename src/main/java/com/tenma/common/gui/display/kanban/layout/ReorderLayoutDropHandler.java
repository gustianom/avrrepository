package com.tenma.common.gui.display.kanban.layout;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;

import java.util.Iterator;

/**
 * User: ndwijaya
 * Date: 5/31/13
 * Time: 4:50 PM
 */
public class ReorderLayoutDropHandler implements DropHandler {

    private final AbstractOrderedLayout layout;

    public ReorderLayoutDropHandler(final AbstractOrderedLayout layout) {
        this.layout = layout;
    }

    @Override
    public AcceptCriterion getAcceptCriterion() {
        return new Not(SourceIsTarget.get());
    }

    @Override
    public void drop(final DragAndDropEvent dropEvent) {
        final Transferable transferable = dropEvent.getTransferable();
        final Component sourceComponent = transferable.getSourceComponent();
        if (sourceComponent instanceof WrappedComponent) {
            final TargetDetails dropTargetData = dropEvent
                    .getTargetDetails();
            final DropTarget target = dropTargetData.getTarget();

            // find the location where to move the dragged component
            boolean sourceWasAfterTarget = true;
            int index = 0;
            final Iterator<Component> componentIterator = layout
                    .getComponentIterator();
            Component next = null;
            while (next != target && componentIterator.hasNext()) {
                next = componentIterator.next();
                if (next != sourceComponent) {
                    index++;
                } else {
                    sourceWasAfterTarget = false;
                }
            }
            if (next == null || next != target) {
                // component not found - if dragging from another layout
                return;
            }

            // drop on top of target?
            if (dropTargetData.getData("verticalLocation").equals(
                    VerticalDropLocation.MIDDLE.toString())) {
                if (sourceWasAfterTarget) {
                    index--;
                }
            }

            // drop before the target?
            else if (dropTargetData.getData("verticalLocation").equals(
                    VerticalDropLocation.BOTTOM.toString())) {
                index--;
                if (index < 0) {
                    index = 0;
                }
            }

            // move component within the layout
            layout.removeComponent(sourceComponent);
            layout.addComponent(sourceComponent, index);
        }
    }
}