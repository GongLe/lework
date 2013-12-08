package org.lework.runner.utils;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Collecitons3Test {
    //测试集合成员查找
    @Test
    public void testFindElement() throws Exception {
        TestBean3 bean1 = new TestBean3();
        bean1.setId(1);
        TestBean3 bean2 = new TestBean3();
        bean2.setId(2);

        List list = Lists.newArrayList(bean1, bean2);
        assertEquals(bean2, Collections3.getNextElementIndex(list, bean1));
    }


    @Test
    public void convertElementPropertyToString() {
        TestBean3 bean1 = new TestBean3();
        bean1.setId(1);
        TestBean3 bean2 = new TestBean3();
        bean2.setId(2);

        List list = Lists.newArrayList(bean1, bean2);

        assertEquals("1,2", Collections3.extractToString(list, "id", ","));
    }

    @Test
    public void convertElementPropertyToList() {
        TestBean3 bean1 = new TestBean3();
        bean1.setId(1);
        TestBean3 bean2 = new TestBean3();
        bean2.setId(2);

        List list = Lists.newArrayList(bean1, bean2);
        List<String> result = Collections3.extractToList(list, "id");
        assertEquals(2, result.size());
        assertEquals(1, result.get(0));
    }

    @Test
    public void convertCollectionToString() {
        List<String> list = Lists.newArrayList("aa", "bb");
        String result = Collections3.convertToString(list, ",");
        assertEquals("aa,bb", result);

        result = Collections3.convertToString(list, "<li>", "</li>");
        assertEquals("<li>aa</li><li>bb</li>", result);
    }

    public static class TestBean3 {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
