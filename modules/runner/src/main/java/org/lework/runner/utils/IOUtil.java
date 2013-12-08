package org.lework.runner.utils;

import java.io.*;

/**
 * 常用IO操作工具类
 * User: Gongle
 * Date: 13-11-26
 * Time: 下午4:42
 */
public class IOUtil {
    private static final int BUFFER_SIZE = 1024 * 4;

    /**
     * 合并多个文件
     *
     * @param destination
     * @param sources
     * @throws IOException
     */
    public static void joinFiles(final File destination, File[] sources)
            throws IOException {
        OutputStream output = null;
        try {
            output = createAppendableStream(destination);
            for (File source : sources) {
                appendFile(output, source);
            }
        } finally {
            IOUtil.closeQuietly(output);
        }
    }

    /**
     * 合并多个文件到输出流
     *
     * @param outputStreams
     * @param sources
     * @throws IOException
     */
    public static void joinFiles(final OutputStream outputStreams, File[] sources)
            throws IOException {
        OutputStream output = null;
        try {

            for (File source : sources) {
                appendFile(outputStreams, source);
            }
        } finally {
            IOUtil.closeQuietly(output);
        }
    }

    private static BufferedOutputStream createAppendableStream(File dest)
            throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(dest, true));
    }

    /**
     * 合并文件到输出流
     *
     * @param output
     * @param source
     * @throws IOException
     */
    private static void appendFile(OutputStream output, File source)
            throws IOException {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(source));
            IOUtil.copy(input, output);
        } finally {
            IOUtil.closeQuietly(input);
        }
    }


    public static long copy(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static void closeQuietly(Closeable output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
