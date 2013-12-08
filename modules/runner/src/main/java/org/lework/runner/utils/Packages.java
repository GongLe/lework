package org.lework.runner.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * Created with IntelliJ IDEA.
 * User: Gongle
 * Date: 13-7-16
 * Time: 下午2:23
 * java package工具类
 */
public class Packages {
    private static Logger logger = LoggerFactory.getLogger(Packages.class);


    /** 扫描包下面所有的类 */
    public static List<String> scanPackageClass(String rootPackageName) {
        List<String> classNames = Lists.newArrayList();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL url = loader.getResource(rootPackageName.replace('.', '/'));

            Exceptions.makeRunTimeWhen(url == null, "package[%s] not found!", rootPackageName);

            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                logger.debug("scan in file");
                File[] files = new File(url.toURI()).listFiles();
                for (File f : files) {
                    scanPackageClassInFile(rootPackageName, f, classNames);
                }
            } else if ("jar".equals(protocol)) {
                logger.debug("scan in jar");
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                scanPackageClassInJar(jar, rootPackageName, classNames);
            }

        } catch (URISyntaxException e) {
        } catch (IOException e) {
        }
        return classNames;
    }

    /** 扫描文件夹下所有class文件 */
    private static void scanPackageClassInFile(String rootPackageName, File rootFile, List<String> classNames) {
        String absFileName = rootPackageName + "." + rootFile.getName();
        if (rootFile.isFile() && absFileName.endsWith(".class")) {
            classNames.add(absFileName.substring(0, absFileName.length() - 6));
        } else if (rootFile.isDirectory()) {
            String tmPackageName = rootPackageName + "." + rootFile.getName();
            for (File f : rootFile.listFiles()) {
                scanPackageClassInFile(tmPackageName, f, classNames);
            }
        }
    }

    /** 扫描jar里面的类 */
    private static void scanPackageClassInJar(JarFile jar, String packageDirName, List<String> classNames) {
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName().replace('/', '.');
            if (name.startsWith(packageDirName) && name.endsWith(".class")) {
                classNames.add(name.substring(0, name.length() - 6));
            }
        }
    }
}
