package org.lework.runner.misc.range;


import java.util.ArrayList;
import java.util.List;

/**
 * 对有序列表分区操作
 *
 * @param <T>
 */
public class EachRange<T> {
    /**
     * @param totalContent 有序列表
     * @param pageSize     区块大小
     * @param callback     回调接口
     * @see RangeCallback#each(int, java.util.List)
     */
    public EachRange(List<T> totalContent, int pageSize, RangeCallback<T> callback) {
        if (totalContent == null || totalContent.size() == 0)
            return;
        this.totalContent = totalContent;
        this.size = pageSize;
        this.totoal = totalContent.size();
        this.totalPages = getSize() == 0 ? 1 : (int) Math.ceil((double) getTotoal() / (double) getSize());
        //each page
        int begin;
        int end;
        for (int i = 0; i < getTotalPages(); i++) {
            //计算偏移
            begin = i * getSize();
            end = begin + getSize();
            end = getTotoal() > end ? end : getTotoal();
            //回调
            callback.each(i, getRange(begin, end));
        }
    }

    private List<T> totalContent;
    private int size = 50;

    private int totoal = -1;
    private int totalPages = -1;

    /**
     * 获取有序列表范围内数据.
     *
     * @param begin
     * @param end
     * @return
     */
    private List<T> getRange(int begin, int end) {
        List<T> ret = new ArrayList<T>(end - begin);
        for (int i = begin; i < end; i++) {
            ret.add(getTotalContent().get(i));
        }
        return ret;
    }

    public int getTotoal() {
        return totoal;
    }

    public int getSize() {
        return size;
    }

    public int getTotalPages() {
        return totalPages;
    }


    public List<T> getTotalContent() {
        return totalContent;
    }
}
