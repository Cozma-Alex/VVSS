package org.example.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

import java.util.List;

@DefaultUrl("https://github.com")
public class DictionaryPage extends PageObject {



    @FindBy(css = "a.HeaderMenu-link--sign-in")
    WebElement signInLink;

    @FindBy(css = "div.flash.flash-full.flash-error .js-flash-alert")
    WebElement loginErrorMessage;


    @FindBy(css = "img.avatar.circle")
    private WebElementFacade profileAvatar;

    @FindBy(css = "button[data-target='qbsearch-input.inputButton']")
    private WebElementFacade searchButton;

    @FindBy(css = "input#query-builder-test")
    private WebElementFacade searchInput;

    @FindBy(css = "a.prc-Link-Link-85e08[href='/torvalds/linux']")
    private WebElementFacade torvaldsLinuxLink;

    @FindBy(css = "button.btn.BtnGroup-item[aria-label^='Star this repository']")
    private WebElementFacade starButton;


    @FindBy(css = "a[href='/bangabriel16666666666666?tab=stars']")
    private WebElementFacade yourStarsLabel;

    @FindBy(css = "button[aria-label='Unstar this repository']")
    private WebElementFacade unstarButton;

    public boolean unstar_repository() {
        unstarButton.click();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Force refresh the page
        getDriver().navigate().refresh();

        return getDriver().getPageSource().contains("Linux kernel source tree");

    }
    public boolean go_to_your_stars() {
        profileAvatar.click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        yourStarsLabel.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getDriver().getCurrentUrl().endsWith("?tab=stars");

    }
    public boolean star_repository() {

        starButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getDriver().getPageSource().contains("Unstar");
    }

    public boolean click_search_button() {
        searchButton.click();
        return searchInput.isDisplayed();
    }

    public void search_for(String term) {
        searchInput.sendKeys(term);
        searchInput.submit();
    }
    public boolean linux_torvald_linux_present() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return torvaldsLinuxLink.isDisplayed();
    }

    public boolean click_linux_torvalds_linux_link() {
        torvaldsLinuxLink.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getDriver().getPageSource().contains("Linux kernel source tree");
    }
    public boolean avatar_is_present() {

        return profileAvatar.isDisplayed();
    }

    public void enter_login_data(String email, String password) {
        WebElement emailField = find(By.id("login_field"));
        WebElement passwordField = find(By.id("password"));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        WebElement signInButton = find(By.name("commit"));
        signInButton.click();
    }




    public String get_login_error_message() {
        return loginErrorMessage.getText();
    }
    public void click_sign_in_link() {
        signInLink.click();
    }

    public List<String> getDefinitions() {
        WebElementFacade definitionList = find(By.tagName("ol"));
        return definitionList.findElements(By.tagName("li")).stream()
                .map( element -> element.getText() )
                .collect(Collectors.toList());
    }
}