package hatester.pages;

import hatester.common.BasePage;
import hatester.helpers.PropertiesHelper;
import hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginPage extends BasePage {
    private By headerPage = By.xpath("//h1[normalize-space()='Login']");
    private By inputEmailAddress = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    private By checkboxRememberMe = By.xpath("//input[@id='remember']");
    private By linkForgotPassword = By.xpath("//a[normalize-space()='Forgot Password?']");
    private By errorMessageInValidEmailOrPassword = By.xpath("//div[@id='alerts']/div[normalize-space()='Invalid email or password']");
    private By errorMessageEmailRequired = By.xpath("//div[normalize-space()='The Email Address field is required.']");
    private By errorMessagePasswordRequired = By.xpath("//div[normalize-space()='The Password field is required.']");

    public void openCRM(String url) {
        WebUI.openURL(PropertiesHelper.getValue("URL"));
    }

    public void verifyHeaderLoginDisplay() {
        Assert.assertTrue(WebUI.checkElementExist(headerPage, 10), "The header page is not displayed.");
    }

    public void enterEmail(String email) {
        WebUI.setText(inputEmailAddress, email);
    }

    public void enterPassword(String password) {
        WebUI.setText(inputPassword, password);
    }

    public void clickLogin() {
        WebUI.clickToElement(buttonLogin);
    }

    public void loginCRM(String email, String password) {
        openCRM(PropertiesHelper.getValue("URL"));
        verifyHeaderLoginDisplay();
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        WebUI.waitForPageLoaded();
    }

    public void loginCRM() {
        openCRM(PropertiesHelper.getValue("URL"));
        verifyHeaderLoginDisplay();
        enterEmail(PropertiesHelper.getValue("EMAIL_ADDRESS"));
        enterPassword(PropertiesHelper.getValue("PASSWORD"));
        clickLogin();
        verifyLoginSuccess();
    }

    public void verifyLoginSuccess() {
        Assert.assertTrue(WebUI.checkElementExist(menuDashboard,5), "The Dashboard menu is not displayed.");
    }

    public void verifyLoginFailedWithEmailOrPasswordInvalid(){
        Assert.assertTrue(WebUI.checkElementExist(errorMessageInValidEmailOrPassword,5),"The error message for invalid input is not displayed.");
    }

    public void verifyLoginFailedWithEmailIsNull(){
        Assert.assertTrue(WebUI.checkElementExist(errorMessageEmailRequired,5),"The error message for a required email is not displayed.");
    }

    public void verifyLoginFailedWithPasswordIsNull(){
        Assert.assertTrue(WebUI.checkElementExist(errorMessagePasswordRequired,5),"The error message for a required password is not displayed.");
    }
}
