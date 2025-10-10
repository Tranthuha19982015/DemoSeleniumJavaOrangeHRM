package hatester.pages;

import hatester.common.BasePage;
import hatester.helpers.PropertiesHelper;
import hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginPage extends BasePage {
    private By headerPage = By.xpath("//h1[normalize-space()='Login']");
    private By emailAddress = By.xpath("//input[@id='email']");
    private By password = By.xpath("//input[@id='password']");
    private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    private By checkboxRememberMe = By.xpath("//input[@id='remember']");
    private By linkForgotPassword = By.xpath("//a[normalize-space()='Forgot Password?']");

    public void openCRM(String url) {
        WebUI.openURL(PropertiesHelper.getValue("URL"));
    }

    public void verifyHeaderLoginDisplay() {
        Assert.assertTrue(WebUI.checkElementExist(headerPage, 10), "The header page is not displayed.");
    }

    public void enterEmail(String email) {
        WebUI.setText(emailAddress, email);
    }

    public void enterPassword(String passw) {
        WebUI.setText(password, passw);
    }

    public void clickLogin() {
        WebUI.clickToElement(buttonLogin);
    }

    public void loginCRM(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        WebUI.waitForPageLoaded();
    }

    public void loginCRM() {
        enterEmail(PropertiesHelper.getValue("EMAIL_ADDRESS"));
        enterPassword(PropertiesHelper.getValue("PASSWORD"));
        clickLogin();
        verifyLoginSuccess();
    }

    public void verifyLoginSuccess() {
        Assert.assertTrue(WebUI.checkElementExist(menuDashboard,5), "The Dashboard menu is not displayed.");
    }
}
