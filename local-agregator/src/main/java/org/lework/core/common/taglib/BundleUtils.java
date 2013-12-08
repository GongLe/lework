package org.lework.core.common.taglib;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.lework.core.common.AppConfigConstant;
import org.lework.runner.security.Digests;
import org.lework.runner.utils.Encodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Vector;

public class BundleUtils {
    private static Logger logger = LoggerFactory.getLogger(BundleUtils.class);

    /**
     * 压缩资源文件 => 写入到应用static\min目录
     *
     * @param in 资源文件输入流
     * @return 返回static相对路径
     * <p>
     *
     * </p>
     */
    public static String compressorAndWrite(InputStream in, String outFileName) throws IOException {
        //TODO 压缩文件
        //写入到磁盘
        String relatedPath =   "/static/min/" + outFileName;
        OutputStream out = new FileOutputStream(AppConfigConstant.REAL_PATH  + relatedPath );
        IOUtils.copy(in, out);
        return relatedPath;
    }

    /**
     * 根据标签资源描述信息,读取=>合并到一个输出流.
     *
     * @param resourceMetaList
     * @return
     */
    public static InputStream mergeResourceAsOne(List<? extends ITagResourceMeta> resourceMetaList) throws FileNotFoundException {
        Vector<FileInputStream> srcFiles = new Vector<FileInputStream>();
        ITagResourceMeta rs;
        InputStream input;
        for (int i = 0; i < resourceMetaList.size(); i++) {
            rs = resourceMetaList.get(i);
            String path = FilenameUtils.concat(AppConfigConstant.REAL_PATH, rs.getSrc());
            File file = new File(path);
            if (!file.exists()) {
                logger.warn("file:{} no exist ", path);
                continue;
            }
            logger.debug("预合并文件:{}", path);
            srcFiles.add(new FileInputStream(file));
        }
        if (srcFiles.size() > 1) {
            input = new SequenceInputStream(srcFiles.elements());
        } else {
            input = srcFiles.get(0);
        }

        return input;
    }

    public static String buildSha1(String name) {
        //计算sha1 value
        byte[] salt = Digests.generateSalt(8);
        byte[] sha1Result = Digests.sha1(name.getBytes(), salt);
        return Encodes.encodeHex(sha1Result);
    }

}
