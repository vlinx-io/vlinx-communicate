package io.vlinx.communicate.logging;

import io.vlinx.communicate.app.AppMsgUtil;
import io.vlinx.communicate.app.IAppMsgListener;

public class Logger {

    public static String INFO(String tag, Object msg, IAppMsgListener listener) {
        String log = io.vlinx.logging.Logger.INFO(tag, msg);
        AppMsgUtil.sendMessage("info", log, listener);
        return log;
    }

    public static String INFO(Object msg, IAppMsgListener listener) {

        String log = io.vlinx.logging.Logger.INFO(msg);
        AppMsgUtil.sendMessage("info", log, listener);

        return log;

    }

    public static String DEBUG(String tag, Object msg, IAppMsgListener listener) {

        String log = io.vlinx.logging.Logger.DEBUG(tag, msg);
        AppMsgUtil.sendMessage("debug", log, listener);
        return log;
    }

    public static String DEBUG(Object msg, IAppMsgListener listener) {

        String log = io.vlinx.logging.Logger.DEBUG(msg);
        AppMsgUtil.sendMessage("debug", log, listener);
        return log;
    }

    public static String ERROR(String tag, Object msg, IAppMsgListener listener) {
        String log = io.vlinx.logging.Logger.ERROR(tag, msg);
        AppMsgUtil.sendMessage("error", log, listener);
        return log;
    }

    public static String ERROR(Object msg, IAppMsgListener listener) {
        String log = io.vlinx.logging.Logger.ERROR(msg);
        AppMsgUtil.sendMessage("error", log, listener);
        return log;

    }

    public static String ERROR(Exception e, IAppMsgListener listener) {

        String log = io.vlinx.logging.Logger.ERROR(e);
        AppMsgUtil.sendMessage("error", log, listener);
        return log;

    }

    public static String WARN(String tag, Object msg, IAppMsgListener listener) {
        String log = io.vlinx.logging.Logger.ERROR(tag, msg);
        AppMsgUtil.sendMessage("warn", log, listener);
        return log;
    }

    public static String WARN(Object msg, IAppMsgListener listener) {
        String log = io.vlinx.logging.Logger.WARN(msg);
        AppMsgUtil.sendMessage("warn", log, listener);
        return log;

    }

    public static String WARN(Exception e, IAppMsgListener listener) {

        String log = io.vlinx.logging.Logger.WARN(e);
        AppMsgUtil.sendMessage("warn", log, listener);
        return log;

    }

    public static String FATAL(String tag, Object msg, IAppMsgListener listener) {
        String log = io.vlinx.logging.Logger.WARN(tag, msg);
        AppMsgUtil.sendMessage("fatal", log, listener);
        return log;
    }

    public static String FATAL(Object msg, IAppMsgListener listener) {
        String log = io.vlinx.logging.Logger.WARN(msg);
        AppMsgUtil.sendMessage("fatal", log, listener);
        return log;

    }

    public static String FATAL(Exception e, IAppMsgListener listener) {

        String log = io.vlinx.logging.Logger.ERROR(e);
        AppMsgUtil.sendMessage("fatal", log, listener);
        return log;

    }
}
