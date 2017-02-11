package com.tenma.common.gui.display;

import com.tenma.auth.util.db.DaoHelper;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.CrudCode;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: anom
 * Date: 10/27/12
 * Time: 3:51 PM
 */

public abstract class TenmaListPaging extends TenmaPanel {
    public final String PAGING_INDEX_ID = "PAGINGINDEX-";
    public int totalDataRows = 0;
    public int pageSize = param.getPageSize();
    public VerticalLayout pagingLayout;
    private boolean showTotalRow = true;
    private Panel panel;
    private int pageIndex = 1;
    private Panel pagingArea;
    private int itotalPage;
    private List loadedList;


    public TenmaListPaging() {
        super();
        System.out.println("pageSize = " + pageSize);
        preparingListPaging();
    }

    public void setShowTotalRow(boolean showTotalRow) {
        this.showTotalRow = showTotalRow;
    }

    public void setPagingPage(int newPageSize) {
        pageSize = newPageSize;
    }

    private int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private void preparingListPaging() {
        panel = new Panel();
        panel.setStyleName("tablepanel");
//        panel.setSizeUndefined();
        Page.Styles styles = Page.getCurrent().getStyles();
        int width = Page.getCurrent().getBrowserWindowWidth();
        width = width * 8 / 10;
        StringBuilder buf = new StringBuilder();
        buf.append(".tablepanel{");
        buf.append(" max-width: ");
        buf.append(width);
        buf.append("px;");
        buf.append("}");
        styles.add(buf.toString());

        pagingArea = new Panel();
        pagingArea.setStyleName("pageare");

//        pagingArea.setWidth("100%");
//        pagingArea.setHeight("20px");
        pagingArea.setVisible(false);
        pagingLayout = new VerticalLayout();
//        VerticalLayout nvl = new VerticalLayout();
//        nvl.setSpacing(true);
        pagingLayout.setSpacing(true);
//        layout.setHeight("90%");
        pagingLayout.addComponent(pagingArea);
        pagingLayout.addComponent(panel);
        pagingLayout.setSizeUndefined();

//        pagingLayout.addComponent(nvl);
    }

    public final void addDataList(Component component) {
        panel.setContent(component);
        panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
        panel.getContent().setSizeUndefined();
    }

    protected abstract Component getDataListComponent();

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

    List loadPagingData(int apageSize, HashMap parameterMap, String sortField, Constants.SORTING_TYPE sortOrder, String loggingCaption, DaoHelper helper) {
        pageSize = apageSize;
        return loadPagingData(parameterMap, sortField, sortOrder, loggingCaption, helper);
    }

    protected List loadPagingData(HashMap parameterMap, String sortField, Constants.SORTING_TYPE sortOrder, String loggingCaption, DaoHelper helper) {
        String searchKey = (parameterMap == null ? null : (String) parameterMap.get("searchValue"));
        if (searchKey == null) {
            searchKey = getTextSearch().getValue();
        }

        System.out.println("TenmaMasterList.loadPagingData pageIndex = " + pageIndex + " searchKey = [" + searchKey + "], sortField = [" + sortField + "], sortOrder = [" + sortOrder + "], loggingCaption = [" + loggingCaption + "], helper = [" + helper + "]");
        if (parameterMap == null) parameterMap = new HashMap();

        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);

        List list = new ArrayList();

        if ((searchKey != null) && (searchKey.trim().length() != 0))
            if (searchKey.indexOf("%") == -1)
                searchKey = new StringBuilder().append("%").append(searchKey).append("%").toString();
        parameterMap.put(Constants.HEADER_SEARCH, searchKey);

