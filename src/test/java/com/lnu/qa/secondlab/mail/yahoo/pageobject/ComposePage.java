package com.lnu.qa.secondlab.mail.yahoo.pageobject;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@AllArgsConstructor
public class ComposePage {

    private WebDriver driver;

    @Step("Compose New Mail")
    public void composeNewMail(String to, String subject, String message) {
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
    }

    @Step("Close New Message")
    public void closeNewMessage() {
        driver
                .findElement(By.xpath("//button[@data-test-id='icon-btn-close']"))
                .click();
    }


    @Step("Send New Message")
    public void sendNewMail() {
        driver
                .findElement(By.xpath("//button[@data-test-id='compose-send-button']"))
                .click();
    }

    @Step("Find IncorrectToError Message")
    public WebElement findIncorrectToErrorMessage() {
        return driver
                .findElement(By.xpath("//span[text()='Please correct these email addresses before sending: ']"));
    }

    @Step("Close Error Message")
    public void closeErrorMessage() {
        driver
                .findElement(By.xpath("//button[@data-test-id='icon-btn-close_mini']"))
                .click();
    }

}
