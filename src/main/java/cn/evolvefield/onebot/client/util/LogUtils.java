package cn.evolvefield.onebot.client.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: onebot-sdk
 * Author: cnlimiter
 * Date: 2022/12/30 22:44
 * Description:
 */
public class LogUtils {
    /**
     *
     * @param clazz
     */
    public LogUtils(Class<?> clazz) {
        packageName = clazz.getPackage().getName();
        logger = Logger.getLogger(packageName);
    }

    public LogUtils(String key) {
        packageName = key;
        logger = Logger.getLogger(packageName);
    }

    // 所在的包名
    private String packageName;

    // 包装这个 logger
    private Logger logger;

    // 缓存
    private static Map<String, LogUtils> cache = new HashMap<>();

    /**
     * 打印一个日志
     *
     * @param msg
     *            日志信息
     */
    public void info(String msg) {
        logger.logp(Level.INFO, packageName, getMethodName(), msg);
    }

    /**
     * 打印一个日志
     *
     * @param tpl
     *            信息语句之模板
     * @param params
     *            信息参数
     */
    public void info(String tpl, Object... params) {
        logger.logp(Level.INFO, packageName, getMethodName(), tpl, params);

    }

    /**
     * 打印一个日志（警告级别）
     *
     * @param msg
     *            警告信息
     */
    public void warn(String msg) {
        logger.logp(Level.WARNING, packageName, getMethodName(), msg);
    }

    /**
     * 打印一个日志（警告级别）
     *
     * @param tpl
     *            信息语句之模板
     * @param params
     *            信息参数
     */
    public void warn(String tpl, Object... params) {
        logger.logp(Level.WARNING, packageName, getMethodName(), tpl, params);

    }

    /**
     * 记录一个异常
     *
     * @param ex
     */
    public void warn(Throwable ex) {
        logger.logp(Level.WARNING, packageName, getMethodName(), ex.getMessage(), ex);
    }


    /**
     * 打印一个日志（debug级别）
     *
     * @param msg
     *            警告信息
     */
    public void debug(String msg) {
        logger.logp(Level.FINER, packageName, getMethodName(), msg);
    }

    /**
     * 打印一个日志（debug级别）
     *
     * @param tpl
     *            信息语句之模板
     * @param params
     *            信息参数
     */
    public void debug(String tpl, Object... params) {
        logger.logp(Level.FINER, packageName, getMethodName(), tpl, params);

    }

    /**
     * 记录一个异常
     *
     * @param ex
     */
    public void debug(Throwable ex) {
        logger.logp(Level.FINER, packageName, getMethodName(), ex.getMessage(), ex);
    }

    /**
     * 打印一个日志（错误级别）
     *
     * @param msg
     *            警告信息
     */
    public void error(String msg) {
        logger.logp(Level.SEVERE, packageName, getMethodName(), msg);
    }

    /**
     * 打印一个日志（错误级别）
     *
     * @param tpl
     *            信息语句之模板
     * @param params
     *            信息参数
     */
    public void error(String tpl, Object... params) {
        logger.logp(Level.SEVERE, packageName, getMethodName(), tpl, params);

    }

    /**
     * 记录一个异常
     *
     * @param ex
     */
    public void error(Throwable ex) {
        logger.logp(Level.SEVERE, packageName, getMethodName(), ex.getMessage(), ex);
    }



    /**
     * 获取所在的方法，调用时候
     *
     * @return
     */
    private String getMethodName() {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        StackTraceElement frame = st[2];
        String method = String.format((Locale) null, "%s(%s:%s)", frame.getMethodName(), frame.getFileName(),
                frame.getLineNumber());

        return method;
    }

    /**
     * 获取自定义的 logger
     *
     * @param clazz
     * @return
     */
    public static LogUtils getLog(Class<?> clazz) {
        String key = clazz == null ? "root" : clazz.getPackage().getName();

        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            LogUtils logger = new LogUtils(clazz);
            cache.put(key, logger);
            return logger;
        }
    }

    /**
     * 获取自定义的 logger
     *
     * @param key
     * @return
     */
    public static LogUtils getLog(String key) {

        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            LogUtils logger = new LogUtils(key);
            cache.put(key, logger);
            return logger;
        }
    }
}
