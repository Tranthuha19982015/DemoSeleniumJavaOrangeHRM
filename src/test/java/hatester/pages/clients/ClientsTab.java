package hatester.pages.clients;

import hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

public class ClientsTab extends ClientsPageCommon{
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

    //Pop-up Add client
    private By headerAddClient = By.xpath("//h4[@id='ajaxModalTitle']");
    //radio
    private By radioButtonTypeOrganization = By.xpath("//input[@id='type_organization']");
    private By radioButtonTypePerson = By.xpath("//input[@id='type_organization']");
    //input
    private By inputCompanyName = By.xpath("//input[@id='company_name']");
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
    private By inputGTSNumber = By.xpath("//form[@id='client-form']/descendant::input[@id='gst_number']");
    private By inputClientsGroups = By.xpath("((//label[@for='groups']/following-sibling::div)/descendant::ul)/descendant::input");
    private By inputLabel = By.xpath("((//label[@for='client_labels']/following-sibling::div)/descendant::ul)/descendant::input");


    public void clickButtonAddClient() {
        WebUI.clickToElement(buttonAddClient);
    }

    public void verifyHeaderAddClienDisplay() {
        Assert.assertTrue(WebUI.checkElementExist(headerAddClient, 10), "The header Add Client is not display!");
    }

    public void fillDataNewClient(String name, String owner,String address, int flag) {
        if (flag == 1) {
            WebUI.clickToElement(radioButtonTypePerson);
        }
        WebUI.setText(inputCompanyName, name);

        WebUI.clickToElement(dropdownOwner);
        WebUI.setTextAndKey(inputSearchOwner, owner, Keys.ENTER);

        WebUI.setText(inputAddress,address);
    }
}
