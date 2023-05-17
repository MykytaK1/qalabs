package com.lnu.qa.secondlab.mail.yahoo.tests;

import com.lnu.qa.secondlab.allure.TestListener;
import com.lnu.qa.secondlab.mail.yahoo.YahooMailTest;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.ComposePage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.MainPage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.SentMessagesPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.lnu.qa.secondlab.TestUtils.uuid;
public class IncorrectReceiverTest extends YahooMailTest {


    @Test(dataProvider = "subject_and_receiver_data_provider")
    public void shouldNotSendMessageWithIncorrectReceiver(String subject, String receiver) {
        //Login
        logIn();
        //Write and send message with incorrect receiver
        var composeView = new ComposePage(getWebDriver());
        composeView.composeNewMail(receiver, subject, uuid());
        sleep();
        composeView.sendNewMail();
        sleep();
        //Close error message
        composeView.findIncorrectToErrorMessage();
        composeView.closeErrorMessage();
        sleep();
        //Send message with correct receiver
        composeView.composeNewMail(getReceiverEmailAddress(), subject, uuid());
        composeView.sendNewMail();
        sleep();
        //Open Sent messages
        var mainPage = new MainPage(getWebDriver());
        mainPage.openSentMessages();
        //Find sent message by subject
        var sentMessagesPage = new SentMessagesPage(getWebDriver());
        WebElement message = sentMessagesPage.findMessage(subject);
        sentMessagesPage.selectMessage(message);
        sleep();
        //Then
        Assert.assertNotNull(message);
    }

    @DataProvider(name = "subject_and_receiver_data_provider")
    public Object[][] subjectAndReceiversDataProvider() {
        return new Object[][]{
                {uuid(), uuid()},
                {uuid(), uuid()}
        };
    }
}
