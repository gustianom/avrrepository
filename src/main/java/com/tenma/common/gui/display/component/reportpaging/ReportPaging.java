/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.common.gui.display.component.reportpaging;

import com.tenma.common.TA;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.tenma.common.util.monitor.TenmaMonitor;
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
 */
public class ReportPaging extends Panel implements Button.ClickListener {
    public final static int PAGE_SIZE = 70;
    public final String PAGING_INDEX_ID = "PAGINGINDEX-";

    private final Params param;
    public int totalDataRows = 0;
    public int pageSize = 0;
    private TenmaLog logger;

    private int pageIndex = 1;
    private VerticalLayout pagingLayout;
    private Panel pagingArea;
    private int itotalPage;
//    private List loadedList;

    private int totalSizePage;
    private ReportPagingListener listener;

    private String sortField;

    public ReportPaging(ReportPagingListener listener) {

        this.listener = listener;
        logger = TenmaLog.getInstance();
        param = TA.getCurrent().getParams();
        preparingListPaging();
    }

    private void preparingListPaging() {
        pageSize = PAGE_SIZE; //param.getPageSize();

        pagingArea = new Panel();
        pagingArea.setStyleName("forumpageare");

        pagingArea.setWidth("100%");
        pagingArea.setHeight("20px");
        pagingArea.setVisible(false);
        pagingLayout = new VerticalLayout();
        VerticalLayout nvl = new VerticalLayout();
        nvl.setSpacing(true);
        pagingLayout.setWidth("100%");
        nvl.addComponent(pagingArea);
        pagingLayout.addComponent(nvl);
        setStyleName(ValoTheme.PANEL_BORDERLESS);
        setContent(pagingLayout);

    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public final Panel getPagingArea() {
        return pagingArea;
    }

    public final int getPageIndex() {
        return pageIndex;
    }

    public final void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public final int getTotalDataRows() {
        System.out.println("TenmaMasterList.getTotalDataRows " + totalDataRows);
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

        try {
            pageSize = PAGE_SIZE; //param.getPageSize();

            String lc = getLogger().openLog();
            totalSize = helper.countTotalList(parameterMap);
            setTotalDataRows(totalSize);

            updatePagingPageWithoutFirstLast();
            getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, loggingCaption);
        } catch (Exception e) {
            Notification.show("Error Occured, please contact your administrator", Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return totalSize;
    }

    public final List loadPagingData(HashMap parameterMap, String sortOrder, String loggingCaption, TenmaHelper helper) {

        if (parameterMap == null) parameterMap = new HashMap();

        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);

        List list = new ArrayList();


        parameterMap.put(Constants.RECORDSELECT_SKIP, skip);
        parameterMap.put(Constants.RECORDSELECT_MAX, pageSize);
        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, sortField);
        parameterMap.put(Constants.RECORDSELECT_SORTORDER, sortOrder);

        try {
            String lc = getLogger().openLog();
            list = helper.getListRenderer(parameterMap, true);

            // avoid to query tot record if the first query record is less than pagesize
            pageSize = PAGE_SIZE; //param.getPageSize();
            int totalSize = list.size();
            if (totalSize >= pageSize || skip.intValue() > 0)
                totalSize = helper.countTotalList(parameterMap);
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
        pageSize = PAGE_SIZE;//param.getPageSize();
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
            e.printStackTrace();
        }
//        setLoadedList(list);
        return list;
    }


    private void updatePagingPageWithoutFirstLast() {
        pageSize = PAGE_SIZE;//param.getPageSize();
        int totalSize = getTotalDataRows();
        totalSizePage = totalSize;
        if (totalSize > pageSize) {
            pagingArea.setVisible(true);

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

            HorizontalLayout generalLay = new HorizontalLayout();
            generalLay.setWidth("100%");
            generalLay.addComponent(lay);
            generalLay.setComponentAlignment(lay, Alignment.BOTTOM_LEFT);
            pagingArea.setContent(generalLay);
            pagingArea.setWidth("100%");
            pagingArea.setHeight("20px");
        } else
            pagingArea.setVisible(false);
    }

    private void updatePagingPage() {
        pageSize = PAGE_SIZE;//param.getPageSize();
        int totalSize = getTotalDataRows();
        totalSizePage = totalSize;
        if (totalSize > pageSize) {
            pagingArea.setVisible(true);

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

            HorizontalLayout generalLay = new HorizontalLayout();
            generalLay.setWidth("100%");
            generalLay.addComponent(lay);
            generalLay.setComponentAlignment(lay, Alignment.BOTTOM_LEFT);
            pagingArea.setContent(generalLay);
            pagingArea.setWidth("100%");
            pagingArea.setHeight("20px");
        } else
            pagingArea.setVisible(false);
    }

    private void populatePagingWithoutFirstLast(HorizontalLayout lay, int itotalPage) {
        int start = getPageIndex() - 5;
        if (start < 0) start = 0;


        int maxPagingView = 10;
        int cnt = start + maxPagingView;
        if (cnt >= itotalPage) cnt = itotalPage;


        for (int i = start; i < cnt; i++) {
            String ttlID = new StringBuffer().append(PAGING_INDEX_ID).append(i + 1).toString();
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
            btnfirst.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btnfirst);

            Button btnprev = new Button(param.getLabel("default.prev"), this);
            btnprev.setId("PREV_PAGING_ID");
            btnprev.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btnprev);
        }
        for (int i = start; i < cnt; i++) {
            String ttlID = new StringBuffer().append(PAGING_INDEX_ID).append(i + 1).toString();
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
            btnnext.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btnnext);

            Button btnlast = new Button(new StringBuffer().append(param.getLabel("default.last")).append("(").append(itotalPage).append(")").toString(), this);
            btnlast.setId("LAST_PAGING_ID");
            btnlast.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btnlast);
        }


    }

    public void setPageLast() {

    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
                pageSize = 1;//param.getPageSize();
                int nextPage = getPageIndex() + 1;
                if (nextPage > itotalPage)
                    nextPage = itotalPage;
                setPageIndex(nextPage);
                refreshTable();
            } else if ("PREV_PAGING_ID".equals(id)) {
                pageSize = 1;//param.getPageSize();
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

