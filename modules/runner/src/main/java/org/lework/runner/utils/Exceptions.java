package org.lework.runner.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * 关于异常的工具类.
 *
 * @author calvin
 */
public class Exceptions {

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }

    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    public static void makeRunTimeWhen(boolean flag, String message, Object... args) {
        if (flag) {
            message = String.format(message, args);
            RuntimeException e = new RuntimeException(message);
            throw correctStackTrace(e);
        }
    }

    public static void makeRuntime() {
        RuntimeException e = new RuntimeException();
        throw correctStackTrace(e);
    }

    public static void makeRuntime(String message, Object... args) {
        message = String.format(message, args);
        RuntimeException e = new RuntimeException(message);
        throw correctStackTrace(e);
    }

    public static void makeRuntime(Throwable cause) {
        RuntimeException e = new RuntimeException(cause);
        throw correctStackTrace(e);
    }

    public static void makeRuntime(String message, Throwable cause, Object... args) {
        message = String.format(message, args);
        RuntimeException e = new RuntimeException(message, cause);
        throw correctStackTrace(e);
    }

    /**
     * 移除 Lang层堆栈信息
     */
    private static RuntimeException correctStackTrace(RuntimeException e) {
        StackTraceElement[] s = e.getStackTrace();
        e.setStackTrace(Arrays.copyOfRange(s, 1, s.length));
        return e;
    }
}
