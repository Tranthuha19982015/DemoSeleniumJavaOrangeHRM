package hatester.pages.clients;

import hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.Hashtable;

public class ClientsTab extends ClientsPageCommon {
    //Clients List Page
    private By inputSearchClients = By.xpath("//div[@id='client-table_filter']/descendant::input[@type='search']");

    private By firstRowNameClients(String name) {
        String xpathColumnName = "//table[@id='client-table']/descendant::td[normalize-space()='" + name + "']";
        return By.xpath(xpathColumnName);
    }

    private By buttonEdit(String name) {
        String xpathButtonEdit = "(//table[@id='client-table']/descendant::td[normalize-space()='" + name + "'])/following-sibling::td/a[@class='edit']";
        return By.xpath(xpathButtonEdit);
    }

    private By buttonDelete(String name) {
        String xpathButtonEdit = "(//table[@id='client-table']/descendant::td[normalize-space()='" + name + "'])/following-sibling::td/a[@class='delete']";
        return By.xpath(xpathButtonEdit);
    }

    private By buttonAddContact = By.xpath("//a[normalize-space()='Add contact' and @data-title='Add contact']");

    //Pop-up Add client
    private By headerAddClient = By.xpath("//h4[@id='ajaxModalTitle']");
    //radio
    private By radioButtonTypeOrganization = By.xpath("//input[@id='type_organization']");
    private By radioButtonTypePerson = By.xpath("//input[@id='type_organization']");
    //input
    private By inputCompanyName = By.xpath("//input[@id='company_name']");
    private By errorMessageRequireName = By.xpath("//span[@id='company_name-error']");
    //dropdown Owner
    private By dropdownOwner = By.xpath("(//label[normalize-space()='Owner' and @for='created_by']/following-sibling::div)/descendant::a");
    private By inputSearchOwner = By.xpath("//input[@id='created_by']/following::label[normalize-space()='Owner']/parent::div");
    //input
    private By inputAddress = By.xpath("//textarea[@id='address']");
    private By inputCity = By.xpath("//form[@id='client-form']/descendant::input[@id='city']");
    private By inputState = By.xpath("//form[@id='client-form']/descendant::input[@id='state']");
    private By inputZip = By.xpath("//form[@id='client-form']/descendant::input[@id='zip']");
    private By inputCountry = By.xpath("//form[@id='client-form']/descendant::input[@id='country']");
    private By inputPhone = By.xpath("//form[@id='client-form']/descendant::input[@id='phone']");
    private By inputWebsite = By.xpath("//form[@id='client-form']/descendant::input[@id='website']");
    private By inputVATNumber = By.xpath("//form[@id='client-form']/descendant::input[@id='vat_number']");
    private By inputGSTNumber = By.xpath("//form[@id='client-form']/descendant::input[@id='gst_number']");
    private By inputClientsGroups = By.xpath("((//label[@for='groups']/following-sibling::div)/descendant::ul)/descendant::input");

    private By getValueClientsGroups(String value) {
        String xpathValue = "(//label[@for='groups']/following-sibling::div)/descendant::li[normalize-space()='" + value + "']";
        return By.xpath(xpathValue);
    }

    private By inputLabel = By.xpath("((//label[@for='client_labels']/following-sibling::div)/descendant::ul)/descendant::input");
    //button
    private By buttonClose = By.xpath("//button[@id='save-and-continue-button']/preceding-sibling::button[normalize-space()='Close']");
    private By buttonSaveAndContinue = By.xpath("//button[@id='save-and-continue-button']");
    private By buttonSave = By.xpath("//button[@id='save-and-continue-button']/following-sibling::button[normalize-space()='Save']");


    public void clickButtonAddClient() {
        WebUI.clickToElement(buttonAddClient);
    }

    public void verifyHeaderAddClienDisplay() {
        Assert.assertTrue(WebUI.checkElementExist(headerAddClient, 10), "The header Add Client is not display!");
    }

