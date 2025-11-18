package hatester.pages;

import hatester.common.BasePage;
import hatester.constants.FrameworkConstant;
import hatester.helpers.PropertiesHelper;
import hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginPage extends BasePage {
    private By imgLoginPage = By.xpath("//div[contains(@class,'card-header')]/img");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonSignIn = By.xpath("//button[normalize-space()='Sign in']");
    private By linkForgotPassword = By.xpath("//a[normalize-space()='Forgot password?']");
    private By linkSignUp = By.xpath("//a[normalize-space()='Sign up']");
    private By errorMessageInValidEmailOrPassword = By.xpath("//form[@id='signin-form']/div[contains(@class,'alert-danger')]");
    private By errorMessageEmailRequired = By.xpath("//input[@id='email']/following-sibling::span[@id='email-error']");
    private By errorMessagePasswordRequired = By.xpath("//input[@id='password']/following-sibling::span[@id='password-error']");

    public void openRISE(String url) {
        WebUI.openURL(FrameworkConstant.URL_RISE);
    }

    public void verifyImageLoginDisplay() {
        Assert.assertTrue(WebUI.checkElementExist(imgLoginPage, 5), "The img page is not displayed.");
    }

    public void enterEmail(String email) {
        WebUI.setText(inputEmail, email);
    }

    public void enterPassword(String password) {
        WebUI.setText(inputPassword, password);
    }

    public void clickSignIn() {
        WebUI.clickToElement(buttonSignIn);
    }

    public void loginRISE(String email, String password) {
        openRISE(FrameworkConstant.URL_RISE);
        verifyImageLoginDisplay();
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        WebUI.waitForPageLoaded();
    }

    public void loginRISE() {
        openRISE(FrameworkConstant.URL_RISE);
        verifyImageLoginDisplay();
        enterEmail(FrameworkConstant.EMAIL_ADMIN);
        enterPassword(FrameworkConstant.PASSWORD_ADMIN);
        clickSignIn();
        verifyLoginSuccess();
    }

    public void verifyLoginSuccess() {
        Assert.assertTrue(WebUI.checkElementExist(menuDashboard, 5), "The Dashboard menu is not displayed.");
    }

    public void verifyLoginFailedWithEmailOrPasswordInvalid() {
        Assert.assertTrue(WebUI.checkElementExist(errorMessageInValidEmailOrPassword, 5), "The error message for invalid input is not displayed.");
    }

    public void verifyLoginFailedWithEmailRequire() {
        Assert.assertTrue(WebUI.checkElementExist(errorMessageEmailRequired, 5), "The error message for a required email is not displayed.");
    }

    public void verifyLoginFailedWithPasswordRequire() {
        Assert.assertTrue(WebUI.checkElementExist(errorMessagePasswordRequired, 5), "The error message for a required password is not displayed.");
    }
}
