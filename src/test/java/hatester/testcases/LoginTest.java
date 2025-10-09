package hatester.testcases;

import hatester.common.BaseTest;
import hatester.helpers.PropertiesHelper;
import hatester.pages.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @Test
    public void testLoginCRMSuccess() {
        loginPage = new LoginPage();
        loginPage.openCRM(PropertiesHelper.getValue("URL"));
        loginPage.verifyHeaderLoginDisplay();
        loginPage.loginCRM();
        loginPage.verifyLoginSuccess();
    }
}
