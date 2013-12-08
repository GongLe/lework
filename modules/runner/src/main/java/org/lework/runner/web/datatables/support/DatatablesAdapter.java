package org.lework.runner.web.datatables.support;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Datatables ajax 请求参数封装
 * such as:
 * <pre>
 * bRegex: false
 * bRegex_0: false
 * bRegex_1: false
 * bRegex_2: false
 * bSearchable_0: true
 * bSearchable_1: true
 * bSearchable_2: true
 * bSortable_0: false
 * bSortable_1: true
 * bSortable_2: false
 * iColumns: 3
 * iDisplayLength: 10
 * iDisplayStart: 0
 * iSortCol_0: 1
 * iSortingCols: 1
 * mDataProp_0: "name"
 * mDataProp_1: "code"
 * mDataProp_2: "id"
 * sColumns: ""
 * sEcho: 2
 * sSearch: ""
 * sSearch_0: ""
 * sSearch_1: ""
 * sSearch_2: ""
 * sSortDir_0: "asc"
 * </pre>
 *
 * @author Gongle
 */
public class DatatablesAdapter {
    private boolean bRegex;
    private int iColumns;
    private int iSortingCols; // 排序的列数
    private String sSearch;
    private String sEcho;
    private int iDisplayLength;
    private int iDisplayStart;
    private List<SColumn> sColumns = new ArrayList<SColumn>(); //对应的列
    private List<Sort.Order> orders = new ArrayList<Sort.Order>() ;


    public static PageRequest buildPageRequest(HttpServletRequest request) {
        DatatablesAdapter adapter = new DatatablesAdapter(request);
        //转换成Spring Data Jpa Pageable
        PageRequest pr = new PageRequest(0, adapter.getiDisplayLength(), new Sort(adapter.getOrders()));
        return pr;
    }

    public DatatablesAdapter(HttpServletRequest request) {
        convert(request);
        convertColumns(request);
    }

    private void convert(final HttpServletRequest request) {
        this.bRegex = Boolean.valueOf(request.getParameter("bRegex"));
        this.iColumns = Integer.valueOf(request.getParameter("iColumns"));
        this.sSearch = request.getParameter("sSearch");
        this.sEcho = request.getParameter("sEcho");
        this.iSortingCols = Integer.valueOf(request.getParameter("iSortingCols"));
        this.iDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        this.iDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
    }

    private void convertColumns(final HttpServletRequest request) {
        SColumn _col = new SColumn();
        for (int i = 0; i < iColumns; i++) {
            _col.setIndex(i);
            _col.setbRegex(Boolean.valueOf(request.getParameter("bRegex_" + i)));
            _col.setbSearchable(Boolean.valueOf(request.getParameter("bSearchable_" + i)));
            _col.setbSortable(Boolean.valueOf(request.getParameter("bSortable_" + i)));
            _col.setiSortCol(Integer.valueOf(request.getParameter("bSortable_" + i)));
            _col.setmDataProp(request.getParameter("mDataProp_" + i));
            _col.setsSearch(request.getParameter("sSearch_" + i));
            _col.setsSortDir(request.getParameter("sSortDir_" + i));
            this.sColumns.add(_col);

            if (_col.sortable()) {
                Sort.Direction dir = Sort.Direction.valueOf(Sort.Direction.class, _col.getsSortDir().toUpperCase());
                orders.add(new Sort.Order(dir, _col.getmDataProp()));
            }
            _col = new SColumn();
        }
    }

    public boolean isbRegex() {
        return bRegex;
    }

    public void setbRegex(boolean bRegex) {
        this.bRegex = bRegex;
    }

    public int getiColumns() {
        return iColumns;
    }

    public void setiColumns(int iColumns) {
        this.iColumns = iColumns;
    }

    public int getiSortingCols() {
        return iSortingCols;
    }

    public void setiSortingCols(int iSortingCols) {
        this.iSortingCols = iSortingCols;
    }

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public List<SColumn> getsColumns() {
        return sColumns;
    }

    public void setsColumns(List<SColumn> sColumns) {
        this.sColumns = sColumns;
    }
    public List<Sort.Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Sort.Order> orders) {
        this.orders = orders;
    }

}
