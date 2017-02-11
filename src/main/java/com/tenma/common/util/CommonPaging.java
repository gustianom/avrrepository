/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.common.util;

import com.tenma.common.TA;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.util.error.Severity;
import com.tenma.common.util.error.TenmaStackTraceManager;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 3/13/14
 * Time: 2:45 PM
 * <p>
 * 11.12.14 Added Features
 * - support custom paging size
 * - support custom helper method execute with implement CommonPagingHelper
 * - showing total record on the top right (TBD)
 * -
 */
public class CommonPaging extends Panel implements Button.ClickListener {
    public static final String DEF_BG_COLOR = "background-color: rgb(229, 229, 229);";
    public static final String WHITE_BG_COLOR = "background-color: rgb(255, 255, 255);";
    public final String PAGING_INDEX_ID = "PAGINGINDEX-";
    private final Params param;
    public int totalDataRows = 0;
    private boolean customPage = false;
    private boolean showTotalRow = false;
    private int pageSize = 0;
    private int pageIndex = 1;
    //    private Panel pagingArea;
    private HorizontalLayout hzLayout;
    private int itotalPage;
    private int totalSizePage;
    private CommonPagingListener listener;

    private String sortField;
    private TenmaLog logger;

    public CommonPaging(CommonPagingListener listener) {
        super();
        this.listener = listener;
        param = TA.getCurrent().getParams();
        preparingListPaging();
        setBackgroundColor(DEF_BG_COLOR);
        logger = TenmaLog.getInstance();
    }

    public void setCustomPageSize(int pageSize) {
        this.pageSize = pageSize;
        customPage = true;
    }

    public void showTotalRow(boolean totalROw) {
        showTotalRow = totalROw;
    }

    private void updatePageSize() {
        if (!customPage) {
            pageSize = param.getPageSize();
        }
    }

    public void setBackgroundColor(String bgColor) {
        setStyleName("commonPaging");
        Page.Styles styles = Page.getCurrent().getStyles();
        StringBuilder buf = new StringBuilder();
        buf.append("div.v-panel-caption-commonPaging {}");
        buf.append("div.v-panel-nocaption-commonPaging,  ");
        buf.append("div.v-panel-deco-commonPaging,");
        buf.append("div.v-panel-content-commonPaging {");
        buf.append(" border: none;");
        buf.append(bgColor);
        buf.append("}");
        styles.add(buf.toString());
    }


    private void preparingListPaging() {
        updatePageSize();
        hzLayout = new HorizontalLayout();
        hzLayout.setVisible(false);
//        hzLayout.setWidth("100%");
//        setWidth("100%");
        setStyleName(ValoTheme.PANEL_BORDERLESS);
        setContent(hzLayout);
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public final HorizontalLayout getPagingArea() {
        return hzLayout;
    }

    public final int getPageIndex() {
        return pageIndex;
    }

    public final void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public final int getTotalDataRows() {
        return totalDataRows;
    }

    public final void setTotalDataRows(int totalDataRows) {
        this.totalDataRows = totalDataRows;
    }

    public int loadPagingCount(HashMap parameterMap, String sortField, String sortOrder, String loggingCaption, TenmaHelper helper) {
        if (parameterMap == null) parameterMap = new HashMap();

        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);

        int totalSize = 0;

        parameterMap.put(Constants.RECORDSELECT_SKIP, skip);
        parameterMap.put(Constants.RECORDSELECT_MAX, pageSize);
        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, sortField);
        parameterMap.put(Constants.RECORDSELECT_SORTORDER, sortOrder);

