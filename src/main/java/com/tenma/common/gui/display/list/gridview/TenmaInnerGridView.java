package com.tenma.common.gui.display.list.gridview;

import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 18/11/14.
 */
public abstract class TenmaInnerGridView extends VerticalLayout {
    public final void generateGridView(List ls, HashMap mapExistItem, int maxCols) {
        if (ls != null && ls.size() != 0) {
            int iCols = 0;

            HorizontalLayout cols = null;
            for (int i = 0; i < ls.size(); i++) {
                Object m = ls.get(i);
                if (iCols == 0) {
                    cols = new HorizontalLayout();
                    cols.setSpacing(true);
                }

                if (mapExistItem != null) {
                    if (!isExist(mapExistItem, m)) {
                        iCols++;
                        cols.addComponent(createViewMenu(m));
                    }
                } else {
                    cols.addComponent(createViewMenu(m));
                    iCols++;
                }

                if (iCols == maxCols) {
                    iCols = 0;
                    addComponent(cols);
                }
            }
            if (iCols < maxCols && cols != null)
                addComponent(cols);
        } else
            addComponent(new Label(getNoRecordAvailableLabel()));
    }

    protected abstract String getNoRecordAvailableLabel();


    public abstract boolean isExist(HashMap mapExistingItems, Object data);

    private VerticalLayout createViewMenu(Object data) {


        Component firstComponent = createFirstLayerFrameComponent(data);
        HorizontalLayout firstLayer = new HorizontalLayout(firstComponent);
        firstLayer.setComponentAlignment(firstComponent, Alignment.TOP_CENTER);
        firstLayer.setWidth(100, Unit.PERCENTAGE);
        firstLayer.setPrimaryStyleName(getFirstLayerStyleName());


        Component secondComponent = createSecondLayerFrameComponent(data);
        VerticalLayout secondLayer = new VerticalLayout();
        secondLayer.addComponent(secondComponent);
        secondLayer.setWidth(100, Unit.PERCENTAGE);
        secondLayer.setComponentAlignment(secondComponent, Alignment.TOP_CENTER);
        secondLayer.setPrimaryStyleName(getSecondLayerStyleName());


        Component thirdComponent = createThirdLayerFrameComponent(data);
        HorizontalLayout thirdLayer = new HorizontalLayout();
        thirdLayer.addComponent(thirdComponent);
        thirdLayer.setWidth(100, Unit.PERCENTAGE);
        thirdLayer.setComponentAlignment(thirdComponent, Alignment.MIDDLE_RIGHT);
        thirdLayer.setPrimaryStyleName(getThirdLayerStyleName());

        VerticalLayout ly = new VerticalLayout(firstLayer, secondLayer, thirdLayer);


//        ly.setWidth(160, Unit.PIXELS);
//        ly.setHeight(210, Unit.PIXELS);
        ly.setPrimaryStyleName(getFrameItemStyleName());

        if (getFrameDescription(data) != null && getFrameDescription(data).trim().length() != 0)
            ly.setDescription(getFrameDescription(data));


        ly.setComponentAlignment(firstLayer, Alignment.TOP_CENTER);
        ly.setComponentAlignment(secondLayer, Alignment.MIDDLE_CENTER);
        ly.setComponentAlignment(thirdLayer, Alignment.BOTTOM_RIGHT);
        return ly;
    }

    protected abstract String getFrameItemStyleName();

    protected abstract String getThirdLayerStyleName();

    protected abstract Component createThirdLayerFrameComponent(Object data);

    protected abstract Component createSecondLayerFrameComponent(Object data);

    protected abstract String getSecondLayerStyleName();

    protected abstract String getFirstLayerStyleName();

    protected abstract Component createFirstLayerFrameComponent(Object data);

    protected abstract String getFrameDescription(Object data);

}
