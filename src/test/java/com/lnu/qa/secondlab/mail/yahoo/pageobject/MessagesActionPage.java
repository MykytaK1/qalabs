package com.lnu.qa.secondlab.mail.yahoo.pageobject;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@AllArgsConstructor
public class MessagesActionPage {

    private WebDriver driver;

    @Step("Find Message")
    public WebElement findMessage(String subject) {
        return new WebDriverWait(driver, Duration.ofMinutes(1))
                .until(ExpectedConditions.visibilityOf(driver
                        .findElement(By.xpath("//span[text()='" + subject + "']"))));
    }

    @Step("Get Messages")
    public List<WebElement> getMessages() {
        return driver.findElements(By.xpath("//span[@data-test-id='message-subject']"));
    }

    @Step("Star Message")
    public void starMessages() {
        driver.findElement(By.xpath("//button[@data-test-id='toolbar-more']")).click();
        driver.findElement(By.xpath("//div[@data-test-id='more-menu-list-container']//li[@data-test-id='mark-as-starred']//a")).click();
    }

    @Step("Select Message")
    public void selectMessage(WebElement message) {
        var checkbox = message.findElement(By.xpath("ancestor::a[@data-test-id='message-list-item']//button[@data-test-id='icon-btn-checkbox']"));
        checkbox.click();
    }

    @Step("Delete Message")
    public void deleteMessage() {
        driver
                .findElement(By.xpath("//button[@data-test-id='toolbar-delete']")).click();
    }



}
