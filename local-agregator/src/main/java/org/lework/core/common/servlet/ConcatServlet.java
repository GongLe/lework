package org.lework.core.common.servlet;
//


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lework.core.common.taglib.yui.YUICompressorUtils;
import org.lework.runner.web.Servlets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;

/* ------------------------------------------------------------ */

/**
 * Concatenation Servlet
 * This servlet may be used to concatenate multiple resources into
 * a single response.  It is intended to be used to load multiple
 * javascript or css files, but may be used for any content of the
 * same mime type that can be meaningfully concatenated.
 * <p/>
 * The servlet uses {@link javax.servlet.RequestDispatcher#include(javax.servlet.ServletRequest, javax.servlet.ServletResponse)}
 * to combine the requested content, so dynamically generated content
 * may be combined (Eg engine.js for DWR).
 * <p/>
 * The servlet uses parameter names of the query string as resource names
 * relative to the context root.  So these script tags:
 * <pre>
 *  &lt;script type="text/javascript" src="../js/behaviour.js"&gt;&lt;/script&gt;
 *  &lt;script type="text/javascript" src="../js/ajax.js&/chat/chat.js"&gt;&lt;/script&gt;
 *  &lt;script type="text/javascript" src="../chat/chat.js"&gt;&lt;/script&gt;
 * </pre> can be replaced with the single tag (with the ConcatServlet mapped to /concat):
 * <pre>
 *  &lt;script type="text/javascript" src="../concat?/js/behaviour.js&/js/ajax.js&/chat/chat.js"&gt;&lt;/script&gt;
 * </pre>
 * The {@link javax.servlet.ServletContext#getMimeType(String)} method is used to determine the
 * mime type of each resource.  If the types of all resources do not match, then a 415
 * UNSUPPORTED_MEDIA_TYPE error is returned.
 * <p/>
 * If the init parameter "development" is set to "true" then the servlet will run in
 * development mode and the content will be concatenated on every request. Otherwise
 * the init time of the servlet is used as the lastModifiedTime of the combined content
 * and If-Modified-Since requests are handled with 206 NOT Modified responses if
 * appropriate. This means that when not in development mode, the servlet must be
 * restarted before changed content will be served.
 */
