package hatester.common;

import hatester.keywords.WebUI;
import hatester.pages.clients.ClientsPageCommon;
import org.openqa.selenium.By;

public class BasePage {
    public By menuDashboard = By.xpath("//ul[@id='sidebar-menu']/descendant::span[normalize-space()='Dashboard' and contains(@class,'menu-text')]");
    public By menuClients = By.xpath("//ul[@id='sidebar-menu']/descendant::span[normalize-space()='Clients' and contains(@class,'menu-text')]");

    public void clickMenuDashboard() {
        WebUI.clickToElement(menuDashboard);
    }

    public ClientsPageCommon clickMenuClients() {
        WebUI.clickToElement(menuClients);
        return new ClientsPageCommon();
    }
}
