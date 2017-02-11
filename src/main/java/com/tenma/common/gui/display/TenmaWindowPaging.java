package com.tenma.common.gui.display;


import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.util.Constants;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: anom
 * Date: 6/27/13
 * Time: 4:43 PM
 */
public abstract class TenmaWindowPaging extends TenmaWindow implements Button.ClickListener {

    public final String PAGING_INDEX_ID = "PAGINGINDEX-";
    public int totalDataRows = 0;
    public int pageSize = 0;
    public VerticalLayout pagingLayout;
    private Panel panel;
    private int pageIndex = 1;
    private Panel pagingArea;
    private int itotalPage;
    private List loadedList;
    private Button searchButton;
    private TextField searchText;


    public TenmaWindowPaging() {

        preparingListPaging();

    }

    private void preparingListPaging() {
        pageSize = param.getPageSize();

        panel = new Panel();
        panel.setWidth("100%");
        pageSize = param.getPageSize();
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
//        nvl.add
        nvl.addComponent(pagingArea);
        nvl.addComponent(panel);
        pagingLayout.addComponent(nvl);
        setContent(pagingLayout);
    }

    public final void setContent(Component content) {
        super.setContent(content);
    }


    public void doRefresh(Object newData) {

    }

    public Object getSelectedObject() {
        return null;
    }

    public void setSelectedObject(Object obj) {

    }

    public final void addDataList(Component component) {
        panel.setContent(component);
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public TextField getSearchText() {
        return searchText;
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

    List loadPagingData(HashMap parameterMap, String sortField, String sortOrder, String loggingCaption, TenmaHelper helper) {
        String searchKey = getSearchText().getValue();
        System.out.println("TenmaMasterList.loadPagingData pageIndex = " + pageIndex + " searchKey = [" + searchKey + "], sortField = [" + sortField + "], sortOrder = [" + sortOrder + "], loggingCaption = [" + loggingCaption + "], helper = [" + helper + "]");
        if (parameterMap == null) parameterMap = new HashMap();
        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);
        List list = new ArrayList();
        if ((searchKey != null) && (searchKey.trim().length() != 0))
            if (searchKey.indexOf("%") == -1)
                searchKey = new StringBuilder().append("%").append(searchKey).append("%").toString();
        parameterMap.put(Constants.HEADER_SEARCH, searchKey);
        parameterMap.put(Constants.RECORDSELECT_SKIP, skip);
        parameterMap.put(Constants.RECORDSELECT_MAX, pageSize);
        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, sortField);
        parameterMap.put(Constants.RECORDSELECT_SORTORDER, sortOrder);

        try {
            String lc = getLogger().openLog();
            list = helper.getListRenderer(parameterMap, true);
            pageSize = param.getPageSize();
            int totalSize = list.size();
            if (totalSize >= pageSize || skip.intValue() > 0)
                totalSize = helper.countTotalList(parameterMap);
            setTotalDataRows(totalSize);
            updatePagingPage();
            getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, loggingCaption);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLoadedList(list);
        return list;
    }


    public final List loadPagingData(List listOfData, String loggingCaption) {
        String searchKey = getSearchText().getValue();
        pageSize = param.getPageSize();
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
            e.printStackTrace();
        }
        setLoadedList(list);
        return list;
    }

    private void updatePagingPage() {
        pageSize = param.getPageSize();
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
            btnfirst.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btnfirst);

            Button btnprev = new Button(param.getLabel("default.prev"), this);
            btnprev.setId("PREV_PAGING_ID");
            btnprev.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btnprev);
        }
        for (int i = start; i < cnt; i++) {
            String ttlID = new StringBuilder().append(PAGING_INDEX_ID).append(i + 1).toString();
            Button btn = new Button(String.valueOf(i + 1), this);
            btn.setId(ttlID);
            if (i == getPageIndex() - 1)
                btn.setPrimaryStyleName("selectedPaging");
//               btn.setPrimaryStyleName("badge badge-info");
            else
                btn.setPrimaryStyleName("unSelectedPaging");
//                btn.setPrimaryStyleName("badge badge-inverse");
            lay.addComponent(btn);
        }

        if (getPageIndex() < itotalPage) {
            Button btnnext = new Button(param.getLabel("default.next"), this);
            btnnext.setId("NEXT_PAGING_ID");
            btnnext.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btnnext);

            Button btnlast = new Button(new StringBuilder().append(param.getLabel("default.last")).append("(").append(itotalPage).append(")").toString(), this);
            btnlast.setId("LAST_PAGING_ID");
            btnlast.setPrimaryStyleName("unSelectedPaging");
            lay.addComponent(btnlast);
        }


    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String id = clickEvent.getButton().getId();
        System.out.println("TenmaWindowPaging.buttonClick id = " + id);
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

    }

    public List getLoadedList() {
        return loadedList;
    }

    public void setLoadedList(List loadedList) {
        this.loadedList = loadedList;
    }

}
