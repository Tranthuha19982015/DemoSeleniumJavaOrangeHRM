package hatester.constants;

import hatester.helpers.PropertiesHelper;
import hatester.helpers.SystemHelper;

public class FrameworkConstant {
    private FrameworkConstant() {
    }

//    static {
//        PropertiesHelper.loadAllFiles();
//    }

    public static String EXCEL_DATA_FILE_PATH = PropertiesHelper.getValue("EXCEL_DATA_FILE_PATH");

    public static String BROWSER = PropertiesHelper.getValue("BROWSER");
    public static String URL_RISE = PropertiesHelper.getValue("URL");
    public static String EMAIL_ADMIN = PropertiesHelper.getValue("EMAIL_ADDRESS");
    public static String PASSWORD_ADMIN = PropertiesHelper.getValue("PASSWORD");
    public static String WINDOW_SIZE = PropertiesHelper.getValue("WINDOW-SIZE");
    public static String HEADLESS = PropertiesHelper.getValue("HEADLESS");
    public static String SCREENSHOT_PATH = PropertiesHelper.getValue("SCREENSHOT_PATH");
    public static String VIDEO_RECORD_PATH = PropertiesHelper.getValue("VIDEO_RECORD_PATH");
    public static String SCREENSHOT_ALL_STEP = PropertiesHelper.getValue("SCREENSHOT_ALL_STEP");
    public static String SCREENSHOT_FAILURE = PropertiesHelper.getValue("SCREENSHOT_FAILURE");
    public static String SCREENSHOT_SUCCESS = PropertiesHelper.getValue("SCREENSHOT_SUCCESS");
    public static String VIDEO_RECORD = PropertiesHelper.getValue("VIDEO_RECORD");
}
