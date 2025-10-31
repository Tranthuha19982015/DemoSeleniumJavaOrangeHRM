package hatester.helpers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SystemHelper {
    public static String getCurrentDir() {
        String currentDir = System.getProperty("user.dir") + File.separator;
        return currentDir;
    }

    public static String getCurrentDatetime() {
        return new SimpleDateFormat("_ddMMyyyy_HHmmss").format(new Date());
    }

    public static String getDatetimeNowFormat() {
        //Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        //Định dạng ngày giờ theo partern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        //Chuyển sang chuỗi và thay thế ký tự
        String formated = now.format(formatter).replace("-", "_").replace(" ", "_").replace(":", "_");
        return formated;
    }
}