        System.out.println("CommonPaging.loadPagingCount -- > " + parameterMap);
        try {
            updatePageSize();

            String lc = getLogger().openLog();
            totalSize = helper.countTotalList(parameterMap);

            System.out.println("CommonPaging.loadPagingCount TOTAL  lIst  : " + totalSize);

            setTotalDataRows(totalSize);

            updatePagingPageWithoutFirstLast();
            getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, loggingCaption);
        } catch (Exception e) {
            Notification.show("Error Occured, please contact your administrator", Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return totalSize;
    }


    public final List loadPagingData(int pageSize, HashMap parameterMap, String sortField, String sortOrder, String loggingCaption, CommonPagingHelper helper) {
        return loadPagingData(parameterMap, sortField, sortOrder, loggingCaption, helper);
    }

    public final List loadPagingData(HashMap parameterMap, String sortField, String sortOrder, String loggingCaption, CommonPagingHelper helper) {

        if (parameterMap == null) parameterMap = new HashMap();

        System.out.println("CommonPaging.loadPagingData pageIndex = " + pageIndex);

        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);

        System.out.println("CommonPaging.loadPagingData skip = " + skip);


        List list = new ArrayList();


        parameterMap.put(Constants.RECORDSELECT_SKIP, skip);
        parameterMap.put(Constants.RECORDSELECT_MAX, pageSize);
        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, sortField);
        parameterMap.put(Constants.RECORDSELECT_SORTORDER, sortOrder);

        System.out.println("CommonPaging.loadPagingData -> " + parameterMap);

        try {
            String lc = getLogger().openLog();
            list = helper.getCustomListRenderer(parameterMap, true);

            // avoid to query tot record if the first query record is less than pagesize
            updatePageSize();

            int totalSize = list.size();
            System.out.println("CommonPaging.loadPagingData total size " + totalSize);
            if (totalSize >= pageSize || skip.intValue() > 0)
                totalSize = helper.countTotalList(parameterMap);


            System.out.println("CommonPaging.loadPagingData new total size " + totalSize);
            setTotalDataRows(totalSize);


            updatePagingPage();
            getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, loggingCaption);
        } catch (Exception e) {
            Notification.show("Error Occured, please contact your administrator", Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
//        setLoadedList(list);
        return list;
    }

    public final List loadPagingData(List listOfData, String loggingCaption) {
        return pagingList(listOfData, loggingCaption, false);
    }


    public final List loadPagingData(List listOfData, String loggingCaption, boolean includePaging) {
        return pagingList(listOfData, loggingCaption, includePaging);
    }


    private List pagingList(List listOfData, String loggingCaption, boolean includePaging) {
        updatePageSize();
        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);
        List list = new ArrayList();

        try {
            String lc = getLogger().openLog();
            if (listOfData.size() <= pageSize)
                list.addAll(listOfData);
            else
                list = listOfData.subList(skip, skip + pageSize);

            int totalSize = listOfData.size();
            if (!includePaging)
                setTotalDataRows(totalSize);
            updatePagingPage();
            getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, loggingCaption);
        } catch (Exception e) {
            Notification.show("Error Occured, please contact your administrator", Notification.Type.ERROR_MESSAGE);
            TenmaStackTraceManager.getInstance().addNewStackTrace(Severity.SEVERITY40, e);
        }
