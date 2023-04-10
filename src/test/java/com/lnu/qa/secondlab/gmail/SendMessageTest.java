package com.lnu.qa.secondlab.gmail;

import com.lnu.qa.secondlab.gmail.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.UUID;

public class SendMessageTest extends GmailTest {


    @Test
    public void signInTest() throws InterruptedException {
        WebDriver driver = getWebDriver();

        driver.get(getEmailUrl());
//        driver.manage().window().maximize();

        SignInYahooPage signInPage = new SignInYahooPage(getWebDriver());
        signInPage.signIn(getEmailAddress(), getEmailPassword());

        var composeView = new ComposePage(driver);
        String subject = UUID.randomUUID().toString();
        composeView.composeNewMessage("mykyta.kostiuk.mpzip.2022@lpnu.ua", subject, "message");
        composeView.sendNewMessage();
        TestUtils.sleep(Duration.ofSeconds(5));

        var mainPage = new MainPage(getWebDriver());
        mainPage.openSentMessages();

        var sentMessagesPage = new SentMessagesPage(getWebDriver());
        WebElement message = sentMessagesPage.findMessage(subject);
        sentMessagesPage.selectMessage(subject);

    }
}
