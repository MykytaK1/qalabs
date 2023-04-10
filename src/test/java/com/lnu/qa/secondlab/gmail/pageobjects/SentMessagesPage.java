package com.lnu.qa.secondlab.gmail.pageobjects;

import com.lnu.qa.secondlab.gmail.TestUtils;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class SentMessagesPage {

    private WebDriver driver;

    public WebElement findMessage(String subject) {
        return driver
                .findElement(By.xpath("//span[text()='" + subject + "']"));
    }

    public void selectMessage(String subject) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        for (int i = 0; i < 100; i++) {
            try {
                var message = driver
                        .findElement(By.xpath("//span[text()='" + subject + "']"));
                System.out.println(Thread.currentThread());
                var parent = message.findElement(By.xpath("ancestor::tr"));
                var checkbox1 = parent
                        .findElement(By.xpath("//td[@data-tooltip='Select']//div[@role='checkbox']"));
                TestUtils.sleep(Duration.ofSeconds(5));
                checkbox1.click();
                break;
            } catch (Exception e) {
                System.out.println(e);
            }

        }



//        var checkboxw = new WebDriverWait(driver, Duration.ofSeconds(100))
//                .until(ExpectedConditions.elementToBeClickable(checkbox));

        Actions action = new Actions(driver);
        List<WebElement> checkBoxes = driver.findElements(By.xpath("//*[@role='checkbox']"));
        // Це блок коду, яким я це все перевірив після години невдачних спроб натиснути на чекбокс
        // останнього надісланого повідомлення.
        // Помилка падає тільки тоді, коли елемент неактивний або недоступний на сторінці,
        // хоча ми отримуємо його у нашому списку.
        // У випадку помилки - ми просто не отримаємо виведення індексу елемента.

//        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
//        for (int i = 2; i < checkBoxes.size(); i++) {
//            try {
//                WebElement checkbox = checkBoxes.get(i);
//                checkbox1.click();
//                checkbox.click();
//                System.out.println(i);
//                break;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        //Performing the mouse hover action on the target element.
//        action.click(checkboxw).perform();

        TestUtils.sleep(Duration.ofSeconds(5));
    }

}
