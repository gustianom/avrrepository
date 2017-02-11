package com.tenma.common;

import com.tenma.common.gui.display.TenmaMasterList;
import com.tenma.common.gui.display.TenmaMetroMasterList;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.ui.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by ndwijaya on 12/19/2014.
 */
public class TenmaTable extends Table {
    public static final String DD_MM_YYYY_HHMM = "dd-MMM-yyyy HH:mm";
    public static final String DD_MM_YYYY = "dd-MMM-yyyy";
    protected final Logger logger;
    private TenmaMetroMasterList parent2;
    private TenmaMasterList parent;
    private boolean ascending = false;
    private String[] dbColumn;
    private String dateFormatType;

    public TenmaTable(TenmaMasterList parent) {
        logger = LoggerFactory.getLogger(TenmaTable.class);
        this.parent = parent;
        this.dateFormatType = DD_MM_YYYY_HHMM;
        addHeaderClickListener(createHeaderClicListener());
    }

    public TenmaTable(TenmaMetroMasterList parent) {
        logger = LoggerFactory.getLogger(TenmaTable.class);
        this.parent2 = parent;
        this.dateFormatType = DD_MM_YYYY_HHMM;
        addHeaderClickListener(createHeaderClicListener());
    }


    public void setDateFormatType(String formatType) {
        this.dateFormatType = formatType;
    }

    public void setColumnDate(String columnId) {
        setColumnDate(columnId, dateFormatType);
    }

    public void setColumnDate(String columnId, final String dateFormatType) {
        setConverter(columnId, new StringToDateConverter() {
            @Override
            protected DateFormat getFormat(Locale locale) {
                return new SimpleDateFormat(dateFormatType);
            }
        });

    }

    public void setDbColumn(String[] dbColumn) {
        logger.info("TenmaTable.setDbColumn");
        this.dbColumn = dbColumn;
    }


    private HeaderClickListener createHeaderClicListener() {
        HeaderClickListener headerListener = new HeaderClickListener() {
            @Override
            public void headerClick(HeaderClickEvent event) {
                fireTable(event.getPropertyId().toString());
                ascending = !ascending;
                setSortAscending(!ascending);
            }
        };
        return headerListener;
    }

    private void fireTable(String column) {

        String dbColumn = null;
        try {
            logger.info("TenmaTable.fireTable 1");
            dbColumn = changeColumnToDBField(column);
        } catch (Exception e) {
            logger.info("columnId :" + column + " is not defined");
        }

        if (dbColumn != null) {
            parent.fireCustomDBSort(dbColumn, ascending);
            parent2.fireCustomDBSort(dbColumn, ascending);
        } else {
            logger.info("Table DB Sort is not implemented !!!");
        }
    }

    private String changeColumnToDBField(String headerColumn) throws Exception {
        Collection columheader = getContainerPropertyIds();
        Iterator iter = columheader.iterator();
        logger.info("columheader = " + columheader);
        int index = 0;
        for (int i = 0; iter.hasNext(); i++) {
            Object o = iter.next();
            logger.info("columheader = " + o.toString() + "_________vs______" + headerColumn);
            if (o.equals(headerColumn)) {
                index = i;
                logger.info("header column is = " + o);
                break;
            }
        }
        logger.info("DB column is = " + dbColumn[index]);
        return dbColumn[index];
    }
}
