package hatester.testcases;

import hatester.common.BaseTest;
import hatester.common.DataProviderFactory;
import hatester.helpers.PropertiesHelper;
import hatester.pages.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @Test(priority = 1, dataProvider = "data_provider_login_excel", dataProviderClass = DataProviderFactory.class)
    public void testLoginSuccess(String email, String password) {
        loginPage = new LoginPage();
        loginPage.openCRM(PropertiesHelper.getValue("URL"));
        loginPage.verifyHeaderLoginDisplay();
        loginPage.loginCRM(email, password);
        loginPage.verifyLoginSuccess();
    }
}
