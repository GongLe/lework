package org.lework.runner.web.datatables.support;

/**
 * Datatables Column
 * 封转ajax server process 传递过来的Column相关参数
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
public class SColumn implements Sortable, Searchable {
    private int index;
    private boolean bRegex; // bRegex_(int)
    private boolean bSearchable; //bSearchable_(int)
    private String mDataProp; //mDataProp_(int)
    private String sSearch; //sSearch_(int)
    private boolean bSortable; //bSortable_(int)
    private String sSortDir; //sSearch_(int)
    private int iSortCol; //iSortCol_(int)  被排序的列

    @Override
    public boolean sortable() {
        return bSortable;
    }

    @Override
    public boolean searchable() {
        return bSearchable;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isbRegex() {
        return bRegex;
    }

    public void setbRegex(boolean bRegex) {
        this.bRegex = bRegex;
    }

    public boolean isbSearchable() {
        return bSearchable;
    }

    public void setbSearchable(boolean bSearchable) {
        this.bSearchable = bSearchable;
    }

    public String getmDataProp() {
        return mDataProp;
    }

    public void setmDataProp(String mDataProp) {
        this.mDataProp = mDataProp;
    }

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }

    public boolean isbSortable() {
        return bSortable;
    }

    public void setbSortable(boolean bSortable) {
        this.bSortable = bSortable;
    }

    public String getsSortDir() {
        return sSortDir;
    }

    public void setsSortDir(String sSortDir) {
        this.sSortDir = sSortDir;
    }

    public int getiSortCol() {
        return iSortCol;
    }

    public void setiSortCol(int iSortCol) {
        this.iSortCol = iSortCol;
    }


}
