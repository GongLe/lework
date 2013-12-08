package org.lework.core.common.taglib.yui;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * YUICompressor 压缩js,css简单封装
 * User: Gongle
 * Date: 13-11-26
 */
public class YUICompressorUtils {
    public static YuiOptions default_options = new YuiOptions();

    public static void compressJavascript(InputStream input, OutputStream output) {
        compressJavascript(input, output, default_options);
    }

    public static void compressJavascript(File file, OutputStream output) {
        try {
            compressJavascript(new FileInputStream(file), output, default_options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static void compressJavascript(InputStream input, OutputStream output, YuiOptions o) {
        Reader in = null;
        Writer out = null;
        try {
            in = new InputStreamReader(input, o.charset);
            JavaScriptCompressor compressor = new JavaScriptCompressor(in, new YuiCompressorErrorReporter());
            out = new OutputStreamWriter(output, o.charset);
            compressor.compress(out, o.lineBreakPos, o.munge, o.verbose, o.preserveAllSemiColons, o.disableOptimizations);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    public static void compressStylesheet(InputStream input, OutputStream output) {

        compressStylesheet(input, output, default_options);
    }

    public static void compressStylesheet(File file, OutputStream output) {
        try {
            compressStylesheet(new FileInputStream(file), output, default_options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * @param input InputStream finally closeQuietly
     * @param output OutputStream finally closeQuietly
     * @param o   YuiOptions @see #YuiOptions
     */
    public static void compressStylesheet(InputStream input, OutputStream output, YuiOptions o) {
        Reader in = null;
        Writer out = null;
        try {
            in = new InputStreamReader(input, o.charset);
            CssCompressor compressor = new CssCompressor(in);
            in.close();

            out = new OutputStreamWriter(output, o.charset);
            compressor.compress(out, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }
}
