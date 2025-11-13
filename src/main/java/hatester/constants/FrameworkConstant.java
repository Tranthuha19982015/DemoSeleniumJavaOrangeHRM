package hatester.constants;

import hatester.helpers.PropertiesHelper;

public class FrameworkConstant {
    private FrameworkConstant() {
    }

    public static String BROWSER = PropertiesHelper.getValue("BROWSER");
    public static String URL_RISE=PropertiesHelper.getValue("URL");
    public static String WINDOW_SIZE = PropertiesHelper.getValue("WINDOW-SIZE");
    public static String HEADLESS = PropertiesHelper.getValue("HEADLESS");
    public static String SCREENSHOT_PATH = PropertiesHelper.getValue("SCREENSHOT_PATH");
    public static String VIDEO_RECORD_PATH = PropertiesHelper.getValue("VIDEO_RECORD_PATH");
}
