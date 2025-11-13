package hatester.common;

import org.openqa.selenium.By;

public class BasePage {
    public By menuDashboard = By.xpath("//ul[@id='sidebar-menu']/descendant::span[normalize-space()='Dashboard' and @class='menu-text ']");
}
