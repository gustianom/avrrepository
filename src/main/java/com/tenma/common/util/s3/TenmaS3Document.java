package com.tenma.common.util.s3;

import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by ndwijaya on 4/18/2016.
 */
public class TenmaS3Document extends TenmaPanel {
    private final String ICON = "DOC_ICON";
    private final String DOC_FOLDER = "DOC_FOLDER";
    private final String DOC_SIZE = "DOC_SIZE";
    private final String DOC_LAST_MODIFY = "DOC_LAST_MODIFY";
    private final String DOC_BUCKET = "DOC_BUCKET";
    protected TenmaTableContainer container;
    private Table table;
    private List resultset;
    private TenmaS3DocModel selectedDoc;

    public TenmaS3Document() {
        System.out.println("TenmaS3Document.TenmaS3Document");
        initUI();
        loadS3Data();
        setResultsTableValues();
    }

    private void loadS3Data() {
        List<TenmaS3DocModel> ls = TenmaS3Util.getInstance().listBucket();
        System.out.println("ls = " + ls.size());
        resultset = ls;
    }

    private void initUI() {
        table = new Table();
        table.setSelectable(true);
        container = new TenmaTableContainer();

        container.addContainerProperty(ICON, Button.class, null);
        container.addContainerProperty(DOC_FOLDER, String.class, "");
        container.addContainerProperty(DOC_SIZE, Long.class, "");
        container.addContainerProperty(DOC_LAST_MODIFY, String.class, "");
        container.addContainerProperty(DOC_BUCKET, Boolean.class, Boolean.FALSE);

        table.setContainerDataSource(container);
        table.commit();

        table.setWidth("800px");
        table.setImmediate(true);
        table.setVisibleColumns(new Object[]{ICON, DOC_FOLDER, DOC_SIZE, DOC_LAST_MODIFY});
        table.setColumnWidth(ICON, 50);
        table.setColumnWidth(DOC_SIZE, 100);
        table.setColumnWidth(DOC_LAST_MODIFY, 200);
        table.addItemClickListener(
                new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent event) {
                        Item item = event.getItem();
                        if (item != null) {
                            TenmaS3DocModel pcm = new TenmaS3DocModel();
                            pcm.setDocName(item.getItemProperty(DOC_FOLDER).getValue().toString());
                            pcm.setDocSize((Long) item.getItemProperty(DOC_SIZE).getValue());
                            pcm.setLastModify(TenmaConverter.stringToDate(item.getItemProperty(DOC_LAST_MODIFY).getValue().toString(), "dd-MMM-yyyy"));
                            pcm.setIsBucket((Boolean) item.getItemProperty(DOC_BUCKET).getValue());
                            setSelectedDoc(pcm);
                        }

                        if (item != null && event.isDoubleClick() && selectedDoc.isBucket()) {
                            table = new Table();
                            resultset = TenmaS3Util.getInstance().listDocOnBucket(selectedDoc.getDocName());
                            setResultsTableValues();
                        }

                    }
                }
        );

        VerticalLayout vl = new VerticalLayout();
        vl.setMargin(true);
        vl.addComponent(table);
        setContent(vl);
    }

    private void setSelectedDoc(TenmaS3DocModel doc) {
        selectedDoc = doc;
    }

    public void setResultsTableValues() {
        container.removeAllItems();
        System.out.println("TenmaS3Document.setResultsTableValues");
        for (int i = 0; i < resultset.size(); i++) {
            TenmaS3DocModel mdl = (TenmaS3DocModel) resultset.get(i);
            Item id = container.addItem(i);
            Button btn;
            if (mdl.isBucket()) {
                btn = new Button(new ThemeResource("layouts/images/16/023.png"));
                btn.setPrimaryStyleName("btn-blank");
            } else {
                btn = new Button(new ThemeResource("layouts/images/16/folder.png"));
                btn.setPrimaryStyleName("btn-blank");
            }
            btn.addClickListener(e -> {
                resultset = TenmaS3Util.getInstance().listDocOnBucket(selectedDoc.getDocName());
                setResultsTableValues();
            });

            id.getItemProperty(ICON).setValue(btn);
            id.getItemProperty(DOC_FOLDER).setValue(mdl.getDocName());
            id.getItemProperty(DOC_SIZE).setValue(mdl.getDocSize());
            id.getItemProperty(DOC_LAST_MODIFY).setValue(TenmaConverter.dateToString(mdl.getLastModify(), "dd-MMM-yyyy"));
            id.getItemProperty(DOC_BUCKET).setValue(mdl.isBucket());
        }
        if (resultset.size() > 20)
            table.setPageLength(20);
        else
            table.setPageLength(resultset.size());
        table.setSizeFull();
        setSizeUndefined();
    }

}
