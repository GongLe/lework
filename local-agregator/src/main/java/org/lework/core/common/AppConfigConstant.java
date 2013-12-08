package org.lework.core.common;

import org.lework.runner.utils.PropertiesLoader;

/**
 * Web Application  Configurate Constant
 * User: Gongle
 * Date: 13-9-22
 */
public class AppConfigConstant {
    public static PropertiesLoader LOADER;

    static {
        LOADER = new PropertiesLoader("appConfigConstant.properties");
        runtimeEnv = RuntimeEnv.valueOf(LOADER.getProperty(Keys.runtimeEnv));

    }
    public static String  get(String key){
       return  LOADER.getProperty(key);
    }

    /**
     * 属性会在Spring启动的时候根据appConfig.properties计算出来。
     * @see ResourcePathExposer
     */
    public static String CTX;

    /**
     * as getServletContext().getRealPath("")
     * @see ResourcePathExposer
     */
    public static String REAL_PATH;
    /**
     * 属性会在Spring启动的时候根据appConfig.properties计算出来。
     * @see ResourcePathExposer
     */
    public static String SRC;

    /**
     * Wher  runs on?.
     */
    private static RuntimeEnv runtimeEnv;

    /**
     * Which mode  runs in?
     */
    private static RuntimeMode runtimeMode;

    private static class Keys {

        public static final String runtimeEnv = "runtimeEnv";
    }
}
