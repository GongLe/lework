package org.lework.runner.web.datatables;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Datatable ajax source DTO
 * <pre>
 * datatable请求时传递的参数
 *
 * 类型	    名称	            说明
 * int	    iDisplayStart	显示的起始索引
 * int	    iDisplayLength	显示的行数
 * int	    iColumns	    显示的列数
 * string   	sSearch	        全局搜索字段
 * boolean	bEscapeRegex	全局搜索是否正则
 * boolean	bSortable_(int)	表示一列是否在客户端被标志位可排序
 * boolean	bSearchable_(int)	表示一列是否在客户端被标志位可搜索
 * string	    sSearch_(int)	个别列的搜索
 * boolean	bEscapeRegex_(int)	个别列是否使用正则搜索
 * int	    iSortingCols	排序的列数
 * int	    iSortCol_(int)	被排序的列
 * string	    sSortDir_(int)	排序的方向 "desc" 或者 "asc".
 * string	    sEcho	        DataTables 用来生成的信息
 *
 * 服务器应该返回如下的 JSON 格式数据
 *
 * 类型	    名称	          说明
 * int	    iTotalRecords	实际的行数
 * int	    iTotalDisplayRecords	过滤之后，实际的行数。
 * string	    sEcho	来自客户端 sEcho 的没有变化的复制品，
 * string	    sColumns	可选，以逗号分隔的列名，
 * array array mixed  aaData	data list
 *
 * </pre>
 */
public class DataTableResult<T> {
    public DataTableResult() {
    }

    public  static <T> DataTableResult build(Page<T> page) {
        return new DataTableResult(page);
    }

    public DataTableResult(Page<T> page) {


        this.aaData = page.getContent();
        iTotalRecords = (int) page.getTotalElements();
        iTotalDisplayRecords = (int) page.getTotalElements();
     //   iTotalDisplayRecords = page.hasContent() ? page.getContent().size() : 0;
        // iTotalDisplayRecords = (int) page.getTotalElements() ;

    }

    private int iTotalRecords;
    private int iTotalDisplayRecords;
    private int sEcho;
    //private String sColumns;
    private List<T> aaData = new ArrayList<T>();


    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("iTotalRecords", getiTotalRecords())
                .append("iTotalDisplayRecords", getiTotalDisplayRecords())
                .append("sEcho", getsEcho())
                .toString();
    }
}
