package com.lnu.qa.secondlab.mail.yahoo.pageobject;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@AllArgsConstructor
public class MainPage {

    private WebDriver driver;

    public void openSentMessages() {
        driver
                .findElement(By.xpath("//div[@data-test-folder-container='Sent']"))
                .click();
    }

    public void openDraftMessages() {
        driver
                .findElement(By.xpath("//div[@data-test-folder-container='Draft']"))
                .click();
    }

    public void openInboxMessages() {
        driver
                .findElement(By.xpath("//div[@data-test-folder-container='Inbox']"))
                .click();
    }


    public void openStarredMessages() {
        driver
                .findElement(By.xpath("//a[@data-test-smartview-type='STARRED']"))
                .click();
    }

}
