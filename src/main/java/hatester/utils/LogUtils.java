package hatester.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    //Initialize Log4j instance
    private static final Logger logger = LogManager.getLogger(LogUtils.class);

    //Info Level Logs
    // in những thông tin thông thường cho người dùng. VD: Điền url, điền email-pass, click button login,...
    public static void info(String message) {
        logger.info(message);
    }

    public static void info(Object object) {
        logger.info(object);
    }

    //Warn Level Logs
    //những gì để cảnh báo. VD cảnh báo chrome không tương thích với phiên bản của selenium,...
    public static void warn(String message) {
        logger.warn(message);
    }

    public static void warn(Object object) {
        logger.warn(object);
    }

    //Error Level Logs
    //những gì khi xảy ra lỗi thì xuất hiện ra màn hình (thông tin nhưng cung cấp lỗi). VD click element không được,...
    public static void error(String message) {
        logger.error(message);
    }

    public static void error(Object object) {
        logger.error(object);
    }

    //Fatal Level Logs (level cao nhất)
    public static void fatal(String message) {
        logger.fatal(message);
    }

    public static void fatal(Object object) {
        logger.fatal(object);
    }

    //Debug Level Logs
    public static void debug(String message) {
        logger.debug(message);
    }

    public static void debug(Object object) {
        logger.debug(object);
    }
}
