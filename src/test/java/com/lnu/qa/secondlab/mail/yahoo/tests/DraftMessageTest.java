package com.lnu.qa.secondlab.mail.yahoo.tests;

import com.lnu.qa.secondlab.allure.TestListener;
import com.lnu.qa.secondlab.mail.yahoo.YahooMailTest;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.ComposePage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.DraftMessagesPage;
import com.lnu.qa.secondlab.mail.yahoo.pageobject.MainPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.lnu.qa.secondlab.TestUtils.uuid;

public class DraftMessageTest extends YahooMailTest {

    @Test
    public void messageShouldBeMovedToDraftAndSent() {
        //Login
        logIn();
        //Write message
        var composeView = new ComposePage(getWebDriver());
        var subject = uuid();
        composeView.composeNewMail(getReceiverEmailAddress(), subject, uuid());
         sleep();
        //Close new message
        composeView.closeNewMessage();
         sleep();
        //Go to main page
        var mainPage = new MainPage(getWebDriver());
        //Open drafts
        mainPage.openDraftMessages();
         sleep();
        //Find message in drafts
        var draftMessagesYahooPage = new DraftMessagesPage(getWebDriver());
        var message = draftMessagesYahooPage.findMessage(subject);
        //Open message
        draftMessagesYahooPage.openMessage(message);
         sleep();
        composeView.sendNewMail();
         sleep();
    }
}