//        setLoadedList(list);
        return list;
    }


    private void updatePagingPageWithoutFirstLast() {
        updatePageSize();
        int totalSize = getTotalDataRows();
        totalSizePage = totalSize;
        if (totalSize > pageSize) {
            hzLayout.setVisible(true);

            if (getPageIndex() * pageSize > totalSize) {
                setPageIndex(((totalSize - 1) / pageSize) + 1);
            }
            if (getPageIndex() * pageSize < 1) {
                setPageIndex(1);
            }
            itotalPage = totalSize % pageSize == 0 ? (totalSize / pageSize) : ((totalSize / pageSize) + 1);

            HorizontalLayout lay = new HorizontalLayout();
            lay.setSpacing(true);

            populatePagingWithoutFirstLast(lay, itotalPage);

//            HorizontalLayout generalLay = new HorizontalLayout();
//            hzLayout.setWidth("100%");
            hzLayout.addComponent(lay);
            hzLayout.setComponentAlignment(lay, Alignment.BOTTOM_LEFT);
//            pagingArea.setContent(generalLay);
//            pagingArea.setWidth("100%");
//            pagingArea.setHeight("20px");
        } else
            hzLayout.setVisible(false);
    }

    private void updatePagingPage() {
        updatePageSize();
        int totalSize = getTotalDataRows();
        totalSizePage = totalSize;
        if (totalSize > pageSize) {
            hzLayout.setVisible(true);
            hzLayout.removeAllComponents();

            if (getPageIndex() * pageSize > totalSize) {
                setPageIndex(((totalSize - 1) / pageSize) + 1);
            }
            if (getPageIndex() * pageSize < 1) {
                setPageIndex(1);
            }
            itotalPage = totalSize % pageSize == 0 ? (totalSize / pageSize) : ((totalSize / pageSize) + 1);

            HorizontalLayout lay = new HorizontalLayout();
            lay.setSpacing(true);

            populatePaging(lay, itotalPage);

//            HorizontalLayout generalLay = new HorizontalLayout();
//            generalLay.setWidth("100%");
            Label space = new Label("");
            hzLayout.addComponent(lay);
            hzLayout.setComponentAlignment(lay, Alignment.BOTTOM_LEFT);
            hzLayout.addComponent(space);
            hzLayout.setComponentAlignment(space, Alignment.BOTTOM_CENTER);
            hzLayout.setMargin(new MarginInfo(false, false, false, true));
            if (showTotalRow) {

                Label totalRow = new Label("(" + String.valueOf(totalSizePage) + " " + TA.getCurrent().getParams().getLabel("page.record") + ")");

                hzLayout.addComponent(totalRow);

                hzLayout.setComponentAlignment(totalRow, Alignment.BOTTOM_RIGHT);
                hzLayout.setExpandRatio(space, 1.0f);
            }
            hzLayout.setWidth("100%");
//            pagingArea.setContent(generalLay);
//            pagingArea.setWidthUndefined();
//            pagingArea.setHeight("20px");
        } else
            hzLayout.setVisible(false);
    }

    private void populatePagingWithoutFirstLast(HorizontalLayout lay, int itotalPage) {
        int start = getPageIndex() - 5;
        if (start < 0) start = 0;


        int maxPagingView = 10;
        int cnt = start + maxPagingView;
        if (cnt >= itotalPage) cnt = itotalPage;


        for (int i = start; i < cnt; i++) {
            String ttlID = new StringBuilder().append(PAGING_INDEX_ID).append(i + 1).toString();
            Button btn = new Button(String.valueOf(i + 1), this);
            btn.setId(ttlID);
            if (i == getPageIndex() - 1)
                btn.setPrimaryStyleName("selectedPaging");
            else
                btn.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btn);
        }

    }


    private void populatePaging(HorizontalLayout lay, int itotalPage) {
        int start = getPageIndex() - 5;
        if (start < 0) start = 0;


        int maxPagingView = 10;
        int cnt = start + maxPagingView;
        if (cnt >= itotalPage) cnt = itotalPage;

        if (getPageIndex() > 1) {
            Button btnfirst = new Button(param.getLabel("default.first"), this);
            btnfirst.setId("FIRST_PAGING_ID");
            btnfirst.setPrimaryStyleName("unSelectedPaging-nb");
            lay.addComponent(btnfirst);

            Button btnprev = new Button(param.getLabel("default.prev"), this);
            btnprev.setId("PREV_PAGING_ID");
            btnprev.setPrimaryStyleName("unSelectedPaging-nb");
            lay.addComponent(btnprev);
        }
        for (int i = start; i < cnt; i++) {
            String ttlID = new StringBuilder().append(PAGING_INDEX_ID).append(i + 1).toString();
            Button btn = new Button(String.valueOf(i + 1), this);
            btn.setId(ttlID);
            if (i == getPageIndex() - 1)
                btn.setPrimaryStyleName("selectedPaging");
            else
                btn.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btn);
        }

        if (getPageIndex() < itotalPage) {
            Button btnnext = new Button(param.getLabel("default.next"), this);
            btnnext.setId("NEXT_PAGING_ID");
            btnnext.setPrimaryStyleName("unSelectedPaging-nb");
            lay.addComponent(btnnext);

            Button btnlast = new Button(new StringBuilder().append(param.getLabel("default.last")).append("(").append(itotalPage).append(")").toString(), this);
            btnlast.setId("LAST_PAGING_ID");
            btnlast.setPrimaryStyleName("unSelectedPaging-nb");
            lay.addComponent(btnlast);
        }


    }

    public void setPageLast() {

    }

    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String id = clickEvent.getButton().getId();
        System.out.println("CommonPaging.buttonClick  id = " + id);
        if ((id != null) && (id.trim().length() != 0)) {
            TenmaMonitor.getInstance().pageLoaded();
            if (id.startsWith(PAGING_INDEX_ID)) {
                String idx = id.substring(PAGING_INDEX_ID.length());
                int nextPage = new Integer(idx);
                setPageIndex(nextPage);
                refreshTable();
            } else if ("NEXT_PAGING_ID".equals(id)) {
                pageSize = param.getPageSize();
                int nextPage = getPageIndex() + 1;
                if (nextPage > itotalPage)
                    nextPage = itotalPage;
                setPageIndex(nextPage);
                refreshTable();
            } else if ("PREV_PAGING_ID".equals(id)) {
                pageSize = param.getPageSize();
                int nextPage = getPageIndex() - 1;
                if (nextPage < 1)
                    nextPage = 1;
                setPageIndex(nextPage);
                refreshTable();
            } else if ("FIRST_PAGING_ID".equals(id)) {
                setPageIndex(0);
                refreshTable();
            } else if ("LAST_PAGING_ID".equals(id)) {
                setPageIndex(itotalPage);
                refreshTable();
            }

        }
    }

    public void refreshTable() {
        listener.refreshUI();
    }

    public TenmaLog getLogger() {
        return logger;
    }

    @Override
    public String toString() {
        return totalSizePage + " o " + super.toString();
    }

    public void refreshLast() {
        int totalSize = getTotalDataRows();
        itotalPage = totalSize % pageSize == 0 ? (totalSize / pageSize) : ((totalSize / pageSize) + 1);
        setPageIndex(itotalPage);
        refreshTable();
    }
}