    public void fillDataNewClient(String type, String companyName, String owner, String address, String city, String state, String zip,
                                  String country, String phone, String website, String vat, String gst, String clientGroups, String labels) {
        if (type.equalsIgnoreCase("Person")) {
            WebUI.clickToElement(radioButtonTypePerson);
        } else {
            if (WebUI.waitForElementVisible(radioButtonTypeOrganization).isSelected() == false) {
                WebUI.clickToElement(radioButtonTypeOrganization);
            }
        }
        WebUI.setText(inputCompanyName, companyName);

        WebUI.clickToElement(dropdownOwner);
        WebUI.setTextAndKey(inputSearchOwner, owner, Keys.ENTER);

        WebUI.setText(inputAddress, address);
        WebUI.setText(inputCity, city);
        WebUI.setText(inputState, state);
        WebUI.setText(inputZip, zip);
        WebUI.setText(inputCountry, country);
        WebUI.setText(inputPhone, phone);
        WebUI.setText(inputWebsite, website);
        WebUI.setText(inputVATNumber, vat);
        WebUI.setText(inputGSTNumber, gst);
        WebUI.setTextAndKey(inputClientsGroups, clientGroups, Keys.ENTER);
        WebUI.setTextAndKey(inputLabel, labels, Keys.ENTER);
    }

    public void clickButtonSave() {
        WebUI.clickToElement(buttonSave);
    }

    public void clickButtonSaveAndContinue() {
        WebUI.clickToElement(buttonSaveAndContinue);
    }

    public void verifyClientNameRequire() {
        Assert.assertFalse(WebUI.checkElementExist(errorMessageRequireName, 5), "Client name is not null.");
    }

    public void searchAndCheckNewClient(String name) {
        WebUI.waitForPageLoaded();
        WebUI.clickToElement(inputSearchClients);
        WebUI.setTextAndKey(inputSearchClients, name, Keys.ENTER);
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(firstRowNameClients(name)), "The client was not added correctly just now.");
    }

    public void clickButtonEdit(String name) {
        WebUI.clickToElement(buttonEdit(name));
    }

    public void verifyNewClientOnEdit(SoftAssert softAssert, String type, String companyName, String owner, String address, String city, String state,
                                      String zip, String country, String phone, String website, String vat, String gst, String clientGroups, String labels) {
        if (type.equalsIgnoreCase("Person")) {
            softAssert.assertTrue(WebUI.waitForElementVisible(radioButtonTypePerson).isSelected(),"The type selected for this client is incorrect.");
        } else {
            softAssert.assertTrue(WebUI.waitForElementVisible(radioButtonTypeOrganization).isSelected(),"The type selected for this client is incorrect.");
        }
        softAssert.assertEquals(WebUI.getElementAttribute(inputCompanyName, "value"), companyName, "The Name entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementText(dropdownOwner), owner, "The Owner selected for the new client is incorrect");
        softAssert.assertEquals(WebUI.getElementAttribute(inputAddress, "value"), address, "The Address entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputCity, "value"), city, "The City entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputState, "value"), state, "The State entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputZip, "value"), zip, "The Zip entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputCountry, "value"), country, "The Country entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputPhone, "value"), phone, "The Phone entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputWebsite, "value"), website, "The Website entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputVATNumber, "value"), vat, "The VAT Number entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputGSTNumber, "value"), gst, "The GST Number entered for the new client is incorrect.");
        softAssert.assertTrue(WebUI.checkElementExist(getValueClientsGroups(clientGroups),5), "The GST Number entered for the new client is incorrect.");
        softAssert.assertEquals(WebUI.getElementAttribute(inputGSTNumber, "value"), gst, "The GST Number entered for the new client is incorrect.");
    }

    public void viewClient(String name) {
        WebUI.clickToElement(firstRowNameClients(name));
    }

    public ContactsTab clickButtonAddContact() {
        return new ContactsTab();
    }
}
