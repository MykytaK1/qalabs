package com.lnu.qa.secondlab.allure;

import com.lnu.qa.secondlab.ChromeTest;
import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.ByteArrayInputStream;

public class TestListener implements TestLifecycleListener {

    @Override
    public void beforeTestStop(TestResult result) {
        if (result.getStatus().equals(Status.FAILED) || result.getStatus().equals(Status.BROKEN)) {
            ChromeDriver webDriver = ChromeTest.getWebDriverThreadLocal().get();
            byte[] screenshot = webDriver.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(result.getName(), "image/png", new ByteArrayInputStream(screenshot), ".png");
        }
    }
}
