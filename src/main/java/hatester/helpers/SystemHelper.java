package hatester.helpers;

import java.io.File;

public class SystemHelper {
    public static String getCurrentDir() {
        String currentDir = System.getProperty("user.dir") + File.separator;
        return currentDir;
    }
}
