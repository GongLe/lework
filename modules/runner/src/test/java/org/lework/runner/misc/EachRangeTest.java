package org.lework.runner.misc;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.lework.runner.misc.range.EachRange;
import org.lework.runner.misc.range.RangeCallback;

import java.util.List;

public class EachRangeTest {
    List<Integer> totalContent = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<Integer> totalContent2 = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    List<Integer> totalContent3 = Lists.newArrayList(0);

    @Test
    public void testPage() {
        new EachRange<Integer>(totalContent, 5, new RangeCallback<Integer>() {
            @Override
            public void each(int curPageNum, List<Integer> callbackData) {
                for (Integer i : callbackData) {
                    System.out.println(String.format("pageSize:5,pageNum:%s,%s", curPageNum, i));
                }
            }
        });

        new EachRange<Integer>(totalContent3, 5, new RangeCallback<Integer>() {
            @Override
            public void each(int curPageNum, List<Integer> callbackData) {
                for (Integer i : callbackData) {
                    System.out.println(String.format("pageSize:5,pageNum:%s,%s", curPageNum, i));
                }
            }
        });
    }


}
