package hatester.common;

import hatester.helpers.ExcelHelper;
import hatester.helpers.SystemHelper;
import hatester.utils.LogUtils;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {

    @DataProvider(name = "data_login_excel_success", parallel = false)
    public Object[][] dataLoginSuccess() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getDataHashTable(SystemHelper.getCurrentDir() + "src/test/resources/datatest_CRM/datatest.xlsx", "Login",1,3);
        LogUtils.info("Login Data From Excel: " + data);
        return data;
    }

    @DataProvider(name = "data_login_excel_email_invalid", parallel = true)
    public Object[][] dataLoginFailWithEmailInvalid() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getDataHashTable(SystemHelper.getCurrentDir() + "src/test/resources/datatest_CRM/datatest.xlsx", "Login", 4,5);
        LogUtils.info("Login Data From Excel: " + data);
        return data;
    }

    @DataProvider(name = "data_login_excel_password_invalid", parallel = false)
    public Object[][] dataLoginFailWithPasswordInvalid() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getDataHashTable(SystemHelper.getCurrentDir() + "src/test/resources/datatest_CRM/datatest.xlsx", "Login", 6,7);
        LogUtils.info("Login Data From Excel: " + data);
        return data;
    }
}
