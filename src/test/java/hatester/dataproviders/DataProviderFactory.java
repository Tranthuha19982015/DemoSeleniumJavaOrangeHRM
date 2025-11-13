package hatester.dataproviders;

import hatester.constants.FrameworkConstant;
import hatester.helpers.ExcelHelper;
import hatester.helpers.SystemHelper;
import hatester.utils.LogUtils;
import org.testng.annotations.DataProvider;

import java.util.Arrays;

public class DataProviderFactory {

    private Object[][] getData(int startRow, int endRow) {
        ExcelHelper excelHelper = new ExcelHelper();
        String excelPath = SystemHelper.getCurrentDir() + FrameworkConstant.EXCEL_DATA_FILE_PATH;
        String sheetName = "Login";
        Object[][] data = excelHelper.getDataHashTable(excelPath, sheetName, startRow, endRow);
        //Arrays.toString(): chỉ in được nội dung của mảng 1 chiều
        //Arrays.deepToString(): để in toàn bộ nội dung của mảng nhiều chiều
        LogUtils.info("Loaded data from Excel [rows " + startRow + "-" + endRow + "] => " + Arrays.deepToString(data));
        return data;
    }


    @DataProvider(name = "data_login_excel_success", parallel = false)
    public Object[][] dataLoginSuccess() {
        return getData(1, 3);
    }

    @DataProvider(name = "data_login_excel_email_invalid", parallel = true)
    public Object[][] dataLoginFailWithEmailInvalid() {
        return getData(4, 5);
    }

    @DataProvider(name = "data_login_excel_password_invalid", parallel = false)
    public Object[][] dataLoginFailWithPasswordInvalid() {
        return getData(6, 7);
    }

    @DataProvider(name = "data_login_excel_email_require", parallel = false)
    public Object[][] dataLoginFailWithEmailRequire() {
        return getData(8, 8);
    }

    @DataProvider(name = "data_login_excel_password_require", parallel = false)
    public Object[][] dataLoginFailWithPasswordRequire() {
        return getData(9, 9);
    }
}