public class ConcatServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ConcatServlet.class);
    /**
     * 需要被Gzip压缩的Mime类型.
     */
    private static final String[] GZIP_MIME_TYPES = {"text/html", "application/xhtml+xml", "text/plain", "text/css",
            "text/javascript", "application/x-javascript", "application/json"};

    /**
     * 需要被Gzip压缩的最小文件大小.
     */
    private static final int GZIP_MINI_LENGTH = 512;

    private MimetypesFileTypeMap mimetypesFileTypeMap;

    private ApplicationContext applicationContext;

    boolean _development;
    boolean _clean;
    long _lastModified;
    ServletContext _context;

    /* ------------------------------------------------------------ */
    public void init() throws ServletException {
        _lastModified = System.currentTimeMillis();
        _context = getServletContext();
        _development = "true".equals(getInitParameter("development"));

        // 初始化mimeTypes, 默认缺少css的定义
        mimetypesFileTypeMap = new MimetypesFileTypeMap();
        mimetypesFileTypeMap.addMimeTypes("text/css css");
    }

    /* ------------------------------------------------------------ */

    /* ------------------------------------------------------------ */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _clean = "true".equals(req.getParameter("clean"));
        String q = req.getParameter("src");
        if (q == null) {
            logger.debug("concat file paramter  not find - returning 400");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        // 获取请求内容的基本信息.
        ContentInfo contentInfo = getContentInfo();

        // 根据Etag或ModifiedSince Header判断客户端的缓存文件是否有效, 如仍有效则设置返回码为304,直接返回.
        if (!_clean || !Servlets.checkIfModifiedSince(req, resp, getLastModified())
                || !Servlets.checkIfNoneMatchEtag(req, resp, getEtag())) {
            logger.debug("Resource not modified - returning 304");
            return;
        }
        // 设置Etag/过期时间
        Servlets.setExpiresHeader(resp, Servlets.ONE_YEAR_SECONDS);
        Servlets.setLastModifiedHeader(resp, getLastModified());
        Servlets.setEtag(resp, getEtag());


        String[] parts = q.split(";");   //多个资源文件以":"分隔
        if (parts == null || parts.length == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String type = null;
        for (int i = 0; i < parts.length; i++) {
            String t = _context.getMimeType(parts[i]);
            if (t != null) {
                if (type == null)
                    type = t;
                else if (!type.equals(t)) {
                    resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                    return;
                }
            }
        }

        if (type != null)
            resp.setContentType(type);
        //TODO 获取本地文件,合并到单个文件
        Vector<FileInputStream> srcFiles = new Vector<FileInputStream>();
        for (int i = 0; i < parts.length; i++) {
            String realFilePath = getServletContext().getRealPath(parts[i]);
            File file = new File(realFilePath);
            if (!file.exists()) {
                logger.warn("file:{} no exist ", parts[i]);
                continue;
            }
            logger.debug("预合并文件:{}", file.getAbsolutePath());
            srcFiles.add(new FileInputStream(file));
        }
        InputStream input;
        OutputStream output;
        if (srcFiles.size() > 1) {

            input = new SequenceInputStream(srcFiles.elements());
        } else {
            input = srcFiles.get(0);
        }
        // 构造OutputStream
        if (checkAccetptGzip(req) && contentInfo.needGzip) {
            // 使用压缩传输的outputstream, 使用http1.1 trunked编码不设置content-length.
            output = buildGzipOutputStream(resp);
        } else {

            output = resp.getOutputStream();

        }

        //TODO 压缩 css or js
        //TODO 写入到输出流
        //测试压缩javascript
        try {
            //   YUICompressorUtils.compressJavascript(input, output);
            resp.setContentType("text/javascript;charset=UTF-8");

            // YUICompressorUtils.compressJavascript(new File(inputFilenameJS), new FileOutputStream(outputFilenameJS));
          /*  byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = input.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();*/

            //  IOUtils.copy(input, os) ;
            // 高效读取文件内容并输出,然后关闭input file
            YUICompressorUtils.compressJavascript(input,output);
           //  IOUtils.copy(input, output);
       /*     byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = input.read(bufferData)) != -1) {
                output.write(bufferData, 0, read);
            }*/
            // FileUtils.copyFile(new File(inputFilenameJS), output);
            output.flush();  /**/
         //   IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(input);
        } catch (Exception e) {
            logger.error("压缩文件出错,{}", e);
            resp.reset();
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //  Number length = IOUtil.copy(sis, output);
        // 使用普通outputstream, 设置content-length.
        // resp.setContentLength(length.intValue());

        // 高效读取文件内容并输出,然后关闭input file
        //    FileUtils.copyFile(contentInfo.file, output);
        // output.flush();
        // IOUtil.closeQuietly(input);
    }

    /**
     * 设置Gzip Header并返回GZIPOutputStream.
     */
    private OutputStream buildGzipOutputStream(HttpServletResponse resp) throws IOException {
        resp.setHeader("Content-Encoding", "gzip");
        resp.setHeader("Vary", "Accept-Encoding");
        return new GZIPOutputStream(resp.getOutputStream());
    }

    /**
     * 检查浏览器客户端是否支持gzip编码.
     */
    private static boolean checkAccetptGzip(HttpServletRequest request) {
        // Http1.1 header
        String acceptEncoding = request.getHeader("Accept-Encoding");

        return StringUtils.contains(acceptEncoding, "gzip");
    }


    protected long getLastModified() {
        return   _lastModified;
    }

    protected String getEtag() {
        return "W/\"" + getLastModified() + "\"";
    }

    /**
     * 创建Content基本信息.
     */
    private ContentInfo getContentInfo() {
        ContentInfo contentInfo = new ContentInfo();

        contentInfo.needGzip = true;

        return contentInfo;
    }

    /**
     * 定义Content的基本信息.
     */
    static class ContentInfo {

        protected File file;

        protected int length;

        protected boolean needGzip;
    }
}
