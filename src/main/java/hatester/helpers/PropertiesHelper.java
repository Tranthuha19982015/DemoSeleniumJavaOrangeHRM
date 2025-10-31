package hatester.helpers;

import hatester.utils.LogUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesHelper {
    private static Properties properties;
    private static String relPropertiesFilePathDefault = "src/test/resources/configs/config.properties";

    public static Properties loadAllFiles() {
        LinkedList<String> files = new LinkedList<>();
        files.add("src/test/resources/configs/config.properties");
        files.add("src/test/resources/configs/message.properties");

        try {
            properties = new Properties();
            for (String f : files) {
                try (FileInputStream fis = new FileInputStream(SystemHelper.getCurrentDir() + f)) {
                    Properties tempPro = new Properties();
                    tempPro.load(fis);
                    properties.putAll(tempPro);
                }
            }
            return properties;
        } catch (IOException ioe) {
            return new Properties();
        }
    }

    public static void setFile(String relPropertiesFilePath) {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(SystemHelper.getCurrentDir() + relPropertiesFilePath)) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultFile() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(SystemHelper.getCurrentDir() + relPropertiesFilePathDefault)) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        String value = null;
        try {
            if (properties == null || properties.isEmpty()) {
                setDefaultFile();
            }
            value = properties.getProperty(key);
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
        return value;
    }

    public static void setValue(String key, String value) {
        try {
            if (properties == null || properties.isEmpty()) {
                setDefaultFile();
            }
            //Ghi vào cùng file Prop với file lấy ra
            String linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
            try (FileOutputStream fos = new FileOutputStream(linkFile)) {
                LogUtils.info(linkFile);
                properties.setProperty(key, value);
                properties.store(fos, null);
            }

        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    public static void setValue(String key, String value, String linkFilePath) {
        try {
            if (properties == null || properties.isEmpty()) {
                setDefaultFile();
            }
            //Ghi vào cùng file Prop với file lấy ra
            try (FileOutputStream fos = new FileOutputStream(SystemHelper.getCurrentDir() + linkFilePath)) {
                LogUtils.info(SystemHelper.getCurrentDir() + linkFilePath);
                properties.setProperty(key, value);
                properties.store(fos, null);
            }
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }
}
