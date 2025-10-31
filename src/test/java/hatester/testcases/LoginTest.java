package hatester.testcases;

import hatester.common.BaseTest;
import hatester.common.DataProviderFactory;
import hatester.helpers.PropertiesHelper;
import hatester.pages.LoginPage;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @Test(priority = 1, dataProvider = "data_login_excel_success", dataProviderClass = DataProviderFactory.class)
    public void testLoginSuccess(Hashtable< String, String > data) {
        loginPage = new LoginPage();
        loginPage.loginCRM(data.get("EMAIL"), data.get("PASSWORD"));
        loginPage.verifyLoginSuccess();
    }

    @Test(priority = 2, dataProvider = "data_login_excel_email_invalid", dataProviderClass = DataProviderFactory.class)
    public void testLoginFailedWithEmailInvalid(Hashtable < String, String > data) {
        loginPage = new LoginPage();
        loginPage.loginCRM(data.get("EMAIL"), data.get("PASSWORD"));
        loginPage.verifyLoginFailedWithEmailOrPasswordInvalid();
    }

    @Test(priority = 3, dataProvider = "data_login_excel_password_invalid", dataProviderClass = DataProviderFactory.class)
    public void testLoginFailedWithPasswordInvalid(Hashtable < String, String > data) {
        loginPage = new LoginPage();
        loginPage.loginCRM(data.get("EMAIL"), data.get("PASSWORD"));
        loginPage.verifyLoginFailedWithEmailOrPasswordInvalid();
    }
}
