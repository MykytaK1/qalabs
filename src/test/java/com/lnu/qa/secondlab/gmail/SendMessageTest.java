package com.lnu.qa.secondlab.gmail;

import com.lnu.qa.secondlab.gmail.pageobjects.ComposeYahooPage;
import com.lnu.qa.secondlab.gmail.pageobjects.MainYahooPage;
import com.lnu.qa.secondlab.gmail.pageobjects.SentMessagesYahooPage;
import com.lnu.qa.secondlab.gmail.pageobjects.SignInYahooPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.UUID;

public class SendMessageTest extends GmailTest {


    @Test
    public void signInTest(){
        WebDriver driver = getWebDriver();

        driver.get(getEmailUrl());
//        driver.manage().window().maximize();

        SignInYahooPage signInPage = new SignInYahooPage(getWebDriver());
        signInPage.signIn(getEmailAddress(), getEmailPassword());

        var composeView = new ComposeYahooPage(driver);
        String subject = UUID.randomUUID().toString();
        composeView.composeNewMessage("mykyta.kostiuk.mpzip.2022@lpnu.ua", subject, "message");
        composeView.sendNewMessage();

        var mainPage = new MainYahooPage(getWebDriver());
        mainPage.openSentMessages();

        TestUtils.sleep(Duration.ofSeconds(5));
        var sentMessagesPage = new SentMessagesYahooPage(getWebDriver());
        WebElement message = sentMessagesPage.findMessage(subject);
        sentMessagesPage.selectMessage(message);

    }
}
