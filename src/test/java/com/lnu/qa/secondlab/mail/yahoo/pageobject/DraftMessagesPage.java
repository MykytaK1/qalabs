package com.lnu.qa.secondlab.mail.yahoo.pageobject;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@AllArgsConstructor
public class DraftMessagesPage {

    private WebDriver driver;

    public WebElement findMessage(String subject) {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOf(driver
                .findElement(By.xpath("//span[text()='" + subject + "']"))));
    }

    public void openMessage(WebElement message) {
        var parent = message.findElement(By.xpath("ancestor::div[contains(@class, 'message-list-item')]"));
        parent.click();
    }

}