        System.out.println("pageSize = " + pageSize);
        parameterMap.put(Constants.RECORDSELECT_SKIP, skip);
        parameterMap.put(Constants.RECORDSELECT_MAX, pageSize);
        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, sortField);
        if (sortOrder != null)
            parameterMap.put(Constants.RECORDSELECT_SORTORDER, sortOrder.getValue());

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
            commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.READ, "Error Occured, please contact your administrator", "");
            e.printStackTrace();
        }
        setLoadedList(list);

        return list;
    }

    protected abstract TextField getTextSearch();

    public final List loadPagingData(List listOfData, String loggingCaption) {
        return pagingList(listOfData, loggingCaption, false, listOfData.size());
    }

    public final List loadPagingData(List listOfData, String loggingCaption, boolean includePaging) {
        return pagingList(listOfData, loggingCaption, includePaging, listOfData.size());
    }

    public final List loadPagingData(List listOfData, String loggingCaption, boolean includePaging, int totalSize) {
        return pagingList(listOfData, loggingCaption, includePaging, totalSize);
    }

    private List pagingList(List listOfData, String loggingCaption, boolean includePaging, int totalSize) {
        String searchKey = getTextSearch().getValue();

        Integer skip = new Integer((pageIndex <= 1 ? 0 : pageIndex - 1) * pageSize);
        System.out.println("TenmaListPaging.pagingList skip " + skip);
        System.out.println("TenmaListPaging.pagingList pageIndx " + pageIndex);
        List list = new ArrayList();

        try {
            String lc = getLogger().openLog();
            if (listOfData.size() <= pageSize)
                list.addAll(listOfData);
            else
                list = listOfData.subList(skip, skip + pageSize < totalSize ? skip + pageSize : totalSize);

            setTotalDataRows(totalSize);
            updatePagingPage();
            getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, loggingCaption);
        } catch (Exception e) {
            commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.READ, "Error Occured, please contact your administrator", "");
            e.printStackTrace();
        }
        setLoadedList(list);
        return list;
    }

    private void updatePagingPage() {

        int totalSize = getTotalDataRows();

        System.out.println("TenmaListPaging.updatePagingPage  totalSize = " + totalSize + " pageSize = " + pageSize);
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
//            lay.setSpacing(true);

            populatePaging(lay, itotalPage);

            HorizontalLayout generalLay = new HorizontalLayout();
            generalLay.setWidth("100%");
            generalLay.addComponent(lay);
            generalLay.setComponentAlignment(lay, Alignment.MIDDLE_LEFT);
            if (showTotalRow) {
                Label totalRow = new Label("(" + String.valueOf(totalSize) + " " + getLabel("page.record") + ")");
                generalLay.addComponent(totalRow);
                generalLay.setComponentAlignment(totalRow, Alignment.BOTTOM_RIGHT);
            }
            generalLay.setWidth("100%");
            pagingArea.setContent(generalLay);
            pagingArea.setWidth("100%");
//            pagingArea.setHeight("20px");
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
            String ttlID = new StringBuilder().append(PAGING_INDEX_ID).append(i + 1).toString();
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

            Button btnlast = new Button(new StringBuilder().append(param.getLabel("default.last")).append("(").append(itotalPage).append(")").toString(), this);
            btnlast.setId("LAST_PAGING_ID");
            btnlast.setPrimaryStyleName("page");
            lay.addComponent(btnlast);
        }


    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        String id = clickEvent.getButton().getId();
        System.out.println("TenmaListPaging.buttonClick id = " + id);
        if ((id != null) && (id.trim().length() != 0)) {
            TenmaMonitor.getInstance().pageLoaded();
            if (id.startsWith(PAGING_INDEX_ID)) {
                String idx = id.substring(PAGING_INDEX_ID.length());
                int nextPage = new Integer(idx);
                setPageIndex(nextPage);
                refreshTable();
            } else if ("NEXT_PAGING_ID".equals(id)) {
                doBeforePaging(id);
//                pageSize = param.getPageSize();
                int nextPage = getPageIndex() + 1;
                if (nextPage > itotalPage)
                    nextPage = itotalPage;
                setPageIndex(nextPage);
                refreshTable();
            } else if ("PREV_PAGING_ID".equals(id)) {
                doBeforePaging(id);
//                pageSize = param.getPageSize();
                int nextPage = getPageIndex() - 1;
                if (nextPage < 1)
                    nextPage = 1;
                setPageIndex(nextPage);
                refreshTable();
            } else if ("FIRST_PAGING_ID".equals(id)) {
                doBeforePaging(id);
                setPageIndex(0);
                refreshTable();
            } else if ("LAST_PAGING_ID".equals(id)) {
                doBeforePaging(id);
                setPageIndex(itotalPage);
                refreshTable();
            }

        }
    }

    /**
     * This method executed before paging button is processed
     *
     * @param buttonActionId
     */
    public void doBeforePaging(String buttonActionId) {

    }

    public List getLoadedList() {
        return loadedList;
    }

    public void setLoadedList(List loadedList) {
        this.loadedList = loadedList;
    }

    protected abstract void doPrint();


}
