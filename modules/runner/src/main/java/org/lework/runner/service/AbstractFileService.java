package org.lework.runner.service;

import eu.medsea.mimeutil.MimeUtil;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

/**
 * Abstract File Service，具体文件操作被子类实现。
 * User:Gongle
 * Date: 13-10-2
 */
public abstract class   AbstractFileService implements FileService {
    @Override
    public String getMimeType(File file) {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        Collection collection = MimeUtil.getMimeTypes(file);
        if (collection == null || collection.size() < 1) {
            return "application/octet-stream";
        }
        return collection.toArray()[0].toString();
    }

    @Override
    public String getMimeType(InputStream inputStream) {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        Collection collection = MimeUtil.getMimeTypes(inputStream);
        if (collection == null || collection.size() < 1) {
            return "application/octet-stream";
        }
        return collection.toArray()[0].toString();
    }

    @Override
    public String getMimeType(byte[] bytes) {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        Collection collection = MimeUtil.getMimeTypes(bytes);
        if (collection == null || collection.size() < 1) {
            return "application/octet-stream";
        }
        return collection.toArray()[0].toString();
    }
}
