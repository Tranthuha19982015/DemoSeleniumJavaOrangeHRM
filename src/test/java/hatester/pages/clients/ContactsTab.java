package hatester.pages.clients;

import org.openqa.selenium.By;

public class ContactsTab extends ClientsPageCommon{
    private By buttonImportContacts = By.xpath("//a[@id='import-btn' and normalize-space()='Import contacts']");
    //input
    private By inputFirstName = By.xpath("//form[@id='contact-form']/descendant::input[@id='first_name']");
    private By inputLastName = By.xpath("//form[@id='contact-form']/descendant::input[@id='last_name']");
    private By inputEmail = By.xpath("//form[@id='contact-form']/descendant::input[@id='email']");
    private By inputPhoneContact = By.xpath("//form[@id='contact-form']/descendant::input[@id='phone']");
    private By inputSkype = By.xpath("//form[@id='contact-form']/descendant::input[@id='skype']");
    private By inputJobTitle = By.xpath("//form[@id='contact-form']/descendant::input[@id='job_title']");
    //radio button
    private By radioButtonGenderMale = By.xpath("//form[@id='contact-form']/descendant::input[@id='gender_male']");
    private By radioButtonGenderFemale = By.xpath("//form[@id='contact-form']/descendant::input[@id='gender_female']");
    private By radioButtonGenderOther = By.xpath("//form[@id='contact-form']/descendant::input[@id='gender_other']");
    //input
    private By inputPassword = By.xpath("//form[@id='contact-form']/descendant::input[@id='login_password']");
    private By buttonGenerate = By.xpath("//form[@id='contact-form']/descendant::label[@id='generate_password']");
    private By iconViewPassword = By.xpath("//form[@id='contact-form']/descendant::label[@id='show_hide_password']");
    //checkbox
    private By checkboxCanAccessEverything = By.xpath("//form[@id='contact-form']/descendant::input[@id='can_access_everything']");
    private By inputCanAccessOnly = By.xpath("((//label[@for='can_access_only']/following-sibling::div)/descendant::ul)//input");
    private By checkboxEmailLoginDetails = By.xpath("//form[@id='contact-form']/descendant::input[@id='email_login_details']");
    //button
    private By buttonClose = By.xpath("(//form[@id='contact-form']/descendant::button[@id='save-and-add-button'])/preceding-sibling::button[normalize-space()='Close']");
    private By buttonSaveAndAddMore = By.xpath("//form[@id='contact-form']/descendant::button[@id='save-and-add-button']");
}
