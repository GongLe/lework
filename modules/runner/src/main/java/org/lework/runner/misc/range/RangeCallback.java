package org.lework.runner.misc.range;

import java.util.List;

/**
 * 区块回调接口
 * @param <T>
 */
public interface RangeCallback<T> {

    public void each(int curPageNum, List<T> rangeData);
}
