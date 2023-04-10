package com.lnu.qa.secondlab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class MainTest {

    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    public WebDriverWait wait;

    public WebDriver getDriver() {
        return drivers.get();
    }

    private String link;
    private String wrongEmail;
    private int implicitlyWait;

    @BeforeClass
    public void retrieveProperties() {
        try (InputStream input = MainTest.class.getClassLoader().getResourceAsStream("selenium.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            prop.load(input);

            link = prop.getProperty("link");
            wrongEmail = prop.getProperty("wrong_email");
            implicitlyWait = Integer.parseInt(prop.getProperty("implicitly_wait"));

            System.out.printf("link: %s%n", link);
            System.out.printf("wrongEmail: %s%n", wrongEmail);
            System.out.printf("implicitlyWait: %s%n", implicitlyWait);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @BeforeMethod
    public void init(Method method) {
        WebDriver driver = new ChromeDriver();
        drivers.set(driver);

        //load the web-page
        driver.get(this.link);

        driver.manage().window().maximize();
        setDefaultWaitingPeriod(driver);

        wait = new WebDriverWait(driver, Duration.ZERO);
    }

    @DataProvider(parallel = true)
    public Object[][] loginData() {
        return new Object[][]{
                {"yurii.malskyi.mpz.2020@lpnu.ua", "28.04.1999"},
                {"yurii.malskyi.pz.2016@lpnu.ua", "28.04.1999"}
        };
    }

    @Test(dataProvider = "loginData")
    public void task1(String email, String password) throws InterruptedException {
        WebDriver driver = getDriver();
        setDefaultWaitingPeriod(driver);

        login(driver, email, password);

        performDefaultSendMessageProcess(driver,
                "Software quality | Lab 2 | Task 1", "This is an auto-generated mail for task 1:)", email);

        // redirecting to "Sent messages" tab
        redirectTo(driver, EmailTab.SENT_TAB);

        Thread.sleep(1000);

        // При тестуванні функціоналу, я випадково знайшов цікаву логіку роботи чекбоксів в gmail
        // Якщо із вкладки Вхідні перейти у вкладку Надіслані, при отримані списку чекбоксів повертає об'єкти
        // із ОБОХ!!! вкладок. Схоже що це зроблено для зменшення навантаження серверної частини.
        // Об'єкти скоріш за все знаходяться в одному масиві та фільтруються при виведенні на екран відповідно до вкладки якій належать дані
        // Таким чином у масиві будуть усі чекбокси із обох вкладок + 2 чекбокси загального вибору.
        //
        // Індексація наступна:
        // елемент[0] - Обрати всі повідомлення у вкладці Вхідні
        // елемент[1] - Обрати всі повідомлення у вкладці Надіслані
        // елемент[2] ... елемент[розмірМасиву / 2] - елементи-чекбокси із вкладки Вхідні
        // елемент[(розмірМасиву / 2) + 1] ... елемент[розмірМасиву - 1] - елементи-чекбокси із вкладки Надіслані
        // Таким чином, щоб видалити останнє надіслане повідомлення, потрібно отримати елемент[(розмірМасиву / 2) + 1]

        //  removing message
        List<WebElement> checkBoxes = driver.findElements(By.xpath("//*[@role='checkbox']"));
        System.out.println("checkboxes count: " + checkBoxes.size());
        // Це блок коду, яким я це все перевірив після години невдачних спроб натиснути на чекбокс
        // останнього надісланого повідомлення.
        // Помилка падає тільки тоді, коли елемент неактивний або недоступний на сторінці,
        // хоча ми отримуємо його у нашому списку.
        // У випадку помилки - ми просто не отримаємо виведення індексу елемента.

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
        for (int i = 2; i < checkBoxes.size(); i++) {
            try {
                WebElement checkbox = checkBoxes.get(i);
                checkbox.click();
                System.out.println(i);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setDefaultWaitingPeriod(driver);

        Thread.sleep(1000);

        WebElement recycleBin = driver.findElements(By.className("mA")).get(8);
        recycleBin.click();

        Thread.sleep(3000);

    }

    @Test(dataProvider = "loginData")
    public void task2(String email, String password) throws InterruptedException {
        WebDriver driver = getDriver();
        setDefaultWaitingPeriod(driver);

        login(driver, email, password);

        openMessageWindow(driver);

        fillMessage(driver, "Software quality | Lab 2 | Task 2", "This is an auto-generated mail for task 2:)");
        Thread.sleep(1500);

        closeMessageWindow(driver);
        Thread.sleep(1000);

        redirectTo(driver, EmailTab.DRAFTS_TAB);
        Thread.sleep(1500);

        System.out.println("driver.findElements(By.xpath(\"//*[@role='row']\"): " + driver.findElements(By.xpath("//*[@role='row']")).size());
        int messageRows = driver.findElements(By.xpath("//*[@role='row']")).size();

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
        for (int i = 0; i < messageRows; i++) {
            try {
                driver.findElements(By.xpath("//*[@role='row']")).get(i).click();
                System.out.println("i: " + i);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        setDefaultWaitingPeriod(driver);

        Thread.sleep(2000);

        sendMessage(driver);

        Thread.sleep(2000);

    }

    @Test(dataProvider = "loginData")
    public void task3(String email, String password) throws InterruptedException {
        WebDriver driver = getDriver();
        setDefaultWaitingPeriod(driver);

        login(driver, email, password);

        for (int i = 0; i < 3; i++) {
            performDefaultSendMessageProcess(driver,
                    "Software quality | Lab 2 | Task 3", "This is an auto-generated mail for task 3:)", email);
        }

        // TODO - розказати чому на цьому моменті мій тест падав з шансом в 40%
//        List<WebElement> stars = driver.findElements(By.xpath("//*[@class='apU xY']"));
//        System.out.println("stars count: " + stars.size());

//        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
//        for (int i = 0; i < 3; i++) {
//            stars.get(i).click();
//        }

        starMessages(driver);


        Thread.sleep(2000);

        redirectTo(driver, EmailTab.STARRED_TAB);

        Thread.sleep(1000);

        List<WebElement> checkBoxes = driver.findElements(By.xpath("//*[@role='checkbox']"));

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);

        // messages to delete
        int messages_count = 3;
        for (int i = 2; i < checkBoxes.size() && messages_count > 0; i++) {
            try {
                checkBoxes.get(i).click();
                System.out.println(i);
                messages_count--;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        setDefaultWaitingPeriod(driver);

        Thread.sleep(1000);

        WebElement recycleBin = driver.findElements(By.className("mA")).get(8);
        recycleBin.click();

        System.out.println("Messages removed!");

        Thread.sleep(3000);

    }

    @Test(dataProvider = "loginData")
    public void task4(String email, String password) throws InterruptedException {
        WebDriver driver = getDriver();
        setDefaultWaitingPeriod(driver);

        login(driver, email, password);

        openMessageWindow(driver);

        fillMessage(driver, "Software quality | Lab 2 | Task 4", "This is an auto-generated mail for task 4:)", wrongEmail);

        Thread.sleep(1000);
        sendMessage(driver);

        Thread.sleep(1000);

        // confirm alert about wrong email
        WebElement alertWindowButton = driver.findElement(By.className("J-at1-auR"));
        alertWindowButton.click();

        Thread.sleep(1000);

        clearMessageReceiver(driver, wrongEmail);

        fillMessageReceiver(driver);

        Thread.sleep(1000);

        sendMessage(driver);

        redirectTo(driver, EmailTab.SENT_TAB);

        Thread.sleep(1500);

    }

    private void login(WebDriver driver, String email, String password) {
        new SignInPage(driver).signIn(email, password);
    }

    private void openMessageWindow(WebDriver driver) {
        new NewMessageModal(driver).openMessageWindow();
    }

    private void fillMessage(WebDriver driver, String title, String message, String... email) {
        new NewMessageModal(driver).fillMessage(title, message, email);
    }

    private void fillMessageReceiver(WebDriver driver, String... emails) {
        new NewMessageModal(driver).fillMessageReceiver(emails);
    }

    private void clearMessageReceiver(WebDriver driver, String email) {
        new NewMessageModal(driver).clearMessageReceiver(email);
    }

    private void closeMessageWindow(WebDriver driver) {
        new NewMessageModal(driver).closeMessageWindow();
    }

    private void sendMessage(WebDriver driver) throws InterruptedException {
        new NewMessageModal(driver).sendMessage();
    }

    /**
     * Perform the default sending flow:
     * 1. Opens up a new message window
     * 2. Filling the data
     * 3. Sending the message
     *
     * @param driver  WebDriver instance;
     * @param title   Title of the new message;
     * @param message Message of the new message;
     * @param email   Receiver's email;
     * @throws InterruptedException if something goes wrong with the thread.
     */
    private void performDefaultSendMessageProcess(WebDriver driver, String title, String message, String email) throws InterruptedException {
        new NewMessageModal(driver).performDefaultSendMessageProcess(title, message, email);
    }

    /**
     * Mark messages as favorite
     * Marking from newest to oldest (from top to bottom)
     *
     * @param driver WebDriver instance;
     */
    private void starMessages(WebDriver driver) {
        for (int i = 0; i < 3; i++) {
            while (true) {
                try {
                    driver.findElements(By.xpath("//*[@class='apU xY']")).get(i).click();
                    break;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("Marking is complete!");
    }

    /**
     * Perform redirect onto another page
     *
     * @param driver WebDriver instance;
     * @param tab    the tab you want to redirect to.
     */
    private void redirectTo(WebDriver driver, EmailTab tab) {
        RedirectMenu.redirectTo(driver, tab);
    }

    /**
     * Setting the timeout to wait for the needed element to be loaded.
     */
    private void setDefaultWaitingPeriod(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        drivers.get().close();
        drivers.get().quit();
        drivers.remove();
    }
}
