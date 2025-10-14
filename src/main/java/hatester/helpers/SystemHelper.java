package hatester.helpers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemHelper {
    public static String getCurrentDir() {
        String currentDir = System.getProperty("user.dir") + File.separator;
        return currentDir;
    }

    public static String getCurrentDatetime() {
        return new SimpleDateFormat("_ddMMyyyy_HHmmss").format(new Date());
    }
}
