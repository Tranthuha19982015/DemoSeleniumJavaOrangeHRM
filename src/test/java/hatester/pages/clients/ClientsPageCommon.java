package hatester.pages.clients;

import hatester.keywords.WebUI;
import org.openqa.selenium.By;

public class ClientsPageCommon {
    //tab
    private By tabOverview = By.xpath("//ul[@id='client-tabs']/descendant::a[text()='Overview']");
    private By tabClients = By.xpath("//ul[@id='client-tabs']/descendant::a[text()='Clients']");
    private By tabContacts = By.xpath("//ul[@id='client-tabs']/descendant::a[text()='Contacts']");

    //Clients List Page
    By buttonManageLabels = By.xpath("//ul[@id='client-tabs']/descendant::a[normalize-space()='Manage labels']");
    By buttonImportClients = By.xpath("//ul[@id='client-tabs']/descendant::a[normalize-space()='Import clients']");
    By buttonAddClient = By.xpath("//ul[@id='client-tabs']/descendant::a[normalize-space()='Add client']");

    public OverviewTab clickTabOverview() {
        WebUI.clickToElement(tabClients);
        return new OverviewTab();
    }

    public ClientsTab clickTabClients() {
        WebUI.clickToElement(tabClients);
        return new ClientsTab();
    }

    public ContactsTab clickTabContacts() {
        WebUI.clickToElement(tabClients);
        return new ContactsTab();
    }
}
