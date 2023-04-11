package com.lnu.qa.secondlab.gmail.pageobjects;

import com.lnu.qa.secondlab.gmail.TestUtils;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@AllArgsConstructor
public class ComposeYahooPage {

    private WebDriver driver;

    public void composeNewMessage(String to, String subject, String message) {
        driver
                .findElement(By.xpath("//a[@data-test-id='compose-button']"))
                .click();
        driver
                .findElement(By.xpath("//div[@data-test-id='compose']//input[@aria-owns='react-typehead-list-to']"))
                .sendKeys(to);
        driver
                .findElement(By.xpath("//div[@data-test-id='compose']//input[@data-test-id='compose-subject']"))
                .sendKeys(subject);
        driver
                .findElement(By.xpath("//div[@data-test-id='rte']"))
                .sendKeys(message);
        TestUtils.sleep(Duration.ofSeconds(3));

    }

    public void closeNewMessage() {
        driver
                .findElement(By.xpath("//div[@aria-label='Save & close']"))
                .click();
    }

    public void sendNewMessage() {
        driver
                .findElement(By.xpath("//button[@data-test-id='compose-send-button']"))
                .click();
    }

}
