package org.lework.runner.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * java对象序列化接口{@link Serializable}工具类
 *
 * @author Gongle
 */
public class Serializers {

    public Serializers() {
    }

    /**
     * Serializes an object using default serialization.
     *
     * @param obj an object need to serialize
     * @return the byte array of the serialized object
     * @throws java.io.IOException io exception
     */
    public static byte[] serialize(final Serializable obj) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);

        try {
            oos.writeObject(obj);
        } finally {
            oos.close();
        }
        return baos.toByteArray();
    }

    /**
     * Uses default de-serialization to turn a byte array into an object.
     *
     * @param data the byte array need to be converted
     * @return the converted object
     * @throws java.io.IOException    io exception
     * @throws ClassNotFoundException class not found exception
     */
    public static Object deserialize(final byte[] data) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final BufferedInputStream bis = new BufferedInputStream(bais);
        final ObjectInputStream ois = new ObjectInputStream(bis);

        try {
            try {
                return ois.readObject();
            } catch (final IOException e) {
                throw e;
            } catch (final ClassNotFoundException e) {
                throw e;
            }
        } finally {
            ois.close();
        }
    }
}
