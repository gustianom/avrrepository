package com.tenma.common.gui.display.community;

import com.tenma.common.TA;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 3/12/14.
 */
public abstract class CommunityListPaging extends Panel implements Button.ClickListener {
    public final String PAGING_INDEX_ID = "PAGINGINDEX-";
    private final Params param;
    public int totalDataRows = 0;
    public int pageSize = 0;
    private Panel panel;
    private TenmaLog logger;

    private int pageIndex = 1;
    private VerticalLayout pagingLayout;
    private Panel pagingArea;
    private int itotalPage;
    private List loadedList;


    public CommunityListPaging(int pageSize) {
        logger = TenmaLog.getInstance();
        param = TA.getCurrent().getParams();
        this.pageSize = pageSize;
        preparingListPaging();
    }

    private void preparingListPaging() {
        panel = new Panel();
        panel.setWidth("100%");
        pagingArea = new Panel();
        pagingArea.setStyleName("pageare");

        pagingArea.setWidth("100%");
        pagingArea.setHeight("20px");
        pagingArea.setVisible(false);
        pagingLayout = new VerticalLayout();
        VerticalLayout nvl = new VerticalLayout();
        nvl.setSpacing(true);
        pagingLayout.setWidth("100%");
//        layout.setHeight("90%");
        nvl.addComponent(pagingArea);
        nvl.addComponent(panel);
        pagingLayout.addComponent(nvl);
        setContent(pagingLayout);
    }


    public final void addDataList(Component component) {
        panel.setContent(component);
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

    public final List loadPagingData(HashMap parameterMap, String sortField, String sortOrder, String loggingCaption, TenmaHelper helper) {
        String searchKey = null;
        if (getTextSearch() != null)
            searchKey = getTextSearch().getValue();

        System.out.println("TenmaMasterList.loadPagingData pageIndex = " + pageIndex + " searchKey = [" + searchKey + "], sortField = [" + sortField + "], sortOrder = [" + sortOrder + "], loggingCaption = [" + loggingCaption + "], helper = [" + helper + "]");
        if (parameterMap == null) parameterMap = new HashMap();

        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);

        List list = new ArrayList();

        if ((searchKey != null) && (searchKey.trim().length() != 0))
            if (searchKey.indexOf("%") == -1)
                searchKey = new StringBuffer().append("%").append(searchKey).append("%").toString();
        parameterMap.put(Constants.HEADER_SEARCH, searchKey);


        parameterMap.put(Constants.RECORDSELECT_SKIP, skip);
        parameterMap.put(Constants.RECORDSELECT_MAX, pageSize);
        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, sortField);
        parameterMap.put(Constants.RECORDSELECT_SORTORDER, sortOrder);

        try {
            String lc = getLogger().openLog();
            list = helper.getListRenderer(parameterMap, true);

            // avoid to query tot record if the first query record is less than pagesize
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
        setLoadedList(list);

        return list;
    }

    protected abstract TextField getTextSearch();

    public final List loadPagingData(List listOfData, String loggingCaption) {
        String searchKey = getTextSearch().getValue();
        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);
        List list = new ArrayList();

        try {
            String lc = getLogger().openLog();
            if (listOfData.size() <= pageSize)
                list.addAll(listOfData);
            else
                list = listOfData.subList(skip, skip + pageSize);

            int totalSize = listOfData.size();
            setTotalDataRows(totalSize);
            updatePagingPage();
            getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, loggingCaption);
        } catch (Exception e) {
            Notification.show("Error Occured, please contact your administrator", Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
        setLoadedList(list);
        return list;
    }

    private void updatePagingPage() {
        int totalSize = getTotalDataRows();
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
            generalLay.setComponentAlignment(lay, Alignment.MIDDLE_LEFT);
            pagingArea.setContent(generalLay);
            pagingArea.setWidth("100%");
            pagingArea.setHeight("20px");
        } else
            pagingArea.setVisible(false);
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
            btnfirst.setPrimaryStyleName("page");
            lay.addComponent(btnfirst);

            Button btnprev = new Button(param.getLabel("default.prev"), this);
            btnprev.setId("PREV_PAGING_ID");
            btnprev.setPrimaryStyleName("page");
            lay.addComponent(btnprev);
        }
        for (int i = start; i < cnt; i++) {
            String ttlID = new StringBuffer().append(PAGING_INDEX_ID).append(i + 1).toString();
            Button btn = new Button(String.valueOf(i + 1), this);
            btn.setId(ttlID);
            if (i == getPageIndex() - 1)
                btn.setPrimaryStyleName("page-active");
//               btn.setPrimaryStyleName("badge badge-info");
            else
                btn.setPrimaryStyleName("page");
//                btn.setPrimaryStyleName("badge badge-inverse");
            lay.addComponent(btn);
        }

        if (getPageIndex() < itotalPage) {
            Button btnnext = new Button(param.getLabel("default.next"), this);
            btnnext.setId("NEXT_PAGING_ID");
            btnnext.setPrimaryStyleName("page");
            lay.addComponent(btnnext);

            Button btnlast = new Button(new StringBuffer().append(param.getLabel("default.last")).append("(").append(itotalPage).append(")").toString(), this);
            btnlast.setId("LAST_PAGING_ID");
            btnlast.setPrimaryStyleName("page");
            lay.addComponent(btnlast);
        }


    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String id = clickEvent.getButton().getId();
        System.out.println("CommunityListPaging.buttonClick id = " + id);
        if ((id != null) && (id.trim().length() != 0)) {
            TenmaMonitor.getInstance().pageLoaded();
            if (id.startsWith(PAGING_INDEX_ID)) {
                String idx = id.substring(PAGING_INDEX_ID.length());
                int nextPage = new Integer(idx);
                setPageIndex(nextPage);
                refreshTable();
            } else if ("NEXT_PAGING_ID".equals(id)) {
                int nextPage = getPageIndex() + 1;
                if (nextPage > itotalPage)
                    nextPage = itotalPage;
                setPageIndex(nextPage);
                refreshTable();
            } else if ("PREV_PAGING_ID".equals(id)) {
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
    }

    public List getLoadedList() {
        return loadedList;
    }

    public void setLoadedList(List loadedList) {
        this.loadedList = loadedList;
    }

    public TenmaLog getLogger() {
        return logger;
    }

    protected abstract void doPrint();


}
