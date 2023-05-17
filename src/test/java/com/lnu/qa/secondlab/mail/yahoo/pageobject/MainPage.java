package com.lnu.qa.secondlab.mail.yahoo.pageobject;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@AllArgsConstructor
public class MainPage {

    private WebDriver driver;

    @Step("Open Sent Messages")
    public void openSentMessages() {
        driver
                .findElement(By.xpath("//div[@data-test-folder-container='Sent']"))
                .click();
    }

    @Step("Open Draft Messages")
    public void openDraftMessages() {
        driver
                .findElement(By.xpath("//div[@data-test-folder-container='Draft']"))
                .click();
    }

    @Step("Open Inbox Messages")
    public void openInboxMessages() {
        driver
                .findElement(By.xpath("//div[@data-test-folder-container='Inbox']"))
                .click();
    }

    @Step("Open Starred Messages")
    public void openStarredMessages() {
        driver
                .findElement(By.xpath("//a[@data-test-smartview-type='STARRED']"))
                .click();
    }

}
