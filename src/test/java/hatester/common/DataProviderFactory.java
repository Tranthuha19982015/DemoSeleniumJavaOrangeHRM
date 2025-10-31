package hatester.common;

import hatester.helpers.ExcelHelper;
import hatester.helpers.SystemHelper;
import hatester.utils.LogUtils;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {

    @DataProvider(name = "data_provider_login_excel", parallel = false)
    public Object[][] dataLoginCRMFromExcel() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData(SystemHelper.getCurrentDir() + "src/test/resources/datatest_CRM/datatest.xlsx", "Login");
        LogUtils.info("Login Data From Excel: " + data);
        return data;
    }
}
