package com.lnu.qa.secondlab.mail.yahoo.tests;

import com.lnu.qa.secondlab.mail.yahoo.YahooMailTest;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.ComposePage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.MainPage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.SentMessagesPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.lnu.qa.secondlab.TestUtils.uuid;

public class SendMessageTest extends YahooMailTest {


    @Test(dataProvider = "subjects_data_provider")
    public void messageShouldBeSentAndRemoved(String subject) {
        //LogIn
        logIn();
        //Send mail
        var composeView = new ComposePage(getWebDriver());
        composeView.composeNewMail(getYahooEmailAddress(), subject, uuid());
         sleep();
        composeView.sendNewMail();
         sleep();
        //Open sent messages
        var mainPage = new MainPage(getWebDriver());
        mainPage.openSentMessages();
        //Find and delete the message
        var sentMessagesPage = new SentMessagesPage(getWebDriver());
        WebElement message = sentMessagesPage.findMessage(subject);
        sentMessagesPage.selectMessage(message);
         sleep();
        sentMessagesPage.deleteMessage();
         sleep();
        //Then
        Assert.expectThrows(NoSuchElementException.class, () -> sentMessagesPage.findMessage(subject));
    }

    @DataProvider(name = "subjects_data_provider")
    public Object[][] subjectsDataProvider() {
        return new Object[][]{
                {uuid()},
                {uuid()},
        };
    }


}
