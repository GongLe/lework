package org.lework.runner.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * {@link Serializers} 单元测试类
 */
public class SerializerTestCase {

    /**
     * Length of an integer object in bytes.
     */
    private static final int INTEGER_LENGTH = 81;

    /**
     * Tests {@linkplain Serializers#serialize(java.io.Serializable)} method.
     *
     * @throws Exception exception
     */
    @Test
    public void serialize() throws Exception {
        final byte[] bytes = Serializers.serialize(new Integer(0));
        assertEquals(bytes.length, INTEGER_LENGTH);
    }
}
