package com.lnu.qa.firstlab.controllers;

import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class MockitoSetup {

    private MockitoSession mockito;

    @BeforeClass
    public void setupMockito() {
        // initialize session to start mocking
        mockito = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
    }

    @AfterClass
    public void tearDownMockito() {
        // It is necessary to finish the session so that Mockito
        // can detect incorrect stubbing and validate Mockito usage
        mockito.finishMocking();
    }
}
